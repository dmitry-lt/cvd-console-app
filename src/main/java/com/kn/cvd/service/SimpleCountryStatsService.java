package com.kn.cvd.service;

import com.kn.cvd.model.CountryStats;
import com.kn.cvd.model.rest.Cases;
import com.kn.cvd.model.rest.CasesDetails;
import com.kn.cvd.model.rest.ConfirmedHistory;
import com.kn.cvd.model.rest.ConfirmedHistoryDetails;
import com.kn.cvd.model.rest.Vaccines;
import com.kn.cvd.model.rest.VaccinesDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple implementation of the service to return statistics by country.
 * Loads all the data at initialization.
 * Only returns country statistics data if all the pieces of data are available for the country.
 */
@Service
@Slf4j
public class SimpleCountryStatsService implements CountryStatsService {
    private final DataLoadingService dataLoadingService;

    private Cases cases;
    private Vaccines vaccines;
    private ConfirmedHistory confirmedHistory;

    public SimpleCountryStatsService(DataLoadingService dataLoadingService) {
        this.dataLoadingService = dataLoadingService;
        init();
    }

    private void init() {
        log.info("Loading data...");

        Runnable loadCases = () -> cases = dataLoadingService.loadCases();
        Runnable loadVaccines = () -> vaccines = dataLoadingService.loadVaccines();
        Runnable loadConfirmedHistory = () -> confirmedHistory = dataLoadingService.loadConfirmedHistory();

        // load data in parallel
        Stream.of(loadCases, loadVaccines, loadConfirmedHistory).parallel().forEach(Runnable::run);

        log.info("Data loaded");
    }

    @Override
    public boolean isCountryStatsAvailable(String countryName) {
        // for simplicity only return data if the country name is present in all three datasets
        return cases.containsKey(countryName)
                && vaccines.containsKey(countryName)
                && confirmedHistory.containsKey(countryName);
    }

    private CountryStats doGetCountryStats(String countryName) {
        CasesDetails allCountryCases = cases.get(countryName).get("All");
        long confirmed = allCountryCases.getConfirmed();
        long recovered = allCountryCases.getRecovered();
        long deaths = allCountryCases.getDeaths();

        VaccinesDetails vaccinesDetails = vaccines.get(countryName).get("All");
        double vaccinatedPercentage = 100.0d * vaccinesDetails.getPeopleVaccinated() / vaccinesDetails.getPopulation();

        ConfirmedHistoryDetails confirmedHistoryDetails = confirmedHistory.get(countryName).get("All");
        List<Long> twoLatestConfirmedValues = confirmedHistoryDetails.getDates().keySet()
                .stream()
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .map(confirmedHistoryDetails.getDates()::get)
                .collect(Collectors.toList());
        long newConfirmedCases = twoLatestConfirmedValues.get(0) - twoLatestConfirmedValues.get(1);

        return CountryStats.builder()
                .confirmed(confirmed)
                .recovered(recovered)
                .deaths(deaths)
                .vaccinatedPercentage(vaccinatedPercentage)
                .newConfirmedCases(newConfirmedCases)
                .build();
    }

    @Override
    public CountryStats getCountryStats(String countryName) {
        try {
            return doGetCountryStats(countryName);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Cannot retrieve data for country " + countryName, e);
        }
    }
}

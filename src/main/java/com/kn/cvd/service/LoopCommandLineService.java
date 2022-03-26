package com.kn.cvd.service;

import com.kn.cvd.model.CountryStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Service to handle command line input and output.
 * Asks the user for a country name in a loop, until the user types "exit"
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoopCommandLineService implements CommandLineService {

    private final CountryStatsService countryStatsService;
    private final PrintStream output;
    private final InputStream input;

    @Override
    public Integer runAndReturnExitCode() {
        try {
            Scanner in = new Scanner(input);
            while (true) {
                output.println("Please enter a country name or \"exit\": ");
                String input = in.nextLine();
                if ("exit".equals(input)) {
                    break;
                }
                try {
                    printCountryStats(input);
                } catch (RuntimeException e) {
                    log.warn(e.getMessage(), e);
                    output.println("Something went wrong\n");
                }
            }
            return 0;
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            return -2;
        }
    }

    private void printCountryStats(String countryName) {
        if (countryStatsService.isCountryStatsAvailable(countryName)) {
            CountryStats stats = countryStatsService.getCountryStats(countryName);
            output.printf("confirmed: %,d%n", stats.getConfirmed());
            output.printf("recovered: %,d%n", stats.getRecovered());
            output.printf("deaths: %,d%n", stats.getDeaths());
            output.printf("vaccinated level in %% of total population: %.2f%n", stats.getVaccinatedPercentage());
            output.printf("new confirmed cases since last data available: %,d%n", stats.getNewConfirmedCases());
        } else {
            output.printf("Data for country %s not found%n", countryName);
        }
        output.printf("%n");
    }
}

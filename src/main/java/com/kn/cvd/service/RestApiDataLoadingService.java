package com.kn.cvd.service;

import com.kn.cvd.model.rest.Cases;
import com.kn.cvd.model.rest.ConfirmedHistory;
import com.kn.cvd.model.rest.Vaccines;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service to load raw data from <a href="https://github.com/M-Media-Group/Covid-19-API">Covid-19-API</a>
*/
@Service
@RequiredArgsConstructor
public class RestApiDataLoadingService implements DataLoadingService {
    private static final String API_BASE = "https://covid-api.mmediagroup.fr/v1/";

    private final RestTemplate restTemplate;

    /**
     * Load cases data from <a href="https://covid-api.mmediagroup.fr/v1/cases">Covid-19-API</a>
     * @return cases data
     */
    @Override
    public Cases loadCases() {
        return restTemplate.getForObject(API_BASE + "cases", Cases.class);
    }

    /**
     * Load vaccines data from <a href="https://covid-api.mmediagroup.fr/v1/vaccines">Covid-19-API</a>
     * @return vaccines data
     */
    @Override
    public Vaccines loadVaccines() {
        return restTemplate.getForObject(API_BASE + "vaccines", Vaccines.class);
    }

    /**
     * Load historical data for confirmed cases from <a href="https://covid-api.mmediagroup.fr/v1/history?status=confirmed">Covid-19-API</a>
     * @return historical data for confirmed cases
     */
    @Override
    public ConfirmedHistory loadConfirmedHistory() {
        return restTemplate.getForObject(API_BASE + "history?status=confirmed", ConfirmedHistory.class);
    }
}

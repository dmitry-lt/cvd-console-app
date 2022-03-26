package com.kn.cvd.service;

import com.kn.cvd.model.rest.Cases;
import com.kn.cvd.model.rest.ConfirmedHistory;
import com.kn.cvd.model.rest.Vaccines;

/**
 * Service to load raw data
 */
public interface DataLoadingService {
    /**
     * Load cases data
     * @return cases data
     */
    Cases loadCases();

    /**
     * Load vaccines data
     * @return vaccines data
     */
    Vaccines loadVaccines();

    /**
     * Load historical data for confirmed cases
     * @return historical data for confirmed cases
     */
    ConfirmedHistory loadConfirmedHistory();
}

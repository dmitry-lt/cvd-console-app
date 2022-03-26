package com.kn.cvd.service;

import com.kn.cvd.model.CountryStats;

/**
 * Service to return statistics by country
 */
public interface CountryStatsService {
    /**
     * Checks if country statistics is available
     * @param countryName country name
     * @return {@code true} if statistics is available
     */
    boolean isCountryStatsAvailable(String countryName);

    /**
     * Returns country statistics
     * @param countryName country name
     * @return country statistics
     * @throws IllegalArgumentException if country statistics is not available
     */
    CountryStats getCountryStats(String countryName);
}

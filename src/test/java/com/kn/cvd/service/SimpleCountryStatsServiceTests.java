package com.kn.cvd.service;

import com.kn.cvd.model.CountryStats;
import com.kn.cvd.util.JsonDataUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleCountryStatsServiceTests {
    private DataLoadingService loadingServiceMock() {
        DataLoadingService dataLoadingService = mock(DataLoadingService.class);

        when(dataLoadingService.loadCases()).thenReturn(JsonDataUtils.getCases());
        when(dataLoadingService.loadVaccines()).thenReturn(JsonDataUtils.getVaccines());
        when(dataLoadingService.loadConfirmedHistory()).thenReturn(JsonDataUtils.getConfirmedHistory());

        return dataLoadingService;
    }

    private SimpleCountryStatsService simpleStatsService() {
        return new SimpleCountryStatsService(loadingServiceMock());
    }

    @Test
    public void givenCorrectName_whenCheckValidCountryName_thenReturnTrue() {
        // given
        SimpleCountryStatsService simpleCountryStatsService = simpleStatsService();
        String countryName = "France";

        // when
        boolean validCountryName = simpleCountryStatsService.isCountryStatsAvailable(countryName);

        // then
        assertTrue(validCountryName);
    }

    @Test
    public void givenCorrectName_whenGetCountryStats_thenReturnStats() {
        // given
        SimpleCountryStatsService simpleCountryStatsService = simpleStatsService();
        String countryName = "France";

        // when
        CountryStats stats = simpleCountryStatsService.getCountryStats(countryName);

        // then
        assertEquals(23812253, stats.getConfirmed());
        assertEquals(123, stats.getRecovered());
        assertEquals(138212, stats.getDeaths());
        assertEquals(80.65718, stats.getVaccinatedPercentage(), 0.0001);
        assertEquals(180777, stats.getNewConfirmedCases());
    }

    @Test
    public void givenIncorrectName_whenCheckValidCountryName_thenReturnFalse() {
        // given
        SimpleCountryStatsService simpleCountryStatsService = simpleStatsService();
        String countryName = "Non-existent-country";

        // when
        boolean validCountryName = simpleCountryStatsService.isCountryStatsAvailable(countryName);

        // then
        assertFalse(validCountryName);
    }

    @Test
    public void givenIncorrectName_whenGetCountryStats_thenThrowException() {
        // given
        SimpleCountryStatsService simpleCountryStatsService = simpleStatsService();
        String countryName = "IncorrectCountryName";

        // when
        Executable getCountryStats = () -> simpleCountryStatsService.getCountryStats(countryName);

        // then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, getCountryStats);
        assertEquals("Cannot retrieve data for country " + countryName, exception.getMessage());
    }
}

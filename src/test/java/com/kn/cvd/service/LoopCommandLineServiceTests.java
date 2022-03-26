package com.kn.cvd.service;

import com.kn.cvd.model.CountryStats;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoopCommandLineServiceTests {

    private LoopCommandLineService serviceWithFranceData(InputStream input, ByteArrayOutputStream outContent) {
        String countryName = "France";
        CountryStats countryStats = CountryStats.builder()
                .confirmed(10)
                .recovered(5)
                .deaths(1)
                .vaccinatedPercentage(12.34)
                .newConfirmedCases(3)
                .build();
        CountryStatsService countryStatsService = mock(CountryStatsService.class);
        when(countryStatsService.isCountryStatsAvailable(countryName)).thenReturn(true);
        when(countryStatsService.getCountryStats(countryName)).thenReturn(countryStats);

        PrintStream output = new PrintStream(outContent);

        return new LoopCommandLineService(countryStatsService, output, input);
    }

    @Test
    public void givenCorrectCountryName_whenPrintStats_thenPrintCorrectData() {
        // given
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        InputStream input = new ByteArrayInputStream("France\nexit\n".getBytes());
        LoopCommandLineService consoleService = serviceWithFranceData(input, outContent);

        // when
        consoleService.runAndReturnExitCode();

        // then
        List<String> out = Arrays.asList(outContent.toString().split("\\r?\\n"));
        assertTrue(out.contains("confirmed: 10"));
        assertTrue(out.contains("recovered: 5"));
        assertTrue(out.contains("deaths: 1"));
        assertTrue(out.contains("vaccinated level in % of total population: 12.34"));
        assertTrue(out.contains("new confirmed cases since last data available: 3"));
    }

    @Test
    public void givenIncorrectCountryName_whenPrintStats_thenPrintNotFound() {
        // given
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        InputStream input = new ByteArrayInputStream("IncorrectCountryName\nexit\n".getBytes());
        LoopCommandLineService consoleService = serviceWithFranceData(input, outContent);

        // when
        consoleService.runAndReturnExitCode();

        // then
        List<String> out = Arrays.asList(outContent.toString().split("\\r?\\n"));
        assertTrue(out.contains("Data for country IncorrectCountryName not found"));
    }

}

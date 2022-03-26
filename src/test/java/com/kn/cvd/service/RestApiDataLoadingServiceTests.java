package com.kn.cvd.service;

import com.kn.cvd.model.rest.Cases;
import com.kn.cvd.model.rest.ConfirmedHistory;
import com.kn.cvd.model.rest.Vaccines;
import com.kn.cvd.util.JsonDataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RestApiDataLoadingServiceTests {
    private static final String API_BASE = "https://covid-api.mmediagroup.fr/v1/";

    @Test
    public void givenRestTemplateMock_whenLoadCases_thenReturnApiResponse() {
        // given
        String url = API_BASE + "cases";
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForObject(url, Cases.class)).thenReturn(JsonDataUtils.getCases());
        RestApiDataLoadingService statsLoadingService = new RestApiDataLoadingService(restTemplate);

        // when
        Cases cases = statsLoadingService.loadCases();

        // then
        verify(restTemplate).getForObject(url, Cases.class);
        assertEquals(JsonDataUtils.getCases(), cases);
    }

    @Test
    public void givenRestTemplateMock_whenLoadVaccines_thenReturnApiResponse() {
        // given
        String url = API_BASE + "vaccines";
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForObject(url, Vaccines.class)).thenReturn(JsonDataUtils.getVaccines());
        RestApiDataLoadingService statsLoadingService = new RestApiDataLoadingService(restTemplate);

        // when
        Vaccines vaccines = statsLoadingService.loadVaccines();

        // then
        verify(restTemplate).getForObject(url, Vaccines.class);
        assertEquals(JsonDataUtils.getVaccines(), vaccines);
    }

    @Test
    public void givenRestTemplateMock_whenLoadConfirmedHistory_thenReturnApiResponse() {
        // given
        String url = API_BASE + "history?status=confirmed";
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForObject(url, ConfirmedHistory.class)).thenReturn(JsonDataUtils.getConfirmedHistory());
        RestApiDataLoadingService statsLoadingService = new RestApiDataLoadingService(restTemplate);

        // when
        ConfirmedHistory confirmedHistory = statsLoadingService.loadConfirmedHistory();

        // then
        verify(restTemplate).getForObject(url, ConfirmedHistory.class);
        assertEquals(JsonDataUtils.getConfirmedHistory(), confirmedHistory);
    }

}

package com.kn.cvd.model.rest;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

/**
 * Detailed history data for confirmed cases
 */
@Data
public class ConfirmedHistoryDetails {
    private Map<LocalDate, Long> dates;
}

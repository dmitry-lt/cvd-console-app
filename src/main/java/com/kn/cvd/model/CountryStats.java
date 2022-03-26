package com.kn.cvd.model;

import lombok.Builder;
import lombok.Data;

/**
 * Precalculated country statistics
 */
@Data
@Builder
public class CountryStats {
    private long confirmed;
    private long recovered;
    private long deaths;
    private double vaccinatedPercentage;
    private long newConfirmedCases;
}

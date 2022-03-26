package com.kn.cvd.model.rest;

import lombok.Data;

/**
 * Detailed cases data
 */
@Data
public class CasesDetails {
    private long confirmed;
    private long recovered;
    private long deaths;
}

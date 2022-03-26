package com.kn.cvd.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Detailed vaccines data
 */
@Data
public class VaccinesDetails {
    @JsonProperty("people_vaccinated")
    private long peopleVaccinated;
    private long population;
}

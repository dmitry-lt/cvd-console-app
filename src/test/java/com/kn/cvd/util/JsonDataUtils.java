package com.kn.cvd.util;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kn.cvd.model.rest.Cases;
import com.kn.cvd.model.rest.ConfirmedHistory;
import com.kn.cvd.model.rest.Vaccines;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Utility tool to load json data from class path resources
 */
public class JsonDataUtils {
    private static final Cases cases;
    private static final Vaccines vaccines;
    private static final ConfirmedHistory confirmedHistory;

    static {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule module = new SimpleModule();
        module.addKeyDeserializer(LocalDate.class, new KeyDeserializer() {
            @Override
            public Object deserializeKey(String key, DeserializationContext context) throws IOException {
                return LocalDate.parse(key);
            }
        });
        mapper.registerModule(module);

        try {
            cases = mapper.readValue(new ClassPathResource("rest/cases.json").getInputStream(), Cases.class);
            vaccines = mapper.readValue(new ClassPathResource("rest/vaccines.json").getInputStream(), Vaccines.class);
            confirmedHistory = mapper.readValue(new ClassPathResource("rest/confirmedHistory.json").getInputStream(), ConfirmedHistory.class);
        } catch (IOException e) {
            throw new RuntimeException("Error loading test json files", e);
        }
    }

    public static Cases getCases() {
        return cases;
    }

    public static Vaccines getVaccines() {
        return vaccines;
    }

    public static ConfirmedHistory getConfirmedHistory() {
        return confirmedHistory;
    }
}

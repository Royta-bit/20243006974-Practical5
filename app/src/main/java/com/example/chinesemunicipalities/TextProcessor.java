package com.example.chinesemunicipalities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Validation helper for municipality names.
 *
 * <p>The class keeps no reference to an Activity and has no layout of its own, so both
 * screens obtain the same validation results from the same instance methods.</p>
 */
public class TextProcessor {

    /**
     * Supported names with their capitalisation, held in the order used by the summaries in
     * {@code res/values/arrays.xml}.
     */
    private static final List<String> MUNICIPALITIES = Collections.unmodifiableList(
            Arrays.asList("Beijing", "Shanghai", "Chongqing", "Tianjin"));

    /**
     * Compares the typed text with the supported names, ignoring case and surrounding spaces.
     *
     * @param municipality text as typed by the user, possibly {@code null}
     * @return the matching name with its capitalisation, or {@code null} for null, empty,
     *         whitespace only or unsupported text
     */
    public String processMunicipality(String municipality) {
        if (isBlank(municipality)) {
            return null;
        }

        String candidate = municipality.trim();
        for (String supported : MUNICIPALITIES) {
            if (supported.equalsIgnoreCase(candidate)) {
                return supported;
            }
        }
        return null;
    }

    /**
     * @param text text to inspect
     * @return {@code true} for null, empty or whitespace only text
     */
    public boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    /**
     * @param municipalityName name already normalised by {@link #processMunicipality(String)}
     * @return the position of the name in the summary array, or {@code -1} when the name is
     *         not supported
     */
    public int positionOf(String municipalityName) {
        return MUNICIPALITIES.indexOf(municipalityName);
    }

    /**
     * @return the supported names, in the order used by {@code municipalities_array}
     */
    public List<String> supportedMunicipalities() {
        return MUNICIPALITIES;
    }
}

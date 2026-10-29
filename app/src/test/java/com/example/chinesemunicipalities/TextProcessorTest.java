package com.example.chinesemunicipalities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link TextProcessor}.
 *
 * <p>Author: He Chengxiang (20243006974)</p>
 */
public class TextProcessorTest {

    private TextProcessor processor;

    @Before
    public void createProcessor() {
        processor = new TextProcessor();
    }

    @Test
    public void capitalisesLowerCaseInput() {
        assertEquals("Beijing", processor.processMunicipality("beijing"));
    }

    @Test
    public void capitalisesUpperCaseInput() {
        assertEquals("Shanghai", processor.processMunicipality("SHANGHAI"));
        assertEquals("Tianjin", processor.processMunicipality("TIANJIN"));
    }

    @Test
    public void capitalisesMixedCaseInput() {
        assertEquals("Chongqing", processor.processMunicipality("ChOnGqInG"));
    }

    @Test
    public void ignoresSurroundingSpaces() {
        assertEquals("Tianjin", processor.processMunicipality("   tianjin "));
    }

    @Test
    public void rejectsUnsupportedName() {
        assertNull(processor.processMunicipality("Guangzhou"));
    }

    @Test
    public void rejectsMissingText() {
        assertNull(processor.processMunicipality(null));
        assertNull(processor.processMunicipality(""));
        assertNull(processor.processMunicipality("    "));
    }

    @Test
    public void blankDetectionCoversTheSameCases() {
        assertTrue(processor.isBlank(null));
        assertTrue(processor.isBlank(""));
        assertTrue(processor.isBlank("  \t "));
        assertFalse(processor.isBlank("Chongqing"));
    }

    @Test
    public void positionFollowsTheSummaryOrder() {
        assertEquals(0, processor.positionOf("Beijing"));
        assertEquals(1, processor.positionOf("Shanghai"));
        assertEquals(2, processor.positionOf("Chongqing"));
        assertEquals(3, processor.positionOf("Tianjin"));
        assertEquals(-1, processor.positionOf("Guangzhou"));
    }
}

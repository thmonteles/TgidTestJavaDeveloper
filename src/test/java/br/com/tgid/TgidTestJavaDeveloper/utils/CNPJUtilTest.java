package br.com.tgid.TgidTestJavaDeveloper.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CNPJUtilTest {

    @Test
    void testCleanCNPJ() {
        String dirtyCNPJ = "13.505.754/0001-45";
        String cleanedCNPJ = CNPJUtil.cleaning(dirtyCNPJ);
        assertEquals("13505754000145", cleanedCNPJ);
    }

    @Test
    void testValidCNPJ() {
        assertTrue(CNPJUtil.validate("13505754000145"));
    }

    @Test
    void testInvalidCNPJLength() {
        assertFalse(CNPJUtil.validate("123456789012345678"));
    }

    @Test
    void testInvalidCNPJWithSameDigits() {
        assertFalse(CNPJUtil.validate("11111111111111"));
    }

    @Test
    void testInvalidCNPJWithIncorrectDigits() {
        assertFalse(CNPJUtil.validate("13505754000146"));
    }

    @Test
    void testInvalidCNPJWithNonNumericCharacters() {
        assertFalse(CNPJUtil.validate("13A5057.540/0011-45"));
    }
}

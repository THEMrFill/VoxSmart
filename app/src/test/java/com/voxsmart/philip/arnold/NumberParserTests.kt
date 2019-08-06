package com.voxsmart.philip.arnold

import junit.framework.Assert.assertEquals
import org.junit.Test

class NumberParserTests {
    val countryCodes = HashMap<String, Int>()
    val prefixes = HashMap<String, String>()
    val parser: NumberParser
    init {
        countryCodes.put("GB", 44); prefixes.put("GB", "0");
        countryCodes.put("US", 1);  prefixes.put("US", "1");

        parser = NumberParser(countryCodes, prefixes)

    }

    @Test
    fun GBExistingCountryTest() {
         assertEquals("+442079460056", parser.parse("+442079460056", "+441614960148"));
    }
    @Test
    fun GBPrefixesTest() {
        assertEquals("+442079460056", parser.parse("02079460056", "+441614960148"));
        assertEquals("+442079460056", parser.parse("02079460056", "+441614960148"));
    }

    @Test
    fun ExistingDialingCodeTest() {
        assertEquals("+332079460056", parser.parse("+332079460056", "+441614960148"));
    }

    @Test
    fun UnknownCountryCodeTest() {
        try {
            assertEquals("+332079460056", parser.parse("02079460056", "+331614960148"));
        } catch (e: Exception) {
            assertEquals(e.message, "Unknown country code")
        }
    }
}
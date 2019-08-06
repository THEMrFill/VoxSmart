package com.voxsmart.philip.arnold

class NumberParser(val countryCodes: HashMap<String, Int>, val prefixes: HashMap<String, String>) {
    // note, there is no validation for prefixes having the same entries as the country codes
    // I'd normally have a data class that has the prefix & the dial code to avoid this problem
    fun parse(myPhone: String, otherPhone: String): String {
        // loop over the known country codes
        for ((code, value) in countryCodes) {
            // make the dialing code a string for easier checks
            val dialCode = String.format("+%d", value)
            // get the relevant prefix
            val prefix = prefixes.get(code)
            // check if we're in the right country
            if (otherPhone.substring(0, dialCode.length).equals(dialCode)) {
                // check if the dialing code correct?
                if (myPhone.substring(0, dialCode.length).equals(dialCode)) {
                    return myPhone
                }
                // check if the prefix is correct, if so, remove it and add the dialing code
                if (myPhone.substring(0, prefix!!.length).equals(prefix)) {
                    return String.format("%s%s", dialCode, myPhone.substring(prefix!!.length))
                }
                // we have a different dialing code, so just send it back
                // this could throw an error if required
                if (myPhone.substring(0, 1).equals("+")) {
                    return myPhone
                }
                // prefix & dialing code aren't correct, as there are no rules, just return it
                return myPhone
            }
        }
        // if the prefix of the provided number is unknown, throw an error
        throw Exception("Unknown country code")
    }
}
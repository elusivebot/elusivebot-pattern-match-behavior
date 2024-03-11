package com.sirnuke.elusivebot.behaviors.pattern.match.lib

/**
 * API level interface for using the pattern match library.
 */
interface PatternMatchService {
    /**
     * Attempts to match an input string to a response string.
     *
     * @param input The input string.
     * @return A corresponding response string, or null if no matches.
     */
    fun process(input: String): String?
    companion object {
        /**
         * Creates an instance of the service using the bundled default configuration.
         *
         * @return A new service instance.
         */
        fun create(): PatternMatchService = PatternMatchServiceImpl("/configs/default.json")
    }
}

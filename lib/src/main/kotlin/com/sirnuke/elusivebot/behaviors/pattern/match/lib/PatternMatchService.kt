package com.sirnuke.elusivebot.behaviors.pattern.match.lib

interface PatternMatchService {
    companion object {
        fun create(): PatternMatchService {
            return PatternMatchServiceImpl("/configs/default.json")
        }
    }

    fun process(input: String): String?
}
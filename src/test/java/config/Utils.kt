package config

import java.util.*


class Utils {

    @Suppress("UNCHECKED_CAST")
    fun <T> getProp(key: String): T {
        val props  = javaClass.classLoader.getResourceAsStream("config.properties").use {
            Properties().apply { load(it) }
        }
        return (props.getProperty(key) as T) ?: throw RuntimeException("could not find property $key")
    }
}
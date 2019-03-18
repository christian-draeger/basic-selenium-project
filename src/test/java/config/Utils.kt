package config

import java.util.*


class Utils {

    @Suppress("UNCHECKED_CAST")
    fun <T> getProp(key: String): T? {
        val path = "src/test/resources/config.properties"
        val props  = javaClass.getResourceAsStream(path).use {
            Properties().apply { load(it) }
        }
        return (props.getProperty(key) as T)
    }
}
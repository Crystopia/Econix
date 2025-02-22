package me.jesforge.econix.config

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.jesforge.econix.data.Currency
import me.jesforge.econix.data.CurrencyData
import me.jesforge.econix.data.DatabaseData
import me.jesforge.econix.data.SettingsData
import java.io.File

object ConfigManager {

    private val settingsFile = File("plugins/Econix/config.json")
    private val currencyFile = File("plugins/Econix/currency.json")

    var settings = settingsFile.loadConfig(
        SettingsData(
            database = DatabaseData()
        )
    )
    var currency = currencyFile.loadConfig(CurrencyData())

    fun save() {
        settingsFile.writeText(json.encodeToString(settings))
        currencyFile.writeText(json.encodeToString(currency))
    }

    fun reload() {
        settings = loadFromFile(settingsFile)
        currency = loadFromFile(currencyFile)
    }

}
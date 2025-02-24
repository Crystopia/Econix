package me.jesforge.econix.functions

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.jesforge.econix.Econix
import me.jesforge.econix.config.ConfigManager
import me.jesforge.econix.data.Currency
import me.jesforge.econix.database.DatabaseManager
import org.bukkit.entity.Player

class CurrencyServices {

    fun updatePlayerCurrencies(player: Player) {
        val currentCurrencies = ConfigManager.currency.currencys.map { it.value.id }

        DatabaseManager.database!!.useConnection { connection ->
            val query = connection.prepareStatement(
                "SELECT currencys FROM users WHERE uuid = ?"
            )
            query.setString(1, player.uniqueId.toString())
            val resultSet = query.executeQuery()

            if (resultSet.next()) {
                val existingCurrencies =
                    Json.decodeFromString<MutableMap<String, Double>>(resultSet.getString("currencys"))

                val newCurrencies = currentCurrencies.filterNot { it in existingCurrencies.keys }
                if (newCurrencies.isNotEmpty()) {
                    newCurrencies.forEach { currency ->
                        existingCurrencies[currency] = 0.0
                    }

                    val updateQuery = connection.prepareStatement(
                        "UPDATE users SET currencys = ? WHERE uuid = ?"
                    )
                    updateQuery.setString(1, Json.encodeToString(existingCurrencies))
                    updateQuery.setString(2, player.uniqueId.toString())
                    updateQuery.executeUpdate()

                    println("Updated currencies for player: ${player.name}")
                } else {
                    println("There are no Currency updates for player: ${player.name}")
                }
            } else {
                val defaultCurrencies = mutableMapOf<String, Double>().apply {
                    currentCurrencies.forEach { this[it] = 0.0 }
                }

                val insertQuery = connection.prepareStatement(
                    "INSERT INTO users (name, uuid, currencys) VALUES (?, ?, ?)"
                )
                insertQuery.setString(1, player.name)
                insertQuery.setString(2, player.uniqueId.toString())
                insertQuery.setString(3, Json.encodeToString(defaultCurrencies))
                insertQuery.executeUpdate()

                println("Added new player: ${player.name} with default currencies.")
            }
        }
    }

    fun registerCurrency(currency: Currency, currencyId: String) {

        if (ConfigManager.currency.currencys.contains(currencyId)) {
            return
        }
        ConfigManager.currency.currencys[currencyId] = currency
        ConfigManager.save()
        ConfigManager.reload()
        Econix.instance.server.onlinePlayers.forEach { player ->
            updatePlayerCurrencies(player)
        }
    }

}
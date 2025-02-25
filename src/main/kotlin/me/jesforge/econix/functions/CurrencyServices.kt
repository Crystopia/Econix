package me.jesforge.econix.functions

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.jesforge.econix.Econix
import me.jesforge.econix.config.ConfigManager
import me.jesforge.econix.data.Currency
import me.jesforge.econix.database.DatabaseManager
import org.bukkit.entity.Player
import java.util.UUID

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

    fun getTopFromCurrency(currency: String, limit: Int): List<Pair<String, Double>> {
        try {
            DatabaseManager.database!!.useConnection { connection ->
                val selectQuery = connection.prepareStatement(
                    "SELECT uuid, currencys FROM users"
                )
                val resultSet = selectQuery.executeQuery()
                val playersList = mutableListOf<Pair<String, Double>>()

                while (resultSet.next()) {
                    val playerUuid = resultSet.getString("uuid")
                    val existingCurrencies =
                        Json.decodeFromString<MutableMap<String, Double>>(resultSet.getString("currencys"))

                    if (existingCurrencies.containsKey(currency)) {
                        playersList.add(playerUuid to (existingCurrencies[currency] ?: 0.0))
                    }
                }
                return playersList.sortedByDescending { it.second }.take(limit)
            }
        } catch (e: Exception) {
            println("Database Error:$e")
            return emptyList()
        }
    }


    fun getTopPlayerName(currency: String, position: Int): String {

        val topPlayers = CurrencyServices().getTopFromCurrency(currency, 10)
        return if (position in 1..topPlayers.size) {
            Econix.instance.server.getPlayer(UUID.fromString(topPlayers[position - 1].first))!!.name
        } else {
            "No Player"
        }
    }

    fun getTopBalance(currency: String, position: Int): Double {

        val topPlayers = CurrencyServices().getTopFromCurrency(currency, 10)
        return if (position in 1..topPlayers.size) {
            topPlayers[position - 1].second
        } else {
            0.0
        }
    }

    fun getTopBalancePlain(currency: String, position: Int): String {

        val balance = getTopBalance(currency, position)
        return balance.toString()
    }


    fun getTopBalanceShort(currency: String, position: Int): String {

        val balance = getTopBalance(currency, position)
        return formatShort(balance)
    }

    fun getTopBalanceShortPlain(currency: String, position: Int): String {

        val balance = getTopBalance(currency, position)
        return formatShort(balance)
    }

    private fun formatShort(balance: Double): String {
        return when {
            balance >= 1_000_000 -> "%.1fM".format(balance / 1_000_000)
            balance >= 1_000 -> "%.1fk".format(balance / 1_000)
            else -> balance.toString()
        }
    }


    private fun getPlayerNameByUuid(uuid: String): String {
        return uuid
    }


}
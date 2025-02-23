package me.jesforge.econix.functions

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.jesforge.econix.database.DatabaseManager
import me.jesforge.econix.utils.ErrorCodes
import org.bukkit.entity.Player
import java.io.IOException

class UserServices {

    fun giveCurrency(player: Player, amount: Double, currency: String): ErrorCodes {
        try {
            DatabaseManager.database.useConnection { connection ->
                val insertQuery = connection.prepareStatement(
                    "SELECT currencys FROM users WHERE uuid = ?"
                )
                insertQuery.setString(1, player.uniqueId.toString())
                val resultSet = insertQuery.executeQuery()

                if (resultSet.next()) {
                    val existingCurrencies =
                        Json.decodeFromString<MutableMap<String, Double>>(resultSet.getString("currencys"))

                    if (existingCurrencies.isEmpty()) return ErrorCodes.NO_CURRENCY

                    existingCurrencies[currency] = amount + existingCurrencies[currency]!!.toDouble()

                    val updateQuery = connection.prepareStatement(
                        "UPDATE users SET currencys = ? WHERE uuid = ?"
                    )

                    updateQuery.setString(1, Json.encodeToString(existingCurrencies))
                    updateQuery.setString(2, player.uniqueId.toString())
                    updateQuery.executeUpdate()
                    return ErrorCodes.SUCCESS
                } else return ErrorCodes.ERROR
            }
        } catch (e: Exception) {
            println("Database Error:$e")
            return ErrorCodes.DATABASE_ERROR
        }
    }

    fun setCurrency(player: Player, amount: Double, currency: String): ErrorCodes {
        try {
            DatabaseManager.database.useConnection { connection ->
                val insertQuery = connection.prepareStatement(
                    "SELECT currencys FROM users WHERE uuid = ?"
                )
                insertQuery.setString(1, player.uniqueId.toString())
                val resultSet = insertQuery.executeQuery()

                if (resultSet.next()) {
                    val existingCurrencies =
                        Json.decodeFromString<MutableMap<String, Double>>(resultSet.getString("currencys"))

                    if (existingCurrencies.isEmpty()) return ErrorCodes.NO_CURRENCY

                    existingCurrencies[currency] = amount

                    val updateQuery = connection.prepareStatement(
                        "UPDATE users SET currencys = ? WHERE uuid = ?"
                    )

                    updateQuery.setString(1, Json.encodeToString(existingCurrencies))
                    updateQuery.setString(2, player.uniqueId.toString())
                    updateQuery.executeUpdate()
                    return ErrorCodes.SUCCESS
                } else return ErrorCodes.ERROR
            }
        } catch (e: Exception) {
            println("Database Error:$e")
            return ErrorCodes.DATABASE_ERROR
        }
    }

    fun removeCurrency(player: Player, amount: Double, currency: String): ErrorCodes {
        try {
            DatabaseManager.database.useConnection { connection ->
                val insertQuery = connection.prepareStatement(
                    "SELECT currencys FROM users WHERE uuid = ?"
                )
                insertQuery.setString(1, player.uniqueId.toString())
                val resultSet = insertQuery.executeQuery()

                if (resultSet.next()) {
                    val existingCurrencies =
                        Json.decodeFromString<MutableMap<String, Double>>(resultSet.getString("currencys"))

                    if (existingCurrencies.isEmpty()) return ErrorCodes.NO_CURRENCY

                    existingCurrencies[currency] = existingCurrencies[currency]!!.toDouble() - amount

                    val updateQuery = connection.prepareStatement(
                        "UPDATE users SET currencys = ? WHERE uuid = ?"
                    )

                    updateQuery.setString(1, Json.encodeToString(existingCurrencies))
                    updateQuery.setString(2, player.uniqueId.toString())
                    updateQuery.executeUpdate()
                    return ErrorCodes.SUCCESS
                } else return ErrorCodes.ERROR
            }
        } catch (e: Exception) {
            println("Database Error:$e")
            return ErrorCodes.DATABASE_ERROR
        }
    }

    fun getCurrency(player: Player, currency: String): Double {
        try {
            DatabaseManager.database.useConnection { connection ->
                val insertQuery = connection.prepareStatement(
                    "SELECT currencys FROM users WHERE uuid = ?"
                )
                insertQuery.setString(1, player.uniqueId.toString())
                val resultSet = insertQuery.executeQuery()

                if (resultSet.next()) {
                    val existingCurrencies =
                        Json.decodeFromString<MutableMap<String, Double>>(resultSet.getString("currencys"))

                    return existingCurrencies[currency]!!.toDouble()
                } else return -0.0
            }
        } catch (e: Exception) {
            println("Database Error:$e")
            return -0.0
        }
    }
}
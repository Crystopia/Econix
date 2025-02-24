package me.jesforge.econix.functions

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.jesforge.econix.Econix
import me.jesforge.econix.config.ConfigManager
import me.jesforge.econix.database.DatabaseManager
import me.jesforge.econix.utils.ErrorCodes
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class UserServices {

    fun giveCurrency(player: Player, amount: Double, currency: String): ErrorCodes {
        try {
            DatabaseManager.database!!.useConnection { connection ->
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
            DatabaseManager.database!!.useConnection { connection ->
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
            DatabaseManager.database!!.useConnection { connection ->
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
        println(DatabaseManager.database.toString())
        try {
            DatabaseManager.database!!.useConnection { connection ->

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

    fun currencyToItem(player: Player, currency: String, amount: Double) {
        val key = NamespacedKey(Econix.instance, "econix.currency.${currency}")
        val currencyItem =
            ItemStack(ConfigManager.currency.currencys[currency]!!.currencyItem.item)
        val currencyMetaData = currencyItem.itemMeta
        val displayName: Component =
            MiniMessage.miniMessage().deserialize(
                ConfigManager.currency.currencys[currency]!!.currencyItem.name.replace(
                    "{amount}",
                    amount.toString()
                ).replace("{currency}", currency)
            )
        currencyMetaData.displayName(displayName)
        currencyMetaData.setCustomModelData(ConfigManager.currency.currencys[currency]!!.currencyItem.customModeData)
        currencyMetaData.persistentDataContainer.set(
            key, PersistentDataType.DOUBLE, amount
        )
        currencyMetaData.lore(ConfigManager.currency.currencys[currency]?.currencyItem?.lore?.map { line ->
            MiniMessage.miniMessage().deserialize(
                line
            )
        } ?: emptyList())
        currencyItem.itemMeta = currencyMetaData

        player.inventory.addItem(currencyItem)
    }
}
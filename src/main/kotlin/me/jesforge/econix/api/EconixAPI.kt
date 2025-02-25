package me.jesforge.econix.api

import me.jesforge.econix.data.Currency
import me.jesforge.econix.utils.ErrorCodes
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

interface EconixAPI {

    fun getVersion(): String

    fun getBalance(player: OfflinePlayer, currency: String): Double

    fun setBalance(player: OfflinePlayer, currency: String, value: Double): ErrorCodes

    fun addBalance(player: OfflinePlayer, currency: String, value: Double): ErrorCodes

    fun removeBalance(player: OfflinePlayer, currency: String, value: Double): ErrorCodes

    fun registerBalance(currency: Currency, currencyId: String)

    fun giveCurrencyItem(player: Player, currency: String, amount: Double): Unit

    fun getTopFromCurrency(currency: String, limit: Int): List<Pair<String, Double>>

    fun getTopPlayerName(currency: String, position: Int): String

    fun getTopBalance(currency: String, position: Int): Double

    fun getTopBalancePlain(currency: String, position: Int): String

    fun getTopBalanceShort(currency: String, position: Int): String

    fun getTopBalanceShortPlain(currency: String, position: Int): String


}
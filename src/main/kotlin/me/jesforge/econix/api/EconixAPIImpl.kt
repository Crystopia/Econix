package me.jesforge.econix.api

import me.jesforge.econix.Econix
import me.jesforge.econix.data.Currency
import me.jesforge.econix.functions.CurrencyServices
import me.jesforge.econix.functions.UserServices
import me.jesforge.econix.utils.ErrorCodes
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class EconixAPIImpl : EconixAPI {

    override fun getVersion(): String {
        return Econix.instance.description.version
    }

    override fun getBalance(player: OfflinePlayer, currency: String): Double {
        return UserServices().getCurrency(player, currency)
    }

    override fun setBalance(player: OfflinePlayer, currency: String, value: Double): ErrorCodes {
        return UserServices().setCurrency(player, value, currency)
    }

    override fun addBalance(player: OfflinePlayer, currency: String, value: Double): ErrorCodes {
        return UserServices().giveCurrency(player, value, currency)
    }

    override fun removeBalance(player: OfflinePlayer, currency: String, value: Double): ErrorCodes {
        return UserServices().removeCurrency(player, value, currency)
    }

    override fun registerBalance(currency: Currency, currencyId: String) {
        return CurrencyServices().registerCurrency(currency, currencyId)
    }

    override fun giveCurrencyItem(player: Player, currency: String, amount: Double): Unit {
        return UserServices().currencyToItem(player, currency, amount)
    }

    override fun getTopFromCurrency(currency: String, limit: Int): List<Pair<String, Double>> {
        return CurrencyServices().getTopFromCurrency(currency, limit)
    }

    override fun getTopPlayerName(currency: String, position: Int): String {
        return CurrencyServices().getTopPlayerName(currency, position)
    }

    override fun getTopBalance(currency: String, position: Int): Double {
        return CurrencyServices().getTopBalance(currency, position)
    }

    override fun getTopBalancePlain(currency: String, position: Int): String {
        return CurrencyServices().getTopBalancePlain(currency, position)
    }

    override fun getTopBalanceShort(currency: String, position: Int): String {
        return CurrencyServices().getTopBalanceShort(currency, position)
    }

    override fun getTopBalanceShortPlain(currency: String, position: Int): String {
        return CurrencyServices().getTopBalanceShortPlain(currency, position)
    }

}
package me.jesforge.econix.hooks.vault

import me.jesforge.econix.Econix
import me.jesforge.econix.config.ConfigManager
import me.jesforge.econix.functions.UserServices
import me.jesforge.econix.utils.ErrorCodes
import net.milkbowl.vault.economy.AbstractEconomy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.entity.Player
import java.util.*

class EconixEconomy(currency: String) : AbstractEconomy() {

    private var currency = currency

    private var currencyConfig = ConfigManager.currency.currencys[currency]


    private fun getPlayer(uuid: String): Player? {
        return Econix.instance.server.getPlayer(UUID.fromString(uuid))
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getName(): String {
        return currency
    }

    override fun hasBankSupport(): Boolean {
        return false
    }

    override fun fractionalDigits(): Int {
        return 2 // Standardmäßig 2 Dezimalstellen für Währungen
    }

    override fun format(amount: Double): String {
        return "$amount ${currencyConfig!!.symbol}"
    }

    override fun currencyNamePlural(): String {
        return currencyConfig!!.name
    }

    override fun currencyNameSingular(): String {
        return currencyConfig!!.name
    }

    override fun hasAccount(uuid: String?): Boolean {
        return uuid?.let { UserServices().getPlayerAccount(it) }!!
    }

    override fun hasAccount(p0: String?, p1: String?): Boolean {
        TODO("Use the other method!")
    }

    override fun getBalance(uuid: String?): Double {
        return uuid?.let { getPlayer(it)?.let { UserServices().getCurrency(it, currency) } }!!
    }

    override fun getBalance(uuid: String?, currency: String): Double {
        return if (uuid != null) {
            getPlayer(uuid)?.let { UserServices().getCurrency(it, currency) } ?: 0.0
        } else {
            0.0
        }
    }

    override fun has(uuid: String?, amount: Double): Boolean {
        val playersBalance = uuid?.let { getPlayer(it)?.let { UserServices().getCurrency(it, currency) } } ?: 0.0
        return playersBalance >= amount
    }

    override fun has(uuid: String?, unused: String?, amount: Double): Boolean {
        return uuid?.let {
            getPlayer(it)?.let {
                UserServices().getCurrency(
                    it, currency
                )
            }
        }!! >= amount
    }

    override fun withdrawPlayer(uuid: String?, amount: Double): EconomyResponse {
        val result = uuid?.let { getPlayer(it)?.let { UserServices().removeCurrency(it, amount, currency) } }
        return if (result == ErrorCodes.SUCCESS) EconomyResponse(
            amount, 0.0, EconomyResponse.ResponseType.SUCCESS, "Success"
        )
        else EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Failed to withdraw")
    }

    override fun withdrawPlayer(uuid: String?, currency: String, amount: Double): EconomyResponse {
        val result = uuid?.let { it -> getPlayer(it)?.let { UserServices().removeCurrency(it, amount, currency) } }
        return if (result == ErrorCodes.SUCCESS) EconomyResponse(
            amount, 0.0, EconomyResponse.ResponseType.SUCCESS, "Success"
        )
        else EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Failed to withdraw")
    }

    override fun depositPlayer(uuid: String?, amount: Double): EconomyResponse {
        val result = uuid?.let { getPlayer(it)?.let { UserServices().giveCurrency(it, amount, currency) } }
        return if (result == ErrorCodes.SUCCESS) EconomyResponse(
            amount, 0.0, EconomyResponse.ResponseType.SUCCESS, "Success"
        )
        else EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Failed to deposit")
    }

    override fun depositPlayer(uuid: String?, currency: String, amount: Double): EconomyResponse {
        val result = uuid?.let { getPlayer(it)?.let { UserServices().giveCurrency(it, amount, currency) } }
        return if (result == ErrorCodes.SUCCESS) EconomyResponse(
            amount, 0.0, EconomyResponse.ResponseType.SUCCESS, "Success"
        )
        else EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Failed to deposit")
    }

    override fun createBank(p0: String?, p1: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Econix does not support banks")
    }

    override fun deleteBank(p0: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Econix does not support banks")
    }

    override fun bankBalance(p0: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Econix does not support banks")
    }

    override fun bankHas(p0: String?, p1: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Econix does not support banks")
    }

    override fun bankWithdraw(p0: String?, p1: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Econix does not support banks")
    }

    override fun bankDeposit(p0: String?, p1: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Econix does not support banks")
    }

    override fun isBankOwner(p0: String?, p1: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Econix does not support banks")
    }

    override fun isBankMember(p0: String?, p1: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Econix does not support banks")
    }

    override fun getBanks(): MutableList<String> {
        return mutableListOf()
    }

    override fun createPlayerAccount(p0: String?): Boolean {
        return false
    }

    override fun createPlayerAccount(p0: String?, p1: String?): Boolean {
        return false
    }
}

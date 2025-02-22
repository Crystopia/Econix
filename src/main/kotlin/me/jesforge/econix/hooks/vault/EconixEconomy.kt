package me.jesforge.econix.hooks.vault

import net.milkbowl.vault.economy.AbstractEconomy
import net.milkbowl.vault.economy.EconomyResponse

class EconixEconomy(currency: String) : AbstractEconomy() {

    private var currency = currency

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
        TODO("Not yet implemented")
    }

    override fun format(p0: Double): String {
        TODO("Not yet implemented")
    }

    override fun currencyNamePlural(): String {
        TODO("Not yet implemented")
    }

    override fun currencyNameSingular(): String {
        TODO("Not yet implemented")
    }

    override fun hasAccount(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasAccount(p0: String?, p1: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getBalance(p0: String?): Double {
        TODO("Not yet implemented")
    }

    override fun getBalance(p0: String?, p1: String?): Double {
        TODO("Not yet implemented")
    }

    override fun has(p0: String?, p1: Double): Boolean {
        TODO("Not yet implemented")
    }

    override fun has(p0: String?, p1: String?, p2: Double): Boolean {
        TODO("Not yet implemented")
    }

    override fun withdrawPlayer(p0: String?, p1: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun withdrawPlayer(p0: String?, p1: String?, p2: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun depositPlayer(p0: String?, p1: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun depositPlayer(p0: String?, p1: String?, p2: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun createBank(p0: String?, p1: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun deleteBank(p0: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankBalance(p0: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankHas(p0: String?, p1: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankWithdraw(p0: String?, p1: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankDeposit(p0: String?, p1: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankOwner(p0: String?, p1: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankMember(p0: String?, p1: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun getBanks(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun createPlayerAccount(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun createPlayerAccount(p0: String?, p1: String?): Boolean {
        TODO("Not yet implemented")
    }
}
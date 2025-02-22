package me.jesforge.econix.utils

import me.jesforge.econix.config.ConfigManager
import me.jesforge.econix.data.Currency
import me.jesforge.econix.data.CurrencyIcon
import org.bukkit.Material

class DefaultLoader {

    fun loadDefaultCurrencys() {
        ConfigManager.currency.currencys["crystals"] = Currency(
            id = "crystals",
            name = "Crystals",
            symbol = "💎",
            vautlHook = true,
            commandName = "crystals",
            currencyItem = CurrencyIcon(
                item = Material.DIAMOND, customModeData = 1
            ),
            startValue = 0.0,
            maxValue = -1.0,
            transferAllowed = false,
            minTransferValue = 0.1,
            exchangeAllowed = false
        )
        ConfigManager.settings.loadDefaults = false
        ConfigManager.save()
    }
}
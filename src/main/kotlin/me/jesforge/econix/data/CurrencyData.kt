package me.jesforge.econix.data

import kotlinx.serialization.Serializable
import org.bukkit.Material

@Serializable
data class CurrencyData(
    var currencys: MutableMap<String, Currency> = mutableMapOf(),
)

@Serializable
data class Currency(
    var id: String = "crystals",
    var name: String = "Crystals",
    var symbol: String = "Crystals",
    var vautlHook: Boolean = true,
    var commandName: String = "crystals",
    var currencyItem: CurrencyIcon,
    var startValue: Double = 0.0,
    var maxValue: Double = 0.0,
    var transferAllowed: Boolean = false,
    var minTransferValue: Double = 0.0,
    var exchangeAllowed: Boolean = false,
)

@Serializable
data class CurrencyIcon(
    var item: Material = Material.STONE,
    var customModeData: Int = 1,
)
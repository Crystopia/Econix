package me.jesforge.econix.hooks

import me.jesforge.econix.Econix
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import me.jesforge.econix.config.ConfigManager
import me.jesforge.econix.functions.CurrencyServices
import me.jesforge.econix.functions.UserServices
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player


class PlaceholderAPIHook : PlaceholderExpansion() {

    override fun getIdentifier(): String {
        return "econix"
    }

    override fun getAuthor(): String {
        return "jesforge"
    }

    override fun getVersion(): String {
        return Econix.instance.description.version ?: "1.0.0"
    }

    override fun onRequest(player: OfflinePlayer?, params: String): String {
        if (params.startsWith("balance_")) {
            val split = params.split("_")
            if (split.size >= 3) {
                val currency = split.last()
                val type = split.drop(1).dropLast(1).joinToString("_")
                val balance = player?.player?.let { UserServices().getCurrency(it, currency) } ?: 0.0
                val currencyData = ConfigManager.currency.currencys[currency]
                val symbol = currencyData?.symbol ?: currencyData?.name ?: ""
                return when (type) {
                    "plain" -> "$balance $symbol"
                    "raw" -> balance.toString()
                    "rounded" -> "${balance.toInt()}"
                    "short" -> formatShort(balance) + " $symbol"
                    "short_plain" -> formatShort(balance)
                    else -> "$balance $symbol"
                }
            }
        }

        if (params.startsWith("top_")) {
            val split = params.split("_")
            if (split.size >= 4) {
                val currency = split[2]
                val type = split.drop(1).dropLast(1).joinToString("_")
                val balance = player?.player?.let { UserServices().getCurrency(it, currency) } ?: 0.0
                val currencyData = ConfigManager.currency.currencys[currency]
                val symbol = currencyData?.symbol ?: currencyData?.name ?: ""

                return when (type.split("_")[0]) {
                    "player" -> {
                        val position = split.getOrNull(3)?.toIntOrNull() ?: 1
                        CurrencyServices().getTopPlayerName(currency, position)
                    }

                    "balance" -> {
                        val position = split.getOrNull(3)?.toIntOrNull() ?: 1
                        CurrencyServices().getTopBalance(currency, position).toString()
                    }

                    "balance_plain" -> {
                        val position = split.getOrNull(3)?.toIntOrNull() ?: 1
                        CurrencyServices().getTopBalancePlain(currency, position)
                    }

                    "balance_short" -> {
                        val position = split.getOrNull(3)?.toIntOrNull() ?: 1
                        CurrencyServices().getTopBalanceShort(currency, position)
                    }

                    "balance_short_plain" -> {
                        val position = split.getOrNull(3)?.toIntOrNull() ?: 1
                        CurrencyServices().getTopBalanceShortPlain(currency, position)
                    }

                    else -> "$balance $symbol"
                }
            }
        }


        return "null"
    }

    override fun onPlaceholderRequest(player: Player?, params: String): String {
        TODO()
    }


    private fun formatShort(value: Double): String {
        return when {
            value >= 1_000_000_000 -> "${(value / 1_000_000_000).format(1)}B"
            value >= 1_000_000 -> "${(value / 1_000_000).format(1)}M"
            value >= 1_000 -> "${(value / 1_000).format(1)}K"
            else -> value.toString()
        }
    }

    private fun Double.format(digits: Int) = "%.\${digits}f".format(this)

}
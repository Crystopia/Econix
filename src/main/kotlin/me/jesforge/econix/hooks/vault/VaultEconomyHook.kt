package me.jesforge.econix.hooks.vault

import me.jesforge.econix.Main
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.ServicesManager
import java.util.*


class VaultEconomyHook {

    private lateinit var economy: EconixEconomy

    // Add here the getter from the ConfigManager to get the first Vault Currency (currency)
    fun setup(plugin: Main, currency: String) {
        // Add here a function to set the currency (currency)
        economy = EconixEconomy(currency)

        val services: ServicesManager = plugin.getServer().getServicesManager()
        services.register(Economy::class.java, economy, plugin, ServicePriority.High)

        plugin.logger.info("Registered '" + currency + "' as Vault Economy!")


        println()
    }

}
package me.jesforge.econix.hooks.vault

import me.jesforge.econix.Econix
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.ServicesManager


class VaultEconomyHook {

    private lateinit var economy: EconixEconomy

    fun setup(plugin: Econix, currency: String) {
        economy = EconixEconomy(currency)

        val services: ServicesManager = plugin.getServer().getServicesManager()
        services.register(Economy::class.java, economy, plugin, ServicePriority.High)

        plugin.logger.info("Registered '" + currency + "' as Vault Economy!")
    }

}
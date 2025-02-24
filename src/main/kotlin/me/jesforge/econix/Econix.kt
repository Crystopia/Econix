package me.jesforge.econix

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import me.jesforge.econix.api.EconixAPI
import me.jesforge.econix.commands.CurrencyCommand
import me.jesforge.econix.commands.EconixCommand
import me.jesforge.econix.config.ConfigManager
import me.jesforge.econix.database.DatabaseManager
import me.jesforge.econix.events.JoinEvent
import me.jesforge.econix.hooks.PlaceholderAPIHook
import me.jesforge.econix.hooks.vault.VaultEconomyHook
import me.jesforge.econix.utils.DefaultLoader
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


class Econix : JavaPlugin() {

    companion object {
        lateinit var instance: Econix
        lateinit var api: EconixAPI
    }

    init {
        instance = this
        api = EconixAPI()
    }

    override fun onLoad() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true))

        DatabaseManager.init()
        DefaultLoader().loadDefaultCurrencys()

        logger.info("Loading Plugin...")

    }

    override fun onEnable() {
        CommandAPI.onEnable()

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            logger.info("Hooking into PlaceholderAPI")
            PlaceholderAPIHook().register();
            logger.info("Hooked into PlaceholderAPI")
            logger.info("-> Loaded PlaceholderAPI Hook")
        } else {
            logger.warning("PlaceholderAPI is not enabled!")
        }

        if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            logger.info("Hooking into Vault")
            // See VaultEconomyHook for the Currency...
            VaultEconomyHook().setup(this, ConfigManager.currency.currencys.filter {
                it.value.vautlHook
            }.firstNotNullOf {
                it.value.id
            })
        } else {
            logger.warning("Vault is not enabled!")
        }

        EconixCommand()
        CurrencyCommand()

        val settings = ConfigManager.settings

        server.pluginManager.registerEvents(JoinEvent(), this)

        logger.info("Plugin enabled!")
    }

    override fun onDisable() {
        CommandAPI.onDisable()

        logger.info("Plugin disabled!")
    }

}
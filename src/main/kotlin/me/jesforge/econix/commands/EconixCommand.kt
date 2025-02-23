package me.jesforge.econix.commands

import dev.jorel.commandapi.executors.CommandExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import me.jesforge.econix.Main
import me.jesforge.econix.config.ConfigManager
import me.jesforge.econix.database.DatabaseManager
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EconixCommand {
    val mm = MiniMessage.miniMessage()

    val commands = commandTree("econix", "econix") {
        executes(CommandExecutor { sender, args ->
            sender.sendMessage(
                mm.deserialize(
                    "<color:#78e2ff>Econix <color:#d4ecff>v.${Main.instance.description.version}</color></color>\n<gray>Loaded Placeholder API:</gray> <color:#d4ecff>${
                        Main.instance.server.pluginManager.getPlugin(
                            "PlaceholderAPI"
                        )!!.isEnabled
                    }</color>\n<gray>Loaded Vault:</gray> <color:#d4ecff>${
                        Main.instance.server.pluginManager.getPlugin(
                            "Vault"
                        )!!.isEnabled
                    }</color>\n  <gray>- Loaded Vault Currency:</gray> <color:#d4ecff>${
                        ConfigManager.currency.currencys.filter {
                            it.value.vautlHook

                        }.firstNotNullOf {
                            it.value.id

                        }
                    }</color>"))
        })
        literalArgument("reload") {
            executes(CommandExecutor { sender, args ->
                sender.sendMessage("<gray>Reloading Config</gray>")
                ConfigManager.reload()
                ConfigManager.save()
                sender.sendMessage("<gray>Reloaded Config Files</gray>")

                sender.sendMessage("<gray>Loading Database</gray>")
                DatabaseManager.init()
                sender.sendMessage("<gray>Reloaded Database</gray>")
            })
        }
    }
}
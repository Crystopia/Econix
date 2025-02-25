package me.jesforge.econix.commands

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.executors.CommandExecutor
import dev.jorel.commandapi.kotlindsl.*
import kotlinx.serialization.json.Json
import me.jesforge.econix.Econix
import me.jesforge.econix.config.ConfigManager
import me.jesforge.econix.database.DatabaseManager
import me.jesforge.econix.functions.UserServices
import me.jesforge.econix.utils.ErrorCodes
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

class CurrencyCommand {
    val mm = MiniMessage.miniMessage()

    val commands = ConfigManager.currency.currencys.forEach {

        commandTree(it.value.commandName, "econix") {
            withPermission("crystopia.commands.econix.${it.value.commandName}")
            literalArgument("give") {
                stringArgument("player") {
                    replaceSuggestions(
                        ArgumentSuggestions.strings {
                            Econix.instance.server.offlinePlayers.map { it.name }.toTypedArray()
                        })
                    doubleArgument("amount") {
                        executes(CommandExecutor { sender, args ->
                            val player = Econix.instance.server.getOfflinePlayer(args[0].toString())

                            val req = UserServices().giveCurrency(
                                player.uniqueId.toString(), args[1].toString().toDouble(), it.value.id
                            )

                            if (req == ErrorCodes.ERROR) {
                                sender.sendMessage(mm.deserialize("<color:#ff5d3d>There was a problem with the process</color>"))
                            } else if (req == ErrorCodes.SUCCESS) {
                                sender.sendMessage(
                                    mm.deserialize(
                                        "<color:#9effa1>You have added <gray>${
                                    args[1].toString().toDouble()
                                } ${
                                    it.value.symbol.ifEmpty { it.value.name }
                                }</gray> to <b>${(args[0] as Player).name}</b>.</color>"))
                            } else if (req == ErrorCodes.DATABASE_ERROR) {
                                sender.sendMessage(mm.deserialize("<color:#ff5d3d>An error has occurred with the database</color>"))
                            } else if (req == ErrorCodes.NO_CURRENCY) {
                                sender.sendMessage(mm.deserialize("The currency <b>${it.value.id}</b> was not found!"))
                            } else if (req == ErrorCodes.NO_USER) {
                                sender.sendMessage(mm.deserialize("The player <b>${(args[0] as Player).name}</b> was not found!"))
                            }
                        })
                    }
                }
            }
            literalArgument("remove") {
                stringArgument("player") {
                    replaceSuggestions(
                        ArgumentSuggestions.strings {
                            Econix.instance.server.offlinePlayers.map { it.name }.toTypedArray()
                        })
                    doubleArgument("amount") {
                        executes(CommandExecutor { sender, args ->
                            val player = Econix.instance.server.getOfflinePlayer(args[0].toString())

                            val req = UserServices().removeCurrency(
                                player.uniqueId.toString(), args[1].toString().toDouble(), it.value.id
                            )

                            if (req == ErrorCodes.ERROR) {
                                sender.sendMessage(mm.deserialize("<color:#ff5d3d>There was a problem with the process</color>"))
                            } else if (req == ErrorCodes.SUCCESS) {
                                sender.sendMessage(
                                    mm.deserialize(
                                        "<color:#9effa1>You have removed <gray>${
                                    args[1].toString().toDouble()
                                } ${
                                    it.value.symbol.ifEmpty { it.value.name }
                                }</gray> from <b>${(args[0] as Player).name}'s account</b>. <gray>(now ${
                                    UserServices().getCurrency(
                                        (args[0] as Player).uniqueId.toString(), it.value.id
                                    )
                                } ${it.value.symbol})</color>"))
                            } else if (req == ErrorCodes.DATABASE_ERROR) {
                                sender.sendMessage(mm.deserialize("<color:#ff5d3d>An error has occurred with the database</color>"))
                            } else if (req == ErrorCodes.NO_CURRENCY) {
                                sender.sendMessage(mm.deserialize("The currency <b>${it.value.id}</b> was not found!"))
                            } else if (req == ErrorCodes.NO_USER) {
                                sender.sendMessage(mm.deserialize("The player <b>${(args[0] as Player).name}</b> was not found!"))
                            }
                        })
                    }
                }
            }
            literalArgument("set") {
                stringArgument("player") {
                    replaceSuggestions(
                        ArgumentSuggestions.strings {
                            Econix.instance.server.offlinePlayers.map { it.name }.toTypedArray()
                        })
                    doubleArgument("amount") {
                        executes(CommandExecutor { sender, args ->
                            val player = Econix.instance.server.getOfflinePlayer(args[0].toString())

                            val req = UserServices().setCurrency(
                                player.uniqueId.toString(), args[1].toString().toDouble(), it.value.id
                            )

                            if (req == ErrorCodes.ERROR) {
                                sender.sendMessage(mm.deserialize("<color:#ff5d3d>There was a problem with the process</color>"))
                            } else if (req == ErrorCodes.SUCCESS) {
                                sender.sendMessage(
                                    mm.deserialize(
                                    "<color:#9effa1>You have set <gray>${
                                    args[1].toString().toDouble()
                                } ${
                                    it.value.symbol.ifEmpty { it.value.name }
                                }</gray> to <b>${(args[0] as Player).name}'s</b> account.</color>"))
                            } else if (req == ErrorCodes.DATABASE_ERROR) {
                                sender.sendMessage(mm.deserialize("<color:#ff5d3d>An error has occurred with the database</color>"))
                            } else if (req == ErrorCodes.NO_CURRENCY) {
                                sender.sendMessage(mm.deserialize("The currency <b>${it.value.id}</b> was not found!"))
                            } else if (req == ErrorCodes.NO_USER) {
                                sender.sendMessage(mm.deserialize("The player <b>${(args[0] as Player).name}</b> was not found!"))
                            }
                        })
                    }
                }
            }
            literalArgument("giveall") {
                doubleArgument("amount") {
                    executes(CommandExecutor { sender, args ->

                        Econix.instance.server.onlinePlayers.forEach { player ->
                            val req = UserServices().giveCurrency(
                                player.uniqueId.toString(), args[1].toString().toDouble(), it.value.id
                            )

                            if (req == ErrorCodes.ERROR) {
                                sender.sendMessage(mm.deserialize("<color:#ff5d3d>There was a problem with the process</color>"))
                            } else if (req == ErrorCodes.SUCCESS) {
                                sender.sendMessage(
                                    mm.deserialize(
                                        "<color:#9effa1>You have added <gray>${
                                    args[1].toString().toDouble()
                                } ${
                                    it.value.symbol.ifEmpty { it.value.name }
                                }</gray> to <b>${(args[0] as Player).name}</b>.</color>"))
                            } else if (req == ErrorCodes.DATABASE_ERROR) {
                                sender.sendMessage(mm.deserialize("<color:#ff5d3d>An error has occurred with the database</color>"))
                            } else if (req == ErrorCodes.NO_CURRENCY) {
                                sender.sendMessage(mm.deserialize("The currency <b>${it.value.id}</b> was not found!"))
                            } else if (req == ErrorCodes.NO_USER) {
                                sender.sendMessage(mm.deserialize("The player <b>${(args[0] as Player).name}</b> was not found!"))
                            }
                        }

                        sender.sendMessage(mm.deserialize("<gray>Added all online players the correct amount!</gray>"))

                    })
                }
            }
            literalArgument("item") {
                playerArgument("player") {
                    doubleArgument("amount") {
                        executes(CommandExecutor { sender, args ->

                            val req = UserServices().removeCurrency(
                                (args[0] as Player).uniqueId.toString(), args[1].toString().toDouble(), it.value.id
                            )

                            if (req == ErrorCodes.ERROR) {
                                sender.sendMessage(mm.deserialize("<color:#ff5d3d>There was a problem with the process</color>"))
                            } else if (req == ErrorCodes.TOO_LOW) {
                                mm.deserialize("<red>This user has not enough ${it.value.symbol}!</red>")
                                return@CommandExecutor
                            } else if (req == ErrorCodes.SUCCESS) {
                                sender.sendMessage(
                                    mm.deserialize(
                                        "<color:#9effa1>You have removed <gray>${
                                    args[1].toString().toDouble()
                                } ${
                                    it.value.symbol.ifEmpty { it.value.name }
                                }</gray> from <b>${(args[0] as Player).name}'s account</b>. <gray>(now ${
                                    UserServices().getCurrency(
                                        (args[0] as Player).toString(), it.value.id
                                    )
                                } ${it.value.symbol})</color>"))

                                UserServices().currencyToItem(
                                    currency = it.value.id,
                                    amount = args[1].toString().toDouble(),
                                    player = args[0] as Player
                                )

                                sender.sendMessage(mm.deserialize("<gray>Added item to the player successfully!</gray>"))
                            } else if (req == ErrorCodes.DATABASE_ERROR) {
                                sender.sendMessage(mm.deserialize("<color:#ff5d3d>An error has occurred with the database</color>"))
                            } else if (req == ErrorCodes.NO_CURRENCY) {
                                sender.sendMessage(mm.deserialize("The currency <b>${it.value.id}</b> was not found!"))
                            } else if (req == ErrorCodes.NO_USER) {
                                sender.sendMessage(mm.deserialize("The player <b>${(args[0] as Player).name}</b> was not found!"))
                            }
                        })
                    }
                }
            }
            literalArgument("balance") {
                stringArgument("player") {
                    replaceSuggestions(
                        ArgumentSuggestions.strings {
                            Econix.instance.server.offlinePlayers.map { it.name }.toTypedArray()
                        })
                    executes(CommandExecutor { sender, args ->
                        val player = Econix.instance.server.getOfflinePlayer(args[0].toString())

                        val req = UserServices().getCurrency(
                            player.uniqueId.toString(), it.value.id
                        )

                        sender.sendMessage(
                            mm.deserialize(
                                "<color:#9effa1>The Balance from <b>${(args[0] as Player).name}'s</b> is <gray>${req} ${it.value.symbol}</gray> to  account.</color>"
                            )
                        )
                    })
                }
            }
        }
    }
}
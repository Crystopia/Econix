package me.jesforge.econix.commands

import dev.jorel.commandapi.executors.CommandExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.doubleArgument
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.playerArgument
import me.jesforge.econix.config.ConfigManager
import me.jesforge.econix.functions.UserServices
import me.jesforge.econix.utils.ErrorCodes
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

class CurrencyCommand {
    val mm = MiniMessage.miniMessage()

    val commands = ConfigManager.currency.currencys.forEach {

        commandTree(it.value.commandName, "econix") {
            literalArgument("give") {
                playerArgument("player") {
                    doubleArgument("amount") {
                        executes(CommandExecutor { sender, args ->

                            val req = UserServices().giveCurrency(
                                args[0] as Player, args[1].toString().toDouble(), it.value.id
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
                playerArgument("player") {
                    doubleArgument("amount") {
                        executes(CommandExecutor { sender, args ->

                            val req = UserServices().removeCurrency(
                                args[0] as Player, args[1].toString().toDouble(), it.value.id
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
                                                args[0] as Player, it.value.id
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
                playerArgument("player") {
                    doubleArgument("amount") {
                        executes(CommandExecutor { sender, args ->

                            val req = UserServices().setCurrency(
                                args[0] as Player, args[1].toString().toDouble(), it.value.id
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

            }
            literalArgument("item") {

            }
            literalArgument("balance") {
                playerArgument("player") {
                    executes(CommandExecutor { sender, args ->
                        val req = UserServices().getCurrency(
                            args[0] as Player, it.value.id
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
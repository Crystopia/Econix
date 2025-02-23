package me.jesforge.econix.commands

import dev.jorel.commandapi.executors.CommandExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import me.jesforge.econix.config.ConfigManager

class CurrencyCommand {

    val commands = ConfigManager.currency.currencys.forEach {

        commandTree(it.value.commandName, "econix") {
            literalArgument("give") {
                executes(CommandExecutor { sender, args ->

                })
            }
            literalArgument("remove") {

            }
            literalArgument("set") {

            }
            literalArgument("giveall") {

            }
            literalArgument("balance") {

            }
            literalArgument("send") {

            }
        }
    }


}
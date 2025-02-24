package me.jesforge.econix.api

import me.jesforge.econix.Main
import me.jesforge.econix.functions.UserServices
import org.bukkit.entity.Player

class EconixAPI {
    companion object {

        var econixPlugin = Main.instance

        fun getBalance(player: Player, currency: String): Double {
            return UserServices().getCurrency(player, currency)
        }
    }
}
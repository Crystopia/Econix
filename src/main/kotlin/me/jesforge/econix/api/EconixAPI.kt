package me.jesforge.econix.api

import me.jesforge.econix.functions.UserServices
import org.bukkit.entity.Player

class EconixAPI {

    fun getBalance(player: Player, currency: String): Double {
        return UserServices().getCurrency(player, currency)
    }

}
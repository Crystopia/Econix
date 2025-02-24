package me.jesforge.econix.api

import org.bukkit.entity.Player

interface EconixAPI {

    fun getBalance(player: Player, currency: String): Double
}
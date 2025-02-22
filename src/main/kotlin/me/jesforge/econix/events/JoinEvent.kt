﻿package me.jesforge.econix.events

import me.jesforge.econix.functions.CurrencyUpdater
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinEvent : Listener {

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        val player = event.player
        CurrencyUpdater().updatePlayerCurrencies(player)
    }
}
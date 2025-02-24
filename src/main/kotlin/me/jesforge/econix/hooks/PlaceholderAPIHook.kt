package me.jesforge.econix.hooks

import me.jesforge.econix.Econix
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player


class PlaceholderAPIHook : PlaceholderExpansion() {

    override fun getIdentifier(): String {
        return "econix"
    }

    override fun getAuthor(): String {
        return "jesforge"
    }

    override fun getVersion(): String {
        return Econix.instance.description.version ?: "1.0.0"
    }

    override fun onRequest(player: OfflinePlayer?, params: String): String {
        TODO()
    }

    override fun onPlaceholderRequest(player: Player?, params: String): String {
        TODO()
    }

}
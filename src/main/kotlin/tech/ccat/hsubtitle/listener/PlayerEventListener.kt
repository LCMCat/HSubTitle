package tech.ccat.hsubtitle.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import tech.ccat.hsubtitle.HSubTitlePlugin

class PlayerEventListener(private val plugin: HSubTitlePlugin) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        plugin.playerTaskHandler.startForPlayer(event.player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        plugin.playerTaskHandler.stopForPlayer(event.player)
    }
}
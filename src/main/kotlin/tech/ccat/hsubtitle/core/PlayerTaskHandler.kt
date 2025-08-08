package tech.ccat.hsubtitle.core

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import tech.ccat.hsubtitle.HSubTitlePlugin

/**
 * 管理每个玩家的更新任务
 */
internal class PlayerTaskHandler(private val plugin: HSubTitlePlugin) {
    // 存储玩家及其对应任务
    private val playerTasks = mutableMapOf<Player, BukkitTask>()

    /**
     * 为玩家启动更新任务
     */
    fun startForPlayer(player: Player) {
        // 如果已有任务则先取消
        stopForPlayer(player)

        val task = object : BukkitRunnable() {
            override fun run() {
                try {
                    updateActionBar(player)
                } catch (e: Exception) {
                    plugin.logger.warning("更新玩家${player.name}的actionbar时出错: ${e.message}")
                }
            }
        }

        // 每10 ticks (0.5秒) 运行一次
        playerTasks[player] = task.runTaskTimer(plugin, 0, 10)
    }

    /**
     * 停止玩家的更新任务
     */
    fun stopForPlayer(player: Player) {
        playerTasks.remove(player)?.cancel()
    }

    /**
     * 立即更新玩家的动作条
     */
    fun updateActionBar(player: Player) {
        val text = plugin.textProviderManager.composeText(player)
        if (text.isNotEmpty()) {
            player.sendActionBar(Component.text(text))
        }
    }

    /**
     * 停止所有玩家的更新任务
     */
    fun stopAll() {
        playerTasks.values.forEach { it.cancel() }
        playerTasks.clear()
    }
}
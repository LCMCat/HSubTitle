package tech.ccat.hsubtitle

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import tech.ccat.hsubtitle.api.HSubTitleAPI
import tech.ccat.hsubtitle.api.TextProvider
import tech.ccat.hsubtitle.core.PlayerTaskHandler
import tech.ccat.hsubtitle.core.TextProviderManager
import tech.ccat.hsubtitle.listener.PlayerEventListener

class HSubTitlePlugin : JavaPlugin(), HSubTitleAPI {
    // 核心组件
    internal lateinit var textProviderManager: TextProviderManager
    internal lateinit var playerTaskHandler: PlayerTaskHandler


    override fun onEnable() {
        // 初始化组件
        textProviderManager = TextProviderManager()
        playerTaskHandler = PlayerTaskHandler(this)

        // 注册事件监听器
        server.pluginManager.registerEvents(PlayerEventListener(this), this)

        // 将自身注册为服务提供者 (Bukkit方式)
        server.servicesManager.register(
            HSubTitleAPI::class.java,
            this,
            this,
            ServicePriority.Normal
        )

        // 为所有已在线玩家启动任务 (reload情况)
        Bukkit.getOnlinePlayers().forEach {
            playerTaskHandler.startForPlayer(it)
        }

        logger.info("HSubTitle API 已启用")
    }

    override fun onDisable() {
        // 停止所有任务
        playerTaskHandler.stopAll()

        // 取消服务注册
        server.servicesManager.unregister(this)

        logger.info("${description.name} 已禁用")
    }

    // ===== API实现 =====
    override fun registerProvider(provider: TextProvider) {
        textProviderManager.registerProvider(provider)
        forceUpdateAll()  // 立即更新所有玩家
    }

    override fun unregisterProvider(provider: TextProvider) {
        textProviderManager.unregisterProvider(provider)
        forceUpdateAll()  // 立即更新所有玩家
    }

    override fun forceUpdate(player: Player) {
        playerTaskHandler.updateActionBar(player)
    }

    override fun forceUpdateAll() {
        Bukkit.getOnlinePlayers().forEach(playerTaskHandler::updateActionBar)
    }
}
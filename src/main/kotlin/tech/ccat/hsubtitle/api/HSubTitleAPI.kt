package tech.ccat.hsubtitle.api

import org.bukkit.entity.Player

/**
 * 提供给其他插件的核心API接口
 */
interface HSubTitleAPI {
    /**
     * 注册文本提供器
     */
    fun registerProvider(provider: TextProvider)

    /**
     * 取消注册文本提供器
     */
    fun unregisterProvider(provider: TextProvider)

    /**
     * 强制刷新指定玩家的动作条
     */
    fun forceUpdate(player: Player)

    /**
     * 强制刷新所有在线玩家的动作条
     */
    fun forceUpdateAll()
}
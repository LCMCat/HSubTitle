package tech.ccat.hsubtitle.api

import org.bukkit.entity.Player
import tech.ccat.hsubtitle.model.Info

/**
 * 其他插件需要实现的文本提供器接口
 */
interface TextProvider {
    /**
     * 为指定玩家生成要显示的动作条文本
     * @param player 目标玩家
     * @return Info对象包含文本和优先级
     */
    fun provideText(player: Player): Info
}
package tech.ccat.hsubtitle.core

import org.bukkit.entity.Player
import tech.ccat.hsubtitle.api.TextProvider
import tech.ccat.hsubtitle.model.Info
import java.util.concurrent.CopyOnWriteArrayList

/**
 * 管理所有文本提供器并组合文本
 */
internal class TextProviderManager {
    // 线程安全的提供器列表
    private val providers = CopyOnWriteArrayList<TextProvider>()

    /**
     * 注册提供器
     */
    fun registerProvider(provider: TextProvider) {
        providers.add(provider)
    }

    /**
     * 取消注册提供器
     */
    fun unregisterProvider(provider: TextProvider) {
        providers.remove(provider)
    }

    /**
     * 组合所有提供器的文本
     * @return 按优先级排序后拼接的字符串
     */
    fun composeText(player: Player): String {
        return providers.mapNotNull {
            try {
                it.provideText(player)
            } catch (e: Exception) {
                // 防止单个提供器异常影响整体
                null
            }
        }
            .sortedByDescending { it.priority }
            .joinToString("   ") { it.text }
    }
}
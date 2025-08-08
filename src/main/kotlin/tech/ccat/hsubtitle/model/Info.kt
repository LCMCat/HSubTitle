package tech.ccat.hsubtitle.model

/**
 * 表示要在动作条上显示的文本信息
 * @property text 显示的文本内容
 * @property priority 优先级（数字越大越优先）
 */
data class Info(
    val text: String,
    val priority: Int
)
package xyz.jeelzzz.aerocore

data class ConfigData (
    val prefix: String? = "<#d4e7ff>&lAero<#2a84ff>&lCore &r&8» ",
    val features: Features
)

data class Features (
    val gamemode: Gamemode,
    val kittycannon: KittyCannon
) {
    data class Gamemode (
        val enabled: Boolean,
        val messages: Messages
    ) {
        data class Messages (
            val set_self: String,
            val set_other: String
        )
    }
    data class KittyCannon (
        val enabled: Boolean,
        val random_type: Boolean
    )
}
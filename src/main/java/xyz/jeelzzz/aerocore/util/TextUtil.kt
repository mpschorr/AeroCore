package xyz.jeelzzz.aerocore.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import java.util.function.Function
import java.util.stream.Collectors

object TextUtil {
    // minimessage
    private val mm = MiniMessage.miniMessage()
    fun deserialize(text: String): Component {
        return mm.deserialize(convertAmpToMM(text))
    }

    fun serialize(component: Component): String {
        return mm.serialize(component)
    }

    fun convertAmpToMM(text: String): String {
        return text
            .replace("&0", "<black>")
            .replace("&1", "<dark_blue>")
            .replace("&2", "<dark_green>")
            .replace("&3", "<dark_aqua>")
            .replace("&4", "<dark_red>")
            .replace("&5", "<dark_purple>")
            .replace("&6", "<gold>")
            .replace("&7", "<gray>")
            .replace("&8", "<dark_gray>")
            .replace("&9", "<blue>")
            .replace("&a", "<green>")
            .replace("&b", "<aqua>")
            .replace("&c", "<red>")
            .replace("&d", "<light_purple>")
            .replace("&e", "<yellow>")
            .replace("&f", "<white>")
            .replace("&k", "<obf>")
            .replace("&l", "<b>")
            .replace("&m", "<st>")
            .replace("&n", "<u>")
            .replace("&o", "<i>")
            .replace("&r", "<reset>")
    }

    fun log(component: Component?) {
        Bukkit.getConsoleSender().sendMessage(component!!)
    }

    // deserialize (string -> component)
    fun deserializeLegacy(text: String?): Component {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text!!)
    }

    fun deserializeLegacy(texts: List<String?>): List<Component> {
        return texts.stream().map(Function<String?, Component> { obj: String? -> deserializeLegacy(obj) })
            .collect(Collectors.toList())
    }

    // deserialize (component -> string)
    fun serializeLegacy(component: Component?): String {
        return LegacyComponentSerializer.legacyAmpersand().serialize(component!!)
    }

    fun serializeLegacy(components: List<Component?>): List<String> {
        return components.stream().map(Function<Component?, String> { obj: Component? -> serializeLegacy(obj) })
            .collect(Collectors.toList())
    }
}
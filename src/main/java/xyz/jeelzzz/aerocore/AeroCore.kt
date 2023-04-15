package xyz.jeelzzz.aerocore

import cc.ekblad.toml.decode
import cc.ekblad.toml.model.TomlException
import cc.ekblad.toml.tomlMapper
import dev.triumphteam.cmd.bukkit.BukkitCommandManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import xyz.jeelzzz.aerocore.commands.gamemode.*
import xyz.jeelzzz.aerocore.commands.misc.KittyCannonCommand
import xyz.jeelzzz.aerocore.util.TextUtil
import java.io.File

class AeroCore : JavaPlugin() {
    private val defaultPrefix = "<#d4e7ff>&lAero<#2a84ff>&lCore &r&8» "
    lateinit var config: ConfigData private set

    override fun onEnable() {
        // Plugin startup logic
        val configSuccess = loadConfig()
        if (!configSuccess) return;
        loadCommands()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    val commandManager: BukkitCommandManager<CommandSender> = BukkitCommandManager.create(this);
    private fun loadCommands() {
        if (config.features.gamemode.enabled) {
            commandManager.registerCommand(GamemodeCommand(this))
            commandManager.registerCommand(GMSCommand(this))
            commandManager.registerCommand(GMCCommand(this))
            commandManager.registerCommand(GMACommand(this))
            commandManager.registerCommand(GMSPCommand(this))
        }
        if (config.features.kittycannon.enabled) {
            commandManager.registerCommand(KittyCannonCommand(this))
        }
    }

    private fun loadConfig(): Boolean {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
        saveResource("config.toml", false)
        val configFile = File(dataFolder, "config.toml")

        val mapper = tomlMapper { }

        var errors = 0
        try {
            config = mapper.decode<ConfigData>(configFile.toPath())
        } catch (e: TomlException.DecodingError.MissingNonNullableValue) {
            Bukkit.getConsoleSender().sendMessage(formatted("&cValue '&4${e.parameter.name}&c' not found"))
            errors++
        }

        if (errors < 1) {
            Bukkit.getConsoleSender().sendMessage(formatted("&aReloaded config with no errors."))
        } else {
            Bukkit.getConsoleSender().sendMessage(formatted("&cReloaded config with &4$errors &cerrors. &4"))
            return false
        }
        return true
    }

    fun formatted(message: String, prefixed: Boolean = true): Component {
        if (prefixed) {
            val prefix = if (!this::config.isInitialized) defaultPrefix else config.prefix
            return TextUtil.deserialize(prefix + message);
        }
        return TextUtil.deserialize(message);
    }
}
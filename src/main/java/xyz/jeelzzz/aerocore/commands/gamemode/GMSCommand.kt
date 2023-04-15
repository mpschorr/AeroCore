package xyz.jeelzzz.aerocore.commands.gamemode

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.ArgName
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import dev.triumphteam.cmd.core.annotation.Optional
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.jeelzzz.aerocore.AeroCore
import xyz.jeelzzz.aerocore.util.TextUtil

@Command("gms")
class GMSCommand(plugin: AeroCore) : BaseCommand() {
    val plugin: AeroCore
    init {
        this.plugin = plugin
    }

    @Default
    fun gamemodeCommand(sender: CommandSender, @Optional @ArgName("target") cmdTarget: Player?) {
        if (cmdTarget == null && sender !is Player) return
        val target: Player = cmdTarget ?: sender as Player

        if (target != sender) {
            sender.sendMessage(plugin.formatted(plugin.config.features.gamemode.messages.set_other.format(target.name, "survival")))
        }
        target.sendMessage(plugin.formatted(plugin.config.features.gamemode.messages.set_self.format("survival")))

        target.gameMode = GameMode.SURVIVAL;
    }
}
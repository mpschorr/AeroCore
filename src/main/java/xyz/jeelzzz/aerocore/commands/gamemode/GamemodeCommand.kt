package xyz.jeelzzz.aerocore.commands.gamemode

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.ArgName
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import dev.triumphteam.cmd.core.annotation.Optional
import dev.triumphteam.cmd.core.annotation.Suggestion
import dev.triumphteam.cmd.core.suggestion.SuggestionKey
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.jeelzzz.aerocore.AeroCore
import xyz.jeelzzz.aerocore.util.TextUtil
import javax.annotation.Nullable

@Command("gamemode")
@Permission("aerocore.command.gamemode")
class GamemodeCommand(plugin: AeroCore) : BaseCommand() {
    val plugin: AeroCore
    init {
        this.plugin = plugin
        plugin.commandManager.registerSuggestion(SuggestionKey.of("legacyGamemode")) { sender, context ->
            listOf(
                "survival", "creative", "adventure", "spectator",
                "s", "c", "a", "sp",
                "0", "1", "2", "3"
            )
        }
    }

    @Default
    fun gamemodeCommand(sender: CommandSender,
                        @Suggestion("legacyGamemode") gamemode: String,
                        @Optional @ArgName("target") cmdTarget: Player?
    ) {
        if (cmdTarget == null && sender !is Player) return
        val target: Player = cmdTarget ?: sender as Player

        val gameMode: GameMode
        val gameModeName: String
        if (listOf("survival", "s", "0").contains(gamemode.lowercase())) {
            gameMode = GameMode.SURVIVAL
            gameModeName = "survival"
        } else if (listOf("creative", "c", "1").contains(gamemode.lowercase())) {
            gameMode = GameMode.CREATIVE
            gameModeName = "creative"
        } else if (listOf("adventure", "a", "2").contains(gamemode.lowercase())) {
            gameMode = GameMode.ADVENTURE
            gameModeName = "adventure"
        } else if (listOf("spectator", "sp", "3").contains(gamemode.lowercase())) {
            gameMode = GameMode.SPECTATOR
            gameModeName = "spectator"
        } else return

        target.gameMode = gameMode

        if (target != sender) {
            sender.sendMessage(plugin.formatted(plugin.config.features.gamemode.messages.set_other.format(target.name, gameModeName)))
        }
        target.sendMessage(plugin.formatted(plugin.config.features.gamemode.messages.set_self.format(gameModeName)))
    }
}
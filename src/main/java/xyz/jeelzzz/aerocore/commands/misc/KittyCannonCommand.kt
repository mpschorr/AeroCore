package xyz.jeelzzz.aerocore.commands.misc

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Cat
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import xyz.jeelzzz.aerocore.AeroCore
import java.util.Random

@Command("kittycannon", alias = ["kc"])
@Permission("aerocore.command.kittycannon")
class KittyCannonCommand(plugin: AeroCore) : BaseCommand() {
    val plugin: AeroCore
    val random = Random()
    init {
        this.plugin = plugin
    }

    private fun spawnEntity(player: Player): Cat {
        val cat = player.world.spawnEntity(player.eyeLocation, EntityType.CAT) as Cat

        if (plugin.config.features.kittycannon.random_type) {
            val i = random.nextInt(Cat.Type.values().size)
            val catType = Cat.Type.values()[i]
            cat.catType = catType
        }

        cat.isTamed = true
        cat.setBaby()
        cat.velocity = player.eyeLocation.direction.multiply(2)
        return cat
    }

    @Default
    fun kittyCannonCommand(sender: CommandSender) {
        if (sender !is Player) return
        val player: Player = sender

        val cat = spawnEntity(player)
        val task = object : BukkitRunnable() {
            override fun run() {
                val loc = cat.location
                cat.remove()
                loc.world.createExplosion(loc, 0f)
            }
        }
        task.runTaskLater(plugin, 20L)
    }
}
package de.kolpa.mc.villagerbucketing.storage.villager

import com.destroystokyo.paper.entity.villager.Reputation
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.entity.Villager
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe
import org.bukkit.potion.PotionEffect
import java.util.*

data class SerializedVillager(
    val profession: Villager.Profession,
    val type: Villager.Type,
    val level: Int,
    val experience: Int,
    val restocksToday: Int,
    val reputations: Map<UUID, Reputation>,
    val recipes: List<MerchantRecipe>,
    val items: List<ItemStack?>,
    val potionEffects: Collection<PotionEffect>,
    val health: Double,
    val name: Component?,
    val age: Int,
) {
    companion object {
        fun Villager.serialize() = SerializedVillager(
            profession = profession,
            type = villagerType,
            level = villagerLevel,
            experience = villagerExperience,
            restocksToday = restocksToday,
            reputations = reputations,
            recipes = recipes,
            items = inventory.contents.toList(),
            potionEffects = activePotionEffects,
            health = health,
            name = customName(),
            age = age
        )

        fun SerializedVillager.spawn(location: Location) {
            location.world.spawn(location, Villager::class.java) { villager ->
                villager.villagerLevel = level
                villager.villagerExperience = experience
                villager.profession = profession
                villager.villagerType = type
                villager.restocksToday = restocksToday
                villager.reputations = reputations
                villager.recipes = recipes
                villager.inventory.contents = items.toTypedArray()
                villager.addPotionEffects(potionEffects)
                villager.health = health
                villager.customName(name)
                villager.age = age
            }
        }
    }
}

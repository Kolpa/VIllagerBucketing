package de.kolpa.mc.villagerbucketing.storage.villager

import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.entity.Villager
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*


data class SerializedVillager(
    val profession: Villager.Profession,
    val type: Villager.Type,
    val level: Int,
    val experience: Int,
    val restocksToday: Int,
    val reputations: Map<UUID, SerializableReputation>,
    val recipes: List<SerializableMerchantRecipe>,
    val items: List<ItemStack?>,
    val potionEffects: List<PotionEffect>,
    val health: Double,
    val name: Component?,
    val age: Int,
) : java.io.Serializable {
    companion object {
        fun Villager.serialize() = SerializedVillager(
            profession = profession,
            type = villagerType,
            level = villagerLevel,
            experience = villagerExperience,
            restocksToday = restocksToday,
            reputations = reputations.mapValues { SerializableReputation(it.value) },
            recipes = recipes.map { SerializableMerchantRecipe(it) },
            items = inventory.contents.toList(),
            potionEffects = activePotionEffects.toList(),
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
                villager.reputations = reputations.mapValues { it.value.reputation }
                villager.recipes = recipes.map { it.merchantRecipe }
                villager.inventory.contents = items.toTypedArray()
                villager.addPotionEffects(potionEffects)
                villager.health = health
                villager.customName(name)
                villager.age = age
            }
        }

        fun SerializedVillager.toByteArray(): ByteArray {
            val bout = ByteArrayOutputStream()
            val boos = BukkitObjectOutputStream(bout)

            boos.writeObject(this)

            boos.flush()

            return bout.toByteArray()
        }

        fun ByteArray.toSerializedVillager(): SerializedVillager {
            val bin = ByteArrayInputStream(this)
            val boos = BukkitObjectInputStream(bin)

            val stack = boos.readObject() as SerializedVillager

            boos.close()

            return stack
        }
    }
}

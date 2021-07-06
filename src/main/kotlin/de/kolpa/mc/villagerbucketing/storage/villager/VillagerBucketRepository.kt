package de.kolpa.mc.villagerbucketing.storage.villager

import de.kolpa.mc.villagerbucketing.storage.NamespaceKeyFactory
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Villager
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.*

class VillagerBucketRepository(
    namespaceKeyFactory: NamespaceKeyFactory,
    private val storage: VillagerStorage,
) {
    private val namespaceKey = namespaceKeyFactory.getNamespaceKey(VILLAGER_UUID_NAMESPACE_KEY)

    fun createBucketForVillager(villager: Villager): VillagerBucket {
        val newBucket = ItemStack(Material.BUCKET)
        val newUUID = UUID.randomUUID().toString()

        newBucket.editMeta {
            val bucketLore = listOf(
                Component.text("This bucket contains a Villager"),
                Component.text()
                    .append {
                        Component.text(villager.villagerType.name)
                            .color(TextColor.color(1f, 0f, 0f))
                    }
                    .append {
                        Component.text(" - ")
                    }
                    .append {
                        Component.text(villager.profession.name)
                            .color(TextColor.color(0f, 0f, 1f))
                    }.build()
            )

            it.lore(bucketLore)

            it.displayName(Component.text("Villager Bucket"))

            it.persistentDataContainer
                .set(namespaceKey, PersistentDataType.STRING, newUUID)
        }

        return VillagerBucket(
            itemStack = newBucket,
            uuid = newUUID
        )
    }

    data class VillagerBucket(
        val itemStack: ItemStack,
        val uuid: String,
    )

    fun getSerializedVillagerForBucket(itemStack: ItemStack): SerializedVillager? =
        getUUIDForBucket(itemStack)?.let { storage.loadVillager(it) }

    private fun getUUIDForBucket(itemStack: ItemStack): String? =
        itemStack
            .itemMeta
            .persistentDataContainer
            .get(namespaceKey, PersistentDataType.STRING)


    companion object {
        const val VILLAGER_UUID_NAMESPACE_KEY = "bucket-uuid"
    }
}
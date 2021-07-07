package de.kolpa.mc.villagerbucketing.storage.villager

import de.kolpa.mc.villagerbucketing.storage.NamespaceKeyFactory
import de.kolpa.mc.villagerbucketing.storage.villager.SerializedVillager.Companion.serialize
import de.kolpa.mc.villagerbucketing.storage.villager.SerializedVillager.Companion.toByteArray
import de.kolpa.mc.villagerbucketing.storage.villager.SerializedVillager.Companion.toSerializedVillager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Villager
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class VillagerBucketRepository(
    namespaceKeyFactory: NamespaceKeyFactory,
) {
    private val namespaceKey = namespaceKeyFactory.getNamespaceKey(VILLAGER_UUID_NAMESPACE_KEY)

    fun createBucketForVillager(villager: Villager): ItemStack {
        val newBucket = ItemStack(Material.BUCKET)
        val serializedVillager = villager.serialize()

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
                .set(namespaceKey, PersistentDataType.BYTE_ARRAY, serializedVillager.toByteArray())
        }

        return newBucket
    }

    fun getSerializedVillagerForBucket(itemStack: ItemStack): SerializedVillager? =
        itemStack
            .itemMeta
            .persistentDataContainer
            .get(namespaceKey, PersistentDataType.BYTE_ARRAY)
            ?.toSerializedVillager()


    companion object {
        const val VILLAGER_UUID_NAMESPACE_KEY = "bucket-uuid"
    }
}
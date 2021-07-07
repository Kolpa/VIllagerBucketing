package de.kolpa.mc.villagerbucketing.handlers

import de.kolpa.mc.villagerbucketing.storage.villager.SerializedVillager.Companion.spawn
import de.kolpa.mc.villagerbucketing.storage.villager.VillagerBucketRepository
import de.kolpa.mc.villagerbucketing.util.PlayerInventoryUtils
import org.bukkit.Material
import org.bukkit.entity.Villager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class PlayerInteractEntityEventListener(
    private val villagerBucketRepository: VillagerBucketRepository,
    private val playerInventoryUtils: PlayerInventoryUtils,
) : Listener {
    @EventHandler
    fun onPlayerInteractWithVillager(event: PlayerInteractEntityEvent) {
        val currentItem = playerInventoryUtils.getHeldBucket(event) ?: return

        val villager = event.rightClicked
        if (villager !is Villager) {
            return
        }

        val villagerBucket = villagerBucketRepository.createBucketForVillager(villager)

        villager.remove()

        currentItem.subtract()

        event.player.inventory.addItem(villagerBucket)
    }

    @EventHandler
    fun onPlayerRightClickWithBucket(event: PlayerInteractEvent) {
        val currentItem = playerInventoryUtils.getHeldBucket(event) ?: return

        val spawnPosition = event.interactionPoint ?: return

        val villager = villagerBucketRepository.getSerializedVillagerForBucket(currentItem) ?: return

        villager.spawn(spawnPosition)

        event.player.inventory.remove(currentItem)
        event.player.inventory.addItem(ItemStack(Material.BUCKET))
    }
}
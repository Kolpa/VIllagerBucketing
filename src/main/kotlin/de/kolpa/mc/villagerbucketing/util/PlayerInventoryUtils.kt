package de.kolpa.mc.villagerbucketing.util

import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory

class PlayerInventoryUtils {
    fun getHeldBucket(event: PlayerInteractEntityEvent): ItemStack? {
        val currentItem = getItemFromHand(event.hand, event.player.inventory)

        if (currentItem != null && currentItem.type == Material.BUCKET) {
            return currentItem
        }

        return null
    }

    fun getHeldBucket(event: PlayerInteractEvent): ItemStack? {
        val currentItem = event.hand?.let { getItemFromHand(it, event.player.inventory) }

        if (currentItem != null && currentItem.type == Material.BUCKET) {
            return currentItem
        }

        return null
    }

    private fun getItemFromHand(hand: EquipmentSlot, inventory: PlayerInventory): ItemStack? =
        when (hand) {
            EquipmentSlot.HAND -> inventory.itemInMainHand
            EquipmentSlot.OFF_HAND -> inventory.itemInOffHand
            else -> null
        }
}
package de.kolpa.mc.villagerbucketing

import de.kolpa.mc.villagerbucketing.handlers.PlayerInteractEntityEventListener
import de.kolpa.mc.villagerbucketing.koin.KoinPlugin
import org.bukkit.event.HandlerList
import org.koin.core.component.inject

class VillagerBucketing : KoinPlugin(arrayOf(inventorySortingModule)) {
    override fun onEnable() {
        super.onEnable()

        val playerInteractEntityEventListener by inject<PlayerInteractEntityEventListener>()
        server.pluginManager.registerEvents(playerInteractEntityEventListener, this)
    }

    override fun onDisable() {
        val playerInteractEntityEventListener by inject<PlayerInteractEntityEventListener>()
        HandlerList.unregisterAll(playerInteractEntityEventListener)

        super.onDisable()
    }
}

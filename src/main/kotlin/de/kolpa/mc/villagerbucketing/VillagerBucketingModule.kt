package de.kolpa.mc.villagerbucketing

import de.kolpa.mc.villagerbucketing.handlers.PlayerInteractEntityEventListener
import de.kolpa.mc.villagerbucketing.storage.NamespaceKeyFactory
import de.kolpa.mc.villagerbucketing.storage.villager.VillagerBucketRepository
import de.kolpa.mc.villagerbucketing.util.PlayerInventoryUtils
import org.koin.dsl.module

val inventorySortingModule = module {
    single { VillagerBucketRepository(get()) }
    single { PlayerInventoryUtils() }
    single { NamespaceKeyFactory(get()) }
    single { PlayerInteractEntityEventListener(get(), get()) }
}

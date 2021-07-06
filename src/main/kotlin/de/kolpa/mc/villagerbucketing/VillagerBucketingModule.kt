package de.kolpa.mc.villagerbucketing

import de.kolpa.mc.villagerbucketing.handlers.PlayerInteractEntityEventListener
import de.kolpa.mc.villagerbucketing.storage.NamespaceKeyFactory
import de.kolpa.mc.villagerbucketing.storage.villager.VillagerBucketRepository
import de.kolpa.mc.villagerbucketing.storage.villager.VillagerStorage
import de.kolpa.mc.villagerbucketing.util.PlayerInventoryUtils
import org.koin.dsl.module

val inventorySortingModule = module {
    single { VillagerStorage() }
    single { VillagerBucketRepository(get(), get()) }
    single { PlayerInventoryUtils() }
    single { NamespaceKeyFactory(get()) }
    single { PlayerInteractEntityEventListener(get(), get(), get()) }
}

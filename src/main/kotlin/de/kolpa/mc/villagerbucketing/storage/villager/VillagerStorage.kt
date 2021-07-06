package de.kolpa.mc.villagerbucketing.storage.villager

class VillagerStorage {
    private val storage = mutableMapOf<String, SerializedVillager>()

    fun storeVillager(bucketID: String, serializedVillager: SerializedVillager) {
        storage[bucketID] = serializedVillager
    }

    fun loadVillager(bucketID: String) =
        storage.remove(bucketID)
}
package de.kolpa.mc.villagerbucketing.storage.villager

import com.destroystokyo.paper.entity.villager.Reputation
import com.destroystokyo.paper.entity.villager.ReputationType
import com.google.common.collect.ImmutableMap
import org.bukkit.configuration.serialization.ConfigurationSerializable

data class SerializableReputation(
    val reputation: Reputation
) : ConfigurationSerializable {
    companion object {
        @JvmStatic
        fun deserialize(serial: MutableMap<String, Any>): SerializableReputation {
            val majorNegative = serial["MAJOR_NEGATIVE"]!! as Int
            val minorNegative = serial["MINOR_NEGATIVE"]!! as Int
            val minorPositive = serial["MINOR_POSITIVE"]!! as Int
            val majorPositive = serial["MAJOR_POSITIVE"]!! as Int

            val map = mutableMapOf<ReputationType, Int>()

            map[ReputationType.MAJOR_NEGATIVE] = majorNegative
            map[ReputationType.MINOR_NEGATIVE] = minorNegative
            map[ReputationType.MINOR_POSITIVE] = minorPositive
            map[ReputationType.MAJOR_POSITIVE] = majorPositive

            return SerializableReputation(
                Reputation(map)
            )
        }
    }

    override fun serialize(): MutableMap<String, Any> =
        ImmutableMap.builder<String, Any>()
            .put("MAJOR_NEGATIVE", this.reputation.getReputation(ReputationType.MAJOR_NEGATIVE))
            .put("MINOR_NEGATIVE", this.reputation.getReputation(ReputationType.MINOR_NEGATIVE))
            .put("MINOR_POSITIVE", this.reputation.getReputation(ReputationType.MINOR_POSITIVE))
            .put("MAJOR_POSITIVE", this.reputation.getReputation(ReputationType.MAJOR_POSITIVE))
            .put("TRADING", this.reputation.getReputation(ReputationType.TRADING))
            .build()
}
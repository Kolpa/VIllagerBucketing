package de.kolpa.mc.villagerbucketing.storage.villager

import com.google.common.collect.ImmutableMap
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe

data class SerializableMerchantRecipe(
    val merchantRecipe: MerchantRecipe
) : ConfigurationSerializable {
    companion object {
        @JvmStatic
        fun deserialize(serial: MutableMap<String, Any>): SerializableMerchantRecipe {
            val result = serial["result"]!! as ItemStack
            val uses = serial["uses"]!! as Int
            val maxUses = serial["maxUses"]!! as Int
            val experienceReward = serial["experienceReward"]!! as Boolean
            val villagerExperience = serial["villagerExperience"]!! as Int
            val priceMultiplier = serial["priceMultiplier"]!! as Float
            val ignoreDiscounts = serial["ignoreDiscounts"]!! as Boolean
            val ingredients = serial["ingredients"]!! as List<*>

            return SerializableMerchantRecipe(
                MerchantRecipe(
                    result,
                    uses,
                    maxUses,
                    experienceReward,
                    villagerExperience,
                    priceMultiplier,
                    ignoreDiscounts
                ).also {
                    it.ingredients = ingredients.map { ingredient -> ingredient as ItemStack }
                }
            )
        }
    }

    override fun serialize(): MutableMap<String, Any> =
        ImmutableMap.builder<String, Any>()
            .put("result", this.merchantRecipe.result)
            .put("uses", this.merchantRecipe.uses)
            .put("maxUses", this.merchantRecipe.maxUses)
            .put("experienceReward", this.merchantRecipe.hasExperienceReward())
            .put("villagerExperience", this.merchantRecipe.villagerExperience)
            .put("priceMultiplier", this.merchantRecipe.priceMultiplier)
            .put("ignoreDiscounts", this.merchantRecipe.shouldIgnoreDiscounts())
            .put("ingredients", this.merchantRecipe.ingredients)
            .build()
}
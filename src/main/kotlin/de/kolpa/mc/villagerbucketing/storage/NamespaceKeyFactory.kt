package de.kolpa.mc.villagerbucketing.storage

import org.bukkit.NamespacedKey
import org.bukkit.plugin.java.JavaPlugin

class NamespaceKeyFactory(
    private val javaPlugin: JavaPlugin
) {
    fun getNamespaceKey(string: String): NamespacedKey {
        return NamespacedKey(javaPlugin, string)
    }
}
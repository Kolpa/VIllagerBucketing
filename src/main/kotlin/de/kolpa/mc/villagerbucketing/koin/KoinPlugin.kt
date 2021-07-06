package de.kolpa.mc.villagerbucketing.koin

import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module

open class KoinPlugin(private val usedModules: Array<Module>) : JavaPlugin(), KoinComponent {
    private val baseModule = module {
        single { this@KoinPlugin.logger }
        single<JavaPlugin> { this@KoinPlugin }
    }

    override fun onEnable() {
        startKoin {
            modules(baseModule, *usedModules)
        }
    }

    override fun onDisable() {
        stopKoin()
    }
}
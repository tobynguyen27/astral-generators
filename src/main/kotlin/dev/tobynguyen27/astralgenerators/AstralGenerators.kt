package dev.tobynguyen27.astralgenerators

import com.tterrag.registrate.Registrate
import dev.tobynguyen27.astralgenerators.core.multiblock.level.ChunkEventListeners
import dev.tobynguyen27.astralgenerators.core.multiblock.pool.MultiblocksPool
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.data.client.Texts
import dev.tobynguyen27.astralgenerators.data.config.AGConfig
import dev.tobynguyen27.astralgenerators.data.config.ConfigTexts
import dev.tobynguyen27.astralgenerators.hooks.IntegrationHooks
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import dev.tobynguyen27.astralgenerators.registry.AGBlocks
import dev.tobynguyen27.astralgenerators.registry.AGFluids
import dev.tobynguyen27.astralgenerators.registry.AGItems
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import dev.tobynguyen27.astralgenerators.registry.AGRecipes
import dev.tobynguyen27.astralgenerators.registry.AGSounds
import dev.tobynguyen27.codebebelib.Bebe
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.world.item.CreativeModeTab
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object AstralGenerators : ModInitializer {
    const val MOD_ID = "astralgenerators"
    const val MOD_NAME = "Astral Generators"
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

    val REGISTRATE: Registrate = Registrate.create(MOD_ID)
    val ITEM_GROUP: CreativeModeTab =
        FabricItemGroupBuilder.build(Identifier("general")) {
            AGItems.ASTRALNOMICON.get().defaultInstance
        }

    override fun onInitialize() {
        Bebe.initialize()
        AutoConfig.register<AGConfig>(AGConfig::class.java, ::Toml4jConfigSerializer)
        ConfigTexts.register()

        ChunkEventListeners.initialize()
        MultiblocksPool.initialize()

        registerItemGroups()
        Texts.register()

        AGFluids.register()
        AGItems.register()
        AGBlocks.register()
        AGBlockEntities.register()
        AGMenus.register()
        AGRecipes.register()
        AGSounds.register()

        REGISTRATE.register()
        IntegrationHooks.init()
    }

    private fun registerItemGroups() {
        REGISTRATE.creativeModeTab { ITEM_GROUP }
        REGISTRATE.addRawLang("itemGroup.$MOD_ID.general", MOD_NAME)
    }
}

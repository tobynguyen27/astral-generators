package dev.tobynguyen27.astralgenerators

import com.tterrag.registrate.Registrate
import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.AGBlocks
import dev.tobynguyen27.astralgenerators.contents.AGFluids
import dev.tobynguyen27.astralgenerators.contents.AGItems
import dev.tobynguyen27.astralgenerators.contents.lang.Texts
import dev.tobynguyen27.astralgenerators.gui.AGMenus
import dev.tobynguyen27.astralgenerators.hooks.IntegrationHooks
import dev.tobynguyen27.astralgenerators.multiblocks.level.ChunkEventListeners
import dev.tobynguyen27.astralgenerators.recipes.AGRecipes
import dev.tobynguyen27.astralgenerators.utils.Identifier
import dev.tobynguyen27.codebebelib.Bebe
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.world.item.CreativeModeTab
import org.slf4j.LoggerFactory

object AstralGenerators : ModInitializer {
    const val MOD_ID = "astralgenerators"
    const val MOD_NAME = "Astral Generators"
    val LOGGER = LoggerFactory.getLogger(MOD_ID)

    val REGISTRATE: Registrate = Registrate.create(MOD_ID)
    val ITEM_GROUP: CreativeModeTab =
        FabricItemGroupBuilder.build(Identifier("general")) {
            AGItems.ASTRALNOMICON.get().defaultInstance
        }

    override fun onInitialize() {
        Bebe.initialize()
        ChunkEventListeners.initialize()

        registerItemGroups()
        Texts.register()

        AGFluids.register()
        AGItems.register()
        AGBlocks.register()
        AGBlockEntities.register()
        AGMenus.register()
        AGRecipes.register()

        REGISTRATE.register()
        IntegrationHooks.init()
    }

    private fun registerItemGroups() {
        REGISTRATE.creativeModeTab { ITEM_GROUP }
        REGISTRATE.addRawLang("itemGroup.$MOD_ID.general", MOD_NAME)
    }
}

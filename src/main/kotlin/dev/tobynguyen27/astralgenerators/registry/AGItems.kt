package dev.tobynguyen27.astralgenerators.registry

import com.tterrag.registrate.util.entry.ItemEntry
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.contents.items.Astralnomicon
import dev.tobynguyen27.astralgenerators.contents.items.ResolithManipulator
import dev.tobynguyen27.astralgenerators.contents.items.Wrench
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvaRod
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvarColor
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvarDust
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvarIngot
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvarNugget
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvarPlate
import dev.tobynguyen27.astralgenerators.registry.helper.MaterialSetRegistry

object AGItems {

    val ASTRALNOMICON: ItemEntry<Astralnomicon> =
        REGISTRATE.item(Astralnomicon.ID, ::Astralnomicon).properties { it.stacksTo(1) }.register()

    val WRENCH: ItemEntry<Wrench> =
        REGISTRATE.item(Wrench.ID, ::Wrench).properties { it.stacksTo(1) }.register()

    val RESOLITH_MANIPULATOR: ItemEntry<ResolithManipulator> =
        REGISTRATE.item(ResolithManipulator.ID, ::ResolithManipulator)
            .properties { it.stacksTo(1) }
            .register()

    val CALVAR_INGOT =
        MaterialSetRegistry.registerAlloyIngot(CalvarIngot.ID, ::CalvarIngot, CalvarColor.PRIMARY)
            .register()
    val CALVAR_NUGGET =
        MaterialSetRegistry.registerAlloyNugget(
                CalvarNugget.ID,
                ::CalvarNugget,
                CalvarColor.PRIMARY,
            )
            .register()
    val CALVAR_PLATE =
        MaterialSetRegistry.registerAlloyPlate(CalvarPlate.ID, ::CalvarPlate, CalvarColor.PRIMARY)
            .register()
    val CALVAR_ROD =
        MaterialSetRegistry.registerAlloyRod(CalvaRod.ID, ::CalvaRod, CalvarColor.PRIMARY)
            .register()
    val CALVAR_DUST =
        MaterialSetRegistry.registerAlloyDust(CalvarDust.ID, ::CalvarDust, CalvarColor.DARKER)
            .register()

    fun register() {}
}

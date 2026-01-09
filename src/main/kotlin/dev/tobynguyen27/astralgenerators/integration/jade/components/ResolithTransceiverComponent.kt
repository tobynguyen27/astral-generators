package dev.tobynguyen27.astralgenerators.integration.jade.components

import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.ResolithTransceiverBlockEntity
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.data.client.Texts
import net.minecraft.ChatFormatting
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import snownee.jade.api.BlockAccessor
import snownee.jade.api.IBlockComponentProvider
import snownee.jade.api.IServerDataProvider
import snownee.jade.api.ITooltip
import snownee.jade.api.config.IPluginConfig

object ResolithTransceiverComponent: IBlockComponentProvider, IServerDataProvider<BlockEntity> {

    private const val MODE_KEY = "mode"

    override fun getUid(): ResourceLocation = Identifier("resolith_transceiver")

    override fun appendTooltip(
        tooltip: ITooltip,
        accessor: BlockAccessor,
        config: IPluginConfig
    ) {
        if (
            accessor.serverData.contains(MODE_KEY)
        ) {
            val mode = accessor.serverData.getBoolean(MODE_KEY)
            val modeKey = if(mode) TranslatableComponent(Texts.IMPORT) else TranslatableComponent(Texts.EXPORT)

            tooltip.add(
                TranslatableComponent(
                    Texts.RESOLITH_RELAY_MODE,
                    modeKey,
                )
                    .withStyle(ChatFormatting.GREEN)
            )
        }
    }

    override fun appendServerData(
        data: CompoundTag,
        player: ServerPlayer,
        world: Level,
        blockEntity: BlockEntity,
        showDetails: Boolean
    ) {
        if (blockEntity is ResolithTransceiverBlockEntity) {
            data.putBoolean(MODE_KEY, blockEntity.isImport)
        }
    }
}

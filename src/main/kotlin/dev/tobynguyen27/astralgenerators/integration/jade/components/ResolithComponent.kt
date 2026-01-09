package dev.tobynguyen27.astralgenerators.integration.jade.components

import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlockEntity
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

object ResolithComponent : IBlockComponentProvider, IServerDataProvider<BlockEntity> {

    private const val CONNECTIONS_KEY = "connections"
    private const val CONNECTIONS_LIMIT_KEY = "connections_limit"

    override fun getUid(): ResourceLocation = Identifier("resolith")

    override fun appendTooltip(tooltip: ITooltip, accessor: BlockAccessor, config: IPluginConfig) {
        if (
            accessor.serverData.contains(CONNECTIONS_KEY) &&
                accessor.serverData.contains(CONNECTIONS_LIMIT_KEY)
        ) {
            tooltip.add(
                TranslatableComponent(
                        Texts.RESOLITH_CONNECTIONS,
                        accessor.serverData.getInt(CONNECTIONS_KEY).toString(),
                        accessor.serverData.getInt(CONNECTIONS_LIMIT_KEY).toString(),
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
        showDetails: Boolean,
    ) {
        if (blockEntity is ResolithBlockEntity) {
            data.putInt(CONNECTIONS_KEY, blockEntity.connectedNodes.size)
            data.putInt(CONNECTIONS_LIMIT_KEY, blockEntity.getMaxConnection())
        }
    }
}

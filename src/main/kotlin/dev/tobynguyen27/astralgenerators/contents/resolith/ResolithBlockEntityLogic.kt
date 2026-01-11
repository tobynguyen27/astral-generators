package dev.tobynguyen27.astralgenerators.contents.resolith

import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithAttribute
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.ResolithTransceiver
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.ResolithTransceiverBlockEntity
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import team.reborn.energy.api.EnergyStorage

object ResolithBlockEntityLogic {
    fun serverTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: ResolithBlockEntity,
    ) {
        if (blockEntity !is ResolithTransceiverBlockEntity) return // Just for sure
        if (!blockEntity.isSendEnergy) return

        if (blockEntity.isNetworkDirty) {
            blockEntity.cachedTargets = findTargets(level, blockEntity)
            blockEntity.isNetworkDirty = false
        }

        if (blockEntity.cachedTargets.isEmpty()) return

        val stats =
            ResolithAttribute.getStats(blockEntity.getResolithType(), blockEntity.getResolithTier())
        val maxTransfer = stats.transferRate

        val direction = blockState.getValue(ResolithTransceiver.FACING).opposite
        val sourcePos = blockPos.relative(direction)
        val sourceStorage = EnergyStorage.SIDED.find(level, sourcePos, direction) ?: return

        if (sourceStorage.amount <= 0) return

        var availableEnergy: Long = 0
        Transaction.openOuter().use { availableEnergy = sourceStorage.extract(maxTransfer, it) }

        if (availableEnergy <= 0) return

        Transaction.openOuter().use {
            var currentEnergyPool = availableEnergy
            var targetsRemaining = blockEntity.cachedTargets.size
            var totalInserted: Long = 0

            for (targetPos in blockEntity.cachedTargets) {
                if (currentEnergyPool <= 0) break

                if (!level.isLoaded(targetPos)) {
                    targetsRemaining--
                    continue
                }

                val targetEntity =
                    level.getBlockEntity(targetPos) as? ResolithTransceiverBlockEntity
                if (targetEntity == null || targetEntity.isSendEnergy) {
                    targetsRemaining--
                    continue
                }

                val targetDirection =
                    targetEntity.blockState.getValue(ResolithTransceiver.FACING).opposite
                val targetMachinePos = targetPos.relative(targetDirection)
                val targetStorage =
                    EnergyStorage.SIDED.find(level, targetMachinePos, targetDirection)

                if (targetStorage == null) {
                    targetsRemaining--
                    continue
                }

                val share = currentEnergyPool / targetsRemaining

                val accepted = targetStorage.insert(share, it)

                currentEnergyPool -= accepted
                totalInserted += accepted
                targetsRemaining--
            }

            if (totalInserted > 0) {
                val extracted = sourceStorage.extract(totalInserted, it)

                if (extracted == totalInserted) {
                    it.commit()
                }
            }
        }
    }

    private fun findTargets(
        level: Level,
        startNode: ResolithTransceiverBlockEntity,
    ): MutableList<BlockPos> {
        val targets = ArrayList<BlockPos>()
        val visited = HashSet<BlockPos>()
        val queue = ArrayDeque<BlockPos>()

        queue.add(startNode.blockPos)
        visited.add(startNode.blockPos)

        while (!queue.isEmpty()) {
            val currentPos = queue.removeFirst()
            if (!level.isLoaded(currentPos)) continue

            val currentNode = level.getBlockEntity(currentPos) as? ResolithBlockEntity ?: continue

            if (
                currentNode is ResolithTransceiverBlockEntity &&
                    !currentNode.isSendEnergy &&
                    currentNode != startNode
            ) {
                targets.add(currentPos)
            }

            for (neighborPos in currentNode.connectedNodes) {
                if (visited.add(neighborPos)) {
                    queue.add(neighborPos)
                }
            }
        }
        return targets
    }
}

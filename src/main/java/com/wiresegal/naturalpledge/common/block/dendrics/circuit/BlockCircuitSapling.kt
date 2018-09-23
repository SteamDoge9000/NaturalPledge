package com.wiresegal.naturalpledge.common.block.dendrics.circuit

import com.teamwizardry.librarianlib.features.base.block.BlockModSapling
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import com.wiresegal.naturalpledge.common.block.ModBlocks
import com.wiresegal.naturalpledge.common.lexicon.LexiconEntries
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*

/**
 * @author WireSegal
 * Created at 10:25 PM on 5/29/16.
 */
class BlockCircuitSapling(name: String) : BlockModSapling(name), ILexiconable, ICircuitBlock {

    override fun updateTick(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random) {
        super.updateTick(worldIn, pos, state, rand)
        worldIn.notifyNeighborsOfStateChange(pos, this, true)
    }

    override fun generateTree(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random) {
        defaultSaplingBehavior(worldIn, pos, state, rand, ModBlocks.circuitLog, ModBlocks.circuitLeaves)
    }

    override fun getLightValue(state: IBlockState?, world: IBlockAccess?, pos: BlockPos?): Int {
        return 8
    }

    override fun canProvidePower(state: IBlockState?): Boolean {
        return true
    }

    override fun tickRate(worldIn: World): Int {
        return 1
    }

    override fun getWeakPower(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing?): Int {
        return ICircuitBlock.getPower(blockAccess, pos)
    }

    override fun getEntry(p0: World?, p1: BlockPos?, p2: EntityPlayer?, p3: ItemStack): LexiconEntry? {
        return LexiconEntries.circuitTree
    }
}

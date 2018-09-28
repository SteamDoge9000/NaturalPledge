package com.wiresegal.naturalpledge.common.block

import com.teamwizardry.librarianlib.features.base.block.BlockModPane
import net.minecraft.block.SoundType
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import com.wiresegal.naturalpledge.common.lexicon.LexiconEntries
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry

/**
 * @author WireSegal
 * Created at 1:26 PM on 6/4/16.
 */
class BlockAquamarinePane(name: String) : BlockModPane(name, true, ModBlocks.aquaGlass.defaultState), ILexiconable {

    init {
        blockHardness = 0.3F
        soundType = SoundType.GLASS
    }

    override fun getBeaconColorMultiplier(state: IBlockState?, world: World?, pos: BlockPos?, beaconPos: BlockPos?): FloatArray {
        return floatArrayOf(0.3F, 0.45F, 0.775F)
    }

    override fun getLightValue(state: IBlockState?, world: IBlockAccess?, pos: BlockPos?) = 10

    @SideOnly(Side.CLIENT)
    override fun getRenderLayer(): BlockRenderLayer {
        return BlockRenderLayer.TRANSLUCENT
    }

    override fun canPaneConnectTo(world: IBlockAccess, pos: BlockPos, dir: EnumFacing): Boolean {
        return super.canPaneConnectTo(world, pos, dir) || world.getBlockState(pos.offset(dir)).block == ModBlocks.aquaGlass
    }

    override fun getEntry(p0: World?, p1: BlockPos?, p2: EntityPlayer?, p3: ItemStack): LexiconEntry? {
        return LexiconEntries.njordSpells
    }
}

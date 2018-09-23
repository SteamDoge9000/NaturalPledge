package com.wiresegal.naturalpledge.common.block.dendrics.thunder

import com.teamwizardry.librarianlib.features.base.block.BlockModLeaves
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import com.wiresegal.naturalpledge.common.block.ModBlocks
import com.wiresegal.naturalpledge.common.lexicon.LexiconEntries
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*

/**
 * @author WireSegal
 * Created at 10:36 PM on 5/27/16.
 */
class BlockThunderLeaves(name: String) : BlockModLeaves(name), IThunderAbsorber, ILexiconable {
    override val canBeOpaque: Boolean
        get() = false

    override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item? {
        return Item.getItemFromBlock(ModBlocks.thunderSapling)
    }

    override fun getEntry(p0: World?, p1: BlockPos?, p2: EntityPlayer?, p3: ItemStack): LexiconEntry? {
        return LexiconEntries.thunderTree
    }
}

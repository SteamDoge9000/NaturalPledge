package com.wiresegal.naturalpledge.common.block.dendrics.calico

import com.teamwizardry.librarianlib.features.base.block.BlockModPlanks
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Explosion
import net.minecraft.world.World
import com.wiresegal.naturalpledge.common.lexicon.LexiconEntries
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry

/**
 * @author WireSegal
 * Created at 10:36 PM on 5/27/16.
 */
class BlockCalicoPlanks(name: String) : BlockModPlanks(name), IExplosionDampener, ILexiconable {

    override fun onBlockExploded(world: World?, pos: BlockPos?, explosion: Explosion?) {
        //NO-OP
    }

    override fun getEntry(p0: World?, p1: BlockPos?, p2: EntityPlayer?, p3: ItemStack): LexiconEntry? {
        return LexiconEntries.calicoTree
    }
}

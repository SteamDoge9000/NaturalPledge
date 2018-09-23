package com.wiresegal.naturalpledge.common.block

import com.teamwizardry.librarianlib.features.base.block.BlockModSapling
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import com.wiresegal.naturalpledge.api.SaplingVariantRegistry
import com.wiresegal.naturalpledge.api.lib.LibMisc
import com.wiresegal.naturalpledge.api.sapling.IIridescentSaplingVariant
import com.wiresegal.naturalpledge.api.sapling.IridescentSaplingBaseVariant
import com.wiresegal.naturalpledge.common.block.alt.BlockAltLeaves
import com.wiresegal.naturalpledge.common.block.alt.BlockAltLog
import com.wiresegal.naturalpledge.common.block.colored.BlockIridescentDirt
import com.wiresegal.naturalpledge.common.block.colored.BlockIridescentLeaves
import com.wiresegal.naturalpledge.common.block.colored.BlockIridescentLog
import com.wiresegal.naturalpledge.common.lexicon.LexiconEntries
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.api.state.BotaniaStateProps
import vazkii.botania.api.state.enums.AltGrassVariant
import java.util.*
import vazkii.botania.common.block.ModBlocks as BotaniaBlocks

/**
 * @author WireSegal
 * Created at 4:01 PM on 5/14/16.
 */
class BlockIrisSapling(name: String) : BlockModSapling(name), ILexiconable {

    companion object {
        class IridescentDirtSaplingVariant : IIridescentSaplingVariant {
            override fun getLeaves(soil: IBlockState): IBlockState {
                val dye = soil.getValue(BlockIridescentDirt.COLOR)
                val colorSet = dye.metadata / 4
                val block = ModBlocks.irisLeaves[colorSet]
                return block.defaultState.withProperty(BlockIridescentLeaves.COLOR_PROPS[block.colorSet], dye)
            }

            override fun getWood(soil: IBlockState): IBlockState {
                val dye = soil.getValue(BlockIridescentDirt.COLOR)
                val colorSet = dye.metadata / 4
                val block = ModBlocks.irisLogs[colorSet]
                return block.defaultState.withProperty(BlockIridescentLog.COLOR_PROPS[block.colorSet], dye)
            }

            override fun matchesSoil(soil: IBlockState): Boolean {
                return soil.block == ModBlocks.irisDirt
            }

            override fun getDisplaySoilBlockstates(): MutableList<IBlockState>? {
                return mutableListOf(*Array(16) {
                    ModBlocks.irisDirt.defaultState.withProperty(BlockIridescentDirt.COLOR, EnumDyeColor.byMetadata(it))
                })
            }

            override fun toString(): String {
                return "${LibMisc.MOD_ID}:{ soil=irisDirt wood=irisWood[0-3] leaves=irisLeaves[0-3] }"
            }
        }

        class AltGrassSaplingVariant : IIridescentSaplingVariant {
            override fun getLeaves(soil: IBlockState): IBlockState {
                val variant = soil.getValue(BotaniaStateProps.ALTGRASS_VARIANT)
                val colorSet = variant.ordinal / 4
                val block = ModBlocks.altLeaves[colorSet]
                return block.defaultState.withProperty(BlockAltLeaves.TYPE_PROPS[block.colorSet], variant)
            }

            override fun getWood(soil: IBlockState): IBlockState {
                val variant = soil.getValue(BotaniaStateProps.ALTGRASS_VARIANT)
                val colorSet = variant.ordinal / 4
                val block = ModBlocks.altLogs[colorSet]
                return block.defaultState.withProperty(BlockAltLog.TYPE_PROPS[block.colorSet], variant)
            }

            override fun matchesSoil(soil: IBlockState): Boolean {
                return soil.block == BotaniaBlocks.altGrass
            }

            override fun getDisplaySoilBlockstates(): MutableList<IBlockState>? {
                return mutableListOf(*Array(6) {
                    BotaniaBlocks.altGrass.defaultState.withProperty(BotaniaStateProps.ALTGRASS_VARIANT, AltGrassVariant.values()[it])
                })
            }

            override fun toString(): String {
                return "${LibMisc.MOD_ID}:{ soil=altGrass wood=altWood[0-1] leaves=altLeaves[0-1] }"
            }
        }
    }

    init {
        SaplingVariantRegistry.registerVariant("irisDirt", IridescentDirtSaplingVariant())
        SaplingVariantRegistry.registerVariant("altGrass", AltGrassSaplingVariant())
        SaplingVariantRegistry.registerVariant("rainbowDirt",
                IridescentSaplingBaseVariant(
                        ModBlocks.rainbowDirt.defaultState,
                        ModBlocks.rainbowLog.defaultState,
                        ModBlocks.rainbowLeaves.defaultState))
        SaplingVariantRegistry.registerVariant("auroraDirt",
                IridescentSaplingBaseVariant(
                        ModBlocks.auroraDirt.defaultState,
                        ModBlocks.auroraLog.defaultState,
                        ModBlocks.auroraLeaves.defaultState))
    }

    override fun canSustainBush(state: IBlockState): Boolean {
        return SaplingVariantRegistry.getVariant(state) != null || super.canSustainBush(state)
    }

    override fun generateTree(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random) {
        val soil = worldIn.getBlockState(pos.down()) ?: return
        val variant = SaplingVariantRegistry.getVariant(soil) ?: return

        defaultSaplingBehavior(worldIn, pos, state, rand, variant.getWood(soil), variant.getLeaves(soil))
        worldIn.setBlockState(pos.down(), soil)
    }

    override fun canGrow(worldIn: World, pos: BlockPos, state: IBlockState, isClient: Boolean): Boolean {
        return SaplingVariantRegistry.getVariant(worldIn.getBlockState(pos.down())) != null
    }

    override fun getEntry(p0: World?, p1: BlockPos?, p2: EntityPlayer?, p3: ItemStack): LexiconEntry? {
        return LexiconEntries.sapling
    }
}

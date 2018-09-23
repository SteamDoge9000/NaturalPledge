package com.wiresegal.naturalpledge.common.block

import com.teamwizardry.librarianlib.features.base.block.BlockMod
import com.teamwizardry.librarianlib.features.base.block.BlockModPane
import com.teamwizardry.librarianlib.features.base.block.BlockModSapling
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict.OreDictionary
import com.wiresegal.naturalpledge.api.lib.LibMisc
import com.wiresegal.naturalpledge.common.block.alt.BlockAltLeaves
import com.wiresegal.naturalpledge.common.block.alt.BlockAltLog
import com.wiresegal.naturalpledge.common.block.alt.BlockAltPlanks
import com.wiresegal.naturalpledge.common.block.colored.*
import com.wiresegal.naturalpledge.common.block.colored.BlockIridescentPlanks.BlockAuroraPlanks
import com.wiresegal.naturalpledge.common.block.colored.BlockIridescentPlanks.BlockRainbowPlanks
import com.wiresegal.naturalpledge.common.block.dendrics.calico.*
import com.wiresegal.naturalpledge.common.block.dendrics.circuit.BlockCircuitLeaves
import com.wiresegal.naturalpledge.common.block.dendrics.circuit.BlockCircuitLog
import com.wiresegal.naturalpledge.common.block.dendrics.circuit.BlockCircuitPlanks
import com.wiresegal.naturalpledge.common.block.dendrics.circuit.BlockCircuitSapling
import com.wiresegal.naturalpledge.common.block.dendrics.sealing.*
import com.wiresegal.naturalpledge.common.block.dendrics.thunder.*
import com.wiresegal.naturalpledge.common.block.tile.TileCracklingStar
import com.wiresegal.naturalpledge.common.block.tile.TileLivingwoodFunnel
import com.wiresegal.naturalpledge.common.block.tile.TilePrismFlame
import com.wiresegal.naturalpledge.common.block.tile.TileStar
import com.wiresegal.naturalpledge.common.block.trap.*
import com.wiresegal.naturalpledge.common.lib.LibNames
import com.wiresegal.naturalpledge.common.lib.LibOreDict
import vazkii.botania.api.state.enums.StorageVariant
import vazkii.botania.common.block.ModBlocks as BotaniaBlocks

/**
 * @author WireSegal
 * Created at 2:46 PM on 4/17/16.
 */
object ModBlocks {
    val awakenerCore = BlockAwakenerCore(LibNames.AWAKENER)
    val star: BlockMod
    val flame: BlockMod
    val irisDirt: BlockMod
    val rainbowDirt: BlockMod
    val auroraDirt: BlockMod
    val irisPlanks: BlockMod
    val irisLogs: Array<BlockIridescentLog>
    val rainbowPlanks: BlockMod
    val auroraPlanks: BlockMod
    val rainbowLog: BlockMod
    val auroraLog: BlockMod
    val irisLeaves: Array<BlockIridescentLeaves>
    val rainbowLeaves: BlockMod
    val auroraLeaves: BlockMod
    val irisSapling: BlockModSapling
    val altLogs: Array<BlockAltLog>
    val altLeaves: Array<BlockAltLeaves>
    val altPlanks: BlockMod
    val storage: BlockMod
    val irisLamp: BlockMod

    val sealSapling: BlockModSapling
    val sealPlanks: BlockMod
    val sealLeaves: BlockMod
    val sealLog: BlockMod

    val amp: BlockMod

    val thunderSapling: BlockModSapling
    val thunderPlanks: BlockMod
    val thunderLeaves: BlockMod
    val thunderLog: BlockMod

    val circuitSapling: BlockModSapling
    val circuitPlanks: BlockMod
    val circuitLeaves: BlockMod
    val circuitLog: BlockMod

    val calicoSapling: BlockModSapling
    val calicoPlanks: BlockMod
    val calicoLeaves: BlockMod
    val calicoLog: BlockMod

    val aquaGlass: BlockMod
    val aquaPane: BlockModPane

    val funnel: BlockMod

    val thunderTrap: BlockMod
    val cracklingStar: BlockMod

    val corporeaResonator: BlockMod
    val enderActuator: BlockMod

    val disorientTrap: BlockMod
    val infernoTrap: BlockMod
    val launchTrap: BlockMod
    val rootTrap: BlockMod
    val sandTrap: BlockMod
    val signalTrap: BlockMod
    val wrathTrap: BlockMod

//    val gayBeacon: BlockMod

    init {
        star = BlockFrozenStar(LibNames.STAR)
        flame = BlockPrismFlame(LibNames.PRISM_FLAME)
        irisDirt = BlockIridescentDirt(LibNames.IRIS_DIRT)
        rainbowDirt = BlockRainbowDirt(LibNames.RAINBOW_DIRT)
        irisPlanks = BlockIridescentPlanks(LibNames.IRIS_PLANKS)
        irisLogs = Array(4) {
            object : BlockIridescentLog(LibNames.IRIS_LOG, it) {
                override val colorSet: Int
                    get() = it
            }
        }
        rainbowPlanks = BlockRainbowPlanks(LibNames.RAINBOW_PLANKS)
        rainbowLog = BlockRainbowLog(LibNames.RAINBOW_LOG)
        irisLeaves = Array(4) {
            object : BlockIridescentLeaves(LibNames.IRIS_LEAVES, it) {
                override val colorSet: Int
                    get() = it
            }
        }
        rainbowLeaves = BlockRainbowLeaves(LibNames.RAINBOW_LEAVES)

        auroraDirt = BlockAuroraDirt(LibNames.AURORA_DIRT)
        auroraPlanks = BlockAuroraPlanks(LibNames.AURORA_PLANKS)
        auroraLog = BlockAuroraLog(LibNames.AURORA_LOG)
        auroraLeaves = BlockAuroraLeaves(LibNames.AURORA_LEAVES)

        irisSapling = BlockIrisSapling(LibNames.IRIS_SAPLING)
        altLogs = Array(2) {
            object : BlockAltLog(LibNames.ALT_LOG, it) {
                override val colorSet: Int
                    get() = it
            }
        }
        altLeaves = Array(2) {
            object : BlockAltLeaves(LibNames.ALT_LEAVES, it) {
                override val colorSet: Int
                    get() = it
            }
        }
        altPlanks = BlockAltPlanks(LibNames.ALT_PLANKS)
        storage = BlockStorage(LibNames.STORAGE)
        irisLamp = BlockColoredLamp(LibNames.IRIS_LAMP)

        SoundSealEventHandler
        sealSapling = BlockSealSapling(LibNames.SEAL_SAPLING)
        sealPlanks = BlockSealPlanks(LibNames.SEAL_PLANKS)
        sealLeaves = BlockSealLeaves(LibNames.SEAL_LEAVES)
        sealLog = BlockSealingLog(LibNames.SEAL_LOG)

        amp = BlockAmplifier(LibNames.AMPLIFIER)

        ThunderEventHandler
        thunderSapling = BlockThunderSapling(LibNames.THUNDER_SAPLING)
        thunderPlanks = BlockThunderPlanks(LibNames.THUNDER_PLANKS)
        thunderLeaves = BlockThunderLeaves(LibNames.THUNDER_LEAVES)
        thunderLog = BlockThunderLog(LibNames.THUNDER_LOG)

        circuitSapling = BlockCircuitSapling(LibNames.CIRCUIT_SAPLING)
        circuitPlanks = BlockCircuitPlanks(LibNames.CIRCUIT_PLANKS)
        circuitLeaves = BlockCircuitLeaves(LibNames.CIRCUIT_LEAVES)
        circuitLog = BlockCircuitLog(LibNames.CIRCUIT_LOG)

        CalicoEventHandler
        calicoSapling = BlockCalicoSapling(LibNames.CALICO_SAPLING)
        calicoPlanks = BlockCalicoPlanks(LibNames.CALICO_PLANKS)
        calicoLeaves = BlockCalicoLeaves(LibNames.CALICO_LEAVES)
        calicoLog = BlockCalicoLog(LibNames.CALICO_LOG)

        aquaGlass = BlockAquamarineGlass(LibNames.AQUA_GLASS)
        aquaPane = BlockAquamarinePane(LibNames.AQUA_PANE)

        funnel = BlockFunnel(LibNames.FUNNEL)

        thunderTrap = BlockThunderTrap(LibNames.TRAP)
        cracklingStar = BlockCracklingStar(LibNames.CRACKLING)

        corporeaResonator = BlockCorporeaResonator(LibNames.CORPOREA_RESONATOR)
        enderActuator = BlockEnderBind(LibNames.ENDER_ACTUATOR)

        disorientTrap = BlockDisorientationTrap()
        infernoTrap = BlockInfernoTrap()
        launchTrap = BlockLaunchTrap()
        rootTrap = BlockRootTrap()
        sandTrap = BlockSandTrap()
        signalTrap = BlockSignalTrap()
        wrathTrap = BlockWrathTrap()

//        gayBeacon = BlockGayBeacon()

        GameRegistry.registerTileEntity(TileStar::class.java, ResourceLocation(LibMisc.MOD_ID, LibNames.STAR).toString())
        GameRegistry.registerTileEntity(TileCracklingStar::class.java, ResourceLocation(LibMisc.MOD_ID, LibNames.CRACKLING).toString())
        GameRegistry.registerTileEntity(TilePrismFlame::class.java, ResourceLocation(LibMisc.MOD_ID, LibNames.PRISM_FLAME).toString())
        GameRegistry.registerTileEntity(TileLivingwoodFunnel::class.java, ResourceLocation(LibMisc.MOD_ID, LibNames.FUNNEL).toString())

    }

    // TODO make lowercase
    fun OreDict() {
        OreDictionary.registerOre(LibOreDict.BLOCK_AQUAMARINE, ItemStack(storage, 1, BlockStorage.Variants.AQUAMARINE.ordinal))
        OreDictionary.registerOre(LibOreDict.BLOCK_THUNDERSTEEL, ItemStack(storage, 1, BlockStorage.Variants.THUNDERSTEEL.ordinal))

        OreDictionary.registerOre(LibOreDict.THUNDER_PLANKS, ItemStack(thunderPlanks, 1, OreDictionary.WILDCARD_VALUE))
        OreDictionary.registerOre(LibOreDict.SEAL_PLANKS, ItemStack(sealPlanks, 1, OreDictionary.WILDCARD_VALUE))
        OreDictionary.registerOre(LibOreDict.CIRCUIT_PLANKS, ItemStack(circuitPlanks, 1, OreDictionary.WILDCARD_VALUE))
        OreDictionary.registerOre(LibOreDict.CALICO_PLANKS, ItemStack(calicoPlanks, 1, OreDictionary.WILDCARD_VALUE))

        OreDictionary.registerOre(LibOreDict.IRIS_DIRT, ItemStack(irisDirt, 1, OreDictionary.WILDCARD_VALUE))
        OreDictionary.registerOre(LibOreDict.IRIS_DIRT, ItemStack(rainbowDirt, 1, OreDictionary.WILDCARD_VALUE))
        OreDictionary.registerOre(LibOreDict.IRIS_DIRT, ItemStack(auroraDirt, 1, OreDictionary.WILDCARD_VALUE))
        OreDictionary.registerOre("dirt", ItemStack(irisDirt, 1, OreDictionary.WILDCARD_VALUE))
        OreDictionary.registerOre("dirt", ItemStack(irisDirt, 1, OreDictionary.WILDCARD_VALUE))
        OreDictionary.registerOre("dirt", ItemStack(auroraDirt, 1, OreDictionary.WILDCARD_VALUE))
        OreDictionary.registerOre("blockGlass", ItemStack(aquaGlass, 1, OreDictionary.WILDCARD_VALUE))

        OreDictionary.registerOre(LibOreDict.BLOCK_MANASTEEL, ItemStack(BotaniaBlocks.storage, 1, StorageVariant.MANASTEEL.ordinal))
        OreDictionary.registerOre(LibOreDict.BLOCK_TERRASTEEL, ItemStack(BotaniaBlocks.storage, 1, StorageVariant.TERRASTEEL.ordinal))
        OreDictionary.registerOre(LibOreDict.BLOCK_ELEMENTIUM, ItemStack(BotaniaBlocks.storage, 1, StorageVariant.ELEMENTIUM.ordinal))

        OreDictionary.registerOre(LibOreDict.DYES[16], BotaniaBlocks.bifrostPerm)
    }
}

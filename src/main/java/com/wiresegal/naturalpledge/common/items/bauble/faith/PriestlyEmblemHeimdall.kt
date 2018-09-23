package com.wiresegal.naturalpledge.common.items.bauble.faith

import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util.math.BlockPos
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.fluids.IFluidBlock
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import com.wiresegal.naturalpledge.api.priest.IFaithVariant
import com.wiresegal.naturalpledge.common.NaturalPledge
import com.wiresegal.naturalpledge.common.lib.LibNames
import com.wiresegal.naturalpledge.common.potions.ModPotions
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.block.ModBlocks
import vazkii.botania.common.block.tile.TileBifrost
import vazkii.botania.common.core.helper.Vector3
import vazkii.botania.common.item.ModItems

/**
 * @author WireSegal
 * Created at 9:49 AM on 4/18/16.
 */
object PriestlyEmblemHeimdall : IFaithVariant {

    override fun getName(): String = "heimdall"

    override fun hasSubscriptions(): Boolean = true

    override fun getSpells(stack: ItemStack, player: EntityPlayer): MutableList<String> {
        return mutableListOf(LibNames.SPELL_RAINBOW, LibNames.SPELL_SPHERE)
    }

    @SideOnly(Side.CLIENT)
    override fun getColor(): IItemColor? =
            IItemColor { _, i ->
                if (i == 1)
                    NaturalPledge.PROXY.rainbow().rgb
                else
                    0xFFFFFF
            }

    override fun onUpdate(stack: ItemStack, player: EntityPlayer) {
        if (!player.world.isRemote && ManaItemHandler.requestManaExact(stack, player, 1, true) && !ModPotions.drab.hasEffect(player))
            player.addPotionEffect(PotionEffect(MobEffects.NIGHT_VISION, 610, 0, true, false))
    }

    override fun punishTheFaithless(stack: ItemStack, player: EntityPlayer) {
        player.addPotionEffect(PotionEffect(ModPotions.drab, 600, 2))
        player.removePotionEffect(MobEffects.NIGHT_VISION)
    }

    fun getMotionVec(e: Entity): Vector3 {
        if (e is EntityPlayer) {
            val last = Vector3(e.prevPosX, e.prevPosY, e.prevPosZ)
            val vec = Vector3.fromEntity(e).subtract(last)
            if (vec.mag() < 10)
                return vec
        }

        return Vector3(e.motionX, e.motionY, e.motionZ)
    }

    @SubscribeEvent
    fun bifrostPlatform(e: LivingEvent.LivingUpdateEvent) {
        val player = e.entityLiving
        val world = player.world
        if (world.isRemote) return
        if (player is EntityPlayer) {
            if ((player.heldItemMainhand.item == ModItems.rainbowRod ?: false) || (player.heldItemOffhand.item == ModItems.rainbowRod ?: false)) {
                val emblem = ItemFaithBauble.getEmblem(player, PriestlyEmblemHeimdall::class.java) ?: return
                if (ManaItemHandler.requestManaExact(emblem, player, 10, false)) {
                    val motVec = getMotionVec(player)
                    val pos = BlockPos(player.posX + motVec.x, Math.floor(player.posY + if (player.isSneaking) -2.99 else -.99), player.posZ + motVec.z)
                    if (pos.y < 0 || pos.y >= 256) return
                    val state = world.getBlockState(pos)
                    val block = state.block
                    if (block.isAir(state, world, pos) || block.isReplaceable(world, pos) || block is IFluidBlock) {
                        world.setBlockState(pos, ModBlocks.bifrost.defaultState)
                        val tileBifrost = world.getTileEntity(pos) as TileBifrost
                        tileBifrost.ticks = 5
                        player.fallDistance = 0f
                        ManaItemHandler.requestManaExact(emblem, player, 10, true)
                    } else if (block == ModBlocks.bifrost) {
                        val tileBifrost = world.getTileEntity(pos) as TileBifrost
                        if (tileBifrost.ticks < 2) {
                            tileBifrost.ticks = 5
                            ManaItemHandler.requestManaExact(emblem, player, 10, true)
                        }
                    }
                }
            }
        }
    }
}

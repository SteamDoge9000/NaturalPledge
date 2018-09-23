package com.wiresegal.naturalpledge.common.items.colored

import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import com.wiresegal.naturalpledge.common.items.base.ItemRainbow
import vazkii.botania.api.BotaniaAPI

/**
 * @author WireSegal
 * Created at 10:20 AM on 5/21/16.
 */
class ItemAwakenedDye(name: String) : ItemRainbow(name, true) {
    @SideOnly(Side.CLIENT)
    override fun hasEffect(stack: ItemStack): Boolean {
        return true
    }

    override fun getRarity(stack: ItemStack?): EnumRarity {
        return BotaniaAPI.rarityRelic
    }

    override fun mapOreDict(keys: Array<String>): ItemAwakenedDye {
        super.mapOreDict(keys)
        return this
    }

    override fun mapOreKey(key: String): ItemAwakenedDye {
        super.mapOreKey(key)
        return this
    }
}

package com.wiresegal.naturalpledge.common.items.base

import com.teamwizardry.librarianlib.features.base.item.IItemColorProvider
import com.teamwizardry.librarianlib.features.base.item.ItemMod
import net.minecraft.block.material.MapColor
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import com.wiresegal.naturalpledge.common.NaturalPledge
import com.wiresegal.naturalpledge.common.lib.LibOreDict

/**
 * @author WireSegal
 * Created at 9:27 AM on 4/29/16.
 */
open class ItemRainbow(name: String, val rainbow: Boolean) : ItemMod(name, *Array(16 + if (rainbow) 1 else 0, { name + LibOreDict.COLORS[it] })), IItemColorProvider {
    val types = 16 + if (rainbow) 1 else 0

    open fun mapOreDict(keys: Array<String>): ItemRainbow {
        if (keys.size < types) return this
        for (i in 0 until types)
            OreDictionary.registerOre(keys[i], ItemStack(this, 1, i))
        return this
    }

    open fun mapOreKey(key: String): ItemRainbow {
        for (variant in variants.indices)
            OreDictionary.registerOre(key, ItemStack(this, 1, variant))
        return this
    }

    override val itemColorFunction: ((ItemStack, Int) -> Int)?
        get() = { itemStack, i ->
            if (i == 0) when (itemStack.metadata) {
                16 -> NaturalPledge.PROXY.rainbow().rgb
                else -> MapColor.getBlockColor(EnumDyeColor.byMetadata(itemStack.metadata)).colorValue
            } else 0xFFFFFF
        }
}

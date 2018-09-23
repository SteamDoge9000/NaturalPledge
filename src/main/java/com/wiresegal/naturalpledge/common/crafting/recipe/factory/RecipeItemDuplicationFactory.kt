package com.wiresegal.naturalpledge.common.crafting.recipe.factory

import com.teamwizardry.librarianlib.core.common.RegistrationHandler
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import com.wiresegal.naturalpledge.common.crafting.recipe.RecipeItemDuplication

class RecipeItemDuplicationFactory {
    fun make(name: ResourceLocation, group: ResourceLocation?, output: Item, vararg params: ItemStack): RecipeItemDuplication {
        val lst = NonNullList.create<Ingredient>()
        lst.add(Ingredient.fromItem(output))
        for (i in params)
            lst.add(Ingredient.fromStacks(i))
        val recipe = RecipeItemDuplication(group?.toString() ?: "", ItemStack(output), lst)
        recipe.registryName = name
        RegistrationHandler.register(recipe)
        return recipe

    }
}

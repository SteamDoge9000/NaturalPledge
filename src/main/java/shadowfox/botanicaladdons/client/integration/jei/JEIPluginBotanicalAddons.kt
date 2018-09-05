package shadowfox.botanicaladdons.client.integration.jei

import com.teamwizardry.librarianlib.features.kotlin.nbt
import com.teamwizardry.librarianlib.features.network.PacketHandler
import mezz.jei.api.*
import mezz.jei.api.ingredients.IIngredientRegistry
import mezz.jei.api.ingredients.IModIngredientRegistration
import mezz.jei.gui.overlay.IngredientListOverlay
import mezz.jei.plugins.vanilla.crafting.ShapedOreRecipeWrapper
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import shadowfox.botanicaladdons.api.priest.SpellRecipe
import shadowfox.botanicaladdons.client.integration.jei.spellcrafting.SpellCraftingCategory
import shadowfox.botanicaladdons.client.integration.jei.spellcrafting.SpellCraftingRecipeHandler
import shadowfox.botanicaladdons.client.integration.jei.spellcrafting.SpellCraftingRecipeJEI
import shadowfox.botanicaladdons.client.integration.jei.spellcrafting.SpellCraftingRecipeMaker
import shadowfox.botanicaladdons.client.integration.jei.treegrowing.TreeGrowingCategory
import shadowfox.botanicaladdons.client.integration.jei.treegrowing.TreeGrowingRecipeHandler
import shadowfox.botanicaladdons.client.integration.jei.treegrowing.TreeGrowingRecipeMaker
import shadowfox.botanicaladdons.common.block.ModBlocks
import shadowfox.botanicaladdons.common.core.helper.RainbowItemHelper
import shadowfox.botanicaladdons.common.crafting.ModRecipes
import shadowfox.botanicaladdons.common.items.ItemResource
import shadowfox.botanicaladdons.common.items.ModItems
import shadowfox.botanicaladdons.common.items.bauble.faith.ItemRagnarokPendant
import shadowfox.botanicaladdons.common.items.bauble.faith.Spells
import shadowfox.botanicaladdons.common.network.UpdateRagnarokJEIMessage
import vazkii.botania.common.item.ModItems as BotaniaItems

/**
 * @author WireSegal
 * Created at 9:16 AM on 5/22/16.
 */
@JEIPlugin
class JEIPluginBotanicalAddons : IModPlugin {

    companion object {
        var initialized = false
        lateinit var helpers: IJeiHelpers
        lateinit var runtime: IJeiRuntime
        val RAGNAROK_ITEMS by lazy {
            arrayOf(ItemResource.of(ItemResource.Variants.GOD_SOUL), ItemResource.of(ItemResource.Variants.GOD_SOUL, true),
                    ItemStack(ModItems.ragnarok),
                    ItemStack(ModItems.eclipseHelm),
                    ItemStack(ModItems.eclipseChest),
                    ItemStack(ModItems.eclipseLegs),
                    ItemStack(ModItems.eclipseBoots),
                    ItemStack(ModItems.flarebringer),
                    ItemStack(ModItems.sunmakerHelm),
                    ItemStack(ModItems.sunmakerChest),
                    ItemStack(ModItems.sunmakerLegs),
                    ItemStack(ModItems.sunmakerBoots),
                    ItemStack(ModItems.shadowbreaker),
                    ItemStack(ModItems.fenrisHelm),
                    ItemStack(ModItems.fenrisChest),
                    ItemStack(ModItems.fenrisLegs),
                    ItemStack(ModItems.fenrisBoots),
                    ItemStack(ModItems.nightscourge))
        }
        val RAGNAROK_RECIPES by lazy {
            arrayOf(ModRecipes.recipeEclipseHelm,
                    ModRecipes.recipeEclipseChest,
                    ModRecipes.recipeEclipseLegs,
                    ModRecipes.recipeEclipseBoots,
                    ModRecipes.recipeEclipseWeapon,
                    ModRecipes.recipeSunmakerHelm,
                    ModRecipes.recipeSunmakerChest,
                    ModRecipes.recipeSunmakerLegs,
                    ModRecipes.recipeSunmakerBoots,
                    ModRecipes.recipeSunmakerWeapon,
                    ModRecipes.recipeFenrisHelm,
                    ModRecipes.recipeFenrisChest,
                    ModRecipes.recipeFenrisLegs,
                    ModRecipes.recipeFenrisBoots,
                    ModRecipes.recipeFenrisWeapon,
                    SpellCraftingRecipeJEI(SpellRecipe("netherStar", Spells.ObjectInfusion.UltimateInfusion,
                            ItemResource.of(ItemResource.Variants.GOD_SOUL), ItemResource.of(ItemResource.Variants.GOD_SOUL, true))))
        }
    }

    init {
        MinecraftForge.EVENT_BUS.register(this)

        UpdateRagnarokJEIMessage.add = {
            RAGNAROK_ITEMS.forEach { helpers.ingredientBlacklist.removeIngredientFromBlacklist(it) }
            RAGNAROK_RECIPES.forEach { runtime.recipeRegistry.addRecipe(it) }

            val overlay = runtime.ingredientListOverlay
            if (overlay is IngredientListOverlay)
                overlay.rebuildItemFilter()
        }

        UpdateRagnarokJEIMessage.remove = {
            RAGNAROK_ITEMS.forEach { helpers.ingredientBlacklist.addIngredientToBlacklist(it) }
            RAGNAROK_RECIPES.forEach { runtime.recipeRegistry.removeRecipe(it) }

            val overlay = runtime.ingredientListOverlay
            if (overlay is IngredientListOverlay)
                overlay.rebuildItemFilter()
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun onWorldJoin(e: EntityJoinWorldEvent) {
        val entity = e.entity
        if (!entity.world.isRemote && entity is EntityPlayerMP)
            PacketHandler.NETWORK.sendTo(UpdateRagnarokJEIMessage(), entity)

    }

//    @SubscribeEvent
//    fun onAchievement(e: AdvancementEvent) {
//        val entity = e.entity
//        if (!entity.world.isRemote && entity is EntityPlayerMP)
//            PacketHandler.NETWORK.sendTo(UpdateRagnarokJEIMessage(), entity)
//    }

    override fun register(registry: IModRegistry) {
        initialized = true
        helpers = registry.jeiHelpers

        UpdateRagnarokJEIMessage.lastState = false
        RAGNAROK_ITEMS.forEach { helpers.ingredientBlacklist.addIngredientToBlacklist(it) }

        registry.addRecipeCategories(SpellCraftingCategory, TreeGrowingCategory)
        registry.addRecipeHandlers(SpellCraftingRecipeHandler, TreeGrowingRecipeHandler)

        registry.addRecipes(SpellCraftingRecipeMaker.recipes)
        registry.addRecipes(TreeGrowingRecipeMaker.recipes)

        registry.addRecipeCategoryCraftingItem(ItemStack(ModItems.spellFocus), SpellCraftingCategory.uid)
        registry.addRecipeCategoryCraftingItem(ItemStack(ModBlocks.irisSapling), TreeGrowingCategory.uid)

        // Botania

        registry.jeiHelpers.ingredientBlacklist.addIngredientToBlacklist(ItemStack(BotaniaItems.manaResource, 1, 10)) // Prismarine
    }

    override fun onRuntimeAvailable(jeiRuntime: IJeiRuntime) {
        runtime = jeiRuntime
        UpdateRagnarokJEIMessage.handle()
    }

    override fun registerItemSubtypes(subtypeRegistry: ISubtypeRegistry) {
        subtypeRegistry.registerSubtypeInterpreter(ModBlocks.star.itemForm) {
            RainbowItemHelper.getColor(it).toString()
        }

        subtypeRegistry.registerSubtypeInterpreter(ModBlocks.cracklingStar.itemForm) {
            RainbowItemHelper.getColor(it).toString()
        }

        // Botania

        subtypeRegistry.registerSubtypeInterpreter(BotaniaItems.twigWand) {
            it.nbt["color1"].toString() + it.nbt["color2"].toString()
        }

        subtypeRegistry.registerSubtypeInterpreter(BotaniaItems.brewVial) {
            it.nbt["brewKey"].toString()
        }

        subtypeRegistry.registerSubtypeInterpreter(BotaniaItems.brewFlask) {
            it.nbt["brewKey"].toString()
        }

        subtypeRegistry.registerSubtypeInterpreter(BotaniaItems.incenseStick) {
            it.nbt["brewKey"].toString()
        }
    }

    override fun registerIngredients(registry: IModIngredientRegistration) {
        // NO-OP
    }
}

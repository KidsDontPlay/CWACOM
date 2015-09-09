package mrriegel.cwacom.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingRecipes {

	public static void init() {
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.fldsmdfr), "ifi",
				"ars", "ici", 'i', Items.iron_ingot, 'f', Items.cooked_fished,
				'a', Items.golden_apple, 'r', Items.bucket, 's',
				Items.mushroom_stew, 'c', Items.pumpkin_pie);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.terminal), "ifi",
				"ars", "ici", 'i', Items.iron_ingot, 'f',
				Items.poisonous_potato, 'a', Items.melon, 'r',
				Blocks.redstone_block, 's', Items.cake, 'c', Items.cookie);
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.rc), "g", "e", "i",
				'i', Items.iron_ingot, 'e', Items.ender_pearl, 'g',
				Items.gold_nugget);
	}

}

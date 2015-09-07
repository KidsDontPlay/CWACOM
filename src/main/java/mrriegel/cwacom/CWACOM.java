package mrriegel.cwacom;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import mrriegel.cwacom.config.ConfigurationHandler;
import mrriegel.cwacom.init.CraftingRecipes;
import mrriegel.cwacom.init.ModBlocks;
import mrriegel.cwacom.init.ModItems;
import mrriegel.cwacom.proxy.CommonProxy;
import mrriegel.cwacom.reference.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameData;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class CWACOM {

	@Mod.Instance(Reference.MOD_ID)
	public static CWACOM instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public static SimpleNetworkWrapper net;

	public static Vector<ItemFood> foodList;

	private static int modGuiIndex = 0;
	public static final int ItemInventoryGuiIndex = modGuiIndex++;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		File configFile = event.getSuggestedConfigurationFile();
		ConfigurationHandler.config = new Configuration(configFile);
		ConfigurationHandler.config.load();
		ConfigurationHandler.refreshConfig();

		ModBlocks.init();
		ModItems.init();

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		CraftingRecipes.init();

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		foodList = new Vector<ItemFood>();
		Iterator<Item> f = GameData.getItemRegistry().iterator();
		while (f.hasNext()) {
			Item i = f.next();
			if (i instanceof ItemFood)
				foodList.add((ItemFood) i);
		}
	}
}

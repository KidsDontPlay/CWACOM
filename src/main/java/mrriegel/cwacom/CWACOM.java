package mrriegel.cwacom;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import mrriegel.cwacom.config.ConfigurationHandler;
import mrriegel.cwacom.init.CraftingRecipes;
import mrriegel.cwacom.init.ModBlocks;
import mrriegel.cwacom.init.ModItems;
import mrriegel.cwacom.packet.PacketHandler;
import mrriegel.cwacom.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameData;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class CWACOM {

	@Mod.Instance(Reference.MOD_ID)
	public static CWACOM instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public static ArrayList<ItemStack> foodList;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		File configFile = event.getSuggestedConfigurationFile();
		ConfigurationHandler.config = new Configuration(configFile);
		ConfigurationHandler.config.load();
		ConfigurationHandler.refreshConfig();

		ModBlocks.init();
		ModItems.init();
		PacketHandler.init();
		proxy.registerRenderers();

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
		CraftingRecipes.init();

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		foodList = new ArrayList<ItemStack>();
		Iterator<Item> f = GameData.getItemRegistry().iterator();
		Item x = new Item();
		foodList.add(new ItemStack(x));
		while (f.hasNext()) {
			Item i = f.next();
			if (i instanceof ItemFood) {
				if (i.equals(Items.golden_apple) && !ConfigurationHandler.apple)
					continue;
				// if (i.getHasSubtypes() && event.getSide().isClient()) {
				// i.getSubItems(i, null, foodList);
				// } else
				if (i.getHasSubtypes()) {
					String name = null;
					ArrayList<ItemStack>lis=new ArrayList<ItemStack>();
					for (int in = 0; in < 16; in++) {
						
						lis.add(new ItemStack(i,1,in));
						ItemStack ss = new ItemStack(i, 1, in);
//						if (in == 0)
//							name = ss.getUnlocalizedName();
//						System.out.println("name: " + in + " " + name + " "
//								+ "unlo: " + ss.getUnlocalizedName());
//						if (in != 0 && name.equals(ss.getUnlocalizedName()))
//							break;
						foodList.add(ss);
						// System.out.println("wack: " + in + " "
						// + ss.getUnlocalizedName());
						
					}for(int z=0;z<lis.size();z++)
					System.out.println("hoo: "+i+lis.get(z).getUnlocalizedName());
				} else
					foodList.add(new ItemStack(i));
			}
		}
		System.out.println("ffsize: " + foodList.size());

	}
}

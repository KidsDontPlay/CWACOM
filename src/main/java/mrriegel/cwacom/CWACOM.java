package mrriegel.cwacom;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import mrriegel.cwacom.config.ConfigurationHandler;
import mrriegel.cwacom.init.CraftingRecipes;
import mrriegel.cwacom.init.ModBlocks;
import mrriegel.cwacom.init.ModItems;
import mrriegel.cwacom.packet.PacketHandler;
import mrriegel.cwacom.packet.TerminalPacket;
import mrriegel.cwacom.packet.TerminalPacketHandler;
import mrriegel.cwacom.proxy.CommonProxy;
import mrriegel.cwacom.reference.Reference;
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
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class CWACOM {

	@Mod.Instance(Reference.MOD_ID)
	public static CWACOM instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public static Vector<ItemStack> foodList;

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
		PacketHandler.init();

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
		CraftingRecipes.init();

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println("dd");
		foodList = new Vector<ItemStack>();
		Iterator<Item> f = GameData.getItemRegistry().iterator();
		while (f.hasNext()) {
			Item i = f.next();
			if (i instanceof ItemFood) {
				ItemFood item = (ItemFood) i;
				if (i.getHasSubtypes()) {
					i.getSubItems(i, null, foodList);
				} else
					foodList.add(new ItemStack(i));
			}
		}
	}
}

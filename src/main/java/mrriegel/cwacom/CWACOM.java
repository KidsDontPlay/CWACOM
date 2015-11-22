package mrriegel.cwacom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import mrriegel.cwacom.config.ConfigurationHandler;
import mrriegel.cwacom.init.CraftingRecipes;
import mrriegel.cwacom.init.ModBlocks;
import mrriegel.cwacom.init.ModItems;
import mrriegel.cwacom.packet.PacketHandler;
import mrriegel.cwacom.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import com.google.gson.Gson;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class CWACOM {

	@Mod.Instance(Reference.MOD_ID)
	public static CWACOM instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public ArrayList<ItemStack> foodList;
	public ArrayList<ItemStack> blackList;
	public ArrayList<ItemStack> whiteList;
	public ArrayList<String> ops;

	private File configDir;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws IOException {
		configDir = new File(event.getModConfigurationDirectory(), "CWACOM");
		File configFile = new File(configDir, "config.cfg");
		ConfigurationHandler.config = new Configuration(configFile);
		ConfigurationHandler.config.load();
		ConfigurationHandler.refreshConfig();

		File opFile = new File(configDir, "ops.json");
		if (!opFile.exists()) {
			opFile.createNewFile();
			FileWriter fw = new FileWriter(opFile);
			fw.write(new Gson().toJson(new ArrayList<String>()));
			fw.close();
		}
		ops = new Gson().fromJson(new BufferedReader(new FileReader(opFile)),
				ArrayList.class);

		ModBlocks.init();
		ModItems.init();
		PacketHandler.init();
		proxy.registerRenderers();

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) throws IOException {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
		CraftingRecipes.init();
		File blackFile = new File(configDir, "blackList.json");
		if (!blackFile.exists()) {
			blackFile.createNewFile();
			FileWriter fw = new FileWriter(blackFile);
			fw.write(new Gson().toJson(new ArrayList<String>(Arrays
					.asList(new String[] { "minecraft:golden_apple:0",
							"minecraft:golden_apple:1",
							"minecraft:poisonous_potato:0",
							"DraconicEvolution:dezilsMarshmallow:0",
							"Botania:manaCookie:0" }))));
			fw.close();
		}
		ArrayList<String> blackListS = new Gson().fromJson(new BufferedReader(
				new FileReader(blackFile)), ArrayList.class);
		blackList = new ArrayList<ItemStack>();
		for (String s : blackListS) {
			if (!s.contains(":"))
				throw new RuntimeException("blacklist not correct");
			ItemStack item = GameRegistry.findItemStack(s.split(":")[0],
					s.split(":")[1], 1);
			if (item != null) {
				item.setItemDamage(Integer.valueOf(s.split(":")[2]));
				blackList.add(item);
			}
		}

		File whiteFile = new File(configDir, "whiteList.json");
		if (!whiteFile.exists()) {
			whiteFile.createNewFile();
			FileWriter fw = new FileWriter(whiteFile);
			fw.write(new Gson().toJson(new ArrayList<String>(Arrays
					.asList(new String[] { "minecraft:cooked_beef:0",
							"minecraft:cooked_porkchop:0",
							"minecraft:cooked_chicken:0" }))));
			fw.close();
		}
		ArrayList<String> whiteListS = new Gson().fromJson(new BufferedReader(
				new FileReader(whiteFile)), ArrayList.class);
		whiteList = new ArrayList<ItemStack>();
		for (String s : whiteListS) {
			if (!s.contains(":"))
				throw new RuntimeException("whitelist not correct");
			ItemStack item = GameRegistry.findItemStack(s.split(":")[0],
					s.split(":")[1], 1);
			if (item != null) {
				item.setItemDamage(Integer.valueOf(s.split(":")[2]));
				whiteList.add(item);
			}
		}

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		foodList = new ArrayList<ItemStack>();
		Iterator<Item> f = GameData.getItemRegistry().iterator();
		
		Item x = new Item();
		foodList.add(new ItemStack(x));
		ArrayList<String> done = new ArrayList<String>();
		while (f.hasNext()) {
			Item i = f.next();
			if (i instanceof ItemFood) {
				if (i.getHasSubtypes()) {
					for (int in = 0; in < 16; in++) {
						ItemStack ss = new ItemStack(i, 1, in);
						try {
							if (!done.contains(ss.getUnlocalizedName())
									&& ss.getUnlocalizedName() != null) {
								foodList.add(ss);
								if (!(ss.getUnlocalizedName().contains(
										"appleGold") && in == 0))
									done.add(ss.getUnlocalizedName());
							}
						} catch (ArrayIndexOutOfBoundsException e) {
						}

					}
				} else
					foodList.add(new ItemStack(i));
			}
		}

		if (ConfigurationHandler.blacklist)
			for (Iterator<ItemStack> iter = foodList.listIterator(); iter
					.hasNext();) {
				if (contains(iter.next(), blackList)) {
					iter.remove();
				}
			}
		int i = 0;
		if (ConfigurationHandler.whitelist)
			for (Iterator<ItemStack> iter = foodList.listIterator(); iter
					.hasNext();) {
				if (!contains(iter.next(), whiteList) && i > 0) {
					iter.remove();
				}
				i++;
			}
	}

	private boolean contains(ItemStack ss, ArrayList<ItemStack> blackList2) {
		for (ItemStack s : blackList2)
			if (s.isItemEqual(ss))
				return true;
		return false;
	}

	public boolean isPlayerOP(EntityPlayer player) {
		return !ConfigurationHandler.ops
				|| ops.contains(player.getDisplayName());
	}
}

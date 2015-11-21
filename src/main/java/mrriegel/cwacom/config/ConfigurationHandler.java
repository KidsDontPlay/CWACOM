package mrriegel.cwacom.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

	public static Configuration config;

	public static int rfCost;
	public static int waterCost;
	public static boolean apple;
	public static int rfCapacity;
	public static int waterCapacity;

	public static void refreshConfig() {
		rfCost = config.get("Common", "rfCost", 5000,
				"Cost of RF = this * saturation").getInt();
		waterCost = config.get("Common", "waterCost", 350,
				"Cost of Water = this * saturation").getInt();
		apple = config
				.get("Common",
						"goldenApple",
						false,
						"enable golden apple (ATTENTION: keep in mind this config is equal on server and client)")
				.getBoolean();
		rfCapacity = config.get("Common", "rfCapacity", 200000,
				"RF Capacity in Terminal").getInt();
		waterCapacity = config.get("Common", "waterCapacity", 16,
				"Water Capacity in Fldsmdfr (in buckets)").getInt();
		if (config.hasChanged()) {
			config.save();
		}
	}

}

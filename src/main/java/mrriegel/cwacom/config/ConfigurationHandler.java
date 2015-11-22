package mrriegel.cwacom.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

	public static Configuration config;

	public static int rfCost;
	public static int waterCost;
	public static int rfCapacity;
	public static int waterCapacity;
	public static boolean blacklist;
	public static boolean whitelist;

	public static void refreshConfig() {
		rfCost = config.get("Common", "rfCost", 5000,
				"Cost of RF = this * saturation").getInt();
		waterCost = config.get("Common", "waterCost", 350,
				"Cost of Water = this * saturation").getInt();
		rfCapacity = config.get("Common", "rfCapacity", 200000,
				"RF Capacity in Terminal").getInt();
		waterCapacity = config.get("Common", "waterCapacity", 16,
				"Water Capacity in Fldsmdfr (in buckets)").getInt();
		blacklist = config.get("Common", "blacklist", false,
				"activate blacklist").getBoolean();
		whitelist = config.get("Common", "whitelist", false,
				"activate whitelist").getBoolean();
		if (config.hasChanged()) {
			config.save();
		}
		if (blacklist && whitelist)
			throw new RuntimeException(
					"blacklist and whitelist cannot be true simultaneously");
	}

}

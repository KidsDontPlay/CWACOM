package mrriegel.cwacom.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

	public static Configuration config;

	public static int rfCost;
	public static int waterCost;
	public static boolean apple;

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

		if (config.hasChanged()) {
			config.save();
		}
	}

}

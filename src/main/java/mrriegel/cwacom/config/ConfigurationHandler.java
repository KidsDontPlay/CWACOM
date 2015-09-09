package mrriegel.cwacom.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

	public static Configuration config;
	
	public static int amplifier;
	public static int rfCost;
	public static int waterCost;

	public static void refreshConfig() {
		rfCost=config.get("Common","rfCost",5000,"Cost of RF = this * saturation").getInt();
		waterCost=config.get("Common","waterCost",500,"Cost of Water = this * saturation").getInt();
		amplifier=config.get("Common","amplifier",6000,"The lower the amplifier the more food.").getInt();

	}

}

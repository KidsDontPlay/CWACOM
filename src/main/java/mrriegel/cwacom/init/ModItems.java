package mrriegel.cwacom.init;

import mrriegel.cwacom.item.ItemRC;
import mrriegel.cwacom.reference.Reference;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
	public static final Item rc = new ItemRC();

	public static void init() {
		GameRegistry.registerItem(rc, "rc");
	}

}

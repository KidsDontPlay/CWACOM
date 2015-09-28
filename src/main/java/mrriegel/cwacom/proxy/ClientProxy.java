package mrriegel.cwacom.proxy;

import mrriegel.cwacom.init.ModBlocks;
import mrriegel.cwacom.render.FldsmdfrItemRenderer;
import mrriegel.cwacom.render.FldsmdfrRenderer;
import mrriegel.cwacom.tile.TileFldsmdfr;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileFldsmdfr.class,
				new FldsmdfrRenderer());
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(ModBlocks.fldsmdfr),
				new FldsmdfrItemRenderer());
	}

}

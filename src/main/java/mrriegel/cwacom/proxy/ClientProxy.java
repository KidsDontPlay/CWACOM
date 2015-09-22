package mrriegel.cwacom.proxy;

import mrriegel.cwacom.render.FldsmdfrRenderer;
import mrriegel.cwacom.tile.TileFldsmdfr;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileFldsmdfr.class,
				new FldsmdfrRenderer());
	}

}

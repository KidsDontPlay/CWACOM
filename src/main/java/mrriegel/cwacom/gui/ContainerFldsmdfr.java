package mrriegel.cwacom.gui;

import mrriegel.cwacom.tile.TileFldsmdfr;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerFldsmdfr extends Container {

	TileFldsmdfr tile;

	public ContainerFldsmdfr(EntityPlayer player, InventoryPlayer inventory,
			TileFldsmdfr tile) {
		this.tile = tile;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}

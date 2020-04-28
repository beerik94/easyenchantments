package nl.beerik.easyenchantments.container;

import java.util.Objects;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import nl.beerik.easyenchantments.init.EEContainerTypes;
import nl.beerik.easyenchantments.tile.DisenchanterTileEntity;

public class DisenchanterContainer extends Container {

public final DisenchanterTileEntity tile;
	
	public DisenchanterContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}
	
	public DisenchanterContainer(final int windowId, final PlayerInventory playerInventory, final DisenchanterTileEntity tile) {
		super(EEContainerTypes.DISENCHANTER.get(), windowId);
		this.tile = tile;
		
		final int slotSizePlus2 = 18;		
		final int playerInventoryStartX = 8;
		final int playerInventoryStartY = 174;
		
		// Player Top Inventory slots
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2), playerInventoryStartY + (row * slotSizePlus2)));
			}
		}

		final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
		// Player Hotbar slots
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
		}
	}

	private static DisenchanterTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
		Objects.requireNonNull(data, "data cannot be null!");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof DisenchanterTileEntity)
			return (DisenchanterTileEntity) tileAtPos;
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		if (tile != null) {
			return tile.getWorld().getTileEntity(tile.getPos()) == tile;
		}
		
		return true;
	}
}

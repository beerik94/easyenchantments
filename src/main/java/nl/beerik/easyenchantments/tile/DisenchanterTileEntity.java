package nl.beerik.easyenchantments.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import nl.beerik.easyenchantments.container.DisenchanterContainer;
import nl.beerik.easyenchantments.init.EEBlocks;
import nl.beerik.easyenchantments.init.EETileEntityTypes;

public class DisenchanterTileEntity extends TileEntity implements INamedContainerProvider {
	
	private static final String NBT_INVENTORY = "inventory";
	public final ItemStackHandler inventory = new ItemStackHandler();
	
	// Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
	private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
	
	public DisenchanterTileEntity() {
		super(EETileEntityTypes.DISENCHANTER.get());
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (side == null) {
				return inventoryCapabilityExternal.cast();
			}
		}
		return super.getCapability(cap, side);
	}
	
	/**
	 * Read saved data from disk into the tile.
	 */
	@Override
	public void read(final CompoundNBT compound) {
		super.read(compound);
		this.inventory.deserializeNBT(compound.getCompound(NBT_INVENTORY));
	}

	/**
	 * Write data from the tile into a compound tag for saving to disk.
	 */
	@Nonnull
	@Override
	public CompoundNBT write(final CompoundNBT compound) {
		super.write(compound);
		compound.put(NBT_INVENTORY, this.inventory.serializeNBT());
		return compound;
	}

	@Nonnull
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}
	
	@Override
	public void remove() {
		super.remove();
		// We need to invalidate our capability references so that any cached references (by other mods) don't
		// continue to reference our capabilities and try to use them and/or prevent them from being garbage collected
		inventoryCapabilityExternal.invalidate();
	}

	@Nonnull
	@Override
	public Container createMenu(final int windowId, final PlayerInventory inventory, final PlayerEntity player) {
		return new DisenchanterContainer(windowId, inventory, this);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(EEBlocks.DISENCHANTER.get().getTranslationKey());
	}

}

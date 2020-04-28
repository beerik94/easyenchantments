package nl.beerik.easyenchantments.tile;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
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
	
	public static final int BOOK_SLOT = 0;
	public static final int INPUT_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	
	private static final String NBT_INVENTORY = "inventory";
	
	public final ItemStackHandler inventory = new ItemStackHandler(3) {
		@Override
		public boolean isItemValid(final int slot, @Nonnull final ItemStack stack) {
			switch (slot) {
			case BOOK_SLOT:
				return isBook(stack);
			case INPUT_SLOT:
				return isInput(stack);
			case OUTPUT_SLOT:
				return isOutput(stack);
				default:
					return false;
			}
		}
		
		@Override
		protected void onContentsChanged(final int slot) {
			super.onContentsChanged(slot);
			DisenchanterTileEntity.this.markDirty();
		}
	};
	
	// Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
	private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
	
	public DisenchanterTileEntity() {
		super(EETileEntityTypes.DISENCHANTER.get());
	}
	
	/**
	 * @return If the stack is not empty and has a smelting recipe associated with it
	 */
	private boolean isBook(final ItemStack stack) {
		if (stack.isEmpty()) {
			return false;			
		}
		return true;//getRecipe(stack).isPresent();
	}
	
	/**
	 * @return If the stack is not empty and has a smelting recipe associated with it
	 */
	private boolean isInput(final ItemStack stack) {
		if (stack.isEmpty()) {
			return false;			
		}
		return true;//getRecipe(stack).isPresent();
	}

	/**
	 * @return If the stack's item is equal to the result of smelting our input
	 */
	private boolean isOutput(final ItemStack stack) {
		final Optional<ItemStack> result = getResult(inventory.getStackInSlot(INPUT_SLOT));
		return result.isPresent() && ItemStack.areItemsEqual(result.get(), stack);
	}
	
	/**
	 * @return The result of smelting the input stack
	 */
	private Optional<ItemStack> getResult(final ItemStack input) {
		// Due to vanilla's code we need to pass an IInventory into RecipeManager#getRecipe and
		// AbstractCookingRecipe#getCraftingResult() so we make one here.
		//final Inventory dummyInventory = new Inventory(input);
		//return getRecipe(dummyInventory).map(recipe -> recipe.getCraftingResult(dummyInventory));
		return null;
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

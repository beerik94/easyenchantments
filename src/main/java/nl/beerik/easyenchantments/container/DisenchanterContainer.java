package nl.beerik.easyenchantments.container;

import java.util.Map;
import java.util.Objects;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import nl.beerik.easyenchantments.init.EEContainerTypes;
import nl.beerik.easyenchantments.tile.DisenchanterTileEntity;

public class DisenchanterContainer extends Container {

	public final DisenchanterTileEntity tile;
	
	private final IInventory outputBookInventory = new CraftResultInventory();
	private final IInventory outputInventory = new Inventory(1);
	private final IInventory inputInventory = new Inventory(2) {
		public void markDirty() {
			super.markDirty();
			DisenchanterContainer.this.onCraftMatrixChanged(this);
		}
	};
	
	public DisenchanterContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}
	
	public DisenchanterContainer(final int windowId, final PlayerInventory playerInventory, final DisenchanterTileEntity tile) {
		super(EEContainerTypes.DISENCHANTER.get(), windowId);
		this.tile = tile;
		
		this.addSlot(new Slot(this.inputInventory, 0, 35, 22) {
			public boolean isItemValid(ItemStack stack) {
				return stack.isEnchanted();
			}
		});
		this.addSlot(new Slot(this.inputInventory, 1, 57, 22) {
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.BOOK;
			}
		});
		this.addSlot(new Slot(this.outputInventory, 2, 103, 22) {
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		this.addSlot(new Slot(this.outputBookInventory, 3, 125, 22) {
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
			
			public ItemStack onTake(PlayerEntity player, ItemStack stack) {
				ItemStack input0 = DisenchanterContainer.this.inputInventory.getStackInSlot(0);
				ItemStack input1 = DisenchanterContainer.this.inputInventory.getStackInSlot(1);
				ItemStack newInput0;
				
				newInput0 = DisenchanterContainer.this.updateEnchantments(input0);
				if (newInput0.isEnchanted()) {
					System.out.println("WeaponIsStillEnchanted");
					DisenchanterContainer.this.inputInventory.setInventorySlotContents(0, newInput0);
				}
				else {
					System.out.println("WeaponIsNOTEnchanted");
					DisenchanterContainer.this.outputInventory.setInventorySlotContents(0, newInput0);
					//DisenchanterContainer.this.inputInventory.setInventorySlotContents(0, ItemStack.EMPTY);
				}		
				
				int stackCount = input1.getCount();
				if (stackCount > 1) {
					input1.setCount(stackCount - 1);
				}
				else {
					input1 = ItemStack.EMPTY;
				}
				DisenchanterContainer.this.inputInventory.setInventorySlotContents(1, input1);
				return stack;
			}
		});
		
		
		final int slotSizePlus2 = 18;
		final int playerInventoryStartX = 8;
		final int playerInventoryStartY = 58;
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

	public void onCraftMatrixChanged(IInventory inventoryIn) {
		super.onCraftMatrixChanged(inventoryIn);
		if (inventoryIn == this.inputInventory) {
			this.updateRecipeOutput();
		}
	}
	
	private void updateRecipeOutput() {
		ItemStack inputstack = this.inputInventory.getStackInSlot(0);
		ItemStack bookstack = this.inputInventory.getStackInSlot(1);
		ItemStack outputstack = new ItemStack(Items.ENCHANTED_BOOK);
		
		if (inputstack.isEmpty() || bookstack.isEmpty()) {
			this.outputBookInventory.setInventorySlotContents(0, ItemStack.EMPTY);
		}
		else {
			if ((inputstack.isEnchanted() && inputstack.getItem() == Items.ENCHANTED_BOOK) || !inputstack.isEnchanted()) {
				this.outputBookInventory.setInventorySlotContents(0, ItemStack.EMPTY);
				this.detectAndSendChanges();
				return;
			}
			
			if (!inputstack.isEmpty() && !bookstack.isEmpty()) {
				Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(inputstack);
				Map.Entry<Enchantment, Integer> entry = map.entrySet().iterator().next();
				if (EnchantmentHelper.getEnchantmentLevel(entry.getKey(), outputstack) == 0) {
					EnchantedBookItem.addEnchantment(outputstack, new EnchantmentData(entry.getKey(), entry.getValue()));
				}
				
				if (!ItemStack.areItemsEqual(this.outputBookInventory.getStackInSlot(0), outputstack)) {
					this.outputBookInventory.setInventorySlotContents(0, outputstack);
				}
				
			}
			else {
				this.outputBookInventory.setInventorySlotContents(0, ItemStack.EMPTY);
			}
		}
		
		this.detectAndSendChanges();
	}
	
	private ItemStack updateEnchantments(ItemStack inputStack) {
		ItemStack outputStack = inputStack;
		Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(outputStack);
		Map.Entry<Enchantment, Integer> entry = map.entrySet().iterator().next();
		map.remove(entry.getKey());
		outputStack.setRepairCost(0);
		if (!map.isEmpty()) {
			EnchantmentHelper.setEnchantments(map, outputStack);
			for(int i = 0; i < map.size(); ++i) {
				outputStack.setRepairCost(RepairContainer.getNewRepairCost(outputStack.getRepairCost()));
			}
		}
		else {
			outputStack.removeChildTag("Enchantments");
			outputStack.removeChildTag("StoredEnchantments");
		}
		
		return outputStack;
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

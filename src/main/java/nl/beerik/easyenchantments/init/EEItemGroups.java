package nl.beerik.easyenchantments.init;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class EEItemGroups {
	
	//public static final ItemGroup EE_ITEM_GROUP = new ESItemGroup(EasyEnchantments.MODID, () -> new ItemStack(EEBlocks.STORAGE_CONTROLLER.get()));
	
	public static final class EEItemGroup extends ItemGroup {
		
		@Nonnull
		private final Supplier<ItemStack> iconSupplier;
		
		public EEItemGroup(@Nonnull final String name, @Nonnull final Supplier<ItemStack> iconSupplier) {
			super(name);
			this.iconSupplier = iconSupplier;
		}
		
		@Override
		@Nonnull
		public ItemStack createIcon() {
			return iconSupplier.get();
		}
	}
}

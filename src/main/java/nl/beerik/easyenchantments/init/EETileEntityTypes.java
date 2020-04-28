package nl.beerik.easyenchantments.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nl.beerik.easyenchantments.EasyEnchantments;
import nl.beerik.easyenchantments.tile.DisenchanterTileEntity;
import nl.beerik.easyenchantments.tile.EnchanterTileEntity;

public class EETileEntityTypes {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, EasyEnchantments.MODID);
	
	public static final RegistryObject<TileEntityType<EnchanterTileEntity>> ENCHANTER = TILE_ENTITY_TYPES.register(
			"enchanter", () -> TileEntityType.Builder.create(EnchanterTileEntity::new, EEBlocks.ENCHANTER.get()).build(null));
	
	public static final RegistryObject<TileEntityType<DisenchanterTileEntity>> DISENCHANTER = TILE_ENTITY_TYPES.register(
			"disenchanter", () -> TileEntityType.Builder.create(DisenchanterTileEntity::new, EEBlocks.ENCHANTER.get()).build(null));
}

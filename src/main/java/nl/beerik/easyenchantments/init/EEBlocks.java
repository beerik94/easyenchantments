package nl.beerik.easyenchantments.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nl.beerik.easyenchantments.EasyEnchantments;
import nl.beerik.easyenchantments.block.DisenchanterBlock;
import nl.beerik.easyenchantments.block.EnchanterBlock;

public class EEBlocks {

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, EasyEnchantments.MODID);
	
	public static final RegistryObject<Block> ENCHANTER = BLOCKS.register(
			"enchanter", () -> new EnchanterBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5f)));
	public static final RegistryObject<Block> DISENCHANTER = BLOCKS.register(
			"disenchanter", () -> new DisenchanterBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5f)));
}

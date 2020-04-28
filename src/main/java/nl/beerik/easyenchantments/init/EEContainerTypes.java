package nl.beerik.easyenchantments.init;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nl.beerik.easyenchantments.EasyEnchantments;
import nl.beerik.easyenchantments.container.DisenchanterContainer;
import nl.beerik.easyenchantments.container.EnchanterContainer;

public class EEContainerTypes {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, EasyEnchantments.MODID);
	
	public static final RegistryObject<ContainerType<EnchanterContainer>> ENCHANTER = CONTAINER_TYPES.register(
			"enchanter", () -> IForgeContainerType.create(EnchanterContainer::new));
	public static final RegistryObject<ContainerType<DisenchanterContainer>> DISENCHANTER = CONTAINER_TYPES.register(
			"disenchanter", () -> IForgeContainerType.create(DisenchanterContainer::new));
}

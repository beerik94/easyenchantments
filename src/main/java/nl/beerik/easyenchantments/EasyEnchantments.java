package nl.beerik.easyenchantments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import nl.beerik.easyenchantments.config.ConfigHolder;
import nl.beerik.easyenchantments.init.EEBlocks;
import nl.beerik.easyenchantments.init.EEContainerTypes;
import nl.beerik.easyenchantments.init.EEItems;
import nl.beerik.easyenchantments.init.EETileEntityTypes;

@Mod(EasyEnchantments.MODID)
public final class EasyEnchantments {

	public static final String MODID = "easyenchantments";
	
	public static final Logger LOGGER = LogManager.getLogger(EasyEnchantments.MODID);
	
	public EasyEnchantments() {
		LOGGER.debug("EE: Constructor!");
		
		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		final IEventBus modEventbus = FMLJavaModLoadingContext.get().getModEventBus();
		
		modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);
		
		EEBlocks.BLOCKS.register(modEventbus);
		EEItems.ITEMS.register(modEventbus);
		EEContainerTypes.CONTAINER_TYPES.register(modEventbus);
		EETileEntityTypes.TILE_ENTITY_TYPES.register(modEventbus);
	}
}
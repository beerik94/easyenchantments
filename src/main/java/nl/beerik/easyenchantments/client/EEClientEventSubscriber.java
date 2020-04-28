package nl.beerik.easyenchantments.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import nl.beerik.easyenchantments.EasyEnchantments;
import nl.beerik.easyenchantments.client.gui.screen.DisenchanterScreen;
import nl.beerik.easyenchantments.client.gui.screen.EnchanterScreen;
import nl.beerik.easyenchantments.client.renderer.tileentity.DisenchanterTileEntityRenderer;
import nl.beerik.easyenchantments.client.renderer.tileentity.EnchanterTileEntityRenderer;
import nl.beerik.easyenchantments.init.EEContainerTypes;
import nl.beerik.easyenchantments.init.EETileEntityTypes;

@SuppressWarnings("deprecation")
@EventBusSubscriber(modid = EasyEnchantments.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EEClientEventSubscriber {

	private static final Logger LOGGER = LogManager.getLogger(EasyEnchantments.MODID + " Client Mod Event Subscriber");

	@SubscribeEvent
	public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {

		// Register TileEntity Renderers
		ClientRegistry.bindTileEntityRenderer(EETileEntityTypes.ENCHANTER.get(), EnchanterTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(EETileEntityTypes.DISENCHANTER.get(), DisenchanterTileEntityRenderer::new);
		LOGGER.debug("Registered TileEntity Renderers");

		// Register ContainerType Screens
		// ScreenManager.registerFactory is not safe to call during parallel mod loading so we queue it to run later
		DeferredWorkQueue.runLater(() -> {
			ScreenManager.registerFactory(EEContainerTypes.ENCHANTER.get(), EnchanterScreen::new);
			ScreenManager.registerFactory(EEContainerTypes.DISENCHANTER.get(), DisenchanterScreen::new);
			LOGGER.debug("Registered ContainerType Screens");
		});

	}
}

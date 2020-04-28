package nl.beerik.easyenchantments.config;

import net.minecraftforge.fml.config.ModConfig;

public class ConfigHelper {
	public static void bakeClient(final ModConfig config) {
		EEConfig.clientBoolean = ConfigHolder.CLIENT.clientBoolean.get();
		EEConfig.clientStringList = ConfigHolder.CLIENT.clientStringList.get();
		EEConfig.clientDyeColorEnum = ConfigHolder.CLIENT.clientDyeColorEnum.get();

		EEConfig.modelTranslucency = ConfigHolder.CLIENT.modelTranslucency.get();
		EEConfig.modelScale = ConfigHolder.CLIENT.modelScale.get().floatValue();
	}

	public static void bakeServer(final ModConfig config) {
		EEConfig.serverBoolean = ConfigHolder.SERVER.serverBoolean.get();
		EEConfig.serverStringList = ConfigHolder.SERVER.serverStringList.get();
		EEConfig.serverEnumDyeColor = ConfigHolder.SERVER.serverEnumDyeColor.get();

		EEConfig.electricFurnaceEnergySmeltCostPerTick = ConfigHolder.SERVER.electricFurnaceEnergySmeltCostPerTick.get();
		EEConfig.heatCollectorTransferAmountPerTick = ConfigHolder.SERVER.heatCollectorTransferAmountPerTick.get();
	}
}

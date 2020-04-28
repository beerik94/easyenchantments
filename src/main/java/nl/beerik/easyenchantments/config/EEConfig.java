package nl.beerik.easyenchantments.config;

import java.util.List;

import net.minecraft.item.DyeColor;

public class EEConfig {
	//Client
		public static boolean clientBoolean;
		public static List<String> clientStringList;
		public static DyeColor clientDyeColorEnum;

		public static boolean modelTranslucency;
		public static float modelScale;

		// Server
		public static boolean serverBoolean;
		public static List<String> serverStringList;
		public static DyeColor serverEnumDyeColor;

		public static int electricFurnaceEnergySmeltCostPerTick = 100;
		public static int heatCollectorTransferAmountPerTick = 100;
}

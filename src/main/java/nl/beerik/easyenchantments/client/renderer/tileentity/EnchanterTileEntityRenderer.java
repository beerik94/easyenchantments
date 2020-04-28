package nl.beerik.easyenchantments.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import nl.beerik.easyenchantments.tile.EnchanterTileEntity;

public class EnchanterTileEntityRenderer extends TileEntityRenderer<EnchanterTileEntity> {

	public EnchanterTileEntityRenderer(final TileEntityRendererDispatcher tileRendererDispatcher) {
		super(tileRendererDispatcher);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(final EnchanterTileEntity tileEntityIn, final float partialTicks, final MatrixStack matrixStack, final IRenderTypeBuffer renderTypeBuffer, final int packedLight, final int backupPackedLight) {
		// TODO: Fix this up to actually do the rendering I want

		final BlockState renderState = Blocks.REDSTONE_TORCH.getDefaultState()
				.with(RedstoneTorchBlock.LIT, true);
		// Render the torch (We use the depreciated method because we don't have an IModelData instance and want to use the default one)
		Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(renderState, matrixStack, renderTypeBuffer, packedLight, backupPackedLight);
	}
}

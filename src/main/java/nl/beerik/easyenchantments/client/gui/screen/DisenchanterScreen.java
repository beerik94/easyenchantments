package nl.beerik.easyenchantments.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import nl.beerik.easyenchantments.EasyEnchantments;
import nl.beerik.easyenchantments.container.DisenchanterContainer;

public class DisenchanterScreen extends ContainerScreen<DisenchanterContainer> {

	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(EasyEnchantments.MODID, "textures/gui/container/disenchanter.png");
	
	public DisenchanterScreen(final DisenchanterContainer container, final PlayerInventory inventory,  final ITextComponent title) {
		super(container, inventory, title);
		this.xSize = 176;
		this.ySize = 130;
	}
	
	@Override
	public void render(final int mouseX, final int mouseY, final float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String s = this.title.getFormattedText();
		this.font.drawString(s, (float) (this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 0x404040);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		getMinecraft().getTextureManager().bindTexture(BG_TEXTURE);
		int startX = this.guiLeft;
		int startY = this.guiTop;

		// Screen#blit draws a part of the current texture (assumed to be 256x256) to the screen
		// The parameters are (x, y, u, v, width, height)

		this.blit(startX, startY, 0, 0, this.xSize, this.ySize);
	}
}

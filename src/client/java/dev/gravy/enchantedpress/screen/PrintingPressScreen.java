package dev.gravy.enchantedpress.screen;

import dev.gravy.enchantedpress.EnchantedBookPress;
import dev.gravy.enchantedpress.menu.PrintingPressMenu;
import net.fabricmc.api.EnvType;import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

@Environment(EnvType.CLIENT)
public class PrintingPressScreen extends AbstractContainerScreen<PrintingPressMenu> {
    // TODO: Link the texture for the GUI - probs on the tutorial turtywurty chap

    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(EnchantedBookPress.MOD_ID, "textures/gui/printing_press.png");
    public static final Component COST_LABEL_TEXT = Component.translatable("container." + EnchantedBookPress.MOD_ID + ".printing_press.cost_label_text");
    public final Player player;

    public PrintingPressScreen(PrintingPressMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.player = playerInventory.player;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        int cost = this.menu.getCost();
        Component costLabelText;
        if (!this.menu.getSlot(PrintingPressMenu.RESULT_SLOT).hasItem()) {
            costLabelText = null;
        } else {
            costLabelText = Component.translatable(COST_LABEL_TEXT.getString(), new Object[]{cost});
        }

        int costLabelTextColor = this.menu.getSlot(PrintingPressMenu.RESULT_SLOT).mayPickup(this.player)?
                CommonColors.GREEN : CommonColors.RED;
        if (costLabelText != null) {
            int xPos = this.imageWidth - 8 - this.font.width(costLabelText) - 2;
            int yPos = 69;
            guiGraphics.fill(xPos - 2, yPos - 2, this.imageWidth - 8, yPos + 10, CommonColors.GRAY);
            guiGraphics.drawString(this.font, costLabelText, xPos, 69, costLabelTextColor);
        }
    }


@Override
public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
    renderBackground(guiGraphics, mouseX, mouseY, delta);
    super.render(guiGraphics, mouseX, mouseY, delta);
    renderTooltip(guiGraphics, mouseX, mouseY);
}

@Override
protected void init() {
    super.init();
}
}


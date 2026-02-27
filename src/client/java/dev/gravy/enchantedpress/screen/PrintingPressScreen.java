package dev.gravy.enchantedpress.screen;

import dev.gravy.enchantedpress.EnchantedBookPress;
import dev.gravy.enchantedpress.menu.PrintingPressMenu;
import net.fabricmc.api.EnvType;import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class PrintingPressScreen extends AbstractContainerScreen<PrintingPressMenu> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(EnchantedBookPress.MOD_ID, "textures/gui/printing_press.png");
    public static final Component COST_LABEL_TEXT = Component.translatable("container." + EnchantedBookPress.MOD_ID + ".printing_press.cost_label_text");
    public final Player player;

    public PrintingPressScreen(PrintingPressMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.player = playerInventory.player;
        this.titleLabelX = 36;
        this.titleLabelY = 14;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
    }

    @Override
    protected void renderLabels(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        int xpCost = this.menu.getCost();
        Component xpCostLabelText;
        if (!this.menu.getSlot(PrintingPressMenu.RESULT_SLOT).hasItem()) {
            xpCostLabelText = null;
        } else {
            xpCostLabelText = Component.translatable(COST_LABEL_TEXT.getString(), xpCost);
        }

        int xpCostLabelTextColor = this.menu.getSlot(PrintingPressMenu.RESULT_SLOT).mayPickup(this.player)?
                CommonColors.GREEN : CommonColors.RED;
        if (xpCostLabelText != null) {
            int xPos = this.imageWidth - 8 - this.font.width(xpCostLabelText) - 2;
            int yPos = 69;
            guiGraphics.fill(xPos - 2, yPos - 2, this.imageWidth - 8, yPos + 10, CommonColors.GRAY);
            guiGraphics.drawString(this.font, xpCostLabelText, xPos, yPos, xpCostLabelTextColor);
        }

        // Draw the material cost, done like this to allow single source of truth for material cost value
        Component materialCostText = Component.literal(String.format("x%d", PrintingPressMenu.MATERIAL_REQUIREMENT));
        guiGraphics.drawString(this.font, materialCostText, 33, 59, CommonColors.GRAY, false);
    }


@Override
public void render(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
    renderBackground(guiGraphics, mouseX, mouseY, delta);
    super.render(guiGraphics, mouseX, mouseY, delta);
    renderTooltip(guiGraphics, mouseX, mouseY);
}

@Override
protected void init() {
    super.init();
}
}


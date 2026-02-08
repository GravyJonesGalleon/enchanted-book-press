package dev.gravy.enchantedpress.screen;

import dev.gravy.enchantedpress.EnchantedBookPress;
import dev.gravy.enchantedpress.menu.PrintingPressMenu;
import net.fabricmc.api.EnvType;import net.fabricmc.api.Environment;import net.minecraft.client.Minecraft;import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class PrintingPressScreen extends AbstractContainerScreen<PrintingPressMenu> {
    // TODO: Link the texture for the GUI - probs on the tutorial turtywurty chap

    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(EnchantedBookPress.MOD_ID, "textures/gui/printing_press.png");

    public PrintingPressScreen(PrintingPressMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
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

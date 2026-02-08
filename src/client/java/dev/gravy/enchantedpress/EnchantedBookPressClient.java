package dev.gravy.enchantedpress;

import dev.gravy.enchantedpress.screen.PrintingPressScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class EnchantedBookPressClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		MenuScreens.register(ModMenus.PRINTING_PRESS_MENU, PrintingPressScreen::new);
	}
}
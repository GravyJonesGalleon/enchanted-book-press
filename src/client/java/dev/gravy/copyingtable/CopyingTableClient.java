package dev.gravy.copyingtable;

import dev.gravy.copyingtable.screen.CopyingTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class CopyingTableClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		MenuScreens.register(ModMenuTypes.COPYING_TABLE, CopyingTableScreen::new);
	}
}
package dev.gravy.enchantedpress;

import dev.gravy.enchantedpress.menu.PrintingPressMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
    public static final MenuType PRINTING_PRESS_MENU = register(
            "printing_press_menu",
            new MenuType<>(PrintingPressMenu::new, FeatureFlagSet.of())
    );

    public static MenuType register(String name, MenuType<? extends AbstractContainerMenu> menuType) {
        ResourceKey<MenuType<?>> resourceKey = ResourceKey.create(Registries.MENU, Identifier.fromNamespaceAndPath(EnchantedBookPress.MOD_ID, name));

        return Registry.register(BuiltInRegistries.MENU, resourceKey, menuType);
    }

    public static void initialize() {}
}

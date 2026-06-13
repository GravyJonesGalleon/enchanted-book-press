package dev.gravy.copyingtable;

import dev.gravy.copyingtable.menu.CopyingTableMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static final MenuType COPYING_TABLE = register(
            "copying_table_menu",
            new MenuType<>(CopyingTableMenu::new, FeatureFlagSet.of())
    );

    public static MenuType register(String name, MenuType<? extends AbstractContainerMenu> menuType) {
        ResourceKey<MenuType<?>> resourceKey = ResourceKey.create(Registries.MENU, Identifier.fromNamespaceAndPath(CopyingTableInitializer.MOD_ID, name));

        return Registry.register(BuiltInRegistries.MENU, resourceKey, menuType);
    }

    public static void initialize() {}
}

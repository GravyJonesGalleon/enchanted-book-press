package dev.gravy.enchantedpress;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeBookCategory;

public class ModRecipeBookCategories {
    public static final RecipeBookCategory PRINTING = register("printing");

    private static RecipeBookCategory register(String string) {
        return Registry.register(
                BuiltInRegistries.RECIPE_BOOK_CATEGORY,
                Identifier.fromNamespaceAndPath(EnchantedBookPress.MOD_ID, string),
                new RecipeBookCategory());
    }

    public static void initialize() {}
}

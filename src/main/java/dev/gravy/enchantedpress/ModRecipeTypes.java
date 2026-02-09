package dev.gravy.enchantedpress;

import dev.gravy.enchantedpress.recipe.PrintingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final  RecipeType<PrintingRecipe> PRINTING = register("printing");

    static <T extends Recipe<?>> RecipeType<T> register(String string) {
        return Registry.register(
                BuiltInRegistries.RECIPE_TYPE,
                Identifier.fromNamespaceAndPath(EnchantedBookPress.MOD_ID, string),
                new RecipeType<T>() {
                    public String toString() {
                        return Identifier.fromNamespaceAndPath(EnchantedBookPress.MOD_ID, string).toString();
                    }
                });
    }

    public static void initialize() {}
}
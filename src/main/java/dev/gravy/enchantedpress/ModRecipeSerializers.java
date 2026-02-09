package dev.gravy.enchantedpress;


import dev.gravy.enchantedpress.recipe.PrintingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializers {
    public static final RecipeSerializer<PrintingRecipe> PRINTING = register("printing", PrintingRecipe.Serializer.INSTANCE);

    static <T extends Recipe<?>> RecipeSerializer<T> register(String string, RecipeSerializer<T> recipeSerializer) {
        return Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                Identifier.fromNamespaceAndPath(EnchantedBookPress.MOD_ID, string),
                recipeSerializer
                );
    }

    public static void initialize() {}
}
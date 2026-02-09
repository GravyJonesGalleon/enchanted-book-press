package dev.gravy.enchantedpress.data.builder;


import dev.gravy.enchantedpress.recipe.PrintingRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record PrintingRecipeBuilder(
        Ingredient enchantedBook,
        Ingredient book,
        Ingredient material,
        ItemStack output
) {
    public static PrintingRecipeBuilder create(Ingredient enchantedBook,
                                               Ingredient book,
                                               Ingredient material,
                                               ItemStack output) {
        return new PrintingRecipeBuilder(
                enchantedBook,
                book,
                material,
                output);
    }

    public void offerTo(RecipeOutput exporter, ResourceKey recipeId) {
        exporter.accept(
                recipeId,
                new PrintingRecipe(enchantedBook, book, material, output),
                null);
    }
}

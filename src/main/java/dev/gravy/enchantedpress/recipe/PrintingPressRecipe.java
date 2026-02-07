package dev.gravy.enchantedpress.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public record PrintingPressRecipe(List<Ingredient> ingredients,
                                  ItemStack output,
                                  int experienceCost) implements Recipe<PrintingPressRecipeInput> {
    @Override
    public boolean matches(PrintingPressRecipeInput recipeInput, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(PrintingPressRecipeInput recipeInput, HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public RecipeSerializer<? extends Recipe<PrintingPressRecipeInput>> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<? extends Recipe<PrintingPressRecipeInput>> getType() {
        return null;
    }

    @Override
    public PlacementInfo placementInfo() {
        return null;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }
}

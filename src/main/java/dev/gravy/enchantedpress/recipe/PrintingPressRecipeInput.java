package dev.gravy.enchantedpress.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record PrintingPressRecipeInput(List<ItemStack> stacks) implements RecipeInput{
    @Override
    public ItemStack getItem(int i) {
        return stacks.get(i);
    }

    @Override
    public int size() {
        return stacks.size();
    }
}
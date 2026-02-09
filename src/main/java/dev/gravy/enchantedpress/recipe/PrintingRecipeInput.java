package dev.gravy.enchantedpress.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record PrintingRecipeInput(ItemStack enchantedBook, ItemStack book, ItemStack material) implements RecipeInput {

    @Override
    public ItemStack getItem(int i) {
        return switch (i) {
            case 0 -> this.enchantedBook;
            case 1 -> this.book;
            case 2 -> this.material;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + i);
        };
    }

    @Override
    public int size() { return 3; }

    @Override
    public boolean isEmpty() { return this.enchantedBook.isEmpty() && this.book.isEmpty() && this.material.isEmpty(); }
}
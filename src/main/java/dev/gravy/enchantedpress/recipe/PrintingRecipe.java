package dev.gravy.enchantedpress.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.gravy.enchantedpress.ModRecipeBookCategories;
import dev.gravy.enchantedpress.ModRecipeSerializers;
import dev.gravy.enchantedpress.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

import java.util.List;

public class PrintingRecipe implements Recipe<PrintingRecipeInput> {
    private final Ingredient enchantedBook;
    private final Ingredient book;
    private final Ingredient material;
    private final ItemStack output;
    private PlacementInfo info;

    public PrintingRecipe(Ingredient enchantedBook, Ingredient book, Ingredient material, ItemStack output) {
        this.enchantedBook = enchantedBook;
        this.book = book;
        this.material = material;
        this.output = output;
    }

    @Override
    public RecipeType<PrintingRecipe> getType() {
        return ModRecipeTypes.PRINTING;
    }

    @Override
    public RecipeSerializer<? extends PrintingRecipe> getSerializer(){
        return ModRecipeSerializers.PRINTING;
    }

    @Override
    public boolean matches(PrintingRecipeInput printingRecipeInput, Level level) {
        return this.enchantedBook.test(printingRecipeInput.enchantedBook())
                && this.book.test(printingRecipeInput.enchantedBook())
                && this.material.test(printingRecipeInput.material());
    }

    @Override
    public ItemStack assemble(PrintingRecipeInput recipeInput, HolderLookup.Provider provider) {
        // Take in all the enchantments of the input, produce 2 of the output with the given enchantments
        EnchantmentHelper.setEnchantments(output, EnchantmentHelper.getEnchantmentsForCrafting(recipeInput.enchantedBook()));
        output.setCount(2);
        return output.copy();
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.PRINTING;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.info == null){
            this.info = PlacementInfo.create(List.of(
                    this.enchantedBook,
                    this.book,
                    this.material
            ));
        }

        return this.info;
    }

    public static class Serializer implements RecipeSerializer<PrintingRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public static final MapCodec<PrintingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("enchanted_book").forGetter(PrintingRecipe::getEnchantedBook),
                Ingredient.CODEC.fieldOf("book").forGetter(PrintingRecipe::getBook),
                Ingredient.CODEC.fieldOf("material").forGetter(PrintingRecipe::getMaterial),
                ItemStack.CODEC.fieldOf("output").forGetter(PrintingRecipe::getOutput)
        ).apply(instance, PrintingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, PrintingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, PrintingRecipe::getEnchantedBook,
                Ingredient.CONTENTS_STREAM_CODEC, PrintingRecipe::getBook,
                Ingredient.CONTENTS_STREAM_CODEC, PrintingRecipe::getMaterial,
                ItemStack.STREAM_CODEC, PrintingRecipe::getOutput,
                PrintingRecipe::new
        );

        @Override
        public MapCodec<PrintingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, PrintingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    // Getters

    public Ingredient getEnchantedBook() {
        return enchantedBook;
    }

    public Ingredient getBook() {
        return book;
    }

    public Ingredient getMaterial() {
        return material;
    }

    public ItemStack getOutput() {
        return output;
    }
}

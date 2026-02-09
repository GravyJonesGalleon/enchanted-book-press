package dev.gravy.enchantedpress.data.provider;

import dev.gravy.enchantedpress.EnchantedBookPress;
import dev.gravy.enchantedpress.data.builder.PrintingRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.recipe.FabricRecipeExporter;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class EnchantedBookPressRecipeProvider extends FabricRecipeProvider {
    public EnchantedBookPressRecipeProvider(FabricDataOutput provider, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(provider, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {
                PrintingRecipeBuilder.create(
                        Ingredient.of(Items.ENCHANTED_BOOK),
                        Ingredient.of(Items.BOOK),
                        Ingredient.of(Items.LAPIS_LAZULI),
                        Items.ENCHANTED_BOOK.getDefaultInstance()
                ).offerTo(recipeOutput, ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(EnchantedBookPress.MOD_ID, "base_recipe")));
            }
        };
    }

    @Override
    public String getName() {
        return "EnchantedBookPressRecipeProvider";
    }
}


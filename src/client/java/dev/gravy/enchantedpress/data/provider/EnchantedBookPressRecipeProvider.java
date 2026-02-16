package dev.gravy.enchantedpress.data.provider;

import dev.gravy.enchantedpress.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class EnchantedBookPressRecipeProvider extends FabricRecipeProvider {
    public EnchantedBookPressRecipeProvider(FabricDataOutput provider, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(provider, registriesFuture);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PRINTING_PRESS)
                        .pattern("IF")
                        .pattern("WW")
                        .pattern("WW")
                        .define('I', Items.INK_SAC)
                        .define('F', Items.FEATHER)
                        .define('W', ItemTags.PLANKS)
                        .unlockedBy(getHasName(Items.INK_SAC), has(Items.INK_SAC))
                        .save(recipeOutput);
                }
        };
    }

    @Override
    public @NonNull String getName() {
        return "EnchantedBookPressRecipeProvider";
    }
}


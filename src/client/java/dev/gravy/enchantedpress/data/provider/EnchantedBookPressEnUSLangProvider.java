package dev.gravy.enchantedpress.data.provider;

import dev.gravy.enchantedpress.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class EnchantedBookPressEnUSLangProvider extends FabricLanguageProvider {
    public EnchantedBookPressEnUSLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput,"en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModBlocks.PRINTING_PRESS, "Printing Press");
    }
}

package dev.gravy.enchantedpress.data.provider;

import dev.gravy.enchantedpress.EnchantedBookPress;
import dev.gravy.enchantedpress.ModBlocks;
import dev.gravy.enchantedpress.ModStats;
import dev.gravy.enchantedpress.block.PrintingPressBlock;
import dev.gravy.enchantedpress.screen.PrintingPressScreen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class EnchantedBookPressEnUSLangProvider extends FabricLanguageProvider {
    public EnchantedBookPressEnUSLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput,"en_us", registryLookup);
    }

    private static void addText(@NotNull TranslationBuilder builder, @NotNull Component text, @NotNull String value){
        if (text.getContents() instanceof TranslatableContents translatableContents) {
            builder.add(translatableContents.getKey(), value);
        } else {
            EnchantedBookPress.LOGGER.warn("Failed to add translation for text: {}", text.getString());
        }
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModBlocks.PRINTING_PRESS, "Printing Press");
        translationBuilder.add(ModStats.INTERACT_WITH_PRINTING_PRESS, "Interact with Printing Press");
        addText(translationBuilder, PrintingPressBlock.CONTAINER_TITLE, "Printing Press");
        addText(translationBuilder, PrintingPressScreen.COST_LABEL_TEXT, "Printing Cost: %1$s");
    }
}

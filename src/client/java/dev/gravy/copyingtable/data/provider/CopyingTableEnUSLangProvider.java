package dev.gravy.copyingtable.data.provider;

import dev.gravy.copyingtable.CopyingTableInitializer;
import dev.gravy.copyingtable.ModBlocks;
import dev.gravy.copyingtable.ModStats;
import dev.gravy.copyingtable.block.CopyingTableBlock;
import dev.gravy.copyingtable.screen.CopyingTableScreen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class CopyingTableEnUSLangProvider extends FabricLanguageProvider {
    public CopyingTableEnUSLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput,"en_us", registryLookup);
    }

    private static void addText(@NotNull TranslationBuilder builder, @NotNull Component text, @NotNull String value){
        if (text.getContents() instanceof TranslatableContents translatableContents) {
            builder.add(translatableContents.getKey(), value);
        } else {
            CopyingTableInitializer.LOGGER.warn("Failed to add translation for text: {}", text.getString());
        }
    }

    @Override
    public void generateTranslations(HolderLookup.@NonNull Provider provider, @NonNull TranslationBuilder translationBuilder) {
        translationBuilder.add(ModBlocks.COPYING_TABLE, "Copying Table");
        translationBuilder.add(ModStats.INTERACT_WITH_COPYING_TABLE, "Interact with Copying Table");
        addText(translationBuilder, CopyingTableBlock.CONTAINER_TITLE, "Copy Enchanted Book");
        addText(translationBuilder, CopyingTableScreen.COST_LABEL_TEXT, "Copying Cost: %1$s");
    }
}

package dev.gravy.enchantedpress.data.provider;

import dev.gravy.enchantedpress.ModBlocks;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class EnchantedBookPressModelProvider extends FabricModelProvider {
    public EnchantedBookPressModelProvider(FabricDataOutput output){
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialCube(ModBlocks.PRINTING_PRESS);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {

    }


}

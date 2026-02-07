package dev.gravy.enchantedpress;

import dev.gravy.enchantedpress.data.provider.EnchantedBookPressBlockLootTableProvider;
import dev.gravy.enchantedpress.data.provider.EnchantedBookPressBlockTagProvider;
import dev.gravy.enchantedpress.data.provider.EnchantedBookPressEnUSLangProvider;
import dev.gravy.enchantedpress.data.provider.EnchantedBookPressModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class EnchantedBookPressDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(EnchantedBookPressModelProvider::new);
		pack.addProvider(EnchantedBookPressEnUSLangProvider::new);
		pack.addProvider(EnchantedBookPressBlockLootTableProvider::new);
		pack.addProvider(EnchantedBookPressBlockTagProvider::new);
	}
}

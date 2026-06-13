package dev.gravy.copyingtable;

import dev.gravy.copyingtable.data.provider.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CopyingTableDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(CopyingTableModelProvider::new);
		pack.addProvider(CopyingTableEnUSLangProvider::new);
		pack.addProvider(CopyingTableBlockLootTableProvider::new);
		pack.addProvider(CopyingTableBlockTagProvider::new);
		pack.addProvider(CopyingTableRecipeProvider::new);
	}
}

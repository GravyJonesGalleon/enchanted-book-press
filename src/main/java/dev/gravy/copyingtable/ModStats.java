package dev.gravy.copyingtable;


import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;


public class ModStats {
    public static final Identifier INTERACT_WITH_COPYING_TABLE = makeCustomModStat("interact_with_copying_table", StatFormatter.DEFAULT);

    private static Identifier makeCustomModStat(String string, StatFormatter statFormatter) {
        Identifier identifier = Identifier.fromNamespaceAndPath(CopyingTableInitializer.MOD_ID, string);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, string, identifier);
        Stats.CUSTOM.get(identifier, statFormatter);
        return identifier;
    }

    public static void initialize(){}
}

package dev.gravy.enchantedpress;


import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.CreativeModeTabs;


public class ModStats {
    public static final Identifier INTERACT_WITH_PRINTING_PRESS = makeCustomModStat("interact_with_printing_press", StatFormatter.DEFAULT);

    private static Identifier makeCustomModStat(String string, StatFormatter statFormatter) {
        Identifier identifier = Identifier.fromNamespaceAndPath(EnchantedBookPress.MOD_ID, string);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, string, identifier);
        Stats.CUSTOM.get(identifier, statFormatter);
        return identifier;
    }

    public static void initialize(){}
}

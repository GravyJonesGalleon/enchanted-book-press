package dev.gravy.enchantedpress.block;

import dev.gravy.enchantedpress.ModBlocks;
import dev.gravy.enchantedpress.ModMenuTypes;
import dev.gravy.enchantedpress.ModStats;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class PrintingPressBlockTest {
    @BeforeAll
    static void beforeAll() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        ModBlocks.initialize();
        ModMenuTypes.initialize();
        ModStats.initialize();
    }

    @Test
    void MyTest() {
        System.out.println("I don't know how to unit test this");
    }
}

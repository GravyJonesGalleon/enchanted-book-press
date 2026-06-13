package dev.gravy.copyingtable.block;

import dev.gravy.copyingtable.ModBlocks;
import dev.gravy.copyingtable.ModMenuTypes;
import dev.gravy.copyingtable.ModStats;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class CopyingTableBlockTest {
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

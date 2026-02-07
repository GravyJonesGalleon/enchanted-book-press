package dev.gravy.enchantedpress.blockentities;

import dev.gravy.enchantedpress.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PrintingPressBlockEntity extends BlockEntity {
    public PrintingPressBlockEntity(BlockPos pos, BlockState state){
        super(ModBlockEntities.PRINTING_PRESS_BLOCK_ENTITY, pos, state);
    }
}


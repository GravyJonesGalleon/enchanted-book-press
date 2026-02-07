package dev.gravy.enchantedpress.block;

import com.mojang.serialization.MapCodec;
import dev.gravy.enchantedpress.blockentities.PrintingPressBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class PrintingPressBlock extends BaseEntityBlock {
    public PrintingPressBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PrintingPressBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PrintingPressBlockEntity(pos, state);
    }
}

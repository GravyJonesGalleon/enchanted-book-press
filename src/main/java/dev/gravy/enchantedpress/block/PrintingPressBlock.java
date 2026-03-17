package dev.gravy.enchantedpress.block;

import com.mojang.serialization.MapCodec;
import dev.gravy.enchantedpress.EnchantedBookPress;
import dev.gravy.enchantedpress.ModStats;
import dev.gravy.enchantedpress.menu.PrintingPressMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class PrintingPressBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<PrintingPressBlock> CODEC = simpleCodec(PrintingPressBlock::new);
    public static final Component CONTAINER_TITLE = Component.translatable("container." + EnchantedBookPress.MOD_ID + ".printing_press");

    @Override
    public @NonNull MapCodec<PrintingPressBlock> codec() {
        return CODEC;
    }

    public PrintingPressBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(@NonNull BlockPlaceContext blockPlaceContext) {
        BlockState superState = super.getStateForPlacement(blockPlaceContext);
        return superState == null ? null : superState.setValue(BlockStateProperties.HORIZONTAL_FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState blockState, Level level, @NonNull BlockPos blockPos, @NonNull Player player, @NonNull BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            player.openMenu(blockState.getMenuProvider(level, blockPos));
            player.awardStat(ModStats.INTERACT_WITH_PRINTING_PRESS);
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(@NonNull BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos) {
        return new SimpleMenuProvider((syncId, inventory, player) -> new PrintingPressMenu(
                syncId,
                inventory
        ),
                CONTAINER_TITLE);
    }


}

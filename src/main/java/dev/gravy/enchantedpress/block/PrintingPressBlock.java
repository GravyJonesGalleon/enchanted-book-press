package dev.gravy.enchantedpress.block;

import com.mojang.serialization.MapCodec;
import dev.gravy.enchantedpress.EnchantedBookPress;
import dev.gravy.enchantedpress.ModRecipeTypes;
import dev.gravy.enchantedpress.ModStats;
import dev.gravy.enchantedpress.menu.PrintingPressMenu;
import dev.gravy.enchantedpress.recipe.PrintingRecipe;
import dev.gravy.enchantedpress.recipe.PrintingRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;
import oshi.jna.platform.mac.SystemB;

public class PrintingPressBlock extends Block {
    public static final MapCodec<PrintingPressBlock> CODEC = simpleCodec(PrintingPressBlock::new);
    public static final Component CONTAINER_TITLE = Component.translatable("container." + EnchantedBookPress.MOD_ID + ".printing_press");

    @Override
    public MapCodec<PrintingPressBlock> codec() {
        return CODEC;
    }

    public PrintingPressBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            player.openMenu(blockState.getMenuProvider(level, blockPos));
            player.awardStat(ModStats.INTERACT_WITH_PRINTING_PRESS);
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        return new SimpleMenuProvider((syncId, inventory, player) -> new PrintingPressMenu(
                syncId,
                inventory
        ),
                CONTAINER_TITLE);
    }
}

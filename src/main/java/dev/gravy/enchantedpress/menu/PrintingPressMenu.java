package dev.gravy.enchantedpress.menu;

import dev.gravy.enchantedpress.*;
import dev.gravy.enchantedpress.recipe.PrintingRecipe;
import dev.gravy.enchantedpress.recipe.PrintingRecipeInput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class PrintingPressMenu extends ItemCombinerMenu {
    /* PLEASE NOTE:
    Code has been adapted from https://wiki.fabricmc.net. They are still using the Yarn mappings,
    so some comments describe the wrong Class and method names, and some have been adjusted by me
     */

    public static final int ENCHANTED_BOOK_SLOT = 0;
    public static final int BOOK_SLOT = 1;
    public static final int MATERIAL_SLOT = 2;
    public static final int RESULT_SLOT = 3;
    private final DataSlot hasRecipeError = DataSlot.standalone();
    private final Level level;
    long lastSoundTime;

    public PrintingPressMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL);
    }

    public PrintingPressMenu(int syncId, Inventory playerInventory, ContainerLevelAccess containerLevelAccess) {
        this(syncId, playerInventory, containerLevelAccess, playerInventory.player.level());
    }

    private PrintingPressMenu(int syncId, Inventory playerInventory, ContainerLevelAccess containerLevelAccess, Level level) {
        super(
                ModMenus.PRINTING_PRESS_MENU,
                syncId,
                playerInventory,
                containerLevelAccess,
                createInputSlotDefinitions(level.recipeAccess())
        );
        this.level = level;
        this.addDataSlot(this.hasRecipeError).set(0);
    }

    private static ItemCombinerMenuSlotDefinition createInputSlotDefinitions(RecipeAccess recipeAccess) {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(ENCHANTED_BOOK_SLOT, 8, 48, (itemStack -> {return true;})) // TODO: Maybe make this not always true?
                .withSlot(BOOK_SLOT, 26 , 48, (itemStack -> {return true;}))
                .withSlot(MATERIAL_SLOT, 44, 48, (itemStack -> {return true;}))
                .withResultSlot(RESULT_SLOT, 98, 48)
                .build();
    }

    @Override
    protected boolean isValidBlock(BlockState blockState) { return  blockState.is(ModBlocks.PRINTING_PRESS); }

    @Override
    protected void onTake(Player player, ItemStack itemStack) {
        this.resultSlots.awardUsedRecipes(player, this.getRelevantItems());
        this.shrinkStackInSlot(ENCHANTED_BOOK_SLOT);
        this.shrinkStackInSlot(BOOK_SLOT);
        this.shrinkStackInSlot(MATERIAL_SLOT);
        // TODO: Implement a custom sound?
        this.access.execute((level, blockPos) -> {
            long l = level.getGameTime();
            if (this.lastSoundTime != l) {
                level.playSound(null, blockPos, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                PrintingPressMenu.this.lastSoundTime = l;
            }});
    }

    private List<ItemStack> getRelevantItems() {
        return List.of(
                this.inputSlots.getItem(ENCHANTED_BOOK_SLOT),
                this.inputSlots.getItem(BOOK_SLOT),
                this.inputSlots.getItem(MATERIAL_SLOT)
        );
    }

    private PrintingRecipeInput createRecipeInput() {
        return new PrintingRecipeInput(
                this.inputSlots.getItem(ENCHANTED_BOOK_SLOT),
                this.inputSlots.getItem(BOOK_SLOT),
                this.inputSlots.getItem(MATERIAL_SLOT)
        );
    }

    private void shrinkStackInSlot(int slotIndex) {
        ItemStack itemStack = this.inputSlots.getItem(slotIndex);
        if(!itemStack.isEmpty()) {
            itemStack.shrink(1);
            this.inputSlots.setItem(slotIndex, itemStack);
        }
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (this.level instanceof ServerLevel) {
            boolean bl = this.getSlot(ENCHANTED_BOOK_SLOT).hasItem()
                    && this.getSlot(BOOK_SLOT).hasItem()
                    && this.getSlot(MATERIAL_SLOT).hasItem()
                    && !this.getSlot(this.getResultSlot()).hasItem();
            this.hasRecipeError.set(bl? 1 : 0);
        }
    }

    @Override
    public void createResult() {
        PrintingRecipeInput printingRecipeInput = this.createRecipeInput();
        Optional<RecipeHolder<PrintingRecipe>> optional;
        if (this.level instanceof ServerLevel serverLevel) {
            optional = serverLevel.recipeAccess().getRecipeFor(ModRecipeTypes.PRINTING, printingRecipeInput, serverLevel);
        } else {
            optional = Optional.empty();
        }

        optional.ifPresentOrElse(recipeHolder -> {
            ItemStack itemStack = ((PrintingRecipe)recipeHolder.value()).assemble(printingRecipeInput, this.level.registryAccess());
            this.resultSlots.setRecipeUsed(recipeHolder);
            this.resultSlots.setItem(0, itemStack);
        }, () -> {
            this.resultSlots.setRecipeUsed(null);
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        });
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack itemStack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(itemStack, slot);
    }

    @Override
    protected boolean canMoveIntoInputSlots(ItemStack itemStack) {
        // TODO: NO IDEA MATE
        return false;
    }

    public boolean hasRecipeError() {return this.hasRecipeError.get() > 0; }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.clearContainer(player, this.inputSlots);
    }
}

package dev.gravy.enchantedpress.menu;

import dev.gravy.enchantedpress.*;
import net.minecraft.core.Holder;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class PrintingPressMenu extends AbstractContainerMenu {
    /*
    * Slot Definitions
    * Changing these values will change functionality of the GUI
    */
    public static final int ENCHANTED_BOOK_SLOT = 0;
    public static final int BOOK_SLOT = 1;
    public static final int MATERIAL_SLOT = 2;
    public static final int RESULT_SLOT = 3;
    public static final int INVENTORY_START = RESULT_SLOT+1;
    public static final int INVENTORY_END = RESULT_SLOT+27+1;
    public static final int HOTBAR_START = INVENTORY_END;
    public static final int HOTBAR_END = INVENTORY_END+9;

    /*
    * Balance
    * Requirements state how many of an ingredient is required, some items are non-stackable so can't be above 1
    * Level cost for copying is defined LEVEL_COST_COEFFICIENT * <number of enchantments to copy> + LEVEL_COST_OFFSET
    */
    public static final int ENCHANTED_BOOK_REQUIREMENT = 1; // Non-stackable
    public static final int BOOK_REQUIREMENT = 1;
    public static final int MATERIAL_REQUIREMENT = 64;
    public static final double LEVEL_COST_COEFFICIENT = 1;
    public static final double LEVEL_COST_OFFSET = 2.0;

    private final ContainerLevelAccess access;
    long lastSoundTime;
    public final Container inputSlots = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            PrintingPressMenu.this.slotsChanged(this);
            super.setChanged();
        }
    };
    private final ResultContainer resultSlots = new ResultContainer() {
        @Override
        public void setChanged() {
            PrintingPressMenu.this.slotsChanged(this);
            super.setChanged();
        }
    };
    private final DataSlot cost = DataSlot.standalone();

    public PrintingPressMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL);
    }

    private PrintingPressMenu(int syncId, Inventory playerInventory, ContainerLevelAccess containerLevelAccess) {
        super(
                ModMenuTypes.PRINTING_PRESS,
                syncId
        );
        this.access = containerLevelAccess;

        this.addSlot(new Slot(this.inputSlots, ENCHANTED_BOOK_SLOT, 15, 15) {
            @Override
            public boolean mayPlace(@NonNull ItemStack itemStack) {
                return itemStack.is(Items.ENCHANTED_BOOK);
            }
        });
  this.addSlot(new Slot(this.inputSlots, BOOK_SLOT, 15, 33) {
            @Override
            public boolean mayPlace(@NonNull ItemStack itemStack) {
                return itemStack.is(Items.BOOK);
            }
        });
  this.addSlot(new Slot(this.inputSlots, MATERIAL_SLOT, 15, 51) {
            @Override
            public boolean mayPlace(@NonNull ItemStack itemStack) {
                return itemStack.is(Items.LAPIS_LAZULI);
            }
        });
        this.addSlot(new Slot(this.resultSlots, RESULT_SLOT, 145, 39) {
            @Override
            public boolean mayPlace(@NonNull ItemStack itemStack) {
                return false;
            }

            @Override
            public void onTake(@NonNull Player player, @NonNull ItemStack itemStack) {
                if (!player.hasInfiniteMaterials()) {
                    player.giveExperienceLevels(-PrintingPressMenu.this.cost.get());
                }

                PrintingPressMenu.this.cost.set(0);
                PrintingPressMenu.this.resultSlots.awardUsedRecipes(player, PrintingPressMenu.this.getRelevantItems());
                PrintingPressMenu.this.shrinkStackInSlot(ENCHANTED_BOOK_SLOT, ENCHANTED_BOOK_REQUIREMENT);
                PrintingPressMenu.this.shrinkStackInSlot(BOOK_SLOT, BOOK_REQUIREMENT);
                PrintingPressMenu.this.shrinkStackInSlot(MATERIAL_SLOT, MATERIAL_SLOT);
                // TODO: Implement a custom sound?
                super.onTake(player, itemStack);
            }

            @Override
            public boolean mayPickup(@NonNull Player player) {
                return (player.hasInfiniteMaterials()
                        || player.experienceLevel >= PrintingPressMenu.this.cost.get())
                        && PrintingPressMenu.this.cost.get() > 0;
            }
        });

        addStandardInventorySlots(playerInventory, 8, 84);
    }

    private List<ItemStack> getRelevantItems() {
        return List.of(
                this.inputSlots.getItem(ENCHANTED_BOOK_SLOT),
                this.inputSlots.getItem(BOOK_SLOT),
                this.inputSlots.getItem(MATERIAL_SLOT)
        );
    }

    private void shrinkStackInSlot(int slotIndex, int amount) {
        ItemStack itemStack = this.inputSlots.getItem(slotIndex);
        if(itemStack.getCount() >= amount) {
            itemStack.shrink(amount);
            this.inputSlots.setItem(slotIndex, itemStack);
        }
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return stillValid(this.access, player, ModBlocks.PRINTING_PRESS);
    }

    @Override
    public void slotsChanged(@NonNull Container container) {
        ItemStack enchantedBook = this.inputSlots.getItem(ENCHANTED_BOOK_SLOT);
        ItemStack book = this.inputSlots.getItem(BOOK_SLOT);
        ItemStack material = this.inputSlots.getItem(MATERIAL_SLOT);
        ItemStack result = this.resultSlots.getItem(RESULT_SLOT);

        if (enchantedBook.getCount() >= ENCHANTED_BOOK_REQUIREMENT
                && book.getCount() >= BOOK_REQUIREMENT
                && material.getCount() >= MATERIAL_REQUIREMENT
        ) {
            this.setupResultSlot(enchantedBook);
        } else if (!result.isEmpty() ){
            this.resultSlots.removeItemNoUpdate(RESULT_SLOT);
        }

    }

    public void setupResultSlot(ItemStack enchantedBook) {
        if(enchantedBook.isEmpty()){
            this.resultSlots.removeItemNoUpdate(RESULT_SLOT);
            this.broadcastChanges();
            return;
        }

        // Get the total number of levels on the input enchanted book
        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(enchantedBook);
        int numLevels = 0;
        for (Holder<Enchantment> enchantment : enchantments.keySet()) {
            numLevels += enchantments.getLevel(enchantment);
        }
        int copyingCost = calculateXpCost(numLevels);

        ItemStack result = enchantedBook.copyWithCount(2);
        this.cost.set(copyingCost);
        this.resultSlots.setItem(RESULT_SLOT, result);
        this.broadcastChanges();
    }

    @Override
    public boolean canTakeItemForPickAll(@NonNull ItemStack itemStack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(itemStack, slot);
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int slotId) {
        // We make a copy to see if the item stack has shrunk (I think!)
        ItemStack stackToRemain = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotId);
        if (slot.hasItem()) {
            ItemStack stackToMove = slot.getItem();
            stackToRemain = stackToMove.copy();

            if (slotId == RESULT_SLOT) {
                // Taking from the output
                if(!moveItemStackTo(stackToMove, INVENTORY_START, HOTBAR_END, true)){
                    return ItemStack.EMPTY;
                }
                if(!moveItemStackTo(stackToMove, INVENTORY_START, HOTBAR_END, true)){
                    player.getInventory().placeItemBackInInventory(stackToMove);
                }
                slot.onQuickCraft(stackToMove, stackToRemain);
            } else if (slotId != ENCHANTED_BOOK_SLOT
                    && slotId != BOOK_SLOT
                    && slotId != MATERIAL_SLOT
            ) {
                // NOT taking from the inputs
                if (stackToMove.is(Items.ENCHANTED_BOOK) || stackToMove.is(Items.BOOK) || stackToMove.is(Items.LAPIS_LAZULI)) {
                    // Set ingredient stack to ingredient slot
                    if (!this.moveItemStackTo(stackToMove, ENCHANTED_BOOK_SLOT, RESULT_SLOT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // Swap between inventory and hotbar
                    if (slotId >= INVENTORY_START && slotId < INVENTORY_END) {
                        if (!this.moveItemStackTo(stackToMove, HOTBAR_START, HOTBAR_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slotId >= HOTBAR_START && slotId < HOTBAR_END
                            && !this.moveItemStackTo(stackToMove, INVENTORY_START, INVENTORY_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(stackToMove, INVENTORY_START, HOTBAR_END, false)) {
                // Taking from the inputs
                return ItemStack.EMPTY;
            }

            if (stackToMove.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (stackToMove.getCount() == stackToRemain.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackToMove);
            this.broadcastChanges();
        }
        return stackToRemain;
    }

    @Override
    public void removed(@NonNull Player player) {
        super.removed(player);
        this.resultSlots.removeItemNoUpdate(RESULT_SLOT);
        this.clearContainer(player, this.inputSlots);
    }

    public int calculateXpCost(int numLevels) {
        return (int) Math.ceil(LEVEL_COST_COEFFICIENT * numLevels + LEVEL_COST_OFFSET);
    }

    public int getCost() {
        return this.cost.get();
    }
}

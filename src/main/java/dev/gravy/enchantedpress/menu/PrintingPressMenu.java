package dev.gravy.enchantedpress.menu;

import dev.gravy.enchantedpress.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class PrintingPressMenu extends AbstractContainerMenu {
    /* PLEASE NOTE:
    Code has been adapted from https://wiki.fabricmc.net. They are still using the Yarn mappings,
    so some comments describe the wrong Class and method names, and some have been adjusted by me
     */

    public static final int ENCHANTED_BOOK_SLOT = 0;
    public static final int BOOK_SLOT = 1;
    public static final int MATERIAL_SLOT = 2;

    private final Container inputContainer = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            PrintingPressMenu.this.slotsChanged(this);
            super.setChanged();
        }
    };
    private final ResultContainer resultContainer = new ResultContainer() {
        @Override
        public void setChanged() {
            PrintingPressMenu.this.slotsChanged(this);
            super.setChanged();
        }
    };


    /* This constructor gets called on the client when the server wants it to open the screenHandler,
    The client will call the other constructor with an empty Inventory and the screenHandler will automatically
    sync this empty menu with the menu on the server.
    - From https://wiki.fabricmc.net/tutorial:screenhandler
    This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the menu of the menu
    and can therefore directly provide it as an argument. This menu will then be synced to the client.
    - From https://wiki.fabricmc.net/tutorial:screenhandler
     */
    public PrintingPressMenu(int syncId, Inventory playerInventory) {
        super(ModMenus.PRINTING_PRESS_MENU, syncId);

        // some inventories do custom logic when a player opens it. I don't think ours does
        inputContainer.startOpen(playerInventory.player);

        /* This will place the slot in the correct locations for the input/output. The slots exist on both server and client!
        This will not render the background of the slots however, this is the Screens job
        - Code and comments adapted from https://wiki.fabricmc.net/tutorial:screenhandler
         */
        // Add slots for input
        this.addSlot(new Slot(inputContainer, ENCHANTED_BOOK_SLOT, 8, 48));
        this.addSlot(new Slot(inputContainer, BOOK_SLOT, 26, 48));
        this.addSlot(new Slot(inputContainer, MATERIAL_SLOT, 44, 48));

        // Show the player inventory
        this.addStandardInventorySlots(playerInventory, 8, 84);
        this.addInventoryHotbarSlots(playerInventory, 8, 142);
    }


    @Override
    public boolean stillValid(Player player){
        return this.inputContainer.stillValid(player);
    }

    // Shift + Player Inv Slot
    // TODO: Make this work?
    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        return null;
    }
}

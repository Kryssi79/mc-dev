package net.minecraft.server;

public class ContainerFurnace extends Container {

    private TileEntityFurnace furnace;
    private int b = 0;
    private int c = 0;
    private int h = 0;

    public ContainerFurnace(PlayerInventory playerinventory, TileEntityFurnace tileentityfurnace) {
        this.furnace = tileentityfurnace;
        this.a(new Slot(tileentityfurnace, 0, 56, 17));
        this.a(new Slot(tileentityfurnace, 1, 56, 53));
        this.a(new SlotResult2(playerinventory.player, tileentityfurnace, 2, 116, 35));

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.a(new Slot(playerinventory, i, 8 + i * 18, 142));
        }
    }

    public void addSlotListener(ICrafting icrafting) {
        super.addSlotListener(icrafting);
        icrafting.setContainerData(this, 0, this.furnace.cookTime);
        icrafting.setContainerData(this, 1, this.furnace.burnTime);
        icrafting.setContainerData(this, 2, this.furnace.ticksForCurrentFuel);
    }

    public void a() {
        super.a();

        for (int i = 0; i < this.listeners.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.listeners.get(i);

            if (this.b != this.furnace.cookTime) {
                icrafting.setContainerData(this, 0, this.furnace.cookTime);
            }

            if (this.c != this.furnace.burnTime) {
                icrafting.setContainerData(this, 1, this.furnace.burnTime);
            }

            if (this.h != this.furnace.ticksForCurrentFuel) {
                icrafting.setContainerData(this, 2, this.furnace.ticksForCurrentFuel);
            }
        }

        this.b = this.furnace.cookTime;
        this.c = this.furnace.burnTime;
        this.h = this.furnace.ticksForCurrentFuel;
    }

    public boolean b(EntityHuman entityhuman) {
        return this.furnace.a(entityhuman);
    }

    public ItemStack a(int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.e.get(i);

        if (slot != null && slot.c()) {
            ItemStack itemstack1 = slot.getItem();

            itemstack = itemstack1.cloneItemStack();
            if (i == 2) {
                if (!this.a(itemstack1, 3, 39, true)) {
                    return null;
                }

                slot.a(itemstack1, itemstack);
            } else if (i >= 3 && i < 30) {
                if (!this.a(itemstack1, 30, 39, false)) {
                    return null;
                }
            } else if (i >= 30 && i < 39) {
                if (!this.a(itemstack1, 3, 30, false)) {
                    return null;
                }
            } else if (!this.a(itemstack1, 3, 39, false)) {
                return null;
            }

            if (itemstack1.count == 0) {
                slot.set((ItemStack) null);
            } else {
                slot.d();
            }

            if (itemstack1.count == itemstack.count) {
                return null;
            }

            slot.c(itemstack1);
        }

        return itemstack;
    }
}

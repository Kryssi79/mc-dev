package net.minecraft.server;

public class EntityMushroomCow extends EntityCow {

    public EntityMushroomCow(World world) {
        super(world);
        this.texture = "/mob/redcow.png";
        this.b(0.9F, 1.3F);
    }

    public boolean b(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.inventory.getItemInHand();

        if (itemstack != null && itemstack.id == Item.BOWL.id && this.getAge() >= 0) {
            entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, new ItemStack(Item.MUSHROOM_SOUP));
            return true;
        } else if (itemstack != null && itemstack.id == Item.SHEARS.id && this.getAge() >= 0) {
            this.die();
            this.world.a("largeexplode", this.locX, this.locY + (double) (this.length / 2.0F), this.locZ, 0.0D, 0.0D, 0.0D);
            if (!this.world.isStatic) {
                EntityCow entitycow = new EntityCow(this.world);

                entitycow.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
                entitycow.setHealth(this.getHealth());
                entitycow.V = this.V;
                this.world.addEntity(entitycow);

                for (int i = 0; i < 5; ++i) {
                    this.world.addEntity(new EntityItem(this.world, this.locX, this.locY + (double) this.length, this.locZ, new ItemStack(Block.RED_MUSHROOM)));
                }
            }

            return true;
        } else {
            return super.b(entityhuman);
        }
    }

    public EntityAnimal createChild(EntityAnimal entityanimal) {
        return new EntityMushroomCow(this.world);
    }
}

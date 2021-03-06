package net.minecraft.server;

import java.util.List;

public abstract class EntityAnimal extends EntityAgeable implements IAnimal {

    private int love;
    private int b = 0;

    public EntityAnimal(World world) {
        super(world);
    }

    public void e() {
        super.e();
        if (this.love > 0) {
            --this.love;
            String s = "heart";

            if (this.love % 10 == 0) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;

                this.world.a(s, this.locX + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, this.locY + 0.5D + (double) (this.random.nextFloat() * this.length), this.locZ + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2);
            }
        } else {
            this.b = 0;
        }
    }

    protected void a(Entity entity, float f) {
        if (entity instanceof EntityHuman) {
            if (f < 3.0F) {
                double d0 = entity.locX - this.locX;
                double d1 = entity.locZ - this.locZ;

                this.yaw = (float) (Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
                this.e = true;
            }

            EntityHuman entityhuman = (EntityHuman) entity;

            if (entityhuman.T() == null || !this.a(entityhuman.T())) {
                this.target = null;
            }
        } else if (entity instanceof EntityAnimal) {
            EntityAnimal entityanimal = (EntityAnimal) entity;

            if (this.getAge() > 0 && entityanimal.getAge() < 0) {
                if ((double) f < 2.5D) {
                    this.e = true;
                }
            } else if (this.love > 0 && entityanimal.love > 0) {
                if (entityanimal.target == null) {
                    entityanimal.target = this;
                }

                if (entityanimal.target == this && (double) f < 3.5D) {
                    ++entityanimal.love;
                    ++this.love;
                    ++this.b;
                    if (this.b % 4 == 0) {
                        this.world.a("heart", this.locX + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, this.locY + 0.5D + (double) (this.random.nextFloat() * this.length), this.locZ + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, 0.0D, 0.0D, 0.0D);
                    }

                    if (this.b == 60) {
                        this.c((EntityAnimal) entity);
                    }
                } else {
                    this.b = 0;
                }
            } else {
                this.b = 0;
                this.target = null;
            }
        }
    }

    private void c(EntityAnimal entityanimal) {
        EntityAnimal entityanimal1 = this.createChild(entityanimal);

        if (entityanimal1 != null) {
            this.setAge(6000);
            entityanimal.setAge(6000);
            this.love = 0;
            this.b = 0;
            this.target = null;
            entityanimal.target = null;
            entityanimal.b = 0;
            entityanimal.love = 0;
            entityanimal1.setAge(-24000);
            entityanimal1.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);

            for (int i = 0; i < 7; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;

                this.world.a("heart", this.locX + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, this.locY + 0.5D + (double) (this.random.nextFloat() * this.length), this.locZ + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2);
            }

            this.world.addEntity(entityanimal1);
        }
    }

    public abstract EntityAnimal createChild(EntityAnimal entityanimal);

    protected void b(Entity entity, float f) {}

    public boolean damageEntity(DamageSource damagesource, int i) {
        this.f = 60;
        this.target = null;
        this.love = 0;
        return super.damageEntity(damagesource, i);
    }

    public float a(int i, int j, int k) {
        return this.world.getTypeId(i, j - 1, k) == Block.GRASS.id ? 10.0F : this.world.p(i, j, k) - 0.5F;
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("InLove", this.love);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.love = nbttagcompound.getInt("InLove");
    }

    protected Entity findTarget() {
        if (this.f > 0) {
            return null;
        } else {
            float f = 8.0F;
            List list;
            int i;
            EntityAnimal entityanimal;

            if (this.love > 0) {
                list = this.world.a(this.getClass(), this.boundingBox.grow((double) f, (double) f, (double) f));

                for (i = 0; i < list.size(); ++i) {
                    entityanimal = (EntityAnimal) list.get(i);
                    if (entityanimal != this && entityanimal.love > 0) {
                        return entityanimal;
                    }
                }
            } else if (this.getAge() == 0) {
                list = this.world.a(EntityHuman.class, this.boundingBox.grow((double) f, (double) f, (double) f));

                for (i = 0; i < list.size(); ++i) {
                    EntityHuman entityhuman = (EntityHuman) list.get(i);

                    if (entityhuman.T() != null && this.a(entityhuman.T())) {
                        return entityhuman;
                    }
                }
            } else if (this.getAge() > 0) {
                list = this.world.a(this.getClass(), this.boundingBox.grow((double) f, (double) f, (double) f));

                for (i = 0; i < list.size(); ++i) {
                    entityanimal = (EntityAnimal) list.get(i);
                    if (entityanimal != this && entityanimal.getAge() < 0) {
                        return entityanimal;
                    }
                }
            }

            return null;
        }
    }

    public boolean canSpawn() {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.boundingBox.b);
        int k = MathHelper.floor(this.locZ);

        return this.world.getTypeId(i, j - 1, k) == Block.GRASS.id && this.world.m(i, j, k) > 8 && super.canSpawn();
    }

    public int m() {
        return 120;
    }

    protected boolean n() {
        return false;
    }

    protected int getExpValue(EntityHuman entityhuman) {
        return 1 + this.world.random.nextInt(3);
    }

    public boolean a(ItemStack itemstack) {
        return itemstack.id == Item.WHEAT.id;
    }

    public boolean b(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.inventory.getItemInHand();

        if (itemstack != null && this.a(itemstack) && this.getAge() == 0) {
            if (this.world.getWorldData().getGameType() != 1) {
                --itemstack.count;
                if (itemstack.count <= 0) {
                    entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack) null);
                }
            }

            this.love = 600;
            this.target = null;

            for (int i = 0; i < 7; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;

                this.world.a("heart", this.locX + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, this.locY + 0.5D + (double) (this.random.nextFloat() * this.length), this.locZ + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2);
            }

            return true;
        } else {
            return super.b(entityhuman);
        }
    }

    public boolean r_() {
        return this.love > 0;
    }

    public void s_() {
        this.love = 0;
    }

    public boolean mate(EntityAnimal entityanimal) {
        return entityanimal == this ? false : (entityanimal.getClass() != this.getClass() ? false : this.r_() && entityanimal.r_());
    }
}

package net.minecraft.server;

public class EntitySnowman extends EntityGolem {

    public EntitySnowman(World world) {
        super(world);
        this.texture = "/mob/snowman.png";
        this.b(0.4F, 1.8F);
        this.ak().a(true);
        this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 0.25F, 2, 20));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 0.2F));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityMonster.class, 16.0F, 0, true));
    }

    public boolean c_() {
        return true;
    }

    public int getMaxHealth() {
        return 4;
    }

    public void e() {
        super.e();
        if (this.aS()) {
            this.damageEntity(DamageSource.DROWN, 1);
        }

        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.locZ);

        if (this.world.getBiome(i, j).h() > 1.0F) {
            this.damageEntity(DamageSource.BURN, 1);
        }

        for (i = 0; i < 4; ++i) {
            j = MathHelper.floor(this.locX + (double) ((float) (i % 2 * 2 - 1) * 0.25F));
            int k = MathHelper.floor(this.locY);
            int l = MathHelper.floor(this.locZ + (double) ((float) (i / 2 % 2 * 2 - 1) * 0.25F));

            if (this.world.getTypeId(j, k, l) == 0 && this.world.getBiome(j, l).h() < 0.8F && Block.SNOW.canPlace(this.world, j, k, l)) {
                this.world.setTypeId(j, k, l, Block.SNOW.id);
            }
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    protected int getLootId() {
        return Item.SNOW_BALL.id;
    }

    protected void dropDeathLoot(boolean flag, int i) {
        int j = this.random.nextInt(16);

        for (int k = 0; k < j; ++k) {
            this.b(Item.SNOW_BALL.id, 1);
        }
    }
}

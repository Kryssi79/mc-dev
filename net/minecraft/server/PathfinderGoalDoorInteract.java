package net.minecraft.server;

public abstract class PathfinderGoalDoorInteract extends PathfinderGoal {

    protected EntityLiving a;
    protected int b;
    protected int c;
    protected int d;
    protected BlockDoor e;
    boolean f;
    float g;
    float h;

    public PathfinderGoalDoorInteract(EntityLiving entityliving) {
        this.a = entityliving;
    }

    public boolean a() {
        if (!this.a.positionChanged) {
            return false;
        } else {
            Navigation navigation = this.a.ak();
            PathEntity pathentity = navigation.c();

            if (pathentity != null && !pathentity.b() && navigation.b()) {
                for (int i = 0; i < Math.min(pathentity.e() + 2, pathentity.d()); ++i) {
                    PathPoint pathpoint = pathentity.a(i);

                    this.b = pathpoint.a;
                    this.c = pathpoint.b + 1;
                    this.d = pathpoint.c;
                    if (this.a.e((double) this.b, this.a.locY, (double) this.d) <= 2.25D) {
                        this.e = this.a(this.b, this.c, this.d);
                        if (this.e != null) {
                            return true;
                        }
                    }
                }

                this.b = MathHelper.floor(this.a.locX);
                this.c = MathHelper.floor(this.a.locY + 1.0D);
                this.d = MathHelper.floor(this.a.locZ);
                this.e = this.a(this.b, this.c, this.d);
                return this.e != null;
            } else {
                return false;
            }
        }
    }

    public boolean b() {
        return !this.f;
    }

    public void c() {
        this.f = false;
        this.g = (float) ((double) ((float) this.b + 0.5F) - this.a.locX);
        this.h = (float) ((double) ((float) this.d + 0.5F) - this.a.locZ);
    }

    public void e() {
        float f = (float) ((double) ((float) this.b + 0.5F) - this.a.locX);
        float f1 = (float) ((double) ((float) this.d + 0.5F) - this.a.locZ);
        float f2 = this.g * f + this.h * f1;

        if (f2 < 0.0F) {
            this.f = true;
        }
    }

    private BlockDoor a(int i, int j, int k) {
        int l = this.a.world.getTypeId(i, j, k);

        if (l != Block.WOODEN_DOOR.id) {
            return null;
        } else {
            BlockDoor blockdoor = (BlockDoor) Block.byId[l];

            return blockdoor;
        }
    }
}

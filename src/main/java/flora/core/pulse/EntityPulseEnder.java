package flora.core.pulse;

import flora.core.init.FLORAEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityPulseEnder extends EntityPulse {

    public EntityPulseEnder(EntityType<? extends EntityPulseEnder> type, Level level) {
        super(type, level);
    }

    public EntityPulseEnder(Level level, LivingEntity shooter) {
        super(FLORAEntityTypes.ENDER_PULSE.get(), shooter, Vec3.ZERO, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide && result.getEntity() instanceof Mob target) {
            // Teleport the target
            RandomSource rand = target.getRandom();
            int x = (int) (target.getX() + rand.nextInt(20) - 10);
            int z = (int) (target.getZ() + rand.nextInt(20) - 10);
            int y = 255;
            BlockPos pos = new BlockPos(x, y, z);
            while (target.level().isEmptyBlock(pos.below()) && y > target.level().getMinBuildHeight()) {
                y--;
                pos = new BlockPos(x, y, z);
            }
            target.teleportTo(x + 0.5, y, z + 0.5);
            target.hurt(this.damageSources().magic(), 2.0F);
            this.discard();
        }
    }
}

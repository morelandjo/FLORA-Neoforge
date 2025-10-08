package flora.core.pulse;

import flora.core.init.FLORAEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityPulsePyrotheum extends EntityPulse {

    public EntityPulsePyrotheum(EntityType<? extends EntityPulsePyrotheum> type, Level level) {
        super(type, level);
    }

    public EntityPulsePyrotheum(Level level, LivingEntity shooter) {
        super(FLORAEntityTypes.PYROTHEUM_PULSE.get(), shooter, Vec3.ZERO, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide && result.getEntity() instanceof LivingEntity target) {
            target.igniteForSeconds(10);
            target.hurt(this.damageSources().inFire(), 3.0F);
            this.discard();
        }
    }
}

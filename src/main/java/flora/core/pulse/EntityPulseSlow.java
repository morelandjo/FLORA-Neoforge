package flora.core.pulse;

import flora.core.init.FLORAEntityTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityPulseSlow extends EntityPulse {

    public EntityPulseSlow(EntityType<? extends EntityPulseSlow> type, Level level) {
        super(type, level);
    }

    public EntityPulseSlow(Level level, LivingEntity shooter) {
        super(FLORAEntityTypes.SLOW_PULSE.get(), shooter, Vec3.ZERO, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide && result.getEntity() instanceof LivingEntity target) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2));
            target.hurt(this.damageSources().magic(), 1.0F);
            this.discard();
        }
    }
}

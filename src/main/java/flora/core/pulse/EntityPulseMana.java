package flora.core.pulse;

import flora.core.init.FLORAEntityTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityPulseMana extends EntityPulse {

    public EntityPulseMana(EntityType<? extends EntityPulseMana> type, Level level) {
        super(type, level);
    }

    public EntityPulseMana(Level level, LivingEntity shooter) {
        super(FLORAEntityTypes.MANA_PULSE.get(), shooter, Vec3.ZERO, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide && result.getEntity() instanceof LivingEntity target) {
            target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
            target.hurt(this.damageSources().magic(), 2.0F);
            this.discard();
        }
    }
}

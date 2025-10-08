package flora.core.pulse;

import flora.core.init.FLORAEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityPulseCoal extends EntityPulse {

    public EntityPulseCoal(EntityType<? extends EntityPulseCoal> type, Level level) {
        super(type, level);
    }

    public EntityPulseCoal(Level level, LivingEntity shooter) {
        super(FLORAEntityTypes.COAL_PULSE.get(), shooter, Vec3.ZERO, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide && result.getEntity() instanceof LivingEntity target) {
            target.hurt(this.damageSources().magic(), 4.0F);
            this.discard();
        }
    }
}

package flora.core.pulse;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class EntityPulse extends AbstractHurtingProjectile {

    protected EntityPulse(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    protected EntityPulse(EntityType<? extends AbstractHurtingProjectile> type, double x, double y, double z, Level level) {
        super(type, x, y, z, level);
    }

    protected EntityPulse(EntityType<? extends AbstractHurtingProjectile> type, LivingEntity shooter, Vec3 movement, Level level) {
        super(type, shooter, movement, level);
    }

    @Override
    public void tick() {
        super.tick();
        // Disable fire rendering
        this.clearFire();
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }
}

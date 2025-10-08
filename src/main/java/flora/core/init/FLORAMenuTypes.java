package flora.core.init;

import flora.core.ConstantsFLORA;
import flora.core.gui.MenuInfuser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLORAMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
        DeferredRegister.create(BuiltInRegistries.MENU, ConstantsFLORA.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<MenuInfuser>> INFUSER =
        MENU_TYPES.register("infuser",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new MenuInfuser(windowId, inv)));
}

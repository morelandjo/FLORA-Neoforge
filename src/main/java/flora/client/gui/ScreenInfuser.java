package flora.client.gui;

import flora.core.ConstantsFLORA;
import flora.core.gui.MenuInfuser;
import flora.core.item.ItemArmorFLORA;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;

public class ScreenInfuser extends AbstractContainerScreen<MenuInfuser> {

    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "textures/gui/infuser.png");

    public ScreenInfuser(MenuInfuser menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        // Draw fluid tank background
        guiGraphics.blit(TEXTURE, 42, 25, 0, this.imageHeight, 116, 14);

        // Draw fluids - read directly from synced slots instead of block entity
        List<FluidStack> tanks = getTotalFluidTanksFromSlots();
        int total = getTotalFluidAmountFromSlots();

        if (total > 0) {
            int currentX = 44;
            List<Component> tooltip = new ArrayList<>();
            int mouseXTranslated = mouseX - leftPos;
            int mouseYTranslated = mouseY - topPos;

            for (FluidStack fluid : tanks) {
                if (!fluid.isEmpty()) {
                    float size = (float) fluid.getAmount() / total;
                    int width = (int) (size * 100);

                    // Make sure the last fluid fills to the end
                    if (tanks.indexOf(fluid) == tanks.size() - 1) {
                        width = 144 - currentX;
                    }

                    drawFluid(guiGraphics, currentX, 27, width, 10, fluid);

                    // Check if mouse is over this fluid
                    if (mouseXTranslated > currentX && mouseXTranslated < (currentX + width) &&
                        mouseYTranslated > 27 && mouseYTranslated < 38) {
                        tooltip.add(Component.literal(fluid.getHoverName().getString()));
                        tooltip.add(Component.literal(fluid.getAmount() + "mB"));
                    }

                    currentX += width;
                }
            }

            // Draw tooltip
            if (!tooltip.isEmpty()) {
                guiGraphics.renderComponentTooltip(this.font, tooltip, mouseX - leftPos, mouseY - topPos + 30);
            }
        }

        // Draw glass overlay
        guiGraphics.blit(TEXTURE, 44, 27, 0, 180, 112, 10);
    }

    /**
     * Reads fluid tanks from menu slots (which are synced to client)
     * instead of from the block entity (which is server-side only)
     */
    private List<FluidStack> getTotalFluidTanksFromSlots() {
        List<FluidStack> fluids = new ArrayList<>();
        // First 4 slots (0-3) are armor slots
        for (int i = 0; i < 4; i++) {
            ItemStack armorStack = menu.getSlot(i).getItem();
            if (!armorStack.isEmpty() && armorStack.getItem() instanceof ItemArmorFLORA armorItem) {
                for (ItemArmorFLORA.FluidTankData tank : armorItem.getFluidTanks(armorStack)) {
                    if (!tank.fluid.isEmpty()) {
                        fluids.add(tank.fluid.copy());
                    }
                }
            }
        }
        return fluids;
    }

    /**
     * Calculates total fluid amount from menu slots (which are synced to client)
     */
    private int getTotalFluidAmountFromSlots() {
        int total = 0;
        // First 4 slots (0-3) are armor slots
        for (int i = 0; i < 4; i++) {
            ItemStack armorStack = menu.getSlot(i).getItem();
            if (!armorStack.isEmpty() && armorStack.getItem() instanceof ItemArmorFLORA armorItem) {
                total += armorItem.getTotalFluidAmount(armorStack);
            }
        }
        return total;
    }

    private void drawFluid(GuiGraphics guiGraphics, int x, int y, int width, int height, FluidStack fluid) {
        if (fluid.isEmpty()) return;

        Fluid fluidType = fluid.getFluid();
        IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(fluidType);
        ResourceLocation stillTexture = props.getStillTexture(fluid);

        if (stillTexture != null) {
            TextureAtlasSprite sprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
            int color = props.getTintColor(fluid);

            int numX = (int) Math.ceil((float) width / 16);

            for (int x2 = 0; x2 < numX; x2++) {
                int w = Math.min(16, width - x2 * 16);
                int tileX = x + x2 * 16;

                guiGraphics.blit(tileX, y, 0, w, height, sprite);
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}

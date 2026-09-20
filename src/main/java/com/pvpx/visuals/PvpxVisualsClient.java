package com.pvpx.visuals;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class PvpxVisualsClient implements ClientModInitializer {

    public static final String MOD_ID = "pvpx_visuals";

    public static final PvpxConfig CONFIG = PvpxConfig.load();

    private static KeyMapping menuKey;

    /*
     * Minecraft 1.21.11 uses a KeyMapping.Category
     * instead of a String for the keybind category.
     */
    private static final KeyMapping.Category PVPX_CATEGORY =
            KeyMapping.Category.register(
                    Component.translatable("key.categories.pvpx_visuals")
            );

    @Override
    public void onInitializeClient() {

        /*
         * RIGHT SHIFT = OPEN VISUALS MENU
         */

        menuKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.pvpx_visuals.open_menu",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_RIGHT_SHIFT,
                        PVPX_CATEGORY
                )
        );

        /*
         * CLIENT TICK
         */

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (menuKey.consumeClick()) {

                client.setScreen(
                        new VisualsScreen(client.screen)
                );
            }

            /*
             * Camera bobbing
             */

            if (client.options != null) {
                client.options.bobView().set(
                        CONFIG.cameraBob
                );

                /*
                 * FOV effects
                 */

                client.options.screenEffectScale().set(
                        CONFIG.fovEffects ? 1.0 : 0.0
                );

                /*
                 * Fullbright
                 */

                client.options.gamma().set(
                        CONFIG.fullbright
                                ? 16.0
                                : CONFIG.savedGamma
                );
            }
        });

        /*
         * HUD
         */

        HudRenderCallback.EVENT.register(
                (guiGraphics, tickDelta) ->
                        renderHud(guiGraphics)
        );
    }

    private static void renderHud(
            GuiGraphics graphics
    ) {

        Minecraft minecraft =
                Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        if (minecraft.options.hideGui) {
            return;
        }

        int width =
                minecraft.getWindow()
                        .getGuiScaledWidth();

        int height =
                minecraft.getWindow()
                        .getGuiScaledHeight();

        /*
         * CUSTOM CROSSHAIR
         */

        if (CONFIG.crosshair) {

            int centerX = width / 2;
            int centerY = height / 2;

            int thickness =
                    Math.max(
                            1,
                            CONFIG.crosshairThickness
                    );

            int size =
                    Math.max(
                            2,
                            CONFIG.crosshairSize
                    );

            int gap =
                    Math.max(
                            1,
                            CONFIG.crosshairGap
                    );

            int color =
                    CONFIG.crosshairColor;

            /*
             * TOP
             */

            graphics.fill(
                    centerX - thickness / 2,
                    centerY - gap - size,
                    centerX + (thickness + 1) / 2,
                    centerY - gap,
                    color
            );

            /*
             * BOTTOM
             */

            graphics.fill(
                    centerX - thickness / 2,
                    centerY + gap,
                    centerX + (thickness + 1) / 2,
                    centerY + gap + size,
                    color
            );

            /*
             * LEFT
             */

            graphics.fill(
                    centerX - gap - size,
                    centerY - thickness / 2,
                    centerX - gap,
                    centerY + (thickness + 1) / 2,
                    color
            );

            /*
             * RIGHT
             */

            graphics.fill(
                    centerX + gap,
                    centerY - thickness / 2,
                    centerX + gap + size,
                    centerY + (thickness + 1) / 2,
                    color
            );
        }

        /*
         * DAMAGE FLASH
         */

        if (
                CONFIG.damageFlash
                        && minecraft.player.hurtTime > 0
        ) {

            float alpha =
                    Math.min(
                            0.45f,
                            minecraft.player.hurtTime / 10.0f
                    );

            int alphaValue =
                    (int) (alpha * 255);

            int flashColor =
                    (alphaValue << 24)
                            | 0x00B00020;

            graphics.fill(
                    0,
                    0,
                    width,
                    height,
                    flashColor
            );
        }

        /*
         * PVPX WATERMARK
         */

        if (CONFIG.watermark) {

            int x = 8;
            int y = 8;

            graphics.fill(
                    x - 4,
                    y - 3,
                    x + 92,
                    y + 13,
                    0x990B0D12
            );

            graphics.drawString(
                    minecraft.font,
                    Component.literal(
                            "PvPX  •  VISUALS"
                    ),
                    x,
                    y,
                    0xFFEAEAF2,
                    true
            );
        }
    }
}

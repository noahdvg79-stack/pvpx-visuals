package com.pvpx.visuals;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class PvpxVisualsClient implements ClientModInitializer {
    public static final String MOD_ID = "pvpx_visuals";
    public static final PvpxConfig CONFIG = PvpxConfig.load();

    private static KeyMapping menuKey;

    @Override
    public void onInitializeClient() {
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.pvpx_visuals.open_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "key.categories.pvpx_visuals"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.consumeClick()) {
                client.setScreen(new VisualsScreen(client.screen));
            }

            if (client.options != null) {
                // Keep vanilla visual options in sync with our toggles.
                client.options.bobView().set(CONFIG.cameraBob);
                client.options.screenEffectScale().set(CONFIG.fovEffects ? 1.0 : 0.0);
                client.options.gamma().set(CONFIG.fullbright ? 16.0 : CONFIG.savedGamma);
            }
        });

        HudRenderCallback.EVENT.register((guiGraphics, tickDelta) -> renderHud(guiGraphics));
    }

    private static void renderHud(GuiGraphics g) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        int w = mc.getWindow().getGuiScaledWidth();
        int h = mc.getWindow().getGuiScaledHeight();

        if (CONFIG.crosshair) {
            int cx = w / 2;
            int cy = h / 2;
            int t = Math.max(1, CONFIG.crosshairThickness);
            int s = Math.max(2, CONFIG.crosshairSize);
            int gap = Math.max(1, CONFIG.crosshairGap);
            int c = CONFIG.crosshairColor;

            // Four clean PvP crosshair bars.
            g.fill(cx - t / 2, cy - gap - s, cx + (t + 1) / 2, cy - gap, c);
            g.fill(cx - t / 2, cy + gap, cx + (t + 1) / 2, cy + gap + s, c);
            g.fill(cx - gap - s, cy - t / 2, cx - gap, cy + (t + 1) / 2, c);
            g.fill(cx + gap, cy - t / 2, cx + gap + s, cy + (t + 1) / 2, c);
        }

        if (CONFIG.damageFlash && mc.player.hurtTime > 0) {
            float alpha = Math.min(0.45f, mc.player.hurtTime / 10.0f);
            int a = ((int)(alpha * 255) << 24) | 0xB00020;
            g.fill(0, 0, w, h, a);
        }

        if (CONFIG.watermark) {
            int x = 8;
            int y = 8;
            g.fill(x - 4, y - 3, x + 82, y + 13, 0x990B0D12);
            g.drawString(mc.font, Component.literal("PvPX  •  VISUALS"), x, y, 0xFFEAEAF2, true);
        }
    }
}

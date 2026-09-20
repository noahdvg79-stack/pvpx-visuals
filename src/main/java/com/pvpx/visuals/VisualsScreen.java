package com.pvpx.visuals;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.SliderButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class VisualsScreen extends Screen {
    private final Screen parent;

    public VisualsScreen(Screen parent) {
        super(Component.translatable("screen.pvpx_visuals.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int left = cx - 150;
        int right = cx + 10;
        int y = 52;

        addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("screen.pvpx_visuals.enabled"),
                        Component.translatable("screen.pvpx_visuals.disabled"))
                .withInitialValue(PvpxVisualsClient.CONFIG.crosshair)
                .create(left, y, 140, 20, Component.translatable("screen.pvpx_visuals.crosshair"),
                        (b, value) -> { PvpxVisualsClient.CONFIG.crosshair = value; PvpxVisualsClient.CONFIG.save(); }));

        addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("screen.pvpx_visuals.enabled"),
                        Component.translatable("screen.pvpx_visuals.disabled"))
                .withInitialValue(PvpxVisualsClient.CONFIG.damageFlash)
                .create(right, y, 140, 20, Component.translatable("screen.pvpx_visuals.damage_flash"),
                        (b, value) -> { PvpxVisualsClient.CONFIG.damageFlash = value; PvpxVisualsClient.CONFIG.save(); }));

        y += 28;
        addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("screen.pvpx_visuals.enabled"),
                        Component.translatable("screen.pvpx_visuals.disabled"))
                .withInitialValue(PvpxVisualsClient.CONFIG.fullbright)
                .create(left, y, 140, 20, Component.translatable("screen.pvpx_visuals.fullbright"),
                        (b, value) -> { PvpxVisualsClient.CONFIG.fullbright = value; PvpxVisualsClient.CONFIG.save(); }));

        addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("screen.pvpx_visuals.enabled"),
                        Component.translatable("screen.pvpx_visuals.disabled"))
                .withInitialValue(PvpxVisualsClient.CONFIG.cameraBob)
                .create(right, y, 140, 20, Component.translatable("screen.pvpx_visuals.camera_bob"),
                        (b, value) -> { PvpxVisualsClient.CONFIG.cameraBob = value; PvpxVisualsClient.CONFIG.save(); }));

        y += 28;
        addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("screen.pvpx_visuals.enabled"),
                        Component.translatable("screen.pvpx_visuals.disabled"))
                .withInitialValue(PvpxVisualsClient.CONFIG.fovEffects)
                .create(left, y, 140, 20, Component.translatable("screen.pvpx_visuals.fov_effects"),
                        (b, value) -> { PvpxVisualsClient.CONFIG.fovEffects = value; PvpxVisualsClient.CONFIG.save(); }));

        addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("screen.pvpx_visuals.enabled"),
                        Component.translatable("screen.pvpx_visuals.disabled"))
                .withInitialValue(PvpxVisualsClient.CONFIG.particles)
                .create(right, y, 140, 20, Component.translatable("screen.pvpx_visuals.particles"),
                        (b, value) -> { PvpxVisualsClient.CONFIG.particles = value; PvpxVisualsClient.CONFIG.save(); }));

        y += 28;
        addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("screen.pvpx_visuals.enabled"),
                        Component.translatable("screen.pvpx_visuals.disabled"))
                .withInitialValue(PvpxVisualsClient.CONFIG.watermark)
                .create(left, y, 140, 20, Component.translatable("screen.pvpx_visuals.watermark"),
                        (b, value) -> { PvpxVisualsClient.CONFIG.watermark = value; PvpxVisualsClient.CONFIG.save(); }));

        y += 34;
        addRenderableWidget(new SliderButton(left, y, 140, 20,
                Component.translatable("screen.pvpx_visuals.crosshair_size"),
                (PvpxVisualsClient.CONFIG.crosshairSize - 2) / 10.0,
                0.0, 1.0, false) {
            @Override protected void updateMessage() {
                this.setMessage(Component.translatable("screen.pvpx_visuals.crosshair_size")
                        .append(": " + PvpxVisualsClient.CONFIG.crosshairSize));
            }
            @Override protected void applyValue() {
                PvpxVisualsClient.CONFIG.crosshairSize = 2 + (int)Math.round(this.value * 10);
                PvpxVisualsClient.CONFIG.save();
            }
        });

        addRenderableWidget(new SliderButton(right, y, 140, 20,
                Component.translatable("screen.pvpx_visuals.crosshair_gap"),
                (PvpxVisualsClient.CONFIG.crosshairGap - 1) / 7.0,
                0.0, 1.0, false) {
            @Override protected void updateMessage() {
                this.setMessage(Component.translatable("screen.pvpx_visuals.crosshair_gap")
                        .append(": " + PvpxVisualsClient.CONFIG.crosshairGap));
            }
            @Override protected void applyValue() {
                PvpxVisualsClient.CONFIG.crosshairGap = 1 + (int)Math.round(this.value * 7);
                PvpxVisualsClient.CONFIG.save();
            }
        });

        y += 28;
        addRenderableWidget(Button.builder(Component.translatable("screen.pvpx_visuals.reset"),
                b -> { PvpxVisualsClient.CONFIG.reset(); this.minecraft.setScreen(new VisualsScreen(parent)); })
                .bounds(left, y, 140, 20).build());

        addRenderableWidget(Button.builder(Component.translatable("screen.pvpx_visuals.close"),
                b -> this.onClose()).bounds(right, y, 140, 20).build());
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) this.minecraft.setScreen(parent);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(0, 0, this.width, this.height, 0xB90A0B10);
        graphics.fill(this.width / 2 - 165, 24, this.width / 2 + 165, this.height - 22, 0xF012141C);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 34, 0xFFFFFFFF);
        super.render(graphics, mouseX, mouseY, delta);
    }
}

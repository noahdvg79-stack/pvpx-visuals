package com.pvpx.visuals;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
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
        int center = this.width / 2;

        int left = center - 150;
        int right = center + 10;

        int y = 55;

        /*
         * CROSSHAIR
         */

        addRenderableWidget(
                booleanButton(
                        left,
                        y,
                        Component.translatable("screen.pvpx_visuals.crosshair"),
                        PvpxVisualsClient.CONFIG.crosshair,
                        value -> {
                            PvpxVisualsClient.CONFIG.crosshair = value;
                            PvpxVisualsClient.CONFIG.save();
                        }
                )
        );

        /*
         * DAMAGE FLASH
         */

        addRenderableWidget(
                booleanButton(
                        right,
                        y,
                        Component.translatable("screen.pvpx_visuals.damage_flash"),
                        PvpxVisualsClient.CONFIG.damageFlash,
                        value -> {
                            PvpxVisualsClient.CONFIG.damageFlash = value;
                            PvpxVisualsClient.CONFIG.save();
                        }
                )
        );

        y += 28;

        /*
         * FULLBRIGHT
         */

        addRenderableWidget(
                booleanButton(
                        left,
                        y,
                        Component.translatable("screen.pvpx_visuals.fullbright"),
                        PvpxVisualsClient.CONFIG.fullbright,
                        value -> {
                            PvpxVisualsClient.CONFIG.fullbright = value;
                            PvpxVisualsClient.CONFIG.save();
                        }
                )
        );

        /*
         * CAMERA BOBBING
         */

        addRenderableWidget(
                booleanButton(
                        right,
                        y,
                        Component.translatable("screen.pvpx_visuals.camera_bob"),
                        PvpxVisualsClient.CONFIG.cameraBob,
                        value -> {
                            PvpxVisualsClient.CONFIG.cameraBob = value;
                            PvpxVisualsClient.CONFIG.save();
                        }
                )
        );

        y += 28;

        /*
         * FOV EFFECTS
         */

        addRenderableWidget(
                booleanButton(
                        left,
                        y,
                        Component.translatable("screen.pvpx_visuals.fov_effects"),
                        PvpxVisualsClient.CONFIG.fovEffects,
                        value -> {
                            PvpxVisualsClient.CONFIG.fovEffects = value;
                            PvpxVisualsClient.CONFIG.save();
                        }
                )
        );

        /*
         * PARTICLES
         */

        addRenderableWidget(
                booleanButton(
                        right,
                        y,
                        Component.translatable("screen.pvpx_visuals.particles"),
                        PvpxVisualsClient.CONFIG.particles,
                        value -> {
                            PvpxVisualsClient.CONFIG.particles = value;
                            PvpxVisualsClient.CONFIG.save();
                        }
                )
        );

        y += 28;

        /*
         * WATERMARK
         */

        addRenderableWidget(
                booleanButton(
                        left,
                        y,
                        Component.translatable("screen.pvpx_visuals.watermark"),
                        PvpxVisualsClient.CONFIG.watermark,
                        value -> {
                            PvpxVisualsClient.CONFIG.watermark = value;
                            PvpxVisualsClient.CONFIG.save();
                        }
                )
        );

        /*
         * CROSSHAIR SIZE
         */

        addRenderableWidget(
                Button.builder(
                        Component.translatable("screen.pvpx_visuals.crosshair_size")
                                .append(": " + PvpxVisualsClient.CONFIG.crosshairSize),
                        button -> {
                            PvpxVisualsClient.CONFIG.crosshairSize++;

                            if (PvpxVisualsClient.CONFIG.crosshairSize > 12) {
                                PvpxVisualsClient.CONFIG.crosshairSize = 2;
                            }

                            PvpxVisualsClient.CONFIG.save();
                            button.setMessage(
                                    Component.translatable("screen.pvpx_visuals.crosshair_size")
                                            .append(": " + PvpxVisualsClient.CONFIG.crosshairSize)
                            );
                        }
                ).bounds(
                        right,
                        y,
                        140,
                        20
                ).build()
        );

        y += 28;

        /*
         * CROSSHAIR GAP
         */

        addRenderableWidget(
                Button.builder(
                        Component.translatable("screen.pvpx_visuals.crosshair_gap")
                                .append(": " + PvpxVisualsClient.CONFIG.crosshairGap),
                        button -> {
                            PvpxVisualsClient.CONFIG.crosshairGap++;

                            if (PvpxVisualsClient.CONFIG.crosshairGap > 8) {
                                PvpxVisualsClient.CONFIG.crosshairGap = 1;
                            }

                            PvpxVisualsClient.CONFIG.save();
                            button.setMessage(
                                    Component.translatable("screen.pvpx_visuals.crosshair_gap")
                                            .append(": " + PvpxVisualsClient.CONFIG.crosshairGap)
                            );
                        }
                ).bounds(
                        left,
                        y,
                        140,
                        20
                ).build()
        );

        /*
         * CROSSHAIR THICKNESS
         */

        addRenderableWidget(
                Button.builder(
                        Component.translatable("screen.pvpx_visuals.crosshair_thickness")
                                .append(": " + PvpxVisualsClient.CONFIG.crosshairThickness),
                        button -> {
                            PvpxVisualsClient.CONFIG.crosshairThickness++;

                            if (PvpxVisualsClient.CONFIG.crosshairThickness > 4) {
                                PvpxVisualsClient.CONFIG.crosshairThickness = 1;
                            }

                            PvpxVisualsClient.CONFIG.save();
                            button.setMessage(
                                    Component.translatable("screen.pvpx_visuals.crosshair_thickness")
                                            .append(": " + PvpxVisualsClient.CONFIG.crosshairThickness)
                            );
                        }
                ).bounds(
                        right,
                        y,
                        140,
                        20
                ).build()
        );

        y += 32;

        /*
         * RESET
         */

        addRenderableWidget(
                Button.builder(
                        Component.translatable("screen.pvpx_visuals.reset"),
                        button -> {
                            PvpxVisualsClient.CONFIG.reset();

                            Minecraft minecraft = this.minecraft;

                            if (minecraft != null) {
                                minecraft.setScreen(new VisualsScreen(this.parent));
                            }
                        }
                ).bounds(
                        left,
                        y,
                        140,
                        20
                ).build()
        );

        /*
         * CLOSE
         */

        addRenderableWidget(
                Button.builder(
                        Component.translatable("screen.pvpx_visuals.close"),
                        button -> this.onClose()
                ).bounds(
                        right,
                        y,
                        140,
                        20
                ).build()
        );
    }

    private CycleButton<Boolean> booleanButton(
            int x,
            int y,
            Component label,
            boolean initialValue,
            java.util.function.Consumer<Boolean> onChange
    ) {
        return CycleButton.booleanBuilder(
                Component.translatable("screen.pvpx_visuals.enabled"),
                Component.translatable("screen.pvpx_visuals.disabled"),
                initialValue
        ).create(
                x,
                y,
                140,
                20,
                label,
                (button, value) -> onChange.accept(value)
        );
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.parent);
        }
    }

    @Override
    public void render(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float delta
    ) {
        /*
         * Background
         */

        graphics.fill(
                0,
                0,
                this.width,
                this.height,
                0xB90A0B10
        );

        /*
         * Main panel
         */

        int panelLeft = this.width / 2 - 165;
        int panelRight = this.width / 2 + 165;

        graphics.fill(
                panelLeft,
                24,
                panelRight,
                this.height - 22,
                0xF012141C
        );

        /*
         * Title
         */

        graphics.drawCenteredString(
                this.font,
                this.title,
                this.width / 2,
                34,
                0xFFFFFFFF
        );

        /*
         * Subtitle
         */

        graphics.drawCenteredString(
                this.font,
                Component.literal("PvP Visual Settings"),
                this.width / 2,
                43,
                0xFF8F96A8
        );

        super.render(
                graphics,
                mouseX,
                mouseY,
                delta
        );
    }
}

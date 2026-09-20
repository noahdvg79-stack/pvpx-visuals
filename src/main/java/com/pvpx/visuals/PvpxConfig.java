package com.pvpx.visuals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import net.fabricmc.loader.api.FabricLoader;

public final class PvpxConfig {
    public boolean crosshair = true;
    public boolean damageFlash = true;
    public boolean fullbright = false;
    public boolean cameraBob = false;
    public boolean fovEffects = false;
    public boolean particles = true;
    public boolean watermark = true;

    public int crosshairSize = 4;
    public int crosshairGap = 2;
    public int crosshairThickness = 1;
    public int crosshairColor = 0xFFFFFFFF;
    public double savedGamma = 1.0;

    private static Path path() {
        return FabricLoader.getInstance().getConfigDir().resolve("pvpx-visuals.properties");
    }

    public static PvpxConfig load() {
        PvpxConfig c = new PvpxConfig();
        Path p = path();
        if (!Files.exists(p)) {
            c.save();
            return c;
        }
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(p)) {
            props.load(in);
            c.crosshair = Boolean.parseBoolean(props.getProperty("crosshair", "true"));
            c.damageFlash = Boolean.parseBoolean(props.getProperty("damageFlash", "true"));
            c.fullbright = Boolean.parseBoolean(props.getProperty("fullbright", "false"));
            c.cameraBob = Boolean.parseBoolean(props.getProperty("cameraBob", "false"));
            c.fovEffects = Boolean.parseBoolean(props.getProperty("fovEffects", "false"));
            c.particles = Boolean.parseBoolean(props.getProperty("particles", "true"));
            c.watermark = Boolean.parseBoolean(props.getProperty("watermark", "true"));
            c.crosshairSize = Integer.parseInt(props.getProperty("crosshairSize", "4"));
            c.crosshairGap = Integer.parseInt(props.getProperty("crosshairGap", "2"));
            c.crosshairThickness = Integer.parseInt(props.getProperty("crosshairThickness", "1"));
            c.crosshairColor = Integer.parseUnsignedInt(props.getProperty("crosshairColor", "4294967295"));
            c.savedGamma = Double.parseDouble(props.getProperty("savedGamma", "1.0"));
        } catch (Exception ignored) {
            c = new PvpxConfig();
        }
        return c;
    }

    public void save() {
        Properties props = new Properties();
        props.setProperty("crosshair", Boolean.toString(crosshair));
        props.setProperty("damageFlash", Boolean.toString(damageFlash));
        props.setProperty("fullbright", Boolean.toString(fullbright));
        props.setProperty("cameraBob", Boolean.toString(cameraBob));
        props.setProperty("fovEffects", Boolean.toString(fovEffects));
        props.setProperty("particles", Boolean.toString(particles));
        props.setProperty("watermark", Boolean.toString(watermark));
        props.setProperty("crosshairSize", Integer.toString(crosshairSize));
        props.setProperty("crosshairGap", Integer.toString(crosshairGap));
        props.setProperty("crosshairThickness", Integer.toString(crosshairThickness));
        props.setProperty("crosshairColor", Long.toUnsignedString(Integer.toUnsignedLong(crosshairColor)));
        props.setProperty("savedGamma", Double.toString(savedGamma));
        try {
            Files.createDirectories(path().getParent());
            try (OutputStream out = Files.newOutputStream(path())) {
                props.store(out, "PvPX Visuals configuration");
            }
        } catch (IOException ignored) {}
    }

    public void reset() {
        crosshair = true;
        damageFlash = true;
        fullbright = false;
        cameraBob = false;
        fovEffects = false;
        particles = true;
        watermark = true;
        crosshairSize = 4;
        crosshairGap = 2;
        crosshairThickness = 1;
        crosshairColor = 0xFFFFFFFF;
        save();
    }
}

package klaxon.klaxon.gregtoriooverlays;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

// This enum defines all the mixins to load
public enum GOMixins {

    // Add the snitch! This mixin updates the client every 60s on all pollution everywhere
    INSERT_POLLUTION_REPORTER(new Builder("Insert Pollution Reporter")
            .addMixinClasses("gregtech.GT_PollutionMixin")
            .setSide(Side.BOTH)
            .addTargetedMod(TargetedMod.GT5U)
            .setPhase(Phase.LATE));

    public final String name;
    public final List<String> mixinClasses;
    public final Phase phase;
    private final Side side;
    public final List<TargetedMod> targetedMods;

    private static class Builder {

        private final String name;
        private final List<String> mixinClasses = new ArrayList<>();
        private Supplier<Boolean> applyIf;
        private Side side = Side.BOTH;
        private Phase phase = Phase.LATE;
        private final List<TargetedMod> targetedMods = new ArrayList<>();
        private final List<TargetedMod> excludedMods = new ArrayList<>();

        public Builder(String name) {
            this.name = name;
        }

        public Builder addMixinClasses(String... mixinClasses) {
            this.mixinClasses.addAll(Arrays.asList(mixinClasses));
            return this;
        }

        public Builder setPhase(Phase phase) {
            this.phase = phase;
            return this;
        }

        public Builder setSide(Side side) {
            this.side = side;
            return this;
        }

        public Builder addTargetedMod(TargetedMod mod) {
            this.targetedMods.add(mod);
            return this;
        }
    }

    GOMixins(Builder builder) {
        this.name = builder.name;
        this.mixinClasses = builder.mixinClasses;
        this.side = builder.side;
        this.targetedMods = builder.targetedMods;
        this.phase = builder.phase;
        if (this.targetedMods.isEmpty()) {
            throw new RuntimeException("No targeted mods specified for " + this.name);
        }
    }

    private boolean shouldLoadSide() {
        return (side == Side.BOTH
                || (side == Side.SERVER && FMLLaunchHandler.side().isServer())
                || (side == Side.CLIENT && FMLLaunchHandler.side().isClient()));
    }

    enum Side {
        BOTH,
        CLIENT,
        SERVER
    }

    public enum Phase {
        EARLY,
        LATE,
    }
}

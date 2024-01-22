package klaxon.klaxon.goverlays;

public class Constants {

    // Text font constants
    public static int TEXT_BG_COLOR = 0x000000;
    public static int TEXT_BG_ALPHA = 180;
    public static int TEXT_COLOR = 0xFFFFFF;
    public static int TEXT_ALPHA = 255;

    // Won't change unless I decide to do serious shenanigans later.
    public static int CHUNK_SIZE = 16;

    // These values are based on the pollution effects in GT5U-GTNH
    // They are undergoing a major rework and still subject to change
    // This is current as of 5.09.41.132-dev
    public enum EffectSteps {

        POLLUTION_1(500_000), // Smog
        POLLUTION_2(750_000), // Poison
        POLLUTION_3(1_000_000), // Dying Plants
        POLLUTION_MAX(1_500_000); // Sour Rain

        public final int pollution;

        EffectSteps(int pollution) {
            this.pollution = pollution;
        }
    }
}

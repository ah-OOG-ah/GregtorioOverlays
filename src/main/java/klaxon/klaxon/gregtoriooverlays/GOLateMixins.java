package klaxon.klaxon.gregtoriooverlays;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Most this is shamelessly copied from Hodgepodge, thank you, Mitch!
@LateMixin
public class GOLateMixins implements ILateMixinLoader {

    // This class SHOULD load late mixins.
    // GTNHMixins should load it, GO does not reference this class.

    // Returns the LateMixin config JSON
    @Override
    public String getMixinConfig() { return "mixins.gregtoriooverlays.late.json"; }

    // Returns the actual mixins to load
    @Override
    public List<String> getMixins(Set<String> loadedMods) {

        // List of mixins
        final List<String> mixins = new ArrayList<>();

        // For each mixin...
        for (GOMixins mixin : GOMixins.values()) {

            // If late
            if (mixin.phase == GOMixins.Phase.LATE) {

                // Add them to mixins
                mixins.addAll(mixin.mixinClasses);
            }
        }

        // Return a list of mixins!
        return mixins;
    }
}

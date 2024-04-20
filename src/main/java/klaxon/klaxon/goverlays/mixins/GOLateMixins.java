package klaxon.klaxon.goverlays.mixins;

import java.util.List;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

// Most this is shamelessly copied from Hodgepodge, thank you, Mitch!
@LateMixin
public class GOLateMixins implements ILateMixinLoader {

    // This class SHOULD load late mixins.
    // GTNHMixins should load it, GO does not reference this class.

    // Returns the LateMixin config JSON
    @Override
    public String getMixinConfig() {
        return "mixins.goverlays.late.json";
    }

    // Returns the actual mixins to load
    @Override
    public List<String> getMixins(Set<String> loadedMods) {

        return Mixins.getLateMixins(loadedMods);
    }
}

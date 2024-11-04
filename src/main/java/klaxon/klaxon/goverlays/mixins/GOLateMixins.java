/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2023-2024 ah-OOG-ah
 * Copyright (C) 2021-2023 various authors (from Hodgepodge)
 *
 * GregtorioOverlays is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GregtorioOverlays is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package klaxon.klaxon.goverlays.mixins;

import java.util.List;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

// Most this is shamelessly copied from Hodgepodge; thank you, Mitch!
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

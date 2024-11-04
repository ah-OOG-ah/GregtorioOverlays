## Gregtorio Overlays
Adds Factorio-style overlays to GT5u-1.7.10, using VisualProspecting's API.

### TODO:
- Add two modes to future pollution cache: a universal one, gated by a satellite, and one updated by hand/machine
  available pre-Ev
- Run a profiler to make sure that this isn't a resource hog

### Libraries/Dependecies
- GT5u from GTNH, for the pollution. This is actually an optional dependency now, feel free to PR additional backends
- Journeymap for the map.
- Navigator for the map-editing API.
- GTNHLib for the config system and bundled fastutil.
- Unimixins for... mixins.

### Other credits
Because mcmod.info doesn't wrap lines
- The GTNH team and many others, for the boilerplate code and a working toolchain.

### Licensing
GregtorioOverlays is copyrighted by ah-OOG-ah, 2024, and distributed under the LGPLv3 or any later version. License
headers must be applied to new files and updated to existing ones as per [the template](/license-header-template.txt).
The LGPLv3 by itself is in [LICENSE](/LICENSE), but the GPLv3 and LGPLv3 are also in [COPYING](/COPYING) and
[COPYING.LESSER](/COPYING.LESSER) as per FSF convention.

Some third-party code was used in this project. See [3RD-PARTY-LICENSES](/3RD-PARTY-LICENCES) for details.

All four of these files should be kept in sync with the ones in [src/main/resources](/src/main/resources). I would just
symlink them, but that may cause issues for people checking out the repository on different platforms.

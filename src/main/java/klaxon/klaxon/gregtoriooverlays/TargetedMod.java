package klaxon.klaxon.gregtoriooverlays;

public enum TargetedMod {

    GT5U("GregTech5u", null, "gregtech"); // Also matches GT6.

    public final String modName;
    public final String coreModClass;
    public final String modId;

    TargetedMod(String modName, String coreModClass, String modId) {
        this.modName = modName;
        this.coreModClass = coreModClass;
        this.modId = modId;
    }

    @Override
    public String toString() {
        return "TargetedMod{modName='" + modName + "', coreModClass='" + coreModClass + "', modId='" + modId + "'}";
    }
}

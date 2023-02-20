package klaxon.klaxon.gregtoriooverlays.utils;

public class FancyText {

    public enum PrefixType {
        SI,
        BINARY
    }

    public static String[] siPrefixes = {"", "k", "M", "G", "T", "P", "E", "Z", "Y", "R", "Q"};

    // robi- (Ri) and quebi- (Qi) aren't official yet as of Nov. 2022, adding them because why not
    public static String[] binaryPrefixes = {"Ki", "Mi", "Gi", "Ti", "Pi", "Ei", "Zi", "Yi", "Ri", "Qi"};
}

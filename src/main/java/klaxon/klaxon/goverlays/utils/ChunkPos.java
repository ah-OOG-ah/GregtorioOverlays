package klaxon.klaxon.goverlays.utils;

public class ChunkPos {
    public static int getX(long pos) { return (int) (pos >> 32); }
    public static int getZ(long pos) { return (int) pos; }
}

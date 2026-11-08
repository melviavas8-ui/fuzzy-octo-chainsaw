package daamky.core;





public final class Rot {
    public static boolean active;
    public static float yaw, pitch;

    public static boolean moveFix;
    private Rot() {}
    public static void set(float y, float p) { yaw = y; pitch = p; active = true; }
    public static void clear() { active = false; }
}

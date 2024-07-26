package me.combimagnetron.comet.util;

public class Version {
    private final int major;
    private int minor = -1;
    private String patch = "";

    protected Version(int major, int minor, String patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public static Version major(int major) {
        return new Version(major, -1, "");
    }

    public static Version parse(String version) {
        String[] split = version.split("\\.");
        int major = Integer.parseInt(split[0]);
        int minor = split.length > 1 ? Integer.parseInt(split[1]) : -1;
        String patch = split.length > 2 ? split[2] : "";
        return new Version(major, minor, patch);

    }

    public Version minor(int minor) {
        this.minor = minor;
        return this;
    }

    public Version patch(String patch) {
        this.patch = patch;
        return this;
    }

    public int major() {
        return major;
    }

    public int minor() {
        return minor;
    }

    public String patch() {
        return patch;
    }

    public String version() {
        String seperator = (minor == -1 && patch.equals("")) ? "" : ".";
        String seperatorPatch = patch.equals("") ? "" : ".";
        return major + seperator + minor + seperatorPatch + patch;
    }


}

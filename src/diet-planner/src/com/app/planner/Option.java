package com.app.planner;

public class Option {

    private ThemeType CSSFile;
    private final String CSSDirectory = "com/app/planner/";
    private String releaseVersion = "1.0";
    private float volume;

    public Option(ThemeType CSSFile) {
        this.CSSFile = CSSFile;
    }

    public Option() {
    }

    public ThemeType getCSSFile() {
        return CSSFile;
    }

    public void setCSSFile(ThemeType CSSFile) {
        this.CSSFile = CSSFile;
    }

    public String getCSSDirectory() {
        return CSSDirectory;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}

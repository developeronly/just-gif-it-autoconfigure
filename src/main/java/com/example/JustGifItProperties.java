package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

@ConfigurationProperties(prefix = "com.example")
public class JustGifItProperties {

    private File gifLocation;
    private Boolean optimize;

    public File getGifLocation() {
        return gifLocation;
    }

    public void setGifLocation(File gifLocation) {
        this.gifLocation = gifLocation;
    }

    public Boolean getOptimize() {
        return optimize;
    }

    public void setOptimize(Boolean optimize) {
        this.optimize = optimize;
    }

}

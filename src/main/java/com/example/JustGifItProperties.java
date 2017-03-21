package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.example")
public class JustGifItProperties {

    private String gifLocation;
    private Boolean optimize;

    public String getGifLocation() {
        return gifLocation;
    }

    public void setGifLocation(String gifLocation) {
        this.gifLocation = gifLocation;
    }

    public Boolean getOptimize() {
        return optimize;
    }

    public void setOptimize(Boolean optimize) {
        this.optimize = optimize;
    }
    
}

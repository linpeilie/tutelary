package com.tutelary.common.config;

import java.util.Objects;

public class TutelaryAgentProperties {

    private String appName;
    private String arthasVersion;
    private String tutelaryVersion;
    private String tutelaryServerUrl;
    private Long startup;

    private Integer connectTimeout;

    private String tutelaryWorkspace;

    private String applicationBasePackage;


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getArthasVersion() {
        return arthasVersion;
    }

    public void setArthasVersion(String arthasVersion) {
        this.arthasVersion = arthasVersion;
    }

    public String getTutelaryVersion() {
        return tutelaryVersion;
    }

    public void setTutelaryVersion(String tutelaryVersion) {
        this.tutelaryVersion = tutelaryVersion;
    }

    public Long getStartup() {
        return startup;
    }

    public void setStartup(Long startup) {
        this.startup = startup;
    }

    public String getTutelaryServerUrl() {
        return tutelaryServerUrl;
    }

    public void setTutelaryServerUrl(String tutelaryServerUrl) {
        this.tutelaryServerUrl = tutelaryServerUrl;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getTutelaryWorkspace() {
        return tutelaryWorkspace;
    }

    public void setTutelaryWorkspace(String tutelaryWorkspace) {
        this.tutelaryWorkspace = tutelaryWorkspace;
    }

    public String getApplicationBasePackage() {
        return applicationBasePackage;
    }

    public void setApplicationBasePackage(String applicationBasePackage) {
        this.applicationBasePackage = applicationBasePackage;
    }

    @Override
    public String toString() {
        return "TutelaryAgentProperties{" +
                "appName='" + appName + '\'' +
                ", arthasVersion='" + arthasVersion + '\'' +
                ", tutelaryVersion='" + tutelaryVersion + '\'' +
                ", tutelaryServerUrl='" + tutelaryServerUrl + '\'' +
                ", startup=" + startup +
                ", connectTimeout=" + connectTimeout +
                ", tutelaryWorkspace='" + tutelaryWorkspace + '\'' +
                ", applicationBasePackage='" + applicationBasePackage + '\'' +
                '}';
    }
}

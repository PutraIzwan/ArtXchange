package com.artxchange.artxchange.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "firebase")
public class FirebaseProperties {
    private String projectId;
    private String databaseUrl;

    // Getters and setters
    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }
    public String getDatabaseUrl() { return databaseUrl; }
    public void setDatabaseUrl(String databaseUrl) { this.databaseUrl = databaseUrl; }
}
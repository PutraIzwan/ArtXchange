package com.artxchange.artxchange.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @PostConstruct
    public void initializeFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) { // Check if already initialized
                InputStream serviceAccount = getClass().getClassLoader()
                        .getResourceAsStream("serviceAccountKey.json");

                if (serviceAccount == null) {
                    throw new IllegalStateException("Firebase service account file not found");
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                logger.info("Firebase application initialized successfully");
            }
        } catch (IOException e) {
            logger.error("Firebase initialization failed: ", e);
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }
}
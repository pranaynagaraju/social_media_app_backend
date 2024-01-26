package com.techbypranay.socialmediaapp.firebaseconfig;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    private static final String SERVICE_ACCOUNT_KEY_PATH = "src/main/resources/serviceAccountKey.json";

    @Bean
    public FirebaseApp firebaseApp() {
        return initializeFirebaseApp();
    }

    private synchronized FirebaseApp initializeFirebaseApp() {
        try (FileInputStream serviceAccount = new FileInputStream(SERVICE_ACCOUNT_KEY_PATH)) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new RuntimeException("Error initializing Firebase", e);
        }
    }
}

package me.wingert.vocabularybuilder.VocabularyBuilder.authentication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;


// Creates a Spring bean of configuration stereotype.
@Configuration

// Creates a binding between a configuration entity class and Spring configuration
// stereotype so that after injection within a service, properties can be retrieved
// easily.
@EnableConfigurationProperties

// Used to bind a class with an externalized property file. Used to separate out bean
// classes with configuration entity classes.
@ConfigurationProperties(prefix="firebase")

public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @Primary
    @Bean
    public void firebaseInit()
    {
        InputStream in = null;
        try
        {
            in = new ClassPathResource("firebase_config.json").getInputStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(in))
                    .build();

            if (FirebaseApp.getApps().isEmpty())
            {
                FirebaseApp.initializeApp(firebaseOptions);
            }

            logger.debug("Firebase initialized");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}

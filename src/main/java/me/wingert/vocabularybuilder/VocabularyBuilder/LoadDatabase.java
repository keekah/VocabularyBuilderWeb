package me.wingert.vocabularybuilder.VocabularyBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

//    @Bean
//    CommandLineRunner initDatabase(VocabWordRepository repository) {
//        return args -> {
//            log.info("Preloading " + repository.save(new VocabularyWord("pabulum", "intellectual sustenance; food")));
//            log.info("Preloading " + repository.save(new VocabularyWord("exculpate", "to clear from alleged fault or guilt")));
//        };
//    }
}

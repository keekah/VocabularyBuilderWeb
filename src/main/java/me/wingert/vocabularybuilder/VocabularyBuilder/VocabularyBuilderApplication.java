package me.wingert.vocabularybuilder.VocabularyBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VocabularyBuilderApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(VocabularyBuilderApplication.class);
		logger.info("Calling SpringApplication.run(VocabularyBuilderApplication)");
		SpringApplication.run(VocabularyBuilderApplication.class, args);
	}

}

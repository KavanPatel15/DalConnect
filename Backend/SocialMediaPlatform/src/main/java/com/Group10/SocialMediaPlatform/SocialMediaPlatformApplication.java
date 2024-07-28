package com.Group10.SocialMediaPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Spring Boot application.
 * It uses the @SpringBootApplication annotation to enable
 * auto-configuration, component scanning, and configuration
 * for the application.
 */
@SpringBootApplication
public class SocialMediaPlatformApplication {

	/**
	 * The main method that launches the Spring Boot application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SocialMediaPlatformApplication.class, args);
	}
}

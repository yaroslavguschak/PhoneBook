package com.github.yaroslavguschak;

import com.github.yaroslavguschak.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class PhoneBookApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(PhoneBookApplication.class);

		String filename = System.getProperty("lardi.conf");
		checkFileNameParam(filename);
		Properties properties = getProfile(filename);
		System.setProperty("fullpath.to.file", filename);
		String profile = properties.getProperty("profile");
		System.out.println("==== PROFILE: " + profile + "======");



		app.setAdditionalProfiles(profile);

		ConfigurableApplicationContext ctx = app.run(args);

	}

	private static void checkFileNameParam(String filename)
	{
		//Just param
		if (filename == null)
			throw new IllegalArgumentException("Argument 'filename' cannot be null");
	}
	private static Properties getProfile(String filename)
	{
		Properties result = new Properties();
		try {
			try (FileInputStream is = new FileInputStream(filename))
			{
				result.load(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}




}

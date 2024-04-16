package com.safetynet.alerts;

import com.safetynet.alerts.model.AllInfo;
import com.safetynet.alerts.utils.LoadData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class SafetyNetAlertsApplication {

	private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

	@Value("${info.data}")
	private String dataFile;

	public static void main(final String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Bean
	public AllInfo jsonFileLoader() throws IOException {
		logger.debug("Loading Data.json file to memory... ");
		return LoadData.readJsonFile(dataFile);
	}
}

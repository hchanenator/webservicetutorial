/**
 * 
 */
package org.springframework.gs_consuming_web_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hello.wsdl.GetCityForecastByZIPResponse;

/**
 * @author herb
 *
 */
@SpringBootApplication
public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class);

	}
	
	@Bean
	CommandLineRunner lookup(WeatherClient weatherClient) {
		return args -> {
			String zipCode = "94304";
			
			if (args.length > 0) {
				System.out.println("Using " + args[0]);
				zipCode = args[0];
			}
			GetCityForecastByZIPResponse response = weatherClient.getCityByForecastByZip(zipCode);
			weatherClient.printResponse(response);
		};
	}

}

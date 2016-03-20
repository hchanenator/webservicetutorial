/**
 * Tutorial from https://spring.io/guides/gs/consuming-web-service/
 */
package org.springframework.gs_consuming_web_service;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import hello.wsdl.Forecast;
import hello.wsdl.ForecastReturn;
import hello.wsdl.GetCityForecastByZIP;
import hello.wsdl.GetCityForecastByZIPResponse;
import hello.wsdl.Temp;

/**
 * @author herb
 *
 */
public class WeatherClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(WeatherClient.class);

	public GetCityForecastByZIPResponse getCityByForecastByZip(String zipCode) {
		GetCityForecastByZIP request = new GetCityForecastByZIP();
		request.setZIP(zipCode);
		
		String s = "Requesting forecast for " + zipCode;

		log.info(s);
//		System.out.println(s);

		GetCityForecastByZIPResponse response = (GetCityForecastByZIPResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://wsf.cdyne.com/WeatherWS/Weather.asmx", request,
						new SoapActionCallback("http://ws.cdyne.com/WeatherWS/GetCityForecastByZIP"));

		return response;
	}

	public void printResponse(GetCityForecastByZIPResponse response) {
		ForecastReturn forecastReturn = response.getGetCityForecastByZIPResult();

		if (forecastReturn.isSuccess()) {
			log.info("Forecast for " + forecastReturn.getCity() + ", " + forecastReturn.getState());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			for (Forecast forecast : forecastReturn.getForecastResult().getForecast()) {
				Temp temperature = forecast.getTemperatures();
				
				String s = String.format("%s %s %s°-%s°",
						sdf.format(forecast.getDate().toGregorianCalendar().getTime()), forecast.getDesciption(),
						temperature.getMorningLow(), temperature.getDaytimeHigh());

				log.info(s);
				
				log.info("");
				
//				System.out.println(s);
			}
		} else {
			log.info("No forecast received");
			System.out.println("No forecast received");
		}
	}

}

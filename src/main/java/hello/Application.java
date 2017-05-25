package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;



@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	private static final String DEFAULT_SPRING_URL = "http://gturnquist-quoters.cfapps.io/api/random";
	private static final String BASE_URL = "https://2016.api.levelmoney.com/api/v2/core";
	private static final String GET_ALL_TRANSACTIONS_URL = "/get-all-transactions";

	// Get the necessary API credentials from the application.properties file next to the jar	
	@Value("${authString}")
	private String authString;
	
	
	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			
			Quote quote = restTemplate.getForObject(DEFAULT_SPRING_URL, Quote.class);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			
			HttpEntity<String> entity = new HttpEntity<String>(authString, headers);
			
			log.info("Entity to be sent to server: " + entity.toString());
			
			//String retJson = restTemplate.postForObject(BASE_URL + GET_ALL_TRANSACTIONS_URL, entity, String.class);
			GetAllTransactionsResponse resp = restTemplate.postForObject(BASE_URL + GET_ALL_TRANSACTIONS_URL, entity, GetAllTransactionsResponse.class);
			log.info("Error message for GetAllTransactions: " + resp.getError());
			calcMonthlyAndAvgExpense(resp.getTransactions());
			
			
			log.info(quote.toString());
			
		};
	}
	
	/**
	 * Calculates the monthly spend and income, as well as average, and prints it out
	 * @param transactions
	 */
	private void calcMonthlyAndAvgExpense(ArrayList<Transaction> transactions) {
		// Map to help us organize our data
		Map<String, ArrayList<Transaction>> sortedTransactions = new HashMap<String, ArrayList<Transaction>>();
		
		// Iterate through the list and separate out the transactions by month
		log.debug("Now iterating through received transactions");
		for (Transaction tx : transactions) {
			// Split out the date and keep what we want for given output format "yyyy-mm"
			String[] timeElements = tx.getTransactionTime().split("-");
			String yearMonthDate = timeElements[0] + "-" + timeElements[1];
			
			// If the map doesn't have this date combination, we need to initialize the ArrayList
			if (!sortedTransactions.containsKey(yearMonthDate)) {
				log.debug("New date combination found: " + yearMonthDate);
				ArrayList<Transaction> tempArrList = new ArrayList<Transaction>();
				sortedTransactions.put(yearMonthDate, tempArrList);			
			}
			
			// Add this transaction into the proper ArrayList within the Map
			log.debug("Now adding transaction into the ArrayList within the Map");
			sortedTransactions.get(yearMonthDate).add(tx);
		}
	}
}
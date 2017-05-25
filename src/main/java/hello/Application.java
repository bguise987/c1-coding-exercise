package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



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
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			
			HttpEntity<String> entity = new HttpEntity<String>(authString, headers);
			
			log.info("Entity to be sent to server: " + entity.toString());
			
			//String retJson = restTemplate.postForObject(BASE_URL + GET_ALL_TRANSACTIONS_URL, entity, String.class);
			GetAllTransactionsResponse resp = restTemplate.postForObject(BASE_URL + GET_ALL_TRANSACTIONS_URL, entity, GetAllTransactionsResponse.class);
			log.info("Error message for GetAllTransactions: " + resp.getError());
			
			// Pass all transactions into sorting method
			HashMap<String, ArrayList<Transaction>> sortedTx = sortTransactionsByMonth(resp.getTransactions());
			log.debug("Now sending data to be calculated and printed");
			calcAndPrintSpendIncome(sortedTx);
			
		};
	}
	
	/**
	 * Takes in an already assembled HashMap with keys being the months (in "yyyy-mm" format) and calculates the
	 * monthly spend and incomee, as well as average for the data set
	 * @param sortedTx
	 */
	private void calcAndPrintSpendIncome(HashMap<String, ArrayList<Transaction>> sortedTx) {
		String averageKey = "average";
		long totalSpent = 0;
		long totalIncome = 0;
		
		HashMap<String, MonthlyIncomeSpendOutput> spentIncomeRes = new HashMap<String, MonthlyIncomeSpendOutput>();
		
		Set<String> sortedTxKeys = sortedTx.keySet();
		
		// Iterate through all entries of our map (every month)
		for (String key : sortedTxKeys) {
			// Setup variables to track total spent and income for the month
			long spent = 0;
			long income = 0;
			// Extract the ArrayList and iterate through it for this month
			ArrayList<Transaction> txArr = sortedTx.get(key);
			for (Transaction tx : txArr) {
				long txAmount = tx.getAmount();
				if (txAmount < 0) {
					spent += (txAmount * -1);
				} else {
					income += txAmount;
				}
			}
			totalSpent += spent;
			totalIncome += income;
			MonthlyIncomeSpendOutput monthTotals = new MonthlyIncomeSpendOutput(spent, income);
			log.debug("Creating new output class for " + key);
			log.debug(key + " has spent and income vals, respectively: " + spent + ", " + income);
			spentIncomeRes.put(key, monthTotals);
		}
		
		// Calculate the averages
		long averageSpent = totalSpent / spentIncomeRes.size();
		long averageIncome = totalIncome / spentIncomeRes.size();
		MonthlyIncomeSpendOutput avgTotals = new MonthlyIncomeSpendOutput(averageSpent, averageIncome);
		log.debug("Creating new output class for " + averageKey);
		log.debug(averageKey + " has spent and income vals, respectively: " + averageSpent + ", " + averageIncome);
		spentIncomeRes.put(averageKey, avgTotals);
		
		// Convert our results to a JSON string for printing
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonInString = mapper.writeValueAsString(spentIncomeRes);
			// Print out our results!
			log.info("------------------------------Now printing results of calculations: ------------------------------");
			log.info(jsonInString);
			log.info("--------------------------------------------------------------------------------------------------");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Sorts the transactions into a Map of ArrayLists by month
	 * @param transactions
	 */
	private HashMap<String, ArrayList<Transaction>> sortTransactionsByMonth(ArrayList<Transaction> transactions) {
		// Map to help us organize our data
		HashMap<String, ArrayList<Transaction>> sortedTransactions = new HashMap<String, ArrayList<Transaction>>();
		
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
		return sortedTransactions;
	}
}
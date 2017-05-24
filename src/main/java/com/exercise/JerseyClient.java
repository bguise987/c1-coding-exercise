package com.exercise;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class JerseyClient {
	// This file should be created in src/main/resources/
	// prior to doing a build
	public static final String credentialsFilename = "apicredentials.properties";
	
	public static final String BASE_URL = "https://2016.api.levelmoney.com/api/v2/core";
	public static final String GET_ALL_TRANSACTIONS_ENDPOINT = "/get-all-transactions";
	
	private static String TOKEN = null;
	private static String API_TOKEN = null;
	
	Properties prop = new Properties();
	InputStream input = null;
	
	
	
	private Client client = null;
	private WebTarget target = null;
	
	public JerseyClient() {
		this.client = ClientBuilder.newClient();
		this.target = client.target(BASE_URL);
		
		// Grab the API credentials
		try {
			input = JerseyClient.class.getClassLoader().getResourceAsStream(credentialsFilename);
			if (input == null) {
				System.out.println("Sorry, can't find API credentials. Perhaps you forgot to include them before building?");
			}
			
			// Load in the properties from the file
			prop.load(input);
			
			this.TOKEN = prop.getProperty("token");
			this.API_TOKEN = prop.getProperty("apitoken");
			System.out.println("Using token: " + TOKEN);
			System.out.println("Using api-token: " + API_TOKEN + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Make call to the GetAllTransactions API endpoint
	 */
	public void getAllTransactions() {
		target = target.path(GET_ALL_TRANSACTIONS_ENDPOINT);
		
		Response response = target.request(MediaType.APPLICATION_JSON)
				.header("token", TOKEN).header("api-token", API_TOKEN).post(Entity.entity(null, MediaType.APPLICATION_JSON), Response.class);
		
		if (response.getStatus() == 200) {
			System.out.println("Successful API call!");
		} else {
			System.out.println("Something went wrong :(");
			System.out.println("Response code: " + response.getStatus());
			System.out.println("URL attempted: " + target.toString());
		}
	}
	
	
}

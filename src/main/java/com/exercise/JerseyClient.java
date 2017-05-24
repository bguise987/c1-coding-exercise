package com.exercise;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class JerseyClient {
	public static final String BASE_URL = "https://2016.api.levelmoney.com/api/v2/core/";
	public static final String GET_ALL_TRANSACTIONS_ENDPOINT = "get-all-transactions";
	
	private Client client = null;
	private WebTarget target = null;
	
	public JerseyClient() {
		this.client = ClientBuilder.newClient();
		this.target = client.target(BASE_URL);
	}
	
	
	
}

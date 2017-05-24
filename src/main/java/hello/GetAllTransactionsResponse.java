package hello;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAllTransactionsResponse {

	@JsonProperty("error")
	private String error;
	@JsonProperty("transactions")
	private ArrayList<Transaction> transactions;
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
}

package hello;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

	@JsonProperty("transaction-id")
	private String transactionId;
	@JsonProperty("account-id")
	private String accountId;
	@JsonProperty("raw-merchant")
	private String rawMerchant;
	@JsonProperty("merchant")
	private String merchant;
	@JsonProperty("is-pending")
	private boolean isPending;
	@JsonProperty("transaction-time")
	private String transactionTime;
	@JsonProperty("amount")
	private long amount;
	@JsonProperty("previous-transaction-id")
	private String previousTransactionId;
	@JsonProperty("categorization")
	private String categorization;
	@JsonProperty("clear-date")
	private long clearDate;
	
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getRawMerchant() {
		return rawMerchant;
	}
	public void setRawMerchant(String rawMerchant) {
		this.rawMerchant = rawMerchant;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public boolean isPending() {
		return isPending;
	}
	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getPreviousTransactionId() {
		return previousTransactionId;
	}
	public void setPreviousTransactionId(String previousTransactionId) {
		this.previousTransactionId = previousTransactionId;
	}
	public String getCategorization() {
		return categorization;
	}
	public void setCategorization(String categorization) {
		this.categorization = categorization;
	}
	public long getClearDate() {
		return clearDate;
	}
	public void setClearDate(long clearDate) {
		this.clearDate = clearDate;
	}
	
	
}

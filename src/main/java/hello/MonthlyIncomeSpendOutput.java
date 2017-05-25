package hello;

import java.text.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MonthlyIncomeSpendOutput {

	@JsonIgnore
	private long spentNum;
	@JsonIgnore
	private long incomeNum;
	
	@JsonProperty("spent")
	private String spent;
	
	@JsonProperty("income")
	private String income;
	
	public MonthlyIncomeSpendOutput(long spentNum, long incomeNum) {
		// These values are in centocents, e.g. 20,000 = 2 dollars
		this.spentNum = spentNum;
		this.incomeNum = incomeNum;
		
		double tempSpent = spentNum / 10000;
		double tempIncome = incomeNum / 10000;
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		this.spent = formatter.format(tempSpent);
		this.income = formatter.format(tempIncome);
	}

	public long getSpentNum() {
		return spentNum;
	}

	public void setSpentNum(long spentNum) {
		this.spentNum = spentNum;
	}

	public long getIncomeNum() {
		return incomeNum;
	}

	public void setIncomeNum(long incomeNum) {
		this.incomeNum = incomeNum;
	}

	public String getSpent() {
		return spent;
	}

	public String getIncome() {
		return income;
	}
	
	
}

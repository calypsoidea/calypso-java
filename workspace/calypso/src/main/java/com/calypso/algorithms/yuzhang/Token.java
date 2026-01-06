package com.calypso.algorithms.yuzhang;

import java.util.Objects;

public class Token  {
	
	private String tokenAddress;
	
	private String symbol;
	private int digits;
	
	public Token(String tokenAddress) {
		this.setTokenAddress(tokenAddress);
	}
		
	public String getTokenAddress() {
		return  tokenAddress;
	}

	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	} 
	
	 public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	@Override
	  public boolean equals(Object other) {
	    if (other instanceof Token) {
	      Token that = (Token) other;
	      return this.tokenAddress.equals(that.tokenAddress);
	    }
	    return false;
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(tokenAddress);
	  } 

	  @Override
	  public String toString() {
	    return "( " + symbol + "  " + tokenAddress + ")";
	  }
	
}

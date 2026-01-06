package com.calypso.algorithms.yuzhang;

import java.util.Objects;

import com.calypso.general.datastructures.Vertex;

public class Token_Yu_I  {
	
	private String tokenAddress;
	private String marketAddress;
	
	public Token_Yu_I(String tokenAddress, String marketAddress) {
		// TODO Auto-generated constructor stub
		this.setTokenAddress(tokenAddress);
		setMarketAddress(marketAddress);
	}
	
	public String getMarketAddress() {
		return this.marketAddress;
	}

	public void setMarketAddress(String ID) {
		this.marketAddress = ID;
	}
	
	public String getTokenAddress() {
		return this.tokenAddress;
	}

	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	}
	
	 @Override
	  public boolean equals(Object other) {
	    if (other instanceof Token_Yu_I) {
	      Token_Yu_I that = (Token_Yu_I) other;
	      return this.marketAddress.equals(that.marketAddress) && 
	    		 this.tokenAddress.equals(that.tokenAddress);
	    }
	    
	    return false;
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(tokenAddress, marketAddress);
	  } 

	  @Override
	  public String toString() {
	    return "( " + tokenAddress + ":" + marketAddress + " )";
	  }
	
}

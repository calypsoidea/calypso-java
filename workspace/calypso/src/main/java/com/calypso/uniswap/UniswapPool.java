package com.calypso.uniswap;

import java.math.BigInteger;
import java.util.Objects;

import com.calypso.algorithms.yuzhang.UniswapPricingFunctions;
import com.calypso.general.algorithms.MathFunctions;

public class UniswapPool {
	
	
	private String defiName;
	
	private String marketAddress;
	
	private String token0;
	private String token0name;
	
	private String balance0;
	
	private String token1;
	private String token1name;
	private String balance1;
	
	private double K = 0.0;
	private double sqtK = 0.0; 
	private double Tetaij = 0.0; 
	private double a = 0.0; // i
	private double b = 0.0; // j
	
	private double cosTetaij = 0.0;
	
	// in the future: factory address and name
	
	// TVL, per amount of coins and per USD, and per other currency
	
	// delta Y after deltaX, 
	
	// Price P1,2 from token 1, em relation to Token 2
	
	// feasibilty Prod, put per price or reserves?
	
	// as many hops as relayer profit increases ?
	
	// tx gas fees ?! +- 
	
	
	
	

	public UniswapPool() {
		// TODO Auto-generated constructor stub
	}
	
	public UniswapPool(String marketAddress, 
					   String tokeni, String balancei, 
					   String tokenj, String balancej) {
	
			this.setMarketAddress(marketAddress);
			this.setToken0(tokeni);
			this.setBalance0(balancei);
			
			this.setToken1(tokenj);
			this.setBalance1(balancej);
			
			this.K = this.calculateK();
			this.calculateHyperbolicParams();
	}
	
	

	public String getDefiName() {
		return defiName;
	}

	public void setDefiName(String defiName) {
		this.defiName = defiName;
	}

	public String getToken0name() {
		return token0name;
	}

	public void setToken0name(String token0name) {
		this.token0name = token0name;
	}

	public String getToken1name() {
		return token1name;
	}

	public void setToken1name(String token1name) {
		this.token1name = token1name;
	}

	public String getMarketAddress() {
		return marketAddress;
	}

	public void setMarketAddress(String marketAddress) {
		this.marketAddress = marketAddress;
	}

	public String getToken0() {
		return token0;
	}

	public void setToken0(String token0) {
		this.token0 = token0;
	}

	public String getBalance0() {
		return balance0;
	}

	public void setBalance0(String balance0) {
		this.balance0 = balance0;
	}

	public String getToken1() {
		return token1;
	}

	public void setToken1(String token1) {
		this.token1 = token1;
	}

	public String getBalance1() {
		return balance1;
	}

	public void setBalance1(String balance1) {
		this.balance1 = balance1;
	}
	
	public Double getK() {
		return new Double (this.K);
	}
	
	private double calculateK() {
	    return (new BigInteger(this.getBalance0()).doubleValue()) *
	    		(new BigInteger(this.getBalance1()).doubleValue());
	}
	
	private void calculateHyperbolicParams() {
	    sqtK = Math.sqrt(this.K); // Removed .doubleValue() since K is already double

	    // Tetaij = Math.acos((new BigInteger(this.getBalance1()).doubleValue() / sqtK) );

	    // cosTetaij = Math.cos(Tetaij);

	    // b = sqtK * cosTetaij;
	    // a = sqtK * (1 / cosTetaij);
	    
	    a = (new BigInteger(this.getBalance0()).doubleValue());
	    b = (new BigInteger(this.getBalance1()).doubleValue());
	}
	
	private double getSqtK() {
	    return Math.sqrt(this.K);
	}
	
	public double getAmountOut(double amountIn) throws BelowThresholdException {
		
		if (amountIn > this.a) {
	        throw new BelowThresholdException(
	            "Final amount " + amountIn + " is higher the reserves "
	        );
	    } 
		
	    // return this.K / ((this.sqtK * (1 / cosTetaij)) + amountIn);
		
		return this.K / (a + amountIn);
	}
		
	public Double getTVL() {
		return null;
	}
	
	public Double[] getMidPricing() {
		
		// get Pricing as specified by Yus, using Us constant
		
		// Double[0] Token0 -> Token1, TokenI -> TokenJ, Reserves0 / Reserves1
		// Double[1] Token1 -> Token0, TokenJ -> TokenI, Reserves1 / Reserves0
		
		// compasate the decimals?
		
		Double[] prices = new Double[2];
				
		prices[0] = UniswapPricingFunctions.priceByYus_Kadu(new BigInteger(this.getBalance0()), new BigInteger(this.getBalance1())); 
		//prices[1] = new Double(UniswapPricingFunctions.YUS_CONSTANT.doubleValue() - prices[0].byteValue());
		prices[1] = UniswapPricingFunctions.priceByYus_Kadu(new BigInteger(this.getBalance1()), new BigInteger(this.getBalance0()));
		
		
		return prices;
	}
	
	public Double[] getExecutionPricing() {
		
		// Double[0] Token0 -> Token1, TokenI -> TokenJ, Reserves0 / Reserves1
		// Double[1] Token1 -> Token0, TokenJ -> TokenI, Reserves1 / Reserves0
			
		Double[] prices = new Double[2];
		
		return prices;
	}
		
	@Override
	  public boolean equals(Object other) {
	    if (other instanceof UniswapPool) {
	      UniswapPool that = (UniswapPool) other;
	      return this.marketAddress.equals(that.marketAddress) && 
	    		  (
	    			(this.token0.equals(that.token0) && 
	    			 this.token1.equals(that.token1)) ||
	    			(this.token0.equals(that.token1) && 
	    	    	 this.token1.equals(that.token0))
	    		   );
	    }
	    return false;
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(this.getMarketAddress(), token0.hashCode(), token1.hashCode());
	  }


	@Override
	public String toString() {

		return 	"Market Address: " + this.getMarketAddress() + "\n" +
				"Token0: " + this.getToken0() + "\n" +
				"Balance0: " + this.getBalance0() + "\n" +
				"Token1: " + this.getToken1() + "\n" +
				"Balance1: " + this.getBalance1() + "\n";


	}
	
	public static void main(String[] args) throws Exception { 
		 
		/*
		 * {  "marketAddress": "0x3Aa370AacF4CB08C7E1E7AA8E8FF9418D73C7e0F" , 
		 * "token0": "0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48" ,  // weth
		 * "balance0": "351725331521" , 
		 * "token1": "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2" , // USDC
		 * "balance1": "131967018517940707307" }  , 
		 * 
		 * UniswapPool(String marketAddress, 
					   String tokeni, String balancei, 
					   String tokenj, String balancej) 
		 * 
		 */
		
		/*UniswapPool pool = new UniswapPool("0x3Aa370AacF4CB08C7E1E7AA8E8FF9418D73C7e0F",
				"0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48",
				"351725331521",
				"0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2",
				"131967018517940707307"); */
		
		
		/* Double[] prices = pool.getMidPricing();
		
		System.out.println("{Price01: " + prices[0] + " Prices10: " + prices[1]); */
		
		UniswapPool pool1 = new UniswapPool ("LOLLA0", "WETH", "1", "USDT", "2");
		// UniswapPool pool2 = new UniswapPool ("LOLLA1", "WETH", "3", "USDT", "4");
		UniswapPool pool2 = new UniswapPool ("LOLLA1", "USDT", "4", "WETH", "3");
		
		Double K1 = pool1.getK();
		Double K2 = pool2.getK();
		
		Double tokenOut = pool1.getAmountOut(1.0);
		//Double tokenOutDoubleHop = pool1.getAmoutOutDoubleHop(1.0, pool2);
		//Double[] investment = pool1.findMininumInvestmentForADelta(1.0, pool2);
		 
		System.out.println("Token Out - Pool1: " + tokenOut);
		//System.out.println("Token Out - Double Pool1: " + tokenOutDoubleHop);
		//System.out.println("Token Out - Investment Pool1: " + investment);
		
	}
	
}

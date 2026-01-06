package com.calypso.algorithms.yuzhang;

import java.math.*;

public class UniswapPricingFunctions {
	
	public static double uniswapFee = 0.03;
	public static double complementUniswapFee = 1 - uniswapFee;
	//public static Double YUS_CONSTANT = new Double(-2 * (Math.log(1 - uniswapFee)));
	
	// 0.06091841496941715
	public static Double YUS_CONSTANT = new Double(0.06091841496941715);
	public static Double HALF_YUS_CONSTANT = new Double(YUS_CONSTANT.doubleValue() / 2);
	
	public UniswapPricingFunctions() {
		
	}
	
	public static Double priceByYus_Kadu(BigInteger reservesTokeni, BigInteger reservesTokenj) {
		
		Double priceij = null;
		
		// double reservesRatio = (reservesTokenj.doubleValue() / reservesTokeni.doubleValue()) ;
		// Mid Price Uniswap Pij = Rj/Ri, normalizing the prices...
		// not taking consideration of factor K, not following its rule.
		// Pij Yus_kadu = Ri/Rj = 1/Price Uniswap normal.
		// Im not considering the decimals, this may lead to an error. must check.
		// in such situation must be considered that:
		// ETH GWEI
		// USDT micro-micro-micro-cents
		
		
		// https://ethereum.stackexchange.com/questions/155869/getting-uniswap-v2-latest-price-and-interpreting-values-using-python
		// we may adjust the edges by the entry... 
		
		double reservesRatio = (reservesTokeni.doubleValue() / reservesTokenj.doubleValue()) ;
		
		priceij = - Math.log(complementUniswapFee * reservesRatio);
		
		return new Double(priceij);
		
	}
	
}

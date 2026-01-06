package com.calypso.bots;

import java.time.LocalTime;
import java.util.HashMap;

import com.calypso.algorithms.yuzhang.YuZhangMethod;
import com.calypso.uniswap.UniswapMarketsDeparser;
import com.calypso.uniswap.UniswapPool;

public class Calypso_Yus_test {
	/*
	 * 
	 * 0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48 -> 0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48
	 * 
	 * 0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2
	 */
	
	public Calypso_Yus_test() {
		
	}
	
	public static void main(String[] args) throws Exception {
		
		// must check liquitdity, so we dont pick zero stuff...
		
		YuZhangMethod searcherYuzHan = new YuZhangMethod();
		
		LocalTime currentTime = LocalTime.now();
        System.out.println("Starting Analysis at: " + currentTime); 
		
		HashMap<String, UniswapPool> poolList = UniswapMarketsDeparser.initFromBuffer();
		
		UniswapPool[] pools = poolList.values().toArray(new UniswapPool[0]);

		searcherYuzHan.findOpportunities(pools, "0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48", "0x3Aa370AacF4CB08C7E1E7AA8E8FF9418D73C7e0F"); 
		
		currentTime = LocalTime.now();
        System.out.println("Finishing Analysis at: " + currentTime); 

	}
	
}

package com.calypso.bots;

import java.time.LocalTime;
import java.util.HashMap;

import com.calypso.algorithms.yuzhang.YuZhangMethod;
import com.calypso.uniswap.UniswapMarketsDeparser;
import com.calypso.uniswap.UniswapPool;

public class Calypso_test {

	// The first one
	
	// pick data using node and javascript
	// save new tokens, log pools reserves, in timeframe basis (block or whatever)
	// separate those we want to proceed
	
	/* filter ->
	 
	 * cycling arbs
	 * all -
	 * 
	 
	 */
	
	/*
	 * 
	 * 0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48 -> 0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48
	 * 
	 * 0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2
	 * 
	 * 
	 */
	
	
	private static YuZhangMethod searcherYuzHan = new YuZhangMethod();
	
	public Calypso_test() {
		;
	}
	
	public static void main(String[] args) throws Exception {
		
		// must check liquitdity, so we dont pick zero stuff...
		
		
		LocalTime currentTime = LocalTime.now();
        System.out.println("Starting Analysis at: " + currentTime); 
		
		// String BLOCK = "21078798";
        
        //String BLOCK = "21073510";
		
		//String BLOCK = "21073674";
		
        String BLOCK = "21078701";
        
        String ROOT = "C:\\Users\\Administrator\\Downloads\\ProjectsCalypsoI\\probing-bots-uniswap\\probing-bots-uniswap\\Data\\Parsed_Data_Uniswap_10_30_2024\\";
		//String ROOT = "C:\\Users\\Administrator\\Downloads\\Projects\\probing-bots-uniswap\\Data\\Parsed_Data_Uniswap_10_30_2024\\";
		String FILE = "parsedMarkets";
		String EXTENSION = ".mkts";

		String POOLS_LIST_FILE = ROOT + FILE + BLOCK + EXTENSION;
		
		
		HashMap<String, UniswapPool> poolList = UniswapMarketsDeparser.initFromBuffer();
		
		UniswapPool[] pools = poolList.values().toArray(new UniswapPool[0]);

		searcherYuzHan.findOpportunities(pools, "0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48", "0x3Aa370AacF4CB08C7E1E7AA8E8FF9418D73C7e0F"); 
		
		//UniswapMarketsDeparser.printPoolList(poolList);
		
		// filter arbs -> get cycling and no pooling yet
		
		// put gas into account

		System.out.println("Number of Pools: " + poolList.size());

		currentTime = LocalTime.now();
        System.out.println("Finishing Analysis at: " + currentTime); 

	}
	
}

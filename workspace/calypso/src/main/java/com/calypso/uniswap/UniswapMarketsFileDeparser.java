package com.calypso.uniswap;

import java.io.FileReader;
import java.util.*;
import java.time.LocalTime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UniswapMarketsFileDeparser {
	
	private static JSONParser parser = new JSONParser();

	public UniswapMarketsFileDeparser() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {

		LocalTime currentTime = LocalTime.now();
        System.out.println("Starting Analysis at: " + currentTime); 
        
        //String BLOCK = "21073510";
		
		//String BLOCK = "21078798";
		
		//String BLOCK = "21073674";
        
        String BLOCK = "21373932";
		
		//String ROOT = "C:\\Users\\Administrator\\Downloads\\Projects\\probing-bots-uniswap\\Data\\Parsed_Data_Uniswap_10_30_2024\\";
		
        //String ROOT = "C:\\Users\\Administrator\\Downloads\\ProjectsCalypsoI\\probing-bots-uniswap\\probing-bots-uniswap\\Data\\Parsed_Data_Uniswap_10_30_2024\\";
		
        String ROOT = "C:\\Users\\Administrator\\Downloads\\ProjectsCalypsoI\\probing-bots-uniswap\\probing-bots-uniswap\\Data\\Parsed_Data_Uniswap_12_10_2024\\";
        
		String FILE = "parsedMarkets";
		String EXTENSION = ".mkts";

		String POOLS_LIST_FILE = ROOT + FILE + BLOCK + EXTENSION;

		HashMap<String, UniswapPool> poolList = UniswapMarketsFileDeparser.initFromFile(POOLS_LIST_FILE);
	
		System.out.println("Number of Pools: " + poolList.size());
		System.out.println("");
		
		UniswapMarketsFileDeparser.printPoolList(poolList);

		currentTime = LocalTime.now();
        System.out.println("Finishing Analysis at: " + currentTime); 

	}
	
	public static HashMap<String, UniswapPool> initFromFile(String filename) throws Exception {
		
		 HashMap<String, UniswapPool> poolList = new HashMap<String, UniswapPool>();
		
		JSONArray a = (JSONArray) parser.parse(new FileReader(filename));

		  for (Object o : a)
		  {
		    JSONObject market = (JSONObject) o;

		    UniswapPool pool = new UniswapPool();
		    
			String marketAddress = (String )market.get("marketAddress");

		    pool.setMarketAddress(marketAddress);
			pool.setToken0((String) market.get("token0"));
			pool.setBalance0((String) market.get("balance0"));
			pool.setToken1((String) market.get("token1"));
			pool.setBalance1((String) market.get("balance1"));
			
			poolList.put(marketAddress, pool);
		    	    
		  }
		
		  if (poolList.isEmpty()) throw new Exception();

		  return poolList;
	}

	public static void printPoolList(HashMap<String, UniswapPool> poolList) throws Exception {

		if (poolList.isEmpty()) throw new Exception();

		System.out.println();
		System.out.println("Number of Pools : " + poolList.size());

		for (Map.Entry<String, UniswapPool> entry : poolList.entrySet() ) {

			String key = entry.getKey();
			UniswapPool pool = (UniswapPool) entry.getValue();

			System.out.println("");
			//System.out.println("Key : " + key );
			System.out.println("Pool :");
			System.out.println(pool.toString());

		}
	}
	
}

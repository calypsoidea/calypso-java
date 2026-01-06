package com.calypso.uniswap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.time.LocalTime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UniswapMarketsDeparser {
	
	
	public UniswapMarketsDeparser() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		
		LocalTime currentTime = LocalTime.now();
        System.out.println("Starting Analysis at: " + currentTime); 
        
		HashMap<String, UniswapPool> poolList = UniswapMarketsDeparser.initFromBuffer();
	
		UniswapMarketsDeparser.printPoolList(poolList);

		currentTime = LocalTime.now();
        System.out.println("Finishing Analysis at: " + currentTime); 

	}
	
	// refactoring... three different functions doing the same stuff
		
	public static HashMap<String, UniswapPool> init(String jsonPools) throws Exception {
		
		JSONParser parser = new JSONParser();
		
		HashMap<String, UniswapPool> poolList = new HashMap<String, UniswapPool>();
		
		JSONArray a = (JSONArray) parser.parse(jsonPools);


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
	
	public static HashMap<String, UniswapPool> initFromBuffer() throws Exception {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    
		JSONParser parser = new JSONParser();
		
		HashMap<String, UniswapPool> poolList = new HashMap<String, UniswapPool>();
		
		String input = null;
		
		 while ((input = reader.readLine()) != null) {
			 
			  JSONArray jsonArray = (JSONArray) parser.parse(input);
	          
	          for (Object o : jsonArray)
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

		 } 
		  if (poolList.isEmpty()) throw new Exception();

		  return poolList;
	}


	public static void printPoolList(HashMap<String, UniswapPool> poolList) throws Exception {

		if (poolList.isEmpty()) throw new Exception();

		System.out.println("Number of Pools : " + poolList.size());
		System.out.println("");
		
		for (Map.Entry<String, UniswapPool> entry : poolList.entrySet() ) {

			UniswapPool pool = (UniswapPool) entry.getValue();
			System.out.println(pool.toString());

		}
	}
	
	// jsonfy the poollist
	
}

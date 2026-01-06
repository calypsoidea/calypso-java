package com.calypso.tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.calypso.uniswap.UniswapPool;

public class YourJavaApp {
	
	//private static JSONParser parser = new JSONParser();
	
    public static void main(String[] args) throws Exception {
    	
    	JSONParser parser = new JSONParser();
    	
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        //JSONArray a = (JSONArray) parser.parse(reader);
        
        System.out.println("In the java app");
        
        /*String input = reader.readLine();

        // Check if the input is not null
        if (input != null) {
          System.out.println("Input received from TypeScript: " + input);
          //System.out.println(a.toJSONString());
        }*/
        
        /*
        String input = null;
        JSONParser jsonParser = new JSONParser();
        while ((input = reader.readLine()) != null) {
          JSONObject jsonObject = (JSONObject) jsonParser.parse(input);
          //System.out.println("JSON Value data received from TypeScript: " + jsonObject.toJSONString());
          
          System.out.println("JSON Value data received from TypeScript: " + jsonObject.get("key"));
        } */
        
        
        String input = null;
        JSONParser jsonParser = new JSONParser();
        int cont = 0;
        while ((input = reader.readLine()) != null) {
          JSONArray jsonArray = (JSONArray) jsonParser.parse(input);
          System.out.println("Cont = " + (++cont));
          
          for (Object Obj: jsonArray) {
        	  
        	  JSONObject market = (JSONObject) Obj;
        	  
        	  System.out.println(" [" + market.get("id") + "]: " + market.get("name")); 
        	  
          }
          
          System.out.println("JSON array received from TypeScript: " + jsonArray.toJSONString());
        }
        
        //.out.println(a.toJSONString());	
        
        /*for (Object o : a)
		  {
		    JSONObject market = (JSONObject) o;			
			System.out.println(market.toJSONString());	    
		  }*/
    }
}

/*
 * public class YourJavaApp {

  public static void main(String[] args) throws IOException, ParseException {
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    String input = null;
    JSONParser jsonParser = new JSONParser();
    while ((input = reader.readLine()) != null) {
      JSONArray jsonArray = (JSONArray) jsonParser.parse(input);
      System.out.println("JSON array received from TypeScript: " + jsonArray.toJSONString());
    }
    
    // ... Rest of your code
  }
}
 * 
 * 
 */

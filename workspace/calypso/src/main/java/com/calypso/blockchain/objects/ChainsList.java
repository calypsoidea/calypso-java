package com.calypso.blockchain.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum ChainsList {
	
	LOCAL("", "Local"),
	GOERLI("5", "Goerli"),
	SEPOLIA("11155111", "Sepolia"),
	ETH_MAINNET("1", "ETH/Mainnet");
	
	public final String ID;
	public final String name;
	
	 private static final Map<String, ChainsList> BY_ID = new HashMap<>();
	 private static final Map<String, ChainsList> BY_NAME = new HashMap<>();
	  
	 
	 static {
	    for (ChainsList e : values()) {
	        BY_ID.put(e.ID, e);
	        BY_NAME.put(e.name, e);
	    }
	}
	
	private ChainsList(String _ID, String _name) {
		this.ID = _ID;
		this.name = _name;
	}
	
	public static ChainsList valueOfID(String _ID) {
        return BY_ID.get(_ID);
    }
	
	public static ChainsList valueOfName(String _name) {
        return BY_NAME.get(_name);
    }
	
	@Override
	public String toString() {
		return this.name;
	}
}

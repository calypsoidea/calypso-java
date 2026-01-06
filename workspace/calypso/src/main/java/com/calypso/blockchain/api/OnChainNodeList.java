package com.calypso.blockchain.api;

import java.util.HashMap;
import java.util.Map;

import com.calypso.blockchain.objects.ChainsList;

public enum OnChainNodeList {

	LOCAL_RPC(ChainsList.LOCAL,"http://127.0.0.1:8545"),
	
	ALCHEMY_SEPOLIA(ChainsList.SEPOLIA,"https://eth-sepolia.g.alchemy.com/v2/eJL1q2Fuwb94bHgnfERZxCNjP3AJKasH"),
	//ALCHEMY_MAINNET(ChainsList.ETH_MAINNET, "https://eth-mainnet.g.alchemy.com/v2/eJL1q2Fuwb94bHgnfERZxCNjP3AJKasH");
	ALCHEMY_MAINNET_KATE(ChainsList.ETH_MAINNET, "https://eth-mainnet.g.alchemy.com/v2/OkuSBG15eD8Di2XmZy-ZbRePgqlup3z2");
	
	// Infura mainnet https://mainnet.infura.io/v3/c4e6cba8a8f848d7ba612428940e3450
	// c4e6cba8a8f848d7ba612428940e3450
	
	public final ChainsList chain;
	public final String URL;
	
	private static final Map<ChainsList, OnChainNodeList> BY_CHAIN = new HashMap<>();
	private static final Map<String, OnChainNodeList> BY_URL= new HashMap<>();
	 
	 static {
	    for (OnChainNodeList e : values()) {
	        BY_CHAIN.put(e.chain, e);
	        BY_URL.put(e.URL, e);
	    }
	}
	
	private OnChainNodeList (ChainsList _chain, String _URL) {
		this.URL = _URL;
		this.chain = _chain;
	}
	
	public static OnChainNodeList valueOfChain(ChainsList _chain) {
        return BY_CHAIN.get(_chain);
    }
	
}
 
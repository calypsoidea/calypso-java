package com.calypso.blockchain.accounts;

import java.io.IOException;

import com.calypso.blockchain.api.OnChainNodeList;
import com.calypso.blockchain.api.web3j.Web3jConnect;
import com.calypso.blockchain.objects.Account;

public class MainNetCalypso extends Account {
	
	/*
	 * 
	 * Conta Operaciona ETh 
		Address: 0x13e3E28330da49EE81B95fA3a89Ca51687Fee279

	 * 
	 * 
	 */

	public MainNetCalypso() throws IOException {
		super(new Web3jConnect(OnChainNodeList.ALCHEMY_MAINNET_KATE), "0x13e3E28330da49EE81B95fA3a89Ca51687Fee279", "312c52933a7e3c8f86e32f1babe00f37b310cee42dbe02bca67ba158d74fbd1e");
		
	}
 
}
 
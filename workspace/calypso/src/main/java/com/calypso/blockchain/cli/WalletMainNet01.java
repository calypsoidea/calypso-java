package com.calypso.blockchain.cli;

import java.io.IOException;
import java.math.BigDecimal;

import com.calypso.blockchain.objects.ChainsList;
import com.calypso.blockchain.accounts.*;

public class WalletMainNet01 {
	
	// check balance
	// transfer

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		MainNet01 maninet01 = new MainNet01();
		
		String address = maninet01.getAddress();
		BigDecimal balance = maninet01.getBalance();
		ChainsList chain = maninet01.getChain(); 
		
		boolean sameChain = maninet01.onTheSameChain(new MainNet01());
		
		System.out.println("Address: " + address 
				+ "Balance:"  + balance 
				+ "Chain: " + chain 
				+ "SameChain?: 0-"+ sameChain);

	}

}

package com.calypso.tests;

import java.math.BigInteger;

import com.calypso.blockchain.api.OnChainNodeList;
import com.calypso.blockchain.api.web3j.Web3jConnect;
import com.calypso.blockchain.objects.Account;
import com.calypso.blockchain.accounts.*;

public class TestTxWalletsGetGasFee {

	public TestTxWalletsGetGasFee() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String args[]) throws Exception { 
		System.out.println("Connecting to Ethereum ...");
	    
	    Web3jConnect connection = new Web3jConnect(OnChainNodeList.ALCHEMY_SEPOLIA);
	    System.out.println("Successfuly connected to Ethereum");
	    
	    System.out.println("Chain: " + connection.getNetwork().name);
	    
	    BigInteger gasPrice = connection.getGasPriceWei();
	    System.out.println("Gas Price: " + connection.getGasPriceWei());
	    
	    Account acc01 = new Sepolia01();
	    Account acc02 = new Sepolia02();
	    
	    BigInteger balance01 = acc01.getBalanceWei(); // fix to big integer????
	    System.out.println("Balance 01: " + balance01);  
	    
	    BigInteger chainTransferCost = Account.GAS_LIMIT_TRANSFER_SEPOLIA;
	    
	    BigInteger transferGasCost01 = chainTransferCost.multiply(gasPrice);
	    BigInteger transferAmount = BigInteger.valueOf(1);
	    BigInteger transferCost01 = transferGasCost01.add(transferAmount);
	    
	    System.out.println("General Transfer Cost: " + transferCost01);
	    
	    if (balance01.compareTo(transferCost01) > 0) {
	    	System.out.println("There are funds on Sepolia 01 to transfer 1 wei");
	    	System.out.println("Balance Due: " + balance01.subtract(transferCost01).toString() );
	    } 
	    
	    
	}
}

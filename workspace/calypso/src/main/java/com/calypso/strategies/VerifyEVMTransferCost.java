package com.calypso.strategies;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.utils.Convert;
import com.calypso.blockchain.api.OnChainNodeList;
import com.calypso.blockchain.api.web3j.Web3jConnect;
import com.calypso.blockchain.objects.Account;
import com.calypso.blockchain.objects.ChainsList;
import com.calypso.blockchain.accounts.*;

public class VerifyEVMTransferCost extends Strategy {
	
	private Account wallet01;
	
	protected BigInteger blockNumber;
	protected ChainsList chain;
	protected  BigInteger gasPrice;
	protected  BigInteger balance;
	protected  BigInteger balanceDue;
	protected BigInteger totalTransferGasCost;
	protected BigInteger totalTransferCost;
	protected boolean haveFunds;

	public VerifyEVMTransferCost(Account _wallet01) throws IOException {
		this.wallet01 = _wallet01;
		this.chain = this.wallet01.getChain();
	}
	 
	public void execute(BigInteger transferAmountWei) throws IOException {
		 Web3jConnect connection = new Web3jConnect(OnChainNodeList.valueOfChain(wallet01.getChain()));
		 
		 gasPrice = connection.getGasPriceWei();
		 balance = wallet01.getBalanceWei();
		 blockNumber = connection.getBlockNumber();
		 
		 // fees on sepolia, make it auto
		 BigInteger chainTransferCost = Account.GAS_LIMIT_TRANSFER_SEPOLIA;
		   
		 this.totalTransferGasCost = chainTransferCost.multiply(gasPrice);
		 totalTransferCost = totalTransferGasCost.add(transferAmountWei);
	    
		 balanceDue = balance.subtract(totalTransferCost);
	    haveFunds = false;
	    
	    if (balance.compareTo(totalTransferCost) > 0) {
	    	haveFunds = true;
	    }		 
	}
	
	public static void main(String args[]) throws IOException {
		
		Account sepolia01 = new Sepolia01();
		
		VerifyEVMTransferCost strategy = new VerifyEVMTransferCost(sepolia01);
		
		strategy.execute(Convert.toWei(BigDecimal.valueOf(1), Convert.Unit.ETHER).toBigInteger());  
		
		System.out.println(strategy.chain);
		System.out.println(strategy.blockNumber);
		System.out.println(strategy.gasPrice);
		System.out.println(strategy.balance);;
		System.out.println(strategy.balanceDue);
		System.out.println(strategy.haveFunds);
	}
	
}
 
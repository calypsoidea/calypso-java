package com.calypso.smartcontracts.flashbotsmulticallFL;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import com.calypso.blockchain.accounts.MainNet01;
import com.calypso.blockchain.api.OnChainNodeList;
import com.calypso.blockchain.api.web3j.Web3jConnect;
import com.calypso.blockchain.objects.ChainsList;
import com.calypso.blockchain.objects.SmartContract;
import com.calypso.smartcontracts.FlashBotsUniswapQuery.FlashBotsUniswapQuery_Wrap;
import com.calypso.blockchain.objects.Account;

public class FlashBotsMultiCallFL extends SmartContract {
	
	static enum ContractAddress {

		LOCAL_RPC("127.0.0.1", ChainsList.LOCAL),
		/*SEPOLIA("", ChainsList.SEPOLIA),
		GOERLI("", ChainsList.GOERLI), */
		MAINNET("0xAaBcFE801e4C9086F3E72e4920EFb381965c854b", ChainsList.ETH_MAINNET);
		
		public final String address;
		public final ChainsList chain;
		
		 private static final Map<ChainsList, String> BY_CHAIN = new HashMap<>();
		 
		 static {
		    for (ContractAddress e : values()) {
		        BY_CHAIN.put(e.chain, e.address);
		       
		    }
		}
		
		private ContractAddress (String _address, ChainsList _chain) {
			this.address = _address;
			this.chain = _chain;
		}
		
		public static String addressOnChain(ChainsList chain) {
			
			return BY_CHAIN.get(chain);
			
		}
		 
	}
	
	public FlashBotsMultiCallFL(Web3jConnect connection, Account signerWallet) throws IOException {
		super(connection, ContractAddress.addressOnChain(connection.getNetwork()), new FlashBotsMultiCallFL_Wrap(ContractAddress.addressOnChain(connection.getNetwork()), connection.getWeb3j(), signerWallet.getCredentials(), null));
	}
	
	public RemoteFunctionCall<TransactionReceipt> flashloan(String token0, String token1,
	            BigInteger amountToBorrow, byte[] _params) {
		 return ((FlashBotsMultiCallFL_Wrap) contract).flashloan(token0, token1,
		             amountToBorrow,  _params);
	 } 
	
	public static void main(String[] args) throws Exception { 
		
		Web3jConnect connection = new Web3jConnect(OnChainNodeList.ALCHEMY_MAINNET_KATE);
		
		Account signerWallet = new MainNet01();
		
		//FlashBotsMultiCallFL multiCallSwap = new FlashBotsMultiCallFL(connection, signerWallet);
		
		FlashBotsUniswapQuery_Wrap multiCallSwap = new FlashBotsUniswapQuery_Wrap(
				"0xAaBcFE801e4C9086F3E72e4920EFb381965c854b",
				connection.getWeb3j(), 
				signerWallet.getCredentials(), 
				new DefaultGasProvider());
		
		if (multiCallSwap.isValid()) {
			System.out.println(" Valid ...");
		} else {
			System.out.println(" Not Valid ...");
		}
	}
	
}
	 
 
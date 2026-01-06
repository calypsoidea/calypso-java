package com.calypso.blockchain.objects;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SignatureException;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.Wallet;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import com.calypso.blockchain.api.web3j.Web3jConnect;
import com.calypso.blockchain.accounts.*;

public class Account extends OnChainObject {
	
	public static BigInteger GAS_LIMIT_TRANSFER_SEPOLIA = BigInteger.valueOf(6721975);
	
	// turn this into enum for each chai and tx type
	// create object to subscribe to chain info like gasprice..
	
	private Wallet derivedWallet = null; // implemente Wallet features later
	
	private String privateKey = null;
	private Credentials credentials;
	
	public Account(Web3jConnect connection, String address, String _privateKey) throws IOException {
		super(connection, address, OnChainObjectTypes.ACCOUNT);
		
		this.privateKey = _privateKey;
		credentials = Credentials.create(privateKey);
		
	}
	
	public Credentials getCredentials() {
		return this.credentials;
	}
	
	// functions wallet must perform? no credentials given.
	
	public BigInteger getNonce() throws Exception {
		// to test
		// is this here or above? Does Smart COntraxcts use it?
		// go inside web3j
		
        EthGetTransactionCount ethGetTransactionCount =
                (((Web3jConnect) chainRPcConnection).getWeb3j()).ethGetTransactionCount(this.getAddress(), DefaultBlockParameterName.LATEST)
                        .sendAsync()
                        .get();

        return ethGetTransactionCount.getTransactionCount();
    }
	
	public  TransactionReceipt transfer(String amount, OnChainObject toAddress) throws Exception {
		// to test
		
		// remember, everything here is being coded for ETH/EVM compatible
		
		// move part of this to Web3j, to make API compliant
		
		// go inside web3j
		
		// it blocks until tx is fullfilled....
		
		if (this.onTheSameChain(toAddress)) {
			
			
			BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();

			BigInteger nonce = this.getNonce();
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, chainRPcConnection.getGasPriceWei(), GAS_LIMIT_TRANSFER_SEPOLIA, toAddress.getAddress(), value);
			
			
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, this.credentials);
	        String hexValue = Numeric.toHexString(signedMessage);

	        EthSendTransaction ethSendTransaction =
	        		((( Web3jConnect) chainRPcConnection).getWeb3j()).ethSendRawTransaction(hexValue).sendAsync().get();
	        String transactionHash = ethSendTransaction.getTransactionHash();
	        
	       return (( Web3jConnect) chainRPcConnection).waitForTransactionReceipt(transactionHash);
	        
	    } else {
			new Exception();
			
			// insufficient funds
		}
		
		return null; // ????
		 
	}
	
	// todo, make trasnafer with bignumber and wei...
	
	public EthSendTransaction call(String data, BigInteger amountWei, SmartContract toAddress) throws Exception {
        
               // info about fees
		
		BigInteger gasLimit = null;
		BigInteger maxPriorityFeePerGas = null;
        BigInteger maxFeePerGas = null;
        
        // chainRPcConnection

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                Long.getLong(this.getChain().ID),
                this.getNonce(),
                gasLimit,
                toAddress.getAddress(),
                amountWei,
                data,
                maxPriorityFeePerGas,
                maxFeePerGas);
        
        byte[] signedMessage =
                TransactionEncoder.signMessage(rawTransaction, credentials);
        String signedHexMessage = Numeric.toHexString(signedMessage);
         
        return chainRPcConnection.ethSendRawTransaction(signedHexMessage);
        
        //System.out.println("Transaction hash: " + ethSendTransaction.getTransactionHash());
        //System.out.println("Tx Receipt = " + web3j.ethGetTransactionReceipt(ethSendTransaction.getTransactionHash()).send().getTransactionReceipt());

    }
	
	// just give tx without signing
    
    /*

private static RawTransaction createEip1559RawTransaction() {
        return RawTransaction.createEtherTransaction(
                3L,
                BigInteger.valueOf(0),
                BigInteger.valueOf(30000),
                "0x627306090abab3a6e1400e9345bc60c78a8bef57",
                BigInteger.valueOf(123),
                BigInteger.valueOf(5678),
                BigInteger.valueOf(1100000));
        }	 * 
	 */
	/////////////////
	
	public  OnChainObject generateOnChain() {
		return null;
	}
	
	public static void main(String args[]) throws Exception {
		
		Account mainnet0101 = new MainNet01();
		Account mainnet0102 = new MainNet02();
		
		System.out.println("Balance Mainnet 0101: " + mainnet0101.getBalance()); 
		System.out.println("Balance Mainnet 0102: " + mainnet0102.getBalance()); 
		
		Account sepolia01 = new Sepolia01();
		Account sepolia0102 = new Sepolia02();
		
		System.out.println("Balance Sepolia 01: " + sepolia01.getBalance()); 
		System.out.println("Balance Sepolia 0102: " + sepolia0102.getBalance()); 
		
		System.out.println("Nonce Sepolia01: " + sepolia01.getNonce());
		System.out.println("Nonce Sepolia0102: " + sepolia0102.getNonce());
		
		TransactionReceipt txReceipt = sepolia01.transfer("0.0001", sepolia0102);
		
		String txHash = txReceipt.getTransactionHash();
		BigInteger gasUsedWei = txReceipt.getGasUsed();
		
		System.out.println("Transfering the dough...");
		
		System.out.println("Balance Sepolia 01: " + sepolia01.getBalance()); 
		System.out.println("Balance Sepolia 0102: " + sepolia0102.getBalance()); 
		
		System.out.println("Tx Hash: " + txHash);
		System.out.println("Gas Used: " + gasUsedWei);
		
		System.out.println("Nonce Sepolia01: " + sepolia01.getNonce());
		
		
	}
	
}

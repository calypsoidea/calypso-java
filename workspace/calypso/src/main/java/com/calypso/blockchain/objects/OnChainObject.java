package com.calypso.blockchain.objects;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import com.calypso.blockchain.api.IOnChainObjectAPI;
import com.calypso.blockchain.api.web3j.Web3jConnect;

public abstract class OnChainObject {
	
	private String address;
	private ChainsList chain;
	private OnChainObjectTypes type;
	public IOnChainObjectAPI chainRPcConnection;
	
	//https://docs.web3j.io/4.8.7/transactions/wallet_files/
	// https://github.com/hyperledger-web3j/web3j/blob/main/integration-tests/src/test/java/org/web3j/protocol/scenarios/CreateRawTransactionIT.java
	// https://github.com/hyperledger-web3j/web3j/blob/main/integration-tests/src/test/java/org/web3j/protocol/scenarios/Scenario.java
	
	// gas used, tx hash, tx history, etc....
	
	public String description = "";
	
	public OnChainObject() {
		this.chainRPcConnection = null;
		this.address = "";
		this.type = OnChainObjectTypes.ADDRESS;
		this.chain = null;
	}
	
	public OnChainObject(IOnChainObjectAPI _onChainRPC, String address) throws IOException {
		this.chainRPcConnection = _onChainRPC;
		this.address = address;
		this.type = OnChainObjectTypes.ADDRESS; // wrong, set object type
		this.chain = this.chainRPcConnection.getNetwork();
	}
	
	public OnChainObject(IOnChainObjectAPI _onChainRPC, String address, OnChainObjectTypes _type) throws IOException {
		this.chainRPcConnection = _onChainRPC;
		this.address = address;
		this.type = _type; 
		this.chain = this.chainRPcConnection.getNetwork();
	}
	
	public ChainsList getChain() throws IOException {  
		return chainRPcConnection.getNetwork();
	}

	public void setChain(Web3jConnect chain) {
		this.chainRPcConnection = chain;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public boolean onTheSameChain(Object _object) {
		
		if (this.chain.equals(((OnChainObject)_object).chain)) {
			return true;
		}
		
		return false;
		
	}
	
	public BigInteger getBalanceWei() throws IOException {
		
		if (chainRPcConnection != null)
			return chainRPcConnection.getBalanceWei(address);
		else 
			throw new IOException();
	}
	
	public BigDecimal getBalance() throws IOException {
		
		if (chainRPcConnection != null) 
			return chainRPcConnection.getBalance(address);
		else 
			throw new IOException();

	} 
	
	protected String getTransactionHash(TransactionReceipt receipt) throws IOException {
		
		if (chainRPcConnection != null) 
			return chainRPcConnection.getTransactionHash(receipt);
		else 
			throw new IOException();
		
	}
	
	public String getTransactionReceipt(String transactionHash) throws IOException {
		if (chainRPcConnection != null) 
			return chainRPcConnection.getTransactionReceipt(transactionHash);
		else 
			throw new IOException();
	}
	
	public abstract OnChainObject generateOnChain() throws Exception;
	// it is chain dependent, that means each contract, etc, must know ehre to pick the code in the chain.
	// pattern for that.... dont know
	
	
	/*
	 * EthBlock.Block block = web3j.ethGetBlockByNumber(
    DefaultBlockParameter.valueOf(“<block number>”),
    true  //returnFullTransactionObjects
    ).send().get().getBlock();

 block.getBaseFeePerGas();
	 * 
	 * 
	 * 
	 * EthBlock.Block block = web3j.ethGetBlockByHash(String blockHash, boolean returnFullTransactionObjects);

EthBlock.Block block = web3j.ethGetUncleByBlockHashAndIndex(String blockHash, BigInteger transactionIndex);

EthBlock.Block block = web3j.ethGetUncleByBlockNumberAndIndex(DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);
Also access Transactions new field through Web3j

EthTransaction ethTransaction = web3j.ethGetTransactionByHash(String transactionHash).send();

EthTransaction ethTransaction = web3j.ethGetTransactionByBlockHashAndIndex
      (String blockHash, BigInteger transactionIndex).send();
EthTransaction ethTransaction = web3j.ethGetTransactionByBlockNumberAndIndex(
      DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex).se
	 * 
	 */
	
	@Override
	public boolean equals(Object _object) {
		// same chain, same address
		
		if (this.getClass().equals(_object.getClass())) {
			if (this.chain.equals(((OnChainObject)_object).chain)) {
				if (address.equals(((OnChainObject)_object).address)) {
					return true;
				}
			}
		}
		 
		return false;
	}
	
	@Override
	  public int hashCode() {
	    return Objects.hash(chain + address);
	  } 

	  @Override
	  public String toString() {
	    return " " + chain + ": " + address + " ";
	  }
	
}
	

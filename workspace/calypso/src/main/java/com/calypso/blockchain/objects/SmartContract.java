package com.calypso.blockchain.objects;

import java.io.IOException;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import com.calypso.blockchain.api.IOnChainObjectAPI;
import com.calypso.blockchain.api.ISmartContractAPI;
import com.calypso.blockchain.api.web3j.Web3jConnect;

public class SmartContract extends OnChainObject {
	
	// implement FlashBot Query... cehck...
	
	protected Contract contract;
	
	private ContractGasProvider contractGasProvider;
	protected ISmartContractAPI chainRPC;
	
	public SmartContract(Web3jConnect chain, String address, 
			Contract _contract) throws IOException {
		super(chain, address, OnChainObjectTypes.CONTRACT);
		
		if (_contract.isValid()) {
			this.contract = _contract;
		} else {
			 throw new IOException();
		} 
	}
	
	public boolean isValid() throws IOException {
		return this.contract.isValid();
	}
	
	public SmartContract(Web3jConnect chain, String address, 
			Contract _contract, ContractGasProvider contractGasProvider) throws IOException {
		super(chain, address, OnChainObjectTypes.CONTRACT);
		
		this.contract = _contract;
		
	}
	
	/*
	 * https://docs.web3j.io/4.8.7/transactions/wallet_files/
	 * 
	 * YourSmartContract contract = YourSmartContract.load(
                contractAddress,
                web3j,
                credentials,
                new DefaultGasProvider()
        );
	 * 
	 * 
	 */
	
	/*
	 *  private static RawTransaction createSmartContractTransaction(BigInteger nonce)
            throws Exception {
        return RawTransaction.createContractTransaction(
                nonce, GAS_PRICE, GAS_LIMIT, BigInteger.ZERO, Fibonacci.BINARY);
    }
	 * 
	 */
	
	public  OnChainObject generateOnChain() {
		return null;
	}

}

package com.calypso.smartcontracts.erc.token;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

public class WETH extends TokenErc20 {

	public WETH(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
		super(contractAddress, web3j, credentials, contractGasProvider);
		// TODO Auto-generated constructor stub
	}

	public WETH(String contractAddress, Web3j web3j, TransactionManager transactionManager,
			ContractGasProvider contractGasProvider) {
		super(contractAddress, web3j, transactionManager, contractGasProvider);
		// TODO Auto-generated constructor stub
	}

}

package com.calypso.smartcontracts.ethereum;

import java.io.IOException;

import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;

import com.calypso.blockchain.api.ISmartContractAPI;
import com.calypso.blockchain.api.web3j.Web3jConnect;
import com.calypso.blockchain.objects.SmartContract;

public class BundleExecutor extends SmartContract implements ISmartContractAPI {

	public BundleExecutor(Web3jConnect chain, String address, Contract _contract) throws IOException {
		super(chain, address, _contract);
		// TODO Auto-generated constructor stub
	}

	public BundleExecutor(Web3jConnect chain, String address, Contract _contract,
			ContractGasProvider contractGasProvider) throws IOException {
		super(chain, address, _contract, contractGasProvider);
		// TODO Auto-generated constructor stub
	}
	
	

}

package com.calypso.blockchain.api.web3j;

import java.math.BigInteger;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.calypso.blockchain.api.IAccountAPI;
import com.calypso.blockchain.objects.OnChainObject;

public class Web3jWalletAPI implements IAccountAPI {

	@Override
	public BigInteger getNonce(String address) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionReceipt transfer(String amount, OnChainObject toAddress) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

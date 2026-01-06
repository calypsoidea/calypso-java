package com.calypso.blockchain.api;

import java.math.BigInteger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import com.calypso.blockchain.objects.OnChainObject;

public interface IAccountAPI {
	
	public BigInteger getNonce(String address) throws Exception; //?
	public TransactionReceipt transfer(String amount, OnChainObject toAddress) throws Exception;

}

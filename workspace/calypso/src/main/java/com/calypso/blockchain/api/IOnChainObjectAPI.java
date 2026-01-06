package com.calypso.blockchain.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;

import com.calypso.blockchain.objects.ChainsList;

public interface IOnChainObjectAPI extends IApi {
	
	public String getClientVersion() throws IOException;
	
	public ChainsList getNetwork() throws IOException;
	
	public String getNetworkToPlainString() throws IOException;
	
	public BigInteger getBlockNumber() throws IOException;
	
	public BigInteger getGasPriceWei() throws IOException;
	
	public BigDecimal getGasPrice() throws IOException;
	
	public String getGasPriceToPlainString() throws IOException;
	
	public BigInteger getBalanceWei(String _address) throws IOException;

	public BigDecimal getBalance(String _address) throws IOException;
	
	public RawTransaction createTransaction(
            long chainId,
            BigInteger nonce,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            String data,
            BigInteger maxPriorityFeePerGas,
            BigInteger maxFeePerGas);
	
	public RawTransaction createEtherTransaction(
            long chainId,
            BigInteger nonce,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            BigInteger maxPriorityFeePerGas,
            BigInteger maxFeePerGas); 
	
	public EthSendTransaction ethSendRawTransaction(String signedHexMessage) throws IOException;
	// hash and receipt
	
	public String getTransactionHash(TransactionReceipt receipt) throws IOException;
	
	public String getTransactionReceipt(String transactionHash) throws IOException;
	
	public String getBalanceToPlainString(String _address) throws IOException;
}

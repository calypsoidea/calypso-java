package com.calypso.blockchain.api.web3j;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;

import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.utils.Convert;

import com.calypso.blockchain.api.OnChainNodeList;
import com.calypso.blockchain.api.IOnChainObjectAPI;
import com.calypso.blockchain.objects.ChainsList;

public class Web3jConnect implements IOnChainObjectAPI, 
									 IWeb3jFriendly {
	
	private OnChainNodeList node = null;	// pick URL
	private Web3j web3;
	
	public final int SLEEP_DURATION = 15000;
	public final int ATTEMPTS = 40;
	
	public Web3jConnect() {
		
		this.node = OnChainNodeList.LOCAL_RPC;
		
		web3 = Web3j.build(new HttpService(node.URL));
		
	}
	
	public Web3jConnect(OnChainNodeList _node) {
		
		this.node = _node;
		
		web3 = Web3j.build(new HttpService(node.URL));
		
	}
	
	public Web3j getWeb3j() {
		// get out, dont use it!!! Refactor the whole class
		return this.web3;
	}
	
	public boolean isConnectec() {
		if (this.web3 == null) {
			return true;
		} 
		
		return false;
	}
	
	public String getClientVersion() throws IOException {
		
		web3 = Web3j.build(new HttpService("https://eth-sepolia.g.alchemy.com/v2/eJL1q2Fuwb94bHgnfERZxCNjP3AJKasH"));
		
		
		Web3ClientVersion clientVersion = web3.web3ClientVersion().send();
		
		return clientVersion.getWeb3ClientVersion();
	}
	
	public BigInteger getBlockNumber() throws IOException {
		EthBlockNumber blockNumber = web3.ethBlockNumber().send();
		
		return blockNumber.getBlockNumber();
	}
	
	public BigInteger getGasPriceWei() throws IOException {
	  EthGasPrice gasPrice =  web3.ethGasPrice().send();
	  
	  return gasPrice.getGasPrice(); 
	}
	
	public BigDecimal getGasPrice() throws IOException {
		  BigInteger gasPrice = this.getGasPriceWei();
		  
		  return Convert.fromWei(gasPrice.toString(), Convert.Unit.ETHER); 
		}
	
	public String getGasPriceToPlainString() throws IOException {
		  return this.getGasPrice().toPlainString(); 
		}
	
	public BigInteger getBalanceWei(String _address) throws IOException {
		
		EthGetBalance balanceResponse = web3.ethGetBalance(_address, DefaultBlockParameterName.LATEST).send();
		
		return balanceResponse.getBalance();
	}
	 
	public BigDecimal getBalance(String _address) throws IOException {
		
		return Convert.fromWei(getBalanceWei(_address).toString(), Convert.Unit.ETHER);
		
	} 
	
	public String getBalanceToPlainString(String _address) throws IOException {
		
		return getBalance(_address).toPlainString();
		
	}
	
	public ChainsList getNetwork() throws IOException {
		return node.chain;
	}
	
	public String getNetworkToPlainString() throws IOException {
		return this.getNetwork().name;
	}
	
	@Override
	public RawTransaction createTransaction(long chainId, BigInteger nonce, BigInteger gasLimit, String to,
			BigInteger value, String data, BigInteger maxPriorityFeePerGas, BigInteger maxFeePerGas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RawTransaction createEtherTransaction(long chainId, BigInteger nonce, BigInteger gasLimit, String to,
			BigInteger value, BigInteger maxPriorityFeePerGas, BigInteger maxFeePerGas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EthSendTransaction ethSendRawTransaction(String signedHexMessage) throws IOException {
		
		EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(signedHexMessage).send();
		
		return ethSendTransaction;
	} 

	@Override
	public boolean equals(Object _object) {
		
		if (web3.equals(((Web3jConnect) _object).web3)) {
			return true;
		} 
		
		return false;
	}
	
	@Override
	  public int hashCode() {
	    return Objects.hash(web3);
	  } 

	  @Override
	  public String toString() {
	    return "( " + web3  + " )";
	  }
	
	
	// get transaction details 
	
	/*
	 * 
	 * 
	 * const { ethers } = require("ethers");

// Connect to Alchemy
const provider = new ethers.providers.JsonRpcProvider("https://eth-mainnet.alchemyapi.io/v2/YOUR_ALCHEMY_KEY");

// Transaction hashes (from the bundle)
const txHashes = ["0xabc...", "0xdef..."]; // Replace with your transaction hashes

// Query all transactions
async function getTransactionDetails(txHashes) {
  for (const txHash of txHashes) {
    const tx = await provider.getTransaction(txHash);
    console.log("Transaction details for", txHash, ":", tx);

    if (tx.blockNumber) {
      console.log("Transaction included in block:", tx.blockNumber);
    } else {
      console.log("Transaction is pending or failed.");
    }
  }
}

getTransactionDetails(txHashes);
	 * 
	 * 
	 */
	
	
	  private Optional<TransactionReceipt> getTransactionReceipt(
	            String transactionHash, int sleepDuration, int attempts) throws Exception {

	        Optional<TransactionReceipt> receiptOptional =
	                sendTransactionReceiptRequest(transactionHash);
	        for (int i = 0; i < attempts; i++) {
	            if (!receiptOptional.isPresent()) {
	                Thread.sleep(sleepDuration);
	                receiptOptional = sendTransactionReceiptRequest(transactionHash);
	            } else {
	                break;
	            }
	        }

	        return receiptOptional;
	    }
	
	public TransactionReceipt waitForTransactionReceipt(String transactionHash) throws Exception {

        // to debug
		Optional<TransactionReceipt> transactionReceiptOptional =
                getTransactionReceipt(transactionHash, SLEEP_DURATION, ATTEMPTS);

        if (!transactionReceiptOptional.isPresent()) {
           throw new Exception();
        }

        return transactionReceiptOptional.get();
    }
	
	 private Optional<TransactionReceipt> sendTransactionReceiptRequest(String transactionHash)
	            throws Exception {
		 
		 // to test
	        EthGetTransactionReceipt transactionReceipt =
	                web3.ethGetTransactionReceipt(transactionHash).sendAsync().get();

	        return transactionReceipt.getTransactionReceipt();
	    }
	 
		public String getTransactionHash(TransactionReceipt receipt) throws IOException {
			return receipt.getTransactionHash();
		}
		
		@Override
		public String getTransactionReceipt(String transactionHash) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	
	public static void main(String[] args) throws IOException {
		
		System.out.println(OnChainNodeList.ALCHEMY_MAINNET_KATE.URL);
		System.out.println(OnChainNodeList.ALCHEMY_MAINNET_KATE.chain);
		
		   // get chain
	    // get client version
	    // blockcnumber
	    // gasprice wei
	    // gas price
	    // equals ??
	    // to string
		
	    System.out.println("Connecting to Ethereum ...");
	    // Web3jConnect connection = new Web3jConnect(OnChainAPINodeList.ALCHEMY_MAINNET);
	    
	    Web3jConnect connection = new Web3jConnect(OnChainNodeList.ALCHEMY_MAINNET_KATE);
	    System.out.println("Successfuly connected to Ethereum");
	    
	    try {
	      
	    	String networkName = connection.getNetworkToPlainString();
	    	
	    	// web3_clientVersion returns the current client version.
	      String clientVersion = connection.getClientVersion();

	      // eth_blockNumber returns the number of most recent block.
	      BigInteger blockNumber = connection.getBlockNumber();

	      // eth_gasPrice, returns the current price per gas in wei.
	      BigInteger gasPriceWei = connection.getGasPriceWei();
	      BigDecimal gasPrice = connection.getGasPrice();
	      
	      ChainsList chainID = connection.getNetwork();

	      // Print result
	   //   System.out.println("Client version: " + clientVersion);
	      System.out.println("Block number: " + blockNumber);
	      System.out.println("Gas price in Wei: " + gasPriceWei);
	      System.out.println("Gas price : " + gasPrice.toPlainString());

	      System.out.println("ChainId : " + chainID 
	    		  + "Network name: " + networkName
	    		  + "Client Version: " + clientVersion);

	    } catch (IOException ex) {
	      throw new RuntimeException("Error whilst sending json-rpc requests", ex);
	    } 
	    
	       
	  }

	
	
	/*
	 * 
	 * #BUNDLE_EXECUTOR_ADDRESS using Dumb contract
BUNDLE_EXECUTOR_ADDRESS="0xAaBcFE801e4C9086F3E72e4920EFb381965c854b"
	 * 
	 */

}

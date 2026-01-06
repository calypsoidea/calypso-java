package com.calypso.blockchain.api.web3j;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import com.calypso.blockchain.api.ISmartContractAPI;

public interface IWeb3jSmartContractWrapper extends ISmartContractAPI {
	
    public Contract load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider);
        

    public Contract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider);
    
}

package com.calypso.blockchain.accounts;

import java.io.IOException;

import com.calypso.blockchain.api.OnChainNodeList;
import com.calypso.blockchain.api.web3j.Web3jConnect;
import com.calypso.blockchain.objects.Account;

public class Sepolia02 extends Account {

	public Sepolia02() throws IOException {
		super(new Web3jConnect(OnChainNodeList.ALCHEMY_SEPOLIA), "0xc2479652659900f6d1d0d55632c92b3bfe1cfb20", "0x73da9e815d7ea74cbb6857bca5b6705d7df02b882009a4d442610db6c96e413a");
		
	}
}
 
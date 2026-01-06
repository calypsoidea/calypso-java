package com.calypso.blockchain.accounts;

import java.io.IOException;

import com.calypso.blockchain.api.OnChainNodeList;
import com.calypso.blockchain.api.web3j.IWeb3jFriendly;
import com.calypso.blockchain.api.web3j.Web3jConnect;
import com.calypso.blockchain.objects.Account;

public class Sepolia01 extends Account {

	public Sepolia01() throws IOException {
		super(new Web3jConnect(OnChainNodeList.ALCHEMY_SEPOLIA), "0x954447cd671c6181282f12352e1c838944554df8", "0x13e1bad48b5002f2644d05037300fa22065cc8914698ba801c6dcbe948c66dbc");
		
	}
}
 
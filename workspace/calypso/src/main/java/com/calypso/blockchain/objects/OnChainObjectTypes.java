package com.calypso.blockchain.objects;

public enum OnChainObjectTypes {
	
	ADDRESS("Address"),
	CONTRACT("Contract"),
	WALLET("Wallet"),
	ACCOUNT("Account");
	 
	public String label;
	
	private OnChainObjectTypes(String _label) {
		this.label = _label;
	}

}

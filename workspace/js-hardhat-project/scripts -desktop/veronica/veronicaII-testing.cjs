const hre = require("hardhat");
const { ethers } = hre;

const addresses = require("../utils/addresses.cjs");
const abis = require("../utils/abis.cjs");
//const {getToken} = require("../utils/dexUtils.cjs");

const USDC = addresses.TOKENS.USDC;
const DAI = addresses.TOKENS.DAI;
const ERC20_ABI = abis.ERC20;

//const whaleUSDC = addresses.WHALES.USDC
//const whaleDAI = addresses.WHALES.DAI

const abbot = addresses.HARDHAT_ACCOUNTS.Abbot.address


// helper function so you can query any ERC20 balance (e.g. whale, your signer)

// app - Chain

async function init() {

    console.log("\n🐋 Starting Yus with Moby Dick ...");

    const provider = ethers.provider;
    const network = await provider.getNetwork();
    console.log("Connected to network:", network.name, "Chain ID:", network.chainId);

    const block = await provider.getBlock("latest");
    const feeData = await provider.getFeeData();

    console.log("📦 Latest Block:", block.number);
    console.log("⛽ Gas Price:", feeData.gasPrice)
    console.log("⛽ maxFeePerGas:", feeData.maxFeePerGas)
    console.log("⛽ maxPriotityFee:", feeData.maxPriorityFeePerGas)

    return provider

}

class Chain {

    constructor(ethers) {
        this.ethers = ethers
        this.provider = ethers.provider
    }

    async init() {

    }

    async getGasInfo() {

    }

    static async create(ethers)  {

        const chain = new Chain(ethers)
        await chain.init()

        return chain

    }
}

class HardhatChain extends Chain{

    constructor(ethers) {
        super(ethers)
    }

    async init()  {

        super.init();

        console.log("\n🐋 Starting with Moby Dick ...");

        this.network = await this.provider.getNetwork();
        this.block = await this.provider.getBlock("latest");
        this.blockNumber = this.block.number;

        const feeData = await this.provider.getFeeData();

        this.gasPrice = feeData.gasPrice
        this.maxFeePerGas = feeData.maxFeePerGas
        this.maxPriorityFeePerGas = feeData.maxPriorityFeePerGas

        console.log("Connected to network:", this.network.name, "Chain ID:", this.network.chainId);

        console.log("📦 Latest Block:", this.blockNumber);
        console.log("⛽ Gas Price:", this.gasPrice)
        console.log("⛽ maxFeePerGas:", this.maxFeePerGas )
        console.log("⛽ maxPriotityFee:", this.maxPriorityFeePerGas)

    }

    async getGasInfo() {

        super.getGasInfo();

        const gasPrice = this.gasPrice
        const maxFeePerGas = this.maxFeePerGas
        const maxPriority = this.maxPriorityFeePerGas

        return { gasPrice, maxFeePerGas, maxPriority} 
    }

}

class Account {
    constructor(address, chain) {
        this.address = address
        this.ethers = chain.ethers
        this.provider = this.ethers.provider
        this.signer = null
        // must have private key for production
    }

    async init() {

        this.signer = await this.ethers.getSigner(this.address);

    }

    async getBalance() {
        // ETH, fuel

        const rawBalance = await this.provider.getBalance(this.address);
        const balance = ethers.formatEther(rawBalance);
       
        return balance;
    }

    static async create(address, chain) {

        const account = new Account(address, chain)
        await account.init()

        return account

    }
    
}

class AccountAtHardhat extends Account {

    constructor(accountAddress, chain) {
        super(accountAddress, chain)
        
    }

    async init() {
        await super.init();

        await provider.send(
            "hardhat_impersonateAccount",
            [super.address]
        );

        super.signer = await this.ethers.getSigner(super.address);
    }

    async setBalance (amount) {
        await provider.send("hardhat_setBalance", [
                this.address,
                ethers.toBeHex(ethers.parseEther(amount)), // 10 ETH
        ]);
    }

    static async create(address, chain) {

        const account = new  AccountAtHardhat(address, chain)
        await account.init()

        return account

    }
}

/**
 * TokenERC20 utility class
 */
class TokenERC20 {
    constructor(tokenAddress, chain) {
        
        /** @type {ethers.providers.Provider} */
        this.provider = chain.provider;

        /** @type {ethers.Contract} */
        this.contract = new ethers.Contract(tokenAddress, ERC20_ABI, chain.provider);

        /** @type {number|null} */
        this.decimals = 0;

        /** @type {string|null} */
        this.symbol = "";

        /** @type {string|null} */
        this.name = "";
    }

    async init() {
        this.decimals = await this.contract.decimals();
        this.symbol = await this.contract.symbol();
        this.name = await this.contract.name();
        
        console.log("\nToken Address:", await this.contract.getAddress());
        console.log("Token Name:", this.name);
        console.log("Token Symbol:", this.symbol);
        console.log("Token Decimals:", this.decimals);
        
        return this;
    }

    async getTotalSupply() {

        const totalSupply = await this.contract.totalSupply();
        console.log("Token Total Supply:", ethers.formatUnits(totalSupply, this.decimals));
        return totalSupply;

    }

    // ERC20 balance
    async getTokenBalance(account) {

        //const decimals = await this.contract.decimals();
        const rawBalance = await this.contract.balanceOf(account.address);
        const balance = ethers.formatUnits(rawBalance, this.decimals);

        console.log(`💰 Balance of ${await this.contract.symbol()} for ${account.address}:`, balance);
        return balance;
    }

    

   // ERC20 transfer with pre-checks
    async transfer(fromAddress, toAddress, amount) {

        // Do you also want me to add a check before transfer (verify fromAddress has enough balance & ETH for gas) so it fails gracefully instead of reverting on-chain?
        // estimate gas

        const fromSigner = await ethers.getSigner(fromAddress)
        const tokenHolder = await this.contract.connect(fromSigner)
        const tokenHolderBalance = await tokenHolder.balanceOf(fromAddress);

        // digits... fix
        const transferAmount = ethers.parseUnits(amount, this.decimals); // 500 USDC
        
        console.log(`\n💸 Transferring ${ethers.formatUnits(transferAmount, 18)} DAI from whale to Abbot...`);
        
        const transferTx = await tokenHolder.transfer(toAddress, transferAmount);
        await transferTx.wait();
    }

    // approve(spender, amount)
    // allowance(owner, spender)

    // whale impersonation snippet

    // error messages return structured JSON objects (with fields like reason, needed, have) instead of plain text logs, so you can consume them in a frontend/GUI later?

}




//

async function main() {

    // const provider = await init();

    const chain = await Chain.create(ethers)
    
    const abbot = new Account(addresses.WALLETS.MAINNET01.address, chain) 
    await abbot.init();

    console.log('Address: ', await abbot.address)
    console.log('Balance Fuel:', await abbot.getBalance())

    console.log('Signer: ', abbot.signer)
    
    
    const usdc = new TokenERC20(USDC, chain);
    await usdc.init();

    //console.log(usdc.decimals)
    await usdc.getTokenBalance(abbot);
/*
    await usdc.getTotalSupply()

    // await usdc.transfer(whaleUSDC, abbot, "10.5");

    // Check balances after
    console.log("\n=== AFTER TRANSFER ===");
//    console.log("Sender ETH:", await usdc.getEthBalance(whaleUSDC));
  //  console.log("Sender USDC:", await usdc.getTokenBalance(whaleUSDC));
    console.log("Recipient USDC:", await usdc.getTokenBalance(abbot));
    */
}

// utils para ver preco de gasolina, etc que estao nos chats

main().catch(console.error);

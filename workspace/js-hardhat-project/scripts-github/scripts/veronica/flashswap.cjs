const { ethers, assert } = require("ethers");
const addresses = require("../utils/addresses.cjs");
const abis = require("../utils/abis.cjs");
const { ERC20Token } = require("./ERC20Token.cjs");
const contracts = require("../utils/contracts.cjs");
const { Pool } = require("./Pool.cjs"); // Import the Pool class
const { Chain } = require("./Chain.cjs");
const { Contract } = require("./Contract.cjs");


class FlashSwap extends Contract {
    // make it singleton 
    constructor(chain) {    
        if (FlashSwap.instance) {
            return FlashSwap.instance;
        }

        this.id = 'FlashSwap';
        this.address = contracts.FlashBotsMultiCallFL.address;
        this.chain = chain; 
        this.provider = chain.provider;
        this.contract = new ethers.Contract(this.address, contracts.FlashBotsMultiCallFL.ABI, this.provider);
        
        FlashSwap.instance = this;
    }

    static async getInstance(_ethers) {
        if (!FlashSwap.instance) {
            const chain = await Chain.create(_ethers);            
            FlashSwap.instance = new FlashSwap(chain);
        }
        return FlashSwap.instance;
    }

    static async getInstanceAtHardhat() {
        const hre = require("hardhat");
        const { ethers } = hre;
        return await FlashSwap.getInstance(ethers);
    }

    static async getInstanceAtMainNet() {

        // still need to update
        return null;
    }

    async getActualBlock() {
        try {
            return await this.chain.getLatestBlock().number;
        } catch (error) {
            console.error("Error fetching actual block:", error);
            throw error;
        }
    }

      /**
     * Signed call 
     * 
     * @param {string} token0 - initial token
     * @param {string} token1 - final   token
     * @param {string} loanAmount - Uniswap factory address (optional)
     * @param {string} bribe
     * @param {memory} params - DEX type from Pool.DEX_TYPES
     * @returns {Promise<Array>} Array of Pool objects
     * 
     *      address token0, address token1, uint256 amountToBorrow, bytes memory _params

            token0 = "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2"; // WETH
            token1 = "0x6B175474E89094C44Da98b954EedeAC495271d0F"; // DAI
            amountToBorrow = ethers.utils.parseEther("10.0"); // Borrow 10 ETH worth
        
            ethAmountToCoinbase = ethers.utils.parseEther("0.01"); // 0.01 ETH to coinbase
 
            _targets = [
                "0x7a250d5630B4cF539739dF2C5dAcb4c659F2488D", // Uniswap Router
                // Add your target contracts here
            ];
            
            `payloads = [
                "0x", // encoded function call for first target
                // Add your encoded function calls here
            ];
     */
    async flashloan(token0, token1, loanAmount, bribe, _targets, _payloads) {

        /*
            NOT TESTED
        */

            amountToBorrow = ethers.utils.parseEther(loanAmount); // Borrow 10 ETH worth
            ethAmountToCoinbase = ethers.utils.parseEther(bribe); // 0.01 ETH to coinbase
 
            const _params = ethers.utils.defaultAbiCoder.encode(
                                ["uint256", "address[]", "bytes[]"],
                                [_ethAmountToCoinbase, _targets, _payloads]
                            );

            try {
                // Call the flashloan function
                const tx = await flashLoanContract.flashloan(
                    token0,        // token0 address
                    token1,        // token1 address  
                    amountToBorrow, // amount to borrow
                    _params        // encoded parameters
                );

                console.log("Flashloan transaction hash:", tx.hash);
                
                // Wait for confirmation
                const receipt = await tx.wait();
                console.log("Flashloan confirmed in block:", receipt.blockNumber);

                return receipt
                
        } catch (error) {
            onsole.error("Error calling flashloan:", error);
        }
    }

    // same paramenters as above, but unsigned. This is goof to be used in MVs
    //  @returns unsignedTx
    async unsignedFlashloan(token0, token1, loanAmount, bribe, _targets, _payloads) {

        amountToBorrow = ethers.utils.parseEther(loanAmount); // Borrow 10 ETH worth
            ethAmountToCoinbase = ethers.utils.parseEther(bribe); // 0.01 ETH to coinbase
 
        const _params = ethers.utils.defaultAbiCoder.encode(
                            ["uint256", "address[]", "bytes[]"],
                            [_ethAmountToCoinbase, _targets, _payloads]
                        );

        const unsignedTx = await flashLoanContract.populateTransaction.flashloan(
            token0, 
            token1, 
            amountToBorrow, 
            _params
        );

        return unsignedTx

    }

}

    


module.exports = { ContractUniswapQuery: FlashSwap };

// Enhanced example usage
async function main() {
   
   
   /* const hre = require("hardhat");
    const { ethers } = hre;
    
    // Get singleton instance
    const uniswapQuery = await ContractUniswapQuery.getInstance(ethers);
    */

    const flashswap = await FlashSwap.getInstanceAtHardhat()
    

}

if (require.main === module) {
    main().catch(console.error);
}
const { Account }    = require("./Account.cjs");
const { ContractUniswapQuery } = require("./ContractUniswapQuery.cjs");
const { YusWrapper } = require("./YusWrapper.cjs")

class Veronica {

    constructor () { 

      this.sponsor = null;
      this.receiver = null;
      this.ContractUniswapQuery = null;
      this.yus = null;

    }

    static async getInstanceHardhat () {

      if (!Veronica._instance ) {
        Veronica._instance = new Veronica() // to run on hardhat
        await Veronica._instance.initHardhat()
      }

      return Veronica._instance
    }

    // later we can create a generic get instaca that read from Chain
    // like Account,etc... 

    static async getInstanceMainnet () {

      if (!Veronica._instance ) {
        Veronica._instance = new Veronica() // to run on hardhat
        await Veronica._instance.initMainnet()
      }

      return Veronica._instance
    }

    async initHardhat() {
        this.sponsor = await Account.createWhaleUSDC();
        this.receiver = await Account.createAbbot();
        
        this.uniswapQuery = await ContractUniswapQuery.getInstanceAtHardhat();
        this.yus = new YusWrapper()
        return this; // Return instance for chaining
    }

    async initMainnet() {
      
      // to create and test
    }

    async execute(jsonArgs) { 
      
        //  how can I optmize memory to send the Jsons...
        // pick Pools turn them into jsons...
        // change the Pool object, reserves to balance... it is wrong

      const rawPoolList = await this.uniswapQuery.getPoolsByIndexRangeSmart(jsonArgs.initialPool, jsonArgs.finalPool, { strategy: 'auto' });

      const poolArray = typeof rawPoolList === 'string' 
        ? JSON.parse(rawPoolList) 
        : rawPoolList;

        /*const jsonPools =  poolArray.map(pool => ({
            marketAddress: pool.poolAddress,
            token0: pool.token0.address,
            token1: pool.token1.address,
            balance0: pool.reserve0.toString(), // Convert BigInt to string
            balance1: pool.reserve1.toString()
        }));*/

        const jsonPools = [ 
      
        {
          "marketAddress": "UNI1",
          "token0": "WETH",
          "balance0": "4",
          "token1":"0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2",
          "balance1": "2"
        }, 

        {
          "marketAddress": "UNI2",
          "token0": "KaduCoin",
          "balance0": "1",
          "token1":"0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2",
          "balance1": "3"
        }, 

        {
          "marketAddress": "UNI3",
          "token0": "KaduCoin",
          "balance0": "2",
          "token1":"WETH",
          "balance1": "1"
        }
      
      ]

        const routes = await this.yus.execute(jsonArgs.amount, 
                                            jsonPools, 
                                            jsonArgs.currency, 
                                            jsonArgs.minProfit) 

      console.log(`The answer for routes is: ${routes}`)
    }
}

// Wrap execution in an async function
async function main() {

    const jsonArgs = {
      initialPool: 0,
      finalPool: 3,
      amount: 1,
      currency: "ETH",
      minProfit: 1
    }

    const veronica = await Veronica.getInstanceHardhat();
    await veronica.execute(jsonArgs);
}

// Execute and handle errors
main().catch((error) => {
    console.error(error);
    process.exit(1);
});



const { JavaWrapper } = require('./JavaWrapper.cjs')

class CalypsoWrapper extends JavaWrapper {

    // change this class name

    constructor () {
        super("java_proxies", "CalypsoProxy")
        // this.functionName = "Yus"
        this.functionName = "HelloWorld"
    }

    async execute( amount, 
                  rawPoolList,
                  currencySymbol,
                  minimumAmountExpected) {

        // check on Claude how can I optmize memory to send the Jsons...
        // pick Pools turn them into jsons...
        // change the Pool object, reserves to balance... it is wrong

        const jsonPools = JSON.stringify(
        rawPoolList.map(pool => ({
            marketAddress: pool.poolAddress,
            token0: pool.token0.address,
            token1: pool.token1.address,
            balance0: pool.reserve0.toString(), // Convert BigInt to string
            balance1: pool.reserve1.toString()
        })));

        const args = {

            initialAmount: amount.toString(),
            poolList: jsonPools,
            currency: currencySymbol,
            minimumAmountExpected: minimumAmountExpected.toString()

        }

        const poolList = await super.execute(this.functionName, 
                                             /* JSON.stringify(args) */
                                            args.poolList)

        return poolList // return routes... ready to be processed

    }

}

module.exports = { CalypsoWrapper }

async function main() {

    const yus = new CalypsoWrapper()
    
    /*const poolList = await yus.execute()

    console.log("The Pool List is: ")
    console.log(poolList)*/

    yus.execute()
    .then( poolList => {
            console.log("The Pool List is: ")
            console.log(poolList)
        }
     )

} 

if (require.main === module) {
    main().catch(console.error);
}

/*

      pools[0] = new UniswapPool("UNI", "WETH", "4", "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2", "2");
		  pools[1] = new UniswapPool("UNI", "WETH", "1", "KaduCoin", "2");
		  pools[2] = new UniswapPool("UNI", "KaduCoin", "1", "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2", "3");

      const jsonPools = `[ 
      
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
      
      ]`
      */

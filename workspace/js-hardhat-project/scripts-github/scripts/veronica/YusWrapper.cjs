
const { JavaWrapper } = require('./JavaWrapper.cjs')

class YusWrapper extends JavaWrapper {

    // change this class name

    constructor () {
        super("java_proxies", "CalypsoProxy")
        this.functionName = "Yus"
    }

    async execute(amount, 
                  jsonPools,
                  currencySymbol,
                  minimumAmountExpected) {

                    
      const args = {

            initialAmount: amount.toString(),
            poolList: jsonPools,
            currency: currencySymbol,
            minimumAmountExpected: minimumAmountExpected.toString()

        } 

        const routes = await super.execute(this.functionName, 
                                           JSON.stringify(args.poolList))

        return routes 

    }

}

module.exports = { YusWrapper }

async function main() {


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

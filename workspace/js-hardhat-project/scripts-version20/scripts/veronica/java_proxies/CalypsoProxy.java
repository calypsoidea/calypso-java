
import java.util.HashMap;

import com.calypso.general.HelloWorld;
import com.calypso.uniswap.UniswapMarketsDeparser;
import com.calypso.uniswap.UniswapPool;
import com.calypso.algorithms.yuzhang.*;

public class CalypsoProxy {

    /*
    
    javac -classpath calypso.jar CalypsoProxy.java
    
    create MANIFEST.MF
        Main-Class: CalypsoProxy
        Class-Path: calypso.jar

    jar -cvfm CalypsoProxy.jar MANIFEST.MF CalypsoProxy.class
    
    java -jar CalypsoProxy.jar <arg0> <arg1>
    
    */
   
    // note, it is important, to add a blank line at the end of the Manigest file
    // I think I will create a factory class in Calypso to call properly the functions

    static String solve(String jsonArg) {
        return "Asset Suggested: " + jsonArg;
    }

    public static void main(String[] args) throws Exception {

        String methodName = args[0];
        String jsonArg = args[1]; // change this to array...

        switch (methodName) {
            case "HelloWorld":

                HelloWorld helloCarlos = new HelloWorld(HelloWorld.CARLOS);
                System.out.println(helloCarlos.sayHello());

                HelloWorld helloKadu = new HelloWorld(HelloWorld.Kadu);
                System.out.println(helloKadu.sayHello());
                
                System.out.println(solve(jsonArg));
                
                break;
        
            case "Yus" :
                
             
                HashMap<String, UniswapPool> poolList = UniswapMarketsDeparser.init(jsonArg);
		       
                UniswapPool[] poolsArray = poolList.values().toArray(UniswapPool[]::new);
                
                YuZhangMethod yus = new YuZhangMethod();
                yus.findOpportunities( poolsArray , "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2", "UNI");
                
            break;

            default:
                System.out.println("Unknown Function");
            break;
        }

       
    }
    
}

/*

const jsonStr = [ 
      
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

*/

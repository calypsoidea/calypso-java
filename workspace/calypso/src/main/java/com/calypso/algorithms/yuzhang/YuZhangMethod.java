package com.calypso.algorithms.yuzhang;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.calypso.general.datastructures.williamfisset.BellmanFordAdjacencyMatrix;
import com.calypso.uniswap.UniswapPool;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class YuZhangMethod {
	
	// create constantY Yus 
	
	// initial scenarios 0.00001, 0.0001, 0.001, 0.01, 0.1, 1, 10, 100, 1000, 10000
	
	// BTC, ETH, USDT, BRL?
	
	
	// between 4- 8 cyclesz
	
	private MutableValueGraph<Token, Double> G; 
	private MutableValueGraph<EndpointPair<Token>, Double> L;
	
	private BellmanFordAdjacencyMatrix BFM;
	
	private Token  ZERO_TOKEN = new Token("Zero_TOKEN");  // go to constant file
	private EndpointPair<Token> ZERO_POOL = null;
	
	private ArrayList<EndpointPair<Token>> pools = null;
	
	public YuZhangMethod() {
		
		G = ValueGraphBuilder
				.directed()
				.allowsSelfLoops(true)
				.build();
		
		L = ValueGraphBuilder
				.directed()
				.allowsSelfLoops(true)
				.build();
		
	}
	
	private void buildG(UniswapPool[] pools) {
		int poolsLenght = pools.length;
		
		for(int contPool = 0; contPool < poolsLenght ; contPool++) {
			
			UniswapPool market = pools[contPool];
			
			String tokeni = market.getToken0();
			
			String tokenj = market.getToken1();
			
			Double[] prices = market.getMidPricing(); // 0 -> Pij, 1 -> Pji
			
			Token  i = new Token (tokeni);
			Token  j = new Token (tokenj);
			
			G.putEdgeValue(i, j, prices[0].doubleValue());
			G.putEdgeValue(j, i, prices[1].doubleValue());
		}
		
	}
	
	private void buildL() {
		
		Set<EndpointPair<Token >> pools =  G.edges();
		
		// check for one degree vertices and take them out.
				// I want 2 predecessors and 1 sucessor, 
				// as one successor is one predecessor
		
		pools.forEach( (pair) -> {
			
			Token  tokeni = pair.nodeU();
			Token  tokenj = pair.nodeV();
			
			addEdgeToL(tokeni, tokenj);
			
		}); // for each pair
		
	}
	
	private void addEdgeToL(Token  tokeni, Token  tokenj) {
		
		Set<Token> successors = G.successors(tokenj);
		
		successors.forEach((tokenl) -> {
			
			if (!(tokenl.equals(tokeni))) {
				
				EndpointPair<Token> ViVj =  EndpointPair.ordered( tokeni, tokenj);
				EndpointPair<Token> VjVl =  EndpointPair.ordered( tokenj, tokenl);
				
				double Wijl = G.edgeValue(tokenj, tokenl).get();
				
				L.putEdgeValue(ViVj, VjVl, Wijl); 
				
			}
		}); // for each token
	}
	
	private void putZeroEdge(Token  token0) {
		
		addEdgeToL(ZERO_TOKEN, token0);
		
	}
	
	private void removeZeroEdge(Token  token0) {
		// remove ZEROToken?
	}
	
	private void MBF_Phase_I(Token  token0) { 
		
		pools = new ArrayList<EndpointPair<Token>>(L.nodes());
		
		int n = pools.size();
		
		ZERO_POOL = EndpointPair.ordered(this.ZERO_TOKEN, token0);
		
		int start = pools.indexOf(ZERO_POOL);;
		  
		// check for Zero Edge
		
		Set<EndpointPair<EndpointPair<Token>>> poolOfPools = L.edges();
		
		double[][] graph = new double[n][n]; // change to sparse later
	    
	 // Setup completely disconnected graph with the distance
	    // from a node to itself to be zero.
	    for (int i = 0; i < n; i++) {
	      java.util.Arrays.fill(graph[i], Double.POSITIVE_INFINITY);
	    }
	    
		poolOfPools.forEach( (pool) -> {
			
			EndpointPair<Token> ViVj = pool.nodeU();
			EndpointPair<Token> VjVl = pool.nodeV();
			
			double Wijl = L.edgeValue(pool).get(); // ha poderia ter posto direto
			
			int indexPoolViVj = pools.indexOf(ViVj);
			int indexPoolVjVl = pools.indexOf(VjVl);
			
			graph[indexPoolViVj][indexPoolVjVl] = Wijl;
			
		});
		
		//int start = 0;
		
	    BellmanFordAdjacencyMatrix solver;
	    solver = new BellmanFordAdjacencyMatrix(start, graph);
	    double[] d = solver.getShortestPaths();

	    for (int i = 0; i < n; i++)
	      System.out.printf("The cost to get from node %d to %d is %.2f\n", start, i, d[i]);

	    System.out.println();

	    for (int i = 0; i < n; i++) {
	      String strPath;
	      List<Integer> path = solver.reconstructShortestPath(i);

	      if (path == null) {
	        strPath = "Infinite number of shortest paths.";
	      } else {
	        List<String> nodes = path.stream().map(Object::toString).collect(Collectors.toList());
	        strPath = String.join(" -> ", nodes);
	        
	        Integer[] pathIndexes = path.toArray(new Integer[0]);
	        
	        System.out.printf("The shortest path from %d to %d is: [%s]\n", start, i, strPath);
		    
	        // circular path
	        if (pathIndexes.length >= 1) {
	         Integer lastToken = pathIndexes[pathIndexes.length - 1];
	         EndpointPair<Token> lastpool = pools.get(lastToken);
	         
	         if (token0.equals(lastpool.nodeV())) {
	        	 System.out.println("Cycled"); 
	        	 System.out.println("");
	         } else if ((new Token("0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2")).equals(lastpool.nodeV())) {
	        	// I have no idea what is going on here 
				
				System.out.println("Found Good Arb"); 
	        	 System.out.println("");
	         }
	        
		      for (Integer index: pathIndexes) {
		        	EndpointPair<Token> pool = pools.get(index);
		        	
		        	System.out.println(" [" + index +"]: " + pool.toString());
		        	
		        	// it can go here
		        }
		      
		      System.out.println();
	        }
	        
	        }
	      
	     
	    }
	        
	}
		
	private void MBF_PhaseII(Token  token0) {
		
	
		
	}
	
	public Double[] getDeltaProfitability(double dist, int numberOfHops) {
		
		// give delta Profit and Delta % from initial capital to result
		
		// Double[0] -> Delta Profit
		// Double[1] -> delta %
		
		// e (dist - nYus) - 1
		
		double deltaProfit = (Math.exp(dist - 
					(numberOfHops * UniswapPricingFunctions.YUS_CONSTANT.doubleValue()))) - 1;
		
		double deltaPercent = 100 * deltaProfit;
		
		Double[] response = new Double[2];
		
		response[0] = Double.valueOf(deltaProfit);
		response[1] = Double.valueOf(deltaPercent);
		
		return response;
	}
		
	public Object findOpportunities(UniswapPool[] pools, String token0Address, String marketAddress) {
		
		Token  token0 = new Token (token0Address);
		
		buildG(pools);
		
		putZeroEdge(token0);
		
		buildL();
		
		MBF_Phase_I(token0);
		
		removeZeroEdge(token0);
		
		MBF_PhaseII(token0);
		
		return  packageResults();
		
	}
	
	// for those bellow, check if it can do it
	
	public void printResults() {
		
		System.out.println("G: ");
		//System.out.println(G);
		System.out.println("");
		System.out.println("L: ");
		System.out.println(L);
		System.out.println("");
		
	}
	
	public Object packageResults() {
		
		// return paths with profit, just cycle arbs
		// all pools
		// all tokens
		
		printResults();
		
		return null;
	}
	
	public Set<Token > tokenList() { 
		return G.nodes();
	}
	
	public EndpointPair<Token>[] getPools() {
		
		// test
		
		if (pools != null) {
			return (EndpointPair<Token>[])pools.toArray();
		}
		
		return null;
	}
	
	public String printG() {
		return G.toString();
	}
	
	public String printGEdges() {
		
		Set<EndpointPair<Token >> tokenSet =  G.edges();
		 
		return tokenSet.toString();
	}
	
	public String printL() {
		return L.toString();
	}
	
	 public static void main(String[] args) throws Exception { 
		 

			// filter arbs -> get cycling and no pooling yet
			
		 
		 // we may have problems when tokens > tokens
		 
		 /* YuZhangMethod yusHan = new YuZhangMethod();
		  
		  UniswapPool[] pools = new UniswapPool[5];
		  
		  pools[0] = new UniswapPool("UNI", "WETH:UNI", "1", "USD:UNI", "2");
		  pools[1] = new UniswapPool("UNI", "HK", "3", "USD:PAN", "4");
		  pools[2] = new UniswapPool("UNI", "HK", "1", "WETH:PAN", "2");
		  
		  pools[3] = new UniswapPool("UNI", "USD:UNI", "1", "USD:PAN", "1");
		  pools[4] = new UniswapPool("UNI", "WETH:UNI", "1", "WETH:PAN", "1");
			 
		  
		  yusHan.findOpportunities(pools, "WETH:UNI", "UNI"); */
		  
		 YuZhangMethod yusHan = new YuZhangMethod();
		  
		 /* UniswapPool[] pools = new UniswapPool[3];
		  
		  pools[0] = new UniswapPool("UNI", "WETH", "1", "USD", "2");
		  pools[1] = new UniswapPool("UNI", "HK", "3", "USD", "4");
		  pools[2] = new UniswapPool("UNI", "HK", "1", "WETH", "2");
		  
		  yusHan.findOpportunities(pools, "WETH", "UNI"); */
		 
		  /* 
		 UniswapPool[] pools = new UniswapPool[2];
		  
		  pools[0] = new UniswapPool("UNI", "WETH:UNI", "2", "USD:UNI", "4");
		  pools[1] = new UniswapPool("UNI", "WETH:LOLLA", "3", "USD:UNI", "4");
		  */

		UniswapPool[] pools = new UniswapPool[3];
		  
		  pools[0] = new UniswapPool("UNI1", "WETH", "4", "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2", "2");
		  pools[1] = new UniswapPool("UNI2", "WETH", "1", "KaduCoin", "2");
		  pools[2] = new UniswapPool("UNI3", "KaduCoin", "1", "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2", "3");


		  yusHan.findOpportunities(pools, "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2", "UNI");
		  
		  //System.out.println("Connected and sent!!!!: " + args[0]);
		
		   
		  // we shall put restriction on how many pools against tokens.
		  // TOkens  > Pools ?
		  
		  // now turn this to find the  cycles...
		  
		  /*pools[0] = new UniswapPool("UNI", "WETH:UNI", "1", "USD:UNI", "2");
		  pools[1] = new UniswapPool("UNI", "WETH:PAN", "3", "USD:PAN", "4");
		  
		  pools[2] = new UniswapPool("UNI", "USD:UNI", "1", "USD:PAN", "1");
		  
		  yusHan.findOpportunities(pools, "WETH", "UNI"); */
		  
		  /*Double[] response = yusHan.getDeltaProfitability(1.19, 3);
		  System.out.println(response[0]);
		  System.out.println(response[1]);
		  System.out.println("");*/
		  
		  System.out.println(UniswapPricingFunctions.YUS_CONSTANT.doubleValue());
		
	 }
	

}

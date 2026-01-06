package com.calypso.algorithms.yuzhang.pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.calypso.algorithms.yuzhang.Token;
import com.calypso.general.datastructures.GraphCalypso;
import com.calypso.general.datastructures.Vertex;
import com.calypso.general.datastructures.williamfisset.BellmanFordAdjacencyMatrix;
import com.calypso.uniswap.UniswapPool;
import com.google.common.base.Optional;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class YuZhangMethod_Pool {
	
	// create constantY Yus 
	
	private MutableValueGraph<Token, Double> G; 
	private MutableValueGraph<EndpointPair<Token>, Double> L;
	
	private BellmanFordAdjacencyMatrix BFM;
	
	private Token  ZERO_TOKEN = new Token("Zero_TOKEN");  // go to constant file
	
	public YuZhangMethod_Pool() {
		
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
		
		ArrayList<EndpointPair<Token>> pools = 
				new ArrayList<EndpointPair<Token>>(L.nodes());
		
		System.out.println(pools);
		
		int n = pools.size();
		
		EndpointPair<Token> ZERO_POOL =
				EndpointPair.ordered(this.ZERO_TOKEN, token0);
		
		int start = pools.indexOf(ZERO_POOL);;
		  
		// check for Zero Edge
		
		Set<EndpointPair<EndpointPair<Token>>> poolOfPools = L.edges();
		
		//int n = 9;
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
		
		/*
	    
	    graph[0][1] = 0.7236;
	    graph[0][5] = 0.0; // this may give troubles
	    
	    graph[1][2] = 0.0;
	    graph[2][3] = 0.3181;
	    graph[3][4] = 0;
	    graph[4][1] = 0.7236;
	    graph[5][6] = -0.2572;
	    graph[7][8] = -0.6627;
	    graph[8][5] = -0.2572; */
	    
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
		      
		      for (Integer index: pathIndexes) {
		        	EndpointPair<Token> pool = pools.get(index);
		        	
		        	System.out.println(" [" + index +"]: " + pool.toString());
		        }
		      
		      System.out.println();
	        
	      }
	      
	     
	    }
	        
	}
		
	private void MBF_PhaseII(Token  token0) {
		
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
		System.out.println(G);
		System.out.println("");
		System.out.println("L: ");
		System.out.println(L);
		System.out.println("");
		
	}
	
	public Object packageResults() {
		
		printResults();
		
		return null;
	}
	
	public Set<Token > tokenList() { 
		return G.nodes();
	}
	
	public Set<EndpointPair<Token >> getPools() {
		return G.edges();
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
		 
		 // we may have problems when tokens > tokens
		 
		 YuZhangMethod_Pool yusHan = new YuZhangMethod_Pool();
		  
		  UniswapPool[] pools = new UniswapPool[3];
		  
		  pools[0] = new UniswapPool("UNI", "WETH", "1", "USD", "2");
		  pools[1] = new UniswapPool("UNI", "HK", "3", "USD", "4");
		  pools[2] = new UniswapPool("UNI", "HK", "1", "WETH", "2");
		  
		  yusHan.findOpportunities(pools, "WETH", "UNI"); 
		  
		  
		  // we shall put restriction on how many pools against tokens.
		  // TOkens  > Pools ?
		  
		  // now turn this to find the  cycles...
		  
		  /*
		  pools[0] = new UniswapPool("UNI", "W", "2", "KD", "3");
		  pools[1] = new UniswapPool("UNI", "KD", "2", "BRL", "5");
		  //pools[2] = new UniswapPool("UNI", "BRL", "1", "USD", "5");
		  pools[2] = new UniswapPool("UNI", "W", "1", "USD", "5");
		  //pools[3] = new UniswapPool("UNI", "USD", "4", "KD", "3");
		  
		  //pools[4] = new UniswapPool("UNI", "USDT", "3", "W", "2");
		  
		  yusHan.findOpportunities(pools, "W", "UNI"); */
		  
		  
		  //yusHan.MBF_Phase_I(null); 
		 
	 }
	

}

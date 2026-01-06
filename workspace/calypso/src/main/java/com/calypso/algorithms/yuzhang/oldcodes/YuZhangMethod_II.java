package com.calypso.algorithms.yuzhang.oldcodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.calypso.algorithms.yuzhang.Token_Yu_I;
import com.calypso.general.datastructures.GraphCalypso;
import com.calypso.general.datastructures.Vertex;
import com.calypso.general.datastructures.pi.yuzhang.Edge;
import com.calypso.general.datastructures.pi.yuzhang.Graph_Yu;
import com.calypso.uniswap.UniswapPool;
import com.google.common.base.Optional;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class YuZhangMethod_II {
	
	// create constantY Ys 
	
	private Map<String, Token_Yu_I> tokens;
	private String[] tokenYu;
	
	private MutableValueGraph<Token_Yu_I, Double> G;
	private Graph_Yu L;
	
	Map<String, Double> Dis;
    Map<String, List<String>> Path;
	 
	Map<String, Double> Dtoken = new HashMap<>();
    Map<String, List<String>> Ptoken = new HashMap<>();
	
	private Token_Yu_I ZERO_TOKEN = new Token_Yu_I("Zero_TOKEN", "00"); 
	
	public YuZhangMethod_II() {
		
		tokens = new HashMap<String, Token_Yu_I>();
		
		G = ValueGraphBuilder
				.directed()
				.allowsSelfLoops(true)
				.build();
		
		L = new Graph_Yu();
		
		Dis = new HashMap<>();
		Path = new HashMap<>();
	}
	
	private void buildG(UniswapPool[] pools) {
		int poolsLenght = pools.length;
		
		for(int contPool = 0; contPool < poolsLenght ; contPool++) {
			
			UniswapPool market = pools[contPool];
			
			String tokeni = market.getToken0();
			String tokenj = market.getToken1();
			
			Double[] prices = market.getMidPricing(); // 0 -> Pij, 1 -> Pji
			
			Token_Yu_I i = new Token_Yu_I(tokeni, market.getMarketAddress());
			Token_Yu_I j = new Token_Yu_I(tokenj, market.getMarketAddress());
			
			G.putEdgeValue(i, j, prices[0].doubleValue());
			G.putEdgeValue(j, i, prices[1].doubleValue());
		}
		
	}
	
	private void buildL() {
		
		this.tokenYu = G.nodes()
				.stream()
				.map((node) -> node.toString())
				.toArray(String[]::new); 
		
		Set<EndpointPair<Token_Yu_I>> edges =  G.edges();
		
		// prepare token list to numbers
		
		// check for one degree vertices and take them out.
				// I want 2 predecessors and 1 sucessor, 
				// as one successor is one predecessor
		// connect same tokens pools with different addresses, attribute cost zero to both edges.
		
		edges.forEach( (pair) -> {
			
			Token_Yu_I tokeni = pair.nodeU();
			Token_Yu_I tokenj = pair.nodeV();
			
			addEdgeToL(tokeni, tokenj);
			
		}); // for each pair
	}
	
	private void addEdgeToL(Token_Yu_I tokeni, Token_Yu_I tokenj) {
		Set<Token_Yu_I> successors = G.successors(tokenj);
		
		successors.forEach((tokenl) -> {
			
			if (!(tokenl.equals(tokeni))) {
				
				double Wijl = G.edgeValue(tokenj, tokenl).get();
				
				String vi = tokeni.toString();
				String vj = tokenj.toString();
				
				L.addEdge( vi, vj, Wijl);
				
				String key = vi + "->" + vj;
				
				Dis.put(key, Double.POSITIVE_INFINITY);
		        Path.put(key, new ArrayList<>());
			}

		}); // for each token

	}
	
	private void putZeroEdge(Token_Yu_I token0) {
		// Edge 0Vo = EndpointPair.ordered( ZERO_TOKEN, token0)
		
		//Dis.put(EndpointPair.ordered( ZERO_TOKEN, token0), Double.valueOf(0.0)); 
		addEdgeToL(ZERO_TOKEN, token0);
		
		String O = ZERO_TOKEN.toString();
		String v0 = token0.toString();
		
		String key = O + "->" + v0;
		
		Dis.put(key, 0.0);
		Path.put(key, new ArrayList<>());
		
	}
	
	
	private void MMBF_PhaseI(Token_Yu_I token0) {
		
		int M = this.tokenYu.length;
		String v0 = token0.toString();
	    
	   // Dis.put("(0, " + v0 + ")", 0.0);

	    for (int m = 1; m <= M; m++) { // for each token ou transformar Dis e Path into arrays
	        for (Edge e : L.getEdges()) {
	            String vi = e.getSource();
	            String vj = e.getDestination();
	            //String vl = String.valueOf(m);
	            String vl = this.tokenYu[m - 1];
	            
	            String key = vi + "->" + vj;
	            double dist_via_key = Dis.get(key).doubleValue() + e.getWeight();

	            if (Dis.get(vj + "->" + vl) != null) {
	            	double disVjVl = Dis.get(vj + "->" + vl).doubleValue();
		            if (dist_via_key <  disVjVl &&
		                    (!Path.get(vj + "->" + vl).contains(vl) || m == 0)) {
		                List<String> path = Path.get(key);
		                path.add(vl);
		                Path.put(vj + "->" + vl, path);
		                Dis.put(vj + "->" + vl, dist_via_key);
		            }
	            }
	        }
	    }
	}
	
	private void MMBF_PhaseII(Token_Yu_I token0) {

	    for (Map.Entry<String, Double> entry : Dis.entrySet()) {
	        String t = entry.getKey().split("->")[1];
	        Double dist = entry.getValue();

	        if (dist < Dtoken.getOrDefault(t, Double.POSITIVE_INFINITY)) {
	            Dtoken.put(t, dist);
	            Ptoken.put(t, Path.get(entry.getKey()));
	        }
	    }
	}
	
	public Object findOpportunities(UniswapPool[] pools, String token0Address, String marketAddress) {
		
		Token_Yu_I token0 = new Token_Yu_I(token0Address, marketAddress);
		
		buildG(pools);
		putZeroEdge(token0);
		buildL();
		MMBF_PhaseI(token0);
		MMBF_PhaseII(token0);
		
		return  packageResults();
	}
	
	public Set<Token_Yu_I> tokenList() { 
		return G.nodes();
	}
	
	public Set<EndpointPair<Token_Yu_I>> getPools() {
		// remove Zero edge
		return G.edges();
	}
	
	public Object packageResults() {
		
		printResults();
		
		return null;
	}
	
	// for debugging, must be erased
	
	public String printG() {
		return G.toString();
	}
	
	public String printGEdges() {
		return getPools().toString();
	}
	
	public String printL() {
		return G.toString();
	}
	
	public void printResults() {
		
		System.out.println("G: ");
		System.out.println(G);
		System.out.println("");
		System.out.println("L: ");
		System.out.println(L);
		System.out.println("");
		System.out.println("Dis: ");
		System.out.println(Dis);
		System.out.println("");
		System.out.println("Path:");
		System.out.println(Path);
		System.out.println("");
		System.out.println("Dtoken:");
		System.out.println(Dtoken);
		System.out.println("");
		System.out.println("Ptoken:");
		System.out.println(Ptoken);
	}
	
	 public static void main(String[] args) throws Exception { 
		 
		 YuZhangMethod_II yusHan = new YuZhangMethod_II();
		  
		  UniswapPool[] pools = new UniswapPool[6];
		  
		  
		  pools[0] = new UniswapPool("UNI", "WETH", "1", "USD", "2");
		  pools[1] = new UniswapPool("UNI", "HK", "3", "USD", "4");
		  pools[2] = new UniswapPool("UNI", "HK", "1", "WETH", "2");
		  
		  yusHan.findOpportunities(pools, "WETH", "UNI");
		  
		  
		 /* pools[0] = new UniswapPool("UNI", "WETH:POOL1", "1", "USD:POOL!", "2");
		  pools[1] = new UniswapPool("UNI", "HK:POOL2", "3", "USD:POOL2", "4");
		  pools[2] = new UniswapPool("UNI", "HK:POOL3", "1", "WETH:POOL3", "2");
		  
		  pools[3] = new UniswapPool("UNI", "HK:POOL2", "1", "HK:POOL3", "1");
		  pools[4] = new UniswapPool("UNI", "USD:POOL1", "1", "USD:POOL2", "1");
		  pools[5] = new UniswapPool("UNI", "WETH:POOL1", "1", "WETH:POOL3", "1");
		  
		  
		  yusHan.findOpportunities(pools, "WETH:POOL1", "UNI");
		   * 
		   * 
		   */
		 
	 }
	

}

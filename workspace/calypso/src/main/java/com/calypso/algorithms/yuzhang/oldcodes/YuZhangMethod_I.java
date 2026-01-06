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
import com.calypso.general.datastructures.pi.AllPathsFinder;
import com.calypso.uniswap.UniswapPool;
import com.google.common.base.Optional;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class YuZhangMethod_I {
	
	// create constantY Ys 
	
	private Map<String, Token_Yu_I> tokens;
	
	private MutableValueGraph<Token_Yu_I, Double> G;
	private MutableValueGraph<EndpointPair<Token_Yu_I>, Double> L;
	
	private Map<EndpointPair<Token_Yu_I>, Double> Dis = new HashMap<EndpointPair<Token_Yu_I>, Double>();
	
	private MutableGraph<Token_Yu_I> Path;
	
	private Map<Token_Yu_I, Double> D_Token = new HashMap<Token_Yu_I, Double>(); // optmize
	private Map<Token_Yu_I, List<List<Token_Yu_I>>> P_Token = new HashMap<Token_Yu_I, List<List<Token_Yu_I>>>(); // optmize
	
	private Token_Yu_I ZERO_TOKEN = new Token_Yu_I("Zero_TOKEN", "00"); 
	
	public YuZhangMethod_I() {
		tokens = new HashMap<String, Token_Yu_I>();
		
		G = ValueGraphBuilder
				.directed()
				.allowsSelfLoops(true)
				.build();
		
		L = ValueGraphBuilder
				.directed()
				.allowsSelfLoops(true)
				.build();
		
		Path = GraphBuilder
				.directed()
				.allowsSelfLoops(false)
				.build();
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
		
		Set<EndpointPair<Token_Yu_I>> edges =  G.edges();
		
		// check for one degree vertices and take them out.
				// I want 2 predecessors and 1 sucessor, 
				// as one successor is one predecessor
		
		
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
				
				EndpointPair<Token_Yu_I> ViVj =  EndpointPair.ordered( tokeni, tokenj);
				EndpointPair<Token_Yu_I> VjVl =  EndpointPair.ordered( tokenj, tokenl);
				
				double Wijl = G.edgeValue(tokenj, tokenl).get();
				
				L.putEdgeValue(ViVj, VjVl, Wijl);
				// recover: Dis.put(VjVl, Double.POSITIVE_INFINITY ); 
				
			}

		}); // for each token
	}
	
	private void putZeroEdge(Token_Yu_I token0) {
		// Edge 0Vo = EndpointPair.ordered( ZERO_TOKEN, token0)
		
		Dis.put(EndpointPair.ordered( ZERO_TOKEN, token0), Double.valueOf(0.0)); 
		
		Path.addNode(token0); // check if we need to change
		
		addEdgeToL(ZERO_TOKEN, token0);
		
	}
	
	private void removeZeroEdge(Token_Yu_I token0) {
		Dis.remove(EndpointPair.ordered(ZERO_TOKEN, token0));
		// remove ZEROToken?
	}
	
	private void MMBF_PhaseI(Token_Yu_I token0) {
		int M = G.nodes().size();
		
		Set<EndpointPair<EndpointPair<Token_Yu_I>>> pools =  L.edges();
		
		// put flag when relax an edge
		
		for (int cont = 1; cont <= M; cont++) {
			
			Iterator<EndpointPair<EndpointPair<Token_Yu_I>>> it = pools.iterator();
			
			while(it.hasNext()) {
				EndpointPair<EndpointPair<Token_Yu_I>> edgeL = it.next();
				
				EndpointPair<Token_Yu_I> edgeViVj = edgeL.nodeU();
				EndpointPair<Token_Yu_I> edgeVjVl = edgeL.nodeV();
				
				double Wijl = L.edgeValue(edgeViVj, edgeVjVl).get();
				
				Token_Yu_I tokenl = edgeVjVl.nodeV();
				
				double disViVj = Dis.get(edgeViVj).doubleValue();
				double disVjVl = Dis.get(edgeVjVl).doubleValue();
				double hopLength = disViVj + Wijl;
				
				boolean isVlStarter = tokenl.equals(token0);
				// error path deve vir de to para tl, check if it may be possible to optmize
				//boolean isVlNotInPathViVj = AllPathsFinder.findAllPaths(Path, token0, tokenl).isEmpty();
				
				boolean isVlNotInPathViVj = true;
				
				//boolean isVlNotInPathViVj = !(Path.nodes().contains(tokenl));
				
				if (((hopLength) < disVjVl ) && (isVlStarter || isVlNotInPathViVj)) {
					
					Dis.put(edgeVjVl, hopLength);
					
					Token_Yu_I tokenj = edgeVjVl.nodeU();
					
					/*if ((tokenj.equals(token0))) { 
						// I dont believe in such scenario...
						
						System.out.println("LIne 169: Zero == tokeni");
						
					} else { */
					
					Set<Token_Yu_I> predecessorsTokenj = Path.predecessors(tokenj);
					int numberOfPredecessorsj = predecessorsTokenj.size();
					
					if (numberOfPredecessorsj > 1) {  
						// I dont believe in such scenario...
						
						System.out.println("ERRO LIne 189: number of predecessor greater than 1");
						//return null;
					} else {
						if (numberOfPredecessorsj == 1) {
							Token_Yu_I tokeni_old = predecessorsTokenj
									.iterator()
									.next();
							
							Path.removeEdge(tokeni_old,  tokenj);
						} 
						
						Path.putEdge(tokenj, tokenl); // serah isto necessario, nao irah causar erro?
						Token_Yu_I tokeni = edgeViVj.nodeU(); // do no anterior
						
						if (!(tokeni.equals(ZERO_TOKEN))) {
							Path.putEdge(tokeni, tokenj);
							// path tem que dar o camino dos pools, nao apenas o token
							// se eu quiser ver o caminho dos pools, terei que muar o approach usando endpoint
							// senao der certo usar o approach do paper
						}
					}	
					//}
				}
			}
				
		}
	}
		
	private void MMBF_PhaseII(Token_Yu_I token0) {
		
		Dis.forEach((Kd,Vd) -> {
			
			EndpointPair<Token_Yu_I>  edgeViVj= Kd;
			
			Token_Yu_I t = edgeViVj.nodeV();
			
			D_Token.put(t, Vd);
			
			this.P_Token.put(t, AllPathsFinder.findAllPaths(Path, token0, t)); // check if we may optmize
		});
	}
		
	public Object findOpportunities(UniswapPool[] pools, String token0Address, String marketAddress) {
		
		Token_Yu_I token0 = new Token_Yu_I(token0Address, marketAddress);
		
		buildG(pools);
		
		putZeroEdge(token0);
		
		buildL();
		
		MMBF_PhaseI(token0);
		
		removeZeroEdge(token0);
		
		MMBF_PhaseII(token0);
		
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
		/*System.out.println("Dis: ");
		System.out.println(Dis);
		System.out.println("");
		System.out.println("Path:");
		System.out.println(Path);
		System.out.println("");*/

	}
	
	public Object packageResults() {
		
		printResults();
		
		return null;
	}
	
	public Set<Token_Yu_I> tokenList() { 
		return G.nodes();
	}
	
	public Set<EndpointPair<Token_Yu_I>> getPoolsInYus() {
		return L.nodes();
	}
	
	public String printG() {
		return G.toString();
	}
	
	public String printGEdges() {
		
		Set<EndpointPair<Token_Yu_I>> tokenSet =  G.edges();
		 
		return tokenSet.toString();
	}
	
	public String printL() {
		return L.toString();
	}
	
	 public static void main(String[] args) throws Exception { 
		 
		 YuZhangMethod_I yusHan = new YuZhangMethod_I();
		  
		  UniswapPool[] pools = new UniswapPool[3];
		  
		  pools[0] = new UniswapPool("UNI", "WETH", "1", "USD", "2");
		  pools[1] = new UniswapPool("UNI", "HK", "3", "USD", "4");
		  pools[2] = new UniswapPool("UNI", "HK", "1", "WETH", "2");
		  
		  yusHan.findOpportunities(pools, "WETH", "UNI");
		  
		  /*System.out.println(yusHan.printG());
		  System.out.println("");
		  System.out.println(yusHan.printGEdges());*/
		 
	 }
	

}

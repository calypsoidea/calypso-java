package com.calypso.general.datastructures;

import java.util.*;

import com.google.common.graph.*;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import com.calypso.algorithms.yuzhang.Token_Yu_I;
import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import javax.annotation.CheckForNull;

public class GraphCalypso {

	  
	  public static void main(String[] args) throws Exception { 
		  
		  MutableValueGraph<Token_Yu_I, Double> weightedGraph = ValueGraphBuilder
				  .directed()
				  .allowsSelfLoops(true)
				  .build();
		  
		  Token_Yu_I usdUni = new Token_Yu_I("USD", "UNI");
		  Token_Yu_I usdPan = new Token_Yu_I("USD", "PAN");
		  Token_Yu_I wethUni = new Token_Yu_I("WETH" ,"UNI");
		  Token_Yu_I wethPan = new Token_Yu_I("WETH" ,"PAN");
		  
		  weightedGraph.putEdgeValue(wethUni, usdUni, 2.0);
		  weightedGraph.putEdgeValue(usdUni, wethUni, 2.0);
		  
		  weightedGraph.putEdgeValue(usdPan, usdUni, 0.0);
		  weightedGraph.putEdgeValue(usdUni, usdPan, 0.0);
		  
		  weightedGraph.putEdgeValue(usdPan, wethPan, 4.5);
		  weightedGraph.putEdgeValue(wethPan, usdPan, 4.5);
		  
		  
		  Set<Token_Yu_I> predecessors = weightedGraph.predecessors(usdUni);
		  int degreeOut = weightedGraph.predecessors(usdUni).size();
		  
		  System.out.println("NUmber of Edges Out: " + degreeOut);
		  
		  predecessors.forEach((token) -> {
			  System.out.println("");
			  System.out.println("Token Address: " + token.getTokenAddress());
			  System.out.println("Market Address: " + token.getMarketAddress());
		  });
		  
		 
		  
	  }
		  
		  

				

}


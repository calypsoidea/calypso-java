package com.calypso.algorithms.yuzhang;

import java.util.Objects;

public class Pool_Yus  {
	
	private Token_Yu_I Vi;
	private Token_Yu_I Vj;

	private Pool_Yus(Token_Yu_I Vi, Token_Yu_I Vj) {
		
		this.Vi = Vi;
		this.Vj = Vj;
	}
	
	

	public Token_Yu_I getVi() {
		return Vi;
	}



	public void setVi(Token_Yu_I vi) {
		Vi = vi;
	}



	public Token_Yu_I getVj() {
		return Vj;
	}



	public void setVj(Token_Yu_I vj) {
		Vj = vj;
	}



	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pool_Yus) {
		      Pool_Yus that = (Pool_Yus) obj;
		      return this.Vi.equals(that.Vi) && 
		    		 this.Vj.equals(that.Vj);
		    }
		    
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Vi.hashCode(), Vj.hashCode());
	}
	
	@Override
	  public String toString() {
	    return "( " + Vi.toString() + " -> " + Vj.toString() + " )";
	  }


}

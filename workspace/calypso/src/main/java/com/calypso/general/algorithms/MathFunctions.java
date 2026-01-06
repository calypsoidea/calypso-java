package com.calypso.general.algorithms;

public class MathFunctions {
	public static double[] calculateRoots(double a, double b, double c) {
	    double discriminant = b * b - 4 * a * c;
	
	    if (discriminant > 0) {
	      double sqrtD = Math.sqrt(discriminant);
	      double root1 = (-b + sqrtD) / (2 * a);
	      double root2 = (-b - sqrtD) / (2 * a);
	      return new double[]{root1, root2};
	    } else if (discriminant == 0) {
	      double root = -b / (2 * a);
	      return new double[]{root, root};
	    } else {
	      // The quadratic equation has no real roots
			return new double[]{Double.NaN, Double.NaN};
		}
	}
	
	public static Double[] calculateRoots(double a, double b, double c, double threshold) {
	    double discriminant = b * b - 4 * a * c;

	    if (discriminant > 0) {
	      double sqrtD = Math.sqrt(discriminant);
	      double root1 = (-b + sqrtD) / (2 * a);
	      double root2 = (-b - sqrtD) / (2 * a);

	      // Check if both roots are non-negative and less than the threshold
		if (root1 >= 0 && root1 < threshold && root2 >= 0 && root2 < threshold) {
		        return new Double[]{root1, root2};
		      }
		    } else if (discriminant == 0) {
		      double root = -b / (2 * a);
		      // Check if the single root is non-negative and less than the threshold
		if (root >= 0 && root < threshold) {
		        return new Double[]{root, root};
		      }
		    }
	
		    // No valid roots found
	    return null;
	  }

  
}



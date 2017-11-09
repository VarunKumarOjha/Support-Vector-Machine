import java.io.*;
import java.util.*;

public class Kernel {
        /**
         * Calculates the squared Euclidean distance of two vectors
         * @param x the first point (vector)
         * @param z the second point (vector)
         * @return The squared Euclidean distance
         */
        public static double euclidean_dist2(Vector v, Vector  w)
		{
                //System.out.println("I M Euclidean");
                double sum=0;
                int i,j;
				
                for (i=0,j=0; i<v.size() && j<w.size(); ) 
                {           
                     double a = Double.parseDouble(v.get(i).toString());
					 double b = Double.parseDouble(w.get(j).toString());
                     sum += (a-b)*(a-b);
                     i++;
                     j++;
                }
                return sum;
        }
        /**
         * Calculates the dot product of two vectors
         * @param x the first vector
         * @param z the second vector
         * @return The dot product of the vectors
         */

		 public static double dot_product(Vector v, Vector  w)
        {
                double sum=0;
                int i,j;
				
                for (i=0,j=0; i<v.size() && j<w.size(); ) 
                {           
                     double a = Double.parseDouble(v.get(i).toString());
					 double b = Double.parseDouble(w.get(j).toString());
                     sum += a*b;
                     i++;
                     j++;
                }
                return sum;
        }
        /**
         * Linear kernel: k(x,z) = <x,z>
         * @param x first vector
         * @param z second vector
         * @return linear kernel value
         */
 		
		public static double kLinear(Vector v, Vector w)
	    {
           return dot_product(v, w);
        }
		
        /**
         * Polynomial kernel: k(x,z) = (a*<x,z>+b)^c
         * @param x first vector
         * @param z second vector
         * @param a coefficient of <x,z>
         * @param b bias
         * @param c power
         * @return polynomial kernel value
         */
        public static double kPoly(Vector v, Vector w, double a, double b, double c) {
                if (c == 1.0)
                     return a*dot_product(v, w)+b;
					 
                return Math.pow(a*dot_product(v, w)+b, c);
        }
        /**
         * Gaussian (RBF) kernel: k(x,z) = (-0.5/sigma^2)*||x-z||^2
         * @param x first vector
         * @param z second vector
         * @param sigma parameter (standard deviation)
         * @return Gaussian kernel value
         */
        public static double kGaussian(Vector v, Vector w, double sigma) {
                return (-0.5/sigma*sigma)*euclidean_dist2(v, w);
        }
        /**
         * Tanh (sigmoid) kernel: k(x,z) = tanh(a*<x,z>+b)
         * @param x first vector
         * @param z second vector
         * @param a coefficient of <x,z>
         * @param b bias
         * @return tanh kernel value
         */
        public static double kTanh(Vector v, Vector w, double a, double b) {
                return Math.tanh(a*dot_product(v, w)+b);
        }
}

import java.io.*;
import java.util.*;

public class Problem
{
    public int n = 0;//number of features
	public int l = 0;//number of patterns
	public int y[];//vector containing type of data	
	public Vector x[];//Array of vectors containing data	
	
	public Problem(){
	    System.out.println("I am @ Problem Constructor");
            l = 0;
            n = 0;
    }
	
    public void loadProblem(PatternSVM[] patterns,int features){
	    System.out.print("I am loading problem...");
		n = features;
		l = patterns.length;
		Vector example = new Vector();
		Vector data;
	    y = new int[l];
		x = new Vector[l];
		for(int i = 0; i < l; i++){
			y[i] = (int)patterns[0].target;
            //System.out.print(y[i] +" :"); 			
			data = new Vector();
			for(int j = 0; j < n; j++){
				double elt = (double)patterns[i].input[j];  				   
				data.add(elt); // Adding element of the Example	
                //System.out.print(elt +" ");		
			}//go to next feature 
			//System.out.println();
			example.add(data);
		}//go to next pattern			   

		for(int i=0; i < l; i++){    
			 //System.out.print(y[i] +":");
			 x[i] = (Vector)example.get(i);
			 //System.out.println(x[i]);
		}	
		System.out.println(" :done!");
    }		
	
    public void loadProblem(String filename){
	     Vector cat = new Vector();
		 Vector example = new Vector();
		 Vector data;
		 
	     int row = 0, col = 0; 
         
		 try{
           FileReader  fr = new FileReader(filename);
           BufferedReader br =  new BufferedReader(fr);
           
           Scanner s = null;
           String Data;
		   int i=0, j=0;

           while((Data = br.readLine()) != null){
		         col = Data.split(" ").length-1;
				 //System.out.println("Print Col:"+col);
				 
                 s = new Scanner(Data);
			     if (s.hasNextDouble())
 				 {   
				     int type = (int)s.nextDouble();
       				 cat.add(type);// Category of the Example
					 //System.out.print(type);
				 }	 
				 
				 data = new Vector();
				 
                 for(j = 0; j < col; j++){
                   if (s.hasNextDouble())
				   { 
                      double elt = (double)s.nextDouble();  				   
                      data.add(elt); // Adding element of the Example
                      //System.out.print(" "+elt);  					  
				   }   
				}
                  				 
 				example.add(data);
			    i++;// Increase row counter
           }
		   row = i; 
		   //System.out.println(row); 
        }
		catch (Exception e)
		{
             System.out.println(e);
        }
	      
		 n = col;
		 //System.out.println(n);
		 l = row; 
		 //System.out.println(l);
	     y = new int[l];
		 x = new Vector[l];
		 
		 int i=0,j = 0;   
			
		for(i=0; i < l; i++)
		{
		     
    	     y[i] = (Integer)cat.get(i);
			 System.out.print(y[i] +":");
			 x[i] = (Vector)example.get(i);
			 System.out.println(x[i]);
		}
		
		//System.out.println(x[2].get(0)); 
	}   
	

	void print(Vector v)
	{
	   for(int j=0; j < n; j++) 
		  {
		     double d = Double.parseDouble(v.get(j).toString());
		     System.out.print(" "+d);
		  }
	}		
}
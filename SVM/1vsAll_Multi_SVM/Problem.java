import java.io.*;
import java.util.*;

public class Problem
{
    public int n = 0;//number of features
	public int l = 0;//number of patterns
	public int y[];//vector containing type of data	
	public Vector x[];//Array of vectors containing data	
	
	
	public Problem() 
	{
            l = 0;
            n = 0;
    }
	
    public void loadProblem(String filename,int cls)
    {
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

           while((Data = br.readLine()) != null)
		   {
		         col = Data.split(" ").length-1;
				 //System.out.println("Print Col:"+col);
				 
                 s = new Scanner(Data);
			     if (s.hasNextDouble())
 				 {   
				     int type = (int)s.nextDouble();
					 
					 if(type == cls)
					   type = 1;
					 else
                       type = -1;
					   
       				 cat.add(type);// Category of the Example
					 //System.out.print(type);
				 }	 
				 
				 data = new Vector();
				 
                 for(j = 0; j < col; j++)
                {
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
			 //System.out.println(y[i]);
			 x[i] = (Vector)example.get(i);
			 //System.out.println(x[i]);
		}
		
		//System.out.println(x[2].get(0));
	}   
	
	public void loadtestProblem(String filename)
    {
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

           while((Data = br.readLine()) != null)
		   {
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
				 
                 for(j = 0; j < col; j++)
                {
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
			 //System.out.println(y[i]);
			 x[i] = (Vector)example.get(i);
			 //System.out.println(x[i]);
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
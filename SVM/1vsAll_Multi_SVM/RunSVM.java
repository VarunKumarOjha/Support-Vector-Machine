import java.io.*;
import java.util.*;

public class RunSVM
{

   public static void main(String args[])
    {
	  
	   int count = 0;
       int type[] = new int[10];
	  
      System.out.println("I M 1 vs All Support Vector Machine");
	  //Load Problem
	  
	  try{
           FileReader  fr = new FileReader("Mtrain.txt");
           BufferedReader br =  new BufferedReader(fr);
           
           Scanner s = null;
           String Data;
		   int i=0;
		   
           while((Data = br.readLine()) != null)
		    {
		         s = new Scanner(Data);
				 if(count == 0)
				 {
			       if (s.hasNextDouble())
 				    {   
				      type[i] = (int)s.nextDouble();
					  //System.out.println(type[i]);
					  count++;
				    }
				}
                else				 
				{
				    if (s.hasNextDouble())
 				    {   
					  int cls = (int)s.nextDouble(); 
					  boolean found = false;
					  //System.out.println(cls); 
					    for(int j = 0; j < count; j++)
					    {
						   if(type[j] == cls)
						   {
						     found = true;
							 break;
						   }
						}
						if(!found)
					    {
					        i++;
					        type[i] = cls; 
					        count++;
					    }
				    }
				}
            }
		}	
		catch (Exception e)
		{
             System.out.println(e);
        }
	  
	  
	  System.out.print("Total No. Class are: "+ count+" :");
	  for(int i = 0; i < count; i++)
      {System.out.print(" "+type[i]);}	 
      
	  System.out.print("   Sorting types : ");
      for(int i = 0; i< count; i++) 		 
	  {
	    int min  = type[i];
		int min_index = i;
        for(int j = i+1; j<count; j++)
        {
            if(type[j-1] > type[j])
            {
                min = type[j];
				min_index = j;
            }
		}
		int temp = type[i];
        type[i] = min;
		type[min_index] = temp;
	  }
	  
	  for(int i = 0; i < count; i++)
      {System.out.print(" "+type[i]);}	 
      
      System.out.println(); 
	  
	     
	  Problem train[] = new Problem[count]; 
	  SVM s[] = new SVM[count];
	  Model model[] = new Model[count];
	  
	  for(int i = 0; i < count; i++)
	  {
	     train[i] = new Problem();
		 train[i].loadProblem("Mtrain.txt", type[i]);
		 System.out.println("Training SVM : "+ i + " for category : "+ type[i]);
	     s[i] = new SVM();
		 model[i] = new Model(); 
	     s[i].svmTrain(train[i], model[i]);//training ith SVM
	  }
	    
	  int No_Test_Sample = 1;
	  Problem test[]  = new Problem[No_Test_Sample];
	  test[0] = new Problem();
	  test[0].loadtestProblem("Mtest.txt");
	  
	  System.out.println("No. of Test Sample:"+test[0].l);	
	  System.out.println("Loading Test Data");	
	  for(int i = 0; i < test[0].l; i++)
	    {
		  System.out.println(" ");
		  test[0].print(test[0].x[i]);
		  System.out.println(" :"+test[0].y[i]); 
	    } 	
		
	for(int j = 0;j<count; j++)
	{
	    for (int i=0; i<model[j].l; i++) 
	    {
          System.out.println(" [:"+model[j].alpha[i]+ " :"+ model[j].y[i] +" :"+ model[j].x[i]+"]");
        }
		System.out.println();
	}	
	
	 //int [] pred = s[0].svmTest(test[0]);
	 
      int [] pred = new int[test[0].l];// no. of test sample/result in hand/required
	  
      for (int i=0; i<test[0].l; i++) 
	  { 
        System.out.print("Sample  = :"+i);  	  
	    int result = 0;
	    for(int j = 0;j<count; j++)// no. of SVM in hand
	    {
	      result = (svmTestOne(test[0].x[i], model[j])<=0?-1:1);// testing ith test sample with jth svm
		  //System.out.println(result);
		  if(result >= 1)
		  {
		     pred[i] = j+1;
			 break;
		  }
		  else
		    pred[i] = 0;
	    }
		System.out.print(" Classified to :"+pred[i]+"\n");
         
      }
       
     
    	//for (int i=0; i<pred.length; i++)
               //System.out.println(pred[i]);
		 
      //EvalMeasures e = new EvalMeasures(test[0], pred, 2);
       //     System.out.println("Accuracy=" + e.Accuracy());
                
      System.out.println("Done.");	

	}
	
	 public static double svmTestOne(Vector v, Model model) 
	 {
               double f = 0;
                for (int i=0; i<model.l; i++) 
				{
                        f += model.alpha[i]*model.y[i]*kernel(v, model.x[i]);
                }
                return f+model.b;
     }
	 
	 public static double kernel(Vector v, Vector w) 
	 {
            double ret = 0;
            ret = Kernel.kLinear(v, w);
            return ret;
     }
}
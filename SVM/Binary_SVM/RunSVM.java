public class RunSVM
{
   JavaRand randGenJava = new JavaRand();
   public void mainSVM(){
	 try{
	    PatternSVM[] pat; 
		int netInfo[] = new int[5];
	    LoadDataRegClass loadData = new LoadDataRegClass();
		loadData.loadDataRegressionClassification();
	    loadData.loadData();
		pat = loadData.runProblemSVM(); 
		netInfo = loadData.networkInfo();
		int features = netInfo[0];//features - cols		
		int totalPat = pat.length;//examples - rows
		int ex;
		int randPat[] = new int[totalPat];
		randPat = parmutation(0,totalPat-1);
		//totalPat = (int)(totalPat*0.05);
		int trainPat = (int)(totalPat*0.6);
		int testPat = totalPat-trainPat;
		PatternSVM[] patTrain = new PatternSVM[trainPat];
		PatternSVM[] patTest = new PatternSVM[testPat];
		//for(i = 0; i < 20; i++){ System.out.print(randPat[i]+" --");}
		for(ex = 0; ex < totalPat; ex++){ 	  
		   if(ex < trainPat){patTrain[ex] = pat[randPat[ex]];}   
		   else{ patTest[ex - trainPat] = pat[randPat[ex]];}   
		}//end totalPat
		System.out.println("Examples Total - "+totalPat+" Training (60%) - "+trainPat+" Test (40%) -"+testPat);		
		
		System.out.println("I M Support Vector Machine");
		//Load Problem
		Problem train = new Problem(); 
		Problem test  = new Problem();
	  
		train.loadProblem(patTrain,features);
		test.loadProblem(patTest,features);
	  
	   
		System.out.println("No. of Trianing Sample:"+train.l);  
		// System.out.println("Loading Trining Data");
		// for(int i = 0; i < train.l; i++){
		  // System.out.print(" ");
		  // train.print(train.x[i]);
		  // System.out.println(" :"+train.y[i]); 
	    // }
		
     
		// System.out.println("Loaded.");
		// System.out.println("Training with "+train.l+" No. of Trianing Sample:");  
	  
		SVM s = new SVM();
		s.svmTrain(train);//training SVM

		System.out.println("No. of Test Sample:"+test.l);	
		// System.out.println("Loading Test Data");	
		// for(int i = 0; i < test.l; i++){
		  // System.out.print(" ");
		  // train.print(test.x[i]);
		  // System.out.println(" :"+test.y[i]); 
	    // } 	
		
		int [] pred = s.svmTest(test);
		//for (int i=0; i<pred.length; i++)
         //System.out.println(pred[i]);
		 
		EvalMeasures e = new EvalMeasures(test, pred, 2);
            System.out.println("Accuracy=" + e.Accuracy());
                
		System.out.println("Done!");
      }catch(Exception e){}		
	}//end mainSVM
	
	// Generating permutation within a range of numbers
	public int[] parmutation(int min,int max){
		int a[] = new int[max+1];
		for(int i = 0; i<max+1; i++){
		   if(i ==0){a[i] = randGenJava.randVal(min,max);}
		   else{
			 while(true){
			    boolean flag = false; 
				int r = randGenJava.randVal(min,max);
				for(int j = 0; j<i;j++){
				  if(r == a[j]){
					flag = true;
					break;
				  }//if	
				}//for
				if(!flag){
				   a[i] = r;
				   break;
				}//if   
			 }//while				
		   }//else 	 
		   //System.out.print(" "+a[i]+" ");
		}//for
		return a;
	}//permutation
	
	 public static void main(String args[]){
	    RunSVM rn = new RunSVM();
		rn.mainSVM();
	 }//end main
	 
}
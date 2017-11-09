import java.io.*;//import input output
import java.util.Random;
import java.util.Scanner; 
import java.util.*;

public class LoadDataRegClass{
  
  public String DataSet = ""; 
  public Vector catName = new Vector();
  public Vector inputName = new Vector();
  public Vector outputName = new Vector();
  public Vector AttrVector = new Vector(); 
  public AttrClass AtrCls;
  public TrainPat tp;
  public Vector Data = new Vector();
  public double dataX[];
  public double dataY[];
  double x[][];
  double y[][];
  double t[];
  boolean classificationProblem = false;
  
  public double normalize(double x,double dataLow,double dataHigh,double normalizedLow,double normalizedHigh){
		return ((x - dataLow) / (dataHigh - dataLow)) * (normalizedHigh - normalizedLow) + normalizedLow; 
  }
  public double denormalize(double x,double dataLow,double dataHigh,double normalizedLow,double normalizedHigh){
		return ((dataLow - dataHigh) * x - normalizedHigh * dataLow + dataHigh * normalizedLow)	/ (normalizedLow - normalizedHigh);
  }
  
  public boolean isNumeric(String s) {  return s.matches("[-+]?\\d*\\.?\\d+"); } 
  public boolean isClass(String s) { return (s.equals("CLASS") || s.equals("Class") || s.equals("class") || s.equals("Type")); } 

  public void callRelation(String Rel,PrintWriter pr){
     //System.out.println(Rel);
     Scanner sr = new Scanner(Rel);
	 while(sr.hasNext()){
		//System.out.println(sr.next());
		String relVal = sr.next();
		
		if(relVal.equals("@RELATION") || relVal.equals("@relation") || relVal.equals("@Relation")){
		  //Do nothing just print relation Name
		  DataSet = sr.next();
		  System.out.println("Dataset is "+DataSet);
		}
		else {
	        throw new Error("Error: Bad Arff file");
	    }
	 }
    //pr.println(Rel); //optional
  }//end callRelation
  
  public void callAttribute(String Attr,PrintWriter pr){
	 String delims = "[\\[\\]\\{\\}\\s*,]+";//
     String[] tokens = Attr.split(delims);
	 //System.out.println(tokens.length);
	 int i = 0;
	 String attrName = tokens[i]; 
	 i++; //counted a token
	 if(attrName.equals("@ATTRIBUTE") || attrName.equals("@Attribute") || attrName.equals("@attribute")){ 	 
		  //Token is  an attribute
		
        //System.out.println("Token Name:"+attrName);	 
	    if(isClass(tokens[i])){ // Attribute is a class? 
		   //System.out.println("Attr Name:"+tokens[i]);
		   i++; //counted a token
		   int j = 0;
		   for (;i < tokens.length; i++){
		     catName.add(tokens[i]);
			 //catVal.add(j);
			 //System.out.println("Cat Name:"+catName.get(j));//+" Cat Val"+ catVal.get(j)); 
		     //System.out.println("Cat Name:"+tokens[i]+" Cat Val"+ j);
			 j++; //increase value j
		   }
		   classificationProblem = true;		   
		}
		else{ //Token is not a class 
		  AtrCls = new AttrClass();
		  attrName = tokens[i];
		  AtrCls.setAttrName(attrName);
		  //System.out.println("Attr Name:"+AtrCls.getAttrName());
		  //System.out.println("Attr Name:"+attrName);
		  i++;//
		  String attrType = tokens[i];
		  AtrCls.setAttrType(attrType);
		  //System.out.println("Attr Name:"+AtrCls.getAttrType());
		  //System.out.println("Attr Type:"+attrType);
		  i++;
		  double range[]=new double[2]; 
		  int j = 0;
		  for (; i < tokens.length; i++){ 
		    if(isNumeric(tokens[i])){
			  double attrVal = (double)Double.parseDouble(tokens[i]); 
              range[j] =  attrVal;				  
		      //System.out.println("Token Val:"+(range[j]));//attrVal));
			  j++;
		    }    
	      }
		  AtrCls.setAttrRange(range);
		  //System.out.println("Token Val:"+AtrCls.getAttrRange()[0]);//attrVal));
		  //System.out.println("Token Val:"+AtrCls.getAttrRange()[1]);//attrVal));
		  AttrVector.add(AtrCls);
	   }//end else  
     }
     else{throw new Error("Error: Bad Arff file");}
    //pr.println(Attr); //optional
  }//end callAttribute

  public void  callInput(String Attr,PrintWriter pr) {
	 String delims = "[\\[\\]\\{\\}\\s*,]+";//
     String[] tokens = Attr.split(delims);
	 //System.out.println(tokens.length);
	 int i = 0;
	 String attrInput = tokens[i]; 
	 i++; //counted a token
	 if(attrInput.equals("@INPUTS") || attrInput.equals("@Inputs") || attrInput.equals("@inputs")){  
		  //System.out.println("Attr Name:"+tokens[i]);
		   int j = 0;
		   for (;i < tokens.length; i++){
		      inputName.add(tokens[i]);
			 //catVal.add(j);
			 //System.out.println("Input Name:"+inputName.get(j));//+" Cat Val"+ catVal.get(j)); 
		     //System.out.println("Cat Name:"+tokens[i]+" Cat Val"+ j);
			 j++; //increase value j
		   }  
     } 
	 else{throw new Error("Error: Bad Arff file"); }
    //pr.println(Attr); //optional
  }//end callInput
  
  public void  callOutput(String Attr,PrintWriter pr){
	 String delims = "[\\[\\]\\{\\}\\s*,]+";//
     String[] tokens = Attr.split(delims);
	 //System.out.println(tokens.length);
	 int i = 0;
	 String attrOutput = tokens[i]; 
	 i++; //counted a token
	 if(attrOutput.equals("@OUTPUTS") || attrOutput.equals("@Outputs") || attrOutput.equals("@outputs")){  
		  //System.out.println("Attr Name:"+tokens[i]);
		   int j = 0;
		   for (;i < tokens.length; i++){
		      outputName.add(tokens[i]);
			 //catVal.add(j);
			  //System.out.println("Output Name:"+outputName.get(j));//+" Cat Val"+ catVal.get(j)); 
		     //System.out.println("Cat Name:"+tokens[i]+" Cat Val"+ j);
			 j++; //increase value j
		   }  
     } 
	 else{throw new Error("Error: Bad Arff file"); }
    //pr.println(Attr); //optional
  }//end callOutput
  
  public void callDataColl(String DATA,PrintWriter pr){  
	 String delims = "[\\[\\]\\{\\}\\s*,]+";//
     String[] tokens = DATA.split(delims);
     int attrLen = AttrVector.size();//for classification problem 
	 int attrLenIn = inputName.size();//for regression problem
	 int attrLenOt = outputName.size();//for regression problem

	 //Find attribute values
	 tp = new TrainPat();
	 int i;
	 dataX = new double[attrLenIn];
	 dataY = new double[attrLenOt];
	 if(classificationProblem){//collect data for classification
	 	 for(i = 0;(i < attrLen && i<tokens.length);i++){
			if(isNumeric(tokens[i])){
				double attrVal = (double)Double.parseDouble(tokens[i]); 
				dataX[i] =   attrVal;  //System.out.println("Token Val:"+(range[j]));//attrVal));
				pr.print(dataX[i]+" "); 
			}    
		}
		tp.setX(dataX);//setting X
	    if(i<tokens.length){//Find attribute category
			String attrCat = tokens[i];
			int attrCatVal = findCat(attrCat);
			tp.setCls(attrCatVal);//setting Y
			pr.print(attrCatVal+" "); 
			//System.out.println(attrCat);
		}
	 }//end loading class data
	 else{//It is regression problem
	 	 for(i = 0;i<tokens.length;i++){
			if(isNumeric(tokens[i])){
				double attrVal = (double)Double.parseDouble(tokens[i]);
				//System.out.println("Token Val:"+attrVal);			
				if(i < attrLenIn){
					dataX[i] =   attrVal;				  
					pr.print(dataX[i]+" ");
					//System.out.print(dataX[i]+" ");
				}
				else{
					dataY[(i-attrLenIn)] =   attrVal;
					pr.print(dataY[i-attrLenIn]+" ");
					//System.out.print(dataY[i-attrLenIn]+" ");
				}		   
			}		 
		}//end for
		tp.setX(dataX);
	    tp.setY(dataY);
	 }//end loading regression data	
	 Data.add(tp);// Adding the training patter
     pr.println();	 
  }//end callData
  
  public int findCat(String attrCat){
     int i;
	 int catVal = 0; 
	 boolean foundCat = false;
	 int catSize = catName.size();
	 for(i = 0; i < catSize; i++){
	   //System.out.println(attrCat+" = "+catName.get(i));
	   if(attrCat.equals(catName.get(i))){
	     catVal = i;
		 foundCat = true;
		 break;
	   }
	 }	 
	 if(!foundCat){
	   throw new Error("Bad arrf file");
	 }
	 return catVal;  
  }//end findCatagory
  
  public void loadData(){
     int noPatterns = Data.size();
	 int noInputFeatures = inputName.size();//AttrVector.size();
	 int noOutputFeatures = outputName.size();//AttrVector.size();
	 int noFeatures = AttrVector.size();
	 
     x = new double[noPatterns][noInputFeatures];
	 y = new double[noPatterns][noOutputFeatures];
	 t = new double[noPatterns];
	 
	 double dataLow;
	 double dataHigh;
	 double normalizedLow = 0;
     double normalizedHigh = 1;
	 double valX;
	 double valY;
	 
	 int i,j,k = 0;
	 if(classificationProblem){//load data for classification
	 	 for(i=0; i < noPatterns; i++){
	     for(j=0; j <  noFeatures; j++){ 
		   dataLow = ((AttrClass)AttrVector.get(j)).getAttrRange()[0];
		   dataHigh = ((AttrClass)AttrVector.get(j)).getAttrRange()[1];
		   valX = ((TrainPat)Data.get(i)).getX()[j];
		   x[i][j] = normalize(valX, dataLow, dataHigh, normalizedLow, normalizedHigh);
		   //System.out.print(x[i][j]+" ");
		 }	
         valY =  ((TrainPat)Data.get(i)).getCls();	
		 t[i] = valY;
		}//end for
		System.out.println("Classification loaded Successfully!");
		System.out.println("Problem has "+noPatterns+ " examples "+ noFeatures+ " Attributes and "+catName.size()+" classes");
	 }//end loading classification data
	 else{//load data for regression
		for(i=0; i < noPatterns; i++){
		   for(j=0; j <  noFeatures; j++){ 
			dataLow = ((AttrClass)AttrVector.get(j)).getAttrRange()[0];
			dataHigh = ((AttrClass)AttrVector.get(j)).getAttrRange()[1];
			if(j < noInputFeatures){
				valX = ((TrainPat)Data.get(i)).getX()[j];
				x[i][j] = normalize(valX, dataLow, dataHigh, normalizedLow, normalizedHigh);
			}
			else{
				valY = ((TrainPat)Data.get(i)).getY()[j-noInputFeatures];
				y[i][(j-noInputFeatures)] = normalize(valY, dataLow, dataHigh, normalizedLow, normalizedHigh);
			}
			}
		}//end for
		System.out.println("Regression loaded Successfully!");
		System.out.println("Problem has "+noPatterns+ " examples "+ noInputFeatures+ " input and "+noOutputFeatures+" Output features"); 
     }//end loading regression data
  }//end loadData
  
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  
  public Pattern[] runProblem(){
	 int totalPat = Data.size();
	 int catSize = catName.size();
	 
	 Pattern[] pat = new Pattern[totalPat];
	 int In = 0,Hi = 0, Ot = 0;	 
	 int i,j;//loop variables
	 double InP[];//input pattern
	 double TrP[];//target pattern
	 if(classificationProblem){//run problem for classification
	 	 In = AttrVector.size();
		 Hi = 10;//catName.size();
		 Ot = catName.size();
		 for(i = 0; i < totalPat; i++){
			InP =new double[In];
			TrP =new double[Ot];
			for(j=0; j <  In; j++){ 
				InP[j] = x[i][j]; //System.out.printf("%1.2f ",InP[j]);
			}	
			for(j=0; j <  Ot; j++){ 
		 		 if(j == t[i]) 
					TrP[j] = 1;
				 else 
					TrP[j] = 0;		 
			}
            pat[i] =  new Pattern(InP,TrP);			
		    //if(i < trainPat){patTrain[i] = new Pattern(InP,TrP);}   
		    //else{patTest[i - trainPat] = new Pattern(InP,TrP);}   
		}//end totalPat 
	 }//end classification
	 else{//run problem for regression
		In =  inputName.size();
		Hi = 10;
		Ot = outputName.size();
		for(i = 0; i < totalPat; i++){
		   InP=new double[In];
		   TrP=new double[Ot];
		   for(j=0; j <  In; j++){ 
				InP[j] = x[i][j]; //System.out.printf("%1.2f ",InP[j]);
		   }	
		   for(j=0; j <  Ot; j++) { 
				TrP[j] = y[i][j]; //System.out.print(TrP[j] +" ");
		   }
           pat[i] =  new Pattern(InP,TrP);		   
		   //if(i < trainPat){patTrain[i] = new Pattern(InP,TrP);}   
		   //else{patTest[i - trainPat] = new Pattern(InP,TrP);}   
	    }//end totalPat 
	 }//end regression	
	 return pat;
  }//end run program
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  public PatternSVM[] runProblemSVM(){
	 int totalPat = Data.size();	 
	 PatternSVM[] pat = new PatternSVM[totalPat];
	 int In = 0;	 
	 int i,j;//loop variables
	 double InP[];//input pattern
	 int TrP = 0;//target pattern
	 if(classificationProblem){//run problem for classification
	 	 In = AttrVector.size();
		 for(i = 0; i < totalPat; i++){
			InP =new double[In];
			for(j=0; j <  In; j++){ 
				InP[j] = x[i][j]; //System.out.printf("%1.2f ",InP[j]);
			}//end features	
			if(t[i] == 0) 
				TrP = -1;
			else 
				TrP = 1;		 
            pat[i] =  new PatternSVM(InP,TrP);			
		}//end totalPat 
	 }//end classification
     else{
	     System.out.println("Sorry! It is not a binary classification problem");
     }	 
	 return pat;
  }//end run program

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
public int[] networkInfo(){
  int netInfo[] = new int[5];
  int In = 0,Hi = 0, Ot = 0, catSize = catName.size(), isClassification = 0;
  if(classificationProblem){
  	 	 In = AttrVector.size();
		 Hi = 10;//catName.size();
		 Ot = catName.size();
		 isClassification = 1;
  }
  else{
		In =  inputName.size();
		Hi = 10;
		Ot = outputName.size();  
  }  
  netInfo[0] = In;
  netInfo[1] = Hi;
  netInfo[2] = Ot;
  netInfo[3] = catSize;
  netInfo[4] = isClassification;
  
  return netInfo;
}//end network info

  //public static void main(String args[]){
   public  void loadDataRegressionClassification(){
    System.out.print("\n --------->Welcome to the System for Neural Network<-----------\n");
	//LoadDataRegClass srp = new LoadDataRegClass();
	boolean  arff = false;
    try{   
	   InputStreamReader in = new InputStreamReader(System.in);
       BufferedReader br = new BufferedReader(in);
	   
	   System.out.print("\nEnter a Problem/Training (eg. iris.dat/iris.arff :");
       String FILE_Prob = br.readLine();
	   
	   FileReader  fin = new FileReader(FILE_Prob);
       BufferedReader brProb = new BufferedReader(fin);
	   
	   FileWriter fwrite = new FileWriter("test.txt");
       PrintWriter pr = new PrintWriter(fwrite);
	   
	   int example = 0;//Integer.parseInt(br.readLine());
       Scanner s = null;
       String Data;     
	   
       while((Data = brProb.readLine()) != null){
	     if(Data.isEmpty()){
		  continue;
		 }
		 else{
			s = new Scanner(Data);//.useDelimiter("\\s*,\\s*");
			//System.out.println(s.hasNext("Iris"));
				
			if(s.hasNext("%")){
			//Do nothing
				//System.out.println("%");
				continue;			
			}
			if(s.hasNext("@RELATION") || s.hasNext("@relation") || s.hasNext("@Relation") ){
			//Operate on Relation 
				//srp.callRelation(Data,pr);
				callRelation(Data,pr);
				//System.out.println("@RELATION");
				arff = true;
				continue;	
			}
			if(s.hasNext("@ATTRIBUTE") || s.hasNext("@attribute") || s.hasNext("@Attribute")){
			//Operate on attributes 
				//System.out.println("@ATTRIBUTE");
				//srp.callAttribute(Data,pr);
				callAttribute(Data,pr);
				continue;		
			}
			if(s.hasNext("@INPUTS") || s.hasNext("@inputs") || s.hasNext("@Inputs")){
				//Operate on attributes 
				//System.out.println("@INPUTS");
				//srp.callInput(Data,pr);
				callInput(Data,pr);
				continue;		
			}
			if(s.hasNext("@OUTPUTS") || s.hasNext("@outputs") || s.hasNext("@Outputs")){
				//Operate on attributes 
				//System.out.println("@OUTPUTS");
				//srp.callOutput(Data,pr);
				callOutput(Data,pr);
				continue;		
			}
			if(s.hasNext("@DATA") || s.hasNext("@data") || s.hasNext("@Data")){
				//Operate on attributes 
				System.out.println("@Data loading..............");
				continue;		
			}
			else if(s.hasNext()){
			    if(arff){ 
				  //srp.callDataColl(Data,pr);
				  callDataColl(Data,pr);
				}
				else{
				   s = s.useDelimiter("\\s*,\\s*"); 
				   while(s.hasNext()){
					  String valString = s.next();
					  pr.print(valString+" ");
				   }
				   pr.println();
				}  
				example++;
			}
		  }	
		 }
	   pr.close();
	   brProb.close();
	   //srp.loadData();
	   //System.out.println("Problem has "+example+ " examples"); 
	   //srp.runProblem();	   
    }catch(Exception e){
       System.out.println("Error "+e);
       System.exit(0); 
    }//catch
  }//main or loadRegression
}//class LoadDataRegClass
	
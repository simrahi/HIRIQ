//SIMRAN RAHI 260604408
import java.util.*;

public class HiRiQ {

	public static void main(String[] args){
		  //position:       0       1      2     3      4     5      6     7      8      9     10    11     12      13    14     15    16     17   18    19    20      21     22    23     24    25     26     27     28     29     30     31     32 
		boolean[] TAsTest={true, true, false, true, false, true, false, false, false, true, false, false, false, false, true, true, false, true, true, false, false, false, true, true, false, false, false, false, false, true, false, false, false};  	 
		//TA: put your test configuration above ^^
		
		//This will print the answer vv
		System.out.println(madeForTA(TAsTest));
	}
	
	//this method is for use by the TA (so they can input a boolean[] and get a String back)
	public static String madeForTA(boolean[] test){
		HiRiQ comp=new HiRiQ();		//creates the HiRiQ object (needed for creating our Tree)
		String answer="";		//this creates the String I will return containing the set of moves that is the answer
		/*my method works with an integer array (I found it has more function in a problem like this) so I have to create the integer 
		 array and then use a method to change the boolean array (true=white, false=black) into an integer one (1=white, 0=black) */
		int[] inputConfig=new int[33];		 
		inputConfig=booleanToIntArray(test);
		long startTime = System.nanoTime();			//This allows us to see how long the calculation took
		answer=solveHiRiQ(inputConfig, comp);
		long endTime = System.nanoTime();		    //This allows us to see how long the calculation took
		//System.out.println("Calculation took "+((endTime - startTime)/1000000000)+" seconds.");		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG THE ALGORITHM TOOK
		return answer;
	}
	
	public static String solveHiRiQ(int[] origConfig, HiRiQ comp){
		//long startTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		int numberOfWhites=countWhites(origConfig);		//this counts how many white pieces we start off with (for the purpose of only checking for answers in the levels that are appropriate (odd/even, n-1 rules)
			//System.out.println(numberOfWhites+"  Whites in original Config") ;		//YOU CAN BRING THIS BACK IF YOU'RE INTERESTED IN SEEING HOW MANY WHITE PIECES WE START OFF WITH
		int level;		//helps us keep track of what level of children nodes we're at
		
		/*both of these booleans are created so we can avoid wasting time by checking for solutions at inappropriate levels*/
		boolean checkifSolved=false;	
		boolean oddEvenAttempt=false;
		
		//Here, I create all the ArrayLists I'll need for each level of children nodes
		ArrayList<Node> nodes=new ArrayList<Node>();
		ArrayList<Node> nodesL2=new ArrayList<Node>();
		ArrayList<Node> nodesL3=new ArrayList<Node>();
		ArrayList<Node> nodesL4=new ArrayList<Node>();
		ArrayList<Node> nodesL5=new ArrayList<Node>();
		ArrayList<Node> nodesL6=new ArrayList<Node>();
		ArrayList<Node> nodesL7=new ArrayList<Node>();
		ArrayList<Node> nodesL8=new ArrayList<Node>();
		ArrayList<Node> nodesL9=new ArrayList<Node>();
		ArrayList<Node> nodesL10=new ArrayList<Node>();
		ArrayList<Node> nodesL11=new ArrayList<Node>();
		ArrayList<Node> tempList= new ArrayList<Node>();
		
		//First and foremost, I will check if the input puzzle is already solved using a helper method (isSolved) that I wrote
		if(isSolved(origConfig)==true){
			return "";
		}
		
		//Then, I create a tree for all our configurations to branch off of and a HashSet for all of our configurations to exist in
		Tree tree=comp.new Tree();
		HashSet <String> allConfigs = new HashSet <String>();
		
		//Now, I put in the original configuration as the head/root of the tree and as the first element in my HashSet
		allConfigs.add(Arrays.toString(origConfig));
		tree.addNode(Arrays.toString(origConfig), null, null);

		/*Each level essentially follows the same level (may be done recursively but I had more control this way); I start building 
		 child nodes at each level until I find a solution and return the string of steps... this code will solve a puzzle anywhere from 1 to 11
		 steps away from the solved state before returning "Unsolvable in 11 steps"*/
		
		//START LEVEL 1
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		//At each level, I update the integer variable "level" to be the correct value
		level=1;
		/*If we start off with more than 2 white pieces, we cannot solve the puzzle in only one step (first level), so this 
		check stops us from wasting time checking for the solution in inappropriate levels*/
		 if(numberOfWhites-level<=1){
			 checkifSolved=true;
		 }
		 /*this step builds the children nodes by being passed the parent configuration, the tree we're adding to, the two booleans we're using to minimize running time, 
		 and the HashSet we want all our children to be added to*/
		 nodes=buildNodes(Arrays.toString(origConfig), tree,"", checkifSolved, true, allConfigs);
		 if(nodes.size()==1 && isSolved(stringToint(nodes.get(0).getConfiguration()))){
			 return nodes.get(0).getPath();
		 }
		 //long endTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //System.out.println("Took "+(endTime - startTime) + " nanoseconds to finish level 1 and size of level 1 is "+nodes.size());		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD 
		 //END LEVEL 1
		 
		 //START LEVEL 2
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		 level=2;
		 if(numberOfWhites-level<=1){
			 checkifSolved=true;
		 }
		 /*If the number of white spaces-1 is even, we will find the configuration at an even level; if the number of white spaces-1
		  is odd, we will solve at an odd level... this if statement just saves us time by not checking for solutions in inappropriate levels*/
		 if(((numberOfWhites-1)%2==0)==(level%2==0)){
			 oddEvenAttempt=true;
		 }
		 //This step differs from the level 1 step because now we have more than one node to loop through to build their children
		 for(int i=0; i<nodes.size();i++){
			 tempList=buildNodes(nodes.get(i).getConfiguration(), tree, nodes.get(i).getPath(), checkifSolved, oddEvenAttempt, allConfigs);
			 /*NOTE: in my buildNodes method, I am checking almost regularly if a solution has just been made... if it has, then the method stops building more children,
			 empties the ArrayList it's been populating, and puts the single solution in the ArrayList (so it contains only one element)*/
			 //Now, an if statement checks if the tempList has a size of one AND if that element is the solution (just to be sure), and if it is, we return the answer right away... MAJOR TIME SAVER
			 if(tempList.size()==1 && isSolved(stringToint(tempList.get(0).getConfiguration()))){
				 return tempList.get(0).getPath();
			 }
			 nodesL2.addAll(tempList);		   
			 tempList.removeAll(tempList);		//This step clears the tempList so the next node's children can be added (without introducing duplicates at every addition)
		 }
		 nodes.removeAll(nodes);		//This removes all the data in the ArrayList nodes... MAJOR MEMORY/SPACE SAVER
		 //endTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //System.out.println("Took "+(endTime - startTime) + " nanoseconds to finish level 2 and size of level 2 is "+nodesL2.size());
		 //END LEVEL 2
		 
		 //START LEVEL 3
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		 level=3;
		 if(numberOfWhites-level<=1){
			 checkifSolved=true;
		 }
		 if(((numberOfWhites-1)%2==0)==(level%2==0)){
			 oddEvenAttempt=true;
		 }
		 for(int i=0; i<nodesL2.size();i++){
			 tempList=buildNodes(nodesL2.get(i).getConfiguration(), tree, nodesL2.get(i).getPath(), checkifSolved, oddEvenAttempt, allConfigs);
			 if(tempList.size()==1 && isSolved(stringToint(tempList.get(0).getConfiguration()))){
				 return tempList.get(0).getPath();
			 }
			 nodesL3.addAll(tempList);		   
			 tempList.removeAll(tempList);
		 }
		 nodesL2.removeAll(nodesL2);
		 //endTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //System.out.println("Took "+(endTime - startTime)  + " nanoseconds to finish level 3 and size of level 3 is "+nodesL3.size());		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //END LEVEL 3
		 
		 //START LEVEL 4
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		 level=4;
		 if(numberOfWhites-level<=1){
			 checkifSolved=true;
		 }
		 if(((numberOfWhites-1)%2==0)==(level%2==0)){
			 oddEvenAttempt=true;
		 }
		 for(int i=0; i<nodesL3.size();i++){
			 tempList=buildNodes(nodesL3.get(i).getConfiguration(), tree, nodesL3.get(i).getPath(), checkifSolved, oddEvenAttempt, allConfigs);
			 if(tempList.size()==1 && isSolved(stringToint(tempList.get(0).getConfiguration()))){
				 return tempList.get(0).getPath();
			 }
			 nodesL4.addAll(tempList);		   
			 tempList.removeAll(tempList);
		 }
		 nodesL3.removeAll(nodesL3);
		 //endTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //System.out.println("Took "+(endTime - startTime)  + " nanoseconds to finish level 4 and size of level 4 is "+nodesL4.size());		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //END LEVEL 4
		 
		 //START LEVEL 5
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		 level=5;
		 if(numberOfWhites-level<=1){
			 checkifSolved=true;
		 }
		 if(((numberOfWhites-1)%2==0)==(level%2==0)){
			 oddEvenAttempt=true;
		 }
		 for(int i=0; i<nodesL4.size();i++){
			 tempList=buildNodes(nodesL4.get(i).getConfiguration(), tree, nodesL4.get(i).getPath(), checkifSolved, oddEvenAttempt, allConfigs);
			 if(tempList.size()==1 && isSolved(stringToint(tempList.get(0).getConfiguration()))){
				 return tempList.get(0).getPath();
			 }
			 nodesL5.addAll(tempList);		   
			 tempList.removeAll(tempList);
		 }
		 nodesL4.removeAll(nodesL4);
		 //endTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //System.out.println("Took "+(endTime - startTime)/1000000  + " milliseconds to finish level 5 and size of level 5 is "+nodesL5.size());		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //END LEVEL 5
		 
		 //START LEVEL 6
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		 level=6;
		 if(numberOfWhites-level<=1){
			 checkifSolved=true;
		 }
		 if(((numberOfWhites-1)%2==0)==(level%2==0)){
			 oddEvenAttempt=true;
		 }
		 for(int i=0; i<nodesL5.size();i++){
			 tempList=buildNodes(nodesL5.get(i).getConfiguration(), tree, nodesL5.get(i).getPath(), checkifSolved, oddEvenAttempt, allConfigs);
			 if(tempList.size()==1 && isSolved(stringToint(tempList.get(0).getConfiguration()))){
				 return tempList.get(0).getPath();
			 }
			 nodesL6.addAll(tempList);		   
			 tempList.removeAll(tempList);
		 }
		 nodesL5.removeAll(nodesL5);
		 //endTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //System.out.println("Took "+(endTime - startTime)/1000000000 + " seconds to finish level 6 and size of level 6 is "+nodesL6.size());		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //END LEVEL 6
		 
		 //START LEVEL 7
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		 level=7;
		 if(numberOfWhites-level<=1){
			 checkifSolved=true;
		 }
		 if(((numberOfWhites-1)%2==0)==(level%2==0)){
			 oddEvenAttempt=true;
		 }
		 for(int i=0; i<nodesL6.size();i++){
			 tempList=buildNodes(nodesL6.get(i).getConfiguration(), tree, nodesL6.get(i).getPath(), checkifSolved, oddEvenAttempt, allConfigs);
			 if(tempList.size()==1 && isSolved(stringToint(tempList.get(0).getConfiguration()))){
				 return tempList.get(0).getPath();
			 }
			 nodesL7.addAll(tempList);		   
			 tempList.removeAll(tempList);
		 }
		 nodesL6.removeAll(nodesL6);
		 //endTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //System.out.println("Took "+(endTime - startTime)/1000000000 + " seconds to finish level 7 and size of level 7 is "+nodesL7.size());		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //END LEVEL 7
		 
		 //START LEVEL 8
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		 level=8;
		 if(numberOfWhites-level<=1){
			 checkifSolved=true;
		 }
		 if(((numberOfWhites-1)%2==0)==(level%2==0)){
			 oddEvenAttempt=true;
		 }
		 for(int i=0; i<nodesL7.size();i++){
			 /*NOTE: at this level, I introduce a "filter"; the logic isn't flawless, but the idea is that if, at level 8, we have a puzzle with 7 or more white 
			  white pieces, It is unlikely (if not impossible) for it to lead to the solved configuration in the next few steps. Therefore, I this if statement stops
			  the addition of configurations with too many white pieces... THIS IS A MAJOR TIME AND MEMORY/SPACE SAVER since we don't have to loop through as many children
			  to build further children/check if any of them are the solution*/
			 if(countWhites(stringToint(nodesL7.get(i).getConfiguration()))<6){  
				 tempList=buildNodes(nodesL7.get(i).getConfiguration(), tree, nodesL7.get(i).getPath(), checkifSolved, oddEvenAttempt, allConfigs);
				 	if(tempList.size()==1 && isSolved(stringToint(tempList.get(0).getConfiguration()))){
				 		return tempList.get(0).getPath();
				 	}
			 nodesL8.addAll(tempList);		   
			 tempList.removeAll(tempList);
		   }
		 }
		 nodesL7.removeAll(nodesL7);
		 //endTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //System.out.println("Took "+(endTime - startTime)/1000000000 + " seconds to finish level 8 and size of level 8 is "+nodesL8.size());		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //END LEVEL 8
		 
		 //START LEVEL 9
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		 level=9;
		 if(numberOfWhites-level<=1){
			 checkifSolved=true;
		 }
		 if(((numberOfWhites-1)%2==0)==(level%2==0)){
			 oddEvenAttempt=true;
		 }
		 for(int i=0; i<nodesL8.size();i++){
			 /*NOTE: this idea is the same as the "filter" in the previous step but slightly more restrictive... the odds of the filter scrapping a meaningful
			  configuration is very very low (i.e. this works almost every time)*/
		 	   if(countWhites(stringToint(nodesL8.get(i).getConfiguration()))<5) {  
		 		   tempList=buildNodes(nodesL8.get(i).getConfiguration(), tree, nodesL8.get(i).getPath(), checkifSolved, oddEvenAttempt, allConfigs);
		 		   	if(tempList.size()==1 && isSolved(stringToint(tempList.get(0).getConfiguration()))){
		 		   		return tempList.get(0).getPath();
		 		   	} 
			 nodesL9.addAll(tempList);		   
			 tempList.removeAll(tempList);
		  }
	}
		 nodesL8.removeAll(nodesL8);
		 //endTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //System.out.println("Took "+(endTime - startTime)/1000000000 + " seconds to finish level 9 and size of level 9 is "+nodesL9.size());		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //END LEVEL 9
		 
		 //START LEVEL 10
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		 level=10;
		 if(numberOfWhites-level<=1){
			 checkifSolved=true;
		 }
		 if(((numberOfWhites-1)%2==0)==(level%2==0)){
			 oddEvenAttempt=true;
		 }
		 for(int i=0; i<nodesL9.size();i++){
			 /*NOTE: we got rid of the filter here (since we've cut down on our ArrayList size enough to run fast*/	 
			 tempList=buildNodes(nodesL9.get(i).getConfiguration(), tree, nodesL9.get(i).getPath(), checkifSolved, oddEvenAttempt, allConfigs);
			 if(tempList.size()==1 && isSolved(stringToint(tempList.get(0).getConfiguration()))){
				 return tempList.get(0).getPath();
			 }
			 nodesL10.addAll(tempList);		  
			 tempList.removeAll(tempList);
		 }
		 nodesL9.removeAll(nodesL9);
		 //endTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //System.out.println("Took "+(endTime - startTime)/1000000000 + " seconds to finish level 10 and size of level 10 is "+nodesL10.size());		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //END LEVEL 10
		 
		 //START LEVEL 11
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		 level=11;
		 if(numberOfWhites-level<=1){
			 checkifSolved=true;
		 }
		 if(((numberOfWhites-1)%2==0)==(level%2==0)){
			 oddEvenAttempt=true;
		 }
		 for(int i=0; i<nodesL10.size();i++){
			 /*NOTE: I can very confidently place this "filter" (<3) since I know this code doesn't go farther than 11 steps, so any configuration with more
			  than 2 white pieces can NOT be solved in this next step*/
			 if(countWhites(stringToint(nodesL10.get(i).getConfiguration()))<3) {  	 
				 tempList=buildNodes(nodesL10.get(i).getConfiguration(), tree, nodesL10.get(i).getPath(), checkifSolved, oddEvenAttempt, allConfigs);
				 	if(tempList.size()==1 && isSolved(stringToint(tempList.get(0).getConfiguration()))){
				 		return tempList.get(0).getPath();
				 	}
			 nodesL11.addAll(tempList);		   
			 tempList.removeAll(tempList);
			  }
		 }
		 nodesL10.removeAll(nodesL10);
		 //endTime = System.nanoTime();		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //System.out.println("Took "+(endTime - startTime)/1000000000 + " seconds to finish level 11 and size of level 11 is "+nodesL11.size());		//YOU CAN BRING THIS BACK IF YOU WANT TO SEE HOW LONG EACH LEVEL IS TAKING TO BUILD
		 //END LEVEL 11
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		//If after going through all the possible configurations at a depth of 11 there is no solution, I just return the statement below
		 return "Unsolvable in 11 steps";
	}
	
	//HERE ARE ALL MY HELPER METHODS
	public static boolean isSolved(int[] configuration){
	//The method loops through the array and adds the integers; it uses the sum of the elements and the position of the 1 to see if the configuration is solved
			boolean isSolved=false;
			int sum=0;
			for (int i=0;i<32; i++){
				 sum=sum+configuration[i]; 
			}
			
			if (sum==1 && configuration[16]==1){
				isSolved=true;
			}
			return isSolved;
		}
	
	public static int countWhites(int[] configuration){
	//This method loops through the array and counts how many white pieces are there
		int sum=0;
		for (int i=0;i<33; i++){
			 sum=sum+configuration[i]; 
		}
		return sum;
	}
	
	public static ArrayList<Node> buildNodes(String parentConfig, Tree tree, String path, boolean checkifSolved, boolean oddEvenAttempt, HashSet<String> allConfigs){
		//This method not only does the transformations, but also returns an ArrayList of all of the possibilities 
			ArrayList<Node> nodes=new ArrayList<Node>();		//this line creates the array I will return at the end of this method
			int[] X = {0,0,0};		//an array used to run through all possible 38 triplets on the board
			int[] Y = {0,0,0};		//an array used to check the substitution rule 
			int sumbw;		//counter that helps us determine if the transformation is B->W or W->B
			char trnsfrm=' ';		//this lets us write out the appropriate transformation(B or W)
			boolean solved = false;		//a boolean that keeps track if we've found a solution
			
			//I then copy the inputted parentConfig into another array so we can perform the transformations without disturbing the original array (reference types have this issue)
			int[] copiedParent= new int[33];	
			for (int j=0; j<33;j++){
				copiedParent[j]=stringToint(parentConfig)[j] ;
			}
			
			//These are the hard coded 18 Horizontal Triplets
			int[][] horizontalTriplets= {{0,1,2},{3,4,5},{6,7,8},{7,8,9},{8,9,10},
					{9,10,11},{10,11,12},{13,14,15},{14,15,16},{15,16,17},
					{16,17,18},{17,18,19},{20,21,22},{21,22,23},{22,23,24},
					{23,24,25},{24,25,26},{27,28,29},{30,31,32}};

			//These are the hard coded 18 Vertical Triplets
 			int[][] verticalTriplets= {{12,19,26},{11,18,25},{2,5,10},{5,10,17},
					{10,17,24},{17,24,29},{24,29,32},{1,4,9},{4,9,16},{9,16,23},
					{16,23,28},{23,28,31},{0,3,8},{3,8,15},{8,15,22},{15,22,27},
					{22,27,30},{7,14,21},{6,13,20}};
 			
 			//VERTICAL
 //+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
			//this loop will run through all possible vertical transformations and see if the triplet in the parentConfig is eligible for a transformation
  			for (int i=0; i<19; i++){
  				/*This loop puts the first set of possible vertical transformations in array X, then checks those positions in the parentConfig
  				 and puts those values in array Y*/
  				/*NOTE: stringToInt is a helper method you will see below that takes a string (which really looks like an array) and turns it
  				 into an integer array... this happens because the Node object I was dealing with takes a String as input*/
	        	X = verticalTriplets[i];
	        	Y[0]=stringToint(parentConfig)[X[0]];
	        	Y[1]=stringToint(parentConfig)[X[1]];
	        	Y[2]=stringToint(parentConfig)[X[2]];
	        	
	        	//now the Y array is carrying the triplet values of the parentConfig and we will check if a substitution can occur
	        	if(CheckBWSubstition(Y)==true){ 
	        		//We now check if which substitution is being applies (B or W) 
	        		     sumbw=0;
	        		for (int j=0;j<3; j++){
	    				 sumbw=sumbw+Y[j] ; 
	    			}		
	        		if (sumbw<=1)  {trnsfrm='B';}	
	        		if (sumbw>=2)  {trnsfrm='W';}
	        	 
	             Y=DoBWSubstition(Y);		//The actual substitution was done on the contents of Y (by use of a helper method) and these values are now being put back in Y
	        	 copiedParent[X[0]]=Y[0];		//Then, the contents of transformed Y are being put back in the copiedParent array (which is just a copy of the parentConfig)
	        	 copiedParent[X[1]]=Y[1];
	        	 copiedParent[X[2]]=Y[2];
	         
	        	//we add this transformation/child as a node to our tree AND AT THE SAME TIME we add the node to the ArrayList
		        //we also check if the transformation/child already exists in the HashSet... if it is, we don't add it again to the HashSet, tree, or ArrayList
	        		if (allConfigs.contains(Arrays.toString(copiedParent))==false){
		        		allConfigs.add(Arrays.toString(copiedParent));
		     		    nodes.add(tree.addNode(Arrays.toString(copiedParent),parentConfig," "+path+X[0]+trnsfrm+X[2]+" "));
		     		   
		     		    /*NOTE: this series of if statements is primarily for the efficiency of running time... we are told not to bother 
		     		     looking for solutions where we know they cannot be... the else for each condition is the same though: re-copy the parentConfig
		     		     back into copiedParent so we can start over with a NEW transformation*/
		     		    if(checkifSolved && oddEvenAttempt){
		     		    	if(isSolved(copiedParent)){
		     		    		//if we encounter a configuration that is solved, we want to return it RIGHT AWAY (to save time and space)
		     		    		solved=true; 
		     		    		nodes.removeAll(nodes);		//this removes all the nodes that have been put in the ArrayList thus far (since they are not the solution)
		     		    		nodes.add(tree.addNode(Arrays.toString(copiedParent), parentConfig," "+path+X[0]+trnsfrm+X[2]+" "));		//this adds the SINGLE solution to the ArrayList 
		     		    		return nodes;		//now, we're returning an ArrayList with only one element (the solution)
		     		    	}
		     		    	else{
		     		    		for(int j=0; j<33;j++){
		                		copiedParent[j]=stringToint(parentConfig)[j];
		     		    		}  
		     		    	}
		     		   }
		     		   else{
		     			  for(int j=0; j<33;j++){
		                		copiedParent[j]=stringToint(parentConfig)[j];
			     		  }  
		     		   }
		     		}
		     		else{
		     		   for(int j=0; j<33;j++){
	                   copiedParent[j]=stringToint(parentConfig)[j];
		     		   }  
		     		}
		        }
	  		}
  			
  			//HORIZONTAL (same reasoning as vertical)
  //+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
  			/*We have a if statement check before entering the horizontal possibilities (to save time) since there is no need to go through 
  			these triplets if the puzzle is already solved*/
  			if (solved==false) {
  			for (int i=0; i<19; i++){
	        	X = horizontalTriplets[i];
	        	Y[0]=stringToint(parentConfig)[X[0]];
	        	Y[1]=stringToint(parentConfig)[X[1]];
	        	Y[2]=stringToint(parentConfig)[X[2]];
	        	if(CheckBWSubstition(Y)==true){ 
	        		     sumbw=0;
	        		for (int j=0;j<3; j++){
	    				 sumbw=sumbw+Y[j] ; 
	    			}		
	        		if (sumbw<=1)  {trnsfrm='B';}	
	        		if (sumbw>=2)  {trnsfrm='W';}
	        	 
	             Y = DoBWSubstition(Y);
	        	 copiedParent[X[0]]=Y[0];
	        	 copiedParent[X[1]]=Y[1];
	        	 copiedParent[X[2]]=Y[2];
	         
	        	if (allConfigs.contains(Arrays.toString(copiedParent))==false){
	        		allConfigs.add(Arrays.toString(copiedParent));
	     		    nodes.add(tree.addNode(Arrays.toString(copiedParent),parentConfig," "+path+X[0]+trnsfrm+X[2]+" "));
	     		   if(checkifSolved && oddEvenAttempt){
	     		    	if(isSolved(copiedParent)){
	     		    		solved=true; 
	     		    		nodes.removeAll(nodes);
	     		    		nodes.add(tree.addNode(Arrays.toString(copiedParent), parentConfig," "+path+X[0]+trnsfrm+X[2]+" "));
	     		    		return nodes;
	     		    	}
	     		    	else{
	     		    		for(int j=0; j<33;j++){
	                		copiedParent[j]=stringToint(parentConfig)[j];
	     		    		}  
	     		    	}
	     		   }
	     		   else{
	     			  for(int j=0; j<33;j++){
	                		copiedParent[j]=stringToint(parentConfig)[j];
		     		    	}  
	     		   }
	     		    }
	     		    else{
	     		    	for(int j=0; j<33;j++){
                		copiedParent[j]=stringToint(parentConfig)[j];
	     		    	}  
	     		    }
	        }
  			}
  		}
  			return nodes;		//this returns an ArrayList of Nodes of all the possible children one particular configuration can lead to
  	}
	
	public static int[] DoBWSubstition(int[] triplet){
		/*This method actually does the work of the transformation (turns BBW into WWB and vice versa) by checking which of the 4 possible triplets the 
		 input is and changing each of it's values to the transformed ones*/
 			int[] xxx = triplet;
 			if (xxx[0]==0 && xxx[1]==0 &&xxx[2]==1 ){
 				xxx[0]=1;
 				xxx[1]=1;
 				xxx[2]=0;
 			}
 			else if (xxx[0]==1 && xxx[1]==1 &&xxx[2]==0 ){
 	 				xxx[0]=0;
 	 				xxx[1]=0;
 	 				xxx[2]=1;
 			}
 			else if (xxx[0]==1 && xxx[1]==0 &&xxx[2]==0 ){
	 				xxx[0]=0;
	 				xxx[1]=1;
	 				xxx[2]=1;
			}
 			else if (xxx[0]==0 && xxx[1]==1 &&xxx[2]==1 ){
	 				xxx[0]=1;
	 				xxx[1]=0;
	 				xxx[2]=0;
			}
 		return xxx;
 		}

 	public static boolean CheckBWSubstition(int[] triplet) {
 	/*This method checks if the transformation is possible by checking if the triple falls into any of the four categories a "transformable" triplet can be*/	
 		int[] xxx = triplet;
 			boolean subOK = false;
 			 if (xxx[0]==0 && xxx[1]==0 &&xxx[2]==1 ){
 		 		 subOK=true; 			
 		 	 }
 				if (xxx[0]==1 && xxx[1]==1 &&xxx[2]==0 ){
 					subOK=true; 			
 				}
 				if (xxx[0]==1 && xxx[1]==0 &&xxx[2]==0 ){
 					subOK=true; 		
 				}
 				if (xxx[0]==0 && xxx[1]==1 &&xxx[2]==1 ){
 					subOK=true; 			
 				}

 				return subOK;
 		}

 	public static int[] stringToint(String config){
 	/*This method changes a String (which really just looks like an integer array) and turns it into an actual integer array; this exists because
 	 the Node class I am working with takes String inputs*/
 	  	int[] ints = new int[33];		//the integer array I will return
 		int j=0;
 	 	for(int i=1; i<99; i=i+3){
 	 		ints[j]=Integer.parseInt(String.valueOf(config.charAt(i))); 
 	 		j++;
 		}
 		return ints;
 	}

 	public static int[] booleanToIntArray(boolean[] test){
 	/*This method exists because the TAs will be inputting a boolean array to test and all my methods work with integer array; this is because
 	 I found it beneficial to work with 1s and 0s as opposed to true and false (it gave me a chance to determine certain qualities by summing up
 	 the elements of the array)*/
 			int[] inputConfig= new int[33];
 			for(int i=0; i<test.length; i++){
 				if(test[i]==false){
 					inputConfig[i]=0;
 				}
 				if(test[i]==true){
 					inputConfig[i]=1;
 				}
 			}
 			return inputConfig;
 		}

 class Tree {
	 /*NOTE: to start my code, I used some code utilizing the properties of trees from the Internet, I modified it and deleted
	  what I think I did not need... there are still some properties I do not need/use here but I'm too afraid to mess with working code so
	  I will leave it there (please pay no attention to it!)*/
	    
	 	private final static int ROOT = 0;
	    private HashMap<String, Node> nodes;

	    //Constructor
	    public Tree() {
	        this.nodes = new HashMap<String, Node>();
	    }
	    
	    public HashMap<String, Node> getNodes() {
	        return nodes;
	    }

	    public Node addNode(String configuration, String parent, String path) {
	        Node node = new Node(configuration, path);
	        nodes.put(configuration, node);
	        if (parent != null) {
	            nodes.get(parent).addChild(configuration);
	        }
	        return node;
	    }
	    
	    //These two methods are unused (I never display the tree, it's just an abstract idea I wanted to use for logic purposes
	    public void display(String configuration) {
	        this.display(configuration, ROOT);
	    }

	    public void display(String configuration, int depth) {
	        ArrayList<String> children = nodes.get(configuration).getChildren();

	        if (depth == ROOT) {
	            System.out.println(nodes.get(configuration).getConfiguration());
	        } else {
	            String tabs = String.format("%0" + depth + "d", 0).replace("0", "    "); // 4 spaces; this indents so we can see the "tree" formation
	            System.out.println(tabs + nodes.get(configuration).getConfiguration()
						+ " path: " + nodes.get(configuration).getPath());
			}
	        depth++;
	        for (String child : children) {
	            // Recursive call
	            this.display(child, depth);
	        }
	    }
}

 class Node {
	 /*NOTE: to start my code, I used some code utilizing the properties of nodes from the Internet, I modified it and deleted
	  what I think I did not need... there are still some properties I do not need/use here but I'm too afraid to mess with working code so
	  I will leave it there (please pay no attention to it!)*/
	    
	 	private String configuration;
	    private ArrayList<String> children;
	    private String path;

	    //Constructor
	    public Node(String configuration, String path )   {
	        this.configuration = configuration;
	        children = new ArrayList<String>();
	        this.path=path;
	    }

	    //this method will return our String configuration
	    public String getConfiguration() {
	        return configuration;
	    }
	    //this method will return the list of children 
	    public ArrayList<String> getChildren() {
	        return children;
	    }
	    //this method will return the path attribute
	    public String getPath() {
	        return path;
	    }
	    //allows us to add a child to a node
	    public void addChild(String configuration) {
	        children.add(configuration);
	    }
	}
}


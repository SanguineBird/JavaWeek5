/**Course: CIT-130-BIN1
Name: Meghan Moore
Date: 10/4/2014
Programming Assignment 5 - Part 2 - readWrite
Purpose: Read and write from binary or text files.*/

import java.util.Scanner;
import java.io.*;

public class readWrite implements Serializable{
  String fileName, fileType, procedure; //user input
  Scanner keyboard = new Scanner(System.in);
//-------------------------------------------------------------------- 	

  public void inputFile(){
    System.out.print("Enter the file name: ");
    fileName = keyboard.next();
  }
//--------------------------------------------------------------------  

  public void inputBT(){ //prompt for binary or text
    boolean inputTypeComplete = false; //sentinel
    while(! inputTypeComplete){
      System.out.print("\nChoose binary or text file(b/t): ");
      fileType = keyboard.next();
      if (fileType.equalsIgnoreCase("b")){
		fileName = (fileName + ".txt");
		inputTypeComplete = true;
	  }
	  else if (fileType.equalsIgnoreCase("t")){
		fileName = (fileName + ".dat");
        inputTypeComplete = true;
      }
      else{
        System.err.println("\nInput must be \"b\" or \"t\".");
      }
    }
  }
//-------------------------------------------------------------------- 
	
  public String inputRW(){
    boolean inputRWComplete = false; //sentinel
    while(! inputRWComplete){
      System.out.print("\nChoose read or write file(r/w): ");
      procedure = keyboard.next();
      if((procedure.equalsIgnoreCase("r")) || (procedure.equalsIgnoreCase("w"))){
        inputRWComplete = true;
        return procedure;
      }
      else{
        System.err.println("\nInput must be \"r\" or \"w\".");
        inputRWComplete = false;
      }
    }
    return ""; //keeps compiler happy
  }
//--------------------------------------------------------------------  
  
  public void read(){
//FILE TYPE IS TEXT
      if (fileType.equalsIgnoreCase("t")){
		  String string; //output
		  Scanner inputStreamText = null; //keeps compiler happy
        
        try{
          inputStreamText = new Scanner(new FileInputStream(fileName)); //opens stream
        }
        catch(FileNotFoundException e){
          System.err.println("\nError: File " + fileName + " not found.");
          System.err.println("Program terminating.");
          System.exit(0);
        }
		  catch(IllegalStateException e){
		    System.err.println(e.getMessage());
          System.err.println("Program terminating.");
		    System.exit(0);
	     }
        
        while(inputStreamText.hasNextLine()){
			 string = inputStreamText.nextLine(); //reads from file
          System.out.println(string);
        }
        inputStreamText.close(); //closes stream
      }
      
//FILE TYPE IS BINARY
      else if (fileType.equalsIgnoreCase("b")){
        boolean moreData = true; //sentinel
		  String string; //output
        ObjectInputStream inputStreamBinary = null; //keeps compiler happy
        
        try{
          inputStreamBinary = new ObjectInputStream(new FileInputStream(fileName)); //opens stream
        }
        catch(FileNotFoundException e){
          System.err.println("\nError: File " + fileName + " not found.");
          System.err.println("Program terminating.");
          System.exit(0);
        }
        catch(IOException e){
          System.err.println(e.getMessage());
          System.exit(0);
        }
		  
		  while(moreData){
          try{
		    string = inputStreamBinary.readUTF(); //reads from file
            System.out.print(string);
            moreData = true;
          }
          catch(EOFException e){ //signifies the end of the file's data
            moreData = false;
          }
	      catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(0);
          }
         try{
          inputStreamBinary.close(); //closes stream
         }
         catch(IOException e){
           System.err.println(e.getMessage());
           System.exit(0);
         }
       }
     }
   }
//-------------------------------------------------------------------- 
  
  public void write(){
    if (! checkName()){ //if the file does not exist
      String filePath = ("Week 5/" + fileName);
      File fileObject = new File(filePath); 
      try{
        if(fileObject.createNewFile()){ //creates new file
		  System.out.println("New file created.");
		  }
		  else{
		    System.err.println("Error: new file cannot be created.\nProgram terminating.");
		    System.exit(0);
		  }
      }
      catch(IOException e){
        System.err.println(e.getMessage());
        System.exit(0);
      }
    }

//FILE TYPE IS TEXT
    if(fileType.equalsIgnoreCase("t")){
      PrintWriter outputStreamText = null; //keeps compiler happy
      try{
        outputStreamText = new PrintWriter(new FileOutputStream(fileName, true)); //opens stream
      }
      catch(FileNotFoundException e){
        System.err.println("\nError: File " + fileName + " not found.");
        System.err.println("Program terminating.");
        System.exit(0);
      }
	  boolean prompt = true; //sentinel   
      while(prompt){
        System.out.println("\nEnter a line of information to write to the file:");
        String string = keyboard.next();
        outputStreamText.println(string); //adds line to existing file
        prompt = anotherLine();
	  }
	  outputStreamText.close();
    }
	  
//FILE TYPE IS BINARY
    else if(fileType.equalsIgnoreCase("b")){
      ObjectOutputStream outputStreamBinary = null; //keeps compiler happy
      try{
        outputStreamBinary = new ObjectOutputStream(new FileOutputStream(fileName)); //opens stream
      }
      catch(FileNotFoundException e){
        System.err.println("\nError: File " + fileName + ".dat not found.");
        System.err.println("Program terminating.");
        System.exit(0);
      }
	  catch(IOException e){
        System.err.println(e.getMessage());
        System.exit(0);
      }
		  
		String string = ""; //user input
        boolean prompt = true; //sentinel
          
        while(prompt){
          System.out.println("\nEnter a line of information to write to the file:");
          string = (string + "\n" + keyboard.next());
          prompt = anotherLine();
        }
		  
		try{
          outputStreamBinary.writeUTF(string); //writes to file
		}
		catch(IOException e){
        System.err.println(e.getMessage());
        System.exit(0);
        }
		try{
        outputStreamBinary.close(); //closes stream
      }
      catch(IOException e){
        System.err.println(e.getMessage());
        System.exit(0);
      }
    }
  }
//--------------------------------------------------------------------  
	
  public boolean checkName(){
    if(fileType.equalsIgnoreCase("t")){
      File fileObject = new File(fileName);
      if(fileObject.exists()){
		return true;
      }
      else{
        return false;
      }
    }
    else if(fileType.equalsIgnoreCase("b")){
      File fileObject = new File(fileName);
      if(fileObject.exists()){
        return true;
      }
      else{
        return false;
      }
    }
    else{
      System.err.println("\nError: Program terminating.");
      System.exit(0);
    }
    return false;
  }
//--------------------------------------------------------------------
  
  public boolean anotherLine(){
    boolean loopDone = false; //sentinel
    boolean answer = true; //return
    
    while(! loopDone){
      System.out.println("\nWould you like to enter another line? (Y/N)");
      String input = keyboard.next();
      
      if(input.equalsIgnoreCase("y")){
        answer = true;
        loopDone = true;
      }
      else if(input.equalsIgnoreCase("n")){
        answer = false;
        loopDone = true;
      }
      else{
        System.err.println("Input must be \"Y\" or \"N\".");
      }
    }
    return answer;
  }
//--------------------------------------------------------------------  
  
  public boolean keepGoing(){
    String input;
    boolean loopDone = false; //sentinel
    boolean incomplete = true; //return
    
    while(! loopDone){
      System.out.println("\nContinue? (Y/N)");
      input = keyboard.next();
      
      if(input.equalsIgnoreCase("y")){
        incomplete = true;
        loopDone = true;
      }
      else if(input.equalsIgnoreCase("n")){
        incomplete = false;
        loopDone = true;
      }
      else{
        System.err.println("Input must be \"Y\" or \"N\".");
      }
    }
    return incomplete;
  }
}

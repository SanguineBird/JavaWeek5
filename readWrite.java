/**Course: CIT-130-BIN1
Name: Meghan Moore
Date: 10/4/2014
Programming Assignment 5 - Part 2 - readWrite
Purpose: Read and write from binary or text files.*/

import java.util.Scanner;
import java.io.*;

public class readWrite implements Serializable{
  String fileName, fileType, procedure;
  boolean moreLines = true;
  Scanner keyboard = new Scanner(System.in);
  
  public boolean checkName(String fileName){
    if(fileType.equalsIgnoreCase("t")){
      File fileObject = new File(fileName + ".txt");
      if(fileObject.exists()){
        return true;
      }
      else{
        return false;
      }
    }
    else if(fileType.equalsIgnoreCase("b")){
      File fileObject = new File(fileName + ".dat");
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


  public void inputBT(){
    boolean inputTypeComplete = false; //counter
    while(! inputTypeComplete){
      System.out.print("\nChoose binary or text file(b/t): ");
      fileType = keyboard.next();
      if ((fileType.equalsIgnoreCase("b")) || (fileType.equalsIgnoreCase("t"))){
        inputTypeComplete = true;
      }
      else{
        System.err.println("\nInput must be \"b\" or \"t\".");
      }
    }
  }
  
  public String inputRW(){
    boolean inputRWComplete = false; //counter
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
  
  
  public void read(String fileName){
    
    if (checkName(fileName)){
      if (fileType.equalsIgnoreCase("t")){
        try{
          Scanner inputStreamText = new Scanner(new FileInputStream(fileName + ".txt"));
          
          while(inputStreamText.hasNextLine()){
            System.out.println(inputStreamText.nextLine());
          }
          inputStreamText.close();
        }
        catch(FileNotFoundException e){
          System.err.println("\nError: File " + fileName + ".txt not found.");
          System.err.println("Program terminating.");
          System.exit(0);
        }
      }
      
      else if (fileType.equalsIgnoreCase("b")){
        boolean moreData = true;
        
        try{
          ObjectInputStream inputStreamBinary = new ObjectInputStream(new FileInputStream(fileName + ".dat"));
          
          while(moreData){
            try{
              System.out.print(inputStreamBinary.readChar());
              moreData = true;
            }
            catch(EOFException e){
              moreData = false;
            }
            catch(IOException e){
              System.err.println(e.getMessage());
              System.exit(0);
            }
            inputStreamBinary.close();
          }
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
      }
    }
    else{
      System.err.println("\nError: File not found.");
    }
  }
  
  
  public void write(String fileName){
    if (! checkName(fileName)){
      String filePath = "Week 5/" + fileName;
      if(fileType.equalsIgnoreCase("t")){
        File fileObject = new File(fileName + ".txt");
        try{
          fileObject.createNewFile();
        }
        catch(IOException e){
          System.err.println(e.getMessage());
          System.exit(0);
        }
      }
      else if(fileType.equalsIgnoreCase("b")){
        File fileObject = new File(fileName + ".dat");
        try{
          fileObject.createNewFile();
        }
        catch(IOException e){
          System.err.println(e.getMessage());
          System.exit(0);
        }
      }
    }
      
    if(checkName(fileName)){
      if(fileType.equalsIgnoreCase("t")){
        try{
          PrintWriter outputStreamText = new PrintWriter(new FileOutputStream(fileName + ".txt", true));
          String string = "";
          boolean prompt = true;
          
          while(prompt){
            System.out.println("\nEnter a line of information to write to the file:");
            string = keyboard.next();
            outputStreamText.println(string);
            prompt = anotherLine();
          }
          
          outputStreamText.close();
        }
        catch(FileNotFoundException e){
          System.err.println("\nError: File " + fileName + ".txt not found.");
          System.err.println("Program terminating.");
          System.exit(0);
        }
      }
      else if(fileType.equalsIgnoreCase("b")){
        try{
          ObjectOutputStream outputStreamBinary = new ObjectOutputStream(new FileOutputStream(fileName + ".dat"));
          String string = "";
          boolean prompt = true;
          
          while(prompt){
            System.out.println("\nEnter a line of information to write to the file:");
            string = string + "\n" + keyboard.next();
            prompt = anotherLine();
          }
          outputStreamBinary.writeObject(string);
          
          outputStreamBinary.close();
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
      }
    }
    else{
      System.err.println("Error! Program terminating.");
            System.exit(0);
    }
  }
  
  
  public boolean anotherLine(){
    String input = "";
    boolean loopDone = false;
    boolean answer = true;
    
    while(! loopDone){
      System.out.println("\nWould you like to enter another line? (Y/N)");
      input = keyboard.next();
      
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
        input = "";
        loopDone = false;
      }
    }
    return answer;
  }
  
  
  public boolean keepGoing(){
    String input;
    boolean loopDone = false;
    boolean answer = true;
    
    while(! loopDone){
      System.out.println("\nContinue? (Y/N)");
      input = keyboard.next();
      
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
        input = "";
        loopDone = false;
      }
    }
    return answer;
  }

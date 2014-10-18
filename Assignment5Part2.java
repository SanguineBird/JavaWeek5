/**Course: CIT-130-BIN1
Name: Meghan Moore
Date: 10/4/2014
Programming Assignment 5 - Part 2
Purpose: Read and write from binary or text files.*/

import java.io.*;
import java.util.Scanner;

public class Assignment5Part2{
  public static void main(String[] args){
    boolean incomplete = true;
    String procedure;
    
    Scanner keyboard = new Scanner(System.in);
    readWrite rW = new readWrite();
    
    while (incomplete){ //"Continue" prompt is Yes
      rW.inputFile(); //prompt for file name
      rW.inputBT(); //prompt for binary or text
      procedure = rW.inputRW(); //prompt for read or write
      
      if (procedure.equalsIgnoreCase("r")){
        rW.read();
      }
      else if (procedure.equalsIgnoreCase("w")){
        rW.write();
      }
      else{
        System.err.println("\nError");
        System.exit(0);
      }
      
      incomplete = (rW.keepGoing());//prompts the user "Continue?"
      
    }
    
    System.out.println("Process completed.");
    System.exit(0);
  }
}

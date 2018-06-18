package studentCoursesBackup.util;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Results implements FileDisplayInterface, StdoutDisplayInterface{
    private ArrayList<String> results;
    private PrintWriter pWrite;

   /**
    * Constructor for Results class. Accepts string as parameter,
    * which indicates where the output file is to write to. Catches
    * IOException if generated by FileWriter. Also initializes data
    * structure used to store result strings
    * 
    * @return - no return value
    */
    public Results(String outFile)
    {
        results = new ArrayList<String>();
        try
        {
            pWrite = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

   /**
    * Implements writeToStdout from StdoutDisplayInterface
    *
    * @return void - no return value
    */
    public void writeToStdout(String s)
    {
        System.out.println(s);
    }
	
   /**
    * Implements writeToFile for FileDisplayInterface
    *
    * @return void - no return value
    */
    public void writeToFile(String s)
    {
        pWrite.write(s+'\n', 0, s.length()+1);
    }

   /**
    * Stores result string in Results ArrayList
    *
    * @return void - no return type
    */
    public void storeNewResult(ArrayList<String> arr)
    {
        results = arr;
    }

   /**
    * Prints all results in results
    *
    * @return void - no return type
    */
    public void printResults(int type)
    {
	if(type == 0)
	{
            writeToStdout("Original Tree:");	
	}
	else if(type == 1)
	{
            writeToStdout("");
            writeToStdout("Backup Tree 1:");
	}
	else
	{
            writeToStdout("");
            writeToStdout("Backup Tree 2:");
	}

	int i;
	for(i=0; i<results.size(); i++)
        {
            writeToStdout(results.get(i));
            writeToFile(results.get(i));
	}
    }
	
   /**
    * Closes PrintWriter to empty buffer and write to file
    *
    * @return void - no return type
    */
    public void close()
    {
        pWrite.close();
    }
}
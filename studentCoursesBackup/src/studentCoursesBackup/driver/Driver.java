import studentCoursesBackup.util.TreeBuilder;
import studentCoursesBackup.util.Results;
import studentCoursesBackup.util.FileProcessor;

import java.util.ArrayList;

public class Driver
{
    public static void main(String[] args)
    {
        if(args.length == 5)
        {
            TreeBuilder tree = new TreeBuilder();

            FileProcessor insertData = new FileProcessor(args[0]);
            FileProcessor deleteData = new FileProcessor(args[1]);
            Results origResults = new Results(args[2]);
            Results b1Results = new Results(args[3]);
            Results b2Results = new Results(args[4]);

            String insLine = insertData.readLine();
            while(insLine != null)
            {
                String[] insInput = insLine.split(":");
	        tree.insert(insInput[0], insInput[1]);
	        insLine = insertData.readLine();
            }

            String delLine = deleteData.readLine();
            while(delLine != null)
            {
	        String[] delInput = delLine.split(":");
	        tree.delete(delInput[0], delInput[1]);
	        delLine = deleteData.readLine();
            }
            insertData.close();
            deleteData.close();  

            tree.printNodes(origResults, 0);
            tree.printNodes(b1Results, 1);
            tree.printNodes(b2Results, 2);
    
            origResults.printResults(0);
            b1Results.printResults(1);
            b2Results.printResults(2);
    
            origResults.close();
            b1Results.close();
            b2Results.close();
        }
        else
        {
            System.out.println("Incorrect number of arguments given");
        }
    }
}

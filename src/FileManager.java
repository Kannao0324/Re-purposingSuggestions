import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * File management class for writing our application data to csv files and reading it back again.
 */
public class FileManager
{
    // csv file to write and read from
    String fileName = "RepurposingSuggestions.txt";

    /**
     * Takes a provided array of Ideas data and writes it to file in semicolon delimited format.
     *
     * @param ideasData The array we want to write file.
     */
    public void WriteToFile(Idea[] ideasData) {
        // Use try catch to get the bufferedWriter work
        try {
            // Create a new object
            BufferedWriter output = new BufferedWriter(new FileWriter(fileName));
            for (int i = 0; i < ideasData.length; i++) {
                // If there is no data in the array, break the loop
                if (ideasData[i] == null) {
                    break;
                }
                // Write data to the file
                output.write(ideasData[i].toString());
                // Add a new line
                output.newLine();
            }
            output.close();
        }
        // If the bufferwriter is not working, display a message
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Takes a provided array of Ideas data and reads it to file in semicolon delimited format.
     * @return
     */
    public FileData ReadFromFile() {

        // Create fileData object
        FileData data = new FileData();
        // Access to the array
        data.ideasData = new Idea[100];
        // Start data from 0
        data.count = 0;

        // Use try catch to get the bufferedReader work
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            // Split the line in the file
            String line;
            // Read the line in the array and compare the next line is null or not
            while((line = input.readLine()) !=null)
            {
                // Create a temp array and split the line with ;
                String[] temp = line.split(";");
                // Put each value into each argument
                data.ideasData[data.count] = new Idea(temp[0],temp[1],temp[2],temp[3],temp[4]);
                // increase the counter
                data.count++;
            }
            // Close the reading file
            input.close();
        }
        // if the reading file does not work, display the error message
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            // Reset the object and clear the form
            data.ideasData = null;
            data.count = 0;
        }
        return data;
    }

}

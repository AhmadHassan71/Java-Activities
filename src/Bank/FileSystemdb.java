package Bank;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FileSystemdb implements Database {
    private File customerFile;

    public FileSystemdb(String dataFile) {
        customerFile = new File(dataFile);
        if (!customerFile.exists()) {
            // Create the customer data file if it doesn't exist
            try {
                customerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void connect() {
        // No need to establish a connection for file storage
    }

    @Override
    public void disconnect() {
        // No need to disconnect for file storage
    }
    
   

    @Override
    public void executeQuery(String data) {
        try (FileWriter writer = new FileWriter(customerFile, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String query) {
        
    }

    public ResultSet executeSelectQuery(String query) {
    	return null;
    }

   
    @Override
    public List<String> loadall() {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(customerFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    
}


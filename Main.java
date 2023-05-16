import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter SQL statement:");
        String sql = scanner.nextLine();
        String[] tokens = sql.split(" ");

        if (tokens[0].equals("CREATE") && tokens[1].equals("TABLE")) {
            String tableName = tokens[2];
            int startIndex = sql.indexOf("(") + 1;
            int endIndex = sql.indexOf(")");
            String[] columns = sql.substring(startIndex, endIndex).split(",");
            String[] columnNames = new String[columns.length];
            String[] columnTypes = new String[columns.length];

            for (int i = 0; i < columns.length; i++) {
                String[] parts = columns[i].trim().split(" ");
                columnNames[i] = parts[0];
                columnTypes[i] = parts[1];
            }

            Table table = new Table(tableName, columnNames, columnTypes);
            System.out.println("Table " + tableName + " created.");
        } else if (tokens[0].equals("INSERT") && tokens[1].equals("INTO")) {
            String tableName = tokens[2];
            int startIndex = sql.indexOf("(") + 1;
            int endIndex = sql.indexOf(")");
            String[] values = sql.substring(startIndex, endIndex).split(",");

            try {
                BufferedReader reader = new BufferedReader(new FileReader(tableName + "_meta.txt"));
                String[] columnNames = reader.readLine().replaceAll("[\\[\\]\\s]+", "").split(",");
                String[] columnTypes = reader.readLine().replaceAll("[\\[\\]\\s]+", "").split(",");
                reader.close();

                if (values.length != columnNames.length) {
                    throw new IllegalArgumentException("Number of values does not match number of columns.");
                }

                Table table = new Table(tableName, columnNames, columnTypes);
                table.insert(values);
                System.out.println("Row inserted into " + tableName + ".");
            } catch (FileNotFoundException e) {
                System.err.println("Table " + tableName + " not found.");
            } catch (IOException e) {
                System.err.println("Error reading metadata for table " + tableName + ".");
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.err.println("Invalid SQL statement.");
        }
    }
}

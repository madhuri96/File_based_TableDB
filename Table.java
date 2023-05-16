import java.io.*;
import java.util.*;

public class Table {
    private String tableName;
    private String[] columnNames;
    private String[] columnTypes;
    private String dataFilePath;

    public Table(String tableName, String[] columnNames, String[] columnTypes) {
        this.tableName = tableName;
        this.columnNames = columnNames;
        this.columnTypes = columnTypes;
        this.dataFilePath = tableName + ".txt";
        writeMetaData();
    }

    private void writeMetaData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(tableName + "_meta.txt"));
            writer.write(Arrays.toString(columnNames));
            writer.newLine();
            writer.write(Arrays.toString(columnTypes));
            writer.newLine();
            writer.write(dataFilePath);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insert(String[] values) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath, true));
            for (int i = 0; i < values.length; i++) {
                if (columnTypes[i].equals("INTEGER")) {
                    writer.write(Integer.parseInt(values[i]) + "");
                } else if (columnTypes[i].equals("STRING")) {
                    writer.write(values[i]);
                }
                if (i != values.length - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


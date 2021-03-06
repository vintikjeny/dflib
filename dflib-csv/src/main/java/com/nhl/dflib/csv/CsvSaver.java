package com.nhl.dflib.csv;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.Index;
import com.nhl.dflib.row.RowProxy;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class CsvSaver {

    private CSVFormat format;
    private boolean createMissingDirs;

    public CsvSaver() {
        this.format = CSVFormat.DEFAULT;
    }

    /**
     * Optionally sets the style or format of the imported CSV. CSVFormat comes from "commons-csv" library and
     * contains a number of predefined formats, such as CSVFormat.MYSQL, etc. It also allows to customize the format
     * further, by defining custom delimiters, line separators, etc.
     *
     * @param format a format object defined in commons-csv library
     * @return this loader instance
     */
    public CsvSaver format(CSVFormat format) {
        this.format = format;
        return this;
    }

    /**
     * Instructs the saver to create any missing directories in the file path.
     *
     * @return this loader instance
     * @since 0.6
     */
    public CsvSaver createMissingDirs() {
        this.createMissingDirs = true;
        return this;
    }

    public void save(DataFrame df, File file) {

        if (createMissingDirs) {
            File dir = file.getParentFile();
            if (dir != null) {
                dir.mkdirs();
            }
        }

        try (FileWriter out = new FileWriter(file)) {
            save(df, out);
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV to " + file + ": " + e.getMessage(), e);
        }
    }

    public void save(DataFrame df, String fileName) {
        save(df, new File(fileName));
    }

    public void save(DataFrame df, Appendable out) {

        try {
            CSVPrinter printer = new CSVPrinter(out, format);
            printHeader(printer, df.getColumnsIndex());

            int len = df.width();
            for (RowProxy r : df) {
                printRow(printer, r, len);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV: " + e.getMessage(), e);
        }
    }

    public String saveToString(DataFrame df) {

        StringWriter out = new StringWriter();
        save(df, out);
        return out.toString();
    }

    private void printHeader(CSVPrinter printer, Index index) throws IOException {
        for (String label : index.getLabels()) {
            printer.print(label);
        }
        printer.println();
    }

    private void printRow(CSVPrinter printer, RowProxy row, int len) throws IOException {
        for (int i = 0; i < len; i++) {
            printer.print(row.get(i));
        }
        printer.println();
    }
}

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.io.Writer;
import java.nio.file.Files;

import org.apache.commons.io.FilenameUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import au.com.bytecode.opencsv.CSVWriter;

public class ProcessFile {

	private String filePath;
	private String[] fileNames;
	private String key;
	private String combinedFileName;
	private String outputFilePath;
	private Table<String, String, String> outputTable;

	public ProcessFile(String filePath, String outputFilePath, String combinedFileName, String[] fileNames,
			String key) {
		this.filePath = filePath;
		this.fileNames = fileNames;
		this.key = key;
		this.outputFilePath = outputFilePath;
		this.combinedFileName = combinedFileName;

		// create a table
		outputTable = HashBasedTable.create();
	}

	public void parseAllFile() {
		for (String fileName : fileNames) {
			String file = filePath + fileName;

			String extension = FilenameUtils.getExtension(file).toLowerCase();

			String[][] arr = {};

			if (extension.equals("html")) {
				ParseHtmlFile parseHtmlFile = new ParseHtmlFile(file);
				arr = parseHtmlFile.parseFile();
			} else if (extension.equals("csv")) {
				ParseCsvFile parseCsvFile = new ParseCsvFile(file);
				arr = parseCsvFile.parseFile();
			}

			// Find the index of key
			int indexOfKey = 0;
			for (int i = 0; i < arr[0].length; i++) {
				if (arr[0][i].equals(key)) {
					indexOfKey = i;
					break;
				}
			}

			// Create table to store all the data
			for (int i = 1; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					// Skip key column
					if (j == indexOfKey)
						continue;
					outputTable.put(arr[i][indexOfKey], arr[0][j], arr[i][j]);
				}
			}
		}
	}

	public void writeFile() {

		try {
			Writer writer = Files.newBufferedWriter(Paths.get(outputFilePath + combinedFileName));

			CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

			Set<String> columnSet = new HashSet<String>();
			// Get column
			columnSet = outputTable.columnKeySet();
			int n = columnSet.size();

			String columnArr[] = new String[n + 1];
			// Key as first column
			columnArr[0] = key;
			System.arraycopy(columnSet.toArray(), 0, columnArr, 1, n);

			// Output header(column)
			csvWriter.writeNext(columnArr);
			System.out.println(Arrays.toString(columnArr));

			// Get key(Sorted)
			Set<String> keySet = new TreeSet<String>();
			keySet = outputTable.rowKeySet();

			String arrKey[] = new String[keySet.size()];
			arrKey = keySet.toArray(arrKey);

			// Get value
			for (int i = 0; i < arrKey.length; i++) {
				String valueArr[] = new String[n + 1];
				// Key as first value
				valueArr[0] = arrKey[i];
				for (int j = 1; j < n + 1; j++) {
					String value = outputTable.get(arrKey[i], columnArr[j]);
					// Set empty string if null
					valueArr[j] = (value == null) ? "" : value;
				}
				// Output value
				csvWriter.writeNext(valueArr);
				System.out.println(Arrays.toString(valueArr));
			}

			csvWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

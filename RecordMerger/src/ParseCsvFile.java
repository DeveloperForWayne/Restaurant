import java.io.FileReader;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class ParseCsvFile implements ParseFile {

	private String file;

	public ParseCsvFile(String file) {
		super();
		this.file = file;
	}

	@Override
	public String[][] parseFile() {
		try {

			// Create an object of filereader class with CSV file as a parameter.
			FileReader filereader = new FileReader(file);

			// create csvReader object
			CSVReader csvReader = new CSVReader(filereader);
			List<String[]> allData = csvReader.readAll();

			// Create Arrays to hold read data
			String[][] Arr = new String[allData.size()][allData.get(0).length];

			for (int i = 0; i < allData.size(); i++) {
				String[] row = allData.get(i);
				for (int j = 0; j < row.length; j++) {
					Arr[i][j] = row[j];
				}
			}

			csvReader.close();

			return Arr;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}

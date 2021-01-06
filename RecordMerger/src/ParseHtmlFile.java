import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtmlFile implements ParseFile {

	private String file;

	public ParseHtmlFile(String file) {
		super();
		this.file = file;
	}

	@Override
	public String[][] parseFile() {
		String[][] Arr = {};

		try {
			File input = new File(file);
			Document doc = Jsoup.parse(input, "UTF-8", "");

			// Find table with ID "directory"
			Element element = doc.select("table#directory").first();

			Elements rows = element.select("tr");

			for (int i = 0; i < rows.size(); i++) {
				// Find header
				Elements columnsTh = rows.get(i).select("th");
				if (columnsTh.size() != 0) {
					Arr = new String[rows.size()][columnsTh.size()];

					for (int j = 0; j < columnsTh.size(); j++) {
						// Output to Array and skip nbsp
						Arr[i][j] = columnsTh.get(j).text().replace("\u00a0", "");
					}
					break;
				}
			}

			for (int i = 0; i < rows.size(); i++) {
				// Find data
				Elements columnsTd = rows.get(i).select("td");
				for (int j = 0; j < columnsTd.size(); j++) {
					// Output to Array and skip nbsp
					Arr[i][j] = columnsTd.get(j).text().replace("\u00a0", "");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return Arr;

	}

}

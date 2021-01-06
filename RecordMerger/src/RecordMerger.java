
public class RecordMerger {

	public static final String FILENAME_COMBINED = "combined.csv";

	public static final String BASE_DIR = "C:\\Users\\Wayne\\eclipse\\eclipse-workspace\\RecordMerger\\";

	public static final String FILE_PATH = BASE_DIR + "data\\";

	public static final String OUTPUT_PATH = BASE_DIR;

	public static final String KEY = "ID";

	/**
	 * Entry point of this test.
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public static void main(final String[] args) throws Exception {

		if (args.length == 0) {
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			System.exit(1);
		}

		// your code starts here.
		ProcessFile processFile = new ProcessFile(FILE_PATH, BASE_DIR, FILENAME_COMBINED, args, KEY);
		processFile.parseAllFile();

		processFile.writeFile();

	}

}

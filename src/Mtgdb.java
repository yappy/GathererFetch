import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Fetch the all card list for reference.<br>
 * https://api.mtgdb.info/
 * 
 * @author yappy
 */
public class Mtgdb {

	private static final int MULTIVERSEID_MAX = 400000;
	private static final String OUT_FILE = "./etc/reflist.txt";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		char[] buf = new char[1024];
		try (PrintWriter out = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream(OUT_FILE),
						StandardCharsets.UTF_8),
				true)) {
			for (int i = 1; i <= MULTIVERSEID_MAX; i++) {
				URL url = new URL("http://api.mtgdb.info/cards/" + i);
				try (Reader r = new InputStreamReader(url.openStream(),
						StandardCharsets.UTF_8)) {
					int len;
					while ((len = r.read(buf, 0, buf.length)) > 0) {
						out.print(new String(buf, 0, len));
					}
					out.println();
					System.out.println(i);
				}
			}
		}
	}

}

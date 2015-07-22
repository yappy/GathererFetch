import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author yappy
 *
 */
public class Fetch {

	private static final String URL_ORACLE = "http://gatherer.wizards.com/Pages/Card/Details.aspx?";
	//private static final String URL_PRINTED = "http://gatherer.wizards.com/Pages/Card/Details.aspx?printed=true&";

	/**
	 * URLとクッキーからGathererページのXHTML文字列を取得する。
	 * 
	 * @param urlStr
	 *            URLを文字列で
	 * @param cookie
	 *            クッキーを文字列で
	 * @return XHTMLを文字列で
	 * @throws IOException
	 *             I/Oエラー
	 */
	private static String fetchHtml(String urlStr, String cookie)
			throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Cookie", cookie);

		StringBuilder result = new StringBuilder();
		char[] buf = new char[1024];
		try (Reader r = new InputStreamReader(conn.getInputStream(),
				StandardCharsets.UTF_8)) {
			int len;
			while ((len = r.read(buf, 0, buf.length)) >= 0) {
				result.append(buf, 0, len);
			}
		}
		return result.toString();
	}

	/**
	 * multiverseidごとの処理。
	 * 
	 * @param id
	 *            multiverseid
	 * @throws IOException
	 *             I/Oエラー
	 */
	private static void processMultiverseId(int id) throws IOException, GathererParseException {
		// English
		String html = fetchHtml(URL_ORACLE + "multiverseid=" + id, "");
		//System.out.println(html);
		Parser.parseOracle(null, html);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		int[] sampleIds = new int[] {
				// tasiguru
				393047, };
		for (int id : sampleIds) {
			System.out.println(id);
			try {
				processMultiverseId(id);
			} catch (IOException | GathererParseException e) {
				e.printStackTrace();
			}
		}
	}

}

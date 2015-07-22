import java.util.Map;
import java.util.Optional;

/**
 * @author yappy
 *
 */
public final class Parser {

	/**
	 * 文字列を探し、先頭のインデックスを返す。
	 * 
	 * @param target
	 * @param str
	 * @param from
	 * @return
	 */
	private static Optional<Integer> search(String target, String str,
			int from) {
		int ind = target.indexOf(str, from);
		if (ind < 0) {
			return Optional.empty();
		}
		return Optional.of(ind);
	}

	/**
	 * 文字列を探し、それを読み捨てた後のインデックスを返す。
	 * 
	 * @param target
	 * @param str
	 * @param from
	 * @return
	 */
	private static Optional<Integer> searchAndDiscard(String target, String str,
			int from) {
		int ind = target.indexOf(str, from);
		if (ind < 0) {
			return Optional.empty();
		}
		return Optional.of(ind + str.length());
	}

	/**
	 * 前後の空白を取り除く。
	 * 
	 * @param str
	 * @return
	 */
	private static String trim(String str) {
		int begin = 0;
		int end = str.length() - 1;
		while (begin < end && Character.isWhitespace(str.charAt(begin))) {
			begin++;
		}
		while (begin < end && Character.isWhitespace(str.charAt(end))) {
			end--;
		}
		return str.substring(begin, end + 1);
	}

	/**
	 * オラクルのページから必要な情報を取得する。
	 * 
	 * @param result
	 * @param html
	 * @throws GathererParseException
	 */
	public static void parseOracle(Map<String, String> result, String html)
			throws GathererParseException {
		int ind = 0;
		int end = 0;
		Optional<Integer> next;
		String extracted;

		// Card Name
		/*
		 * <div id="ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_nameRow"
		 * class="row"> <div class="label"> Card Name:</div> <div class="value">
		 * Tasigur, the Golden Fang</div> </div>
		 */
		next = searchAndDiscard(html,
				"<div id=\"ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_nameRow\" class=\"row\">",
				ind);
		ind = next.orElseThrow(
				() -> new GathererParseException("CardName must be exists"));
		next = searchAndDiscard(html, "<div class=\"value\">", ind);
		ind = next.orElseThrow(
				() -> new GathererParseException("Failed to get CardName"));
		end = search(html, "</div>", ind).orElseThrow(
				() -> new GathererParseException("Failed to get CardName"));
		extracted = trim(html.substring(ind, end));
		ind = end;
		System.out.println(extracted);
	}

}

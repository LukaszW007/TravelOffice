package zad1;

import java.util.ListResourceBundle;

public class CountryInfo_en_GB extends ListResourceBundle {

	public Object[][] getContents() {
		return contents;
	}

	private Object[][] contents = { { "jezioro", "lake" },
			{ "góry", "mountains" }, { "morze", "sea" }, };
}
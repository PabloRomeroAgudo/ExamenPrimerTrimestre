package util;

import java.time.LocalDate;

public class Util {

	/**
	 * Convierte un String en formato aaaa-mm-dd a LocalDate
	 *  
	 * @param string
	 * @return local date
	 */
	public static LocalDate string2date(String s) {
		if (s == null) {
			return null;
		}
		String f[] = s.split("-");
		return LocalDate.of(Integer.parseInt(f[0]), Integer.parseInt(f[1]), Integer.parseInt(f[2])); 
	}

}

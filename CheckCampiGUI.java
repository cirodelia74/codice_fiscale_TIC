package codiceFiscale;

import java.util.Calendar;
import java.util.regex.Pattern;

public final class CheckCampiGUI {
	
	/**
	 * Metodo che controlla che il cognome inserito abbia un formato valido
	 * @param s: stringa da controllare
	 * @return true se la stringa rispetta il patter, false altrimenti
	 */
	public static boolean checkCognome(String s) {
		
		String aux = s;
		// formatto il cognome eliminando eventuali apostrofi e spazi vuoti
		// e trasformando la stringa tutto in maiscolo
		aux = aux.replaceAll( "\\'", ""); 
		aux = aux.replaceAll("\\s+","");
		aux = aux.toUpperCase();
		
		if (Pattern.matches("[A-Z]+", aux) && aux.length() >= 2)
			return true;
		else
			return false;
	}
	
	/**
	 * Metodo che controlla che il cognome inserito abbia un formato valido
	 * @param s: stringa da controllare
	 * @return true se la stringa rispetta il patter, false altrimenti
	 */
	public static boolean checkNome(String s) {
		
		String aux = s;
		// formatto il nome eliminando eventuali spazi vuoti
		// e trasformando la stringa tutto in maiscolo 
		aux = aux.replaceAll("\\s+","");
		aux = aux.toUpperCase();
		
		if (Pattern.matches("[A-Z]+", aux) && aux.length() >= 2)
			return true;
		else
			return false;
	}
	
	/**
	 * Metodo che verifica la validita' della data inserita
	 * @param giorno: giorno di nascita inserito
	 * @param mese: mese di nascita inserito
	 * @param anno: anno di nascita inserito
	 * @return true se i dati inseriti sono conformi, false altrimenti
	 */
	public static boolean isDataValida(int giorno, int mese, int anno) {

        if (giorno <= 0 || mese <= 0 || anno <= 0)
            return false;
        if (mese > 12 || giorno > 31)
            return false;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, anno);
        cal.set(Calendar.MONTH, mese - 1);

        // trova il massimo giorno possibile in base al mese e all'anno.
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (giorno > maxDay)
            return false;

        return true;
    }

}

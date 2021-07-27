/**
 * @author - Ciro Francesco D'ELia
 * Corso TIC - 2021 
 */

package codiceFiscale;

public class CodiceFiscale {
	
	private String cognome;
	private String nome;
	private char sesso;
	private int giornoNascita;
	private int meseNascita;
	private int annoNascita;
	private String comuneNascita;
	private String codiceFiscale;
	
	/**
	 * Costruttore della classe senza parametri che inizializza le proprieta'
	 * a valori di default
	 */
	public CodiceFiscale() {
		
		this.cognome = "";
		this.nome = "";
		this.sesso = Character.MIN_VALUE;
		this.giornoNascita = 0;
		this.meseNascita = 0;
		this.annoNascita = 0;
		this.comuneNascita = "";
		this.codiceFiscale = "";		
	}
	
	/**
	 * Costruttore della classe che inizializza le proprieta' con i valori passati come parametri
	 * @param cognome: Cognome utente
	 * @param nome: Nome utente
	 * @param sesso: Sesso utente
	 * @param giornoNascita: Giorno di nascita dell'utente
	 * @param meseNascita: Mese di nascita dell'utente
	 * @param annoNascita: Anno di nascita dell'utente
	 * @param comuneNascita: Comune di nascita dell'utente
	 */
	public CodiceFiscale(String cognome, String nome, char sesso, int giornoNascita, int meseNascita, int annoNascita,
			String comuneNascita) {
		super();
		this.cognome = cognome;
		this.nome = nome;
		this.sesso = sesso;
		this.giornoNascita = giornoNascita;
		this.meseNascita = meseNascita;
		this.annoNascita = annoNascita;
		this.comuneNascita = comuneNascita;
	}
	
	/**
	 * Metodo che calcola la parte del codice fiscale relativa al cognome
	 * @return true se la procedura e' andata a buon fine, false altrimenti
	 */
	public boolean calcolaCognome() {
		
		String cognomeAux = this.cognome;
		String aux = "";
		char c;
		
		// formatto il cognome eliminando eventuali apostrofi e spazi vuoti
		// e trasformando la stringa tutto in maiuscolo
		cognomeAux = cognomeAux.replaceAll( "\\'", ""); 
		cognomeAux = cognomeAux.replaceAll("\\s+","");
		cognomeAux = cognomeAux.toUpperCase();
		
		for (int i=0; i<cognomeAux.length(); i++) {
			c = cognomeAux.charAt(i);
			if (!isVocale(c)) {
				aux += c;
				if (aux.length() == 3)
					break;
			}
		}
		
		if (aux.length() < 3) {
			for (int i=0; i<cognomeAux.length(); i++) {
				c = cognomeAux.charAt(i);
				if (isVocale(c)) {
					aux += c;
					if (aux.length() == 3)
						break;
				}
			}
		}
		
		if (aux.length() < 3) {
			aux += "X";
		}
		if (aux.length() == 3) {
			this.codiceFiscale += aux;
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Metodo che calcola la parte del codice fiscale relativa al nome
	 * @return true se la procedura e' andata a buon fine, false altrimenti
	 */
	public boolean calcolaNome() {
		
		String nomeAux = this.nome;
		String aux = "";
		char c;
		int numConsonanti = 0;
		
		// formatto il cognome eliminando eventuali apostrofi e spazi vuoti
		// e trasformando la stringa tutto in maiuscolo
		nomeAux = nomeAux.replaceAll( "\\'", ""); 
		nomeAux = nomeAux.replaceAll("\\s+","");
		nomeAux = nomeAux.toUpperCase();
		
		// verifico se il nome contiene piu' di 4 consonanti
		for (int i=0; i<nomeAux.length(); i++) {
			c = nomeAux.charAt(i);
			if (!isVocale(c)) {
				numConsonanti++;
			}
		}
		
		// codice che calcola la parte relativa al nome se sono presenti 4 o piu' consonanti
		if (numConsonanti >= 4) {
			numConsonanti = 0;
			for (int i=0; i<nomeAux.length(); i++) {
				c = nomeAux.charAt(i);
				if (!isVocale(c)) {
					numConsonanti++;
					if (numConsonanti != 2) {
						aux += c;
						if (aux.length() == 3)
							break;
					}
					
				}
				
			}
			this.codiceFiscale += aux;
			return true;
		}
		
		// codice che calcola la parte relativa al nome se sono presenti meno di 4 consonanti
		for (int i=0; i<nomeAux.length(); i++) {
			c = nomeAux.charAt(i);
			if (!isVocale(c)) {
				aux += c;
				if (aux.length() == 3)
					break;
			}
		}
		
		if (aux.length() < 3) {
			for (int i=0; i<nomeAux.length(); i++) {
				c = nomeAux.charAt(i);
				if (isVocale(c)) {
					aux += c;
					if (aux.length() == 3)
						break;
				}
			}
		}
		
		if (aux.length() < 3) {
			aux += "X";
		}
		if (aux.length() == 3) {
			this.codiceFiscale += aux;
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Metodo che verifica se il carattere passato come parametro e' una vocale
	 * @param c: carattere da controllare
	 * @return true se e' una vocale, false altrimenti
	 */
	private boolean isVocale(char c) {
		
		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
			return true;
		return false;
	}
	
	/**
	 * Metodo che calcola la parte del codice fiscale relativa alla data di nascita
	 * @return true se la procedura e' andata a buon fine, false altrimenti
	 */
	public boolean calcolaData() {
		
		String aux = "";
		
		// aggiungo la parte relativa all'anno di nascita
		if (this.annoNascita != 0) {
			String annoNascita = Integer.toString(this.annoNascita);
			aux += annoNascita.substring(2);			
		}
		else 
			return false;
		
		// aggiungo la parte relativa al mese di nascita
		if (this.meseNascita != 0) {
			aux += impostaMese(this.meseNascita);;
		}
		else
			return false;
		
		// aggiungo la parte relativa al giorno di nascita
		if (this.giornoNascita != 0) {
			if (this.sesso == 'F') {
				int giorno = this.giornoNascita + 40;
				aux += Integer.toString(giorno);
			}
			if (this.sesso == 'M' && this.giornoNascita > 0 && this.giornoNascita < 10) {
				aux += ("0" + Integer.toString(this.giornoNascita));
			}
			if (this.sesso == 'M' && this.giornoNascita >= 10) {
				aux += Integer.toString(this.giornoNascita);
			}
			this.codiceFiscale += aux;
			return true;	
		}
		else 
			return false;
	}
	
	/**
	 * Metodo che restuisce il carattere relativo al mese di nascita
	 * @param mese
	 * @return il carattere selezionato
	 */
	private String impostaMese(int mese) {
		
		String s = "";
		switch (mese) {
		case 1:
			s = "A";
			break;
		case 2:
			s = "B";
			break;
		case 3:
			s = "C";
			break;
		case 4:
			s = "D";
			break;
		case 5:
			s = "E";
			break;
		case 6:
			s = "H";
			break;
		case 7:
			s = "L";
			break;
		case 8:
			s = "M";
			break;
		case 9:
			s = "P";
			break;
		case 10:
			s = "R";
			break;
		case 11:
			s = "S";
			break;
		case 12:
			s = "T";
			break;
		}
		return s;
	}
	
	/**
	 * Metodo semplificato che calcola la parte del codice fiscale relativo
	 * al comune di nascita
	 * @return true se la procedura e' andata a buon fine, false altrimenti
	 */
	public boolean calcolaComuneNascita() {
		
		if (!this.comuneNascita.equals("")) {
			this.codiceFiscale += this.comuneNascita;
			return true;
		}
		else
			return false;
	}
	
	
	/**
	 * Metodo che calcola il codice di controllo corrispondente al sedicesimo
	 * carattere del codice fiscale
	 * @return true se il calcolo ha prodotto un risultato
	 * 		   false in caso contrario
	 */
	public boolean calcolaCodiceControllo() {
		
		int sommaCaratteri = 0;
		if (this.codiceFiscale.length() == 15) {
			for (int i=0; i<this.codiceFiscale.length(); i++) {
				if ((i+1)%2 == 0) {
					sommaCaratteri += calcolaNumeroPari(this.codiceFiscale.charAt(i));
				}
				if ((i+1)%2 == 1) {
					sommaCaratteri += calcolaNumeroDispari(this.codiceFiscale.charAt(i));
				}
			}
			
			this.codiceFiscale += codiceControllo(sommaCaratteri%26);
			return true;
		}
		else 
			return false;
	}
	
	/**
	 * Metodo che converte in numeri i caratteri di posizione pari.
	 * @param c: carattere da elaborare
	 * @return l'intero corrispondente nella tabella
	 */
	private int calcolaNumeroPari(char c) {
				
		if (c == 'A' || c == '0')
			return 0;
		if (c == 'B' || c == '1')
			return 1;
		if (c == 'C' || c == '2')
			return 2;
		if (c == 'D' || c == '3')
			return 3;
		if (c == 'E' || c == '4')
			return 4;
		if (c == 'F' || c == '5')
			return 5;
		if (c == 'G' || c == '6')
			return 6;
		if (c == 'H' || c == '7')
			return 7;
		if (c == 'I' || c == '8')
			return 8;
		if (c == 'J' || c == '9')
			return 9;
		if (c == 'K')
			return 10;
		if (c == 'L')
			return 11;
		if (c == 'M')
			return 12;
		if (c == 'N')
			return 13;
		if (c == 'O')
			return 14;
		if (c == 'P')
			return 15;
		if (c == 'Q')
			return 16;
		if (c == 'R')
			return 17;
		if (c == 'S')
			return 18;
		if (c == 'T')
			return 19;
		if (c == 'U')
			return 20;
		if (c == 'V')
			return 21;
		if (c == 'W')
			return 22;
		if (c == 'X')
			return 23;
		if (c == 'Y')
			return 24;
		else
			return 25;
	}
	
	/**
	 * Metodo che converte in numeri i caratteri di posizione dispari.
	 * @param c: carattere da elaborare
	 * @return l'intero corrispondente nella tabella
	 */
	private int calcolaNumeroDispari(char c) {
				
		if (c == 'A' || c == '0')
			return 1;
		if (c == 'B' || c == '1')
			return 0;
		if (c == 'C' || c == '2')
			return 5;
		if (c == 'D' || c == '3')
			return 7;
		if (c == 'E' || c == '4')
			return 9;
		if (c == 'F' || c == '5')
			return 13;
		if (c == 'G' || c == '6')
			return 15;
		if (c == 'H' || c == '7')
			return 17;
		if (c == 'I' || c == '8')
			return 19;
		if (c == 'J' || c == '9')
			return 21;
		if (c == 'K')
			return 2;
		if (c == 'L')
			return 4;
		if (c == 'M')
			return 18;
		if (c == 'N')
			return 20;
		if (c == 'O')
			return 11;
		if (c == 'P')
			return 3;
		if (c == 'Q')
			return 6;
		if (c == 'R')
			return 8;
		if (c == 'S')
			return 12;
		if (c == 'T')
			return 14;
		if (c == 'U')
			return 16;
		if (c == 'V')
			return 10;
		if (c == 'W')
			return 22;
		if (c == 'X')
			return 25;
		if (c == 'Y')
			return 24;
		else
			return 23;
	}
	
	/**
	 * Metodo che individua il codice di controllo relativo al sedicesimo cerattere
	 * del Codice Fiscale in funzione del resto ottenuto dall'algoritmo di calcolo
	 * @param c: carattere da elaborare
	 * @return l'intero corrispondente nella tabella
	 */
	private char codiceControllo(int resto) {
				
		if (resto == 0)
			return 'A';
		if (resto == 1)
			return 'B';
		if (resto == 2)
			return 'C';
		if (resto == 3)
			return 'D';
		if (resto == 4)
			return 'E';
		if (resto == 5)
			return 'F';
		if (resto == 6)
			return 'G';
		if (resto == 7)
			return 'H';
		if (resto == 8)
			return 'I';
		if (resto == 9)
			return 'J';
		if (resto == 10)
			return 'K';
		if (resto == 11)
			return 'L';
		if (resto == 12)
			return 'M';
		if (resto == 13)
			return 'N';
		if (resto == 14)
			return 'O';
		if (resto == 15)
			return 'P';
		if (resto == 16)
			return 'Q';
		if (resto == 17)
			return 'R';
		if (resto == 18)
			return 'S';
		if (resto == 19)
			return 'T';
		if (resto == 20)
			return 'U';
		if (resto == 21)
			return 'v';
		if (resto == 22)
			return 'W';
		if (resto == 23)
			return 'X';
		if (resto == 24)
			return 'y';		
		else
			return 'Z';
	}
	
	/**
	 * Metodo che calcola il codice fiscale in base alle informazioni dell'utente
	 * @return true in caso di procedura eseguita correttamente, false altrimenti
	 */
	public boolean calcolaCodiceFiscale() {
		
		this.codiceFiscale = "";
		
		if (!calcolaCognome()) 
			return false;
		if (!calcolaNome()) 
			return false;
		if (!calcolaData()) 
			return false;
		if (!calcolaComuneNascita()) 
			return false;
		if (!calcolaCodiceControllo()) 
			return false;
		return true;
	}
	
	/**
	 * Metodo che si occupa di resettare tutti i valori degli attributi
	 * dell'oggetto istanziato
	 */
	public void reset() {
		
		this.cognome = "";
		this.nome = "";
		this.sesso = Character.MIN_VALUE;
		this.giornoNascita = 0;
		this.meseNascita = 0;
		this.annoNascita = 0;
		this.comuneNascita = "";
		this.codiceFiscale = "";		
	}

	
	/*
	 * Implementazione dei metodi Setter e Getter delle proprieta' della classe
	 */
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public char getSesso() {
		return sesso;
	}

	public void setSesso(char sesso) {
		this.sesso = sesso;
	}

	public int getGiornoNascita() {
		return giornoNascita;
	}

	public void setGiornoNascita(int giornoNascita) {
		this.giornoNascita = giornoNascita;
	}

	public int getMeseNascita() {
		return meseNascita;
	}

	public void setMeseNascita(int meseNascita) {
		this.meseNascita = meseNascita;
	}

	public int getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(int annoNascita) {
		this.annoNascita = annoNascita;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	/**
	 * Implementazione di un possibile metodo toString() per la classe
	 */
	@Override
	public String toString() {
		return "Informazioni Inserite:"
			 + "\nCognome: " +this.cognome 
			 + "\nNome:" +this.nome 
			 + "\nSesso:" +this.sesso 
			 + "\nData di nascita: " +this.giornoNascita  +"/" +this.meseNascita +"/" +this.annoNascita
			 + "\nComune: " +this.comuneNascita
			 + "\nCodice Fiscale: " +this.codiceFiscale;
	}
	
	
}

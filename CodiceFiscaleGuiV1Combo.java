/**
 * @author - Ciro Francesco D'ELia
 * Corso TIC - 2021 
 */

package codiceFiscale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class CodiceFiscaleGuiV1Combo extends JFrame {
	
	private final String BTN_ACTION_CALCOLA = "BTN_CALCOLA";
	private final String BTN_ACTION_RESET = "BTN_RESET";
	private final Integer[] GIORNI = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,
										  21,22,23,24,25,25,27,28,29,30,31};
	private final Integer[] MESI = {1,2,3,4,5,6,7,8,9,10,11,12};
	private final String RB_M = "Maschile";
	private final String RB_F = "Femminile";
	
	private Integer[] anni;
	private JButton btnCalcola, btnReset;
	private JTextField txtCognome, txtNome, txtComune;
	private JTextField txtCodice;
	private JRadioButton rdSessoM, rdSessoF;
	private ButtonGroup grpSesso;
	private JComboBox<Integer> giorniCombo, mesiCombo, anniCombo;
	private JLabel lblCognome, lblNome, lblSesso, lblComune, lblData, lblCodice;
	private JPanel panelPrinc, panelWest, panelCenter, panelDown, panelRadioButton, panelData;
	
	private MoveFocus moveFocus;
	private MyListener myListener;
	private MyDocumentListener myDocumentListener;
	
	private boolean campiValidi = true;
	
	private CodiceFiscale codiceFiscale;
	
	/**
	 *  Costruttore che si occupa di inizializzare tutti i pannelli,
	 *  di disporli all'interno del pannallo principale ed impostare il Look and Feel.
	 *  Gestisce, inoltre, tutti i Listener dei componenti   
	 */
	public CodiceFiscaleGuiV1Combo() {
		
		super("Codice Fiscale - TIC");
		//setType(Type.UTILITY);
		
		// inizializzo i componenti dell'applicazione
		inizializzaComponent();
		
		// istanzio gli Event Listener
		instanziaEventListener();
		
		// costruisco i pannelli
		componiPanel();
		
		// istanzio l'oggetto dalla classe CodiceFiscale
		codiceFiscale = new CodiceFiscale();
		
		// controllo focus tra i JTextField 
		gestisciFocus();
		
		// Selezione tra le opzioni del RadioBtton
		rdSessoM.setSelected(true);
		rdSessoM.addActionListener(myListener);		
		rdSessoF.addActionListener(myListener);
		
		// Selezione tra le opzioni della ComboBox
		anniCombo.addActionListener(myListener);
		mesiCombo.addActionListener(myListener);
		anniCombo.addActionListener(myListener);
		
		// Gestione degli eventi per i JButton utilizzati
		btnCalcola.setActionCommand(BTN_ACTION_CALCOLA);
		btnCalcola.addActionListener(myListener);
		
		btnReset.setActionCommand(BTN_ACTION_RESET);
		btnReset.addActionListener(myListener);
		
		
		// impostazioni finali del Frame Principale
		setContentPane(panelPrinc);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		try {
		   	// impostazione dell'aspetto dell'applicazione
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			/*
            		Il metodo qui sotto forza l aggiornamento del
            		JFrame e di tutti i componenti grafici contenuti nel suo interno
            		*/
            		SwingUtilities.updateComponentTreeUI(this);
			pack();
        	} catch (Exception e) {
             		JOptionPane.showMessageDialog(null, e.getMessage() , "", JOptionPane.ERROR_MESSAGE);
        	}
		setVisible(true);
	}
	
	/**
	 * Metodo che si occupa di inizializzare tutti i componenti dell'applicazione
	 */
	private void inizializzaComponent() {
		
		// inizializzazione dei JButton per il calcolo ed il reset
		btnCalcola = new JButton("CALCOLA");
		btnCalcola.setEnabled(false);
		btnReset = new JButton("Resetta Campi");
		
		// inizializzazione del RadioButton
		rdSessoM = new JRadioButton(RB_M);
		rdSessoF = new JRadioButton(RB_F);
		grpSesso = new ButtonGroup();
		grpSesso.add(rdSessoM);
		grpSesso.add(rdSessoF);
		
		// inizializzazione di tutti i campi testo editabili e non
		txtCognome = new JTextField();
		txtNome = new JTextField();
		txtComune = new JTextField();
		
		// inizializzazione dei ComboBox della data di nascita
		Calendar dataCorrente = Calendar.getInstance();
		int annoCorrente = dataCorrente.get(Calendar.YEAR);
		this.anni = new Integer[(annoCorrente - 1910) +1];
		for (int i=1910; i<=annoCorrente; i++) {
			this.anni[i - 1910] = i;
		}
		giorniCombo = new JComboBox<>(GIORNI);
		mesiCombo = new JComboBox<>(MESI);
		anniCombo = new JComboBox<>(anni);
		
		txtCodice = new JTextField();
		txtCodice.setHorizontalAlignment(JTextField.CENTER);
		txtCodice.setFont(new Font(txtCodice.getFont().getFamily(), Font.BOLD, 15));
		txtCodice.setEditable(false);
		
		// inizializzazione di tutte le label
		lblCognome = new JLabel("Cognome:");
		lblNome = new JLabel("Nome:");
		lblData = new JLabel("Data di nascita:");
		lblSesso = new JLabel("Sesso:");
		lblComune = new JLabel("Comune di nascita:");
		lblCodice = new JLabel("Codice Fiscale:");

	}
	
	/**
	 * Metodo che istanzia gli oggetti degli Event Listener utilizzati nell'applicazione
	 */
	private void instanziaEventListener() {
		
		moveFocus = new MoveFocus();
		myListener = new MyListener();
		myDocumentListener = new MyDocumentListener();
	}
	
	/**
	 * Metodo che si occupa di inizializzare i pannelli dell'applicazione
	 * e collocarli all'interno del pannello pricipale (panelPrinc)
	 */
	private void componiPanel() {
		
		panelWest = new JPanel(new GridLayout(6, 1, 30, 5));
		panelCenter = new JPanel(new GridLayout(6, 1, 30, 5));
		panelRadioButton = new JPanel(new GridLayout(1, 2, 30, 5));
		panelData = new JPanel(new FlowLayout());
		panelDown = new JPanel(new GridLayout(1, 2, 30, 5));
		panelPrinc = new JPanel(new BorderLayout(10, 10));
		
		panelRadioButton.add(rdSessoM);
		panelRadioButton.add(rdSessoF);
		
		panelData.add(giorniCombo);
		panelData.add(new JLabel("gg"));
		panelData.add(mesiCombo);
		panelData.add(new JLabel("mm"));
		panelData.add(anniCombo);
		panelData.add(new JLabel("aaaa"));
		
		panelWest.add(lblCognome);
		panelWest.add(lblNome);
		panelWest.add(lblData);
		panelWest.add(lblSesso);
		panelWest.add(lblComune);
		panelWest.add(lblCodice);
		
		panelCenter.add(txtCognome);
		panelCenter.add(txtNome);
		panelCenter.add(panelData);
		panelCenter.add(panelRadioButton);
		panelCenter.add(txtComune);
		panelCenter.add(txtCodice);
		
		panelDown.add(btnCalcola);
		panelDown.add(btnReset);
		
		panelPrinc.add(panelWest, BorderLayout.WEST);
		panelPrinc.add(panelCenter, BorderLayout.CENTER);
		panelPrinc.add(panelDown, BorderLayout.SOUTH);
		
		TitledBorder titleBorder = new TitledBorder(new LineBorder(Color.BLACK), "CALCOLO CODICE FISCALE");
		titleBorder.setTitleJustification(TitledBorder.CENTER);
		panelPrinc.setBorder(titleBorder);
		
	}
	
	/**
	 * Metodo che gestisce il focus tra i vari campi di immissione del testo
	 * nell'applicazione
	 */
	private void gestisciFocus() {
		txtCognome.addKeyListener(moveFocus);
		txtNome.addKeyListener(moveFocus);

		giorniCombo.addKeyListener(moveFocus);
		mesiCombo.addKeyListener(moveFocus);
		anniCombo.addKeyListener(moveFocus);
		
		rdSessoM.addKeyListener(moveFocus);
		rdSessoF.addKeyListener(moveFocus);
		txtComune.addKeyListener(moveFocus);
		txtComune.getDocument().addDocumentListener(myDocumentListener);
	}
	
	/**
	 * classe interna che implementa i KeyListener dei componenti che la utilizzano
	 *
	 */
	class MoveFocus implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			
			
			if (e.getComponent().equals(txtCognome) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				txtNome.requestFocusInWindow();
				return;
			}
			if (e.getComponent().equals(txtNome) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				giorniCombo.requestFocusInWindow();
				return;
			}
			if (e.getComponent().equals(giorniCombo) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				mesiCombo.requestFocusInWindow();
				return;
			}
			if (e.getComponent().equals(mesiCombo) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				anniCombo.requestFocusInWindow();
				return;
			}
			if (e.getComponent().equals(anniCombo) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				rdSessoM.requestFocusInWindow();
				return;
			}
			if (e.getComponent().equals(rdSessoM) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				txtComune.requestFocusInWindow();
				return;
			}
			if (e.getComponent().equals(rdSessoF) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				txtComune.requestFocusInWindow();
				return;
			}
			if (e.getComponent().equals(txtComune) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				btnCalcola.requestFocusInWindow();
				return;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub			
		}		
	}
	
	/**
	 * classe interna che implementa ActionListener per i 2 JButton
	 * per il calcolo del Codice Fiscale e per il Reset dei campi di testo
	 */
	class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// gestione del pulsante RESET
			if (e.getActionCommand().equals(BTN_ACTION_RESET)) {
				resetBTN();
				return;
			}
			
			// gestione del pulsante CALCOLA
			if (e.getActionCommand().equals(BTN_ACTION_CALCOLA)) {
				calcolaBTN();
				return;				
			}			
		}
	}
	
	/**
	 * Metodo che svolge le operazioni quando si preme il tasto per resettare l'applicazione
	 */
	private void resetBTN() {
		
		// verifico se l'oggetto istanziato ha gia' valori inseriti
		// in questo caso resetto tutti i suoi valori
		if (!codiceFiscale.getCognome().equals("")) {
			codiceFiscale.reset();			
		}
		// pulizia dei JTextField dell'applicazione
		resetCampiGUI();
	}
	
	/**
	 * Metodo che svolge le operazioni quando si preme il tasto per il calcolo
	 * del Codice Fiscale e verifica la validita' delle informazioni immesse
	 */
	private void calcolaBTN() {
		if (!CheckCampiGUI.checkCognome(txtCognome.getText())) {
			campiValidi = false;
		}
			
		
		if (!CheckCampiGUI.checkNome(txtNome.getText())) {
			campiValidi = false;
		}
			
		// controllo della validita' della data di nascita inserita
		int giorno = 0;
		int mese = 0;
		int anno = 0;
		
		try {
			giorno = (Integer)giorniCombo.getSelectedItem();
			mese = (Integer)mesiCombo.getSelectedItem();
			anno = (Integer)anniCombo.getSelectedItem();
			
			if (!CheckCampiGUI.isDataValida(giorno, mese, anno)) {
				campiValidi = false;
			}
			
		}
		catch (NumberFormatException ex) {
			campiValidi = false;
		}
		
		if (campiValidi) {
			
			// caricamento delle informazioni inserite nell'oggetto
			// codice fiscale istanziato
			codiceFiscale.setCognome(txtCognome.getText());
			codiceFiscale.setNome(txtNome.getText());
			codiceFiscale.setGiornoNascita(giorno);
			codiceFiscale.setMeseNascita(mese);
			codiceFiscale.setAnnoNascita(anno);		
			if (rdSessoM.isSelected()) {
				codiceFiscale.setSesso('M');
			}
			else {
				codiceFiscale.setSesso('F');
			}
			codiceFiscale.setComuneNascita(txtComune.getText());
			
			// calcolo il codice fiscale
			if (codiceFiscale.calcolaCodiceFiscale()) {
				txtCodice.setText(codiceFiscale.getCodiceFiscale());
			}
			else {
				JOptionPane.showMessageDialog(null, "Calcolo del Codice Fiscale non riuscito", "Errore Calcolo Codice", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Valori inseriti non validi.\n Controlla le informazioni o premi 'Resetta Campi'", "Errore Dati", JOptionPane.ERROR_MESSAGE);
			campiValidi = true;					
		}
	}
	
	/**
	 * classe interna che implementa DocumentListner per il campo testo txtComune
	 * che abilita il JButton per il calcolo del Codice Fiscale quando viene
	 * inserito almeno un carattere
	 */
	class MyDocumentListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {

			btnCalcola.setEnabled(true);		
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub		
		}		
	}
	
	/**
	 * Metodo che resetta i valori che componenti JTextField e disabilita il 
	 * JButton pe il calcole del Codice Fiscale
	 */
	private void resetCampiGUI() {
		
		txtCognome.setText("");
		txtNome.setText("");
		giorniCombo.setSelectedIndex(0);
		mesiCombo.setSelectedIndex(0);
		anniCombo.setSelectedIndex(0);
		txtComune.setText("");
		txtCodice.setText("");
		txtCognome.requestFocusInWindow();
		
		btnCalcola.setEnabled(false);
		campiValidi = true;
	}
	
}

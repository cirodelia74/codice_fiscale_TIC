package it.codicefiscale.TIC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
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
public class CodiceFiscaleGuiV1 extends JFrame {
	
	private final String BTN_ACTION_CALCOLA = "BTN_CALCOLA";
	private final String BTN_ACTION_RESET = "BTN_RESET";
	private final String RB_M = "Maschile";
	private final String RB_F = "Femminile";
	
	private JButton btnCalcola, btnReset;
	private JTextField txtCognome, txtNome, txtComune;
	private JTextField txtGiorno, txtMese, txtAnno;
	private JTextField txtCodice;
	private JRadioButton rdSessoM, rdSessoF;
	private ButtonGroup grpSesso;
	private JLabel lblCognome, lblNome, lblSesso, lblComune, lblData, lblCodice;
	private JPanel panelPrinc, panelWest, panelCenter, panelDown, panelRadioButton, panelData;
	
	private boolean campiValidi = true;
	
	private CodiceFiscale codiceFiscale;
	
	public CodiceFiscaleGuiV1() {
		
		super("Codice Fiscale - TIC");
		//setType(Type.UTILITY);
	
		
		// inizializzo i componenti dell'applicazione
		inizializzaComponent();
		
		// costruisco i pannelli
		componiPanel();
		
		// istanzio l'oggetto dalla classe CodiceFiscale
		codiceFiscale = new CodiceFiscale();
		
		// controllo focus tra i JTextField
		txtCognome.addKeyListener(new MoveFocus());
		txtNome.addKeyListener(new MoveFocus());
		txtGiorno.addKeyListener(new MoveFocus());
		txtMese.addKeyListener(new MoveFocus());
		txtAnno.addKeyListener(new MoveFocus());
		rdSessoM.addKeyListener(new MoveFocus());
		rdSessoF.addKeyListener(new MoveFocus());
		txtComune.addKeyListener(new MoveFocus());
		txtComune.getDocument().addDocumentListener(new MyDocumentListener());
		
		// rdSessoM.setMnemonic(KeyEvent.VK_A);
		// rdSessoM.setActionCommand(RB_M);
		rdSessoM.setSelected(true);
		rdSessoM.addActionListener(new MyListener());
		
		// rdSessoF.setMnemonic(KeyEvent.VK_B);
		// rdSessoF.setActionCommand(RB_F);
		rdSessoF.addActionListener(new MyListener());
		
		btnCalcola.setActionCommand(BTN_ACTION_CALCOLA);
		btnCalcola.addActionListener(new MyListener());
		
		btnReset.setActionCommand(BTN_ACTION_RESET);
		btnReset.addActionListener(new MyListener());
		
		// impostazioni finali del Frame Principale
		setContentPane(panelPrinc);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		try {
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            /*
            	Il metodo qui sotto forza l aggiornamento del
            	JFrame e di tutti i componenti grafici contenuti nel suo interno
            */
            SwingUtilities.updateComponentTreeUI(this);

            pack();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage() , "", JOptionPane.ERROR_MESSAGE);
        }

		// pack();
		setVisible(true);
	
	}
	
	private void inizializzaComponent() {
		
		btnCalcola = new JButton("CALCOLA");
		btnCalcola.setEnabled(false);
		btnReset = new JButton("Resetta Campi");
		
		rdSessoM = new JRadioButton(RB_M);
		rdSessoF = new JRadioButton(RB_F);
		grpSesso = new ButtonGroup();
		grpSesso.add(rdSessoM);
		grpSesso.add(rdSessoF);
		
		txtCognome = new JTextField();
		txtNome = new JTextField();
		txtComune = new JTextField();
		txtGiorno = new JTextField(2);
		txtMese = new JTextField(2);
		txtAnno = new JTextField(4);
		txtCodice = new JTextField();
		txtCodice.setEditable(false);
		
		lblCognome = new JLabel("Cognome:");
		lblNome = new JLabel("Nome:");
		lblData = new JLabel("Data di nascita:");
		lblSesso = new JLabel("Sesso:");
		lblComune = new JLabel("Comune di nascita:");
		lblCodice = new JLabel("Codice Fiscale:");

	}
	
	private void componiPanel() {
		
		panelWest = new JPanel(new GridLayout(6, 1, 30, 5));
		panelCenter = new JPanel(new GridLayout(6, 1, 30, 5));
		panelRadioButton = new JPanel(new GridLayout(1, 2, 30, 5));
		panelData = new JPanel(new FlowLayout());
		panelDown = new JPanel(new GridLayout(1, 2, 30, 5));
		panelPrinc = new JPanel(new BorderLayout(10, 10));
		
		panelRadioButton.add(rdSessoM);
		panelRadioButton.add(rdSessoF);
		
		panelData.add(txtGiorno);
		panelData.add(new JLabel("gg  "));
		panelData.add(txtMese);
		panelData.add(new JLabel("mm  "));
		panelData.add(txtAnno);
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
	
	class MoveFocus implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			
			if (e.getComponent().equals(txtCognome) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				txtNome.requestFocusInWindow();
			}
			if (e.getComponent().equals(txtNome) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				txtGiorno.requestFocusInWindow();
			}
			if (e.getComponent().equals(txtGiorno) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				txtMese.requestFocusInWindow();
			}
			if (e.getComponent().equals(txtMese) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				txtAnno.requestFocusInWindow();
			}
			if (e.getComponent().equals(txtAnno) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				rdSessoM.requestFocusInWindow();
			}
			if (e.getComponent().equals(rdSessoM) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				txtComune.requestFocusInWindow();
			}
			if (e.getComponent().equals(rdSessoF) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				txtComune.requestFocusInWindow();
			}
			if (e.getComponent().equals(txtComune) && e.getKeyCode() == KeyEvent.VK_ENTER) {
				btnCalcola.requestFocusInWindow();
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
	
	private void resetCampiGUI() {
		
		txtCognome.setText("");
		txtNome.setText("");
		txtGiorno.setText("");
		txtMese.setText("");
		txtAnno.setText("");
		txtComune.setText("");
		txtCodice.setText("");
		txtCognome.requestFocusInWindow();
		
		btnCalcola.setEnabled(false);
		campiValidi = true;
	}
	
	class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// gestione del pulsante RESET
			if (e.getActionCommand().equals(BTN_ACTION_RESET)) {
				// resetto tutti i campi
				
				// verifico se l'oggetto istanziato ha gia' valori inseriti
				// in questo caso resetto tutti i suoi valori
				if (!codiceFiscale.getCognome().equals("")) {
					codiceFiscale.setCognome("");
					codiceFiscale.setNome("");
					codiceFiscale.setSesso(Character.MIN_VALUE);
					codiceFiscale.setGiornoNascita(0);
					codiceFiscale.setMeseNascita(0);
					codiceFiscale.setAnnoNascita(0);
					codiceFiscale.setComuneNascita("");
					codiceFiscale.setCodiceFiscale("");					
				}
				
				resetCampiGUI();

			}
			
			// gestione del pulsante CALCOLA
			if (e.getActionCommand().equals(BTN_ACTION_CALCOLA)) {
				
				if (!CheckCampiGUI.checkCognome(txtCognome.getText())) {
					campiValidi = false;
				}
					
				
				if (!CheckCampiGUI.checkNome(txtNome.getText())) {
					campiValidi = false;
				}
					
				// fare un controllo se ci sono valori inseriti
				int giorno = 0;
				int mese = 0;
				int anno = 0;
				
				try {
					giorno = Integer.parseInt(txtGiorno.getText());
					mese = Integer.parseInt(txtMese.getText());
					anno = Integer.parseInt(txtAnno.getText());
					
					if (!CheckCampiGUI.isDataValida(giorno, mese, anno)) {
						campiValidi = false;
					}
					
				}
				catch (NumberFormatException ex) {
					campiValidi = false;
				}
				
				if (campiValidi) {
					
					codiceFiscale.setCognome(txtCognome.getText());
					codiceFiscale.setNome(txtNome.getText());
					codiceFiscale.setGiornoNascita(giorno);
					codiceFiscale.setMeseNascita(mese);
					codiceFiscale.setAnnoNascita(anno);		
					if (rdSessoM.isSelected()) {
						codiceFiscale.setSesso('M');
					}
					
					if (rdSessoF.isSelected()) {
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
			
		}
		
	}
	
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

}

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

@SuppressWarnings("serial")
public class Visiteur extends JFrame{
	static String namePracticien;
	static String nameMedicament;
	static String motifVisiteur;
	static Integer nbrEchantillon;
	static Date dateRapport;
	static String bilanRapport;

	static JTextField saisieDate = new JTextField();
	static JTextField fieldMedicament = new JTextField();
	static JTextField fieldEchantillon = new JTextField();
	static JTextField fieldPracticien = new JTextField();
	static JTextArea bilan = new JTextArea();

	static String columns[] = { "Date", "Motif", "Medicament", "Nombre d'échantillon", "Practicien", "bilan" };
	static String data[][] = new String[30][6];

	static DefaultTableModel model = new DefaultTableModel(data, columns);
	static JTable table = new JTable(model);

	static DefaultTableModel modelSynthese = new DefaultTableModel(data, columns);
	static JTable tableSynthese = new JTable(modelSynthese);

	static JButton afficherBilan = new JButton();
	static JTextField fieldChoixAnnee = new JTextField();
	static Date choixDate;
	static String saisieChoixMotif;

	public Visiteur() {
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		Fonction.requeteInformationUtilisateur();

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Informations Utilisateur");
		
		JLabel prenomVisiteur = new JLabel(" Prénom : " + Fonction.prenomVisiteurBDD + " ");
		JLabel nomVisiteur = new JLabel(" Nom : " + Fonction.nomVisiteurBDD + " ");
		setTitle("Application GSB");
		
		JLabel statutVisiteur = new JLabel(" Statut : " + Fonction.statutUtilisateurBDD + " ");
		
		JLabel lbRegion = new JLabel("Region : "+Fonction.region);
		
		
		JSeparator separator = new JSeparator();
		JMenuItem deconnexion = new JMenuItem("Déconnexion");
		deconnexion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Fenetre_Connexion.start.setVisible(true);
				setVisible(false);
			}

		});
		menu.add(prenomVisiteur);   
		menu.add(nomVisiteur);   
		menu.add(statutVisiteur);
		menu.add(lbRegion); 
		menu.add(separator);
		menu.add(deconnexion);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		initComponant();

	}

	public void initComponant() {

		JPanel p1 = new JPanel();
		JPanel panelP1 = new JPanel();

		JPanel panelDate = new JPanel();
		JLabel labelDate = new JLabel("Date : ");
		saisieDate.setPreferredSize(new Dimension(70, 20));
		panelDate.add(labelDate);
		panelDate.add(saisieDate);
		panelP1.add(panelDate);

		JPanel panelMotif = new JPanel();
		JLabel labelMotif = new JLabel("Motif de la visite : ");
		String[] motif = {"Périodicité", "Nouveauté", "Remontage", "Information", "Autres"};
		JComboBox listeMotif = new JComboBox(motif);
		panelMotif.add(labelMotif);
		panelMotif.add(listeMotif);
		panelP1.add(panelMotif);

		JPanel panelMedicament = new JPanel();
		JLabel labelMedicament = new JLabel("Medicament Présenté : ");
		fieldMedicament.setPreferredSize(new Dimension(80, 20));
		JLabel Echantillon = new JLabel("Nombre d'échantillons : ");
		fieldEchantillon.setHorizontalAlignment(JTextField.CENTER); 
		fieldEchantillon.setPreferredSize(new Dimension(30, 20));
		panelMedicament.add(labelMedicament);
		panelMedicament.add(fieldMedicament);
		panelMedicament.add(Echantillon);
		panelMedicament.add(fieldEchantillon);
		panelP1.add(panelMedicament);

		JPanel panelPracticien = new JPanel();
		JLabel labelPracticien = new JLabel("Nom du Practicien : ");
		fieldPracticien.setPreferredSize(new Dimension(80, 20));
		panelPracticien.add(labelPracticien);
		panelPracticien.add(fieldPracticien);
		panelP1.add(panelPracticien);

		JPanel panelBoxLayout = new JPanel();
		panelBoxLayout.setLayout(new BoxLayout(panelBoxLayout, BoxLayout.Y_AXIS));
		JPanel panelBilan = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelBilan.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		JPanel encadreBilan = new JPanel();
		JLabel labelBilan = new JLabel("Bilan : ");
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		bilan.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		bilan.setLineWrap(true);
		bilan.setPreferredSize(new Dimension(800, 490));
		bilan.setSize(new Dimension(100, 100));

		JPanel panelBouton = new JPanel();
		JButton enregistrer = new JButton("Enregistrer");
		enregistrer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					DateFormat format  = new SimpleDateFormat("dd-MM-yyyy");
					java.util.Date dateUtil = format.parse(saisieDate.getText());
					dateRapport = new java.sql.Date(dateUtil.getTime());
				}
				catch (ParseException e) {
					e.printStackTrace();
				}
				motifVisiteur = (String) listeMotif.getSelectedItem();
				try {
					nbrEchantillon = Integer.parseInt(fieldEchantillon.getText());
				} 
				catch (NumberFormatException fe) {
					System.out.println(fe.getMessage());
				}
				namePracticien = fieldPracticien.getText();
				nameMedicament = fieldMedicament.getText();
				bilanRapport = bilan.getText();
				Fonction.requeteInsertBilan();
			}

		});
		encadreBilan.add(bilan);
		panelBilan.add(labelBilan);
		panelBilan.add(encadreBilan);
		panelBouton.add(enregistrer);
		panelBoxLayout.add(panelBilan);
		panelBoxLayout.add(panelBouton);

		p1.add(panelP1);
		p1.add(panelBoxLayout);

		JPanel p2 = new JPanel();
		JPanel panelTitle = new JPanel();
		JLabel title = new JLabel("Liste des rapports effectués dans les 3 dernières années");
		panelTitle.add(title);
		p2.add(panelTitle, BorderLayout.NORTH);

		JPanel panelTable = new JPanel();
		table.setShowGrid(true);
		table.setShowVerticalLines(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setEnabled(false);
		JScrollPane pane = new JScrollPane(table);
		pane.setPreferredSize(new Dimension(800, 503));
		panelTable.add(pane);
		p2.add(panelTable);

		JPanel p3 = new JPanel();

		JPanel panelPrincipalChoix = new JPanel();
		JPanel panelChoixAnnee = new JPanel();		
		JLabel labelChoixAnnee = new JLabel("Année : ");
		fieldChoixAnnee.setPreferredSize(new Dimension(40, 20));
		JButton boutonAfficherDate = new JButton("Afficher");
		boutonAfficherDate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				try {
					DateFormat format  = new SimpleDateFormat("yyyy");
					java.util.Date dateUtil = format.parse(fieldChoixAnnee.getText());
					choixDate = new java.sql.Date(dateUtil.getTime());
				}
				catch (ParseException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < tableSynthese.getRowCount(); i++) {
					for(int j = 0; j < tableSynthese.getColumnCount(); j++) {
						tableSynthese.setValueAt("", i, j);
					}
				}
				Fonction.requeteChoixAnnee();
			}

		});
		JPanel panelChoixMotif = new JPanel();
		JLabel labelChoixMotif = new JLabel("Motif : ");
		String[] choixMotif = {"Périodicité", "Nouveauté", "Remontage", "Information", "Autres"};
		JComboBox listeChoixMotif = new JComboBox(motif);
		JButton boutonAfficherMotif = new JButton("Afficher");
		boutonAfficherMotif.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				for (int i = 0; i < tableSynthese.getRowCount(); i++) {
					for(int j = 0; j < tableSynthese.getColumnCount(); j++) {
						tableSynthese.setValueAt("", i, j);
					}
				}
				saisieChoixMotif = (String) listeChoixMotif.getSelectedItem();
				Fonction.requeteChoixMotif();
			}

		});

		class ButtonRenderer extends JButton implements TableCellRenderer 
		{
			public ButtonRenderer() {
				setOpaque(true);
			}
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				setText((value == null) ? "Afficher le bilan" : value.toString());
				return this;
			}
		}
		class ButtonEditor extends DefaultCellEditor 
		{
			private String label;

			public ButtonEditor(JCheckBox checkBox)
			{
				super(checkBox);
			}
			public Component getTableCellEditorComponent(JTable table, Object value,
					boolean isSelected, int row, int column) 
			{
				label = (value == null) ? "Modifier" : value.toString();
				afficherBilan.setText(label);
				return afficherBilan;
			}
			public Object getCellEditorValue() 
			{
				return new String(label);
			}
		}

		tableSynthese.setShowGrid(true);
		tableSynthese.setShowVerticalLines(true);
		tableSynthese.getTableHeader().setReorderingAllowed(false);
		tableSynthese.setEnabled(false);
		


		JPanel panelTableSynthese = new JPanel();
		JScrollPane paneSynthese = new JScrollPane(tableSynthese);
		paneSynthese.setPreferredSize(new Dimension(800, 503));
		panelTableSynthese.add(paneSynthese);


		panelChoixAnnee.add(labelChoixAnnee);
		panelChoixAnnee.add(fieldChoixAnnee);
		panelChoixMotif.add(labelChoixMotif);
		panelChoixMotif.add(listeChoixMotif);
		panelPrincipalChoix.add(panelChoixAnnee);
		panelPrincipalChoix.add(boutonAfficherDate);
		panelPrincipalChoix.add(panelChoixMotif);
		panelPrincipalChoix.add(boutonAfficherMotif);

		p3.add(panelPrincipalChoix);
		p3.add(panelTableSynthese);


		JTabbedPane onglets = new JTabbedPane();
		onglets.add("Saisie des compte-rendu", p1);
		onglets.add("Consultation des données", p2);
		onglets.add("Synthèse du travail réalisé", p3);
		onglets.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Fonction.requeteSelectRapport();
			}
		});


		getContentPane().add(onglets);

	}
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.data.general.DefaultPieDataset;

//import javafx.scene.chart.PieChart;

@SuppressWarnings("serial")
public class Delegue extends JFrame{
	
	static String regionVisiteur;
	static DefaultTableModel modelRegion = new DefaultTableModel(Fonction.dataRegion, Fonction.columnsRegion);
    static JTable tableRegion = new JTable(modelRegion);
    @SuppressWarnings("rawtypes")
	static DefaultPieDataset stats = new DefaultPieDataset();
    static String tabRegion[]= {"Auvergne","Bourgogne", "Bretagne", "Centre-Val de Loire","Grand Est","Hauts de France", "Ile de France", "Normandie","Nouvelle Aquitaine","Occitanie","Pays de la Loire","Provences"};

    

	public Delegue() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Info Utilisateur");
		menuBar.add(mnNewMenu);
		
		JLabel lblNewLabel = new JLabel("Statut : Responsable");
		mnNewMenu.add(lblNewLabel);
		
		JLabel lblSession = new JLabel("Session : "+Fenetre_Connexion.log);
		mnNewMenu.add(lblSession);
		
		JLabel lbRegion = new JLabel("Region : "+Fonction.region);
		mnNewMenu.add(lbRegion);
		System.out.println(Fonction.region);
		
		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);
		
		JMenuItem mntmDconnexion = new JMenuItem("D\u00E9connexion");
		mnNewMenu.add(mntmDconnexion);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mntmDconnexion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Logout Return Login");
				Fenetre_Connexion.start.setVisible(true);
				setVisible(false);
			}
	});
		setSize(700, 400);
		setTitle("Delegue");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		initComponant();
		
		
	}
	
	public void initComponant() {
		
		// 1er onglet
        JPanel p1 = new JPanel();
        JPanel gridContenant = new JPanel();
        JLabel labelPrenom = new JLabel("Prénom : ");
        JLabel labelNom = new JLabel("Nom : ");
        JLabel labelRegion = new JLabel("Région de rattachement : ");
        Fonction.requeteInformationUtilisateur();
        JLabel prenomVisiteur = new JLabel(Fonction.prenomVisiteurBDD);
        JLabel nomVisiteur = new JLabel(Fonction.nomVisiteurBDD);
        JLabel statutUtilisateur = new JLabel(Fonction.statutUtilisateurBDD);
        JPanel panel = new JPanel();
               
      //liste
	    String[] region = {"Hauts de France", "Normandie", "Grand Est", "Ile de France", "Bretagne", "Pays de la Loire", "Centre-Val de Loire", "Bourgogne", "Auvergne", "Nouvelle Aquitaine", "Occitanie", "Provences"};
        JComboBox listeRegion = new JComboBox(region);
        listeRegion.setBounds(10, 10, 200, 50);
        JButton boutonRegion = new JButton("Rechercher"); 
        boutonRegion.addActionListener(new ActionListener() {
        	
        	@Override
            public void actionPerformed(ActionEvent arg0) {
        		for (int i = 0; i < tableRegion.getRowCount(); i++) {
                    for(int j = 0; j < tableRegion.getColumnCount(); j++) {
                    	tableRegion.setValueAt("", i, j);
                    }
                }
        		regionVisiteur = (String) listeRegion.getSelectedItem();
        		Fonction.requeteSelectRegion();
        		        	}        	
        });
        
        JPanel panelRegion = new JPanel();
		
	    tableRegion.setShowGrid(true);
	    tableRegion.setShowVerticalLines(true);
	    JScrollPane paneRegion = new JScrollPane(tableRegion);
          
        gridContenant.add(labelPrenom);
        gridContenant.add(prenomVisiteur);
        gridContenant.add(labelNom);
        gridContenant.add(nomVisiteur);
        
        p1.add(gridContenant);
        p1.add(panel);
        p1.add(listeRegion);
        p1.add(boutonRegion);
        p1.add(paneRegion);
        
        
        
        // 2eme onglet
        JPanel p2 = new JPanel();
        Fonction.tableauStats();
        JPanel panelTable = new JPanel();
		DefaultTableModel model = new DefaultTableModel(Fonction.data, Fonction.columns);
	    JTable table = new JTable(model);
	    table.setShowGrid(true);
	    table.setShowVerticalLines(true);
	    JScrollPane pane = new JScrollPane(table);
	    panelTable.add(pane);
	    
	    JButton btn = new JButton("Accéder aux Statistiques");
        btn.addActionListener(new ActionListener(){
        	 
    		public void actionPerformed(ActionEvent event){     
    			Fonction.statistique();
    			
                     }
    	  });        
        
        
		p2.add(panelTable);
        p2.add(btn);        
              
        JTabbedPane onglets = new JTabbedPane();
 
        onglets.add("Informations visiteurs par région", p1);
        onglets.add("Activité des employés", p2);

        getContentPane().add(onglets);

	}
}

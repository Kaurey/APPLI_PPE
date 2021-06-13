import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.swing.JTextArea;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class responsable {
	public static void responsablePanel() {
	JFrame  Frame_respon = new JFrame();
	
	Frame_respon.setTitle("Responsable");
	Frame_respon.setSize(900,400);
	Fenetre_Connexion.start.setVisible(false);
	Frame_respon.setVisible(true);
	Frame_respon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Frame_respon.setBounds(100, 100, 691, 451);
	
	JMenuBar menuBar = new JMenuBar();
	Frame_respon.setJMenuBar(menuBar);
	
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
			Frame_respon.setVisible(false);
		}
});
	JPanel header = new JPanel();
    //Créer le panneau 1
    JPanel p1 = new JPanel();
    //Ajouter la zone de texte dans le panneau 1
    //Créer le panneau 2
    JPanel p2 = new JPanel();
    // Créer le panneau 3
    JPanel p3 = new JPanel();
    //Créer le conteneur des onglets
    JTabbedPane onglets = new JTabbedPane();
    @SuppressWarnings("rawtypes")
	JComboBox liste = new JComboBox();
    JLabel data = new JLabel();
    //Définir la position de conteneur d'onglets
    onglets.setBounds(0,0,900,400);
    //Associer chaque panneau à l'onglet correspondant
    onglets.add("Visiteur", header);
    onglets.add("Activité", p2);
    onglets.add("Statistique",p3);
    
    
    //Ajouter les onglets au frame
    JLabel label = new JLabel("Liste des Visiteurs :");
    JLabel groupe = new JLabel("Liste des groupes :");
	JComboBox<String> listeGroupe = new JComboBox<String>();
	JButton searchGrp = new JButton("Groupe");
    Frame_respon.getContentPane().add(onglets);
    Frame_respon.setSize(900,400);
    Frame_respon.getContentPane().setLayout(null);
    Frame_respon.setVisible(true);
    
    header.add(label);
    header.add(data);
    JButton searchVisi = new JButton("Visiteur");
    searchVisi.setBounds(117, 32, 22, 21);
    header.add(liste);
    header.add(searchVisi);
    header.add(groupe);
    header.add(listeGroupe);
    header.add(searchGrp);
    header.add(p1);
/*---------------------------------------------GROUPE--------------------------------------------------------------------------*/  
	try {
	Connection conn = DriverManager.getConnection(Fonction.connectionUrl, Fonction.username, Fonction.password);//System.out.println("Connected To Server Successfully");
	Statement stmt = conn.createStatement();
	ResultSet visiteurlist = stmt.executeQuery("Select * from visiteur where statut='visiteur'");
	  while(visiteurlist.next()) {
		  String prenom = visiteurlist.getString("prenom");
		  String nom = visiteurlist.getString("nom");
		  String id = visiteurlist.getString("id");
		  liste.addItem(id+" "+nom+" "+prenom);
	  }	
	  ResultSet groupelist = stmt.executeQuery("SELECT `equipe`.`id_visiteur`, `equipe`.`id_groupe`, `groupe`.`departement_code`, `visiteur`.`nom`, `visiteur`.`prenom`"
	  			+ "FROM `equipe` "
	  			+ "LEFT JOIN `groupe` ON `equipe`.`id_groupe` = `groupe`.`id_groupe` "
	  			+ "LEFT JOIN `visiteur` ON `equipe`.`id_visiteur` = `visiteur`.`id` "
	  			+ "Where departement_code='"+Fonction.new_cp+"'");
	  	String id_groupe;
	  	ArrayList<String>stockGrp = new ArrayList<String>();
			while(groupelist.next()) {
			id_groupe = groupelist.getString("id_groupe");
			stockGrp.add(id_groupe);
			}
			Set<String>occuGrp = new HashSet<String>(stockGrp);
			List<String> listgrp = new ArrayList<String>(occuGrp);
			   for(int parcours = 0; parcours<occuGrp.size();parcours++) {
				   listeGroupe.addItem(listgrp.get(parcours));
		        	}  
			   
			   searchGrp.addActionListener(new ActionListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
				p1.removeAll();
				data.hide();
				p2.removeAll();
				data.setText((String)listeGroupe.getSelectedItem());
				Fenetre_Connexion.saisie = data.getText();
				System.out.println(Fenetre_Connexion.saisie+"\n"+"- - - - - - - - - - - -");
				try {
					tableurGroupe();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				public void tableurGroupe() throws SQLException{
					  String query1 = ("SELECT `equipe`.`id_visiteur`, `equipe`.`id_groupe`, `groupe`.`departement_code`, `visiteur`.`ville`,`visiteur`.`dateEmbauche`,`visiteur`.`nom`, `visiteur`.`prenom`"
					  			+ "FROM `equipe` "
					  			+ "LEFT JOIN `groupe` ON `equipe`.`id_groupe` = `groupe`.`id_groupe` "
					  			+ "LEFT JOIN `visiteur` ON `equipe`.`id_visiteur` = `visiteur`.`id` "
					  			+ "Where `equipe`.`id_groupe`='"+Fenetre_Connexion.saisie+"'");
				      ResultSet res = stmt.executeQuery(query1);
				      
				      String columns[] = {"id_visiteur" ,"nom", "prenom","dateEmbauche"};
				      String data[][] = new String[8][5];
				    
				      int i = 0;
				      while (res.next()) {
				        String id_visiteur = res.getString("id_visiteur");
				        String nom = res.getString("nom");
				        String prenom = res.getString("prenom");
				        String dateEmbauche = res.getString("dateEmbauche");
				        String ville = res.getString("ville");
				        data[i][0] = id_visiteur + "";
				        data[i][1] = nom;
				        data[i][2] = prenom;
				        data[i][3] = dateEmbauche;
				        data[i][4] = ville;
				        i++;
				      }
				      DefaultTableModel model = new DefaultTableModel(data,columns);
				      JTable table = new JTable(model);
				      table.setShowGrid(true);
				      table.setShowVerticalLines(true);
				      
				      JScrollPane pane = new JScrollPane(table);
				      pane.setPreferredSize(new Dimension(600, 150));
				      p1.add(pane);
				      String query = "SELECT `rapport_activite`.`rap_num`, `rapport_activite`.`rap_date`, `rapport_activite`.`rap_bilan`, `rapport_activite`.`rap_motif`, `praticien`.`pra_nom`, `praticien`.`pra_prenom` FROM `rapport_activite` LEFT JOIN `praticien` ON `rapport_activite`.`pra_code` = `praticien`.`pra_code` LEFT JOIN `visiteur` ON `rapport_activite`.`vis_matri` = `visiteur`.`id` WHERE `grp_id` = '"+Fenetre_Connexion.saisie+"' order by rap_date DESC";
					  ResultSet res1 = stmt.executeQuery(query);
				    
				      String columns1[] = {"date rapport","bilan rapport","motif rapport","nom praticien","prenom praticien"};
				      String data1[][] = new String[11][6];
				      String[] occu = new String [10];
			    	  List<String> stockGroup = new ArrayList<String>();
				      int nbQuery = 0; 
				      JComboBox<String> listActiGroupDate = new JComboBox<String>();
				      List<String>dataGraphique = new ArrayList<String>();
				      int i1 = 0;
				      while (res1.next()) {
					    nbQuery = res1.getRow();
				        
				        String rap_date = res1.getString("rap_date");
				        String rap_bilan = res1.getString("rap_bilan");
				        String rap_motif = res1.getString("rap_motif");
				        String pra_nom = res1.getString("pra_nom");
				        String pra_prenom = res1.getString("pra_prenom");
				       
				        data1[i1][0] = rap_date;
				        data1[i1][1] = rap_bilan;
				        data1[i1][2] = rap_motif;
				        data1[i1][3] = pra_nom;
				        data1[i1][4] = pra_prenom;
				        i1++;
				        dataGraphique.add(rap_bilan);
				      }
				      for(int parcours = 0; parcours<nbQuery;parcours++) {
				    	  	occu[parcours] = data1[parcours][0].substring(0,data1.length-4);
				    	  	stockGroup.add(occu[parcours]);
				        	}
				            Set<String> occuGrp = new HashSet<String>(stockGroup);
				       	    List<String> array_L2 = new ArrayList<String>(occuGrp);
				       	    for(int tabDate=0; tabDate<array_L2.size(); tabDate++) {
				       	    listActiGroupDate.addItem(array_L2.get(tabDate));
				       	    }

				      DefaultTableModel model1 = new DefaultTableModel(data1, columns1);
				      JTable table1 = new JTable(model1);
				      table1.setShowGrid(true);
				      table1.setShowVerticalLines(true);
				      JLabel selectDate = new JLabel("Liste date Activité :");
				      JLabel dateFiltre = new JLabel("Recherche :");
				      JTextArea areaSelectDate = new JTextArea(1, 10);
				      JScrollPane pane1 = new JScrollPane(table1);
				      pane1.setPreferredSize(new Dimension(600, 150));

				      TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table1.getModel());
				      table1.setRowSorter(rowSorter);
				      areaSelectDate.getDocument().addDocumentListener(new DocumentListener(){
				        @Override
				        public void insertUpdate(DocumentEvent e) {
				          String text = areaSelectDate.getText();

				          if (text.trim().length() == 0) {
				            rowSorter.setRowFilter(null);
				          } else {
				            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				          }
				        }

				        @Override
				        public void removeUpdate(DocumentEvent e) {
				          String text = areaSelectDate.getText();

				          if (text.trim().length() == 0) {
				            rowSorter.setRowFilter(null);
				          } else {
				            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				          }
				        }

				        @Override
				        public void changedUpdate(DocumentEvent e) {
				          throw new UnsupportedOperationException("Not supported yet.");
				        }

				      });	   
				      /*----- ONGLET --------2*/
				      p2.add(selectDate);
				      p2.add(listActiGroupDate);
				      p2.add(dateFiltre);
				      p2.add(areaSelectDate);
				      p2.add(pane1);
					 
				      
				      int nombre = 0;
				      nombre = listActiGroupDate.getItemCount();
				      JComboBox<String> dateGraphique = new JComboBox<String>();
				      dateGraphique.setBounds(131, 10, 77, 21);
				      JButton valideDateGrp = new JButton("Recherche");
				      valideDateGrp.setBounds(231, 10, 85, 21);
				      JLabel titreGraphGrp = new JLabel("Liste année des activités entreprient :");
				      JTextArea saisieDateGrp = new JTextArea(1, 20);
				      Set<String>anneeGrafGroupe = new HashSet<String>();
				      for(int nb =0;nb<nombre;nb++) {
				      anneeGrafGroupe.add(listActiGroupDate.getItemAt(nb).substring(0,4));
				      }
				      List<String>RecupDateGroupe = new ArrayList<String>(anneeGrafGroupe);
				      for(int tabDate=0; tabDate<RecupDateGroupe.size(); tabDate++) {
				       	    dateGraphique.addItem(RecupDateGroupe.get(tabDate));
				       	    }
				      if(anneeGrafGroupe.isEmpty()) {
				    	  System.out.println("no data stat");
				      }
				      else{
				      p3.add(titreGraphGrp);
				      p3.add(dateGraphique);
				      p3.add(saisieDateGrp);
				      p3.add(valideDateGrp);
				      }
				      valideDateGrp.addActionListener(new ActionListener(){
						@SuppressWarnings("deprecation")
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub	
							p3.removeAll();
						    p3.add(titreGraphGrp);
							p3.add(saisieDateGrp);
							p3.add(dateGraphique);
						    p3.add(valideDateGrp);
							 DefaultPieDataset<String> pieDataset = new DefaultPieDataset<String>(); 
							    JFreeChart pieChart = ChartFactory.createPieChart("Statistique des Activités en Camembert", pieDataset, true, true, true);
							    ChartPanel cPanel = new ChartPanel(pieChart); 
							    cPanel.setPreferredSize(new Dimension(380,280));
								DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
								JFreeChart lineChart = ChartFactory.createLineChart("Statistique des Activités en Graphique","Mois","Data",dataset, PlotOrientation.VERTICAL,true,true,false);  
								ChartPanel chartPanel = new ChartPanel( lineChart );
						
							saisieDateGrp.setText(dateGraphique.getSelectedItem().toString());
							Fenetre_Connexion.dateYearGrp = saisieDateGrp.getText();
						//    System.out.println(dateYearGrp);
						      String statGraphique = ("SELECT rap_date,rap_bilan,SUM(rap_bilan) as Total FROM `rapport_activite` WHERE grp_id='Groupe_1' && YEAR(rap_date) ='"+Fenetre_Connexion.dateYearGrp+"' group by Month(rap_date) ORDER BY rap_date,grp_id");
						      ResultSet restStat = null;
							try {
								restStat = stmt.executeQuery(statGraphique);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						      //String libelleGrp[] = {"rap_date","rap_bilan","Total"};
						      String dateGrp[][] = new String[11][6];
						      List<String>moisActiGrp = new ArrayList<String>();
						      List<String>TotalBilanGrp = new ArrayList<String>();
						      String concat = null;
						      int i1 =0 ;
						      try {
								while (restStat.next()) {
								    String rap_date = restStat.getString("rap_date");
								    String rap_bilan = restStat.getString("rap_bilan");
								    String Total = restStat.getString("Total");
								    dateGrp[i1][0] = rap_date;
								    dateGrp[i1][1] = rap_bilan;
								    dateGrp[i1][2] = Total;
								    concat = dateGrp[i1][0].substring(5,7);
								    moisActiGrp.add(concat);
								    TotalBilanGrp.add( dateGrp[i1][2]);
								    i1++;
									  System.out.println(Total);
									  System.out.println(concat);
								  }
								   System.out.println("- - - - - - - - - - - -");
								      	for(int info = 0;info<moisActiGrp.size();info++) {	
								      		pieDataset.setValue(moisActiGrp.get(info),new Integer(TotalBilanGrp.get(info)));
								      		//System.out.println(array_L2.get(info));
								      		//System.out.println(dataGraphique.get(info));
								      	}
									  for(int info = 0;info<moisActiGrp.size();info++) {	
										  //  dataset.addValue( 15 , "2021-03" , "1970" );
								      		int totalBilanInt =  Integer.parseInt(TotalBilanGrp.get(info));
								      		dataset.setValue(totalBilanInt,"Bilan du mois",moisActiGrp.get(info));
								      	}
								      chartPanel.setPreferredSize( new java.awt.Dimension( 380 , 280 ) );
								      
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						      p3.add(cPanel);
						      p3.add(chartPanel);
						}
						
				      });
				}
			});
			   searchGrp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					p3.removeAll();
				}
			   });
/*---------------------------------------------VISITEUR--------------------------------------------------------------------------*/  
		  searchVisi.addActionListener(new ActionListener() {
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			p1.removeAll();
			data.hide();
			p2.removeAll();
			p3.removeAll();
			data.setText((String) liste.getSelectedItem());
			Fenetre_Connexion.saisie = data.getText();
			//System.out.println(saisie);
			String[] tab = Fenetre_Connexion.saisie.split(" ");
		    int lenght = tab.length;
		    Fenetre_Connexion.searchByid=tab[0];
		    Fenetre_Connexion.searchBynom = tab[1];
		    Fenetre_Connexion.searchByprenom = tab[lenght-1];
		    System.out.println(Fenetre_Connexion.searchByid+"\n"+Fenetre_Connexion.searchBynom+"\n"+Fenetre_Connexion.searchByprenom);
			try {
				tableurVisiteur();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		public void tableurVisiteur() throws SQLException {
			  String query1 = "SELECT * FROM visiteur where nom='"+Fenetre_Connexion.searchBynom+"' && prenom='"+Fenetre_Connexion.searchByprenom+"'";
		      ResultSet res = stmt.executeQuery(query1);
		    
		      String columns[] = { "adresse", "ville", "cp","dateEmbauche","statut"};
		      String data[][] = new String[8][5];
		    
		      int i = 0;
		      while (res.next()) {
		        String adresse = res.getString("adresse");
		        String ville = res.getString("ville");
		        String cp = res.getString("cp");
		        String dateEmbauche = res.getString("dateEmbauche");
		        String statut = res.getString("statut");
		        data[i][0] = adresse + "";
		        data[i][1] = ville;
		        data[i][2] = cp;
		        data[i][3] = dateEmbauche;
		        data[i][4] = statut;
		        i++;
		      }
		      
		      DefaultTableModel model = new DefaultTableModel(data,columns);
		      JTable table = new JTable(model);
		      table.setShowGrid(true);
		      table.setShowVerticalLines(true);
		      
		      JScrollPane pane = new JScrollPane(table);
		      pane.setPreferredSize(new Dimension(600, 150));
		      p1.add(pane);
		      
			  String query = "SELECT `rapport_activite`.`rap_num`, `rapport_activite`.`rap_date`, `rapport_activite`.`rap_bilan`, `rapport_activite`.`rap_motif`, `praticien`.`pra_nom`, `praticien`.`pra_prenom` FROM `rapport_activite` LEFT JOIN `praticien` ON `rapport_activite`.`pra_code` = `praticien`.`pra_code` LEFT JOIN `visiteur` ON `rapport_activite`.`vis_matri` = `visiteur`.`id` WHERE `vis_matri` = '"+Fenetre_Connexion.searchByid+"'";
			  ResultSet res1 = stmt.executeQuery(query);
		    
		      String columns1[] = {"date rapport","bilan rapport","motif rapport","nom praticien","prenom praticien"};
		      String data1[][] = new String[11][6];
		      String[] occu = new String [10];
	    	  List<String> stockVi = new ArrayList<String>();
		      int nbQuery = 0; 
		      JComboBox<String> listActi = new JComboBox<String>();
		      int i1 = 0;
		      while (res1.next()) {
			    nbQuery = res1.getRow();
		        
		        String rap_date = res1.getString("rap_date");
		        String rap_bilan = res1.getString("rap_bilan");
		        String rap_motif = res1.getString("rap_motif");
		        String pra_nom = res1.getString("pra_nom");
		        String pra_prenom = res1.getString("pra_prenom");
		       
		        data1[i1][0] = rap_date;
		        data1[i1][1] = rap_bilan;
		        data1[i1][2] = rap_motif;
		        data1[i1][3] = pra_nom;
		        data1[i1][4] = pra_prenom;
		        i1++;
		      }
		      
		      for(int parcours = 0; parcours<nbQuery;parcours++) {
		    	  	occu[parcours] = data1[parcours][0].substring(0,data1.length-4);
		    	  	stockVi.add(occu[parcours]);
		        	}
		            Set<String> occuVi = new HashSet<String>(stockVi);
		       	    List<String> array_L2 = new ArrayList<String>(occuVi);
		       	    for(int tabDate=0; tabDate<array_L2.size(); tabDate++) {
		       	    listActi.addItem(array_L2.get(tabDate));
		       	    }
		       	    
		      System.out.println("- - - - - - - - - - - -");
		      DefaultTableModel model1 = new DefaultTableModel(data1, columns1);
		      JTable table1 = new JTable(model1);
		      table1.setShowGrid(true);
		      table1.setShowVerticalLines(true);
		      JLabel selectDate = new JLabel("Liste date Activité :");
		      JLabel dateFiltre = new JLabel("Recherche :");
		      JTextArea areaSelectDate = new JTextArea(1, 10);
		      JScrollPane pane1 = new JScrollPane(table1);
		      pane1.setPreferredSize(new Dimension(600, 150));

		      TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table1.getModel());
		      table1.setRowSorter(rowSorter);
		      areaSelectDate.getDocument().addDocumentListener(new DocumentListener(){

		        @Override
		        public void insertUpdate(DocumentEvent e) {
		          String text = areaSelectDate.getText();

		          if (text.trim().length() == 0) {
		            rowSorter.setRowFilter(null);
		          } else {
		            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
		          }
		        }

		        @Override
		        public void removeUpdate(DocumentEvent e) {
		          String text = areaSelectDate.getText();

		          if (text.trim().length() == 0) {
		            rowSorter.setRowFilter(null);
		          } else {
		            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
		          }
		        }

		        @Override
		        public void changedUpdate(DocumentEvent e) {
		          throw new UnsupportedOperationException("Not supported yet.");
		        }

		      });
		      
		      p2.add(selectDate);
		      p2.add(listActi);
		      p2.add(dateFiltre);
		      p2.add(areaSelectDate);
		      p2.add(pane1); 
/* Action effacer bouton Search*/
		      searchVisi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					p1.remove(pane);
					p2.remove(selectDate);
				    p2.remove(listActi);
				    p2.remove(dateFiltre);
				    p2.remove(areaSelectDate);
				    p2.remove(pane1);
				}
		      });
		      int nombre = 0;
		      nombre = listActi.getItemCount();
		      JComboBox<String> dateGraphique = new JComboBox();
		      dateGraphique.setBounds(131, 10, 77, 21);
		      JButton valideDateVisit = new JButton("Recherche");
		      valideDateVisit.setBounds(231, 10, 85, 21);
		      JLabel titreGraphVisit = new JLabel("Liste année des activités entreprient :");
		      JTextArea saisieDateVisit = new JTextArea(1, 20);
		      Set<String>anneeGrafVisit = new HashSet<String>();
		      for(int nb =0;nb<nombre;nb++) {
		    	  anneeGrafVisit.add(listActi.getItemAt(nb).substring(0,4));
		      }
		      List<String>RecupDateGroupe = new ArrayList<String>(anneeGrafVisit);
		      for(int tabDate=0; tabDate<RecupDateGroupe.size(); tabDate++) {
		       	    dateGraphique.addItem(RecupDateGroupe.get(tabDate));
		       	    }
		      if(anneeGrafVisit.isEmpty()) {
		    	  System.out.println("no data stat");
		      }
		      else{
		      p3.add(titreGraphVisit);
		      p3.add(dateGraphique);
		      p3.add(saisieDateVisit);
		      p3.add(valideDateVisit);
		      }
		      valideDateVisit.addActionListener(new ActionListener(){
				@SuppressWarnings("deprecation")
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub	
					p3.removeAll();
				    p3.add(titreGraphVisit);
					p3.add(saisieDateVisit);
					p3.add(dateGraphique);
				    p3.add(valideDateVisit);
					 DefaultPieDataset<String> pieDataset = new DefaultPieDataset<String>(); 
					    JFreeChart pieChart = ChartFactory.createPieChart("Statistique des Activités en Camembert", pieDataset, true, true, true);
					    ChartPanel cPanel = new ChartPanel(pieChart); 
					    cPanel.setPreferredSize(new Dimension(380,280));
						DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
						JFreeChart lineChart = ChartFactory.createLineChart("Statistique des Activités en Graphique","Mois","Data",dataset, PlotOrientation.VERTICAL,true,true,false);  
						ChartPanel chartPanel = new ChartPanel( lineChart );
				
					saisieDateVisit.setText(dateGraphique.getSelectedItem().toString());
					Fenetre_Connexion.dateYearGrp = saisieDateVisit.getText();
				//    System.out.println(dateYearGrp);
				      String statGraphique = ("SELECT rap_date,rap_bilan,SUM(rap_bilan) as Total FROM `rapport_activite` WHERE `vis_matri` = '"+Fenetre_Connexion.searchByid+"' && YEAR(rap_date) ='"+Fenetre_Connexion.dateYearGrp+"' group by Month(rap_date) ORDER BY rap_date");
				      ResultSet restStat = null;
					try {
						restStat = stmt.executeQuery(statGraphique);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				      //String libelleGrp[] = {"rap_date","rap_bilan","Total"};
				      String dateGrp[][] = new String[11][6];
				      List<String>moisActiGrp = new ArrayList<String>();
				      List<String>TotalBilanGrp = new ArrayList<String>();
				      String concat = null;
				      int i1 =0 ;
				      try {
						while (restStat.next()) {
						    String rap_date = restStat.getString("rap_date");
						    String rap_bilan = restStat.getString("rap_bilan");
						    String Total = restStat.getString("Total");
						    dateGrp[i1][0] = rap_date;
						    dateGrp[i1][1] = rap_bilan;
						    dateGrp[i1][2] = Total;
						    concat = dateGrp[i1][0].substring(5,7);
						    moisActiGrp.add(concat);
						    TotalBilanGrp.add( dateGrp[i1][2]);
						    i1++;
							  System.out.println(Total);
							  System.out.println(concat);
						  }
						   System.out.println("- - - - - - - - - - - -");
						      	for(int info = 0;info<moisActiGrp.size();info++) {	
						      		pieDataset.setValue(moisActiGrp.get(info),new Integer(TotalBilanGrp.get(info)));
						      		//System.out.println(array_L2.get(info));
						      		//System.out.println(dataGraphique.get(info));
						      	}
							  for(int info = 0;info<moisActiGrp.size();info++) {	
								  //  dataset.addValue( 15 , "2021-03" , "1970" );
						      		int totalBilanInt =  Integer.parseInt(TotalBilanGrp.get(info));
						      		dataset.setValue(totalBilanInt,"Bilan du mois",moisActiGrp.get(info));
						      	}
						      chartPanel.setPreferredSize( new java.awt.Dimension( 380 , 280 ) );
						      
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				      p3.add(cPanel);
				      p3.add(chartPanel);
				}
				
		      });
		 }
	});

	}catch (SQLException ex) {System.out.println(ex.getMessage());}
    p1.setVisible(true);
	}
}


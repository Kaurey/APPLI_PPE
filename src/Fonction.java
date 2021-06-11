import java.awt.Panel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

//import javafx.scene.chart.PieChart;

public class Fonction {
	/* Connexion Local
	static String username = "root"; 
	static String password = "";
	static String connectionUrl = "jdbc:mysql://localhost:3306/ppe2gsb";
	*/
	
	/* Connexion BDD EN LIGNE*/
	static String username = "232247_thococpas";	
	static String password = "CocoPascalThomas77ppe";
	static String connectionUrl = "jdbc:mysql://mysql-gsbthococpas.alwaysdata.net:3306/gsbthococpas_gsbv2";
	/*static String username = "kxmo5226_autres"; 
	static String password = "Autres@202021#1234IPSSILMJRT";
	static String connectionUrl = "jdbc:mysql://109.234.161.209:3306/kxmo5226_gsbvcooper";
	*/

	static String prenomVisiteurBDD;
	static String nomVisiteurBDD;
	static String statutUtilisateurBDD;
	
	static String region;
	static int new_cp;
	
	static String columns[] = {"Nom", "Prénom", "Adresse", "Ville", "Code Postal", "Nombre d'échantillons offert","Statut", "Région"};
	static String columnsRegion[] = {"Nom", "Prénom", "Adresse", "Ville", "Code Postal", "Nombre d'échantillons offert", "Région"};
	static String data[][] = new String [30][8];
	static String dataRegion[][] = new String [30][7];

	public static void Connection() {
/*
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");  //System.out.println("Driver Loaded"); 
		}  
		catch (ClassNotFoundException ex) { //System.out.println("Driver Failed To Load"); 
			System.out.println(ex.getMessage()); 
		} // TODO connexion
*/
		try { 
			Connection conn = DriverManager.getConnection(connectionUrl, username, password);
			System.out.println("Connected To Server Successfully");
			java.sql.Statement statement = conn.createStatement();
			ResultSet query = ((java.sql.Statement) statement).executeQuery("Select * from visiteur where login='"+ Fenetre_Connexion.log +"' && mdp='"+ Fenetre_Connexion.mdp +"'");
			while(query.next()) {
				String statut = query.getString("statut");
				String nom = query.getString("nom");
		    	String prenom = query.getString("prenom");
		    	String cp = query.getString("cp");
		    	region = cp.toString();
		    	String cut_cp = cp.substring(0,2);
		    	new_cp = Integer.parseInt(cut_cp);
		    	
				switch (statut) {
				case "visiteur":
					System.out.println("Session "+statut);
					Visiteur fenetre = new Visiteur();
					fenetre.setVisible(true);
					Fenetre_Connexion.start.setVisible(false);
					break;
					
				case "responsable":
					System.out.println("Session "+statut);
					responsable.responsablePanel();
					break;
					
				case "délégué":
					System.out.println("Session "+statut);
					Delegue fenetre2 = new Delegue();
					fenetre2.setVisible(true);
					Fenetre_Connexion.start.setVisible(false);
					break;
				}
			}
		}
		catch (SQLException ex) { 
			System.out.println(ex.getMessage()); 
		}
	}

	public static void requeteInformationUtilisateur() {
		try {
			Connection conn = DriverManager.getConnection(connectionUrl, username, password);
			Statement stmt = conn.createStatement();
			ResultSet query =  stmt.executeQuery("Select nom, prenom, statut from visiteur where login ='" + Fenetre_Connexion.log + "'");
			while(query.next()) {
				prenomVisiteurBDD = query.getString("prenom");
				nomVisiteurBDD = query.getString("nom");
				statutUtilisateurBDD = query.getString("statut");
			}

		}
		catch (SQLException ex) { 
			System.out.println(ex.getMessage()); 
		}
	}

	public static void statistique() { 
		
		try {
		
		String query = "select sum(echantillon_offert) from visiteur group by region";

		Connection con = DriverManager.getConnection(connectionUrl, username, password);

		Statement stm = con.createStatement();
		ResultSet res = stm.executeQuery(query);
		
		int i =0;
		
		while (res.next()) {
			int resRegion = res.getInt("sum(echantillon_offert)");	
			Delegue.stats.setValue(Delegue.tabRegion[i], resRegion);
			i++;
			
		}

		

		JFreeChart chart = ChartFactory.createPieChart("Plus grande donnation par région", Delegue.stats, true, true, true);		
		PiePlot p=(PiePlot)chart.getPlot();
		//p.setForegroundAlpha(alpha);
		ChartFrame frame = new ChartFrame("Statistiques", chart);
		frame.setVisible(true);
		frame.setSize(450,500);	
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

	public static void tableauStats() {

		try {

			String query = "select * from visiteur where statut != 'visiteur'";

			Connection con = DriverManager.getConnection(connectionUrl, username, password);

			Statement stm = con.createStatement();
			ResultSet res = stm.executeQuery(query);

			int i=0;
			while (res.next()) {
				String nom = res.getString("nom");
				String prenom = res.getString("prenom");
				String adresse = res.getString("adresse");
				String ville = res.getString("ville");
				String cp = res.getString("cp");
				String echantillon_offert = res.getString("echantillon_offert");
				String statut = res.getString("statut");
				String region = res.getString("region");

				data[i][0] = nom;
				data[i][1] = prenom;
				data[i][2] = adresse;
				data[i][3] = ville;
				data[i][4] = cp;
				data[i][5] = echantillon_offert;
				data[i][6] = statut;
				data[i][7] = region;
				i++;

			}



		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void requeteSelectRegion() {
		try {
			Connection conn = DriverManager.getConnection(connectionUrl, username, password);
			String requete = "Select * from visiteur where statut = 'visiteur' and region = ?";
			PreparedStatement prepare = conn.prepareStatement(requete);
			
			
			prepare.setString(1, Delegue.regionVisiteur);
			ResultSet result = prepare.executeQuery();
			
			int i=0;
			while (result.next()) {
				String nom = result.getString("nom");
				String prenom = result.getString("prenom");
				String adresse = result.getString("adresse");
				String ville = result.getString("ville");
				String cp = result.getString("cp");
				String echantillon_offert = result.getString("echantillon_offert");
				String region = result.getString("region");
				
				Delegue.tableRegion.setValueAt(nom, i, 0);
                Delegue.tableRegion.setValueAt(prenom, i, 1);
                Delegue.tableRegion.setValueAt(adresse, i, 2);
                Delegue.tableRegion.setValueAt(ville, i, 3);
                Delegue.tableRegion.setValueAt(cp, i, 4);
                Delegue.tableRegion.setValueAt(echantillon_offert, i, 5);
                Delegue.tableRegion.setValueAt(region, i, 6);
				
				i++;
			}
				prepare.close();
				result.close();
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void requeteChoixAnnee() {
		try {
			Connection conn = DriverManager.getConnection(connectionUrl, username, password);
			String requete = "select date, motif, m.libelle, nbrEchantillon, p.pra_nom, bilan\r\n" + 
					"from rapport_visite r\r\n" + 
					"join praticien p on r.idPracticien = p.pra_code\r\n" + 
					"join medicament m on r.idMedicament = m.numero\r\n" + 
					"where year(date) = year(?)  and idVisiteur = (select id from visiteur v where v.nom = ?)";
			PreparedStatement prepare = conn.prepareStatement(requete);
			prepare.setDate(1, Visiteur.choixDate);
			prepare.setString(2, nomVisiteurBDD);
			ResultSet result = prepare.executeQuery();
			int i = 0;
			while(result.next()) {
				Date date = result.getDate("date");
				String motif = result.getString("motif");
				String medicament = result.getString("m.libelle");
				Integer nbrEchantillon = result.getInt("nbrEchantillon");
				String praticien = result.getString("p.pra_nom");
				String bilan = result.getString("bilan");
				Visiteur.tableSynthese.setValueAt(date + "", i, 0);
				Visiteur.tableSynthese.setValueAt(motif, i, 1);
				Visiteur.tableSynthese.setValueAt(medicament, i, 2);
				Visiteur.tableSynthese.setValueAt(nbrEchantillon, i, 3);
				Visiteur.tableSynthese.setValueAt(praticien, i, 4);
				Visiteur.tableSynthese.setValueAt(bilan, i, 5);
				/*class ButtonRenderer extends JButton implements TableCellRenderer 
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
						Visiteur.afficherBilan.setText(label);
						return Visiteur.afficherBilan;
					}
					public Object getCellEditorValue() 
					{
						return new String(label);
					}
				}

				//Visiteur.tableSynthese.getModel().getValueAt(i, 5).setCellRenderer(new ButtonRenderer());
				//Visiteur.tableSynthese.getModel().getValueAt(i, 5).setCellEditor(new ButtonEditor(new JCheckBox());*/
				i++;
			}
			prepare.close();
			result.close();
		}
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}

	public static void requeteInsertBilan() {
		try {
			Connection conn = DriverManager.getConnection(connectionUrl, username, password);

			String requeteSelectNumeroMedicament = "SELECT numero FROM medicament WHERE libelle = '" + Visiteur.nameMedicament + "'";
			Integer numeroMedicament = 0;
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery(requeteSelectNumeroMedicament);
			result.next();
			numeroMedicament = result.getInt(1);
			result.close();
			state.close();

			String requeteSelectIdPracticien = "SELECT pra_code FROM praticien WHERE pra_nom = '" + Visiteur.namePracticien + "'";
			Integer idPracticien = 0;
			Statement state2 = conn.createStatement();
			ResultSet result2 = state2.executeQuery(requeteSelectIdPracticien);
			result2.next();
			idPracticien = result2.getInt(1);
			result2.close();
			state2.close();

			String requete;
			requete = "INSERT INTO rapport_visite(date, motif, nbrEchantillon, bilan, idPracticien, idMedicament, idVisiteur) select ?, ?, ?, ?, ?, ?, id from visiteur where nom = ?";
			PreparedStatement prepare = conn.prepareStatement(requete);
			prepare.setDate(1, Visiteur.dateRapport);
			prepare.setString(2, Visiteur.motifVisiteur);
			prepare.setInt(3, Visiteur.nbrEchantillon);
			prepare.setString(4, Visiteur.bilanRapport);
			prepare.setInt(5, idPracticien);
			prepare.setInt(6, numeroMedicament);
			prepare.setString(7, nomVisiteurBDD);
			prepare.executeUpdate();
			prepare.close();

			JFrame cadre = new JFrame();
			JOptionPane.showMessageDialog (cadre, "Le rapport a bien été inséré dans la base de données.");
			Visiteur.saisieDate.setText("");
			Visiteur.fieldMedicament.setText("");
			Visiteur.fieldEchantillon.setText("");
			Visiteur.fieldPracticien.setText("");
			Visiteur.bilan.setText("");
		}
		catch (SQLException ex) { 
			System.out.println(ex.getMessage());
			JFrame cadre = new JFrame();
			JOptionPane.showMessageDialog (cadre, "Informations Non Valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void requeteChoixMotif() {
		try {
			Connection conn = DriverManager.getConnection(connectionUrl, username, password);
			String requete = "select date, motif, m.libelle, nbrEchantillon, p.pra_nom, bilan\r\n" + 
					"from rapport_visite r\r\n" + 
					"join praticien p on r.idPracticien = p.pra_code\r\n" + 
					"join medicament m on r.idMedicament = m.numero\r\n" + 
					"where motif = ?  and idVisiteur = (select id from visiteur v where v.nom = ?)";
			PreparedStatement prepare = conn.prepareStatement(requete);
			prepare.setString(1, Visiteur.saisieChoixMotif);
			prepare.setString(2, nomVisiteurBDD);
			ResultSet result = prepare.executeQuery();
			int i = 0;
			while(result.next()) {
				Date date = result.getDate("date");
				String motif = result.getString("motif");
				String medicament = result.getString("m.libelle");
				Integer nbrEchantillon = result.getInt("nbrEchantillon");
				String praticien = result.getString("p.pra_nom");
				String bilan = result.getString("bilan");
				Visiteur.tableSynthese.setValueAt(date + "", i, 0);
				Visiteur.tableSynthese.setValueAt(motif, i, 1);
				Visiteur.tableSynthese.setValueAt(medicament, i, 2);
				Visiteur.tableSynthese.setValueAt(nbrEchantillon, i, 3);
				Visiteur.tableSynthese.setValueAt(praticien, i, 4);
				Visiteur.tableSynthese.setValueAt(bilan, i, 5);
				i++;
			}
			prepare.close();
			result.close();
		}
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}

	
	public static void requeteSelectRapport() {
		try {
			Connection conn = DriverManager.getConnection(connectionUrl, username, password);
			String requete = "select date, motif, m.libelle, nbrEchantillon, p.pra_nom, bilan\r\n" + 
					"from rapport_visite r\r\n" + 
					"join praticien p on r.idPracticien = p.pra_code\r\n" + 
					"join medicament m on r.idMedicament = m.numero\r\n" + 
					"where (year(CURRENT_DATE) - year(date)) <= 3 and idVisiteur = (select id from visiteur v where v.nom = ?)";
			PreparedStatement prepare = conn.prepareStatement(requete);
			prepare.setString(1, nomVisiteurBDD);
			ResultSet result = prepare.executeQuery();
			;
			int i = 0;
			while(result.next()) {
				Date date = result.getDate("date");
				String motif = result.getString("motif");
				String medicament = result.getString("m.libelle");
				Integer nbrEchantillon = result.getInt("nbrEchantillon");
				String praticien = result.getString("p.pra_nom");
				String bilan = result.getString("bilan");
				Visiteur.table.setValueAt(date + "", i, 0);
				Visiteur.table.setValueAt(motif, i, 1);
				Visiteur.table.setValueAt(medicament, i, 2);
				Visiteur.table.setValueAt(nbrEchantillon, i, 3);
				Visiteur.table.setValueAt(praticien, i, 4);
				Visiteur.table.setValueAt(bilan, i, 5);
				i++;
			}
			prepare.close();
			result.close();
		}
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}

}

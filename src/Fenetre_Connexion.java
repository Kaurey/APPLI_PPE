import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JTextField;


import java.awt.FlowLayout;


public class Fenetre_Connexion extends JFrame {
	private JTextField saisieLogin = new JTextField();
	private JTextField saisieMdp = new JTextField();
	private static String log;
	private static String mdp;

	private static JFrame start = new Fenetre_Connexion(); 


	public Fenetre_Connexion() {
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		//Connection();
		initComponant();
	}

	public void initComponant() {
		JPanel panelTitre = new JPanel();
		JPanel panelCentral = new JPanel(new GridLayout(2, 0));

		JPanel panelConnexion = new JPanel();
		JPanel panel = new JPanel();
		JPanel panelElementConnexionPlusBouton = new JPanel();
		panelElementConnexionPlusBouton.setLayout(new BoxLayout(panelElementConnexionPlusBouton, BoxLayout.Y_AXIS));
		panelElementConnexionPlusBouton.setPreferredSize(new Dimension(200, 100));
		JPanel panelElementConnexion = new JPanel(new GridLayout(2, 2));
		JPanel panelBouton = new JPanel();
		JButton boutonValider = new JButton("Valider");
		boutonValider.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				log = saisieLogin.getText();
				mdp = saisieMdp.getText();
				Connection();
			}
		});
		
		JButton boutonAnnuler = new JButton("Annuler");
		boutonAnnuler.addActionListener(new effacerJTextField());
		panelBouton.add(boutonValider);
		panelBouton.add(boutonAnnuler);

		JLabel titre = new JLabel("Identification d'utilisateur");
		panelTitre.add(titre);
		getContentPane().add(panelTitre, BorderLayout.NORTH);

		String imgUrl = "image/logo.jpg";
		ImageIcon logo = new ImageIcon(imgUrl);
		JLabel logoTitre = new JLabel(logo, JLabel.CENTER);
		panelCentral.add(logoTitre);

		JLabel login = new JLabel("Login : ");
		JLabel mdp = new JLabel("Mot de passe : ");

		panelElementConnexion.add(login);
		panelElementConnexion.add(saisieLogin);
		panelElementConnexion.add(mdp);
		panelElementConnexion.add(saisieMdp);

		panel.add(panelElementConnexion);
		panelElementConnexionPlusBouton.add(panel);
		panelElementConnexionPlusBouton.add(panelBouton);
		panelConnexion.add(panelElementConnexionPlusBouton);
		panelCentral.add(panelConnexion);

		getContentPane().add(panelCentral);

	}

	public static void Connection() {
		String username = "root"; 
		String password = "";
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");  //System.out.println("Driver Loaded"); 
		}  
		catch (ClassNotFoundException ex) { //System.out.println("Driver Failed To Load"); 
			System.out.println(ex.getMessage()); 
		} // TODO connexion
		String connectionUrl = "jdbc:mysql://127.0.0.1:3306/gsbv2.2";
		try { 
			Connection conn = DriverManager.getConnection(connectionUrl, username, password);
			System.out.println("Connected To Server Successfully");
			java.sql.Statement statement = conn.createStatement();
			ResultSet query = ((java.sql.Statement) statement).executeQuery("Select * from visiteur where login='"+ log +"' && mdp='"+ mdp +"'");
			while(query.next()) {
				String statut = query.getString("statut");
				switch (statut) {
				case "visiteur":
					System.out.println("Session "+statut);
					Visiteur fenetre = new Visiteur();
					start.setVisible(false);
					
					break;
				case "responsable":
					System.out.println("Session "+statut);
					Responsable();

					break;
				case "délégué":
					System.out.println("Session "+statut);

					break;
				}
			}
		}
		catch (SQLException ex) { 
			System.out.println(ex.getMessage()); 
		}
	}

	public static void Responsable() {
		JFrame  Frame_respon = new JFrame();
		Frame_respon.setTitle("Responsable");
		Frame_respon.setSize(500,500);
		start.setVisible(false);
		Frame_respon.setVisible(true);
		Frame_respon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame_respon.setBounds(100, 100, 613, 379);

	}
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					start.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	class effacerJTextField implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			saisieLogin.setText("");
			saisieMdp.setText("");	
		}	
	}

}

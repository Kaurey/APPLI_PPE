import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class Fenetre_Connexion extends JFrame {
	static JTextField saisieLogin = new JTextField();
	static JPasswordField pass = new JPasswordField();
	static String log;
	static String mdp;
	/* Alwaysdata BDD */
	/*
	static String username = "232247_thococpas";	
	static String password = "CocoPascalThomas77ppe";
	static String connectionUrl = "jdbc:mysql://mysql-gsbthococpas.alwaysdata.net:3306/gsbthococpas_gsbv2";
	*/
	/* Local*/
	static String username = "root"; 
	static String password = "";
	static String connectionUrl = "jdbc:mysql://127.0.0.1:3306/ppe2gsb";
	@SuppressWarnings("unused")
	private JPanel contentPane;
	static JPasswordField motdepasse;
	static JTextField login;
	@SuppressWarnings("unused")
	private JLabel reponse_login;
	static String recherche;
	static String data;
	static String saisie;
	static int new_cp;
	static String searchByid;
	static String searchBynom;
	static String searchByprenom;
	static String region;
	static String dateYearGrp;

	static JFrame start = new Fenetre_Connexion(); 


	public Fenetre_Connexion() {
		setTitle("Application GSB");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
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
				mdp = pass.getText();
				Fonction.Connection();
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
		panelElementConnexion.add(pass);

		panel.add(panelElementConnexion);
		panelElementConnexionPlusBouton.add(panel);
		panelElementConnexionPlusBouton.add(panelBouton);
		panelConnexion.add(panelElementConnexionPlusBouton);
		panelCentral.add(panelConnexion);

		getContentPane().add(panelCentral);

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
			pass.setText("");	
		}	
	}

}

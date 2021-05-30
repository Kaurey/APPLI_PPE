import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Gsb_Panel extends JFrame {
	/*
	Connexion AlwaysData BDD
	static String username = "232247_thococpas"; 
    static String password = "CocoPascalThomas77ppe";
    static String connectionUrl = "jdbc:mysql://mysql-gsbthococpas.alwaysdata.net:3306/gsbthococpas_gsbv2";
    */
	static String username = "root"; 
	static String password = "";
	static String connectionUrl = "jdbc:mysql://127.0.0.1:3306/ppe2gsb";
	private JPanel contentPane;
	static JPasswordField motdepasse;
	static JTextField login;
	private JLabel reponse_login;
	static String log;
	static String mdp;
	static String recherche;
	static String data;
	static String saisie;
	static int new_cp;
	static String searchByid;
	static String searchBynom;
	static String searchByprenom;
	static String region;
	static String dateYearGrp;
	static JFrame start = new Gsb_Panel(); 

	/*
	 * Launch the application.
	 */
	public static  void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public  void run() {
				try {
					 start.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gsb_Panel() {
		String defaultTitle =  (" GSB Page Connexion ");
		JLabel erreur = new JLabel(" [Tentative Connexion]");
		setTitle(defaultTitle);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 691, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Connexion");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				log = login.getText();
				mdp = motdepasse.getText();
				if(log!=login.getText()) {
					 Timer timer = new Timer(1000, null);
					    try {
					    timer.start();
						setTitle(defaultTitle+erreur.getText());
					    Thread.sleep(1000);
					    	}
					    catch (InterruptedException TimeErreur) {
					    }
					    timer.stop();
						setTitle(defaultTitle);
					}
				passe();
			}
		});
		btnNewButton.setBounds(227, 181, 212, 42);
		contentPane.add(btnNewButton);
		
		motdepasse = new JPasswordField();
		motdepasse.setBounds(227, 152, 212, 19);
		contentPane.add(motdepasse);
		motdepasse.setColumns(10);
		key_enter enter = new key_enter();
		motdepasse.addKeyListener(enter);
		
		login = new JTextField();
		login.setBounds(227, 107, 212, 19);
		contentPane.add(login);
		login.setColumns(10);
		login.addKeyListener(enter);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.LEFT);
		lblLogin.setBounds(227, 90, 212, 13);
		contentPane.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setBounds(227, 137, 212, 13);
		contentPane.add(lblPassword);
		
		reponse_login = new JLabel();
		reponse_login.setBounds(307, 210, 132, 13);
		contentPane.add(reponse_login);
	
		JLabel lien = new JLabel("Lien Internet");
		lien.setForeground(new Color(123, 104, 238));
		lien.setHorizontalAlignment(SwingConstants.CENTER);
		lien.setBounds(227, 259, 212, 13);
		contentPane.add(lien);
		
		lien.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				 try
				    {
				        Desktop.getDesktop().browse(new URI("https://gsbv2.000webhostapp.com/index.php"));
				    } 
				    catch (URISyntaxException | IOException error) 
				    {
				        error.printStackTrace();
				    }
			}
			@Override public void mousePressed(MouseEvent e) {}@Override public void mouseReleased(MouseEvent e) {}@Override public void mouseEntered(MouseEvent e) {}@Override public void mouseExited(MouseEvent e) {}			
		});
	}
	
	public static void Connection() {
		try { 
		Class.forName("com.mysql.cj.jdbc.Driver");  //System.out.println("Driver Loaded"); 
		}  
		catch (ClassNotFoundException ex) { //System.out.println("Driver Failed To Load"); 
		System.out.println(ex.getMessage()); 
		} // TODO connexion
		//String connectionUrl = "jdbc:mysql://127.0.0.1:3306/gsbv2.2";
	}
	
	public static void passe() {
	//	Connection();
		try { 
		Connection conn = DriverManager.getConnection(connectionUrl, username, password);//System.out.println("Connected To Server Successfully");
		Statement stmt = conn.createStatement();
		ResultSet query = stmt.executeQuery("Select * from visiteur where login='"+log+"' && mdp='"+mdp+"'");
	     while(query.next()) {
	    	 String nom = query.getString("nom");
	    	 String prenom = query.getString("prenom");
	    	 String statut = query.getString("statut");
	    	 String cp = query.getString("cp");
	    	 region = cp.toString();
	    	 String cut_cp = cp.substring(0,2);
	    	 new_cp = Integer.parseInt(cut_cp);
	    	 //String login = query.getString("login");
	    	 //String mdp = query.getString("mdp");

	    	 switch (statut) {
	    	 	case "visiteur":
	    	 		System.out.println("Session "+statut);
	    	 		/* Corentin */
					
	    	 		break;
	    	 	case "responsable":
	    	 		/* Pascal */
	    	 		System.out.println("Session "+statut+":");
	    	 		System.out.println(nom+" "+prenom+" "+cut_cp+" Login");
	    	 		System.out.println("- - - - - - - - - - - -");
	    	 		responsable.responsablePanel();
	    	 	
	    	 		break;
	    	 	case "délégué":
	    	 		System.out.println("Session "+statut);
	    	 		/* Thomas */
	    	 		break; 
	     		}
			}
		}
		catch (SQLException ex) { 
		System.out.println(ex.getMessage()); 
			}
	}
}
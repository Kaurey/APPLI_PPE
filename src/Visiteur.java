import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Visiteur extends JFrame{

	public Visiteur() {
		setSize(600, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		initComponant();
	}
	
	public void initComponant() {
		
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JTabbedPane onglets = new JTabbedPane();
 
        onglets.add("Saisie des compte-rendu", p1);
        onglets.add("Consultation des données", p2);
        onglets.add("Synthèse du travail réalisé", p3);
        
        getContentPane().add(onglets);

	}
	
	/*public static void main(String[] args) {
		Visiteur frame = new Visiteur();
		frame.setVisible(true);
	}*/
}

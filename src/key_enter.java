import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class key_enter implements KeyListener {
	  public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode()==KeyEvent.VK_ENTER){
		    	Gsb_Panel.log = Gsb_Panel.login.getText();
		    	Gsb_Panel.mdp = Gsb_Panel.motdepasse.getText();
		    	Gsb_Panel.passe();
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
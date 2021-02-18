import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class key_enter implements KeyListener {
	  public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode()==KeyEvent.VK_ENTER){
		    System.out.println("ok");
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
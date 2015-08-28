package bot;
import javax.swing.SwingUtilities;


public class DiamondHunter {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainFrame()); /* Lambda Java 8 version shortcutting an anonymous class, can leave out block statement due to single line expression */
	}

}

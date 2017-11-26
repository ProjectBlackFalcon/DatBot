package Main;

import java.awt.Dimension;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import Game.InfoAccount;
import Main.Frame.TextComponentFrame;

public class MainPlugin extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static TextComponentFrame frame;

	public MainPlugin() {
	}

	@Override
	public void run() {
		frame = new TextComponentFrame();
		frame.setLocation(50, 25);
		frame.setPreferredSize(new Dimension(450, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.append("Connection...");

		while (!InfoAccount.isConnected) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		frame.append("Connecté !");
		frame.append("Name : " + InfoAccount.name);
		frame.append("Niveau : " + InfoAccount.lvl);
	}
}
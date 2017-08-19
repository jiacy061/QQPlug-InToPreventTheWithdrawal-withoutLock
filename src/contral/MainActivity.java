package contral;

import java.awt.EventQueue;


public class MainActivity {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainContral();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

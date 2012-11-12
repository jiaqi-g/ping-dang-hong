package game;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		UI ui = new UI();

		ui.setTitle("AI Game");
		ui.setBounds(400,200,600,400);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setVisible(true);
		
	}
}

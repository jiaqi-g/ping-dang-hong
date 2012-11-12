package game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class UI extends JFrame implements GameConstants {
	
	boolean isProfessional = false;
	
	final int span = 10;
	
	final String playerMP_String = "玩家体力值";
	final String computerMP_String = "电脑体力值";
	final String start_String = "开始游戏";
	final String defend_String = "防守";
	final String raise_String = "补充体力";
	final String status_String = "赢次数  / 总次数";
	final String win_String = "您赢了！  \\(^o^)/";
	final String lose_String = "您输了！  ::>_<::";
	
	final String one_String = "草莓~1~";
	final String two_String = "橘子*2*";
	final String three_String = "菠萝$3$";
	final String four_String = "西瓜!4!";
	
	GameSystem system = new GameSystem();
	
	JLabel computerMPLabel = new JLabel("0");
	JLabel playerMPLabel = new JLabel("0");
	JLabel currentLabel = new JLabel(""); //standard

	JLabel computerStatus = new JLabel("Undefined");	
	JLabel playerStatus = new JLabel("Undefined");
	JLabel percentage = new JLabel("0/0");
	
	JButton startButton = new JButton(start_String);
	JButton defendButton = new JButton(defend_String);
	JButton raiseButton = new JButton(raise_String);
	//JButton oneButton = new JButton("<html><font color=grey></font> <br> <font color=grey></font></html>");
	
	JButton oneButton = new JButton("");
	JButton twoButton = new JButton("");
	JButton threeButton = new JButton("");
	JButton fourButton = new JButton("");
	
	JTextArea jtaLog = new JTextArea();
	JScrollPane scrollPane = new JScrollPane(jtaLog); //professional
	
	int size = 10;
	
	Timer timerWin = new Timer(span, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			size++;
			Font font = new Font("宋体",Font.PLAIN, size);
			currentLabel.setForeground(Color.GREEN);
			currentLabel.setFont(font);
			currentLabel.setText("胜!");
			if (size == 80) {
				timerWin.stop();
				size = 10;
			}
		}
	});
	
	Timer timerLose = new Timer(span, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			size++;
			Font font = new Font("宋体",Font.PLAIN, size);
			currentLabel.setForeground(Color.RED);
			currentLabel.setFont(font);
			currentLabel.setText("负!");
			if (size == 80) {
				timerLose.stop();
				size = 10;
			}
		}
	});
	
	Timer timerDraw = new Timer(span, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			size++;
			Font font = new Font("宋体",Font.PLAIN, size);
			currentLabel.setForeground(Color.BLACK);
			currentLabel.setFont(font);
			currentLabel.setText("平!");
			if (size == 80) {
				timerDraw.stop();
				size = 10;
			}
		}
	});
	
	int playerMP;
	int computerMP;
	
	int winTime = 0;
	int allTime = 0;
	int sum = 0;
	
	public UI() {
		
		ImageIcon oneIcon = new ImageIcon("one.jpg");
		oneIcon.setImage(oneIcon.getImage().getScaledInstance(20,30,Image.SCALE_DEFAULT));
		oneButton.setIcon(oneIcon);
		
		ImageIcon twoIcon = new ImageIcon("two.jpg");
		twoIcon.setImage(twoIcon.getImage().getScaledInstance(40,60,Image.SCALE_DEFAULT));
		twoButton.setIcon(twoIcon);
		
		ImageIcon threeIcon = new ImageIcon("three.jpg");
		threeIcon.setImage(threeIcon.getImage().getScaledInstance(60,90,Image.SCALE_DEFAULT));
		threeButton.setIcon(threeIcon);
		
		ImageIcon fourIcon = new ImageIcon("four.jpg");
		fourIcon.setImage(fourIcon.getImage().getScaledInstance(80,120,Image.SCALE_DEFAULT));
		fourButton.setIcon(fourIcon);
				
		setLayout(new GridLayout(3,2));
		
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1,3));
		p1.add(new JLabel(computerMP_String));
		p1.add(computerMPLabel);
		p1.add(computerStatus);
		add(p1);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(new JLabel("Status"), BorderLayout.NORTH);
		if (isProfessional) p2.add(scrollPane, BorderLayout.CENTER); else p2.add(currentLabel, BorderLayout.CENTER);
		jtaLog.setEditable(false);
		add(p2);
		
		JPanel p3 = new JPanel();
		p3.setLayout(new GridLayout(1,3));
		p3.add(new JLabel(playerMP_String));
		p3.add(playerMPLabel);
		p3.add(playerStatus);
		add(p3);
		
		JPanel p4 = new JPanel();
		p4.setLayout(new GridLayout(1,2));
		p4.add(new JLabel(status_String));
		p4.add(percentage);
		add(p4);
		
		JPanel p5 = new JPanel();
		p5.setLayout(new GridLayout(1,3));
		p5.add(startButton);
		p5.add(defendButton);defendButton.setEnabled(false);
		p5.add(raiseButton);raiseButton.setEnabled(false);
		add(p5);
		
		JPanel p6 = new JPanel();
		p6.setLayout(new GridLayout(1,4));
		p6.add(oneButton);oneButton.setEnabled(false);
		p6.add(twoButton);twoButton.setEnabled(false);
		p6.add(threeButton);threeButton.setEnabled(false);
		p6.add(fourButton);fourButton.setEnabled(false);
		add(p6);
		
		addListeners();
		
	}

	private void addListeners() {
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				defendButton.setEnabled(true);
				raiseButton.setEnabled(true);
			}
		});
		
		defendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//playerStatus.setText("Defend");
				doit(defend);
			}
		});
		
		raiseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//playerStatus.setText("Raise");
				doit(raise);
			}
		});
		
		oneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//playerStatus.setText("Attack One");
				doit(one);
			}
		});
		
		twoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//playerStatus.setText("Attack Two");
				doit(two);
			}
		});
		
		threeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//playerStatus.setText("Attack Three");
				doit(three);
			}
		});
		
		fourButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//playerStatus.setText("Attack Four");
				doit(four);
			}
		});
	}

	private void doit(int playerStrategy) {
		
		disableButton();
		
		int result = 0;
		
		switch (playerStrategy) {
		case defend:{
			result = system.compete(defend, playerMP, computerMP);
			playerStatus.setText(defend_String);
			break;
		}
		case raise:{
			result = system.compete(raise, playerMP, computerMP);
			playerStatus.setText(raise_String);
			break;
		}
		case one:{
			result = system.compete(one, playerMP, computerMP);
			playerStatus.setText(one_String);
			break;
		}
		case two:{
			result = system.compete(two, playerMP, computerMP);
			playerStatus.setText(two_String);
			break;
		}
		case three:{
			result = system.compete(three, playerMP, computerMP);
			playerStatus.setText(three_String);
			break;
		}
		case four:{
			result = system.compete(four, playerMP, computerMP);
			playerStatus.setText(four_String);
			break;
		}
		}
		
		int computerStrategy = system.getComputerStrategy();
		
		switch (computerStrategy) {
		case defend:{
			computerStatus.setText(defend_String);
			break;
		}
		case raise:{
			computerStatus.setText(raise_String);
			break;
		}
		case one:{
			computerStatus.setText(one_String);
			break;
		}
		case two:{
			computerStatus.setText(two_String);
			break;
		}
		case three:{
			computerStatus.setText(three_String);
			break;
		}
		case four:{
			computerStatus.setText(four_String);
			break;
		}
		}
		
		sum++;
		jtaLog.append(system.getInfoBeforeTraining());
		jtaLog.append("---------------------------\n");
		jtaLog.append(system.getInfoAfterTraining());
		jtaLog.append("----------------------- No." + sum + " over---------------------\n");
		
		refreshMP(playerStrategy, computerStrategy);
		refreshButton(result);
	}

	//---------------------------
	private void disableButton() {
		defendButton.setEnabled(false);
		raiseButton.setEnabled(false);
		oneButton.setEnabled(false);
		twoButton.setEnabled(false);
		threeButton.setEnabled(false);
		fourButton.setEnabled(false);
	}

	private void initialize() {
		startButton.setEnabled(true);
		currentLabel.setText("");
		jtaLog.setText("");
		sum = 0;
		playerMP = 0;
		computerMP = 0;
		playerStatus.setText("Undefined");
		computerStatus.setText("Undefined");
		playerMPLabel.setText("0");
		computerMPLabel.setText("0");
	}
	
	private void refreshButton(int result) {
		
		if (result == computerWin) {
			timerLose.start();
			JOptionPane.showMessageDialog(null, lose_String);
			allTime++;
			percentage.setText(winTime + "/" + allTime);
			initialize();
			return;
		}
		if (result == computerLose) {
			timerWin.start();
			JOptionPane.showMessageDialog(null, win_String);
			winTime++;
			allTime++;
			percentage.setText(winTime + "/" + allTime);
			initialize();
			return;
		}
		
		timerDraw.start();
		
		defendButton.setEnabled(true);
		raiseButton.setEnabled(true);
		
		if (playerMP >=1 ) {
			oneButton.setEnabled(true);
		}
		
		if (playerMP >=2 ) {
			twoButton.setEnabled(true);
		}
		
		if (playerMP >=3 ) {
			threeButton.setEnabled(true);
		}
		
		if (playerMP >=4 ) {
			fourButton.setEnabled(true);
		}
		
	}

	private void refreshMP(int playerStrategy, int computerStrategy) {
		switch (playerStrategy) {
		case defend:{
			break;
		}
		case raise:{
			playerMP++;
			break;
		}
		case one:{
			playerMP--;
			break;
		}
		case two:{
			playerMP-=2;
			break;
		}
		case three:{
			playerMP-=3;
			break;
		}
		case four:{
			playerMP-=4;
			break;
		}
		}
		
		switch (computerStrategy) {
		case defend:{
			break;
		}
		case raise:{
			computerMP++;
			break;
		}
		case one:{
			computerMP--;
			break;
		}
		case two:{
			computerMP-=2;
			break;
		}
		case three:{
			computerMP-=3;
			break;
		}
		case four:{
			computerMP-=4;
			break;
		}
		}
		
		computerMPLabel.setText(""+computerMP);
		playerMPLabel.setText(""+playerMP);
	}

}

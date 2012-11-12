package game;

import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;
import org.dtools.ini.*;

public class AIStrategy implements GameConstants{
	
	final double[] coefficient_increase = {
			7.7,7.7,7.7,7.7,7.7,7.5,7.5,7.5,7.5,7.5,
			7.3,7.3,7.3,7.3,7.3,6.7,6.7,6.7,6.7,6.7,
			6.4,6.4,6.4,6.4,6.4,6.1,6.1,6.1,6.1,6.1,
			5.5,5.5,5.5,5.5,5.5,4.9,4.9,4.9,4.9,4.9,
			4.4,4.4,4.4,4.4,4.4,3.9,3.9,3.9,3.9,3.9,
			3.3,3.3,3.3,3.3,3.3,3.1,3.1,3.1,3.1,3.1,
			2.9,2.9,2.9,2.9,2.9,2.3,2.3,2.3,2.3,2.3,
			1.9,1.9,1.9,1.9,1.9,1.4,1.4,1.4,1.4,1.4,
			1.2,1.2,1.2,1.2,1.2,0.8,0.8,0.8,0.8,0.8,
			0.4,0.4,0.4,0.4,0.4,0.2,0.2,0.2,0.2,0.2,
			1.0
	};
	
	final double[] coefficient_decrease = {
			0.1,0.2,0.2,0.2,0.2,0.4,0.4,0.4,0.4,0.4,
			0.8,0.8,0.8,0.8,0.8,1.2,1.2,1.2,1.2,1.2,
			1.4,1.4,1.4,1.4,1.4,1.9,1.9,1.9,1.9,1.9,
			2.3,2.3,2.3,2.3,2.3,2.9,2.9,2.9,2.9,2.9,
			3.1,3.1,3.1,3.1,3.1,3.3,3.3,3.3,3.3,3.3,
			3.9,3.9,3.9,3.9,3.9,4.4,4.4,4.4,4.4,4.4,
			4.9,4.9,4.9,4.9,4.9,5.5,5.5,5.5,5.5,5.5,
			6.1,6.1,6.1,6.1,6.1,6.4,6.4,6.4,6.4,6.4,
			6.7,6.7,6.7,6.7,6.7,7.3,7.3,7.3,7.3,7.3,
			7.5,7.5,7.5,7.5,7.5,7.7,7.7,7.7,7.7,7.7,
			0.0
	};
	
	IniFile ini;
	IniFileReader reader;
	IniFileWriter writer;
	
	String sectionName;
	IniSection section;
	int playerMP;
	int computerMP;
	
	int availableValue;
	double defendValue;
	double raiseValue;
	double oneValue;
	double twoValue;
	double threeValue;
	double fourValue;
	
	double remainValue;

	public AIStrategy(IniFile ini, IniFileReader reader, IniFileWriter writer, int playerMP, int computerMP) {
		this.ini = ini;
		this.reader = reader;
		this.writer = writer;
				
		sectionName = new String(playerMP+","+computerMP);
		this.playerMP = playerMP;
		this.computerMP = computerMP;
		
		readStrategy();
	}

	public int chooseStrategy(){

		Random rand = new Random();
		double choose = rand.nextDouble() * 100;

		if (choose < defendValue) {
			return defend;
		}
		else if (choose < defendValue + raiseValue) {
			return raise;
		}
		else if (choose < defendValue + raiseValue + oneValue) {
			return one;
		}
		else if (choose < defendValue + raiseValue + oneValue + twoValue) {
			return two;
		}
		else if (choose < defendValue + raiseValue + oneValue + twoValue + threeValue) {
			return three;
		}
		else if (choose <= defendValue + raiseValue + oneValue + twoValue + threeValue + fourValue) {
			return four;
		}
		else {
			JOptionPane.showMessageDialog(null, "Error! " + choose);
			java.lang.System.exit(0);
			return error;
		}

	}

	public void updateStrategy(int playerStrategy, int computerStrategy, int result) {
		if (result == computerWin) {
			increase(computerStrategy);
		}
		else if (result == computerLose) {
			decrease(computerStrategy);
		}
		else {
			//not necessary defend
			if (playerStrategy == raise && computerStrategy == defend) {
				decrease(computerStrategy);
			}
			//not necessary attack
			else if (playerStrategy == defend && computerStrategy != raise && computerStrategy != four ) {
				decrease(computerStrategy);
				increase(raise);
			}
			//miss chance of raise
			//else if (playerStrategy == defend && computerStrategy == defend) {
				//increase(raise);
			//}
			//reinforce defend
			else if (playerStrategy == one || playerStrategy == two || playerStrategy == three) {
				increase(computerStrategy);
				if (computerStrategy != defend ) increase(defend);
			}
		}
		
		write();
	}
	
	/**
	 * Will change after calling "updateStrategy" method
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		if (availableValue >= 2){
			s.append("Defend: " + defendValue + "\n");
			s.append("Raise: " + raiseValue + "\n");
		}
		
		if (availableValue >= 3){
			s.append("One: " + oneValue + "\n");
		}
		
		if (availableValue >= 4){
			s.append("Two: " + twoValue + "\n");
		}
		
		if (availableValue >= 5){
			s.append("Three: " + threeValue + "\n");
		}
		
		if (availableValue >= 6){
			s.append("Four: " + fourValue + "\n");
		}
		
		return s.toString();
	}
	
	//-----------------------------
	private void increase(int computerStrategy) {
		switch (computerStrategy) {
		case raise:{
			remainValue = 100 - raiseValue;
			double add = coefficient_increase[(int) raiseValue];
			if (raiseValue + add < 100) {
				raiseValue += add;
				section.getItem("raise").setValue(raiseValue);
				balanceIncrease(computerStrategy, add);
			}
			break;
		}
		case defend:{
			remainValue = 100 - defendValue;
			double add = coefficient_increase[(int) defendValue];
			if (defendValue + add < 100) {
				defendValue += add;
				section.getItem("defend").setValue(defendValue);
				balanceIncrease(computerStrategy, add);
			}
			break;
		}
		case one:{
			remainValue = 100 - oneValue;
			double add = coefficient_increase[(int) oneValue];
			if (oneValue + add < 100) {
				oneValue += add;
				section.getItem("one").setValue(oneValue);
				balanceIncrease(computerStrategy, add);
			}
			break;
		}
		case two:{
			remainValue = 100 - twoValue;
			double add = coefficient_increase[(int) twoValue];
			if (twoValue + add < 100) {
				twoValue += add;
				section.getItem("two").setValue(twoValue);
				balanceIncrease(computerStrategy, add);
			}
			break;
		}
		case three:{
			remainValue = 100 - threeValue;
			double add = coefficient_increase[(int) threeValue];
			if (threeValue + add < 100) {
				threeValue += add;
				section.getItem("three").setValue(threeValue);
				balanceIncrease(computerStrategy, add);
			}
			break;
		}
		case four:{
			remainValue = 100 - fourValue;
			double add = coefficient_increase[(int) fourValue];
			if (fourValue + add < 100) {
				fourValue += add;
				section.getItem("four").setValue(fourValue);
				balanceIncrease(computerStrategy, add);
			}
			break;
		}
		}
	}
	
	private void balanceIncrease(int computerStrategy, double add) {
		double part = add / remainValue;
		
		//the following structure can not be replaced by if - then - else
		if (computerStrategy != defend && availableValue >= 1) {
			defendValue -= part * defendValue;
			section.getItem("defend").setValue(defendValue);
		}
		
		if (computerStrategy != raise && availableValue >= 2) {
			raiseValue -= part * raiseValue;
			section.getItem("raise").setValue(raiseValue);
		}
		
		if (computerStrategy != one && availableValue >= 3) {
			oneValue -= part * oneValue;
			section.getItem("one").setValue(oneValue);
		}
		
		if (computerStrategy != two && availableValue >= 4) {
			twoValue -= part * twoValue;
			section.getItem("two").setValue(twoValue);
		}
		
		if (computerStrategy != three && availableValue >= 5) {
			threeValue -= part * threeValue;
			section.getItem("three").setValue(threeValue);
		}
		
		if (computerStrategy != four && availableValue >= 6) {
			fourValue -= part * fourValue;
			section.getItem("four").setValue(fourValue);
		}
	}
	
	private void decrease(int computerStrategy) {
		
		switch (computerStrategy) {
		case raise:{
			remainValue = 100 - raiseValue;
			double minus = coefficient_decrease[(int) raiseValue];
			if (raiseValue - minus > 0) {
				raiseValue -= minus;
				section.getItem("raise").setValue(raiseValue);
				balanceDecrease(computerStrategy, minus);
			}
			break;
		}
		case defend:{
			remainValue = 100 - defendValue;
			double minus = coefficient_decrease[(int) defendValue];
			if (defendValue - minus > 0) {
				defendValue -= minus;
				section.getItem("defend").setValue(defendValue);
				balanceDecrease(computerStrategy, minus);
			}
			break;
		}
		case one:{
			remainValue = 100 - oneValue;
			double minus = coefficient_decrease[(int) oneValue];
			if (oneValue - minus > 0) {
				oneValue -= minus;
				section.getItem("one").setValue(oneValue);
				balanceDecrease(computerStrategy, minus);
			}
			break;
		}
		case two:{
			remainValue = 100 - twoValue;
			double minus = coefficient_decrease[(int) twoValue];
			if (twoValue - minus > 0) {
				twoValue -= minus;
				section.getItem("two").setValue(twoValue);
				balanceDecrease(computerStrategy, minus);
			}
			break;
		}
		case three:{
			remainValue = 100 - threeValue;
			double minus = coefficient_decrease[(int) threeValue];
			if (threeValue - minus > 0) {
				threeValue -= minus;
				section.getItem("three").setValue(threeValue);
				balanceDecrease(computerStrategy, minus);
			}
			break;
		}
		case four:{
			remainValue = 100 - fourValue;
			double minus = coefficient_decrease[(int) fourValue];
			if (fourValue - minus > 0) {
				fourValue -= minus;
				section.getItem("four").setValue(fourValue);
				balanceDecrease(computerStrategy, minus);
			}
			break;
		}
		}
	}
	
	private void balanceDecrease(int computerStrategy, double minus) {
		double part = minus / remainValue;
		
		//the following structure can not be replaced by if - then - else
		if (computerStrategy != defend && availableValue >= 1) {
			defendValue += part  * defendValue;
			section.getItem("defend").setValue(defendValue);
		}
		
		if (computerStrategy != raise && availableValue >= 2) {
			raiseValue += part  * raiseValue;
			section.getItem("raise").setValue(raiseValue);
		}
		
		if (computerStrategy != one && availableValue >= 3) {
			oneValue += part * oneValue;
			section.getItem("one").setValue(oneValue);
		}
		
		if (computerStrategy != two && availableValue >= 4) {
			twoValue += part * twoValue;
			section.getItem("two").setValue(twoValue);
		}
		
		if (computerStrategy != three && availableValue >= 5) {
			threeValue += part * threeValue;
			section.getItem("three").setValue(threeValue);
		}
		
		if (computerStrategy != four && availableValue >= 6) {
			fourValue += part * fourValue;
			section.getItem("four").setValue(fourValue);
		}
	}
	
	//-----------------------------
 	private void readStrategy() {
		//check whether the section corresponds it exists
		//if not exists, create new one and set the default value
		check(playerMP, computerMP);
		
		read();
		section = ini.getSection(sectionName);
		
		availableValue = Integer.parseInt(section.getItem("available").getValue());
		defendValue = Double.parseDouble(section.getItem("defend").getValue());
		raiseValue = Double.parseDouble(section.getItem("raise").getValue());
		oneValue = Double.parseDouble(section.getItem("one").getValue());
		twoValue = Double.parseDouble(section.getItem("two").getValue());
		threeValue = Double.parseDouble(section.getItem("three").getValue());
		fourValue = Double.parseDouble(section.getItem("four").getValue());
		
	}

	private void check(int playerMP, int computerMP) {

		IniSection temp = ini.getSection(sectionName);
		if (temp != null) return;
		else {
			
			IniSection section = ini.addSection(sectionName);
			
			section.addItem("available");
			IniItem availableItem = section.getItem("available");
			section.addItem("defend");
			IniItem defendItem = section.getItem("defend");
			section.addItem("raise");
			IniItem raiseItem = section.getItem("raise");
			section.addItem("one");
			IniItem oneItem = section.getItem("one");
			section.addItem("two");
			IniItem twoItem = section.getItem("two");
			section.addItem("three");
			IniItem threeItem = section.getItem("three");
			section.addItem("four");
			IniItem fourItem = section.getItem("four");
			//default value
			availableItem.setValue(0);
			defendItem.setValue(0.0);
			raiseItem.setValue(0.0);
			oneItem.setValue(0.0);
			twoItem.setValue(0.0);
			threeItem.setValue(0.0);
			fourItem.setValue(0.0);

			double average = 100.0;
			switch (computerMP) {
			case 0:{
				average /= 2;//50
				availableItem.setValue(2);
				if (playerMP > 0) {
					defendItem.setValue(average);
					raiseItem.setValue(average);
				}
				else {
					defendItem.setValue(0.0);
					raiseItem.setValue(100.0);
				}
				break;
			}
			case 1:{
				average /= 3;//33.33
				availableItem.setValue(3);
				if (playerMP > 0) {
					defendItem.setValue(average);
					raiseItem.setValue(average);
					oneItem.setValue(average);
				}
				else {
					defendItem.setValue(0.0);
					raiseItem.setValue(50.0);
					oneItem.setValue(50.0);
				}
				break;
			}
			case 2:{
				average /= 4;//25
				availableItem.setValue(4);
				if (playerMP > 0) {
					defendItem.setValue(average);
					raiseItem.setValue(average - 5);
					oneItem.setValue(average - 5);
					twoItem.setValue(average + 10);
				}
				else {
					defendItem.setValue(0.0);
					raiseItem.setValue(50.0);
					oneItem.setValue(50.0);
					twoItem.setValue(0.0);
				}
				break;
			}
			case 3:{
				average /= 5;//20
				availableItem.setValue(5);
				if (playerMP > 0) {
					defendItem.setValue(average - 5);
					raiseItem.setValue(average - 5);
					oneItem.setValue(average - 5);
					twoItem.setValue(average - 5);
					threeItem.setValue(average + 20);
				}
				else {
					defendItem.setValue(0.0);
					raiseItem.setValue(50.0);
					oneItem.setValue(50.0);
					twoItem.setValue(0.0);
					threeItem.setValue(0.0);
				}
				break;
			}
			default:{
				average /= 6;//16.67
				availableItem.setValue(6);
				if (playerMP > 0) {
					defendItem.setValue(average);
					raiseItem.setValue(average - 10);
					oneItem.setValue(average - 5);
					twoItem.setValue(average + 10);
					threeItem.setValue(average - 5);
					fourItem.setValue(average + 10);
				}
				else {
					defendItem.setValue(0.0);
					raiseItem.setValue(0.0);
					oneItem.setValue(0.0);
					twoItem.setValue(0.0);
					threeItem.setValue(0.0);
					fourItem.setValue(100.0);
				}
				break;
			}
			}
			write();
		}
		
	}
	
	private void write() {
		try {
			writer.write();
		}
		catch( IOException e ) {
			// exception thrown as an input\output exception occured
			e.printStackTrace();
		}
	}
	
	private void read() {
		try {
			reader.read();
		}
		catch( IOException e ) {
			// exception thrown as an input\output exception occured
			e.printStackTrace();
		}
	}

}

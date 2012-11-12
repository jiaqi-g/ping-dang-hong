package game;

import java.io.File;
import java.io.IOException;

import org.dtools.ini.*;

public class GameSystem implements GameConstants {

	File file = new File("settings.ini");
	IniFile ini = new BasicIniFile();
	IniFileReader reader = new IniFileReader( ini, file );
	IniFileWriter writer = new IniFileWriter( ini, file );
	
	int computerStrategy;
	
	String infoBeforeTraining;
	String infoAfterTraining;
	
	public GameSystem() {
		if (file.exists()) {
			try {
				reader.read();
			}
			catch( FormatException e ) {
				// exception thrown because the INI file was in an unexpected format
				e.printStackTrace();
			}
			catch( IOException e ) {
				// exception thrown as an input\output exception occured
				e.printStackTrace();
			}
		}
		else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 	Every time player press one button invokes this method
	 */
	public int compete(int playerStrategy, int playerMP, int computerMP) {
		AIStrategy strategy = new AIStrategy(ini, reader, writer, playerMP, computerMP);

		infoBeforeTraining = strategy.toString();
		
		computerStrategy = strategy.chooseStrategy();
		int result = judge(playerStrategy, computerStrategy);
		strategy.updateStrategy(playerStrategy, computerStrategy, result);
		
		infoAfterTraining = strategy.toString();
		
		return result;
	}
	
	/**
	 * Can not be called before calling "compete" method
	 */
	public String getInfoBeforeTraining() {
		return infoBeforeTraining;
	}
	
	/**
	 * Can not be called before calling "compete" method
	 */
	public String getInfoAfterTraining() {
		return infoAfterTraining;
	}
	
	/**
	 * Can not be called before calling "compete" method
	 */
	public int getComputerStrategy() {
		return computerStrategy;
	}
	
	private int judge(int playerStrategy, int computerStrategy) {

		int result = 0;

		if (computerStrategy != defend && playerStrategy != defend) {
			if (computerStrategy == four && playerStrategy == one) {
				result = computerLose;
			}
			else if (computerStrategy == one && playerStrategy == four) {
				result = computerWin;
			}
			else if (computerStrategy < playerStrategy) {
				result = computerLose;
			}
			else if (computerStrategy > playerStrategy) {
				result = computerWin;
			}
			else if (computerStrategy == playerStrategy) {
				result = draw;
			}
		}
		else if (computerStrategy == defend && playerStrategy != defend) {
			if (playerStrategy == four) {
				result = computerLose;
			}
			else {
				result = draw;
			}
		}
		else if (computerStrategy != defend && playerStrategy == defend) {
			if (computerStrategy == four) {
				result = computerWin;
			}
			else {
				result = draw;
			}
		}
		else if (computerStrategy == defend && playerStrategy == defend) {
			result = draw;
		}

		return result;

	}

}

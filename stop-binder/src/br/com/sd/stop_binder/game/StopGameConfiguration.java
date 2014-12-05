package br.com.sd.stop_binder.game;

public class StopGameConfiguration {

	private int roundsAmount;
	private int roundTime;
	public int getRoundsAmount() {
		return roundsAmount;
	}

	public int getRoundTime() {
		return roundTime;
	}

	public int getMinimumPlayersAmount() {
		return minimumPlayersAmount;
	}

	private int minimumPlayersAmount;
	
	public StopGameConfiguration(int roundsAmount, int roundTime, int minimumPlayersAmount) {
		this.roundsAmount = roundsAmount;
		this.roundTime = roundTime;
		this.minimumPlayersAmount = minimumPlayersAmount;
	}
}

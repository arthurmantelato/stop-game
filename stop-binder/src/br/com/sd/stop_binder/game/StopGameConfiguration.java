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
	
	public StopGameConfiguration(int roundsAmount, int roundTime) {
		this.roundsAmount = roundsAmount;
		this.roundTime = roundTime;
	}
}

package controllers;

public class PlayMode {

	private int level;
	private String difficulty;
	
	public PlayMode(String d, int l) {
		this.setLevel(l);
		this.setDifficulty(d);
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int defaultLevel) {
		this.level = defaultLevel;
	}
	
	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
}

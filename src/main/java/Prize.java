
public enum Prize {
	CAR(true),
	DONKEY(false);
	
	private boolean isWinner;
	
	Prize(final boolean isWinner) {
		this.isWinner = isWinner;
	}
	
	public boolean isWinner() {
		return this.isWinner;
	}
}

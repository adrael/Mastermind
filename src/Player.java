
public class Player {
	private String nameOfPlayer;
	private int rowNumber, pawnNumber, colorNumber;

	public Player() {

	}

	public Player(String s, int r, int p, int c) {
		setName(s);
		setRowNumber(r);
		setPawnNumber(p);
		setColorNumber(c);
	}

	public void setName(String s) {
		this.nameOfPlayer = s;
	}

	public String getName() {
		return this.nameOfPlayer;
	}

	public void setRowNumber(int n) {
		this.rowNumber = n;
	}

	public int getRowNumber() {
		return this.rowNumber;
	}

	public void setPawnNumber(int n) {
		this.pawnNumber = n;
	}

	public int getPawnNumber() {
		return this.pawnNumber;
	}

	public void setColorNumber(int n) {
		this.colorNumber = n;
	}

	public int getColorNumber() {
		return this.colorNumber;
	}
}

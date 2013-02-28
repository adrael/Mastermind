import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Mastermind {
	private PawnGame[] solution; // combinaison générée par la fonction generate
	private int nbPawn; // nombres de pions
	private Player player;

	private final Color[] pawnColors = { Color.RED, Color.YELLOW, Color.GREEN,
			Color.BLUE, Color.CYAN, Color.PINK, Color.MAGENTA, Color.ORANGE,
			Color.WHITE };

	public Mastermind() {

	}

	public Color[] getPawnColors() {
		return this.pawnColors;
	}

	public void setPlayer(Player p) {
		this.player = p;
		this.nbPawn = this.player.getPawnNumber();
	}

	public Player getPlayer() {
		return this.player;
	}

	public void generateSolution() {
		solution = new PawnGame[this.nbPawn];
		Random r = new Random();
		final Color[] possibleColors = new Color[this.player.getColorNumber()];

		for (int i = 0; i < possibleColors.length; ++i)
			possibleColors[i] = this.pawnColors[i];

		for (int i = 0; i < this.nbPawn; ++i)
			this.solution[i] = new PawnGame(
					possibleColors[r.nextInt(this.player.getColorNumber())], 20);
	}

	public PawnGame[] getSolution() {
		return this.solution;
	}

	public boolean checkSolution(PawnGame[] playerSolution, PawnSolution[] tips) {
		boolean goodSolution = true;
		int goodPawns = 0, badPawns = 0;
		ArrayList<Color> colorsInSolution = new ArrayList<>();

		for (int i = 0; i < tips.length; ++i) {
			if (playerSolution[i].getPawnColor() == this.solution[i]
					.getPawnColor()) {
				++goodPawns;
			} else {
				for (int j = 0; j < tips.length; ++j)
					if (playerSolution[i].getPawnColor() == this.solution[j]
							.getPawnColor()
							&& !colorsInSolution.contains(playerSolution[i]
									.getPawnColor())) {
						++badPawns;
						colorsInSolution.add(playerSolution[i].getPawnColor());
						break;
					}
				goodSolution = false;
			}
		}
		System.out.println("gg = " + goodPawns + " / bb = " + badPawns);
		for (int x = 0; x < goodPawns; ++x)
			tips[x].setPawnColor(Color.RED);
		for (int y = goodPawns; y < badPawns + goodPawns; ++y)
			tips[y].setPawnColor(Color.BLACK);
		return goodSolution;
	}
}

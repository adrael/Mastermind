import java.awt.Color;
import java.util.Random;

public class Mastermind {
	private PawnGame[] solution; // combinaison générée par la fonction generate
	private int nbPawn; // nombres de pions
	private Player player; // je pense que ça ne sert à rien

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

	public boolean chekSolution(PawnGame[] playerSolution, PawnSolution[] tips){ //donne le nombre de pions bien placés (good) et mal placé (bad) et met ok à true si la combinaison est trouvée
		int good =0, bad = 0;
		PawnGame[] player = playerSolution, sol = solution;
		boolean ok = false;
		
		for(int i=0; i<=4; i++){
			if(player[i]==solution[i]){
				good++;
				player[i].setPawnColor(Color.GRAY);
				sol[i].setPawnColor(Color.GRAY);
			}
		}
		if(good==4) ok = true;
		else{
			for(int i=0; i<=4; i++){
				if(sol[i].getPawnColor()!=Color.GRAY){
					for(int j=0; j<=4; j++){
						if(player[j].getPawnColor()!=Color.GRAY){
							if(player[j]==sol[i]){
								bad++;
								player[j].setPawnColor(Color.GRAY);
								sol[i].setPawnColor(Color.GRAY);
							}
						}
					}
				}
			}
		}
		return ok;
	}
	/*
	public boolean checkSolution(PawnGame[] playerSolution, PawnSolution[] tips) {
		boolean good = true;

		for (int i = 0; i < tips.length; ++i) {
			if (playerSolution[i].getPawnColor() == this.solution[i].getPawnColor())
				tips[i].setPawnColor(Color.RED);
			else
				for(int j = 0; j < tips.length; ++j)
					if(playerSolution[i].getPawnColor() == this.solution[j].getPawnColor()) {
						tips[i].setPawnColor(Color.BLACK);
						good = false;
						break;
					}
		}
		return good;
	}*/
}

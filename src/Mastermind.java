import java.awt.Color;
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
	}
}
/*
 * public void incNbTry() { nbTry++; }
 * 
 * public int getGoodPawn() //retourne le nombre de pions bien placés; et met
 * result à true si la combinaison est bonne { int nb=0; for(int
 * i=0;i<nbPawn;i++) if(player[i]==solution[i]) nb++; if(nb==nbPawn)
 * result=true; return nb; }
 * 
 * public int getBadPawn() //retroune le nombre de pions mal placés. { int nb=0;
 * 
 * return nb; }
 * 
 * public boolean getResult() { return result; } }
 * 
 * public int getMalPlace() //return le nombre de couleurs mal placÃ©es { int
 * nbMal=0; int k=0; Color joueurCombi[]; joueurCombi = new Color[nbCouleur];
 * Color laCombi[]; laCombi = new Color[nbCouleur];
 * 
 * for(int i=0;i<nbCouleur;i++) { if(laSolution[i] != joueurSolution[i]) {
 * laCombi[k] = laSolution[i]; joueurCombi[k] = joueurSolution[i]; k++; } }
 * for(int i=0;i<k;i++) { int j=0; while(j<k) { if(joueurCombi[i] == laCombi[j])
 * { nbMal++; laCombi[j] = null; j=k+1; } else { j++; } } } return nbMal; }
 */
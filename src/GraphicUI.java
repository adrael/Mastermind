import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class GraphicUI implements ActionListener {
	private final JFrame window;
	private final Container globalPane, menuPane, gamePane, playerPane, solutionPane;
	private final JButton newGame, resetLine, valid, quit, resetPawn,
			validPlayer, quitPlayer, rules;
	private final Mastermind manager;
	private final JTextField name;
	private final JSpinner rowsNumber, pawns, colors;
	private final JLabel nameLabel, rowsLabel, pawnsLabel, colorsLabel, CPU,
			playerLabel, solutionLabel;
	private final ArrayList<PawnGame[]> rows = new ArrayList<>();
	private final ArrayList<PawnSolution[]> tips = new ArrayList<>();
	private final Rules gameRules;

	private Player player;
	private int currentRow = 0, currentPawn = 0, numberOfPawnPerRow = 4,
			numberOfRow = 12, numberOfColor = 9, scoreCPU = 0, scorePlayer = 0;

	public GraphicUI() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		this.player = new Player();
		
		String[] authors = new String[3];
		authors[0] = "Raphaël MARQUES";
		authors[1] = "Jérémie MERCURI";
		authors[2] = "Félix WATTEZ";
		
		this.gameRules = new Rules();
		this.gameRules.setTitle("Mastermind");
		this.gameRules.setText("L'ordinateur génère une combinaison de pions aléatoire selon la difficulté choisie.\n" +
				"A chaque tour, le joueur doit se servir de pions pour remplir une rangée\n" +
				"selon l'idée qu'il se fait des pions dissimulés.\n\n" +
				"Une fois les pions placés, l'ordinateur indique :\n\n" +
				"   - le nombre de pions de la bonne couleur bien placés en utilisant le même nombre de pions rouges\n" +
				"   - le nombre de pions de la bonne couleur, mais mal placés, avec les pions noirs.\n\n\n\n" +
				"http://fr.wikipedia.org/wiki/Mastermind");
		this.gameRules.setAuthors(authors);
		this.gameRules.prepareRules();

		this.window = new JFrame();
		this.window.setVisible(true);
		this.window.setResizable(false);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.manager = new Mastermind();

		this.quit = new JButton("Quitter");
		this.newGame = new JButton("Nouvelle partie");
		this.resetLine = new JButton("Effacer la ligne");
		this.valid = new JButton("Valider");
		this.resetPawn = new JButton("Effacer le dernier pion");
		this.rules = new JButton("Règles");

		this.validPlayer = new JButton("Jouer");
		this.quitPlayer = new JButton("Quitter");

		this.nameLabel = new JLabel("Pseudo :");
		this.name = new JTextField(20);
		this.nameLabel.setFont(new Font(this.nameLabel.getFont().getName(),
				Font.BOLD, this.nameLabel.getFont().getSize()));

		this.rowsLabel = new JLabel("Nombre d'essais :");
		this.rowsNumber = new JSpinner(new SpinnerNumberModel(12, 1, 12, 2));
		this.rowsLabel.setFont(new Font(this.rowsLabel.getFont().getName(),
				Font.BOLD, this.rowsLabel.getFont().getSize()));

		this.pawnsLabel = new JLabel("Nombre de pions :");
		this.pawns = new JSpinner(new SpinnerNumberModel(4, 4, 8, 2));
		this.pawnsLabel.setFont(new Font(this.pawnsLabel.getFont().getName(),
				Font.BOLD, this.pawnsLabel.getFont().getSize()));

		this.colorsLabel = new JLabel("Nombre de couleurs :");
		this.colors = new JSpinner(new SpinnerNumberModel(6, 2, 9, 1));
		this.colorsLabel.setFont(new Font(this.colorsLabel.getFont().getName(),
				Font.BOLD, this.colorsLabel.getFont().getSize()));

		this.CPU = new JLabel("CPU : " + Integer.toString(this.scoreCPU));
		this.playerLabel = new JLabel(this.player.getName() + " : "
				+ Integer.toString(this.scorePlayer));
		
		this.solutionLabel = new JLabel("Solution :");
		this.solutionLabel.setFont(new Font(this.solutionLabel.getFont().getName(),
				Font.BOLD, this.solutionLabel.getFont().getSize()));

		this.globalPane = this.window.getContentPane();
		this.globalPane.setLayout(new BorderLayout());

		this.menuPane = new Container();
		this.menuPane.setLayout(new BoxLayout(this.menuPane, BoxLayout.Y_AXIS));

		this.gamePane = new Container();
		this.gamePane.setLayout(new BoxLayout(this.gamePane, BoxLayout.Y_AXIS));

		this.playerPane = new Container();
		this.playerPane.setLayout(new BoxLayout(this.playerPane,
				BoxLayout.Y_AXIS));
		
		this.solutionPane = new Container();
		this.solutionPane.setLayout(new FlowLayout());

		this.resetPawn.addActionListener(this);
		this.resetLine.addActionListener(this);
		this.newGame.addActionListener(this);
		this.rules.addActionListener(this);
		this.valid.addActionListener(this);
		this.quit.addActionListener(this);

		this.validPlayer.addActionListener(this);
		this.quitPlayer.addActionListener(this);

		initPlayerDisplay();
		createPlayer();
	}

	private void displayPlate() {
		this.numberOfPawnPerRow = this.player.getPawnNumber();
		this.numberOfRow = this.player.getRowNumber();
		this.numberOfColor = this.player.getColorNumber();

		initGameDisplay();
		createMenu();

		this.globalPane.removeAll();
		this.globalPane.add(this.gamePane, BorderLayout.WEST);
		this.globalPane.add(this.menuPane, BorderLayout.EAST);
		this.window.setTitle("Mastermind - " + this.player.getName());
		this.window.pack();
		this.window.setLocationRelativeTo(null);
	}

	private void createPlayer() {
		this.scoreCPU = 0;
		this.scorePlayer = 0;
		this.globalPane.removeAll();
		this.globalPane.add(this.playerPane, BorderLayout.CENTER);
		this.window.setTitle("Infos du joueur");
		this.window.pack();
		this.window.setLocationRelativeTo(null);
	}

	private void initGameDisplay() {
		this.rows.clear();
		this.tips.clear();
		for (int i = 0; i < this.numberOfRow; ++i) {
			PawnGame[] tmp = new PawnGame[this.numberOfPawnPerRow];
			PawnSolution[] tmp2 = new PawnSolution[this.numberOfPawnPerRow];
			for (int j = 0; j < this.numberOfPawnPerRow; ++j) {
				tmp[j] = new PawnGame(Color.GRAY, 10);
				tmp2[j] = new PawnSolution(Color.WHITE, 10);
			}
			this.rows.add(tmp);
			this.tips.add(tmp2);
		}
		displayGame();
	}

	private void initPlayerDisplay() {
		Container buttons = new Container();
		buttons.setLayout(new FlowLayout());
		buttons.add(this.validPlayer);
		buttons.add(this.quitPlayer);

		Container textfield = new Container();
		textfield.setLayout(new FlowLayout());
		textfield.add(this.nameLabel);
		textfield.add(this.name);

		Container rowsSpinner = new Container();
		rowsSpinner.setLayout(new FlowLayout());
		rowsSpinner.add(this.rowsLabel);
		rowsSpinner.add(this.rowsNumber);

		Container pawnsSpinner = new Container();
		pawnsSpinner.setLayout(new FlowLayout());
		pawnsSpinner.add(this.pawnsLabel);
		pawnsSpinner.add(this.pawns);

		Container colorsSpinner = new Container();
		colorsSpinner.setLayout(new FlowLayout());
		colorsSpinner.add(this.colorsLabel);
		colorsSpinner.add(this.colors);

		this.playerPane.add(textfield);
		this.playerPane.add(rowsSpinner);
		this.playerPane.add(pawnsSpinner);
		this.playerPane.add(colorsSpinner);
		this.playerPane.add(buttons);
	}

	private void createMenu() {
		this.menuPane.removeAll();

		JLabel possibleColors = new JLabel("Séléctionnez une couleur");
		possibleColors.setFont(new Font(possibleColors.getFont().getName(),
				Font.BOLD, 13));
		possibleColors.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.menuPane.add(Box.createRigidArea(new Dimension(0, 50)));
		this.menuPane.add(possibleColors);
		this.menuPane.add(Box.createRigidArea(new Dimension(0, 20)));

		Container c = new Container();
		c.setLayout(new FlowLayout());
		c.setPreferredSize(new Dimension(300, 50));

		Color[] pawnColors = this.manager.getPawnColors();
		for (int i = 0; i < this.numberOfColor; ++i) {
			RoundButton tmp = new RoundButton(pawnColors[i], 20);
			tmp.addActionListener(this);
			addButton(tmp, c, 0, 10, 0);
		}

		this.menuPane.add(c);
		this.menuPane.add(Box.createRigidArea(new Dimension(0, 20)));

		PawnSolution black = new PawnSolution(Color.BLACK, 10);
		PawnSolution red = new PawnSolution(Color.RED, 10);
		PawnSolution white = new PawnSolution(Color.WHITE, 10);
		
		JLabel legend = new JLabel("Légende");
		legend.setFont(new Font(legend.getFont().getName(), Font.BOLD, 13));
		legend.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel whiteLabel = new JLabel(" Mauvais pion                          ");
		JLabel blackLabel = new JLabel(" Pion mal placé, bonne couleur");
		JLabel redLabel = new JLabel(" Pion bien placé, bonne couleur");
		
		Container c0 = new Container();
		c0.setLayout(new FlowLayout());
		c0.add(white);
		c0.add(whiteLabel);
		
		Container c1 = new Container();
		c1.setLayout(new FlowLayout());
		c1.add(black);
		c1.add(blackLabel);
		
		Container c2 = new Container();
		c2.setLayout(new FlowLayout());
		c2.add(red);
		c2.add(redLabel);

		this.menuPane.add(legend);
		this.menuPane.add(Box.createRigidArea(new Dimension(0, 10)));
		this.menuPane.add(c0);
		this.menuPane.add(c1);
		this.menuPane.add(c2);
		this.menuPane.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JLabel action = new JLabel("Actions");
		action.setFont(new Font(action.getFont().getName(), Font.BOLD, 13));
		action.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.menuPane.add(action);
		this.menuPane.add(Box.createRigidArea(new Dimension(0, 10)));

		addButton(this.valid, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		addButton(this.resetPawn, this.menuPane, 0, 10,
				Component.CENTER_ALIGNMENT);
		addButton(this.resetLine, this.menuPane, 0, 10,
				Component.CENTER_ALIGNMENT);
		addButton(this.newGame, this.menuPane, 0, 10,
				Component.CENTER_ALIGNMENT);
		addButton(this.rules, this.menuPane, 0, 10,
				Component.CENTER_ALIGNMENT);		
		addButton(this.quit, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);

		JLabel scores = new JLabel("SCORES");
		scores.setFont(new Font(scores.getFont().getName(), Font.BOLD, 30));
		
		this.CPU.setFont(new Font(this.CPU.getFont().getName(), Font.BOLD, 18));
		this.playerLabel.setFont(new Font(this.playerLabel.getFont().getName(),
				Font.BOLD, 18));

		scores.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.CPU.setAlignmentX(Component.RIGHT_ALIGNMENT);
		this.playerLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		updateScores();

		this.menuPane.add(Box.createRigidArea(new Dimension(0, 100)));
		this.menuPane.add(scores);
		this.menuPane.add(Box.createRigidArea(new Dimension(0, 20)));
		this.menuPane.add(this.CPU);
		this.menuPane.add(this.playerLabel);
		this.menuPane.add(Box.createRigidArea(new Dimension(0, 20)));
		this.menuPane.setPreferredSize(new Dimension(300, this.menuPane
				.getPreferredSize().height));
	}

	private void updateScores() {
		this.CPU.setText("CPU : " + Integer.toString(this.scoreCPU));
		this.playerLabel.setText(this.player.getName() + " : "
				+ Integer.toString(this.scorePlayer));
	}

	private void displayGame() {
		this.gamePane.removeAll();
		for (int i = 0; i < this.numberOfRow; ++i) {
			Container c = new Container();
			c.setLayout(new FlowLayout());
			c.setPreferredSize(new Dimension(300, 50));

			for (int j = 0; j < this.numberOfPawnPerRow; ++j)
				addButton(this.rows.get(i)[j], c, 1, 20, 0);

			JSeparator x = new JSeparator(SwingConstants.HORIZONTAL);
			x.setPreferredSize(new Dimension(100, 3));

			this.gamePane.add(x);
			if (this.player.getRowNumber() > 1)
				c.add(displayPawnSolution(i));
			this.gamePane.add(c);
		}

		JSeparator x = new JSeparator(SwingConstants.HORIZONTAL);
		x.setPreferredSize(new Dimension(100, 3));

		this.solutionPane.add(this.solutionLabel);
		this.solutionPane.add(Box.createRigidArea(new Dimension(20, 20)));

		displaySolution(false);

		this.gamePane.add(x);
		this.gamePane.add(this.solutionPane);
	}

	private Container displayPawnSolution(int row) {
		Container c = new Container();
		c.setLayout(new GridLayout(this.numberOfPawnPerRow / 2, 2, 5, 5));
		c.setPreferredSize(new Dimension(this.numberOfPawnPerRow * 5, this.numberOfPawnPerRow * 5));

		for (int i = 0; i < this.numberOfPawnPerRow; ++i)
			c.add(this.tips.get(row)[i]);
		return c;
	}
	
	private void displaySolution(boolean display) {
		this.solutionPane.removeAll();
		PawnGame[] solution = this.manager.getSolution();

		this.solutionPane.add(this.solutionLabel);
		
		for (int j = 0; j < solution.length; ++j)
			addButton(display ? solution[j] : new RoundButton(Color.GRAY, 10), this.solutionPane, 100, 20, 0);
	}

	private void addButton(JButton button, Container container, int vh,
			int space, float alignment) {
		if (alignment > 0)
			button.setAlignmentX(alignment);

		container.add(button);

		if (space > 0)
			if (vh == 0)
				container.add(Box.createRigidArea(new Dimension(0, space)));
			else
				container.add(Box.createRigidArea(new Dimension(space, 0)));
	}

	private void updateRow(Color c) {
		if (this.currentPawn < 0)
			this.currentPawn = 0;
		if (this.currentPawn < this.numberOfPawnPerRow) {
			this.rows.get(this.currentRow)[this.currentPawn].setBackground(c);
			this.rows.get(this.currentRow)[this.currentPawn].setSize(20, 20);
			this.currentPawn++;
		}
	}

	private void resetLastPawn() {
		if (this.currentPawn > 0) {
			this.currentPawn--;
			this.rows.get(this.currentRow)[this.currentPawn]
					.setBackground(Color.GRAY);
			this.rows.get(this.currentRow)[this.currentPawn].setSize(10, 10);
		}
	}

	private void resetLine() {
		for (int i = 0; i < this.numberOfPawnPerRow; ++i)
			resetLastPawn();
	}

	private void validation() {
		PawnGame[] playerSolution = this.rows.get(this.currentRow);
		boolean isCorrectSolution = true;
		
		for (int i = 0; i < playerSolution.length; ++i)
			if (playerSolution[i].getPawnColor() == Color.GRAY) {
				isCorrectSolution = false;
				break;
			}

		if (isCorrectSolution) {
			Integer n = null;

			if (this.manager.checkSolution(this.rows.get(this.currentRow), this.tips.get(this.currentRow))) {
				displaySolution(true);
				manageCommands(false);
				++this.scorePlayer;
				n = JOptionPane
						.showConfirmDialog(
								this.window,
								"Vous avez gagné !!\nVoulez-vous faire une nouvelle partie ?",
								"Victoire !", JOptionPane.YES_NO_OPTION);
			} else if (this.currentRow < this.numberOfRow - 1) {
				this.currentRow++;
				this.currentPawn = 0;
			} else {
				n = JOptionPane
						.showConfirmDialog(
								this.window,
								"Vous avez perdu...\nVoulez-vous faire une nouvelle partie ?",
								"Défaite..", JOptionPane.YES_NO_OPTION);
				displaySolution(true);
				manageCommands(false);
				++this.scoreCPU;
			}

			updateScores();

			if (n != null && n == 0)
				resetAll();
		} else
			JOptionPane.showMessageDialog(this.window,
					"Vous devez remplir la ligne avant de la valider !",
					"Ligne incomplète", JOptionPane.WARNING_MESSAGE);
	}
	
	private void manageCommands(boolean enabled) {
		this.valid.setEnabled(enabled);
		this.resetLine.setEnabled(enabled);
		this.resetPawn.setEnabled(enabled);
	}

	private void resetAll() {
		for (int i = 0; i < this.numberOfRow; ++i) {
			for (int j = 0; j < this.numberOfPawnPerRow; ++j) {
				this.rows.get(i)[j].setPawnColor(Color.GRAY);
				this.rows.get(i)[j].setSize(10, 10);
				this.tips.get(i)[j].setPawnColor(Color.WHITE);
			}
		}

		// 0 = YES
		// 1 = NO
		Integer n = JOptionPane.showConfirmDialog(this.window,
				"Voulez-vous changer de joueur ?", "Changer de joueur ?",
				JOptionPane.YES_NO_OPTION);
		if (n == 0)
			createPlayer();
		
		this.manager.generateSolution();
		displaySolution(false);
		manageCommands(true);
		
		this.currentPawn = 0;
		this.currentRow = 0;
	}
	
	private void displayRules() {
		JFrame rules = new JFrame(this.gameRules.getTitle());
		rules.getContentPane().add(this.gameRules);
		rules.pack();
		rules.setVisible(true);
		rules.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if (o == this.resetPawn)
			resetLastPawn();
		else if (o == this.resetLine)
			resetLine();
		else if (o == this.quit || o == this.quitPlayer)
			System.exit(0);
		else if (o == this.valid)
			validation();
		else if (o == this.newGame)
			resetAll();
		else if (o == this.rules)
			displayRules();
		else if (o == this.validPlayer) {
			if (!name.getText().equals("")) {
				Integer numberOfColors = (Integer) this.colors.getValue();
				this.player
						.setColorNumber(numberOfColors != null ? numberOfColors
								.intValue() : 0);

				Integer numberOfRows = (Integer) this.rowsNumber.getValue();
				this.player.setRowNumber(numberOfRows != null ? numberOfRows
						.intValue() : 0);

				Integer numberOfPawns = (Integer) this.pawns.getValue();
				this.player.setPawnNumber(numberOfPawns != null ? numberOfPawns
						.intValue() : 0);

				this.player.setName(name.getText());
				this.manager.setPlayer(this.player);
				this.manager.generateSolution();
				displayPlate();
			} else
				JOptionPane.showMessageDialog(this.window,
						"Un nom de joueur doit être saisi !",
						"Aucun nom saisi !", JOptionPane.WARNING_MESSAGE);
		} else {
			RoundButton tmp = (RoundButton) ae.getSource();
			updateRow(tmp.getBackground());
		}
	}
}
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
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class GraphicUI implements ActionListener {
	private final JFrame window;
	private final Container globalPane, menuPane, gamePane, playerPane;
	private final JButton newGame, resetLine, valid, quit, resetPawn, validPlayer, quitPlayer;
	private final ArrayList<RoundButton[]> rows = new ArrayList<>();
	private final Mastermind manager;
	private final JTextField name;
	private final JSpinner rowsNumber, pawns, colors;
	private final JLabel nameLabel, rowsLabel, pawnsLabel, colorsLabel;

	private Player player;
	private int currentRow = 0, currentPawn = 0, numberOfPawnPerRow = 4, numberOfRow = 12, numberOfColor = 9;

	public GraphicUI() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		this.player = new Player();

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
		
		this.validPlayer = new JButton("Jouer");
		this.quitPlayer = new JButton("Quitter");

		this.nameLabel = new JLabel("Pseudo :");
		this.name = new JTextField(20);
		this.nameLabel.setFont(new Font(this.nameLabel.getFont().getName(),
				Font.BOLD, this.nameLabel.getFont().getSize()));

		this.rowsLabel = new JLabel("Nombre d'essais :");
		this.rowsNumber = new JSpinner(new SpinnerNumberModel(12, 1, 12, 1));
		this.rowsLabel.setFont(new Font(this.rowsLabel.getFont().getName(),
				Font.BOLD, this.rowsLabel.getFont().getSize()));

		this.pawnsLabel = new JLabel("Nombre de pions :");
		this.pawns = new JSpinner(new SpinnerNumberModel(4, 2, 6, 1));
		this.pawnsLabel.setFont(new Font(this.pawnsLabel.getFont().getName(),
				Font.BOLD, this.pawnsLabel.getFont().getSize()));

		this.colorsLabel = new JLabel("Nombre de couleurs :");
		this.colors = new JSpinner(new SpinnerNumberModel(6, 2, 9, 1));
		this.colorsLabel.setFont(new Font(this.colorsLabel.getFont().getName(),
				Font.BOLD, this.colorsLabel.getFont().getSize()));
		
		this.globalPane = this.window.getContentPane();
		this.globalPane.setLayout(new BorderLayout());

		this.menuPane = new Container();
		this.menuPane.setPreferredSize(new Dimension(200, 400));
		this.menuPane.setLayout(new BoxLayout(this.menuPane, BoxLayout.Y_AXIS));

		this.gamePane = new Container();
		this.gamePane.setLayout(new BoxLayout(this.gamePane, BoxLayout.Y_AXIS));

		this.playerPane = new Container();
		this.playerPane
				.setLayout(new BoxLayout(this.playerPane, BoxLayout.Y_AXIS));
		
		this.resetPawn.addActionListener(this);
		this.resetLine.addActionListener(this);
		this.newGame.addActionListener(this);
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
	}

	private void createPlayer() {
		this.globalPane.removeAll();
		this.globalPane.add(this.playerPane, BorderLayout.CENTER);
		this.window.setTitle("Infos du joueur");
		this.window.pack();
	}

	private void initGameDisplay() {
		this.rows.clear();
		for (int i = 0; i < this.numberOfRow; ++i) {
			RoundButton[] tmp = new RoundButton[this.numberOfPawnPerRow];
			for (int j = 0; j < this.numberOfPawnPerRow; ++j)
				tmp[j] = new RoundButton(Color.GRAY, 10);
			this.rows.add(tmp);
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
		Color[] pawnColors = this.manager.getPawnColors();
		for (int i = 0; i < this.numberOfColor; ++i) {
			RoundButton tmp = new RoundButton(pawnColors[i], 10);
			tmp.addActionListener(this);
			addButton(tmp, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		}

		addButton(this.valid, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		addButton(this.resetPawn, this.menuPane, 0, 10,
				Component.CENTER_ALIGNMENT);
		addButton(this.resetLine, this.menuPane, 0, 10,
				Component.CENTER_ALIGNMENT);
		addButton(this.newGame, this.menuPane, 0, 10,
				Component.CENTER_ALIGNMENT);
		addButton(this.quit, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);

		JLabel scores = new JLabel("SCORES");
		JLabel CPU = new JLabel("CPU : 0");
		JLabel player = new JLabel(this.player.getName() + " : 0");

		scores.setFont(new Font(scores.getFont().getName(), Font.BOLD, 30));
		CPU.setFont(new Font(CPU.getFont().getName(), Font.BOLD, 18));
		player.setFont(new Font(player.getFont().getName(), Font.BOLD, 18));

		scores.setAlignmentX(Component.CENTER_ALIGNMENT);
		CPU.setAlignmentX(Component.RIGHT_ALIGNMENT);
		player.setAlignmentX(Component.RIGHT_ALIGNMENT);

		this.menuPane.add(Box.createRigidArea(new Dimension(0, 100)));
		this.menuPane.add(scores);
		this.menuPane.add(Box.createRigidArea(new Dimension(0, 20)));
		this.menuPane.add(CPU);
		this.menuPane.add(player);
		this.menuPane.setPreferredSize(new Dimension(300, this.menuPane
				.getPreferredSize().height));
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
			c.add(displayPawnSolution(this.numberOfPawnPerRow));
			this.gamePane.add(c);
		}

		Container c = new Container();
		c.setLayout(new FlowLayout());

		JLabel solutionLabel = new JLabel("Solution :");
		solutionLabel.setFont(new Font(solutionLabel.getFont().getName(),
				Font.BOLD, solutionLabel.getFont().getSize()));

		JSeparator x = new JSeparator(SwingConstants.HORIZONTAL);
		x.setPreferredSize(new Dimension(100, 3));

		c.add(solutionLabel);
		c.add(Box.createRigidArea(new Dimension(20, 20)));

		for (int j = 0; j < this.numberOfPawnPerRow; ++j)
			addButton(new RoundButton(Color.GRAY, 10), c, 1, 20, 0);

		this.gamePane.add(x);
		this.gamePane.add(c);
	}

	private Container displayPawnSolution(int pawn) {
		Container c = new Container();
		c.setLayout(new GridLayout(pawn / 2, 2, 5, 5));
		c.setPreferredSize(new Dimension(pawn * 5, pawn * 5));

		for (int i = 0; i < pawn; ++i)
			c.add(new RoundButton(Color.black, 5));
		return c;
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
		if (this.currentRow < this.numberOfRow) {
			this.currentRow++;
			this.currentPawn = 0;
		}
	}

	private void resetAll() {
		for (int i = 0; i < this.numberOfRow; ++i) {
			for (int j = 0; j < this.numberOfPawnPerRow; ++j) {
				this.rows.get(i)[j].setBackground(Color.GRAY);
				this.rows.get(i)[j].setSize(10, 10);
			}
		}
		this.currentPawn = 0;
		this.currentRow = 0;
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
		else if (o == this.validPlayer) {
			if (name.getText() != null) {
				Integer numberOfColors = (Integer) this.colors.getValue();
				this.player.setColorNumber(numberOfColors != null ? numberOfColors.intValue() : 0);

				Integer numberOfRows = (Integer) this.rowsNumber.getValue();
				this.player.setRowNumber(numberOfRows != null ? numberOfRows.intValue() : 0);

				Integer numberOfPawns = (Integer) this.pawns.getValue();
				this.player.setPawnNumber(numberOfPawns != null ? numberOfPawns.intValue() : 0);

				this.player.setName(name.getText());
				this.manager.setPlayer(this.player);
				displayPlate();
			}
		} else {
			RoundButton tmp = (RoundButton) ae.getSource();
			updateRow(tmp.getBackground());
		}
	}
}
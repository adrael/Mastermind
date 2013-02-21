import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class GraphicUI {
	private final JFrame window;
	private final Container globalPane, menuPane, gamePane;
	private final RoundButton green, blue, red, yellow, black, white, none;
	private final JButton newGame, resetLine, valid, quit;

	public GraphicUI() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		this.quit = new JButton("Quitter");
		this.newGame = new JButton("Nouvelle partie");
		this.resetLine = new JButton("Effacer la ligne");
		this.valid = new JButton("Valider");

		this.none = new RoundButton(null, 15);
		this.red = new RoundButton(Color.RED, 15);
		this.blue = new RoundButton(Color.BLUE, 15);
		this.green = new RoundButton(Color.GREEN, 15);
		this.black = new RoundButton(Color.WHITE, 15);
		this.white = new RoundButton(Color.BLACK, 15);
		this.yellow = new RoundButton(Color.YELLOW, 15);

		this.window = new JFrame("Mastermind");
		this.window.setVisible(true);
		this.window.setResizable(false);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.globalPane = this.window.getContentPane();
		this.globalPane.setLayout(new BorderLayout());
		
		this.menuPane = new Container();
		this.menuPane.setPreferredSize(new Dimension(200, 400));
		this.menuPane.setLayout(new BoxLayout(this.menuPane, BoxLayout.Y_AXIS));

		addButton(this.red, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		addButton(this.blue, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		addButton(this.green, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		addButton(this.yellow, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		addButton(this.black, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		addButton(this.white, this.menuPane, 0, 50, Component.CENTER_ALIGNMENT);
		addButton(this.valid, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		addButton(this.resetLine, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		addButton(this.newGame, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		addButton(this.quit, this.menuPane, 0, 10, Component.CENTER_ALIGNMENT);
		
		this.gamePane = new Container();
		this.gamePane.setLayout(new BoxLayout(this.gamePane, BoxLayout.Y_AXIS));
		
		displayGame(this.gamePane, 12, 4);

		this.globalPane.add(menuPane, BorderLayout.EAST);
		this.globalPane.add(gamePane, BorderLayout.WEST);

		this.window.pack();
	}
	
	private void displayGame(Container ctn, int row, int pawn) {
		for(int i = 0; i < row; ++i) {
			Container c = new Container();
			c.setLayout(new FlowLayout());
			c.setPreferredSize(new Dimension(200, 50));
			
			for(int j = 0; j < pawn; ++j)
				 addButton(new RoundButton(Color.GRAY, 8), c, 1, 20, 0);
			
			JSeparator x = new JSeparator(SwingConstants.HORIZONTAL);  
	        x.setPreferredSize(new Dimension(100,3));

			ctn.add(x);
			c.add(displaySolution(pawn));
			ctn.add(c);
		}
	}
	
	private Container displaySolution(int pawn) {
		Container c = new Container();
		c.setLayout(new GridLayout(pawn/2, 2, 5, 5));
		c.setPreferredSize(new Dimension(pawn*5, pawn*5));
		
		for(int i = 0; i < pawn; ++i)
			c.add(new RoundButton(Color.black, 5));
		return c;
	}

	private void addButton(JButton button, Container container, int vh, int space, float alignment) {
		if (alignment > 0)
			button.setAlignmentX(alignment);

		container.add(button);

		if (space > 0)
			if(vh == 0)
				container.add(Box.createRigidArea(new Dimension(0, space)));
			else container.add(Box.createRigidArea(new Dimension(space, 0)));
	}
	
	private static void createAndShowGUI() {
	        try {
				GraphicUI gui = new GraphicUI();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
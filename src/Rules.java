import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Rules extends Container {
	private String title = "", text = "";
	private String[] authors = {};

	public Rules() {
		super();
		this.title = "Title of the game";
		this.text = "Rules";
		this.authors = new String[0];
	}

	public Rules(String title, String text, String[] authors) {
		super();
		setTitle(title);
		setText(text);
		setAuthors(authors);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public String getTitle() {
		return this.title;
	}

	public String getText() {
		return this.text;
	}

	public String[] getAuthors() {
		return this.authors;
	}
	
	public void prepareRules() {
		removeAll();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel("    " + this.title);
		JLabel text = new JLabel("<html>" + this.text.replaceAll("\n", "<br/>") + "</html>");
		JLabel authors = new JLabel("    Ce jeu a été réalisé par :");
		
		title.setFont(new Font(title.getFont().getName(), Font.BOLD, 30));
		
		add(title);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(text);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(authors);
		add(Box.createRigidArea(new Dimension(0, 10)));
		
		for(int i = 0; i < this.authors.length; ++i)
			add(new JLabel("          " + this.authors[i]));
		
		setPreferredSize(new Dimension(500, 500));
	}
	
	public String getRules() {
		String tmp = "Ce jeu a été réalisé par :\n\n";
		for(int i = 0; i < this.authors.length; ++i)
			tmp += "   - " + this.authors[i];
		return this.title + "\n\n\n" + this.text + "\n\n\n" + tmp;
	}
}

public class Main {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					GraphicUI gui = new GraphicUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
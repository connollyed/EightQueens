import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class EightQueensGUI {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				EightQueens model = new EightQueens();
				
				JFrame window = new JFrame("Eight Queens");
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setTitle("N-Queens Problem");
				window.setSize(800, 800);
				//window.setLayout(null);
				
				BoardGUI view = new BoardGUI();
				
				window.setVisible(true);
				window.add(view);
				window.repaint();
				
				
				Controller controller = new Controller(model,view);
				controller.start();
			}
		});
	}
}

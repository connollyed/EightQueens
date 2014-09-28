import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

// view
public class BoardGUI extends JPanel {
	static BufferedImage queen_image = null;

	public BoardGUI() {
		queen_image = getQueenImage();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoard(g);
		drawQueens(g, Controller.current_board);
	}

	public void drawBoard(Graphics g) {
		for (int r = 0; r < Controller.BOARD_SIZE; r++) {
			for (int c = 0; c < Controller.BOARD_SIZE; c++) {

				if ((r + c) % 2 == 0) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.BLACK);
				}
				g.fillRect(r * 80, c * 80, 80, 80);
			}
		}
	}

	public void drawQueens(Graphics g, BoardState current_board) {
		for (int r = 0; r < Controller.BOARD_SIZE; r++) {
			for (int c = 0; c < Controller.BOARD_SIZE; c++) {
				if (current_board.board[r][c] == 1 )
					drawQueen(g, r, c);
			}
		}
	}

	public void drawQueen(Graphics g, int x, int y) {
		g.drawImage(queen_image, (80 * y) + 2, (80 * x) + 5 , null);
	}

	public BufferedImage getQueenImage() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("Queen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int type = img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType();
		return scale(img, type, 80, 80, 0.25, 0.25).getSubimage(0, 0, 75, 80);
	}

	/**
	 * scale image
	 * 
	 * @param sbi
	 *            image to scale
	 * @param imageType
	 *            type of image
	 * @param dWidth
	 *            width of destination image
	 * @param dHeight
	 *            height of destination image
	 * @param fWidth
	 *            x-factor for transformation / scaling
	 * @param fHeight
	 *            y-factor for transformation / scaling
	 * @return scaled image
	 */
	public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
		BufferedImage dbi = null;
		if (sbi != null) {
			dbi = new BufferedImage(dWidth, dHeight, imageType);
			Graphics2D g = dbi.createGraphics();
			AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
			g.drawRenderedImage(sbi, at);
		}
		return dbi;
	}
}

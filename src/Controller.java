import java.awt.Point;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JSlider;

public class Controller extends Thread {
	static final int BOARD_SIZE = 8;
	static BoardState current_board = new BoardState();

	EightQueens model;
	BoardGUI view;
	JFrame window;

	public Controller(EightQueens model, BoardGUI view) {
		this.model = model;
		this.view = view;
	}

	public void run() {
		BoardState board = new BoardState();
		Stack<BoardState> gameboards = new Stack<BoardState>();

		int r = 0;
		int c = 0;
		board.num_of_queens = 0;

		while (c < BOARD_SIZE) {
			while (r < BOARD_SIZE) {
				// System.out.println(r + " " + c);
				if (QueenConflicts(board, r, c) == 0) {
					System.out.println(r + " " + c);

					// Save Current Board State
					board.saveState(r, c, board.num_of_queens);
					gameboards.push(board);

					// Create copy of board
					BoardState newboard = new BoardState();
					newboard.saveState(r, c, board.num_of_queens);
					newboard.copyboard(board.board);

					board = newboard;
					board.board[r][c] = 1;
					board.num_of_queens++;

					printBoard(board);
					current_board = board;
					view.paintImmediately(0, 0, 1000, 1000);

					try {
						Thread.sleep(0);
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}

					if (board.num_of_queens == 8) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
							Thread.currentThread().interrupt();
						}
						//System.exit(0);
						break;
					}
				}

				//printQueenConflicts(board);
				r++;
			}
			c++;

			// checked entire board so backtrack
			if (r > BOARD_SIZE - 1 && c > BOARD_SIZE - 1) {
				board = gameboards.pop();
				System.out.println("Stack Size " + gameboards.size());
				r = board.row;
				c = board.col;
				r = (r + 1) % BOARD_SIZE;
				if (board.row == (BOARD_SIZE - 1)) {
					c = (c + 1) % BOARD_SIZE;
				}
				System.out.println("BOARD POPPED");
				printBoard(board);
			} else {
				r = 0;
			}
		}
		printBoard(board);
	}

	public static int QueenConflicts(BoardState board, int cur_row, int cur_col) {
		// check if any queens are on current row
		int queens = 0;
		for (int r = 0; r < BOARD_SIZE; r++) {
			if (r == cur_row)
				continue;
			if (board.board[r][cur_col] == 1)
				queens++;
		}

		// check if any queens are on current column
		for (int c = 0; c < BOARD_SIZE; c++) {
			if (c == cur_col)
				continue;
			if (board.board[cur_row][c] == 1)
				queens++;
		}

		// check upper left diagonal, row dec, col dec
		for (int i = 1; cur_row - i >= 0 && cur_col - i >= 0; i++) {
			if (board.board[cur_row - i][cur_col - i] == 1)
				queens++;
		}
		// check bottom right diagonal row inc, col inc
		for (int i = 1; cur_row + i < BOARD_SIZE && cur_col + i < BOARD_SIZE; i++) {
			if (board.board[cur_row + i][cur_col + i] == 1)
				queens++;
		}
		// check bottom left diagonal row inc, col dec
		for (int i = 1; cur_row + i < BOARD_SIZE && cur_col - i >= 0; i++) {
			if (board.board[cur_row + i][cur_col - i] == 1)
				queens++;
		}
		// check upper right diagonal row dec, col inc
		for (int i = 1; cur_row - i >= 0 && cur_col + i < BOARD_SIZE; i++) {
			if (board.board[cur_row - i][cur_col + i] == 1)
				queens++;
		}
		return queens;// return number of queen conflicts
	}

	public static void printQueenConflicts(BoardState board) {
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				if (board.board[r][c] == 1)
					System.out.print("X");
				else
					System.out.print(QueenConflicts(board, r, c));
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void printBoard(BoardState board) {
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				if (board.board[r][c] == 1) {
					System.out.print('Q');
				} else {
					System.out.print('-');
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void getSpeed() {
		final int FPS_MIN = 0;
		final int FPS_MAX = 1000;
		final int FPS_INIT = 0; //initial frames per second

		JSlider speed = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);
		//framesPerSecond.addChangeListener(this);

		speed.setLocation(new Point(800, 800));
		//Turn on labels at major tick marks.
		speed.setMajorTickSpacing(10);
		speed.setMinorTickSpacing(1);
		speed.setPaintTicks(true);
		speed.setPaintLabels(true);
	}
}

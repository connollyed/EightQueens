public class BoardState {
	int board[][] = new int[Controller.BOARD_SIZE][Controller.BOARD_SIZE];
	int row;
	int col;
	int num_of_queens;
	
	public void copyboard(int copy[][]){
		for(int r=0;r<Controller.BOARD_SIZE;r++){
			for(int c=0;c<Controller.BOARD_SIZE;c++){
				board[r][c] = copy[r][c];
			}
		}
	}
	public void saveState(int r, int c, int numofqueens){
		row = r;
		col = c;
		num_of_queens = numofqueens;
	}
}

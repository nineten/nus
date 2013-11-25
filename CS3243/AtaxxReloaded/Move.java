public class Move {
	
	private int[][] movement;
	private Move initialMove;
	private int evaluation;

	public Move() {
		movement = new int[2][2];
	}
	
	public Move(int value) {
		evaluation = value;
	}
	
	public Move(int[][] newMove) {
		movement = new int[2][2];
		movement[0][0] = newMove[0][0];
		movement[0][1] = newMove[0][1];
		movement[1][0] = newMove[1][0];
		movement[1][1] = newMove[1][1];
	}
	
	public Move(Move newMove) {
		movement = new int[2][2];
		if (newMove.getValue() != null)
		{
		movement[0][0] = newMove.getValue()[0][0];
		movement[0][1] = newMove.getValue()[0][1];
		movement[1][0] = newMove.getValue()[1][0];
		movement[1][1] = newMove.getValue()[1][1];
		}
		evaluation = newMove.getEvaluation();
		initialMove = newMove.getInitialMove();
	}

	public void setOldXY(int[] oldXY) {
		movement[0][0] = oldXY[0];
		movement[0][1] = oldXY[1];
	}
	
	public void setNewXY(int[] newXY) {
		movement[1][0] = newXY[0];
		movement[1][1] = newXY[1];
	}
	
	public void setInitialMove(Move newIntialMove) {
		initialMove = newIntialMove;
	}
	
	public Move getInitialMove() {
		return initialMove;
	}
	
	public int[][] getValue() {
		return movement;
	}
	
	public void setEvaluation(int value) {
		evaluation = value;
	}
	
	public int getEvaluation() {
		return evaluation;
	}
	
}

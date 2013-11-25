import lejos.nxt.*;
import lejos.robotics.navigation.*;

public class NxtWumpus implements ButtonListener {
	
	private static float moveSpeed = 200;
	private static float gridDistance = 200;
	private static float wheelDiameter = 56;
	private static float axisLength = 111;
	private static Motor motorLeft = Motor.B;
	private static Motor motorRight = Motor.A;
	private static SensorPort light_sp = SensorPort.S1;
	private static SensorPort sound_sp = SensorPort.S2;
	private static LightSensor light = new LightSensor(light_sp, true);
	private static SoundSensor sound = new SoundSensor(sound_sp);
	private static Pilot nxtPilot = new TachoPilot(wheelDiameter, axisLength, motorLeft, motorRight);
	
	private static int l_ShadeBreeze;
	private static int l_ShadeStench;
	private static int l_ShadeGlitter;
	private static int l_ShadeBreezeStench;
	private static int l_ShadeBreezeGlitter;
	private static int l_ShadeStenchGlitter;
	private static int l_ShadeBreezeStenchGlitter;

	private static int c_nothing = 0;
	private static int c_boundary = 1;
	private static int c_breeze = 2;
	private static int c_stench = 3;
	private static int c_glitter = 4;
	private static int c_breezeStench = 5;
	private static int c_breezeGlitter = 6;
	private static int c_stenchGlitter = 7;
	private static int c_breezeStenchGlitter = 8;

	private static String up = "up";
	private static String down = "down";
	private static String left = "left";
	private static String right = "right";
	
	private static boolean isWumpusAlive = true;
	private static boolean isBackTrackMove = false;
	
	private static int currentDirection = 0;
	
	private Grid[][] wumpusWorld;
	private static boolean getOutOfWumpus = false;
	private static boolean outOfWumpus = false;
	private static int curX;
	private static int curY;
	private static int newX;
	private static int newY;
	
	private static boolean notStart = true;
	
	
	public static void main(String[] args) {
		NxtWumpus nxtInstance = new NxtWumpus();
		light.setFloodlight(true);
		nxtInstance.calibrateLight();
		nxtInstance.initiateAI();
	}
	
	public void initiateAI()
	{
		Button.ESCAPE.addButtonListener(this);
		//Initialize starting location
		wumpusWorld = new Grid[19][19];
		curX = 10;
		curY = 10;
		for(int y=0; y<19; y++) {
			for(int x=0; x<19; x++) {
				wumpusWorld[x][y] = new Grid();
			}
		}
		String nextMove;

		Button.RIGHT.addButtonListener(this);
		while (notStart)
		{}
		makeMove(up);
		curY += 1;
		checkColor();
		Sound.beepSequence();
		while (!getOutOfWumpus) {
			notStart = true;
			while (notStart)
			{}
			checkColor();
			System.out.println("Color: "+wumpusWorld[curX][curY].getColor());
			nextMove = decideNextMove();
			makeMove(nextMove);
			Sound.beepSequence();

			updateXY();
			System.out.println("CurrentXY: "+curX+" "+curY);
		}
		
		while (!outOfWumpus) {
			
			makeMove(wumpusWorld[curX][curY].previous);
			
			if (curX == 10 && curY == 10)
				outOfWumpus = true;
		}
		
		System.out.println("We are out of wumpus ");
	}
	

	private void makeMove(String direction) {
		int turnDirection = 0;
		if (direction.equals(up))
		{
			if (!isBackTrackMove)
				wumpusWorld[curX][curY+1].previous = down;
			turnDirection = minimalAngle(turnDirection - currentDirection);
			currentDirection = 0;
		}
		else if (direction.equals(down)) {
			if (!isBackTrackMove)
				wumpusWorld[curX][curY-1].previous = up;
			turnDirection = minimalAngle(turnDirection - currentDirection + 180);
			currentDirection = 180;
		}
		else if (direction.equals(left)) {
			if (!isBackTrackMove)
				wumpusWorld[curX-1][curY].previous = right;
			turnDirection = minimalAngle(turnDirection - currentDirection + 90);
			currentDirection = 90;
		}
		else if (direction.equals(right)) {
			if (!isBackTrackMove)
				wumpusWorld[curX+1][curY+1].previous = left;
			turnDirection = minimalAngle(turnDirection - currentDirection - 90);
			currentDirection = -90;
		}
		
		nxtPilot.rotate(turnDirection);
		nxtPilot.setMoveSpeed(moveSpeed);
		nxtPilot.travel(gridDistance);

		isBackTrackMove = false;
	}

	private String decideNextMove() {
		if (wumpusWorld[curX][curY].boundary) {
			backtrackXY();
			return wumpusWorld[curX][curY].previous;
		}
		else {
			if (wumpusWorld[curX][curY].glitter) {
				pickUpGold();
				getOutOfWumpus = true;
				return "";
			}
			else if (isWumpusAlive && wumpusWorld[curX][curY].stench) {
				findWumpus();
				if (isWumpusAlive == false)
					return makeNormalMove();
				else {
					backtrackXY();
					return wumpusWorld[curX][curY].previous;
				}
					
			}
			else if (wumpusWorld[curX][curY].breeze) {
				backtrackXY();
				return wumpusWorld[curX][curY].previous;
			}
			else
			{
				String value = makeNormalMove();
				System.out.println("A normal move : "+value);
				return value;
			}
		}
	}

	private String makeNormalMove() {
		System.out.println("color of up : "+wumpusWorld[curX][curY+1].getColor());
		if (wumpusWorld[curX][curY+1].getColor() == -1) {
				newY = curY + 1;
				newX = curX;
				return up;
		}
		if (wumpusWorld[curX][curY-1].getColor() == -1) {
				newY = curY - 1;
				newX = curX;
				return down;
		}
		if (wumpusWorld[curX+1][curY].getColor() == -1) {
				newX = curX + 1;
				newX = curX;
				return right;
		}
		if (wumpusWorld[curX-1][curY].getColor() == -1) {
				newX = curX - 1;
				newX = curX;
				return left;
		}
		
		backtrackXY();
		return wumpusWorld[curX][curY].previous;
		
	}


	private void backtrackXY() {
		isBackTrackMove = true;
		if (wumpusWorld[curX][curY].previous.equals(up))
			newY = curY + 1;
		if (wumpusWorld[curX][curY].previous.equals(down))
			newY = curY - 1;
		if (wumpusWorld[curX][curY].previous.equals(left))
			newX = curX - 1;
		if (wumpusWorld[curX][curY].previous.equals(right))
			newX = curX + 1;
	}
	
	private void updateXY() {
		curY = newY;
		curX = newX;
	}

	private void findWumpus() {
		if (wumpusWorld[curX+1][curY+1].stench == true) {
			if (wumpusWorld[curX+1][curY-1].stench == true)
				shootArrow(right);
			else {
				if (wumpusWorld[curX+1][curY-1].getColor() != -1)
					shootArrow(up);
				else {
					if (wumpusWorld[curX-1][curY+1].stench == true)
						shootArrow(up);
					else  {
						if (wumpusWorld[curX-1][curY+1].getColor() != -1)
							shootArrow(right);
					}
				}
			}
		}
		else if (wumpusWorld[curX+1][curY-1].stench == true) {
			if (wumpusWorld[curX+1][curY+1].stench == true)
				shootArrow(right);
			else {
				if (wumpusWorld[curX+1][curY+1].getColor() != -1)
					shootArrow(down);
				else {
					if (wumpusWorld[curX-1][curY-1].stench == true)
						shootArrow(down);
					else {
						if (wumpusWorld[curX-1][curY-1].getColor() != -1)
							shootArrow(right);
					}
				}
			}
		}
		else if (wumpusWorld[curX-1][curY-1].stench == true) {
			if (wumpusWorld[curX-1][curY+1].stench == true)
				shootArrow(left);
			else {
				if (wumpusWorld[curX-1][curY+1].getColor() != -1)
					shootArrow(down);
				else {
					if (wumpusWorld[curX+1][curY-1].stench == true)
						shootArrow(down);
					else {
						if (wumpusWorld[curX+1][curY-1].getColor() != -1)
							shootArrow(left);
					}
				}
			}
		}
		else if (wumpusWorld[curX-1][curY+1].stench == true) {
			if (wumpusWorld[curX-1][curY-1].stench == true)
				shootArrow(left);
			else {
				if (wumpusWorld[curX-1][curY-1].getColor() != -1)
					shootArrow(up);
				else {
					if (wumpusWorld[curX+1][curY+1].stench == true)
						shootArrow(up);
					else {
						if (wumpusWorld[curX+1][curY+1].getColor() != -1)
							shootArrow(left);
					}
				}
			}
		}
	}

	private void shootArrow(String direction) {
		int turnDirection = 0;
		if (direction.equals(up)) {
			turnDirection = minimalAngle(turnDirection - currentDirection);
			currentDirection = 0;
		}
		else if (direction.equals(down)) {
			turnDirection = minimalAngle(turnDirection - currentDirection + 180);
			currentDirection = 180;
		}
		else if (direction.equals(left)) {
			turnDirection = minimalAngle(turnDirection - currentDirection + 90);
			currentDirection = 90;
		}
		else if (direction.equals(right)) {
			turnDirection = minimalAngle(turnDirection - currentDirection - 90);
			currentDirection = -90;
		}
		
		nxtPilot.rotate(turnDirection);
		
		Sound.buzz();
		Sound.beepSequence();
		
		isWumpusAlive = false;
	}

	private int minimalAngle(int turnDirection) {
		if (turnDirection < -90)
			turnDirection = 360 - turnDirection;
		if (turnDirection >= 360)
			turnDirection = turnDirection % 360;
		if (turnDirection == 270)
			turnDirection = -90;
		return turnDirection;
	}

	private void pickUpGold() {
		Sound.beepSequenceUp();
		Sound.twoBeeps();
	}

	private void calibrateLight() {
		Button.ESCAPE.addButtonListener(this);
		System.out.println("Place sensor above pure white...");
		Button.waitForPress();
		light.calibrateHigh();
		
		System.out.println("Place sensor above pure black...");
		Button.waitForPress();
		light.calibrateLow();

		System.out.println("Place sensor above pure white...");
		Button.waitForPress();
		int high = light.getLightValue();
		System.out.println("Place sensor above pure black...");
		Button.waitForPress();
		int low = light.getLightValue();
		
		System.out.println("Place sensor above shade for BREEZE...");
		Button.waitForPress();
		l_ShadeBreeze =  light.getLightValue();
		
		System.out.println("Place sensor above shade for STENCH...");
		Button.waitForPress();
		l_ShadeStench =  light.getLightValue();
		
		System.out.println("Place sensor above shade for GLITTER...");
		Button.waitForPress();
		l_ShadeGlitter =  light.getLightValue();
		
		System.out.println("Place sensor above shade for BREEZE + STENCH...");
		Button.waitForPress();
		l_ShadeBreezeStench =  light.getLightValue();
		
		System.out.println("Place sensor above shade for BREEZE + GLITTER...");
		Button.waitForPress();
		l_ShadeBreezeGlitter =  light.getLightValue();
		
		System.out.println("Place sensor above shade for STENCH + GLITTER...");
		Button.waitForPress();
		l_ShadeStenchGlitter =  light.getLightValue();

		System.out.println("Place sensor above shade for BREEZE + STENCH + GLITTER...");
		Button.waitForPress();
		l_ShadeBreezeStenchGlitter =  light.getLightValue();

		System.out.println(light.getHigh());
		System.out.println(light.getLow());
		System.out.println(high);
		System.out.println(low);
		System.out.println(l_ShadeBreeze);
		System.out.println(l_ShadeStench);
		System.out.println(l_ShadeGlitter);
		System.out.println(l_ShadeBreezeStench);
		//System.out.println(l_ShadeBreezeGlitter);
		//System.out.println(l_ShadeStenchGlitter);
		//System.out.println(l_ShadeBreezeStenchGlitter);

		//System.out.println("Press Right to start NxtWumpus");
	}
	
	private void checkColor() {
		int color = light.getLightValue();
		wumpusWorld[curX][curY].setColor(color);
		System.out.println(color + " : " + l_ShadeStench);
		
		if (color == light.getLow()) {
			wumpusWorld[curX][curY].boundary = true;
		}
		if (color == l_ShadeBreeze) {
			wumpusWorld[curX][curY].breeze = true;
		}
		if (color == l_ShadeStench) {
			wumpusWorld[curX][curY].stench = true;
		}
		if (color == l_ShadeGlitter) {
			wumpusWorld[curX][curY].glitter = true;
		}
		if (color == l_ShadeBreezeStench) {
			wumpusWorld[curX][curY].breeze = true;
			wumpusWorld[curX][curY].stench = true;
		}
		if (color == l_ShadeBreezeGlitter) {
			wumpusWorld[curX][curY].breeze = true;
			wumpusWorld[curX][curY].glitter = true;
		}
		if (color == l_ShadeStenchGlitter) {
			wumpusWorld[curX][curY].stench = true;
			wumpusWorld[curX][curY].glitter = true;
		}
		if (color == l_ShadeBreezeStenchGlitter) {
			wumpusWorld[curX][curY].breeze = true;
			wumpusWorld[curX][curY].stench = true;
			wumpusWorld[curX][curY].glitter = true;
		}
	}

	@Override
	public void buttonPressed(Button arg0) {
		if (arg0 == Button.ESCAPE)
			System.exit(0);
		if (arg0 == Button.RIGHT)
			notStart = false;
	}

	@Override
	public void buttonReleased(Button arg0) { 	
	}
}

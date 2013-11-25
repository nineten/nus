import java.util.*;
import java.io.*;

/**
* Done By : U087139J 
* Parts of file are extracted from RandomPlayer.java done by Ng, Jun Ping
*/
public class AtaxxReloaded {
	// Used by alpha and beta
    private static final int NEGATIVE_INFINITY = Integer.MIN_VALUE;
	private static final int POSITIVE_INFINITY = Integer.MAX_VALUE;
    
	// Used by MiniMax to determine when to cutoff.
    private int cutoff = 4;

	private String m_ClientName;
	
    // These variables are used to track information about the game environment
    private int m_TimeOutInSeconds; // How many seconds we have per move
    private int m_PlayerID;         // In this assignment, we assume we are always 1!
    
    // These variables deal with the I/O
    private BufferedReader m_In;
    private BufferedWriter m_Out;

	/**
     *	AtaxxReloaded Constructor
     */
    public AtaxxReloaded() {
        // More customisations here if necessary
        // ===============
        m_ClientName = "-=|[O.o]|=-  jinkouchinou"; // <-- Change this!
        m_TimeOutInSeconds = 3; // <-- This is just for your reference. The server will manage the timeouts!
        m_PlayerID = 1;         // We assume we are player 1. Change this, and likely you won't do very well!    
        
        
        // Set up buffers to make it easier to deal with stream I/O
        // You don't need to change this likely!
        try {
            m_In = new BufferedReader(new InputStreamReader(System.in));
            m_Out = new BufferedWriter(new OutputStreamWriter(System.out));
        } catch (Exception e) {
            // Honestly, if we have an exception, we cannot recover.
            // All the best!
            //System.err.println("Unable to set up buffers for I/O.");
        }
    } // end Constructor
    
	/**
	 * 	Main method
     *	This is where it all begins
     */
	public static void main(String args[]) {
		AtaxxReloaded l_Player = new AtaxxReloaded();
        l_Player.GoOnline();
	}

    /**
     * This function decides a move to make next.
     * We are only making random moves here.
     * And the function only moves to immediately adjacent tiles even though
     * we can make jumps.
     *
     * You should change this to do something more intelligent.
     * The return value should be a 2-D array with the format
     * [0][0] X-OLD
     * [0][1] Y-OLD
     * [1][0] X-NEW
     * [1][1] Y-NEW
     * where X-OLD is the x coordinate of the tile to move from
     *       Y-OLD is the y coordinate of the tile to move from
     *       X-NEW is the x coordinate of the tile to move to
     *       Y-NEW is the y coordinate of the tile to move to
     *
     * If your move is invalid, your turn will be forfeited.
     *
     * @return array containing old move, and the new move to take.
     * @throws Exception 
     */
    private int[][] MakeMove(GameBoard a_GameBoard) throws Exception {
    	int[][] resultMove = new int[2][2];
        
    	// Lets start the prune!
    	resultMove = AlphaBetaSearch(a_GameBoard).getValue();
    	
    	return resultMove;
    } // end MakeMove

	/**
     *	MiniMax with Alpha-Beta Pruning
     */
    private Move AlphaBetaSearch(GameBoard a_GameBoard) throws Exception {
    	Move decidedMove;
    	// sets alpha to "negative infinity" and "positive infinity"
    	decidedMove = MaxValue(a_GameBoard, NEGATIVE_INFINITY, POSITIVE_INFINITY, null, null, 0).getInitialMove();

    	return decidedMove;
    } // end AlphaBetaSearch

	/**
     *	Max part of MiniMax with Alpha-Beta Pruning
     */
    private Move MaxValue(GameBoard a_GameBoard, int alpha, int beta, Move initialMove, Move currentMove, int ply) throws Exception {
    	// increments the ply to know when to stop
    	ply++;
    	
    	if (ply >= cutoff) {
    		Move tempMove = new Move(EvaluateGameBoard(a_GameBoard, currentMove));
    		return tempMove;
    	}
    	else {
    		Move best_move = new Move(NEGATIVE_INFINITY);
    		
    		// makes a list of all the moves
    	    List<Move> listOfMoves = GenerateMoves(a_GameBoard, 1, ply);
    	    
    	    // if there are no moves, lets just evaluate the board.
    	    if (listOfMoves.size() == 0)
    	    	return EvaluateGameBoard(a_GameBoard, currentMove);
    	    
    	    for (int x=0; x<listOfMoves.size(); x++) {
    	    	// We want to save the first move of that subtree so that we know the move to pick at the end
    	    	if (ply == 1) {
    	    		initialMove = listOfMoves.get(x);
    	    	}
    	    	// sets each move so that we know whos the parent of that move.
    	    	listOfMoves.get(x).setInitialMove(initialMove);
    	    	
    	    	Move possibleMove = MinValue(ApplyMove(a_GameBoard, listOfMoves.get(x), 1), alpha, beta, initialMove, listOfMoves.get(x), ply);

    	    	// If the move is better we want it.
    	    	if (possibleMove.getEvaluation() > best_move.getEvaluation()) {
    	    		best_move = new Move(possibleMove);
     	    		alpha = possibleMove.getEvaluation();
    	    	}

    	    	// Back tracks the board to the previous move so that a new move can be applied
    		    a_GameBoard.backTrack();
    		    
    	    	// Ignore remaining moves
    		    if (beta < alpha)
    		    	return best_move;
    	    } // end loop
    	    return best_move;
    	} // end else
	} // end MaxValue()
	
	/**
     *	Min part of MiniMax with Alpha-Beta Pruning
     */
	private Move MinValue(GameBoard a_GameBoard, int alpha, int beta, Move initialMove, Move currentMove, int ply) throws Exception {
    	// increments the ply to know when to stop
		ply++;
    	
    	if (ply >= cutoff) {
    		Move tempMove = new Move(EvaluateGameBoard(a_GameBoard, currentMove));
    		return tempMove;
    	}
    	else {
    		Move best_move = new Move(POSITIVE_INFINITY);
    		
    		// makes a list of all the moves
    	    List<Move> listOfMoves = GenerateMoves(a_GameBoard, 2, ply);

    	    // if there are no moves, lets just evaluate the board.
    	    if (listOfMoves.size() == 0)
    	    	return EvaluateGameBoard(a_GameBoard, currentMove);
    	    
    	    for (int x=0; x<listOfMoves.size(); x++) {
    	    	// sets each move so that we know whos the parent of that move.
    	    	listOfMoves.get(x).setInitialMove(initialMove);
    	    	
    	    	Move possibleMove = MaxValue(ApplyMove(a_GameBoard, listOfMoves.get(x), 2), alpha, beta, initialMove, listOfMoves.get(x), ply);

    	    	// If the move is worst we want it.
    	    	if (possibleMove.getEvaluation() < best_move.getEvaluation())  {
    	    		best_move = new Move(possibleMove);
    	    		beta = possibleMove.getEvaluation();
    	    	}

    	    	// Back tracks the board to the previous move so that a new move can be applied
    	    	a_GameBoard.backTrack();
    	    	
    	    	// Ignore remaining moves
    	    	if (beta < alpha)
    		    	return best_move;
    	    } // end loop
    	    return best_move;
    	} // end else
	} // end MinValue()
	
	/**
     *	Method to apply a move.
     *	Note: It edits the gameboard given by adding a new board to the list.
     */
	private GameBoard ApplyMove(GameBoard a_GameBoard, Move move, int player) {
		// Calls the gameboard to edit itself, cause we dont want to edit from outside the class.
		a_GameBoard.DoMove(move, player);
		
		return a_GameBoard;
	} // end ApplyMove()
	
	/**
     *	Method to generate all avaliable moves at the current stage.
     */
	private List<Move> GenerateMoves(GameBoard a_GameBoard, int player, int ply) throws Exception {
		
		List<Move> listOfMoves = new ArrayList<Move>();
		ArrayList<int[]> startingTiles = GetPossibleStartLocations(a_GameBoard, player);

		// Find moves for each starting tile.
		for (int i=0; i<startingTiles.size(); i++) {
			listOfMoves.addAll(FindPossibleMove(a_GameBoard, startingTiles.get(i), ply));
		}
		
		return listOfMoves;
	} // end GenerateMoves()
	
	/**
     *	Method to find all possible moves, given a tile.
     */
	private List<Move> FindPossibleMove(GameBoard a_GameBoard, int[] xy, int ply) throws Exception {
			int x = xy[0];
			int y = xy[1];
			int[][] newMove = new int[2][2];
			newMove[0][0] = x;
			newMove[0][1] = y;
			
			// Ensures that the board is on the correct ply.
			if (a_GameBoard.getSize() > ply) {
				a_GameBoard.backTrack();
			}
				
			List<Move> possibleMove = new ArrayList<Move>();
			
			//Adjacent Moves
			if (x != 0)
				if (a_GameBoard.GetValueAtTile(x-1, y) == 0) {
					newMove[1][0] = x-1;
					newMove[1][1] = y;
					possibleMove.add(new Move(newMove));
				}
			if (x != 6)
				if (a_GameBoard.GetValueAtTile(x+1, y) == 0) {
					newMove[1][0] = x+1;
					newMove[1][1] = y;
					possibleMove.add(new Move(newMove));
				}
			if (y != 0)
				if (a_GameBoard.GetValueAtTile(x, y-1) == 0) {
					newMove[1][0] = x;
					newMove[1][1] = y-1;
					possibleMove.add(new Move(newMove));
				}
			if (y != 6)
				if (a_GameBoard.GetValueAtTile(x, y+1) == 0) {
					newMove[1][0] = x;
					newMove[1][1] = y+1;
					possibleMove.add(new Move(newMove));
				}
			if ((x != 0) && (y != 0))
				if (a_GameBoard.GetValueAtTile(x-1, y-1) == 0) {
					newMove[1][0] = x-1;
					newMove[1][1] = y-1;
					possibleMove.add(new Move(newMove));
				}
			if ((x != 0) && (y != 6))
				if (a_GameBoard.GetValueAtTile(x-1, y+1) == 0) {
					newMove[1][0] = x-1;
					newMove[1][1] = y+1;
					possibleMove.add(new Move(newMove));
				}
			if ((x != 6) && (y != 0))
				if (a_GameBoard.GetValueAtTile(x+1, y-1) == 0) {
					newMove[1][0] = x+1;
					newMove[1][1] = y-1;
					possibleMove.add(new Move(newMove));
				}
			if ((x != 6) && (y != 6))
				if (a_GameBoard.GetValueAtTile(x+1, y+1) == 0) {
					newMove[1][0] = x+1;
					newMove[1][1] = y+1;
					possibleMove.add(new Move(newMove));
				}
			
			//Jump Moves
			if (x > 1)
				if (a_GameBoard.GetValueAtTile(x-2, y) == 0) {
					newMove[1][0] = x-2;
					newMove[1][1] = y;
					possibleMove.add(new Move(newMove));
				}
			if (x > 2)
				if (a_GameBoard.GetValueAtTile(x-3, y) == 0) {
					newMove[1][0] = x-3;
					newMove[1][1] = y;
					possibleMove.add(new Move(newMove));
				}
			if (x < 5)
				if (a_GameBoard.GetValueAtTile(x+2, y) == 0) {
					newMove[1][0] = x+2;
					newMove[1][1] = y;
					possibleMove.add(new Move(newMove));
				}
			if (x < 4)
				if (a_GameBoard.GetValueAtTile(x+3, y) == 0) {
					newMove[1][0] = x+3;
					newMove[1][1] = y;
					possibleMove.add(new Move(newMove));
				}
			if (y > 1)
				if (a_GameBoard.GetValueAtTile(x, y-2) == 0) {
					newMove[1][0] = x;
					newMove[1][1] = y-2;
					possibleMove.add(new Move(newMove));
				}
			if (y > 2)
				if (a_GameBoard.GetValueAtTile(x, y-3) == 0) {
					newMove[1][0] = x;
					newMove[1][1] = y-3;
					possibleMove.add(new Move(newMove));
				}
			if (y < 5)
				if (a_GameBoard.GetValueAtTile(x, y+2) == 0) {
					newMove[1][0] = x;
					newMove[1][1] = y+2;
					possibleMove.add(new Move(newMove));
				}
			if (y < 4)
				if (a_GameBoard.GetValueAtTile(x, y+3) == 0) {
					newMove[1][0] = x;
					newMove[1][1] = y+3;
					possibleMove.add(new Move(newMove));
				}
			if ((y < 5) && (x > 1))
				if (a_GameBoard.GetValueAtTile(x-2, y+2) == 0) {
					newMove[1][0] = x-2;
					newMove[1][1] = y+2;
					possibleMove.add(new Move(newMove));
				}
			if ((y < 5) && (x < 5))
				if (a_GameBoard.GetValueAtTile(x+2, y+2) == 0) {
					newMove[1][0] = x+2;
					newMove[1][1] = y+2;
					possibleMove.add(new Move(newMove));
				}
			if ((y > 1) && (x > 1))
				if (a_GameBoard.GetValueAtTile(x-2, y-2) == 0) {
					newMove[1][0] = x-2;
					newMove[1][1] = y-2;
					possibleMove.add(new Move(newMove));
				}
			if ((y > 1) && (x < 5))
				if (a_GameBoard.GetValueAtTile(x+2, y-2) == 0) {
					newMove[1][0] = x+2;
					newMove[1][1] = y-2;
					possibleMove.add(new Move(newMove));
				}

		return possibleMove;
	} // end FindPossibleMove()
	
	/**
     *	The Evaluation function.
     */
	private Move EvaluateGameBoard(GameBoard a_GameBoard, Move currentMove) throws Exception {
		int count = 0;

		for (int x=0; x<a_GameBoard.GetBoardLength(); x++) {
			for (int y=0; y<a_GameBoard.GetBoardLength(); y++) {
				int temp = a_GameBoard.GetValueAtTile(x, y);
				// It is better to have a tile of ours.
				if (temp == 1)
					count++;
				// It is worst to have a tile of opponent.
				if (temp == 2)
					count--;
				// This tweak wins basic logic above.
				// The following does checking of U shaped hazards or L shaped ones that a 4 ply search might not detect.
				if (temp == 0) {
					// checks for our hazards and reduces the score
					count -= CheckNeighbours(a_GameBoard, x, y, 1);
					// checks for opponents hazards and increases the score
					count += CheckNeighbours(a_GameBoard, x, y, 2);
				}
			}
		}

		//System.out.println("Count is " + count);
		currentMove.setEvaluation(count);
		return currentMove;
	} // end EvaluateGameBoard()
	
	/**
     *	Method to check for U and L shaped hazards.
     */
	private int CheckNeighbours(GameBoard a_GameBoard, int x, int y, int player) throws Exception {
		int possibleHazard = 0;
		// Minimum number of adjacent tiles to be considered as hazard
		int minHazard = 4;
		// This penalty is initialised as 0 and is higher when theres more adjacent tiles
		int penalty = 0;
		
		//Adjacent Moves
		if (x != 0)
			if (a_GameBoard.GetValueAtTile(x-1, y) == player) {
				possibleHazard++;
			}
		if (x != 6)
			if (a_GameBoard.GetValueAtTile(x+1, y) == player) {
				possibleHazard++;
			}
		if (y != 0)
			if (a_GameBoard.GetValueAtTile(x, y-1) == player) {
				possibleHazard++;
			}
		if (y != 6)
			if (a_GameBoard.GetValueAtTile(x, y+1) == player) {
				possibleHazard++;
			}
		if ((x != 0) && (y != 0))
			if (a_GameBoard.GetValueAtTile(x-1, y-1) == player) {
				possibleHazard++;
			}
		if ((x != 0) && (y != 6))
			if (a_GameBoard.GetValueAtTile(x-1, y+1) == player) {
				possibleHazard++;
			}
		if ((x != 6) && (y != 0))
			if (a_GameBoard.GetValueAtTile(x+1, y-1) == player) {
				possibleHazard++;
			}
		if ((x != 6) && (y != 6))
			if (a_GameBoard.GetValueAtTile(x+1, y+1) == player) {
				possibleHazard++;
			}
		
		// well if theres hazards, we want to know
		if (possibleHazard >= minHazard)
			penalty = possibleHazard - minHazard + 1;
		
		return penalty;
	} // end checkNeighbours()


	/**
     * This function is the main part of this class.
     * We will wait for a command from stdin.
     * It could be one of:
     * 1. identify
     * 2. move
     * For more details, please refer to the course web page.
     * After servicing the command, we will exit the function (and this program)
     *
     */
    public void GoOnline() {

        // Wait for command
        String l_Command = ReadLineFromStdin();
        
        if (l_Command.compareTo("identify") == 0) {
            
            // We are asked for our name
            SendLineToStdout(m_ClientName);
            
        } else if (l_Command.compareTo("move") == 0) {
            
            // We need to put on our thinking cap and decide on a move to make
            // 1. Read in details about the game environment first
            try {
                String l_CurrentRoundNumStr = ReadLineFromStdin();
                int l_CurrentRoundNum = new Integer(l_CurrentRoundNumStr).intValue();
                String l_LengthOfSquareBoardStr = ReadLineFromStdin();
                int l_LengthOfSquareBoard = new Integer(l_LengthOfSquareBoardStr).intValue();
                String l_GameBoardStr = "";
                for (int i = 0; i < l_LengthOfSquareBoard; ++i) {
                    l_GameBoardStr += ReadLineFromStdin();
                }
                
                GameBoard l_GameBoard = new GameBoard(l_LengthOfSquareBoard, l_GameBoardStr);
                int[][] l_MoveToMake = MakeMove(l_GameBoard);
                    
                // If we managed to get a move, we'd send it to the server            
                if (l_MoveToMake != null) {
                
                    // Send the move to the server
                    // We need to convert it to a string first    
                    String l_MoveToMakeStr = "";

                    l_MoveToMakeStr += 
			(new Integer(l_MoveToMake[0][0])).toString() + ":" +
			(new Integer(l_MoveToMake[0][1])).toString() + ":" +
			(new Integer(l_MoveToMake[1][0])).toString() + ":" +
			(new Integer(l_MoveToMake[1][1])).toString() + ":";

                    SendLineToStdout(l_MoveToMakeStr);
                    
                } else {
                    
                    // We can't find a move to make.
                    // Silently pass then...
                    //System.err.println("Unable to find a move to make");
                    
                }
                
                
            } catch (NumberFormatException e) {
                // Error receiving input from server.
                // Hard to recover from, so we quit.
            } catch (Exception e) {
            	e.printStackTrace();
                // Probably an error receiving inputs from the server.
                // We have to quit.
            }           
            
        } else {
            
            // Unknown command.            
            // We'd terminate then.
            
        } // end if-else...
        
        
        // That's all.
        
    } // end PlayGame()

    /**
     * This function goes through the game board, and returns a set of possible
     * start locations from which we can make a move from.
     * A possible start location will be tiles which we already occupy, and
     * which have adjacent space for us to move into.
     * @param a_GameBoard [in] the current game board
     * @param player 
     * @return an array list of possible locations, represented as an 2-element
     *         array.
     * @throws Exception 
     *
     */
    private ArrayList<int[]> GetPossibleStartLocations(GameBoard a_GameBoard, int player) throws Exception {
    
        ArrayList<int[]> l_ReturnList = new ArrayList<int[]>();
        
        // Go through every tile
        for (int i = 0; i < a_GameBoard.GetBoardLength(); ++i) {
            for (int j = 0; j < a_GameBoard.GetBoardLength(); ++j) {
                if (a_GameBoard.GetValueAtTile(i,j) == player) {
	                int[] l_T = new int[2];
	                l_T[0] = i;
	                l_T[1] = j;
	                l_ReturnList.add(l_T);
                }
            }
        }
        return l_ReturnList;
    
    } // end GetPossibleStartLocations()
    
    /**
     * This function retrieves a line from System.in.
     * The line is expected to be terminated by a \n.
     * However we do not return the \n from this function.
     *
     * @return retrieved line, or null on errors.
     */
    private String ReadLineFromStdin() {
    
        try {
        
            // Listen on stdin 
            String l_InLine = m_In.readLine();
            if (l_InLine == null) {
                // The input stream seems to have closed.
                return null;
            } else {
                return l_InLine;
            }
           
        } catch (IOException e) {
            return null;
        }
        
    
    } // end RecvFromServer()
    
    /**
     * This function takes an input String and sends it to the server.
     * We will terminate the string to send with a \n.     
     *
     * @param a_StringToSend [in] the String to transmit to the server
     *
     */
    private void SendLineToStdout(String a_StringToSend) {
    
        //System.err.println("Sending to server: " + a_StringToSend);        
        try {
            m_Out.write(a_StringToSend);
            m_Out.flush();
        } catch (IOException e) {
            // Error, oh well...
        }
    
    } // end SendToServer()

    
	/**
     *	GameBoard Class
     */
    private class GameBoard {
        
        // Holds information about the game board
        private int m_BoardLength;
        //private int m_GameBoard[][];
        private List<int[][]> m_GameBoard = new ArrayList<int[][]>();
        
        /**
         * Constructor.
         * Initialises an instance of this game board object.
         * @param a_BoardLength [in] the length of the square game board.
         * @param a_BoardDetails [in] a string which captures the state of the game board.
         * @throws Exception if there are errors with the provided arguments.
         */
        public GameBoard(int a_BoardLength, String a_BoardDetails) throws Exception {
            // Set up the data structures
            m_BoardLength = a_BoardLength;
            int[][] newGameBoard = null;
            
            for (int i = 0; i < a_BoardLength; ++i) {
            
                newGameBoard = new int[a_BoardLength][];
                
                for (int j = 0; j < a_BoardLength; ++j) {                
                	newGameBoard[j] = new int[a_BoardLength];
                } // end for j
                
            } // end for i
            m_GameBoard.add(newGameBoard);
            
            // Populate the board with what we know
            StringTokenizer st = new StringTokenizer(a_BoardDetails, ":");            
            for (int i = 0; i < a_BoardLength; ++i) {
                for (int j = 0; j < a_BoardLength; ++j) {
                    try {
                        String Value = st.nextToken();
                        // Note we do j,i and not i,j so that the first index can
                        // refer to the column, and the second index refers to the row
                        m_GameBoard.get(0)[j][i] = (new Integer(Value)).intValue();
                    } catch (NoSuchElementException e) {                        
                        throw new Exception("Unable to parse received board information.");
                    } catch (NumberFormatException e) {                        
                        throw new Exception("Invalid tile information");
                    }
                } // end for j
            } // end for i
        
        } // end constructor
        
    	/**
         *	Method to back track to the previous board
         */
        public void backTrack() {
			m_GameBoard.remove(m_GameBoard.size()-1);
		} // end backTrack()
        
    	/**
         *	Retrieves how many boards there is. That is how many moves made
         */
		public int getSize() {
			return m_GameBoard.size();
		} // end getSize()

		/**
         * Retrieves the length of the board. This is a square board remember!
         * @return number of tiles on one side of the square board.
         */
        public int GetBoardLength() {
            return m_BoardLength;
        } // end GetBoardLength()
        
        
        /**
         * This function retrieves the current value at a particular tile on the game board.
         *
         * @param i [in] the x-coordinate
         * @param j [in] the y-coordinate
         * @return the value at the tile position specified.
         * @throws InvalidParameterException if the arguments are not valid.
         */
        public int GetValueAtTile(int i, int j) throws Exception {
        
            if ((i >= 0 && i < m_BoardLength) && (j >= 0 && j < m_BoardLength)) {
            	//System.out.println(m_GameBoard.size());
                return m_GameBoard.get(m_GameBoard.size()-1)[i][j];
            } else {
                throw new Exception("Input arguments not valid!");
            }
            
        } // end GetValueAtTile()
        
        /**
         *	Method to make the move.
         *	Note: It adds a new board with the move applied to the list of boards
         */
        public void DoMove(Move move, int player) {
        	int[][] moveInArray = move.getValue();
        	int[][] newGameBoard = CopyArray(m_GameBoard.get(m_GameBoard.size()-1));
        	// adds the new board (Move not made yet)
        	m_GameBoard.add(newGameBoard);

        	// Makes the final move and infects the surrounding
        	MoveTile(moveInArray, player);
        	
        	// Checks if the move is a jump or a spawn. Removes the starting tile accordingly
        	if (IsSpawningMove(moveInArray) == false) {
        		m_GameBoard.get(m_GameBoard.size()-1)[moveInArray[0][0]][moveInArray[0][1]] = 0;
        	}
        } // end DoMove()

        /**
         *	Method to copy a 2D array
         */
		private int[][] CopyArray(int[][] arrayToCopy) {
			int[][] copiedArray = new int[m_BoardLength][m_BoardLength];
			for (int i=0; i<m_BoardLength; i++)
			{
				for (int j=0; j<m_BoardLength; j++) {
					copiedArray[i][j] = arrayToCopy[i][j];				
				}
			}
			return copiedArray;
		} // end CopyArray()

        /**
         *	Method to move a tile to the final location. Infects the surrounding.
         */
		private void MoveTile(int[][] moveInArray, int player) {
			int x = moveInArray[1][0];
			int y = moveInArray[1][1];
			int opponent = 2;
    		if (player == 2)
    			opponent = 1;
    		
    		// makes the final tile a tile of the player
    		m_GameBoard.get(m_GameBoard.size()-1)[x][y] = player;
    		
    		// south
    		if (y != 6)
	    		if (m_GameBoard.get(m_GameBoard.size()-1)[x][y+1] == opponent)
	    			m_GameBoard.get(m_GameBoard.size()-1)[x][y+1] = player;
    		// south-east
    		if ((y != 6) && (x != 6))
	    		if (m_GameBoard.get(m_GameBoard.size()-1)[x+1][y+1] == opponent)
	    			m_GameBoard.get(m_GameBoard.size()-1)[x+1][y+1] = player;
    		// south-west
    		if ((y != 6) && (x != 0))
	    		if (m_GameBoard.get(m_GameBoard.size()-1)[x-1][y+1] == opponent)
	    			m_GameBoard.get(m_GameBoard.size()-1)[x-1][y+1] = player;
    		// north
    		if (y != 0)
	    		if (m_GameBoard.get(m_GameBoard.size()-1)[x][y-1] == opponent)
	    			m_GameBoard.get(m_GameBoard.size()-1)[x][y-1] = player;
    		// north-east
    		if ((x != 6) && (y != 0))
	    		if (m_GameBoard.get(m_GameBoard.size()-1)[x+1][y-1] == opponent)
	    			m_GameBoard.get(m_GameBoard.size()-1)[x+1][y-1] = player;
    		// north-west
    		if ((x != 0) && (y != 0))
	    		if (m_GameBoard.get(m_GameBoard.size()-1)[x-1][y-1] == opponent)
	    			m_GameBoard.get(m_GameBoard.size()-1)[x-1][y-1] = player;
    		// east
    		if (x != 6)
	    		if (m_GameBoard.get(m_GameBoard.size()-1)[x+1][y] == opponent)
	    			m_GameBoard.get(m_GameBoard.size()-1)[x+1][y] = player;
    		// west
	    	if (x != 0)
	    		if (m_GameBoard.get(m_GameBoard.size()-1)[x-1][y] == opponent)
	    			m_GameBoard.get(m_GameBoard.size()-1)[x-1][y] = player;
		} // end MoveTile()
		
        /**
         *	Method to check if the current move is a spawning one or a jump
         *	returns true if spawn, false if jump
         */
		private boolean IsSpawningMove(int[][] moveInArray) {
			if ((moveInArray[0][0] == moveInArray[1][0]+1) || (moveInArray[0][0] == moveInArray[1][0]-1))
				return true;
			if ((moveInArray[0][1] == moveInArray[1][1]+1) || (moveInArray[0][1] == moveInArray[1][1]-1))
				return true;
			return false;
		} // end IsSpawningMove()
        
    } // end GameBoard
}

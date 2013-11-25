Done by	: U087139J
Email	: u087139j@nus.edu.sg

Notes
=====
This game AI of the Ataxx makes use of MiniMax with Alpha-Beta pruning algorithm.
It does a limited depth search of up to 4 ply, due to the fact that running 5 ply on sunfire takes mroe than 5 seconds for some moves.
The original evaluation function works on the basic idea of trying to have more tiles than the opponent.
The upgraded version checks for pitfalls of having empty holes in-between cluster of tiles to prevent a large lost of tile that might not be foreseen by a 4 ply search.
That is all.

Thank you for reading
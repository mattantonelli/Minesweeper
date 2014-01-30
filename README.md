Minesweeper
========
<i>By: Matt Antonelli</i>

This Java application is a recreation of the classic Minesweeper game.

The game features a 16 x 16 grid filled with 40 mines that the player must flag. The game ends when the player has flagged all 40 mines and explored all other "empty" cells. When an empty cell is exposed, it bears a number indicating the number of mines in its adjacent cells. Adjacent cells are those cells that are one cell away from the given cell horizontally, vertically, or diagonally. 

The player can conduct a "quick sweep" on a blank cell once the indicated number of flags have has been set in the adjacent cells. This quick sweep will automatically sweep all unflagged adjacent cells.

After the player has either won or lost, they can click the smiley face to restart the game. The player's progress is recorded by the number of mines that have been flagged and the amount of time they have spent on the current round. The number of mines will decrease even if a player places an incorrect flag, so they cannot rely on this as a method for discovering mines.

Instructions:
<ul><li>Left click: Sweep a cell</li>
<li>Double left click: Quick sweep</li>
<li>Middle click: Quick sweep</li>
<li>Right click: Flag cell</li></ul>

Sample runs:

![Sample Run 1](http://tunabytes.com/imgdump/unsolved.png)

![Sample Run 2](http://tunabytes.com/imgdump/solved.png)

![Sample Run 3](http://tunabytes.com/imgdump/gameover.png)
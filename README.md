# Game

This is an implementation of a Minesweeper game. It is meant to be run from an IDE like Eclipse, as the main
method does not work for command line/Unix environments. Upon running of the program, you will be asked how many rows and columns you would like to play with. Once the input is accepted, the game will begin and the board will be randomly populated with mines.

The Minesweeper class also has a constructor which can create a Minesweeper game using a file. If a game is initialized this way, then the file will be read line by line, and a game will automatically be made. It is important that the file adhere to a strict format, otherwise it will not be accepted. The format is the number of rows, followed by a space, followed by the number of columns; this comprises a line. Any following lines of text afterwards, will determine the location of a mine (in other words, this overrides the code to allow the user to select where to place mines, instead of having them populated randomly). An example is shown in sample.txt
  
It is important to note that the main method does not support the file implementation of the game, but only the user input implementation. If somebody would want to use the file implementation, the main method would need to be changed accordingly.

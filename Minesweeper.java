import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class represents a Minesweeper game.
 *
 * @author KIRILL KORYGIN 
 */
public class Minesweeper {

    private boolean[][] mines;// this is the array that holds mine locations
    private String[][] output;// this is the grid printed to screen
    private int roundsCompleted = 0;// keeps track of rounds completed
    private int numMines = 0, minesToCompare=0;// used to execute the winCondition() & calculate score
    String [] [] noFogArray;// used when nofog command called to print grid to screen
    Random random = new Random();
    Scanner keyboard = new Scanner(System.in);

    /**
     * Constructs an object instance of the {@link Minesweeper} class using the
     * information provided in <code>seedFile</code>. Documentation about the
     * format of seed files can be found in the project's <code>README.md</code>
     * file.
     *
     * @param seedFile
     *            the seed file used to construct the game
     * @see <a href=
     *      "https://github.com/mepcotterell-cs1302/cs1302-minesweeper-alpha/blob/master/README.md#seed-files">
     *      README.md#seed-files</a>
     */

    public Minesweeper(File seedFile) {
        try {
            System.out.println(" _"+ "/\\/\\"+ " (_)_ __   ___  _____      _____  ___ _ __   ___ _ __\n"
                    + " /    \\"+ "| | '_ \\"+ " / _ \\"+ "/ __\\"+ " \\ /"+ "\\ / / _ "+ "\\/ _ "
                    + "\\ '_ "+ "\\ / _ "+ "\\ '__|\n"+ "/ /"+ "\\/"+ "\\ "+ "\\ | | | |  __/"
                    + "\\__ \\\\ V  V /  __/  __/ |_) |  __/ |\n"+ "\\/    "+ "\\/_|_| |_|"+ "\\___||___/ "
                    + "\\_/"+ "\\_/ "+ "\\___|"+ "\\___| .__/ "+ "\\___|_|\n"
			       + "                                     ALPHA |_| EDITION");
            Scanner scanner = new Scanner(seedFile);
            String s = scanner.nextLine();
            Scanner lineReader = new Scanner(s);
            int rowsx=0,colsx=0;
            // reads in the # rows and cols
            if (lineReader.hasNextInt()){
                rowsx = lineReader.nextInt();
            }
            if (lineReader.hasNextInt()){
                colsx = lineReader.nextInt();
            } else {
                System.out.println("Cannot create game with "+ seedFile.getName()+", because it is not formatted correctly.");
                System.exit(0);
            }
            if (rowsx<1 || colsx<1 || rowsx>10 || colsx>10){
                System.out.println("ಠ_ಠ says, \"Cannot create a mine field with that many rows and/or columns!\"");
                System.exit(0);
            }
            // initializes the arrays
            mines = new boolean[rowsx][colsx];
            output = new String [rowsx][colsx];
            for (int i = 0; i < rowsx; i++) {
                for (int j = 0; j < colsx; j++) {
                    output[i][j] = " ";
                }
            }
            for (int i = 0; i < rowsx; i++) {
                for (int j = 0; j < colsx; j++) {
                    mines[i][j] = false;
                }
            }
            this.numMines=0;

            s = scanner.nextLine();
            lineReader = new Scanner(s);
            if (lineReader.hasNextInt()){
                this.numMines = lineReader.nextInt();
            } else {
                System.out.println("Cannot create game with "+ seedFile.getName()+", because it is not formatted correctly.");
                System.exit(0);
            }
            int rows=0,cols=0;
            // this loop goes through and sets the mines given by seedFile
            for (int i=0;i<this.numMines;i++){
                if (scanner.hasNextLine()){
                    s = scanner.nextLine();
                    lineReader = new Scanner(s);
                } else {
                    System.out.println("Cannot create game with "+ seedFile.getName()+", because it is not formatted correctly.");
                    System.exit(0);
                }

                if (lineReader.hasNextInt()){
                    rows = lineReader.nextInt();
                    if (rows<0 || rows>=output.length){
                        System.out.println("ಠ_ಠ says, \"Cannot create a mine field with that many rows and/or columns!\"");
                        System.exit(0);
                    }
                } else {
                    System.out.println("Cannot create game with "+ seedFile.getName()+", because it is not formatted correctly.");
                    System.exit(0);
                }
                if (lineReader.hasNextInt()){
                    cols = lineReader.nextInt();
                    if (cols<0 || cols>=output[0].length){
                        System.out.println("ಠ_ಠ says, \"Cannot create a mine field with that many rows and/or columns!\"");
                        System.exit(0);
                    }
                } else {
                    System.out.println("Cannot create game with "+ seedFile.getName()+", because it is not formatted correctly.");
                    System.exit(0);
                }
                if (rows<0 || cols<0 || rows>9 || cols>9){
                    System.out.println("ಠ_ಠ says, \"Cannot create a mine field with that many rows and/or columns!\"");
                    System.exit(0);
                }
                mines[rows][cols]=true;
            }

        } catch (FileNotFoundException e) {
            System.out.println(seedFile.getName()+" cannot be found.");
            System.exit(0);
        }


    }

    /**
     * Constructs an object instance of the {@link Minesweeper} class using the
     * <code>rows</code> and <code>cols</code> values as the game grid's number
     * of rows and columns respectively. Additionally, One tenth (rounded down)
     * of the squares in the grid will will be assigned mines, randomly.
     *
     * @param rows
     *            the number of rows in the game grid
     * @param cols
     *            the number of cols in the game grid
     */

    public Minesweeper(int rows, int cols) {
        if (rows>10 || rows <1 || cols>10 || cols<1){
            System.out.println("ಠ_ಠ says, \"Cannot create a mine field with that many rows and/or columns!\"");
            System.exit(0);// exit gracefully
        }
        System.out.println(" _"+ "/\\/\\"+ " (_)_ __   ___  _____      _____  ___ _ __   ___ _ __\n"
                + " /    \\"+ "| | '_ \\"+ " / _ \\"+ "/ __\\"+ " \\ /"+ "\\ / / _ "+ "\\/ _ "
                + "\\ '_ "+ "\\ / _ "+ "\\ '__|\n"+ "/ /"+ "\\/"+ "\\ "+ "\\ | | | |  __/"
                + "\\__ \\\\ V  V /  __/  __/ |_) |  __/ |\n"+ "\\/    "+ "\\/_|_| |_|"+ "\\___||___/ "
                + "\\_/"+ "\\_/ "+ "\\___|"+ "\\___| .__/ "+ "\\___|_|\n"
			   + "                                     ALPHA |_| EDITION");
        mines = new boolean[rows][cols];
        output = new String[rows][cols];
        //initialize the string array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                output[i][j] = " ";
            }
        }
        // initialize the boolean array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mines[i][j] = false;
            }
        }

        int z = 0, randomRow, randomCol;
        numMines = (int) Math.ceil(((double)rows*cols)/10);//this will round up
        while (z < numMines) { //sets mines randomly, and checks for repetitive positioning
            randomRow = random.nextInt(rows);
            randomCol = random.nextInt(cols);
            if (!mines[randomRow][randomCol]) {
                mines[randomRow][randomCol] = true;
                z++;
            }
        }

    }

    /**
     * Determine if a particular spot has a mine
     * @param i - row coordinate
     * @param j - col coordinate
     * @return boolean stating whether a mine is located in that position
     */
    public boolean hasMine(int i, int j) {
        if (mines[i][j] == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param i - coordinate of the row
     * @param j - coordinate of the col
     * @return - checks boundary requirements
     */
    public boolean checkStates(int i, int j) {
        boolean inBounds = true;
        if (isInBounds(i, j) == false) {
            System.out.println("These co-ordinates are out of bounds. Try again.");
            roundsCompleted++;
            inBounds = false;
            updateGame();
        }
        return inBounds;
    }

    /**
     * exits the game
     */
    public void quit() {
        System.out.println("\nლ(ಠ_ಠლ)\n"
                + "Y U NO PLAY MORE?\n"
			   + "Bye!");
        System.exit(0);
    }


    public void mark(int i, int j) {
        if (checkStates(i, j)) {
            roundsCompleted++;
            output[i][j] = "F";
            minesToCompare++;
            updateGame();

        }
    }

    /**
     * Allows user to guess a point on the grid
     * @param i - row coordinate
     * @param j - col coordinate
     */
    public void guess(int i, int j) {
        if (checkStates(i, j)) {
            roundsCompleted++;
            output[i][j] = "?";
            updateGame();
        }

    }

    /**
     * Returns the number of adjacent mines from a given location
     * @param row
     * @param col
     * @return
     */
    public int getNumAdjMines(int row, int col) {
        int count = 0;
        // check all 8 possible adjacent mines
        if (isInBounds(row-1, col-1) && mines[row-1][col-1]) {
            count++;
        }
        if (isInBounds(row-1, col) && mines[row-1][col]) {
            count++;
        }
        if (isInBounds(row-1, col+1) && mines[row-1][col+1]) {
            count++;
        }
        if (isInBounds(row, col-1) && mines[row][col-1]) {
            count++;
        }
        if (isInBounds(row, col+1) && mines[row][col+1]) {
            count++;
        }
        if (isInBounds(row+1, col-1) && mines[row+1][col-1]) {
            count++;
        }
        if (isInBounds(row+1, col) && mines[row+1][col]) {
            count++;
        }
        if (isInBounds(row+1, col+1) && mines[row+1][col+1]) {
            count++;
        }
        return count;
    }

    /**
     * Checks if a location is in bounds of the grid
     * @param row
     * @param col
     * @return
     */
    public boolean isInBounds(int row, int col) {
        if ((row >= mines.length || col >= mines[0].length) || (row < 0 || col < 0)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Prints the grid once user uses the nofog command
     */
    public void updateFog(){
        // for loop to print out the output array
        System.out.println("\nRounds Completed: " + roundsCompleted + "\n");
        int counter = 0;
        for (int i = 0; i < mines.length; i++) {
            System.out.println();
            System.out.print(counter + " ");
            counter++;
            for (int j = 0; j < mines[0].length; j++) {
                System.out.print(" |" + noFogArray[i][j] + "|");
            }
        }
        counter = 0;
        System.out.println();
        System.out.print("  ");

        for (int i = 0; i < mines[0].length; i++) {
            System.out.print("   " + counter + "  ");
            counter++;
        }
    }

    /**
     * Allows the user to activate the nofog command
     */
    public void nofog(){
        // this is the extra credit method!
        noFogArray = new String [output.length][output[0].length];
        for (int i=0;i<output.length;i++){
            for (int j=0;j<output[0].length;j++){
                noFogArray[i][j]= output[i][j];
                if (mines[i][j]==true){
                    noFogArray[i][j] = "<"+output[i][j]+">";
                } else {
                    noFogArray[i][j]=" "+ output[i][j]+" ";
                }
            }
        }
        updateFog();
    }


    public void reveal(int i, int j) {
        if (checkStates(i, j)) {
            if (mines[i][j] == true) { // if the user reveals a mine, the game ends
                System.out.println("Oh no... You revealed a mine!\n"
                        + " __ _  __ _ _ __ ___   ___    _____   _____ _ __\n"+ "/ _` |/ _` "
				   + "| '_ ` _ \\"+ " / _ \\  "+ "/ _ \\"+ " \\ / / _ "+ "\\ '__|\n"
                        + "| (_| | (_| | | | | | |  __/ | (_) \\ V /  __/ |\n"+ "\\__, "
				   + "|\\__,_|_| |_| |_|"+ "\\___|  "+ "\\___/ "+ "\\_/ "+ "\\___|_|\n"+ "|___/");
                System.exit(0);
            } else {
                roundsCompleted++;
                int x = getNumAdjMines(i, j);
                String z = Integer.toString(x);
                output[i][j] = z;
                updateGame();
            }
        }
    }

    /**
     * Allows user to ask for help from program
     */
    public void help() {
        // this command outputs every possible command besides nofog and increments roundsCompleted
        roundsCompleted++;
        System.out.println("Commands available...\n- Reveal: r/reveal row col\n"
			   + "- Mark: m/mark row col\n- Guess: g/guess row col\n" + "- Help: h/help\n- Quit: q/quit");
    }

    /**
     * Prints out the coordinate grid
     */
    public void updateGame() {
        // the for loop will print out the output array to screen
        System.out.println("\nRounds Completed: " + roundsCompleted + "\n");
        int counter = 0;
        for (int i = 0; i < mines.length; i++) {
            System.out.println();
            System.out.print(counter + " ");
            counter++;
            for (int j = 0; j < mines[0].length; j++) {
                System.out.print("| " + output[i][j] + " |");
            }
        }
        counter = 0;
        System.out.println();
        System.out.print("  ");

        for (int i = 0; i < mines[0].length; i++) {
            System.out.print("  " + counter + "  ");
            counter++;
        }
    }
    /**
     * This method will determine when the user wins the game
     * @return a boolean stating whether user has won
     */
    public boolean winCondition() {
        boolean endLoop=false;
        boolean win=true;
        if (minesToCompare!=numMines){ // this checks if all mines have been marked
            win=false;
        }
        for (int i=0;i<output.length;i++){
            if (endLoop==true){
                win=false;
                break;
            }
            for (int j=0;j<output[0].length;j++){ // this checks that all squares w/o mines have been revealed
                if (!mines[i][j] && (output[i][j].equalsIgnoreCase(" ") || output[i][j].equalsIgnoreCase("?") || output[i][j].equalsIgnoreCase("f") )){
                    endLoop=true;
                    win=false;
                    break;
                }
            }
        }
        return win;

    }

    /**
     * Starts the game and execute the game loop.
     */
    public void run() {
        // the final method. it is responsible for running the game until the user wins or loses
        updateGame();
        Scanner keyboard = new Scanner (System.in);

        while (true){
            if (winCondition()){
                System.out.println("\n\n░░░░░░░░░▄░░░░░░░░░░░░░░▄░░░░\tSo Doge\n"
                        + "░░░░░░░░▌▒█░░░░░░░░░░░▄▀▒▌░░░\n"
                        + "░░░░░░░░▌▒▒█░░░░░░░░▄▀▒▒▒▐░░░\tSuch Score\n"
                        + "░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐░░░\n"
                        + "░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐░░░\tMuch Minesweeping\n"
                        + "░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌░░░\n"
                        + "░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒▌░░\tWow\n"
                        + "░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐░░\n"
                        + "░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄▌░\n"
                        + "░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒▌░\n"
                        + "▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒▐░\n"
                        + "▐▒▒▐▀▐▀▒░▄▄▒▄▒▒▒▒▒▒░▒░▒░▒▒▒▒▌\n"
                        + "▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒▒▒░▒░▒░▒▒▐░\n"
                        + "░▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒░▒░▒░▒░▒▒▒▌░\n"
                        + "░▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▄▒▒▐░░\n"
                        + "░░▀▄▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▄▒▒▒▒▌░░\n"
                        + "░░░░▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀░░░\tCONGRATULATIONS!\n"
                        + "░░░░░░▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀░░░░░\tYOU HAVE WON\n"
				   + "░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▀▀░░░░░░░░\tSCORE: "+((mines.length*mines[0].length)-numMines-roundsCompleted));
                System.exit(0);
            }
            System.out.println("\n\nminesweeper-alpha$  ");
            keyboard = new Scanner (System.in);
            String input = keyboard.nextLine();

            if (input.length()==0){ // if the user enters a blank space, output error to screen
                System.out.println("ಠ_ಠ says, \"Command not recognized!\"");
                roundsCompleted++;
                updateGame();
                continue;
            }
            Scanner scanner = new Scanner (input);
            int row=0,col=0;
            int count=0;


            String command = scanner.next();

            // check which commands have been used
            if (command.equalsIgnoreCase("h") || command.equalsIgnoreCase("help")){
                help();
                updateGame();
                continue;
            } else if (command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit")){
                quit();
            } else if (command.equalsIgnoreCase("nofog")){
                roundsCompleted++;
                nofog();
                continue;
            }
            // check the format
            while (scanner.hasNextInt()){
                row = scanner.nextInt();
                count++;
                if (scanner.hasNextInt()){
                    col = scanner.nextInt();
                    count++;
                }

            }
            // if format is incorrect, print error message
            if (count!=2){
                System.out.println("ಠ_ಠ says, \"Command not recognized!\"");
                roundsCompleted++;
                updateGame();
                continue;
            }

            if (command.equalsIgnoreCase("m") || command.equalsIgnoreCase("mark")){
                mark(row,col);

            } else if (command.equalsIgnoreCase("r") || command.equalsIgnoreCase("reveal")){
                reveal(row,col);
            } else if (command.equalsIgnoreCase("g") || command.equalsIgnoreCase("guess")){
                guess(row,col);
            } else {
                System.out.println("ಠ_ಠ says, \"Command not recognized!\"");
                roundsCompleted++;
                updateGame();
            }

        }

    }

    public static void main(String[] args) {

        int rows = 0;
        int cols = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to MineSweeper! Are you ready to play?" +
			   " Enter 'y' or 'n'\n");
        String s = scanner.next().replaceAll(" ","");
        while (!s.equalsIgnoreCase("y") && !s.equalsIgnoreCase("n")) {
            System.out.println("Please enter a valid command. Enter 'y' or 'n'\n");
            s = scanner.nextLine().replaceAll(" ","");
        }
        if (s.equalsIgnoreCase("n")) {
            System.out.println("Goodbye!");
        } else {
            System.out.println("How many rows would you like to play with?\n");
            rows = scanner.nextInt();
            while (rows<1 || rows>10) {
                System.out.println("You can only play with 1 to 10 rows! Try again: ");
                rows = scanner.nextInt();
            }

            System.out.println("How many columns would you like to play with?\n");
            cols = scanner.nextInt();
            while (cols<1 || cols>10) {
                System.out.println("You can only play with 1 to 10 rows! Try again: ");
                cols = scanner.nextInt();
            }
        }
        Minesweeper game = new Minesweeper(rows,cols);
        game.run();

    } // main


}
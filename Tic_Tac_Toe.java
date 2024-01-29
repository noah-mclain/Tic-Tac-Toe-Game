import java.util.Scanner;
import java.util.Random;

public class Tic_Tac_Toe {
    public static void main(String[] args) {

        //for user input
        Scanner in = new Scanner(System.in);
        int move; //to hold the moce

        if(args.length == 0) {
            TicTacToeG play = new TicTacToeG();
            play.drawBoard(); //draws the board

            //loop until the game ends
            do {
                do { //prompts user for move until valid
                    System.out.print("Player ");

                    if(play.getPlayerTurn()) {
                        System.out.print("1, ");
                    }
                    else {
                        System.out.print("2, ");
                    }

                    System.out.print("Enter a move (1-9): ");
                    move = in.nextInt();
                    System.out.println();

                } while(!play.checkMove(move)); //checks move validity

                play.setMove(move); //records move
                play.drawBoard();//displays the game and positions
            } while(!play.finished() && !play.gameWon());
        
            play.decWinner();
        }

        else if(args.length != 2) { //improper usage
            System.out.println("Usage: Java TicTacToe [-c [1|2]]");
        }
        else if (args[0].equals("-c") && args[1].equals("1")) { //Computer goes first
            //start the game and display the board
            TicTacToeGCPU play = new TicTacToeGCPU(true);
            play.drawBoard();

            //loop until the game ends
            do {
                //computer moves
                play.CPUmove();

                play.drawBoard();

                //if the game hasn't ended
                if(!play.gameWon() && !play.finished()) {
                    //prompt the player for moves until valid

                    do {
                        System.out.print("Player ");

                        if(play.getPlayerTurn()) {
                            System.out.print("1, ");
                        }
                        else {
                            System.out.print("2, ");
                        }

                        System.out.print("Enter a move (1-9): ");
                        move = in.nextInt();
                        System.out.println();
                    } while(!play.checkMove(move));
                
                    play.setMove(move);
                    play.drawBoard();
                }
            } while(!play.finished() && !play.gameWon());

            if(play.getPlayerTurn()) {
                play.decWinner(false);
            }
            else {
                play.decWinner(true);
            }
            
        }
        else if(args[0].equals("-c") && args[1].equals("2")) { //computer goes second
            TicTacToeGCPU play = new TicTacToeGCPU(false);
            play.drawBoard();

            //loop until the game ends
            do {
                //prompt the user for input until valid
                do {
                    System.out.print("Player ");

                    if(play.getPlayerTurn()) {
                        System.out.print("1, ");
                    }
                    else {
                        System.out.print("2, ");
                    }

                    System.out.print("Enter a move (1-9): ");
                    move = in.nextInt();
                    System.out.println();
                } while(!play.checkMove(move));
            
                play.setMove(move);
                play.drawBoard();

                if(!play.gameWon() && !play.finished()) {
                    play.CPUmove();
                    play.drawBoard();
                }
            } while(!play.finished() && !play.gameWon());

            if(play.getPlayerTurn()) {
                play.decWinner(true); //computer wins
            }
            else {
                play.decWinner(false); //player wins
            }
        }
        else { //improper usage
            System.out.println("Usage: Java TicTacToe [-c [1|2]]");
        }
        in.close();
    }  
}

class TicTacToeG {
    protected char[][] game = new char[3][3];
    protected boolean p1Turn; //true is it's player 1's turn

    public TicTacToeG() {
        for(int i = 0; i < 3; i++) { //constructs the board and sets all values to ' '
            for(int j = 0; j < 3; j++) {
                game[i][j] = ' ';
            }
        }

        p1Turn = true; //lets player 1 start
    }
        
    public void drawBoard () {
        System.out.println("Board:              Positions:");

        int p = 1; //the position number

        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(" " + game[i][j] + " ");
                
                if(j < 2) {
                    System.out.print("|"); //prints the rows
                }
            }
            System.out.print("              ");

            for(int j = 0; j < 3; j++) {
                System.out.print(" " + p + " ");
                p++;

                if(j < 2) {
                    System.out.print("|"); //prints the position row
                }
            }
            System.out.println();

            if(i < 2) { //prints row seperators
                System.out.print("-----------");
                System.out.print("              ");
                System.out.println("-----------");
            }
        }
    }

    //checks the users input validity
    public boolean checkMove(int move) {

        String RED = "\u001B[31m";
        String RESET = "\u001B[31m";

        //checks if input is valid
        if(move < 1 || move > 9) {
            System.out.println(RED + "Error: Invalid input" + RESET);
            return false;
        }

        //checks if position is free
        if(!posEmpty(move)) {
            System.out.println(RED + "Error: Position is already filled" + RESET);
            return false;
        }

        //position is empty and valid
        return true;
    }

    //checks if the inout position is emtpty
    public boolean posEmpty(int move) {
        boolean empty = true;
        switch(move) {
            case 1:
                if(game[0][0] != ' '){
                    empty = false;
                }
                break;
            case 2:
                if(game[0][1] != ' ') {
                    empty = false;
                }
                break;
            case 3:
                if(game[0][2] != ' ') {
                    empty = false;
                }
                break;
            case 4:
                if(game[1][0] != ' ') {
                    empty = false;
                }
                break;
            case 5:
                if(game[1][1] != ' ') {
                    empty = false;
                }
                break;
            case 6:
                if(game[1][2] != ' ') {
                    empty = false;
                }
                break;
            case 7:
                if(game[2][0] != ' ') {
                    empty = false;
                }
                break;
            case 8:
                if(game[2][1] != ' ') {
                    empty = false;
                }
                break;
            case 9:
                if(game[2][2] != ' ') {
                    empty = false;
                }
                break;
        }
        return empty;
    }

    //placing the moves onto the board
    public void setMove(int move) {
        char m;

        if(p1Turn) {
            m = 'x';
        }
        else {
            m = 'o';
        }

        switch(move) {
            case 1:
                game[0][0] = m;
                break;
        case 2:
                game[0][1] = m;
                break;
            case 3:
                game[0][2] = m;
                break;
            case 4:
                game[1][0] = m;
                break;
            case 5:
                game[1][1] = m;
                break;
            case 6:
                game[1][2] = m;
                break;
            case 7:
                game[2][0] = m;
                break;
            case 8:
                game[2][1] = m;
                break;
            case 9:
                game[2][2] = m;
                break; 
        }

        //changes the users turn from true to false or vice versa
        p1Turn ^= true;

    }

    //check if the game is complete
    public boolean finished(){
    
    // loop through all game positions
    for(int i = 0; i < 3; i++)
    {
        for(int j = 0; j < 3; j++)
        {
            // empty position
            if(game[i][j] == ' ')
                return false;
        }
    }

    return true;
	}
    
    public boolean getPlayerTurn() {
        return p1Turn;
    }

    public boolean gameWon() {
        //checking player one
        char m = 'x';

        //2- once for each player
        for(int i = 0; i < 2; i++) {
            //going through the center
            if(game[1][1] == m) {
                if(game[0][1] == m && game[2][1] == m) {
                    return true;
                }
                if(game[0][2] == m && game[2][0] == m) {
                    return true;
                }
                if(game[1][2] == m && game[1][0] == m) {
                    return true;
                }
                if(game[2][2] == m && game[0][0] == m) {
                    return true;
                }
            }

            //going through the top left corner
            if(game[0][0] == m) {
                if(game[0][1] == m && game[0][2] == m) {
                    return true;
                }
                if(game[1][0] == m && game[2][0] == m) {
                    return true;
                }
            }

            //going through the bottom right corner
            if(game[2][2] == m) {
                if(game[0][2] == m && game[1][2] == m) {
                    return true;
                }
                if(game[2][0] == m && game[2][1] == 0) {
                    return true;
                }
            }
            m = 'o'; //switch to player 2
        }
        //no winner found
        return false;
    }

    public void decWinner() {
        //checks if the game was won
        if(gameWon()) {
            System.out.print("Player ");

            //checks if it was player 1 or 2
            if(!p1Turn) {
                System.out.print("1 ");
            }
            else {
                System.out.print("2 ");
            }

            System.out.println("won the game");
        }
        else {
            System.out.println("The game has ended in a draw");
        }
    }
}

class TicTacToeGCPU extends TicTacToeG {
    //holds all possible moves for a turn
    boolean[] moves = new boolean[9];

    //holds computer move type 'x' or 'o'
    char mType;
    //oppsing type
    char oType;

    //implements the parent class costructor
    //has the computer go first
    public TicTacToeGCPU(boolean CPUFirst) {
        super(); //calls the parent constructor

        if(CPUFirst) {
            mType = 'x';
            oType = 'o';
        }
        else {
            mType = 'o';
            oType = 'x';            
        }
    }

    //determining and setting the computers moves
    public void CPUmove() {

        //checks if a winning move is available for computer
        winningMove(mType);
        //placing the move
        for(int i = 0; i < 9; i++) {
            if(moves[i] == true) {
                setMove(i + 1);
                displayCPUmove(i + 1);
                resetMoves();
                return;
            }
        }

        //checks if opponent has a winning move
        winningMove(oType);
        //blocking the opponents winning move
        for(int i = 0; i < 9; i++) {
            if(moves[i] == true) {
                setMove(i + 1);
                displayCPUmove(i + 1);
                resetMoves();
                return;
            }
        }

        //placing move to center position
        if(game[1][1] == ' ') {
            setMove(5);
            displayCPUmove(5);
            return;
        }

        //choose randomly if none of the above
        boolean found = false; //usable position
        Random rand = new Random(); 
        int p; //random position

        do {
            //random number between 1-9
            p = rand.nextInt(9);

            if(posEmpty(p)) {
                setMove(p);
                displayCPUmove(p);
                return;
            }
        }while(!found);
    }

    public void displayCPUmove(int dM) {
        System.out.println("Computer plays " + dM);
        System.out.println();
    }

    //check for any winning moves
    public void winningMove(char mT) {
        //check all winning combinations
        //going through the center
        if(game[1][1] == mT) {
            if(game[0][1] == mT && game[2][1] == ' ') {
                moves[7] = true; //8 is the winning move
            }
            if(game[2][1] == mT && game[0][1] == ' ') {
                moves[1] = true; //move 2
            }
            if(game[0][2] == mT && game[1][2] == ' ') {
                moves[6] = true; //move 7
            }
            if(game[2][0] == mT && game[0][2] == ' ') {
                moves[2] = true; //move 3
            }
            if(game[1][2] == mT && game[1][0] == ' ') {
                moves[3] = true; //move 4
            }
            if(game[1][0] == mT && game[1][2] == ' ') {
                moves[5] = true; //move 6
            }
            if(game[2][2] == mT && game[0][0] == ' ') {
                moves[0] = true; //move 1
            }
            if(game[0][0] == mT && game[2][2] == ' ') {
                moves[8] = true; //move 9
            }
        }

        //going through the top left corner
        if(game[0][0] == mT) {
            if(game[0][1] == mT && game[0][2] == ' ') {
                moves[2] = true;
            }
            if(game[0][2] == mT && game[0][1] == ' ') {
                moves[1] = true;
            }  
            if(game[1][0] == mT && game[2][0] == ' ') {
                moves[6] = true;
            }
            if(game[2][0] == mT && game[1][0] == ' ') {
                moves[3] = true;
            }        
        }

        //going through the bottom left corner
        if(game[2][0] == mT) {
            if(game[1][0] == mT && game[0][0] == ' ') {
                moves[0] = true;
            }
            if(game[0][0] == mT && game[1][0] == ' ') {
                moves[3] = true;
            }
            if(game[2][1] == mT && game[2][2] == ' ') {
                moves[8] = true;
            }
            if(game[2][2] == mT && game[2][1] == ' ') {
                moves[7] = true;
            }
        }

        //going through the bottom right corner
        if(game[2][2] == mT) {
            if(game[1][2] == mT && game[0][2] == ' ') {
                moves[2] = true;
            }
            if(game[0][2] == mT && game[1][2] == ' ') {
                moves[5] = true;
            }
            if(game[2][1] == mT && game[2][0] == ' ') {
                moves[6] = true;
            }
            if(game[2][0] == mT && game[2][1] == ' ') {
                moves[7] = true;
            }
        }

        //going through the top right corner
        if(game[0][2] == mT) {
            if(game[0][0] == mT && game[0][1] == ' ') {
                moves[1] = true;
            }
            if(game[0][1] == mT && game[0][0] == ' ') {
                moves[0] = true;
            }
            if(game[1][2] == mT && game[2][2] == ' ') {
                moves[8] = true;
            }
            if(game[2][2] == mT && game[1][2] == ' ') {
                moves[5] = true;
            }
        }
    }

    //resetting moves array to 'null' values
    public void resetMoves() {
        for(int i = 0; i < 9; i++) {
            moves[i] = false;
        }
    }

    public void decWinner(boolean w) {
        //check is there is a winner or if the game is tied
        if(gameWon()) {
            //check if player 1 or 2
            if(w) {
                System.out.print("You lose. The Computer has ");
            }
            else {
                System.out.print("Congratulations. You have ");
            }
            System.out.println("won the game");
        }
        else {
            System.out.println("The game has ended in a draw");
        }
    }
}
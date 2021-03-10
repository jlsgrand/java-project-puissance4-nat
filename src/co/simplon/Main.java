package co.simplon;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Représenter notre plateau de jeu
        String[][] board = new String[6][7];

        // Récupérer le nom des joueurs
        Scanner scanner = new Scanner(System.in);

        System.out.println("Comment s'appelle le joueur 1 ?");
        String player1 = scanner.nextLine();

        System.out.println("Comment s'appelle le joueur 2 ?");
        String player2 = scanner.nextLine();

        boolean win = false;
        String currentPlayer = player2;
        while (!win) {
            int linePosition = -1;
            int chosenColumn = -1;
            // Ma boucle pour rejouer
            do {
                // Récupération de colonne
                System.out.println("Dans quelle colonne veux-tu déposer ton pion ?");
                chosenColumn = scanner.nextInt();
                scanner.nextLine();

                linePosition = play(scanner, board, currentPlayer, chosenColumn);
            } while (linePosition < 0);


            if (win(countConsecutiveMoveHorizontal(currentPlayer, board, linePosition, chosenColumn), 4)
                    || win(countConsecutiveMoveVertical(currentPlayer, board, linePosition, chosenColumn), 4)
                    || win(countConsecutiveMoveNEtoSW(currentPlayer, board, linePosition, chosenColumn), 4)
                    || win(countConsecutiveMoveNWtoSE(currentPlayer, board, linePosition, chosenColumn), 4)) {
                win = true;
            }

            // Mon changement de joueur si on est arrivé à jouer
            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }

            // Une fois qu'un joueur a joué, on affiche le tableau
            printBoard(board);

        }
    }

    private static boolean win(int consecutiveCount, int victoryCriteria) {
        return consecutiveCount >= victoryCriteria;
    }

    private static int countConsecutiveMoveNWtoSE(String player, String[][] board, int linePosition, int columnPosition) {
        int consecutiveCount = 1;
        int linePointer = linePosition;
        int columnPointer = columnPosition;
        boolean consecutivePlayer = true;

        while (linePointer < board.length - 1 && columnPointer < board[linePosition].length - 1 && consecutivePlayer) {
            linePointer++;
            columnPointer++;
            if (board[linePointer][columnPointer] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        linePointer = linePosition;
        columnPointer = columnPosition;
        consecutivePlayer = true;

        while (linePointer > 0 && columnPointer > 0 && consecutivePlayer) {
            linePointer--;
            columnPointer--;
            if (board[linePointer][columnPointer] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        return consecutiveCount;
    }

    private static int countConsecutiveMoveNEtoSW(String player, String[][] board, int linePosition, int columnPosition) {
        int consecutiveCount = 1;
        int linePointer = linePosition;
        int columnPointer = columnPosition;
        boolean consecutivePlayer = true;

        while (linePointer > 0 && columnPointer < board[linePosition].length - 1 && consecutivePlayer) {
            linePointer--;
            columnPointer++;
            if (board[linePointer][columnPointer] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        linePointer = linePosition;
        columnPointer = columnPosition;
        consecutivePlayer = true;

        while (linePointer < board.length - 1 && columnPointer > 0 && consecutivePlayer) {
            linePointer++;
            columnPointer--;
            if (board[linePointer][columnPointer] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        return consecutiveCount;
    }

    private static int countConsecutiveMoveVertical(String player, String[][] board, int linePosition, int columnPosition) {
        int consecutiveCount = 1;
        int linePointer = linePosition;
        boolean consecutivePlayer = true;

        // Je prends en compte les jetons vers le haut
        while (linePointer > 0 && consecutivePlayer) {
            linePointer--;
            if (board[linePointer][columnPosition] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        linePointer = linePosition;
        consecutivePlayer = true;

        while (linePointer < board.length - 1 && consecutivePlayer) {
            linePointer++;
            if (board[linePointer][columnPosition] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        return consecutiveCount;
    }

    private static int countConsecutiveMoveHorizontal(String player, String[][] board, int linePosition, int columnPosition) {
        int consecutiveCount = 1;
        int columnPointer = columnPosition;
        boolean consecutivePlayer = true;

        // Je compte combien de jetons consécutifs j'ai à droite
        while (columnPointer < board[linePosition].length - 1 && consecutivePlayer) {
            columnPointer++;
            if (board[linePosition][columnPointer] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        // Je me replace sur la colonne du pion joué
        columnPointer = columnPosition;
        consecutivePlayer = true;

        // Je compte combien de jetons consécutifs j'ai à gauche
        while (columnPointer > 0 && consecutivePlayer) {
            columnPointer--;
            if (board[linePosition][columnPointer] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        return consecutiveCount;
    }

    private static int play(Scanner scanner, String[][] board, String player, int chosenColumn) {
        // boolean spaceAvailable = false;
        int linePosition = -1;

        // Est-ce que mon numéro de colonne existe dans le tableau ?
        if (chosenColumn > board[0].length - 1 || chosenColumn < 0) {
            // Je suis en dehors du tableau donc je dois rejouer ...
            System.out.println("Tu t'es trompé, t'as joué à côté !");
        } else {
            // Si j'ai choisi une colonne valide, je dois vérifier s'il y a toujours de la place dedans

            for (int lineCounter = board.length - 1; lineCounter >= 0 && linePosition < 0; lineCounter--) {
                if (board[lineCounter][chosenColumn] == null) {
                    board[lineCounter][chosenColumn] = player;
                    linePosition = lineCounter;
                }
            }

            if (linePosition < 0) {
                System.out.println("Tu t'es trompé, t'as choisi un colonne pleine !");
            }
        }

        return linePosition;
    }

    private static void printBoard(String[][] board) {
        // Je me balade sur les lignes de mon plateau de jeu
        for (int lineCounter = 0; lineCounter < board.length; lineCounter++) {
            // Pour chaque ligne je me balade sur les colonnes
            for (int columnCounter = 0; columnCounter < board[lineCounter].length; columnCounter++) {
                // Pour chaque case de mon tableau, si j'ai null à l'intérieur alors j'affiche une chaine vide
                // Sinon, j'affiche le contenu de la case.
                String box = "";
                if (board[lineCounter][columnCounter] != null) {
                    box = board[lineCounter][columnCounter];
                }
                //String box = (board[lineCounter][columnCounter] != null) ? board[lineCounter][columnCounter] : "";
                System.out.printf("[%6s]", box);
            }
            // J'affiche un retour à la ligne une fois que j'ai affiché toutes mes cases de la ligne
            System.out.println();
        }
    }
}

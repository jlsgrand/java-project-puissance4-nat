package co.simplon;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Représenter notre plateau de jeu
        String[][] board = new String[6][7];

        // Récupérer le nom des joueurs
        Scanner scanner = new Scanner(System.in);

        // Demander le nom des joueurs
        System.out.println("Comment s'appelle le joueur 1 ?");
        String player1 = scanner.nextLine();

        System.out.println("Comment s'appelle le joueur 2 ?");
        String player2 = scanner.nextLine();

        // Au départ personne n'a gagné
        boolean win = false;

        // On décide arbitrairement que c'est le joueur 2 qui commence
        String currentPlayer = player2;

        // Tant que personne n'a gagné, on boucle
        while (!win) {
            int linePosition = -1;
            int chosenColumn = -1;
            // Tant que l'utilisateur ne donne pas une colonne valide, on lui redemande de jouer
            do {
                // Récupération de colonne
                System.out.println("Dans quelle colonne veux-tu déposer ton pion ?");
                chosenColumn = scanner.nextInt();
                scanner.nextLine();

                // On appelle la fonction play pour jouer (si elle nous renvoie un chiffre positif, alors c'est ok, sinon on doit rejouer)
                linePosition = play(board, currentPlayer, chosenColumn);
            } while (linePosition < 0);


            // Le joueur courant gagne s'il aligne 4 pions horizontalement, verticalement ou en diagonale
            if (win(countConsecutiveMoveHorizontal(currentPlayer, board, linePosition, chosenColumn), 4)
                    || win(countConsecutiveMoveVertical(currentPlayer, board, linePosition, chosenColumn), 4)
                    || win(countConsecutiveMoveNEtoSW(currentPlayer, board, linePosition, chosenColumn), 4)
                    || win(countConsecutiveMoveNWtoSE(currentPlayer, board, linePosition, chosenColumn), 4)) {
                win = true;
            }

            // On change de joueur si on est arrivé à jouer
            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }

            // Enfin, on affiche le tableau
            printBoard(board);

        }
    }

    private static boolean win(int consecutiveCount, int victoryCriteria) {
        // Je gagne si le consecutive count est supérieur ou égal au critère de victoire
        return consecutiveCount >= victoryCriteria;
    }

    private static int countConsecutiveMoveNWtoSE(String player, String[][] board, int linePosition, int columnPosition) {
        int consecutiveCount = 1;
        int linePointer = linePosition;
        int columnPointer = columnPosition;
        boolean consecutivePlayer = true;

        // Je pars de la position jouée et je descend vers la droite
        while (linePointer < board.length - 1 && columnPointer < board[linePosition].length - 1 && consecutivePlayer) {
            linePointer++;
            columnPointer++;
            if (board[linePointer][columnPointer] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        // Je réinitialise ma position
        linePointer = linePosition;
        columnPointer = columnPosition;
        consecutivePlayer = true;

        // Je pars de la position jouée et je remonte vers la gauche
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

        // Je pars de la position jouée et je remonte vers la droite
        while (linePointer > 0 && columnPointer < board[linePosition].length - 1 && consecutivePlayer) {
            linePointer--;
            columnPointer++;
            if (board[linePointer][columnPointer] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        // Je réinitialise ma position
        linePointer = linePosition;
        columnPointer = columnPosition;
        consecutivePlayer = true;

        // Je pars de la position jouée et je descend vers la gauche
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

        // Je pars de la position jouée et je remonte à la verticale
        while (linePointer > 0 && consecutivePlayer) {
            linePointer--;
            if (board[linePointer][columnPosition] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        // Je réinitialise ma position
        linePointer = linePosition;
        consecutivePlayer = true;

        // Je pars de la position jouée et je descends à la verticale
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

        // Je pars de la position jouée et je vais à droite
        while (columnPointer < board[linePosition].length - 1 && consecutivePlayer) {
            columnPointer++;
            if (board[linePosition][columnPointer] == player) {
                consecutiveCount++;
            } else {
                consecutivePlayer = false;
            }
        }

        // Je réinitialise ma position
        columnPointer = columnPosition;
        consecutivePlayer = true;

        // Je pars de la position jouée et je vais à gauche
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

    private static int play(String[][] board, String player, int chosenColumn) {
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

                System.out.printf("[%6s]", box);
            }
            // J'affiche un retour à la ligne une fois que j'ai affiché toutes mes cases de la ligne
            System.out.println();
        }
    }
}

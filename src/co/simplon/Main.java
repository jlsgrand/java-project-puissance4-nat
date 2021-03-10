package co.simplon;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Représenter notre plateau de jeu
        String[][] board = new String[6][7];

        // Afficher notre plateau de jeu
        printBoard(board);

        // Récupérer le nom des joueurs
        Scanner scanner = new Scanner(System.in);

        System.out.println("Comment s'appelle le joueur 1 ?");
        String player1 = scanner.nextLine();

        System.out.println("Comment s'appelle le joueur 2 ?");
        String player2 = scanner.nextLine();

        boolean win = false;
        String currentPlayer = player2;
        while (!win) {
            boolean validPlay = false;
            do {
                validPlay = play(scanner, board, currentPlayer);
            } while (!validPlay);

            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }
            printBoard(board);

            // win =
        }
    }
//
//    private static boolean winningMove(String player, String[][] board) {
//
//    }

    private static boolean play(Scanner scanner, String[][] board, String player) {
        boolean spaceAvailable = false;
        // Récupération de colonne
        System.out.println("Dans quelle colonne veux-tu déposer ton pion ?");
        int chosenColumn = scanner.nextInt();
        scanner.nextLine();

        // Est-ce que mon numéro de colonne existe dans le tableau ?
        if (chosenColumn > board[0].length - 1 || chosenColumn < 0) {
            // Je suis en dehors du tableau donc je dois rejouer ...
            System.out.println("Tu t'es trompé, t'as joué à côté !");
        } else {
            // Si j'ai choisi une colonne valide, je dois vérifier s'il y a toujours de la place dedans

            for (int lineCounter = board.length - 1; lineCounter >= 0 && !spaceAvailable; lineCounter--) {
                if (board[lineCounter][chosenColumn] == null) {
                    board[lineCounter][chosenColumn] = player;
                    spaceAvailable = true;
                }
            }

            if (!spaceAvailable) {
                System.out.println("Tu t'es trompé, t'as choisi un colonne pleine !");
            }
        }

        return spaceAvailable;
        // Si le numéro de colonne est valide est-ce que la colonne a encore de la place ?
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

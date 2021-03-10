package co.simplon;

import java.util.Scanner;

public class RefactoredMain {

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


            if (
                    win(countConsecutiveMove(currentPlayer, board, linePosition, chosenColumn, "South")
                            + countConsecutiveMove(currentPlayer, board, linePosition, chosenColumn, "North") - 1, 4)
                            ||
                            win(countConsecutiveMove(currentPlayer, board, linePosition, chosenColumn, "East")
                                    + countConsecutiveMove(currentPlayer, board, linePosition, chosenColumn, "West") - 1, 4)
                            ||
                            win(countConsecutiveMove(currentPlayer, board, linePosition, chosenColumn, "SE")
                                    + countConsecutiveMove(currentPlayer, board, linePosition, chosenColumn, "NW") - 1, 4)
                            ||
                            win(countConsecutiveMove(currentPlayer, board, linePosition, chosenColumn, "SW")
                                    + countConsecutiveMove(currentPlayer, board, linePosition, chosenColumn, "NE") - 1, 4)
            ) {
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

    private static int countConsecutiveMove(String player, String[][] board, int linePosition, int columnPosition, String direction) {
        int lineStopCounter = 1;
        int lineIncrementer = 0;
        int columnStopCounter = 1;
        int columnIncrementer = 0;

        boolean consecutivePlayer = true;
        int consecutiveCount = 1;

        int linePointer = linePosition;
        int columnPointer = columnPosition;
        switch (direction) {
            case "North":
                // Pas besoin de condition d'arrêt sur la colonne => -1
                columnStopCounter = -1;

                // Condition d'arret de ma ligne et incrément
                lineStopCounter = 0;
                lineIncrementer = -1;
                break;
            case "South":
                // Pas besoin de condition d'arrêt sur la colonne => -1
                columnStopCounter = -1;

                // Condition d'arret de ma ligne et incrément
                lineStopCounter = board.length - 1;
                lineIncrementer = 1;
                break;
            case "East":
                // Pas besoin de condition d'arrêt sur la ligne => -1
                lineStopCounter = -1;

                // Condition d'arret de ma colonne et incrément
                columnStopCounter = board[0].length - 1;
                columnIncrementer = 1;
                break;
            case "West":
                // Pas besoin de condition d'arrêt sur la ligne => -1
                lineStopCounter = -1;

                // Condition d'arret de ma colonne et incrément
                columnStopCounter = 0;
                columnIncrementer = -1;
                break;
            case "NE":
                // Condition d'arret de ma ligne et incrément
                lineStopCounter = 0;
                lineIncrementer = -1;
                // Condition d'arret de ma colonne et incrément
                columnStopCounter = board[0].length - 1;
                columnIncrementer = 1;
                break;
            case "SE":
                // Condition d'arret de ma ligne et incrément
                lineStopCounter = board.length - 1;
                lineIncrementer = 1;
                // Condition d'arret de ma colonne et incrément
                columnStopCounter = board[0].length - 1;
                columnIncrementer = 1;
                break;
            case "NW":
                // Condition d'arret de ma ligne et incrément
                lineStopCounter = 0;
                lineIncrementer = -1;
                // Condition d'arret de ma colonne et incrément
                columnStopCounter = 0;
                columnIncrementer = -1;
                break;
            case "SW":
                // Condition d'arret de ma ligne et incrément
                lineStopCounter = board.length - 1;
                lineIncrementer = 1;
                // Condition d'arret de ma colonne et incrément
                columnStopCounter = 0;
                columnIncrementer = -1;
                break;
        }

        while (linePointer != lineStopCounter && columnPointer != columnStopCounter && consecutivePlayer) {
            linePointer = linePointer + lineIncrementer;
            columnPointer = columnPointer + columnIncrementer;

            if (board[linePointer][columnPointer] == player) {
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



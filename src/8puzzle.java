import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;

class PuzzleNode {
    char[][] board;
    List<char[][]> moves;
    int depth;

    public PuzzleNode(char[][] board, List<char[][]> moves, int depth) {
        this.board = board;
        this.moves = moves;
        this.depth = depth;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PuzzleNode that = (PuzzleNode) obj;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}

public class homework1 {

    static int enqueueCount = 0; //Add enqueueCount as a static variable

    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int[] getBlankPosition(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '*') {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private static boolean isValidMove(int i, int j) {
        return 0 <= i && i < 3 && 0 <= j && j < 3;
    }

    private static char[][] applyMove(char[][] board, int[] move) {
        int[] blankPosition = getBlankPosition(board);
        int i = blankPosition[0];
        int j = blankPosition[1];
        int newI = i + move[0];
        int newJ = j + move[1];

        if (isValidMove(newI, newJ)) {
            char[][] newBoard = new char[3][3];
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    newBoard[x][y] = board[x][y];
                }
            }
            newBoard[i][j] = newBoard[newI][newJ];
            newBoard[newI][newJ] = '*';
            return newBoard;
        } else {
            return null;
        }
    }

    private static List<int[]> getPossibleMoves() {
        return Arrays.asList(new int[]{-1, 0}, new int[]{1, 0}, new int[]{0, -1}, new int[]{0, 1});
    }

    private static List<char[][]> depthFirstSearch(char[][] initialBoard, int depthLimit) {
        Stack<PuzzleNode> stack = new Stack<>();
        Set<PuzzleNode> visited = new HashSet<>();

        stack.push(new PuzzleNode(initialBoard, new ArrayList<>(), 0));

        while (!stack.isEmpty()) {
            PuzzleNode currentNode = stack.pop();

            if (Arrays.deepEquals(currentNode.board, goalState)) {
                return currentNode.moves;
            }

            if (currentNode.depth < depthLimit) {
                for (int[] move : getPossibleMoves()) {
                    char[][] newBoard = applyMove(currentNode.board, move);

                    if (newBoard != null) {
                        List<char[][]> newMoves = new ArrayList<>(currentNode.moves);
                        newMoves.add(newBoard);
                        PuzzleNode newNode = new PuzzleNode(newBoard, newMoves, currentNode.depth + 1);

                        if (!visited.contains(newNode)) {
                            visited.add(newNode);
                            stack.push(newNode);
                            enqueueCount++; //Increment the counter when a state is enqueued
                        }
                    }
                }
            }
        }

        return null;
    }

    private static List<char[][]> iterativeDeepeningSearch(char[][] initialBoard) {

        for (int depthLimit = 0; depthLimit < 10; depthLimit++) {
            List<char[][]> result = depthFirstSearch(initialBoard, depthLimit);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static int manhattanDistance(char[][] board) {
        int distance = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char value = board[i][j];
                if (value != '*') {
                    int targetX = (value - '1') / 3;
                    int targetY = (value - '1') % 3;
                    distance += Math.abs(i - targetX) + Math.abs(j - targetY);
                }
            }
        }
        return distance;
    }

    private static int misplacedTiles(char[][] board) {
        int misplaced = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != goalState[i][j]) {
                    misplaced++;
                }
            }
        }
        return misplaced;
    }

    private static List<char[][]> aStarSearch(char[][] initialBoard, Function<char[][], Integer> heuristicFunction) {
        PriorityQueue<PuzzleNode> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.depth + heuristicFunction.apply(node.board)));
        Set<PuzzleNode> visited = new HashSet<>();


        queue.add(new PuzzleNode(initialBoard, new ArrayList<>(), 0));

        while (!queue.isEmpty()) {
            PuzzleNode currentNode = queue.poll();

            if (Arrays.deepEquals(currentNode.board, goalState)) {
                return currentNode.moves;
            }

            for (int[] move : getPossibleMoves()) {
                char[][] newBoard = applyMove(currentNode.board, move);

                if (newBoard != null) {
                    PuzzleNode newNode = new PuzzleNode(newBoard, new ArrayList<>(currentNode.moves), currentNode.depth + 1);

                    if (!visited.contains(newNode)) {
                        visited.add(newNode);
                        newNode.moves.add(newBoard);
                        queue.add(newNode);
                        enqueueCount++;
                    }
                }
            }
        }

        return null;
    }

    private static List<char[][]> aStar1(char[][] initialBoard) {
        return aStarSearch(initialBoard, homework1::manhattanDistance);
    }

    private static List<char[][]> aStar2(char[][] initialBoard) {
        return aStarSearch(initialBoard, homework1::misplacedTiles);
    }


    private static char [][] readInitialBoard(String filename) {
        char[][] Board = new char[3][3];
        try {
            Scanner scanner = new Scanner(new File(filename));
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (scanner.hasNext()) {
                        String next = scanner.next();
                        Board[i][j] = next.charAt(0);
                    }
                }
            }
            scanner.close();
            return Board;// file is read, return the initial board and put that into the algorithm to run
        } catch (FileNotFoundException e) {
            System.out.println("Error reading the input file: " +e.getMessage());// Error reading the file
            System.exit(1);
            return null;
        }
    }






    final private static char[][] goalState = {
            {'7', '8', '1'},
            {'6', '*', '2'},
            {'5', '4', '3'}
    };

    public static void main(String[] args) {
        String algorithm_name = args [0];
        char [][] initialBoard = readInitialBoard(args[1]);
        System.out.println("Initial board:");
        printBoard(initialBoard);



        switch (algorithm_name) {
            case "dfs":
            List<char[][]> dfsMoves = depthFirstSearch(initialBoard, 10);

            if (dfsMoves != null) {
                System.out.println("Depth-First Search Results:");
                for (char[][] move : dfsMoves) {
                    printBoard(move);
                }
                System.out.println("Number of moves (DFS) = " + dfsMoves.size());
                System.out.println("Number of state enqueued (DFS)= " + enqueueCount);
                enqueueCount = 0;
            } else {
                System.out.println("Depth-First Search: Goal state not reached within depth limit.");
            }
            break;
            case "ids":
            List<char[][]> idsMoves = iterativeDeepeningSearch(initialBoard);

            if (idsMoves != null) {
                System.out.println("\nIterative Deepening Search Results:");
                for (char[][] move : idsMoves) {
                    printBoard(move);
                }
                System.out.println("Number of moves (IDS) = " + idsMoves.size());
                System.out.println("Number of state enqueued (IDS)= " + enqueueCount);
                enqueueCount = 0;
            } else {
                System.out.println("Iterative Deepening Search: Goal state not reached within depth limit.");
            }
            break;
            case"astar1":
            List<char[][]> aStar1Moves = aStar1(initialBoard);
            if (aStar1Moves != null) {
                System.out.println("\nA* Search with Manhattan Distance Results:");
                for (char[][] move : aStar1Moves) {
                    printBoard(move);
                }
                System.out.println("Number of moves (A* search with Manhattan Distance) = " + aStar1Moves.size());
                System.out.println("Number of state enqueued (A* search with Manhattan Distance)= " + enqueueCount);
                enqueueCount = 0;
            } else {
                System.out.println("A* search with Manhattan Distance: Goal state not reached within depth limit.");
            }
            break;

            case "astar2":
            List<char[][]> aStar2Moves = aStar2(initialBoard);
            if (aStar2Moves != null) {
                System.out.println("\nA* Search with Misplaced Tiles Results:");
                for (char[][] move : aStar2Moves) {
                    printBoard(move);
                }
                System.out.println("Number of moves (A* search with Misplaced Tiles) = " + aStar2Moves.size());
                System.out.println("Number of state enqueued (A* search with Misplaced Tiles)= " + enqueueCount);
                enqueueCount = 0;
            } else {
                System.out.println("A* search with Misplaced Tiles: Goal state not reached within depth limit.");
            }
            break;
        }
    }
}

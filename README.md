---

# 8-Puzzle Solver

## Description
The 8-Puzzle Solver is a Java-based project that implements various search algorithms to solve the 8-puzzle problem. The algorithms include Depth-first Search (DFS), Iterative Deepening Search (IDS), and A* Search with two heuristics: Manhattan Distance and Misplaced Tiles. The project allows users to input the initial state of the puzzle and choose the desired algorithm to find the solution efficiently.

## Features
- **Depth-first Search (DFS)**
- **Iterative Deepening Search (IDS)**
- **A* Search with Manhattan Distance Heuristic**
- **A* Search with Misplaced Tiles Heuristic**

## Technologies
- **Java**

## Skills Demonstrated
- Search Algorithms
- Heuristic Evaluation
- Problem Solving
- Software Design

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- An IDE or text editor (e.g., IntelliJ IDEA, Eclipse, VS Code)

### Installation
1. Download the code file `8puzzle.java` to your computer.
2. Locate the downloaded file in your File Explorer (usually in the Downloads folder).
3. Open the terminal and navigate to the directory containing the `8puzzle.java` file using the `cd` command.

### Running the Program
1. Compile the Java code:
   ```bash
   javac 8puzzle.java
   ```
2. Run the application with the desired algorithm and input file path:
   ```bash
   java 8puzzle <algorithm_name> <input_file_path>
   ```
   Where:
   - `algorithm_name` can be one of the following values:
     - `dfs`: For running the Depth-first search algorithm.
     - `ids`: For running the Iterative Deepening search algorithm.
     - `astar1`: For running the A* search algorithm with heuristic 1 (Manhattan Distance).
     - `astar2`: For running the A* search algorithm with heuristic 2 (Misplaced Tiles).
   - `input_file_path`: Path of the file containing the space-separated input state.

### Example
Run the program:
```bash
java 8puzzle astar1 C:\Users\dinht\OneDrive\Documents\input.txt
```

Sample input (`input.txt`):
```
6 7 1 8 2 * 5 4 3
```

## Heuristic Analysis
1. **Manhattan Distance Heuristic**: Calculates the sum of the Manhattan distances (horizontal and vertical distance) of each tile from its goal position. It provides a better estimate of the cost to reach the goal, leading to a smaller search space and faster performance.
2. **Misplaced Tiles Heuristic**: Counts the number of tiles that are in the wrong position compared to the goal state. It is simple and fast to compute but can underestimate the cost to reach the goal, leading to a larger search space and potentially slower performance.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact
For any questions or suggestions, please contact [dinhtrungpham125@gmail.com](mailto:dinhtrungpham125@gmail.com).

---

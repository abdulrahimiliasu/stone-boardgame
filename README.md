# Stone Board Game

## Description
Consider the game board shown in the image. Initially, a piece of stone is placed on the top left square of the board. 
The goal of the puzzle game is to move the stone to the bottom right square marked with the `* (green square)` symbol. Initially,
the stone can be moved horizontally or vertically.
The numbers in the squares specify the number of squares that the stone must step in a move along the desired direction starting from that square.
However, when the stone lands on a framed square `[] (red square)`, it can be moved only diagonally, until it lands on an unframed square again. 
When the stone lands on an unframed square again, it must be moved horizontally or vertically again.

```text
        0     1     2     3    4    5    6    7
     ╔═════╦═════╦═════╦═════╦═══╦═════╦═══╦═════╗
  0  ║  4  ║ 2   ║ [2] ║ 4   ║ 4 ║ [3] ║ 4 ║ [3] ║
     ╠═════╬═════╬═════╬═════╬═══╬═════╬═══╬═════╣
  1  ║ 3   ║ 5   ║ 3   ║ 4   ║ 2 ║ 3   ║ 5 ║ [2] ║
     ╠═════╬═════╬═════╬═════╬═══╬═════╬═══╬═════╣
  2  ║ 4   ║ 3   ║ 2   ║ [5] ║ 2 ║ 2   ║ 5 ║ 2   ║
     ╠═════╬═════╬═════╬═════╬═══╬═════╬═══╬═════╣
  3  ║ 7   ║ 1   ║ 4   ║ 4   ║ 4 ║ 2   ║ 2 ║ 3   ║
     ╠═════╬═════╬═════╬═════╬═══╬═════╬═══╬═════╣
  4  ║ [3] ║ 2   ║ 2   ║ 4   ║ 2 ║ 5   ║ 2 ║ 5   ║
     ╠═════╬═════╬═════╬═════╬═══╬═════╬═══╬═════╣     
  5  ║ 2   ║ [3] ║ 2   ║ 4   ║ 4 ║ 2   ║ 5 ║ [1] ║     
     ╠═════╬═════╬═════╬═════╬═══╬═════╬═══╬═════╣    n  - Number of Movement in each Square
  6  ║ 6   ║ 2   ║ 2   ║ [3] ║ 2 ║ 5   ║ 6 ║ 3   ║       - Non Framed Square (Vertical and Horizontal Movements Only)
     ╠═════╬═════╬═════╬═════╬═══╬═════╬═══╬═════╣   [n] - Framed Square (Diagonal Movements Only)
  7  ║ 1   ║ [2] ║ 5   ║ 4   ║ 4 ║ 2   ║ 1 ║ *   ║    *  - Goal Square
     ╚═════╩═════╩═════╩═════╩═══╩═════╩═══╩═════╝
```

Starting Position = **(0,0)**\
Goal Position = **(7,7)**

## Requirements
Building the project requires JDK 11 or later and [Apache Maven](https://maven.apache.org/).
## Usage


Created on Mac-OS , using Java 16.

&nbsp;

Install [PostgreSQL Driver](https://jdbc.postgresql.org) and Run with the following commands, from the project's root directory:

* mvn package

* java -jar ./target/stone-boardgame-1.0.jar

&nbsp;

&nbsp;

Developer:
Illo Abdulrahim Iliyasu
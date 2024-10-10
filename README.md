# Java Minesweeper

Welcome to the Java Minesweeper game! This is a classic Minesweeper game implemented in Java with a graphical user interface.

## Getting Started

## Operating System Compatibility

This project has been tested and is compatible with the following operating systems:

- **Windows**: Fully supported and tested on Windows 10 and 11.

### Prerequisites

- Java 8 or higher
- Maven 3.6.0 or higher
- Add JAVA_HOME and M2_HOME variable and path in environment variables

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Gameplay](#gameplay)

## Introduction

This project is a simple implementation of the Minesweeper game using Java. The game features a graphical user interface built with Swing, allowing users to interact with the game board and uncover tiles to avoid mines.

## Features

- Customizable grid size (between 2x2 and 26x26)
- Adjustable number of mines (up to 35% of the total grid size)
- Graphical user interface with Swing
- Option to restart the game after it ends

### Installing

1. **Clone the repository**:

   ```sh
   git clone https://github.com/yourusername/java-minesweeper.git
   cd java-minesweeper
   ```
   
2. **Check java and maven in place**:
   ```sh
   java -version
   mvn -version
   ```

3. **Build the project using Maven**:

   ```sh
   mvn clean install -Dmaven.test.skip=true
   ```

4. **Running the Game**:

   To start the Minesweeper game, run the following command:

   ```sh
   mvn exec:java -Dexec.mainClass="MineSweeperRunner"
   ```
   
5. **Run Test**::
   ```sh
   mvn test
   ```

## Usage

When you run the game, you will be prompted to enter the size of the grid and the number of mines. Follow the on-screen instructions to play the game. After entering the grid size and number of mines, a Swing panel will launch, allowing you to click the tiles and play the game. After the game ends, you will have the option to play again.

## Gameplay

- **Objective**: Uncover all the tiles without detonating any mines.
- **Controls**: Click on a tile to uncover it. If you uncover a mine, the game ends.
- **Winning**: You win the game by uncovering all the tiles that do not contain mines.

## Run Test

Open project in Eclipse or Intellij IDE and Junit-4.13.2.jar and hamcrest-core-1.3.jar and run the MineSweeperRunnerTest and MineSweeperServiceTest

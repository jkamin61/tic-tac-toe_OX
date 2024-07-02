# Ultimate Tic Tac Toe

Welcome to the Ultimate Tic Tac Toe game project! This README provides an overview of the project, how to set it up, and how to play the game. This game is built using Java Swing, AWT, and FX and offers both single-player and multiplayer modes with various difficulty levels and features.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Gameplay](#gameplay)
  - [Single Player](#single-player)
  - [Multiplayer](#multiplayer)
- [Controls](#controls)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Ultimate Tic Tac Toe**: Play on a 3x3 grid of tic tac toe boards, making the game more challenging and strategic.
- **Random Move Generator**: For added unpredictability in single-player mode.
- **Bot with 3 Difficulty Levels**: Choose between easy, medium, and hard levels to match your skill.
- **Move History**: Track and review all the moves made during the game.
- **Single Player and Multiplayer**: Play against the bot or with a friend on the same device.
- **Keyboard Controls**: Use the num pad to play, enhancing accessibility and ease of use.

## Installation

To run this project locally, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/ultimate-tic-tac-toe.git
2. **Navigate to the project directory**:
    ```bash
    cd ultimate-tic-tac-toe
3. **Compile the project**:
    ```bash
    javac -d bin src/**/*.java
4. **Run the game**:
    ```bash
    java -cp bin com.yourpackage.Main
Ensure you have Java installed on your machine. This project requires Java 8 or later.

## Usage
Once the game is running, you will be presented with a menu to choose between single-player and multiplayer modes. Select your desired mode and start playing!

## Gameplay
### Single Player
In single-player mode, you play against the bot. You can choose the difficulty level at the beginning of the game:

**Easy**: The bot makes random moves.
**Medium**: The bot makes semi-strategic moves.
**Hard**: The bot uses advanced strategies to challenge you.

### Multiplayer
In multiplayer mode, two players can play on the same device. Players take turns to make their moves.

## Controls
The game supports keyboard controls using the num pad for making moves. The num pad layout corresponds to the cells on the board as follows:

-
7 | 8 | 9
---------
4 | 5 | 6
---------
1 | 2 | 3
-

- Press the corresponding num pad key to make a move in the desired cell.
- Use the arrow keys to navigate through the 3x3 boards.
## Contributing
I welcome contributions to enhance this project! If you would like to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch:
    ```bash
    git checkout -b feature/your-feature-name
3. Make your changes and commit them:
    ```bash
    git commit -m 'Add some feature'
4. Push to the branch:
    ```bash
    git push origin feature/your-feature-name
5. Open a pull request to the main branch.
Please make sure your code adheres to the project's coding standards and includes appropriate tests.


Thank you for checking out the Ultimate Tic Tac Toe game project! We hope you enjoy playing and contributing to the game. If you have any questions or feedback, please feel free to open an issue in the repository. Happy gaming!

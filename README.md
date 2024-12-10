# Distributed Averaging System (DAS)

A Java application that implements a distributed system for computing averages using UDP communication. The system consists of two roles:
- **Master**: Collects data, calculates averages, and broadcasts results.
- **Slave**: Sends data to the Master.

## Features
- **Master Mode**: Listens on a specified port and manages distributed data.
- **Slave Mode**: Automatically connects to the Master and sends a number.
- UDP-based communication for lightweight, fast data exchange.

## Installation

### Requirements
- JDK 1.8 or higher installed.
- Operating system with a command-line interface.

### Steps
1. Clone the repository or download the source files.
2. Navigate to the folder containing the `.java` files.
3. Compile the source code:
    ```bash
    javac *.java
    ```
4. Run the application
    ```bash
    java DAS <$PORT> <$NUMBER>
    ```
### Usage
## Master Mode
To start the application in Master mode, specify a port and a number:

    java DAS 5000 10

- `5000` is the port where the Master listens.
- `10` is the initial number added to the calculation.

## Slave Mode
To start the application in Slave mode, specify the same port as the Master and the number to send:

    java DAS 5000 20

- `5000` is the Master's port.
- `20` is the number sent to Master.
If the port is already in use, the application will automatically run in Slave mode.

## Protocol

- Master:
  - Listens on the specified port.
  - Receives numbers from Slaves.
  - Sends the calculated average or exit signal via broadcast.
- Slave:
  - Sends a number to the Master and awaits further broadcasts.

## Example Workflow
1. Start the Master:

        java DAS 5000 10

2. Start one or more Slaves:

        java DAS 5000 20
        java DAS 5000 30

3. The Master calculates and broadcasts the average when a 0 is received:

        java DAS 5000 0

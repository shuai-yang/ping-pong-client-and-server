## Overview

Simulate a ping pong game using server/client methodology. The Server program has a runServer() main server method that accepts connections and starts off a new thread by calling ServerThread class to handle each accepted connection. In client program, all clients connects to one  server. The PingPong program is where the main class is and finally, a Ping Pong ball is represented simply using an empty serializable object. 

## Building the code

Compile all java programs in hw1 folder :<br>
`$ javac hw1/*.java `<br>

Specify the command line argument 
<em>Usage: java PingPong <client|server> \<serverHost> <port#></em><br>
We always start server first :<br>
`$ java hw1.PingPong `**`server`**` localhost 5005`<br>

Open another terminal, start client : <br>
`$ java hw1.PingPong `**`client`**` localhost 5005`<br>

Now the game starts infinitely. Then in the client-side terminal, kill the current game with a `Ctrl+C` command. <br>

To start another game, stay in the client-side terminal, and start client again :<br>
`$ java hw1.PingPong `**`client`**` localhost 5005`<br>

Now the game starts infinitely. To stop all games, go to the server-side terminal, kill the server with a `Ctrl+C` command

## Testing

- Opened at least 3 terminals to simulate 1 server and at least 2 clients who are playing games simultaneously with the server. Then kill the games one by one, watch the change on the displayed game numbers in the output recognizing which game is killed and which games are still active. 
- Set delay time to make the output readable.
- Tested the program using port 5005 and 5006 respectively.

## Reflection

- Wrote a PingPong for the main class with [server|client] option in command for Client and Server programs. This is a better design than repeating main class in both Client and Server program respectively. 
- Understand synchronization concept with example.
- Understand why this is an "Multithreaded Server, Singlethreaded client" example.
- Understand how the object/information is passed/exchanged between server and client   

Note: The screenshot of sample output clearly tells me what I am expected to achieve. It would be even better to have more sample outputs including both simple and complicated cases (multiple games are played simultaneously). 

package hw1;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.IOException;

import java.util.Random;

public class Server{
    private ServerSocket ss;
    private int gameNum = 1;
    
    public Server(int port){
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void runServer(){
        Socket socket;

        try {
            while (true) {
            	socket = ss.accept();
                System.out.println("Starting game #"+ gameNum + " with client /"+ socket.getInetAddress().getHostAddress());
                ServerThread thread = new ServerThread(socket, gameNum);
                incGameNum();
                thread.start();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public synchronized void incGameNum() {
    	gameNum++;
    }
}  

class ServerThread extends Thread {
    private Socket socket = null;
    private String role = "pong";
    private int gameNum;
    
    ServerThread(Socket socket, int gameNum) throws SocketException{
        this.socket = socket;
        setPriority(NORM_PRIORITY - 1);
        this.gameNum = gameNum;
    }

    public int generateRandom() {
        Random generator = new Random();
        return generator.nextInt(1000000); 
    } 

    public void run() {
        try {
        	int serverNum = generateRandom();
            
        	InputStream in = socket.getInputStream(); 
        	ObjectInputStream oin = new ObjectInputStream(in);
            int clientNum = (int) oin.readObject();
        	
            System.out.println("Server coin toss: my number: " + serverNum + " their number: " + clientNum);
            
            OutputStream out = socket.getOutputStream(); 
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(serverNum);
            oout.flush();
            
            if (serverNum < clientNum) {
            	role = "ping";
            } 
            System.out.println("Coin toss over: Server plays " + role); 
            
            Ball ball = null;
            if (role.equals("ping")) {
            	 ball = (Ball) oin.readObject();
            }
            System.out.println("Game #" + gameNum + " starts ....");
        	
            while(true) {
        		if (role.equals("ping")) {
        			oout.writeObject(ball);
        			oout.flush();
        			System.out.println("Game #" + gameNum + " Server sent ping");
        			ball = (Ball) oin.readObject();
        			System.out.println("Game #" + gameNum + " Server received pong");
        		} else {
        			ball = (Ball) oin.readObject();
        			System.out.println("Game #" + gameNum + " Server received ping");
        			oout.writeObject(ball);
        			oout.flush();
        			System.out.println("Game #" + gameNum + " Server sent pong");
        		}
        		Thread.sleep(1000);
            }
        } catch (EOFException e) {
            System.out.println("PingPong: " + e + "\nGame #" + gameNum + " Over!");
        } catch (SocketException e) {
        	System.out.println("PingPong: I/O error \nGame #" + gameNum + " Over!");
        } catch (ClassNotFoundException e) {
        	System.err.println(e);
		} catch (InterruptedException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
    }
}
   





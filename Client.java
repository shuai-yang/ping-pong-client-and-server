package hw1;

import java.net.Socket;
import java.net.SocketException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Random;

import java.io.EOFException;
import java.io.IOException;

public class Client{
	private Socket s;
	private Ball b;
	
	public Client (String host, int port){
        try {
            s = new Socket (host, port); 
        } catch (IOException e) {
            System.err.println(e);
        }
    }
	
	public void setBall(Ball b) {
		this.b = b;
	}
	
	public void runClient() {
	    try {
			Random generator = new Random();
	        int clientNum = generator.nextInt(1000000);
	        
	        OutputStream out = s.getOutputStream(); 
	        ObjectOutputStream oout = new ObjectOutputStream(out);
	        oout.writeObject(clientNum);
	        oout.flush();
	        
	        InputStream in = s.getInputStream(); 
	        ObjectInputStream oin = new ObjectInputStream(in);
	        int serverNum = (int) oin.readObject();
	    	
	        System.out.println("Client coin toss: my number: " + clientNum + " their number: " + serverNum);
	        
	        String role = "pong";
	        if (clientNum < serverNum) {
	        	role = "ping";
	        } 
	        System.out.println("Coin toss over: Client plays " + role); 
	        
	        if (role.equals("pong")) {
	        	oout.writeObject(b);
	        	oout.flush();
            }
            System.out.println("Game starts ....");
           
        	while(true) {
        		if (role.equals("ping")) {
        			oout.writeObject(b);
        			oout.flush();
        			System.out.println("Client sent ping");
        			b = (Ball) oin.readObject();
        			System.out.println("Client received pong");     
        		} else {
        			b = (Ball) oin.readObject();
        			System.out.println("Client received ping");
        			oout.writeObject(b);
        			oout.flush();
        			System.out.println("Client sent pong");
        		}
                Thread.sleep(1000); 
        	}

	    } catch (EOFException e) { 
	        System.out.println("PingPong: " + e +"\nGame Over!");
	    } catch (IOException e) {
			System.out.println("PingPong: I/O error \nGame Over!");
	    } catch (ClassNotFoundException e) {
	        System.err.println(e); 
	    } catch (InterruptedException e) {
	    	System.err.println(e);
		}
	} 
}  


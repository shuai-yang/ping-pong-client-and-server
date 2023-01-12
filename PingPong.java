package hw1;

import java.io.IOException;

public class PingPong {
	public static void main(String args[]) {
        if (args.length != 3) {
            System.err.println("Usage: java PingPong <client|server> <serverHost> <port#>");
            System.exit(1);
        }
        String role = args[0];
        String host = args[1];
        int port = Integer.parseInt(args[2]);
        if (role.equals("server")) {
	        Server server = new Server(port); 
	        server.runServer();
        } else {
        	Client client = new Client(host, port);
        	client.setBall(new Ball());
        	client.runClient();
        }
	}
}
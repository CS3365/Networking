/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package networking;

import NetworkIO.ClientBase;
import NetworkIO.ServerBase;

/**
 *
 * @author mike
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        pl("Creating Server...");
        ServerBase server = new ServerBase(1029);
        pl("Creating client and connecting to server");
        ClientBase client = new ClientBase("localhost",1029);
        pl("Registering listeners...");
        TestListener serverListener = new TestListener("server"),
                clientListener = new TestListener("client");
        server.addNetworkListener(serverListener);
        client.addNetworkListener(clientListener);
        pl("Sending message");
        client.send(new StringMessage("test message!!!"));
        server.sendToAll(new StringMessage("got it!!!"));
    }

    public static void pl(String msg) {
        System.out.println(msg);
    }

}

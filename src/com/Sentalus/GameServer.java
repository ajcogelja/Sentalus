package com.Sentalus;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map;

public class GameServer {
    private HashMap<String, double[]> playerData = new HashMap<>();
    private ArrayList<Player> players = new ArrayList<>();
    private static int Id = 0;
    private final List<ClientThread> clients = new ArrayList<ClientThread>();
    private int port;
    private Player player;

    private GameServer(int port){
        this.port = port;
    }

    private void start(){
        try{
            ServerSocket ss = new ServerSocket(port);
            while(true) {
                Socket socket = ss.accept();
                Runnable run = new ClientThread(socket, Id++);
                Thread thread = new Thread(run);
                clients.add((ClientThread) run);
                thread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private final class ClientThread implements Runnable{
        Socket socket;
        ObjectInputStream fromClient;
        ObjectOutputStream toClient;
        int id;
        String username;
        Player clientsPlayer;
        SimpleDateFormat date = new SimpleDateFormat("kk:mm:SSS");

        private ClientThread(Socket socket, int id){
            this.id = id;
            this.socket = socket;
            try{
                toClient = new ObjectOutputStream(socket.getOutputStream());
                fromClient = new ObjectInputStream(socket.getInputStream());
                //Player fetchedPlayer = (Player) fromClient.readObject();
                //players.add(fetchedPlayer);
                //System.out.println(date.format(new Date()) + " " + fetchedPlayer.getName() + " was added");
                /*String username = (String) fromClient.readObject();
                FileOutputStream fos = new FileOutputStream("Server/Players" + username + ".gif");
                int c;
                while((c = fromClient.read()) > -1){
                    fos.write(c);
                }
                fos.close(); */
                //player.setSprite(new ImageView(new Image(new FileInputStream("Server/Players" + player.getName() + ".gif"))));

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        private int userSent = 0;
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(1);
                    Player fetchedPlayer = (Player) fromClient.readObject();
                    String name = (String) fromClient.readObject();
                    double xPos = (double) fromClient.readObject();
                    double yPos = (double) fromClient.readObject();
                    double location[] = {xPos, yPos};
                    playerData.put(name, location);
                    System.out.println(fetchedPlayer.getName() + ": received");
                    fetchedPlayer.setXPos(xPos);
                    //System.out.println(xPos + " " + yPos);
                    fetchedPlayer.setYPos(yPos);
                    //System.out.println(fetchedPlayer.getName() + " X: " + fetchedPlayer.getXPos() + " Y: " + fetchedPlayer.getYPos());
                    boolean exists = false;
                    for (Player p : players) {
                        if (p.getName().equals(fetchedPlayer.getName())){
                            //p.setYPos(yPos);
                            //p.setXPos(xPos);
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        players.add(fetchedPlayer);
                        System.out.println("User added to list");
                    } else {
                        System.out.println("User already exists");
                    }
                    toClient.writeObject(players.get(userSent));
                    //TEST
                    toClient.writeObject(players.get(userSent).getXPos());
                    toClient.writeObject(players.get(userSent).getYPos());
                    //END OF TEST
                    //toClient.writeObject(players.get(userSent).getXPos());
                    //toClient.writeObject(players.get(userSent).getYPos());
                    System.out.println(players.get(userSent).getName() + " was sent to client");
                    if (userSent == players.size() - 1){
                        userSent = 0;
                    } else {
                        userSent++;
                    }
                } catch (Exception e){

                }
            }
        }

    }

    public static void main(String[] args) {
        GameServer server = new GameServer(1900);
        server.start();
    }

}

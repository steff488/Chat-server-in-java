
import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;


public class Client {

    public static String host;
    public static int port;
    public static Socket socket;

    public static DataOutputStream dos;
    public static DataInputStream dis;

    public static Scanner scan;
    public static boolean access_state = false;

    public static void main(String[] args) throws IOException {

        host = "127.0.0.1";
        port = 69;
        NewThread connection_thread = new NewThread();
        try {
            socket = new Socket(host, port);
            System.out.println("Connected.");

            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            scan = new Scanner(System.in);
            connection_thread.start();

            int tries = Integer.parseInt(dis.readUTF());

            int username_tries;
            for (username_tries = 0; username_tries < tries; username_tries++) {
                String username_error;
                System.out.print("\nUsername: ");
                dos.writeUTF(scan.next());
                dos.flush();
                username_error = dis.readUTF();
                if (username_error.equals("valid")) {
                    break;
                } else if (username_error.equals("invalid")) {
                    System.out.println("Invalid username!");
                }
            }
            dos.writeUTF(Integer.toString(username_tries));

            if (username_tries < tries) {
                int password_tries;
                for (password_tries = 0; password_tries < tries; password_tries++) {
                    String password_error;
                    System.out.print("\nPassword: ");
                    dos.writeUTF(scan.next());
                    dos.flush();
                    password_error = dis.readUTF();
                    if (password_error.equals("valid")) {
                        break;
                    } else if (password_error.equals("invalid")) {
                        System.out.println("Invalid password.");
                    }
                }
                dos.writeUTF(Integer.toString(password_tries));

                if (password_tries == tries) {
                    System.out.println("\nAccess denied.\nPassword inserted too many times.");
                    socket.close();
                    System.exit(0);
                }
            } else {
                System.out.println("\nAccess denied.\nUsername inserted too many times.");
                socket.close();
                System.exit(0);
            }

            String access_message = dis.readUTF();

            if (access_message.equals("Granted.")) {
                access_state = true;
                System.out.println("Access granted.");
            } else if (access_message.equals("Not granted.")) {
                System.out.println("Access denied.");
            }

            while (access_state) {
                System.out.println(dis.readUTF());
                System.out.print("Input action id: ");
                String action_id = scan.next();
                dos.writeUTF(action_id);
                dos.flush();

                if (action_id.equals("4")) {
                    access_state = false;
                    throw new Exception("");
                }
            }
        } catch (Exception e) {
            //socket.close();
            connection_thread.stop_thread();
        }
    }
}

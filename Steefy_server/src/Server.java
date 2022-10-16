
import java.nio.charset.StandardCharsets;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.math.*;
import java.security.*;

public class Server {

    public static int PORT;
    public static ServerSocket ss;
    public static Socket con;
    public static NewThread thread_69 = new NewThread();

    public static DataOutputStream dos;
    public static DataInputStream dis;

    public static String username = "Steefy02";
    public static String password = "cfec54b531315ebdfb64451639cd8d29cba98f9c070288154cbc622f83efc2ea";
    //password = dari
    public static Boolean granted = false;

    public static void main(String[] args) throws IOException {

        PORT = 69;
        try {
            ss = new ServerSocket(PORT);
            System.out.println("Server listening on port " + PORT);
            con = ss.accept();
            thread_69.start();
            System.out.println("Accepted connection from " + con.getInetAddress().toString());

            dos = new DataOutputStream(con.getOutputStream());
            dis = new DataInputStream(con.getInputStream());

            int tries = 5;
            dos.writeUTF(Integer.toString(tries));

            String usr;
            String username_error = "invalid";
            for (int i = 0; i < tries; i++) {
                usr = dis.readUTF();
                System.out.println("Inputted username: " + usr);
                if (usr.equals(username)) {
                    username_error = "valid";
                    dos.writeUTF(username_error);
                    dos.flush();
                    break;
                } else {
                    username_error = "invalid";
                    dos.writeUTF(username_error);
                    dos.flush();
                }
            }
            int username_tries = Integer.parseInt(dis.readUTF());
            if (username_tries == tries) {
                throw new Exception("");
            }

            String pswd;
            String password_error = "invalid";

            for (int i = 0; i < tries; i++) {
                pswd = dis.readUTF();
                System.out.println("Inputted password: " + pswd);
                if (password.equals(toHexString(getSHA(pswd)))) {
                    password_error = "valid";
                    dos.writeUTF(password_error);
                    dos.flush();
                    break;
                } else {
                    password_error = "invalid";
                    dos.writeUTF(password_error);
                    dos.flush();
                }
            }
            int password_tries = Integer.parseInt(dis.readUTF());
            if (password_tries == tries) {
                throw new Exception("");
            }

            granted = username_error.equals("valid") && password_error.equals("valid");

            if (granted) {
                dos.writeUTF("Granted.");
                dos.flush();
            }

            while (granted) {
                dos.writeUTF("1 - Piu \n2 - Mac \n3 - Pac pac \n4 - Ok bye");
                dos.flush();
                int id = Integer.parseInt(dis.readUTF());
                switch (id) {
                    case 1 -> {
                        System.out.println("Case 1.");
                    }
                    case 2 -> {
                        System.out.println("Case 2.");
                    }
                    case 3 -> {
                        System.out.println("Case 3.");
                    }
                    case 4 -> {
                        throw new Exception("");
                    }
                }
            }
        } catch (Exception e) {
            //con.close();
            thread_69.stop_thread();
        }
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}
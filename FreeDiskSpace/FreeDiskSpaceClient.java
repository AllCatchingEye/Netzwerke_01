import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FreeDiskSpaceClient {

    public static String HOST;
    public static int PORT;
    public static String path;

    private void connectToServer(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Host: ");
        HOST = scanner.nextLine();
        System.out.println("Port: ");
        PORT = Integer.parseInt(scanner.nextLine());
        System.out.println("Path(pls use double slashes): ");
        path = scanner.nextLine() + "\n";
        scanner.close();

        try (Socket socket = new Socket(HOST, PORT)){
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bw.write(path);
                    bw.flush();

                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    System.out.println(br.readLine());
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        new FreeDiskSpaceClient().connectToServer();
    }
}
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class FreeDiskSpaceServer {
    public static final int PORT = 4711;

    private void startServer() {
        try (ServerSocket servSock = new ServerSocket(PORT)) {
            System.out.println("Warte auf clients...");
            try (Socket sock = servSock.accept()) {
                try {
                    System.out.println("Client has connected!");

                    BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    Path path = Paths.get(br.readLine());

                    long freeSpace = Files.getFileStore(path).getUnallocatedSpace();
                    long TotalSpace = Files.getFileStore(path).getTotalSpace();
                    Date now = new Date();
                    String dateText = DateFormat.getDateInstance().format(now);
                    String timeText = DateFormat.getTimeInstance().format(now);

                    String answer = dateText + " " + timeText + ": Path '" + path + "' "
                            + freeSpace + " bytes of " + TotalSpace + " bytes free";
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                    bw.write(answer);
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        new FreeDiskSpaceServer().startServer();
    }
}

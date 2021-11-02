import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class Server_HTTP {

	public static void main(String[] args) {
		Server_HTTP server = new Server_HTTP();
		server.startServer();

	}

	private void startServer() {
		while (true) {
			try (ServerSocket servSock = new ServerSocket(8082)) {
				System.out.println("Server started, waiting for clients...");

				try (Socket s = servSock.accept();
					 BufferedReader fromClient = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
					 BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8))) {
					System.out.println("Got client connection!");

					String HOST = "/hm.edu";
					for (String line = fromClient.readLine(); line != null
							&& line.length() > 0; line = fromClient.readLine()) {
						if (line.contains("GET")) {
							String[] parts = line.split(" ");
							if (!parts[1].equals("/")) {
								HOST =  parts[1];
							}
							System.out.println("Found GET: " + HOST);
						}
						System.out.println("Client says: " + line);
					}
					final String RES = "";
					HttpURLConnection con;
					StringBuilder index = new StringBuilder();
					try {
						con = (HttpURLConnection) new URL("https:/" + HOST + "/" + RES).openConnection();
						try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
							for (String line = br.readLine(); line != null && line.length() > 0; line = br.readLine()) {
								index.append(line);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}


					String forClient = quarkEditor(index.toString());
					forClient = replaceIMG(forClient);

					toClient.write("HTTP/1.1 200 OK\r\n");
					toClient.write("Content-length: " + forClient.getBytes().length + "\r\n");
					toClient.write("\r\n");
					toClient.write(forClient);
					toClient.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private String quarkEditor(String text) {
		String quarkPattern = "KI|Maschinelles Lernen|Java|Informatikerin|Informatiker|InformatikerInnen|Software|"
		+ "Linux|Windows|Studierende|Studentin|Student|Informatik|Debugger|CISC|RISC|Computer|MMIX";
		return text.replaceAll(quarkPattern, "Quark (It is wednesday!)");
	}

	private String replaceIMG(String text) {
		//return text.replace("<img(.*?)src=\"(.*?)\"(.*?)/>", "I bim 1 Test");
		Pattern patt = Pattern.compile("<img(.*?)src=\"(.*?)\">");
		//return text.replaceAll(String.valueOf(patt), "<img src=\"../frog.png\"");
		//return text.replaceAll(String.valueOf(patt), "<img src=\"https://thumbs.gfycat.com/LamePerfumedHapuka-mobile.mp4\"");
		return text.replaceAll(String.valueOf(patt), "<img src=\"https://thumbs.gfycat.com/DesertedNeglectedErne-mobile.mp4\"");
	}
}

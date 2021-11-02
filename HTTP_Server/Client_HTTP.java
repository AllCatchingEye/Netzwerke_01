

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client_HTTP {
	// public static final String host ="localhost:4711";
	public static final String HOST = "localhost";
	public static final String RES = "";
	public static final String INFO = "hm.edu";

	public static void main(String[] args) {
		Client_HTTP client = new Client_HTTP();
		client.doRequest();
	}

	public void doRequest() {
		// HttpURLConnection con;
		try {
			// open the connection
			// - for unencrypted HTTP use "http://" as protocol specification
			// - for TLS/HTTPS simply use "https://" (Java library will handle encryption
			// and certificate handling)
			//con = (HttpURLConnection) new URL("https://" + HOST + "/" + RES).openConnection();
			try (Socket socket = new Socket(HOST, 8082);
				 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
				bw.write("GET /" + RES + " HTTP/1.1\r\n");
				bw.write("Host: " + HOST + "\r\n");
				bw.write("INFO: " + INFO + "\r\n");
				bw.write("Content-Type: text/html; charset=utf-8\r\n");
				bw.write("\r\n");
				bw.flush();

				try (InputStream input = socket.getInputStream();
					 BufferedReader fromServer = new BufferedReader(new InputStreamReader(input))) {

					// read complete response (content only, headers are decoded by URLConnection!)
					for (String line = fromServer.readLine(); line != null; line = fromServer.readLine()) {
						System.out.println(line);
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

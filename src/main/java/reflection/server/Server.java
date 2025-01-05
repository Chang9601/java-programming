package reflection.server;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpServer;

import reflection.configuration.ServerConfiguration;

public class Server {
	public void startServer() throws IOException {
		HttpServer httpServer = HttpServer.create(ServerConfiguration.getInstance().getServerAddress(), 0);
		
		httpServer.createContext("/greeting").setHandler((exchange) -> {
			String responseMessage = ServerConfiguration.getInstance().getGreetingMessage();
			
			exchange.sendResponseHeaders(200, responseMessage.length());
			
			OutputStream responseBody = exchange.getResponseBody();
			responseBody.write(responseMessage.getBytes());
			responseBody.flush();
			responseBody.close();
		});
		
		System.out.println(String.format("주소 %s:%d에서 서버 시작", 
				ServerConfiguration.getInstance().getServerAddress().getHostName(),
				ServerConfiguration.getInstance().getServerAddress().getPort()));
		
		httpServer.start();
	}
}
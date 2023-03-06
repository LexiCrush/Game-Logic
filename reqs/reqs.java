package reqs;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import Logic.*;

public class reqs {
    public static QuestionGenerator qg;
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/get-q", new HelloHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class HelloHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            qg = new QuestionGenerator();
            qg.getRandomNbTable();
            qg.getReadableNounFromTableName();
            qg.getFilter();
            qg.promptAssembler();
            
            String response = QuestionGenerator.assembledPrompt;
            t.getResponseHeaders().set("Content-Type", "text/plain");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    

}
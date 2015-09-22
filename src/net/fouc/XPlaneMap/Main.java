package net.fouc.XPlaneMap;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.json.simple.JSONObject;

public class Main {
	
    public static void main(String[] args) throws Exception {
    	
    	//initialize the plane list
    	PlanesList list = new PlanesList();
    	
    	//start X-Plane data listening thread
    	new Thread(new UDPListener(list)).start();
    	System.out.println("Started listening to X-Plane");
    	
    	//start web server
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler(list));
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Started the web server");
        
        
        //launch browser
        //figure out "real" IP
        
        Socket s = new Socket("google.com", 80);
        String url = "http://" + s.getLocalAddress().getHostAddress() + ":8000/";
        s.close();
        
        if(Desktop.isDesktopSupported()){ //windows
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else { //Linux
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open "+ url);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        System.out.println("Started browser");
        System.out.println("The map is now visible at address " + url +" on this computer and any device on the same network.");
        
    }

    static class MyHandler implements HttpHandler {
    	private PlanesList planesList;
    	
    	public MyHandler(PlanesList list_) {
    		this.planesList = list_;
    	}
    	
        @SuppressWarnings("unchecked")
		public void handle(HttpExchange t) throws IOException {
        	String req = t.getRequestURI().toString();
        	if (req.startsWith("/data")) {
        		
        		JSONObject planes = new JSONObject();
        		JSONObject latlon;
        		for (String ip : this.planesList.getLatMap().keySet()) {
        			latlon = new JSONObject();
        			latlon.put("lat", this.planesList.getLatMap().get(ip));
        			latlon.put("lon", this.planesList.getLonMap().get(ip));
        			latlon.put("alt", this.planesList.getAltMap().get(ip));
        			planes.put(ip.replace('.', '-').substring(1) , latlon);
        		}
        		StringWriter out = new StringWriter();
        		planes.writeJSONString(out);
        		String response = out.toString();
        		
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
        	}
        	else {
        		sendFile(t, "index.html");
        	}
        	
        }
        
        private void sendFile(HttpExchange t, String file) {
        	try {
        		InputStream is =  this.getClass().getClassLoader().getResourceAsStream(file);
                t.sendResponseHeaders(200, 0);
                OutputStream os = t.getResponseBody();
                
                byte[] buffer = new byte[1024];
                int len = is.read(buffer);
                while (len != -1) {
                    os.write(buffer, 0, len);
                    len = is.read(buffer);
                }
                is.close();
                os.close();
        	}
        	catch (Exception e){
        		e.printStackTrace();
        	}
        }
    }

}
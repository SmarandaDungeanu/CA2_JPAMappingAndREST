package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import exceptions.NotFoundException;
import facades.PersonFacade;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Person;
import model.RoleSchool;
import utils.Utils;

public class Rest
{

    private static int port;
    private static String ip;
    private static String publicFolder;
    private static String startFile;
    private static String filesUri;

    public Rest()
    {
        Properties properties = Utils.initProperties("server.properties");
        ip = properties.getProperty("serverIp");
        port = Integer.parseInt(properties.getProperty("serverPort"));
        publicFolder = "public/";
        startFile = "index.html";
        filesUri = "/pages";
    }

    public void run() throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);

        //REST Routes
        server.createContext("/person", new HandlerPerson());
        server.createContext("/role", new HandlerRole());

        //HTTP Server Routes
        server.createContext(filesUri, new HandlerFileServer());

        server.start();
        System.out.println("Server started, listening on port: " + port);
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length >= 3)
        {
            port = Integer.parseInt(args[0]);
            ip = args[1];
            publicFolder = args[2];
        }
        new Rest().run();
    }

    class HandlerRole implements HttpHandler
    {

        PersonFacade facade = new PersonFacade();

        @Override
        public void handle(HttpExchange he) throws IOException
        {
            String response = "";
            int status = 200;
            String path = he.getRequestURI().getPath();
            if (path.lastIndexOf("/") > 0)
            { //Role/id
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String jsonQuery = br.readLine();
                RoleSchool r = facade.addRoleSchoolFromGson(jsonQuery, id);
                //   System.out.println(facade.addRoleSchoolFromGson(jsonQuery, id));
                //   System.out.println(new Gson().toJson(r));
                response = "added";
                //   System.out.println(response);
            }
            else
            {
                response = "Provide a person id";
            }
            he.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            he.getResponseHeaders().add("Content-Type", "application/json");
            he.sendResponseHeaders(status, 0);
            try (OutputStream os = he.getResponseBody())
            {
                os.write(response.getBytes());
            }
        }
    }

    class HandlerPerson implements HttpHandler
    {

        PersonFacade facade = new PersonFacade();

        @Override
        public void handle(HttpExchange he) throws IOException
        {
            String response = "";
            int status = 200;
            String method = he.getRequestMethod().toUpperCase();
            switch (method)
            {
                case "GET":
                    try
                    {
                        String path = he.getRequestURI().getPath();
                        if (path.lastIndexOf("/") > 0)
                        { //person/id
                            int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                            response = facade.getPersonAsJSON(id);
                            System.out.println(response);
                        }
                        else
                        { //person
                            response = facade.getPersonsAsJSON();
                        }
                    } catch (NumberFormatException nfe)
                    {
                        response = "Id is not a number.";
                        status = 404;
                    } catch (NotFoundException ex)
                    {
                        response = "No person found for this id.";
                        status = 404;
                    }
                    break;
                case "POST":
                    InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String jsonQuery = br.readLine();
                    Person p = facade.addPersonFromGson(jsonQuery);
                    response = new Gson().toJson(p);
                    break;
                case "PUT":
                    break;
                case "DELETE":
                    try
                    {
                        String path = he.getRequestURI().getPath();
                        int lastIndex = path.lastIndexOf("/");
                        if (lastIndex > 0)
                        {  //person/id
                            int id = Integer.parseInt(path.substring(lastIndex + 1));
                            Person p1 = facade.delete(id);
                            response = new Gson().toJson(p1);
                        }
                        else
                        {
                            status = 400;
                            response = "<h1>Bad Request</h1>No id supplied with request";
                        }
                    } catch (NumberFormatException nfe)
                    {
                        response = "Id is not a number";
                        status = 404;
                    } catch (NotFoundException ex)
                    {
                        Logger.getLogger(Rest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    break;
            }
            he.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            he.getResponseHeaders().add("Content-Type", "application/json");
            he.sendResponseHeaders(status, 0);
            try (OutputStream os = he.getResponseBody())
            {
                os.write(response.getBytes());
            }
        }
    }

    class HandlerFileServer implements HttpHandler
    {

        @Override
        public void handle(HttpExchange he) throws IOException
        {
            int responseCode = 500;
            //Set initial error values if an un expected problem occurs
            String errorMsg = null;
            byte[] bytesToSend = "<h1>Internal Error </h1><p>We are sorry. The server encountered an unexpected problem</p>".getBytes();
            String mime = null;

            String requestedFile = he.getRequestURI().toString();
            String f = requestedFile.substring(requestedFile.lastIndexOf("/") + 1);
            try
            {
                String extension = f.substring(f.lastIndexOf("."));
                mime = getMime(extension);
                File file = new File(publicFolder + f);
                System.out.println(publicFolder + f);
                bytesToSend = new byte[(int) file.length()];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(bytesToSend, 0, bytesToSend.length);
                responseCode = 200;
            } catch (Exception e)
            {
                responseCode = 404;
                errorMsg = "<h1>404 Not Found</h1>No context found for request";
            }
            if (responseCode == 200)
            {
                Headers h = he.getResponseHeaders();
                h.set("Content-Type", mime);
            }
            else
            {
                bytesToSend = errorMsg.getBytes();
            }
            he.sendResponseHeaders(responseCode, bytesToSend.length);
            try (OutputStream os = he.getResponseBody())
            {
                os.write(bytesToSend, 0, bytesToSend.length);
            }
        }

        private String getMime(String extension)
        {
            String mime = "";
            switch (extension)
            {
                case ".pdf":
                    mime = "application/pdf";
                    break;
                case ".png":
                    mime = "image/png";
                    break;
                case ".js":
                    mime = "text/javascript";
                    break;
                case ".html":
                    mime = "text/html";
                    break;
                case ".jar":
                    mime = "application/java-archive";
                    break;
            }
            return mime;
        }
    }
}

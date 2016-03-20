/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mongo101java.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;


/**
 *
 * @author Matt
 */
public class WebClientVerticle extends AbstractVerticle{
    
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();
        
        //Router router = new Router();
        
        server.requestHandler(request -> {

         // This handler gets called for each request that arrives on the server
        HttpServerResponse response = request.response();
        response.putHeader("content-type", "text/plain");

        // Write to the response and end it
        response.end("Hello World!");
        });

    server.listen(8080);
    }
       
    
}

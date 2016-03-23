/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mongo101java.verticles;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matt
 */
public class WebClientVerticle extends AbstractVerticle {

    Version v = new Version(2,3,23);
    Configuration configuration = new Configuration(v);
    //configuration. //setClassForTemplateLoading(SparkHomework.class, "/");
    // Need to figure out how to set the classfor template loading.
    
    
    //ToDo Need to create a main verticla that launches others.
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.route().path("/home");
        router.route().handler(routingContext -> {

            // This handler will be called for every request
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");
            StringWriter writer = new StringWriter();
            try{
                Template helloTemplate = configuration.getTemplate("answer.ftl");
                Map<String, String> answerMap = new HashMap<String, String>();
                answerMap.put("answer", createAnswer());
                helloTemplate.process(answerMap, writer);
                response.end(writer.toString());
               
            }
            catch(Exception e)
            {
                response.end("Seems we had a problem :"+e.getMessage());
            }
            //response.write(string);
            // Write to the response and end it
            //response.end("Hello World from Vert.x-Web!");
        });

        server.requestHandler(router::accept).listen(8080);
    }
    
        // Create a silly answer that's not obvious just by code inspection.  Easier just to get it running!
    private static String createAnswer() {
        int i = 0;
        for (int bit = 0; bit < 16; bit++) {
            i |= bit << bit;
        }
        return Integer.toString(i);
    }
    
    /*
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(
                SparkHomework.class, "/");

        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request,
                                 final Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = configuration.getTemplate("answer.ftl");

                    Map<String, String> answerMap = new HashMap<String, String>();
                    answerMap.put("answer", createAnswer());

                    helloTemplate.process(answerMap, writer);
                } catch (Exception e) {
                    logger.error("Failed", e);
                    halt(500);
                }
                return writer;
            }
        });
    
    
    
    */
}

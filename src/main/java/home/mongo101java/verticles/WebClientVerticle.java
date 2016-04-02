/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mongo101java.verticles;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matt Eaton
 * @Date 4/2/2016
 * @Summary Trying to merge Vert.x Http server and not use Spark
 */
public class WebClientVerticle extends AbstractVerticle {

   private Version version;// = new Version(2,3,23);
   private Configuration configuration;
   private File resourceFile; 
   private FileReader reader;
   private Template basicTemplate;

   /**
    * 
    * 
    */
    public WebClientVerticle() {
       try {
           initFreemakerConfiguration();
           //this.configuration = new Configuration(v);
           //ClassLoader classLoader = getClass().getClassLoader();
           //resourceFile = new File(classLoader.getResource("answer.ftl").getFile());
           //try{
           //System.out.println("---------------------");
           //System.out.println(resourceFile.getAbsoluteFile().toString());
           //System.out.println(resourceFile.getCanonicalFile().toString());
           //System.out.println(resourceFile.getParentFile().toString());
           //System.out.println("---------------------");
           //}
           //catch(Exception e)
           //{
           //   System.out.println(e.getMessage());
           // }
       } catch (IOException ex) {
           Logger.getLogger(WebClientVerticle.class.getName()).log(Level.SEVERE, null, ex);
       }
       finally{
           
       }
    }
  
    /**
     * 
     */
    private void initFreemakerConfiguration() throws IOException{
        version = new Version(2,3,23);
        configuration = new Configuration(version);
        ClassLoader classLoader = getClass().getClassLoader();
        resourceFile = new File(classLoader.getResource("answer.ftl").getFile());
        
        System.out.println("---------------------");
        System.out.println(resourceFile.getAbsoluteFile().toString());
        System.out.println(resourceFile.getCanonicalFile().toString());
        System.out.println(resourceFile.getParentFile().toString());
        System.out.println("---------------------");
        configuration.setDirectoryForTemplateLoading(resourceFile.getParentFile());
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        System.out.println("FreeMarker configuration initilization coplete");
    }
            
    
        
    /**
     * AbstractVerticle uses start to initialize the verticle.
     * 
     * @throws java.lang.Exception
     */
    public void start() throws Exception {
        //reader = new FileReader(resourceFile);
        HttpServer server = vertx.createHttpServer();
        //configuration.setDirectoryForTemplateLoading(resourceFile.getParentFile());
        Router router = Router.router(vertx);
        router.route().path("/home");
        router.route().handler(routingContext -> {

            // This handler will be called for every request
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");
            StringWriter writer = new StringWriter();
            //try {
            //    basicTemplate = new Template("basicTemplate", reader, configuration);
            //} 
            //catch (IOException ex) {
            //    Logger.getLogger(WebClientVerticle.class.getName()).log(Level.SEVERE, null, ex);
            //}
            
            //Template temp = configuration.getTemplate("answer.ftl");
            try{
                //Template helloTemplate = configuration.getTemplate("answer.ftl");
                basicTemplate = configuration.getTemplate("answer.ftl");
                Map<String, String> answerMap = new HashMap<String, String>();
                answerMap.put("answer", createAnswer());
                basicTemplate.process(answerMap, writer);
                //reader.close();
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
        System.out.println("*********************************************");
        System.out.println("* Server Running http://localhost:8080/home *");
        System.out.println("*********************************************");
    }
    
    /**
     * Helper method used to return a 
     * @return String from Integer.toString
     */
    private static String createAnswer() {
        int i = 0;
        for (int bit = 0; bit < 16; bit++) {
            i |= bit << bit;
        }
        return Integer.toString(i);
    }
}

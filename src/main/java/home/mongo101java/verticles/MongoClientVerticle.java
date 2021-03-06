package home.mongo101java.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
//import io.vertx.examples.utils.Runner;
import io.vertx.ext.mongo.MongoClient;

public class MongoClientVerticle extends AbstractVerticle {

//ToDo Need to check if Mongo is even running before trying to do anything and return a message


  @Override
  public void start() throws Exception {

    JsonObject config = Vertx.currentContext().config();
    
    // Should try and use the JAVA8 is null option
    String uri = config.getString("mongo_uri");
    if (uri == null) {
      uri = "mongodb://localhost:27017";
    }
    String db = config.getString("mongo_db");
    if (db == null) {
      db = "test";
    }

    JsonObject mongoconfig = new JsonObject()
        .put("connection_string", uri)
        .put("db_name", db);

    MongoClient mongoClient = MongoClient.createShared(vertx, mongoconfig);

    JsonObject product1 = new JsonObject().put("itemId", "12345").put("name", "Cooler").put("price", "100.0");

    mongoClient.save("products", product1, id -> {
      System.out.println("Inserted id: " + id.result());

      mongoClient.find("products", new JsonObject().put("itemId", "12345"), res -> {
        System.out.println("Name is " + res.result().get(0).getString("name"));

        mongoClient.remove("products", new JsonObject().put("itemId", "12345"), rs -> {
          if (rs.succeeded()) {
            System.out.println("Product removed ");
          }
        });

      });

    });

  }
}

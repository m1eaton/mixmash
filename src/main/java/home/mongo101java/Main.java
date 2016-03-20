/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mongo101java;

import home.mongo101java.verticles.MongoClientVerticle;
import home.mongo101java.verticles.WebClientVerticle;
import io.vertx.core.Vertx;

/**
 *
 * @author Matt
 */
public class Main {
    
    // ToDo Add logging
    
    public static void main(String[] args)
    {
        Main.deployit(Vertx.vertx());
        System.out.println("----- Init------");
    }
    
    public static void deployit(Vertx vert)
  {
    vert.deployVerticle(new MongoClientVerticle());
    vert.deployVerticle(new WebClientVerticle());
  }
}

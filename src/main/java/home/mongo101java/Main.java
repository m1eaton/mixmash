/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mongo101java;

import home.mongo101java.verticles.MongoClientVerticle;
import home.mongo101java.verticles.WebClientVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matt
 */
public class Main {

    // ToDo Add logging
    private static final Logger logger = LoggerFactory.getLogger("logger");

    public static void main(String[] args) {
        Main.deployit(Vertx.vertx());
        System.out.println("----- Init------");
    }
    /**
     * Simple static class that uses Vertx instance to deploy verticles.
     * @param vert 
     */
    public static void deployit(Vertx vert) {
        logger.info("Deploying verticles");
        //vert.deployVerticle(new MongoClientVerticle());
        vert.deployVerticle(new WebClientVerticle());
    }
}

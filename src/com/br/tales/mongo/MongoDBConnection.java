/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.tales.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Dillei
 */
public class MongoDBConnection {
    
    private MongoClient client;
    private MongoDatabase dataBase;
    private String ipServer = "192.168.1.10";
    private int port = 27017;
    private String dbName = "talesOfDillei";
    private static MongoDBConnection MDBC;
    
    
    private MongoDBConnection(){ 
        client = new MongoClient(ipServer,port);
        dataBase = client.getDatabase(dbName); 
    }
    
    public static MongoDatabase getDataBase(){
        if(MDBC == null)
            return new MongoDBConnection().dataBase;
        return MDBC.dataBase;
    }
    
     @Override
    protected void finalize(){
        client.close();
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.tales.map;

import org.bson.Document;

/**
 *
 * @author Dillei
 */
public class Localization {
    
    public KeyLocalization key;
    public Terreno type;
    
    public Localization(KeyLocalization key, Terreno type){
        this.key = key;
        this.type = type;
    }
    
    public Document toDocument(){
        Document doc = new Document();
        doc.append("KeyLocalization", key.toDocument());
        doc.append("Terreno", type.toString());
        return doc;        
    }
    
    
    
}

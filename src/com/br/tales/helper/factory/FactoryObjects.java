/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.tales.helper.factory;

import com.br.tales.dilleyer.account.Account;
import com.br.tales.dilleyer.account.Dilleyer;
import com.br.tales.map.KeyLocalization;
import com.br.tales.map.Localization;
import com.br.tales.map.Terreno;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Dillei
 */
public class FactoryObjects {
    
    public static Dilleyer newDilleyer(Document doc){
        return new Dilleyer(doc.getString("nome"), 
                          FactoryObjects.newKeyToKeyLocalization((Document) doc.get("KeyLocalization") ),
                          doc.getBoolean("logado"));
    }
    
    public static Account newAccount(Document doc){
        List<Document> listChars = (List<Document>) doc.get("characters");
        List<Dilleyer> chars = new ArrayList<>();
        
        for(Document d : listChars){
            chars.add(FactoryObjects.newDilleyer(d) );
        }
        
        return new Account(doc.getString("user"), doc.getString("password"), doc.getString("email"), chars);
        
    }
    
    public static KeyLocalization newKeyToKeyLocalization(Document doc){
       return new KeyLocalization(doc.getInteger("ref_x"), doc.getInteger("ref_y"), doc.getInteger("ref_z"));
    }
    
    public static Localization documentToLocalization(Document doc){
        return new Localization( FactoryObjects.newKeyToKeyLocalization((Document) doc.get("KeyLocalization"))  , Terreno.valueOf( doc.get("Terreno").toString() ) );
    }
    
}

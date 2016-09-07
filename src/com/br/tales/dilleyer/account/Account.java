/*
 * Esta conta está responsável por guardar informações de uma conta do jogo
 */
package com.br.tales.dilleyer.account;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class Account {
    
    public final String user;
    public final String password;
    public final String email;
    public List<Dilleyer> characters;
    
    public Account(String user, String password, String email){
        this.user = user;
        this.password = password;
        this.email = email;
        this.characters = new ArrayList<>();
    }
    
    public Account(String user, String password, String email, List<Dilleyer> characters){
        this.user = user;
        this.password = password;
        this.email = email;
        this.characters = characters;
    }
    
    public Document toDocument(){
        Document doc = new Document();
        doc.append("user", user);
        doc.append("password", password);
        doc.append("email", email);
        doc.append("characters", charactersToDocument());
        return doc;
    }
    
    private List<Document> charactersToDocument(){
        List<Document> li = new ArrayList<>();
        for(Dilleyer d : characters){
            li.add(d.toDocument());
        }
        return li;
    }
    
    
    
    
}

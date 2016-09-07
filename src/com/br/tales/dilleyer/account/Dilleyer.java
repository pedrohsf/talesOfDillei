/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.tales.dilleyer.account;

import com.br.tales.map.DilleiLandia;
import com.br.tales.map.KeyLocalization;
import com.br.tales.map.WalkDirection;
import org.bson.Document;

/**
 *
 * @author Dillei
 */
public class Dilleyer {
    
    private String nome;
    private boolean logado;
    public KeyLocalization pos;
    
    public Dilleyer(String nome){
        this.nome = nome;
        this.pos = new KeyLocalization(0, 0, 0);
        this.logado = false;
    }
    
    public Dilleyer(String nome, KeyLocalization pos){
        this.nome = nome;
        this.pos = pos;
        this.logado = false;
    }
    
    public Dilleyer(String nome, KeyLocalization pos, boolean logado){
        this.nome = nome;
        this.pos = pos;
        this.logado = logado;
    }
    
    public void setLogado(boolean logado){
        this.logado = logado;
    }
    
    public Document toDocument(){
        Document doc = new Document();
        doc.append("nome", nome);
        doc.append("logado", logado);
        doc.append("KeyLocalization", pos.toDocument());
        return doc;
    }
    
    public boolean walk(WalkDirection direction){
        KeyLocalization newPos = DilleiLandia.getInstance().walk(direction, pos);
        if( newPos != pos ){
            this.pos = newPos;
            return true;
        }
        return false;
    }
    
   
    
}

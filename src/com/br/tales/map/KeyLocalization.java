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
public class KeyLocalization {
    
    private final int ref_x;
    private final int ref_y;
    private final int ref_z;

    public KeyLocalization(int ref_x, int ref_y, int ref_z){
        this.ref_x = ref_x;
        this.ref_y = ref_y;
        this.ref_z = ref_z;
    }
    
    public Document toDocument(){
        Document doc = new Document();
        doc.append("ref_x", this.ref_x);
        doc.append("ref_y", this.ref_y);
        doc.append("ref_z", this.ref_z);
        return doc;
    }
    
    /*
        Retorna uma "STRINGKEY" uma chave com os posicionamentos separados por ,
    */
    public String getStringKey(){
        return ref_x + "," + ref_y + "," + ref_z;
    }
    
    
    /*
        retorna KeyLocalization para o lado q o cara quer andar
    */
    public String getStringKey(WalkDirection direction){
        int next_x = this.ref_x;
        int next_y = this.ref_y;
        int next_z = this.ref_z;
        switch(direction){
            case NORTH: next_y++; break;
            case SOUTH: next_y--; break;
            case EAST: next_x++;  break;
            case WEST: next_x--;  break;
        }
        return next_x + "," + next_y + "," + next_z;
    }
    
    
}

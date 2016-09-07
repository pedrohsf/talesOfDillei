/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.tales.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Dillei
 */
public class DilleiLandia {
    
    private Map<String, Localization> landiaHash;
    private static DilleiLandia DL;
    
    private DilleiLandia(){
        landiaHash = new HashMap<>();
        Random r = new Random();
        
        for(int x = 0 ; x < 40 ; x++){
            for(int y = 0 ; y < 40 ; y++){
                Localization z = new Localization(new KeyLocalization(x, y, 0), Terreno.values()[r.nextInt(3)]);
                landiaHash.put(z.key.getStringKey(), z);
            }
        }
    }
    
    public static DilleiLandia getInstance(){
        if(DilleiLandia.DL == null)
            DilleiLandia.DL = new DilleiLandia();
        return DilleiLandia.DL;
    }
    
    /*
        Procura no mapa a proxima KeyLocalization de um dado ponto
        Caso exista : retorna a próxima;
        Caso não exista : retorna a atual;
    */
    
    public KeyLocalization walk(WalkDirection direction, KeyLocalization actual){
        if(this.landiaHash.get(actual.getStringKey(direction)) != null )
            return this.landiaHash.get(actual.getStringKey(direction)).key;
        return actual;
    }
    
    
    
}

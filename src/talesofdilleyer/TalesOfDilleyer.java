/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package talesofdilleyer;

import com.br.tales.dilleyer.account.Account;
import com.br.tales.dilleyer.account.Dilleyer;
import com.br.tales.map.KeyLocalization;
import com.br.tales.map.Localization;
import com.br.tales.map.Terreno;
import com.br.tales.map.WalkDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bson.Document;

/**
 *
 * @author Dillei
 */
public class TalesOfDilleyer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        Dilleyer pedro = new Dilleyer("Pedro");
        
        System.out.println(pedro.toDocument().toJson());
        System.out.println(pedro.walk(WalkDirection.NORTH));
        
        System.out.println(pedro.toDocument().toJson());
        System.out.println(pedro.walk(WalkDirection.SOUTH));
        
        System.out.println(pedro.toDocument().toJson());
        System.out.println(pedro.walk(WalkDirection.WEST));
        
        System.out.println(pedro.toDocument().toJson());
        System.out.println(pedro.walk(WalkDirection.EAST));
        
        System.out.println(pedro.toDocument().toJson());
        System.out.println(pedro.walk(WalkDirection.WEST));
        
        System.out.println(pedro.toDocument().toJson());
        
    }
    
}

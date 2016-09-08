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
import com.br.tales.mongo.account.AccountDontExist;
import com.br.tales.mongo.account.AccountOperation;
import com.br.tales.mongo.account.UserAlreadyUsed;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
       
        Account ac;
       
        AccountOperation ao = new AccountOperation();
        try {
            ac = ao.existAccount("arrois", "senha");
            Dilleyer d = ac.characters.get(0);
            d.logado = false;
            
            ao.saveDilleyer(ac);
            
            
        } catch (Exception ex) {
            Logger.getLogger(TalesOfDilleyer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }
    
}

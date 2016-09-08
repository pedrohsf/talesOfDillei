/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.tales.mongo.account;

import com.br.tales.mongo.account.exceptions.CharacterNameUsed;
import com.br.tales.mongo.account.exceptions.UpdateCreateCharacterFail;
import com.br.tales.mongo.account.exceptions.UserAlreadyUsed;
import com.br.tales.mongo.account.exceptions.FailSaveCharacter;
import com.br.tales.mongo.account.exceptions.AccountDontExist;
import com.br.tales.dilleyer.account.Account;
import com.br.tales.dilleyer.account.Dilleyer;
import com.br.tales.helper.factory.FactoryObjects;
import com.br.tales.language.StringsLanguage;
import com.br.tales.mongo.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Dillei
 */
public class AccountOperation {
    
    private MongoCollection<Document> accountsCollection;
    
    public AccountOperation(){
        this.accountsCollection = MongoDBConnection.getDataBase().getCollection("Accounts");
    }
    
    public synchronized void registerNewAccount(Account account) throws UserAlreadyUsed {
        // se não for encontrado uma conta com users iguais salva.
        if ( accountsCollection.find(new Document().append("user", account.toDocument().getString("user"))).first() == null)
            accountsCollection.insertOne(account.toDocument());
        else
            throw new UserAlreadyUsed(StringsLanguage.userAlreadyUsed);
    }
    
    public void createNewDilleyer(Account account, String nome) throws Exception{
        // A conta já está registrada no banco \/
      
            account = existAccount(account.user, account.password);
            // O personagem não existe \/
            if(characterDontExist(account, nome)){
                // O personagem é adicionado a conta
                account.characters.add(new Dilleyer(nome));
                
                // Update no banco acontece 
                if(accountsCollection.updateOne(new Document().append("user", account.user),  new Document("$set", account.toDocument())  ) != null){
                    // personagem criado e dilleyer criado
                }else{ // lança exceção erro no update  
                    throw new UpdateCreateCharacterFail(StringsLanguage.updateCreateCharacterFail);
                }
                
            }else{
                throw new CharacterNameUsed(StringsLanguage.characterNameUsed);
            }
            
        
        
        
    }
    
    
    // Salva mas mudanças feitas no personagem, conta etc
    public void saveDilleyer(Account account) throws FailSaveCharacter{
        // Update no banco acontece
        if(accountsCollection.updateOne(new Document().append("user", account.user),  new Document("$set", account.toDocument())  ) != null){
            // personagem editado com sucesso
        }else{
            throw new FailSaveCharacter(StringsLanguage.failSaveCharacter);
        }
    }
    
    
    // Verifica no banco a existencia de uma account dado um User e um Password
    public Account existAccount(String user, String password) throws AccountDontExist{
        Document account = accountsCollection.find(new Document().append("user", user).append("password", password)).first();
        if( account != null ){
            return FactoryObjects.newAccount(account);
        }
        throw new AccountDontExist(StringsLanguage.accountDontExist);
    }
    
    // True if character Dont exist in this account
    // else false.
    private boolean characterDontExist(Account account, String nome) {
        for(Dilleyer di : account.characters){
            if(di.nome.equals(nome))
                return false;
        }
        return true;
    }


    
    
    
}

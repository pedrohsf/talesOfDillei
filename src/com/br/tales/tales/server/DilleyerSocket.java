/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.tales.tales.server;

import com.br.tales.dilleyer.account.Account;
import com.br.tales.dilleyer.account.Dilleyer;
import com.br.tales.language.StringsLanguage;
import com.br.tales.mongo.account.AccountOperation;
import com.br.tales.mongo.account.exceptions.AccountDontExist;
import com.br.tales.mongo.account.exceptions.FailSaveCharacter;
import com.br.tales.mongo.account.exceptions.UserAlreadyUsed;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.json.JsonParseException;

/**
 *
 * @author Dillei
 */
public class DilleyerSocket implements Runnable {
    
    private Account acc;
    private Socket socket;
    private AccountOperation banco;
    private DataInputStream recived;
    private DataOutputStream send;

    
    
    public DilleyerSocket(Socket socket){
        this.banco = new AccountOperation();
        this.socket = socket;
        try {
            
            this.recived = new DataInputStream(this.socket.getInputStream());
            this.send = new DataOutputStream(this.socket.getOutputStream()); 
            
        } catch (IOException ex) {
            System.out.println(ex + " Erro ao tentar conectar o socket de ip : "+ socket.getInetAddress().toString());
        }
        
    }
    
    @Override
    public void run() {
        
        // espera até recer uma menssagem do client
        Document recive = this.newReciveToDocument(this.waitRecive());
        // Tenta encontrar o tipo de conexão que o client quer fazer "typeRequest" é a key
        // Os valores podem ser "tryNewAccount" ou "tryLogin" 
        String typeRequest = recive.getString("typeRequest");
        
        if (typeRequest != null){
            if(typeRequest.equals("tryNewAccount")){
                tryNewAccount(recive);
            }else if(typeRequest.equals("tryLogin")){
                if(tryLogin(recive)){// se conseguir logar, espera a escolha do personagem

                    sendToClient("tryLogin", this.acc.toDocument());// envia os personagens para logar
                    
                    chooseCharacterToStarPlay();
                    
                }//  caso não consiga logar, ele sai direto   
            }
        }else{
            this.sendToClient("Sorry", "But this don't is my protocol");
            this.closeThisSocket();
        }
    }
    
    private void chooseCharacterToStarPlay(){
        Document reciveCharToLogin = this.newReciveToDocument(this.waitRecive());// espera ate a resposta
        // verifica se o personagem existe
        String typeRequest = reciveCharToLogin.getString("typeRequest");
        String characterName = reciveCharToLogin.getString("characterName");
        
        if (    (typeRequest != null && typeRequest.equals("tryLogin")) &&
                (characterName != null)
                ){ 
            
            for(Dilleyer player : acc.characters){
                // quando encontrar o personagagem passa a responsabilidade de jogar para outra classe
                if(player.nome.equals(characterName)){
                    new DilleyerSocketPlayer(this, player).play();
                    this.closeThisSocket(); // jogador saiu
                    // quando sair do personagem ou o socket morrer ele sai do for
                    break;
                }
            }
            
        }
        // caso não encontre o personagem envia a menssagem de erro e sai
        this.sendToClient("tryLogin", StringsLanguage.tryLoginFailCharacterDoesExist);
    }
    
    // Se o jogador conseguir logar retorna true
    private boolean tryLogin(Document doc){
        if(validDocumentForTryLogin(doc)){ 
            try{
                
                this.acc = banco.existAccount(doc.getString("user"), doc.getString("password"));
                return true;
            }catch(AccountDontExist e){
                sendToClient("tryLogin", StringsLanguage.accountDontExist);
            }
        }
        return false;
    }
    
    private boolean validDocumentForTryLogin(Document doc){
        // verifica se a menssagem não é nula e nem está vazia
        if( doc.getString("user") != null &&
            doc.getString("password") != null)
        if( !doc.getString("user").isEmpty() && 
            !doc.getString("password").isEmpty())
                return true;
        // se for nula ou vazia envia menssagem de erro
        sendToClient("tryLogin", StringsLanguage.tryLoginFailEmptyStrings);
        return false;
    }
    
    /*
        Tenta criar uma nova conta, para criar uma nova conta o document recebido deve conter
        user , password e email, caso não cotenha algum desses 3 ele reenvia uma menssagem de erro ao client
        
    */
    private void tryNewAccount(Document doc){
        if(validDocumentForNewAccount(doc)){
            try {// se não estiverem vazios tenta criar uma nova conta
                banco.registerNewAccount(new Account(doc.getString("user"), 
                                                    doc.getString("password"), 
                                                    doc.getString("email")));
                sendToClient("tryNewAccount",StringsLanguage.tryNewAccountSucess); 
            } catch (UserAlreadyUsed ex) { // se a conta já existir, envia erro ao client
               sendToClient("tryNewAccount",StringsLanguage.tryNewAccountFailUserAlreadyUsed);
               System.out.println(ex);
            }
        }
    }
    
    private void sendToClient(String type,String message){
        try {
            send.writeUTF(new Document().append(type, message).toJson());
        } catch (IOException ex) { // provavelmente socket fechado
            System.out.println(ex + ". Erro ao tentar enviar menssagem type : "+type +" Message: "+ message);
        }
    }
    
    private void sendToClient(String type,Document doc){
        try {
            send.writeUTF(new Document().append(type, doc).toJson());
        } catch (IOException ex) { // provavelmente socket fechado
            System.out.println(ex + "erro ao tentar enviar menssagem type : "+type +" Message: "+ doc);
        }
    }
    
    private boolean validDocumentForNewAccount(Document doc){
      // verifica se os campos não estão vazios nem nulos,  se não estiverem, o documento pode ser cadastrado
        if(doc.getString("user") != null &&
           doc.getString("password") != null &&
           doc.getString("email") != null)
            if( !doc.getString("user").isEmpty()  && 
                !doc.getString("password").isEmpty() && 
                !doc.getString("email").isEmpty()
            )   return true;
        
        // envia erro ao client caso campos vazios
        sendToClient("tryNewAccount",StringsLanguage.tryNewAccountFailEmptyStrings);
        this.closeThisSocket();
        return false;
    }
    
    
    public Document newReciveToDocument(String json){
        
        try{
            Document doc = new Document();
            doc = Document.parse(json);
            return doc;
        }catch(Exception e){
            return new Document();
        }
        
    }
    
    
    
    private String waitRecive(){
        try {
            String recive = recived.readUTF();
            
            while(recive.isEmpty()){ 
                    Thread.sleep(50);
                    recive = recived.readUTF();
                    System.out.println(recive);
            }
            return recive;
        } catch (InterruptedException ex) {
            this.closeThisSocket();
            System.out.println(ex);
        }catch(IOException ex){
            this.closeThisSocket();
            System.out.println(ex);
        }
        return "";
    }
    
    
    
    @Override
    protected void finalize() throws Throwable {
        socket.close();
    }
    
    private void closeThisSocket(){
        try {
            System.out.println("Desconectado : " + this.socket.getInetAddress());
            this.socket.close();
            this.finalize();
        } catch (IOException ex) {
            Logger.getLogger(DilleyerSocket.class.getName()).log(Level.SEVERE, null, ex); 
        } catch(Throwable t){
            Logger.getLogger(DilleyerSocket.class.getName()).log(Level.SEVERE, null, t); 
        }
    } 


    public Account getAcc() {
        return acc;
    }

    public void setAcc(Account acc) {
        this.acc = acc;
    }

    public Socket getSocket() {
        return socket;
    }

    public AccountOperation getBanco() {
        return banco;
    }

    public DataInputStream getRecived() {
        return recived;
    }

    public DataOutputStream getSend() {
        return send;
    }

    
}

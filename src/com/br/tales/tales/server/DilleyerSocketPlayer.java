/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.tales.tales.server;

import com.br.tales.dilleyer.account.Dilleyer;
import com.br.tales.mongo.account.exceptions.FailSaveCharacter;

/**
 *
 * @author Dillei
 */
public class DilleyerSocketPlayer {
    
    
    private DilleyerSocket dilSocket;
    private Dilleyer charLogado;
    
    
     
    public DilleyerSocketPlayer(DilleyerSocket dilSocket, Dilleyer charLogado){
        this.dilSocket = dilSocket;
        this.charLogado = charLogado;
        // salvando personagem indicando que alguém está conectado nele.
        this.saveCharacter(true);
    }
    
    public void play(){
        
        System.out.println("estou jogando");
        
    }
    
    
    // Salva alterações da conta no Socket
    private void saveCharacter(Boolean logado_deslogado){
        
        if(dilSocket.getAcc() != null){
            try{
                // char logado
                this.charLogado.logado = logado_deslogado;
                dilSocket.getBanco().saveDilleyer(dilSocket.getAcc());
            }catch(FailSaveCharacter e){
                System.out.println(e + "Conta : " + dilSocket.getAcc().user);
            }
        }
    
    }
    
}

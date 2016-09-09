/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.tales.tales.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dillei
 */
public class DilleyerServer {
    
    ServerSocket server ;
    List<DilleyerSocket> conections ;
    
    public DilleyerServer(){
            this.conections = new ArrayList<>();
        try {
            this.server = new ServerSocket(7013);
            System.out.println("Servidor ABERTO...");
            while(true){
                DilleyerSocket newConection =  new DilleyerSocket( server.accept() );
                this.conections.add(newConection);
                // passa a responsabilidade da conexção para uma nova thread
                new Thread(newConection).start(); 
                System.out.println("Nova Conexão : " + newConection.getSocket().getInetAddress() );
            }
            
        } catch (IOException ex) {
            System.out.println("Não foi possivel abrir o servidor : " + ex);
        }
        
          
       
        
    }
    
    
    
}

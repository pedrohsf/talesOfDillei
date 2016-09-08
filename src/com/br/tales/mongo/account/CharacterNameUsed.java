/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.tales.mongo.account;

/**
 *
 * @author Dillei
 */
public class CharacterNameUsed extends Exception {

    /**
     * Creates a new instance of <code>CharacterNameUsed</code> without detail
     * message.
     */
    public CharacterNameUsed() {
    }

    /**
     * Constructs an instance of <code>CharacterNameUsed</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CharacterNameUsed(String msg) {
        super(msg);
    }
}

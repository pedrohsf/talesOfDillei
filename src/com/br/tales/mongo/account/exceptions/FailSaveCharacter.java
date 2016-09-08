/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.tales.mongo.account.exceptions;

/**
 *
 * @author Dillei
 */
public class FailSaveCharacter extends Exception {

    /**
     * Creates a new instance of <code>FailSaveCharacter</code> without detail
     * message.
     */
    public FailSaveCharacter() {
    }

    /**
     * Constructs an instance of <code>FailSaveCharacter</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FailSaveCharacter(String msg) {
        super(msg);
    }
}

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
public class UserAlreadyUsed extends Exception {

    /**
     * Creates a new instance of <code>UserAlreadyUsed</code> without detail
     * message.
     */
    public UserAlreadyUsed() {
    }

    /**
     * Constructs an instance of <code>UserAlreadyUsed</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public UserAlreadyUsed(String msg) {
        super(msg);
    }
}

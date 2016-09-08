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
public class AccountDontExist extends Exception {

    /**
     * Creates a new instance of <code>AccountDontExist</code> without detail
     * message.
     */
    public AccountDontExist() {
    }

    /**
     * Constructs an instance of <code>AccountDontExist</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AccountDontExist(String msg) {
        super(msg);
    }
}

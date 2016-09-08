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
public class UpdateCreateCharacterFail extends Exception {

    /**
     * Creates a new instance of <code>UpdateCreateCharacterFail</code> without
     * detail message.
     */
    public UpdateCreateCharacterFail() {
    }

    /**
     * Constructs an instance of <code>UpdateCreateCharacterFail</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateCreateCharacterFail(String msg) {
        super(msg);
    }
}

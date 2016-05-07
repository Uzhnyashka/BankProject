package com.bankproject.utils;

import sun.misc.BASE64Encoder;

/**
 * Created by bobyk on 05/05/16.
 */
public class Base64Test {
    public static void main(String[] args){
        System.out.println(new BASE64Encoder().encode("Andris:parol".getBytes()) );
    }
}

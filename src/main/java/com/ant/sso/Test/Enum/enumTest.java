package com.ant.sso.Test.Enum;

import com.ant.sso.Common.Operator;

public class enumTest {

    public static void main(String[] args) {
        Operator[] operators=Operator.values();
        int size=operators.length;
        String v=operators[0].getValue();
        System.out.println("end...");

    }
}

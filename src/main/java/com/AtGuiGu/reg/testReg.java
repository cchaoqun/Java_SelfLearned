package com.AtGuiGu.reg;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author Chaoqun Cheng
 * @date 2021-04-2021/4/25-10:03
 */

public class testReg {
    @Test
    public void test1(){
        String a1 = "1 5.3";
        String a2 = "1     5.3";
        String a3 = "1,5.3";
        String a4 = "1:5.3";
        String[] s1 = a1.split(" ",2);
        String[] s2 = a2.split(" ",2);
        String[] s3 = a3.split(",",2);
        String[] s4 = a4.split(":",2);
        String[] s5 = a1.split(":",2);
        System.out.println("s1: "+Arrays.toString(s1)+s1.length);
        System.out.println("s2: "+Arrays.toString(s2)+s2.length);
        System.out.println("s3: "+Arrays.toString(s3)+s3.length);
        System.out.println("s4: "+Arrays.toString(s4)+s4.length);
        System.out.println("s5: "+Arrays.toString(s5)+s5.length);
    }
}

package com.AtGuiGu.Day19_Reflection;

import java.io.Serializable;

/**
 * @author Chaoqun Cheng
 * @date 2021-04-2021/4/23-10:42
 */

public class Creature<T> implements Serializable {
    private char gender;
    public double weight;

    private void breath(){
        System.out.println("生物呼吸");
    }

    public void eat(){
        System.out.println("生物吃东西");
    }

}

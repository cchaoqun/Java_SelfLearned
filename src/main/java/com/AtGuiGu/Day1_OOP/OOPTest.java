package com.AtGuiGu.Day1_OOP;

/**
 * 面向对象编程思想: 强调了具备了功能的对象,以类/对象为最小单位, 考虑谁来做
 * 面向过程编程思想: 以函数为单位,考虑了怎么做,以方法/函数为最小单位
 * 三大特性
 *  封装
 *  继承
 *  多态
 * @author Chaoqun Cheng
 * @date 2021-04-2021/4/21-17:08
 */

public class OOPTest {


    //方法的重载 可变个数的形参 等同于对应类型数组的参数, 且必须声明在最后
    //且可变个数形参在方法中中只能声明一个可变形参
    public void show(String ... args){
        char[] cha = new char[]{'a','b','c'};
        System.out.println(cha);
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        };
    }

//    public void show(String[] args){
//        for (int i = 0; i < args.length; i++) {
//            System.out.println(args[i]);
//        };
//    }


}

package com.AtGuiGu.Day13_InnerClass;

/**
 *
 * 类的内部成员之五：内部类
 * 1. Java中允许将一个类A声明在另一个类B中，则类A就是内部类，类B称为外部类
 *
 * 2.内部类的分类：成员内部类（静态、非静态）  vs 局部内部类(方法内、代码块内、构造器内)
 *
 * 3.成员内部类：
 * 		一方面，作为外部类的成员：
 * 			>调用外部类的结构
 * 			>可以被static修饰
 * 			>可以被4种不同的权限修饰
 *
 * 		另一方面，作为一个类：
 * 			> 类内可以定义属性、方法、构造器等
 * 			> 可以被final修饰，表示此类不能被继承。言外之意，不使用final，就可以被继承
 * 			> 可以被abstract修饰
 *
 *
 * 4.关注如下的3个问题
 *   4.1 如何实例化成员内部类的对象
*      	//创建Dog实例(静态的成员内部类):
 * 		Person.Dog dog = new Person.Dog();
 * 		dog.show();
 * 		//创建Bird实例(非静态的成员内部类):
 * 		Person p = new Person();
 * 		Person.Bird bird = p.new Bird();
 * 		bird.sing();
 *   4.2 如何在成员内部类中区分调用外部类的结构
 *      内部类方法的形参 变量名
 *      内部类定义的属性 this.变量名
 *      外部类定义的属性 外部类名.this.变量名
 *   4.3 开发中局部内部类的使用  见《InnerClassTest1.java》
 *
 *
 * @author Chaoqun Cheng
 * @date 2021-04-2021/4/22-10:15
 */

public class TestInnerClass {
    //返回一个实现了Comparable接口的类的对象
    public Comparable getComparable(){

        //创建一个实现了Comparable接口的类:局部内部类
        //方式一：
//		class MyComparable implements Comparable{
//
//			@Override
//			public int compareTo(Object o) {
//				return 0;
//			}
//
//		}
//
//		return new MyComparable();

        //方式二：
        return new Comparable(){

            @Override
            public int compareTo(Object o) {
                return 0;
            }

        };

    }



}



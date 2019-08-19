package com.ym.mall.service;

import java.util.function.IntBinaryOperator;

/**
 * @author matao
 * @create 2019-08-16 14:36
 * java 8 Lambda表达式
 * Lambda表达式允许把函数作为一个方法的参数（函数作为参数传递进方法中去）
 * Lambda表达式的实例
 * 1、不需要参数，返回值为5
 *    () -> 5
 * 2、接收一个参数（数字类型），返回其2倍的值
 *    x -> 2 * x
 * 3、接收2个参数（数字），并返回他们的差值
 *   (x,y) -> x + y
 * 4、接收2个int型整数，返回他们的和
 *   (int x,int y) -> x + y
 * 5、接收一个string对象，并在控制台打印，不返回任何值（看起来像返回void）
 *   (String s) ->System.out.print(s)
 */
public class LambdaTest {

    public static void main(String[] args) {
        LambdaTest test  = new LambdaTest();
        //类型声明  addition：附加
        MathOperation addition = (int a,int b) -> a + b;
        //不用类型的声明 subtraction：减法
        MathOperation subtraction = (a,b) -> a - b;
        //大括弧中的返回语句 multiplication:乘法
        MathOperation multiplication = (int a,int b) -> {return a * b;};
        //没有大括弧和返回语句   division：分开
        MathOperation division = (int a,int b) -> a / b;

        System.out.println("10 + 5 = " + test.operate(10,5,addition));
        System.out.println("10 - 5 = " + test.operate(10,5,subtraction));
        System.out.println("10 * 5 = " + test.operate(10,5,multiplication));
        System.out.println("10 / 5 = " + test.operate(10,5,division));

        //不用括号
        GreetingService greetingService1 = message -> System.out.println("hello" + message);
        //用括号
        GreetingService greetingService2 = (message) -> System.out.println("hello" + message);

        greetingService1.sayMessage("Runnod");
        greetingService2.sayMessage("Google");
    }

    interface MathOperation{
        int operation(int a,int b);
    }

    interface GreetingService{
        void sayMessage(String message);
    }

    private int operate(int a,int b,MathOperation mathOperation){
        return mathOperation.operation(a,b);
    }
}

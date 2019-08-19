package com.ym.mall.service;

import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author matao
 * @create 2019-08-19 11:12
 * Stream简介
 * java8引入了全新的StreamApi
 * Stream是对集合对象功能的增强，它专注于对集合对象进行各种便利的、高效的聚合操作，或者大批量的数据操作
 * 只要给出需要对其包含的元素执行什么操作，比如：过滤掉长度大于10的字符串，获取每个字符串的首字母等，
 * Stream会隐式地在内部进行遍历，做出相应的数据转换
 *
 */
public class ListDemo {
    public static void main(String[] args) {
        //取出偶数
        List<Integer> list = Arrays.asList(1,2,3,4);
        //1、for循坏
        List<Integer> newList1 = new ArrayList<>();
        for (Integer i: list) {
            if(i % 2 == 0){
                newList1.add(i);
            }
        }
        System.out.println(newList1);
        //2、Stream流
        List<Integer> collect = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        System.out.println(collect);
    }
}

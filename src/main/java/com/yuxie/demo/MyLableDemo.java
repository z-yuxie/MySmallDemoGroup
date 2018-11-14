package com.yuxie.demo;

/**
 * 关于JAVA循环标记的功能试验
 */
public class MyLableDemo {

    public static void main(String[] args) {
        testLable();
    }

    /**
     * 什么是lable?
     * 其功能如期名一样，提供一种标记能力
     * 它的标记能力目前仅作用于循环体
     * 如何创建：
     * 先起一个标签名，然后在其后紧跟一个英文冒号，便算是正式声明了一个标签
     * 如下方的outLable或innerLable就是循环体标签lable
     * 使用在何处：
     * 循环体标签只能标记在一个循环体上，代码体现上便是标签后紧跟了一个循环体
     * 但一个循环体可以被多个标签修饰，只是多个标签修饰时
     * 只有最后一个标签能使用continue关键字，前面的标签只能使用break关键字
     * 循环体标签的有效作用域：
     * 每个声明的标签仅能作用于其标记的循环体内部，超出循环体后便会失效
     * 如何使用其功能：
     * 在某个标记的作用域内任何一个位置，使用break或continue关键字并指定该标签
     * 即可触发一个该标签修饰的循环体的一个break或continue操作
     * 如在下方示例中的外层i循环和内层j循环
     * 在外层i循环内，内层j循环外触发break或continue操作并指定标签outLable1,
     * 操作效果等同于在外层i循环内，内层j循环外直接触发未指定标签的break或continue操作
     * 在内层j循环内触发指定标签outLable1的break或continue操作时
     * 操作效果也等同于在外层i循环内，内层j循环外直接触发未指定标签的break或continue操作
     * 比如在内层j循环中进行指定标签outLable1的break操作，那个将直接跳出外层i循环，然后执行i循环之后的代码
     * 在内层j循环中进行指定标签outLable1的break操作，那么将会跳出内层j循环，并让i循环立即进入下一次循环（i++后的循环）
     *
     * 实际使用价值：
     * 循环体标签提供了一种能力，就是在嵌套的循环中，可以直接在内层的循环体中
     * 触发一个外层循环体的break或continue操作
     */
    private static void testLable() {
        System.out.println("-------------------测试开始-----------------------");
        System.out.println("-------------------即将经过outLable:------------------------");
        outLable:outLable1:for (int i = 0 ; i < 10 ; i++) {//同行标记
            System.out.println("-------------------进入外层i循环-----------------------");
            System.out.println("-------------------i = " + i + "-----------------------");
            innerLable://换行标记
            //中间可以再插入换行，但不能插入代码
            for (int j = 0 ; j < 10 ; j++) {
                System.out.println("-------------------进入内层j循环-----------------------");
                System.out.println("-------------------i = " + i + "-----------------------");
                System.out.println("-------------------j = " + j + "-----------------------");
                if (j == 5) {
                    System.out.println("-------------------进入内层j循环 j=5 判断-----------------------");
//                    System.out.println("-------------------即将经过内层j循环 j=5 判断后 break outLable1-----------------------");
//                    break outLable1;
                    System.out.println("-------------------即将经过内层j循环 j=5 判断后 continue outLable1-----------------------");
                    continue outLable1;
//                    System.out.println("-------------------即将经过内层j循环 j=5 判断后 continue outLable-----------------------");
//                    break outLable;
//                    System.out.println("-------------------即将经过内层j循环 j=5 判断后 break innerLable-----------------------");
//                    break innerLable;
//                    System.out.println("-------------------即将经过内层j循环 j=5 判断后 continue innerLable-----------------------");
//                    continue innerLable;
                }
                if (i == 6) {
                    System.out.println("-------------------即将经过内层j循环 i=6 判断后 break outLable-----------------------");
                    break outLable;
                }
                System.out.println("-------------------经过内层j循环 j=5 判断-----------------------");
            }
            System.out.println("-------------------经过内层j循环-----------------------");
            if (i == 3) {
                System.out.println("-------------------进入外层i循环 i=3 判断-----------------------");
//                System.out.println("-------------------即将经过外层i循环 i=3 判断后 break outLable-----------------------");
//                break outLable;
//                    System.out.println("-------------------即将经过外层i循环 i=3 判断后 continue outLable-----------------------");
//                    continue outLable;
            }
            System.out.println("-------------------经过外层i循环 i=3 判断-----------------------");
        }
        outLable:
        for (int i = 0 ; i < 5 ; i++) {
            if (i == 3) {
                break outLable;
            }
        }
        System.out.println("-------------------经过外层i循环-----------------------");
    }
}

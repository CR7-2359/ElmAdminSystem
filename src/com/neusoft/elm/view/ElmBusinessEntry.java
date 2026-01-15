package com.neusoft.elm.view;

import com.neusoft.elm.view.impl.BusinessView;

public class ElmBusinessEntry {
    public static void main(String[] args) {
        printBanner();
        new BusinessView().start();
    }

    private static void printBanner() {
        System.out.println("饿了么商家后台 - 商家端");
        System.out.println("学号姓名: 202408764423LiuXuhui");
        System.out.println("--------------------------------");
    }

}

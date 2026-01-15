package com.neusoft.elm.view;

import com.neusoft.elm.view.impl.AdminView;

public class ElmAdminEntry {
    public static void main(String[] args) {
        printBanner();
        new AdminView().start();
    }

    private static void printBanner() {
        System.out.println("饿了么商家后台 - 管理员端");
        System.out.println("学号姓名: 202408764423LiuXuhui");
        System.out.println("--------------------------------");
    }

}

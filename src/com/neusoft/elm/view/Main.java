package com.neusoft.elm.view;

import com.neusoft.elm.utils.InputUtil;
import com.neusoft.elm.view.impl.AdminView;
import com.neusoft.elm.view.impl.BusinessView;

public class Main {
    public static void main(String[] args) {
        printBanner();
        while (true) {
            printMainMenu();
            int choice = InputUtil.readInt("请选择: ");
            switch (choice) {
                case 1:
                    new AdminView().start();
                    break;
                case 2:
                    new BusinessView().start();
                    break;
                case 0:
                    System.out.println("已退出系统。");
                    return;
                default:
                    System.out.println("无效选项。");
                    break;
            }
        }
    }

    private static void printBanner() {
        System.out.println("饿了么商家后台系统");
        System.out.println("学号姓名: 202408764423LiuXuhui");
        System.out.println("--------------------------------");
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("1. 管理员端");
        System.out.println("2. 商家端");
        System.out.println("0. 退出");
    }
}

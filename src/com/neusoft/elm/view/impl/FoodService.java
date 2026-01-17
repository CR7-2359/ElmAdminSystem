package com.neusoft.elm.view.impl;

import com.neusoft.elm.dao.impl.FoodDao;
import com.neusoft.elm.po.Food;
import com.neusoft.elm.utils.ConsoleUi;
import com.neusoft.elm.utils.CsvUtil;
import com.neusoft.elm.utils.InputUtil;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FoodService {
    private static final int PAGE_SIZE = 10;
    private final FoodDao foodDao = new FoodDao();

    public void listFoods(int businessId) {
        List<Food> foods = foodDao.listByBusinessId(businessId);
        if (foods.isEmpty()) {
            System.out.println(ConsoleUi.info("暂无数据。"));
            return;
        }
        if (foods.size() <= PAGE_SIZE) {
            printFoodTable(foods, 1, 1);
            return;
        }
        paginateFoods(foods);
    }

    public void addFood(int businessId) {
        String name = InputUtil.readLine("名称: ");
        BigDecimal price = InputUtil.readBigDecimal("价格: ");
        String description = InputUtil.readLine("描述: ");
        int status = InputUtil.readInt("状态(1=上架,0=下架): ");

        Food food = new Food();
        food.setBusinessId(businessId);
        food.setName(name);
        food.setPrice(price);
        food.setDescription(description);
        food.setStatus(status);

        boolean ok = foodDao.add(food);
        System.out.println(ok ? ConsoleUi.success("食品新增成功。") : ConsoleUi.error("食品新增失败。"));
    }

    public void updateFood(int businessId) {
        int id = InputUtil.readInt("要修改的食品编号: ");
        Food food = foodDao.getByIdForBusiness(businessId, id);
        if (food == null) {
            System.out.println(ConsoleUi.info("暂无数据。"));
            return;
        }
        String name = InputUtil.readLine("名称(回车保留): ");
        String priceText = InputUtil.readLine("价格(回车保留): ");
        String description = InputUtil.readLine("描述(回车保留): ");
        String statusText = InputUtil.readLine("状态1/0(回车保留): ");

        if (!name.isEmpty()) {
            food.setName(name);
        }
        if (!priceText.isEmpty()) {
            try {
                food.setPrice(new BigDecimal(priceText));
            } catch (NumberFormatException ex) {
                System.out.println(ConsoleUi.warn("价格输入无效。"));
                return;
            }
        }
        if (!description.isEmpty()) {
            food.setDescription(description);
        }
        if (!statusText.isEmpty()) {
            try {
                food.setStatus(Integer.parseInt(statusText));
            } catch (NumberFormatException ex) {
                System.out.println(ConsoleUi.warn("状态输入无效。"));
                return;
            }
        }

        boolean ok = foodDao.update(food);
        System.out.println(ok ? ConsoleUi.success("食品修改成功。") : ConsoleUi.error("食品修改失败。"));
    }

    public void deleteFood(int businessId) {
        int id = InputUtil.readInt("要删除的食品编号: ");
        boolean ok = foodDao.deleteByIdForBusiness(businessId, id);
        System.out.println(ok ? ConsoleUi.success("食品删除成功。") : ConsoleUi.error("食品删除失败。"));
    }

    public void exportFoods(int businessId) {
        String filePath = InputUtil.readLine("导出文件名(回车默认foods_" + businessId + ".csv): ");
        if (filePath.isEmpty()) {
            filePath = "foods_" + businessId + ".csv";
        }
        filePath = "csvData/" + filePath;
        List<Food> foods = foodDao.listByBusinessId(businessId);
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"编号", "名称", "价格", "状态", "描述"});
        for (Food food : foods) {
            String statusText = food.getStatus() == 1 ? "上架" : "下架";
            rows.add(new String[]{
                    String.valueOf(food.getId()),
                    food.getName(),
                    String.valueOf(food.getPrice()),
                    statusText,
                    food.getDescription()
            });
        }
        try {
            CsvUtil.writeCsv(Path.of(filePath), rows);
            System.out.println(ConsoleUi.success("导出成功: " + filePath));
        } catch (Exception ex) {
            System.out.println(ConsoleUi.error("导出失败: " + ex.getMessage()));
        }
    }

    private void paginateFoods(List<Food> foods) {
        int totalPages = (foods.size() + PAGE_SIZE - 1) / PAGE_SIZE;
        int pageIndex = 0;
        while (true) {
            int start = pageIndex * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, foods.size());
            printFoodTable(foods.subList(start, end), pageIndex + 1, totalPages);
            System.out.println();
            System.out.println(ConsoleUi.label(ConsoleUi.ICON_MENU, "分页操作"));
            System.out.println("1. " + ConsoleUi.ICON_PREV + " 上一页");
            System.out.println("2. " + ConsoleUi.ICON_NEXT + " 下一页");
            System.out.println("0. " + ConsoleUi.ICON_EXIT + " 退出查询");
            int choice = InputUtil.readInt("请选择: ");
            switch (choice) {
                case 1:
                    if (pageIndex == 0) {
                        System.out.println(ConsoleUi.warn("已经是第一页。"));
                    } else {
                        pageIndex--;
                    }
                    break;
                case 2:
                    if (pageIndex >= totalPages - 1) {
                        System.out.println(ConsoleUi.warn("已经是最后一页。"));
                    } else {
                        pageIndex++;
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println(ConsoleUi.warn("无效选项。"));
                    break;
            }
        }
    }

    private void printFoodTable(List<Food> foods, int page, int totalPages) {
        System.out.println(ConsoleUi.label(ConsoleUi.ICON_FOOD, "食品列表 (第 " + page + "/" + totalPages + " 页)"));
        System.out.printf("%-5s %-20s %-8s %-6s %-20s%n",
                "编号", "名称", "价格", "状态", "描述");
        for (Food food : foods) {
            System.out.printf("%-5d %-20s %-8s %-6s %-20s%n",
                    food.getId(),
                    food.getName(),
                    food.getPrice(),
                    food.getStatus(),
                    food.getDescription());
        }
    }
}

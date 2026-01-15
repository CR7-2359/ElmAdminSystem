# 饿了么商家后台管理系统（控制台版）

本项目是控制台版商家后台系统，包含管理员端与商家端。
支持登录、商家管理、食品管理与 CSV 导出。

## 功能概览
- 管理员登录
- 商家登录
- 管理员：搜索/查看/新增/删除商家
- 商家：查看/修改商家信息、更新密码
- 商家：查看/新增/修改/删除食品
- CSV 导出（管理员导出商家列表，商家导出食品列表，输出到 `csvData/`）

## 代码结构（核心代码）

包 `com.neusoft.elm.dao.impl`
- `AdminDao`：管理员登录查询
- `BusinessDao`：商家登录、商家 CRUD、更新密码
- `FoodDao`：商家食品 CRUD

包 `com.neusoft.elm.po`
- `Admin` / `Business` / `Food`：实体类（字段与表字段一一对应）

包 `com.neusoft.elm.utils`
- `DBUtil`：JDBC 连接工具（URL/账号/密码）
- `InputUtil`：控制台输入工具
- `CsvUtil`：CSV 写入工具（含 UTF-8 BOM，Excel 打开不乱码）

包 `com.neusoft.elm.view`
- `ElmAdminEntry`：管理员端入口（打印 banner + 启动管理员界面）
- `ElmBusinessEntry`：商家端入口（打印 banner + 启动商家界面）
- `Main`：统一入口（选择管理员/商家）

包 `com.neusoft.elm.view.impl`
- `AdminView`：管理员菜单与业务流程
- `BusinessView`：商家菜单与业务流程
- `FoodService`：食品管理与导出

## 核心代码（节选）

### 1. 数据库连接（DBUtil）
```java
public final class DBUtil {
    private static final String URL =
            "jdbc:mysql://localhost:3306/elm_admin?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("未找到MySQL驱动。", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

### 2. 管理员登录（AdminDao）
```java
public Admin login(String name, String password) {
    String sql = "SELECT admin_id, admin_name, admin_password FROM admin WHERE admin_name=? AND admin_password=?";
    try (Connection conn = DBUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, name);
        stmt.setString(2, password);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("admin_id"));
                admin.setName(rs.getString("admin_name"));
                admin.setPassword(rs.getString("admin_password"));
                return admin;
            }
        }
    } catch (Exception ex) {
        System.out.println("管理员登录异常: " + ex.getMessage());
    }
    return null;
}
```

### 3. CSV 导出（CsvUtil）
```java
public static void writeCsv(Path path, List<String[]> rows) throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
        writer.write('\uFEFF'); // UTF-8 BOM, Excel 打开中文不乱码
        for (String[] row : rows) {
            writer.write(toLine(row));
            writer.newLine();
        }
    }
}
```

### 4. 管理员导出商家列表（AdminView）
```java
private void exportBusinessesFlow() {
    String filePath = InputUtil.readLine("导出文件名(回车默认business.csv): ");
    if (filePath.isEmpty()) {
        filePath = "business.csv";
    }
    filePath = "csvData/" + filePath;
    List<Business> businesses = businessDao.listAll();
    List<String[]> rows = new ArrayList<>();
    rows.add(new String[]{"编号", "账号", "名称", "电话", "地址", "描述"});
    for (Business business : businesses) {
        rows.add(new String[]{
                String.valueOf(business.getId()),
                business.getAccount(),
                business.getName(),
                business.getPhone(),
                business.getAddress(),
                business.getDescription()
        });
    }
    try {
        CsvUtil.writeCsv(Path.of(filePath), rows);
        System.out.println("导出成功: " + filePath);
    } catch (Exception ex) {
        System.out.println("导出失败: " + ex.getMessage());
    }
}
```

### 5. 商家导出食品列表（FoodService）
```java
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
        System.out.println("导出成功: " + filePath);
    } catch (Exception ex) {
        System.out.println("导出失败: " + ex.getMessage());
    }
}
```

## 数据库
- 脚本：`elm_admin.sql`
- 数据库名：`elm_admin`
- 表：`admin` / `business` / `food`

请在 `src/com/neusoft/elm/utils/DBUtil.java` 中修改数据库账号密码。

### 数据库初始化（示例片段）
```sql
CREATE TABLE admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    admin_name VARCHAR(50) NOT NULL UNIQUE,
    admin_password VARCHAR(64) NOT NULL
);

CREATE TABLE business (
    business_id INT PRIMARY KEY AUTO_INCREMENT,
    business_account VARCHAR(50) NOT NULL UNIQUE,
    business_password VARCHAR(64) NOT NULL,
    business_name VARCHAR(100) NOT NULL,
    business_phone VARCHAR(30),
    business_address VARCHAR(255),
    business_desc VARCHAR(255)
);

CREATE TABLE food (
    food_id INT PRIMARY KEY AUTO_INCREMENT,
    business_id INT NOT NULL,
    food_name VARCHAR(100) NOT NULL,
    food_price DECIMAL(10,2) NOT NULL,
    food_desc VARCHAR(255),
    food_status TINYINT NOT NULL DEFAULT 1
);
```

## 运行入口
- 管理员端：`com.neusoft.elm.view.ElmAdminEntry`
- 商家端：`com.neusoft.elm.view.ElmBusinessEntry`
- 统一入口：`com.neusoft.elm.view.Main`

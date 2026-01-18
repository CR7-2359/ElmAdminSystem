package com.neusoft.elm.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/* CSV 写入工具 */
public final class CsvUtil {
    private CsvUtil() {
    }

    /* 使用 UTF-8 + BOM 写入 CSV */
    public static void writeCsv(Path path, List<String[]> rows) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write('\uFEFF'); // UTF-8 BOM，兼容 Excel 打开
            for (String[] row : rows) {
                writer.write(toLine(row)); // 拼接成 CSV 行
                writer.newLine();
            }
        }
    }

    /* 将一行数据转换为 CSV 格式 */
    private static String toLine(String[] row) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(escape(row[i])); // 转义单元格内容
        }
        return sb.toString();
    }

    /* 转义 CSV 单元格内容 */
    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        boolean needQuote = value.contains(",") || value.contains("\"")
                || value.contains("\n") || value.contains("\r");
        String escaped = value.replace("\"", "\"\"");
        return needQuote ? "\"" + escaped + "\"" : escaped;
    }
}

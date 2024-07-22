package util.json2excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

public class Json2Excel {

    public static void main(String[] args) {
        String inputName = "C:\\Users\\xchan\\Downloads\\schemas (17).json";
        String outputName = "C:/Users/xchan/Desktop/output.xlsx";

        try {
            JSONParser parser = new JSONParser();
            Object jsonDataObj = parser.parse(new FileReader(inputName));
            JSONArray jsonData = (JSONArray) jsonDataObj;

            Workbook workbook = new XSSFWorkbook();
            for (Object obj : jsonData) {
                JSONObject schema = (JSONObject) obj;
                Sheet sheet = workbook.createSheet(schema.get("name") + "表(" + schema.get("_id") + ")");
                Row firstRow = sheet.createRow(0);
                firstRow.createCell(0).setCellValue("字段代码");
                firstRow.createCell(1).setCellValue("ck库中类型");
                firstRow.createCell(2).setCellValue("中文名称");
                firstRow.createCell(3).setCellValue("系统包装类型");

                JSONArray properties = (JSONArray) schema.get("properties");
                for (int i = 0; i < properties.size(); i++) {
                    JSONObject property = (JSONObject) properties.get(i);
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue((String) property.get("_id"));
                    row.createCell(1).setCellValue(getTypeMapping((String) property.get("type")));
                    row.createCell(2).setCellValue((String) property.get("name"));
                    row.createCell(3).setCellValue((String) property.get("type"));
                }
            }

            FileOutputStream fileOut = new FileOutputStream(outputName);
            workbook.write(fileOut);
            fileOut.close();

            if (checkUnknown(outputName)) {
                System.out.println("Excel文件中包含Unknown类型");
            } else {
                System.out.println("Excel文件中不包含Unknown类型");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTypeMapping(String type) {
        // 类型映射
        // 实际转换规则需要根据需求进行更改
        // 这里只是简单的示例
        switch (type) {
            case "timestamp":
                return "DateTime('Asia/Shanghai')";
            case "date":
                return "Nullable(DateTime('Asia/Shanghai'))";
            case "duration":
                return "Nullable(Float64)";
            case "ID":
                return "String";
            case "name":
            case "category":
            case "object":
            case "string":
            case "humanName":
            case "image":
            case "file":
                return "Nullable(String)";
            case "tag":
                return "Array(Nullable(String))";
            case "number":
            case "currency":
            case "percentag":
            case "order":
                return "Nullable(Float64)";
            case "int":
                return "Nullable(Int32)";
            case "boolean":
                return "Nullable(Bool)";
            default:
                return "Unknown";
        }

    }

    private static boolean checkUnknown(String outputName) {
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(outputName));
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        if ("Unknown".equals(cell.getStringCellValue())) {
                            return true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
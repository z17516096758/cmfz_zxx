import com.baizhi.Application;
import com.baizhi.entity.Student;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestPoi {
    @Test
    public void testPoi(){
//        Student student = new Student("1","小黑",16,new Date());
//        Student student1 = new Student("1","小红",12,new Date());
//        Student student2 = new Student("1","小绿",12,new Date());
//        Student student3 = new Student("1","小蓝",12,new Date());
//        Student student4 = new Student("1","小紫",12,new Date());
//        Student student5 = new Student("1","小青",12,new Date());
        List<Student> list = new ArrayList<>();
//        list.add(student);
//        list.add(student1);
//        list.add(student2);
//        list.add(student3);
//        list.add(student4);
//        list.add(student5);
        //创建Excel工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表 参数:表名(相当于Excel的sheet1,sheet2/....)
        HSSFSheet sheet = workbook.createSheet("学生信息表");
        //创建标题行
        HSSFRow row = sheet.createRow(0);
        String[] title = {"ID","名字","年龄","生日"};
        for(int i = 0 ; i<title.length ; i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        //创建一个日期格式对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        //创建一个样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //将日期格式放入样式对象中
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));
        //处理数据行
        for(int i = 0 ; i<list.size();i++){
            HSSFRow row1 = sheet.createRow(i + 1);
            Student student6 = list.get(i);
            System.out.println(student6);
            row1.createCell(0).setCellValue(student6.getId());
            row1.createCell(1).setCellValue(student6.getName());
            row1.createCell(2).setCellValue(student6.getAge());
            HSSFCell cell = row1.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(student6.getBir());
        }
        try {
            workbook.write(new FileOutputStream(new File("E://TestPoi.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testPoidImport(){
        //获取要导入的文件
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("E://TestPoi.xls")));
            HSSFSheet sheet = workbook.getSheet("学生信息表");
            for(int i=1;i<sheet.getLastRowNum();i++){
                Student student = new Student();
                //获取行
                HSSFRow row = sheet.getRow(i);
                String id = row.getCell(0).getStringCellValue();
                student.setId(id);
                String name = row.getCell(1).getStringCellValue();
                student.setName(name);
                double numericCellValue = row.getCell(2).getNumericCellValue();
                student.setAge((int)numericCellValue);
                student.setBir(row.getCell(3).getDateCellValue());
                System.out.println(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

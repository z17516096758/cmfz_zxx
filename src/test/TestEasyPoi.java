import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.Application;
import com.baizhi.entity.Student;
import com.baizhi.entity.Teacher;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestEasyPoi {
    @Test
    public void testExport(){
        List<Student> users = new ArrayList<>();
//        users.add(new Student("1", "小黄", 23, new Date()));
//        users.add(new Student("2", "小刘", 26, new Date()));
//        users.add(new Student("3", "小黑", 24, new Date()));
//        users.add(new Student("4", "小张", 18, new Date()));
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班","学生"),Student.class,users);
        try {
            workbook.write(new FileOutputStream("E://easypoi.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void TestEasyPois(){

        //创建学生集
        List<Student> stus = new ArrayList<Student>();
//            stus.add(new Student("1", "小黄", 23,new Date()));
//            stus.add(new Student("2", "小刘", 26,new Date()));
//            stus.add(new Student("3", "小黑", 24,new Date()));
//            stus.add(new Student("4", "小张", 18,new Date()));

        Teacher teacher = new Teacher("1","小王",stus);

        //创建老师集合
        List<Teacher> teachers = new ArrayList<Teacher>();
        teachers.add(teacher);

        //参数：(一级标题，二级标题，表名)，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","计算机","学生"),Teacher.class, teachers);

        try {
            workbook.write(new FileOutputStream(new File("E:/easypois.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testImage(){
        //创建导入对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//表头标题行数,默认0
        params.setHeadRows(3);//表头行数,默认1
        //获取导入数据
        try {
            List<Teacher> list = ExcelImportUtil.importExcel(new FileInputStream("E:/easyPois.xls"),Teacher.class,params);
            list.forEach(teacher -> System.out.println(teacher));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

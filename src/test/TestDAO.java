import com.baizhi.Application;
import com.baizhi.dao.TrendDao;
import com.baizhi.entity.Trend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestDAO {
    @Autowired
    private TrendDao trendDao;
    @Test
    public void show(){
        List<Trend> boys = trendDao.selectAllBySex("男");
        boys.forEach(user -> System.out.println(user));
    }
    @Test
    public void test(){
        List<Trend> boys = trendDao.selectAllBySex("男");
        List<Trend> girls = trendDao.selectAllBySex("女");
        List<Integer> boy = new ArrayList<>();
        List<Integer> girl = new ArrayList<>();
        for(int i =0 ; i<12 ; i++){
            boy.add(0);
            girl.add(0);
        }
        String[] month = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < boys.size(); j++) {
                if(boys.get(j).getMonth().equals(month[i])){
                    boy.remove(i);
                    boy.add(i,boys.get(j).getCount());
                }
            }
            for (int j = 0; j < girls.size(); j++) {
                if(girls.get(j).getMonth().equals(month[i])){
                    girl.remove(i);
                    girl.add(i,girls.get(j).getCount());
                }
            }
        }
        System.out.println(girl);
        System.out.println(boy);
    }
}

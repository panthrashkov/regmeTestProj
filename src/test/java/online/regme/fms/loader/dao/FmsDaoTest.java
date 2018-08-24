package online.regme.fms.loader.dao;

import online.regme.fms.loader.TestConfig;
import online.regme.fms.loader.model.Fms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@Transactional
@DirtiesContext
public class FmsDaoTest {

    @Autowired
    private FmsDao fmsDao;


    @Test
    public void persist(){
        Fms fms = new Fms("code", "name", 1);
        fmsDao.persist(fms);
    }

}
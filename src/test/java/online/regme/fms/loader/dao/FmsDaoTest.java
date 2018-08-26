package online.regme.fms.loader.dao;

import online.regme.fms.loader.TestConfig;
import online.regme.fms.loader.model.Fms;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@Transactional
@DirtiesContext
@TestPropertySource(locations = "classpath:application-test.properties")
public class FmsDaoTest {

    private final String CODE  ="code";
    private final String NAME = "name";
    private final Integer RECORD_VERSION = 1;
    @Autowired
    private FmsDao fmsDao;

    @Before
    public void  setUp(){
        Fms fms = new Fms(CODE, NAME, RECORD_VERSION);
        fmsDao.persist(fms);
    }

    @Test
    public void getByCode(){
        Optional<Fms> fms = fmsDao.getByCode(CODE);
        assertTrue(fms.isPresent());
        assertEquals(NAME, fms.get().getName());
        assertEquals(RECORD_VERSION, fms.get().getRecordVersion());
    }



}
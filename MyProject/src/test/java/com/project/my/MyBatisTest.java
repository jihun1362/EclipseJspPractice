package com.project.my;
 
import javax.inject.Inject;
 
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
 
public class MyBatisTest {
    
      @Inject
       private SqlSessionFactory sqlFactory;
 
 
       @Test
        public void testFactory() {
            System.out.println(">>>>>>>>> sql session factory check Start <<<<<<<<<");
            System.out.println(sqlFactory);
            System.out.println(">>>>>>>>>sql session factory check End <<<<<<<<<");
        }
 
 
       @Test
        public void testSession() throws Exception {
            
            try(SqlSession session = sqlFactory.openSession()) {
                
                System.out.println(">>>>>>>>> mybatis sql session test Start <<<<<<<<<");
                System.out.println(session);
                System.out.println(">>>>>>>>> mybatis sql session test End <<<<<<<<<");
                
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
 
}
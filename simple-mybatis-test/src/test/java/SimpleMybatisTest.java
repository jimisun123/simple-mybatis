import com.jimisun.entity.User;
import com.jimisun.mapper.UserMapper;
import com.jimisun.simplemybatis.io.Resources;
import com.jimisun.simplemybatis.sqlSession.SqlSession;
import com.jimisun.simplemybatis.sqlSession.SqlSessionFactory;
import com.jimisun.simplemybatis.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * SimpleMybatis测试类
 *
 * @author jimisun
 * @create 2022-03-26 10:24 AM
 **/
public class SimpleMybatisTest {

    /**
     * 传统dao方式开发测试
     * @param args
     */
    //public static void main(String[] args) {
    //    //第一步 ： 将具体工程到xml配置文件地址加载为字节输入流
    //    InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapperConfig.xml");
    //    //第二步 ： 将字节输入流加载为具体的容器对象【configuration,mappedStatement】
    //    //第三步 ： 创建SqlSessionFactory用于生成sqlSession
    //    SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(resourceAsStream);
    //    SqlSession sqlSession = sqlSessionFactory.openSqlSession();
    //    //第四步 ： SqlSession将具体的Sql实现委托给Executor执行
    //    User paramUser = new User();
    //    paramUser.setId("1");
    //    User user = sqlSession.selectOne("user.selectOne", paramUser);
    //
    //    System.out.println(user.toString());
    //
    //    System.out.println("end.......");
    //}

    /**
     * mapper代理测试
     * @param args
     */
    //public static void main(String[] args) {
    //    //第一步 ： 将具体工程到xml配置文件地址加载为字节输入流
    //    InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapperConfig.xml");
    //    //第二步 ： 将字节输入流加载为具体的容器对象【configuration,mappedStatement】
    //    //第三步 ： 创建SqlSessionFactory用于生成sqlSession
    //    SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(resourceAsStream);
    //    SqlSession sqlSession = sqlSessionFactory.openSqlSession();
    //    //第四步 ： SqlSession将具体的Sql实现委托给Executor执行
    //    User paramUser = new User();
    //    paramUser.setId("1");
    //    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    //    User user = userMapper.selectOne(paramUser);
    //
    //    System.out.println(user.toString());
    //
    //    System.out.println("end.......");
    //}


    /**
     * 测试更新
     *
     * @param args
     */
    public static void main(String[] args) {
        //第一步 ： 将具体工程到xml配置文件地址加载为字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapperConfig.xml");
        //第二步 ： 将字节输入流加载为具体的容器对象【configuration,mappedStatement】
        //第三步 ： 创建SqlSessionFactory用于生成sqlSession
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        //第四步 ： SqlSession将具体的Sql实现委托给Executor执行
        User paramUser = new User();
        paramUser.setId("1");
        paramUser.setUsername("李四");
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int i = userMapper.updateUserByUserId(paramUser);
        System.out.println(i);


        System.out.println("end.......");
    }
}

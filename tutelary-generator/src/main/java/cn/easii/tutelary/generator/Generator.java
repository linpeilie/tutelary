package cn.easii.tutelary.generator;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import java.io.File;

public class Generator {

    /**
     * 数据库url
     */
    private static final String MYSQL_URL = "192.168.6.166:10001";

    /**
     * 数据库 schema
     */
    private static final String MYSQL_SCHEMA = "tutelary";

    /**
     * 数据库用户名
     */
    private static final String MYSQL_USERNAME = "root";

    /**
     * 数据库密码
     */
    private static final String MYSQL_PASSWORD = "bde80566";

    /**
     * TODO:作者
     */
    private static final String AUTHOR = "linpeilie";

    /**
     * TODO:生成的具体表名
     */
    private static final String TABLE_NAME = "role";

    /**
     * 表名前缀
     */
    private static final String TABLE_PREFIX = "";

    private static final String PACKAGE_PARENT = "cn.easii.tutelary";

    public static void main(String[] args) {
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://" + MYSQL_URL
                                                                         + "/"
                                                                         + MYSQL_SCHEMA
                                                                         + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior" +
                                                                         "=convertToNull", MYSQL_USERNAME, MYSQL_PASSWORD).build();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig.Builder().enableSwagger()
            // 文件覆盖的话，把下面注释放开
            //            .fileOverride()
            .commentDate(DateUtil.now())
            .disableOpenDir()
            .author(AUTHOR).build();

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
            .addInclude(TABLE_NAME)
            .addTablePrefix(TABLE_PREFIX)
            .entityBuilder()
            .formatFileName("%sEntity")
            .enableLombok()
            .addIgnoreColumns("update_time", "create_time", "update_user", "create_user", "deleted")
            .disableSerialVersionUID()
            .build();

        PackageConfig packageConfig = new PackageConfig.Builder()
            .parent(PACKAGE_PARENT)
            .build();

        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig);
        autoGenerator.strategy(strategyConfig).global(globalConfig).packageInfo(packageConfig);
        autoGenerator.execute(new TutelaryTemplateEngine(false));

    }

}

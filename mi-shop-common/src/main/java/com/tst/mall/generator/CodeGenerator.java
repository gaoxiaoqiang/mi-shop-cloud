package com.tst.mall.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CodeGenerator {

    public static void main(String[] args) {
        List<String> tables = new ArrayList<>();

        tables.add("tb_mall_user");

        generate("jdbc:mysql://localhost:3306/mi_shop?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai",
                "root", "12345678", tables,"mi-shop-user");
    }

    /**
     * 生成代码
     * @param dbUrl 数据库连接
     * @param dbUser 用户名
     * @param dbPwd  密码
     * @param tables 需要生成的表名列表（可忽略大小写和前缀）
     */
    public static void generate(String dbUrl, String dbUser, String dbPwd, List<String> tables,String modelName) {
        // 获取主项目路径
        String mainProjectPath = System.getProperty("user.dir");

        mainProjectPath=mainProjectPath+ File.separator+modelName;
        
        if (mainProjectPath.endsWith("code-generator")) {
            mainProjectPath = mainProjectPath.substring(0, mainProjectPath.length() - "code-generator".length());
            System.out.println("Main project path: " + mainProjectPath);
        }
        String finalMainProjectPath = mainProjectPath;

        System.out.println("Current project path: " + mainProjectPath);


        FastAutoGenerator.create(dbUrl, dbUser, dbPwd)
                .globalConfig(builder -> {
                    builder.author("gxq")
                            .enableSpringdoc()
                            .outputDir(finalMainProjectPath + "/src/main/java")
                            .build();
                })
                .packageConfig(builder -> {
                    builder.parent("com.tst.mall")
                            .entity("model.entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            .xml("mapper.xml")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(
                                    OutputFile.xml,
                                    finalMainProjectPath + "/src/main/resources/mapper"
                            ));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            .addTablePrefix("tb_mall")
                            .entityBuilder().enableFileOverride()
                            .naming(NamingStrategy.underline_to_camel)
                            .enableLombok()
                            .enableRemoveIsPrefix()
                            .controllerBuilder()
                            .enableRestStyle()
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
} 
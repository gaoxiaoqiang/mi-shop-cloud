package com.tst.mall.config.mybatis;

import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;
import com.baomidou.mybatisplus.extension.ddl.IDdl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MybatisPlusConfig {

    //fix   Run Application occur Exception ddlApplicationRunner issue
    @Bean
    public DdlApplicationRunner ddlApplicationRunner(@Autowired(required = false) List<IDdl> ddlList) {
        return new DdlApplicationRunner(ddlList);
    }
}

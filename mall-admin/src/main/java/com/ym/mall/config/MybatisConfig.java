package com.ym.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis的配置类
 * @author matao
 * @create 2019-08-14 16:48
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.ym.mall.mapper","com.ym.mall.dao"})
public class MybatisConfig {
}

package com.devadmin.vicky.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FormatConfig {

    /**
     * Properties bean that keep mapping between issueType and their icons in slack
     */
    @Bean("issueTypeIdToIconsMapping")
    public PropertiesFactoryBean propertiesFactoryBean(){
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("issueTypeIdToIcons.properties"));
        return propertiesFactoryBean;
    }
}

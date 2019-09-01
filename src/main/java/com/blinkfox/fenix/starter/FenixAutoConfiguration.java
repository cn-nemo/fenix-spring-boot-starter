package com.blinkfox.fenix.starter;

import com.blinkfox.fenix.config.FenixConfig;
import com.blinkfox.fenix.config.FenixConfigManager;
import com.blinkfox.fenix.consts.Const;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

/**
 * Fenix 自动配置类.
 *
 * @author blinkfox on 2019-08-14.
 * @see FenixProperties
 */
@Configuration
@EnableConfigurationProperties(FenixProperties.class)
public class FenixAutoConfiguration {

    /**
     * {@link FenixProperties} 属性配置类的实例.
     */
    private FenixProperties properties;

    /**
     * 构造方法.
     *
     * @param properties {@link FenixProperties} 的属性配置对象
     */
    @Autowired
    public FenixAutoConfiguration(FenixProperties properties) {
        this.properties = properties;
        this.doConfig();
    }

    /**
     * 根据 properties 中的配置项做 Fenix 的配置.
     */
    private void doConfig() {
        // 获取配置值.
        Boolean printBanner = this.properties.getPrintBanner();
        Boolean printSql = this.properties.getPrintSql();
        Boolean showJpaSql = this.properties.getShowJpaSql();
        List<String> xmlLocations = this.properties.getXmlLocations();
        List<String> handlerLocations = this.properties.getHandlerLocations();

        // 配置常规信息、 xml 和 handler 的扫描路径.
        // 如果未配置 printSql，则使用 jpa 中 show-sql 的配置值，否则使用 printSql 的值.
        FenixConfigManager.getInstance().initLoad(new FenixConfig()
                .setPrintBanner(printBanner == null || Boolean.TRUE.equals(printBanner))
                .setPrintSqlInfo(printSql == null ? Boolean.TRUE.equals(showJpaSql) : Boolean.TRUE.equals(printSql))
                .setXmlLocations(CollectionUtils.isEmpty(xmlLocations) ? null : String.join(Const.COMMA, xmlLocations))
                .setHandlerLocations(CollectionUtils.isEmpty(handlerLocations) ? null
                        : String.join(Const.COMMA, handlerLocations)));
    }

}

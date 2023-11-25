package cn.easii.tutelary.config;

import cn.easii.tutelary.installer.DefaultTableInstallerInvoker;
import cn.easii.tutelary.installer.TableInstaller;
import cn.easii.tutelary.installer.TableInstallerInvoker;
import javax.sql.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnProperty(prefix = "storage.initialize", name = "enabled", havingValue = "true", matchIfMissing = true)
public class TableInstallerConfiguration implements BeanPostProcessor, Ordered, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Bean
    @ConditionalOnMissingBean
    public TableInstallerInvoker installerInvoker(DataSource dataSource, TableInstaller tableInstaller) {
        return new DefaultTableInstallerInvoker(dataSource, tableInstaller);
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            this.beanFactory.getBean(TableInstallerInvoker.class);
        }
        return bean;
    }
}

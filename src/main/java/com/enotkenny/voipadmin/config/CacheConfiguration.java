package com.enotkenny.voipadmin.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.enotkenny.voipadmin.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.enotkenny.voipadmin.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.enotkenny.voipadmin.domain.User.class.getName());
            createCache(cm, com.enotkenny.voipadmin.domain.Authority.class.getName());
            createCache(cm, com.enotkenny.voipadmin.domain.User.class.getName() + ".authorities");
            createCache(cm, com.enotkenny.voipadmin.domain.Device.class.getName());
            createCache(cm, com.enotkenny.voipadmin.domain.Device.class.getName() + ".sipAccounts");
            createCache(cm, com.enotkenny.voipadmin.domain.Device.class.getName() + ".deviceSettings");
            createCache(cm, com.enotkenny.voipadmin.domain.DeviceModel.class.getName());
            createCache(cm, com.enotkenny.voipadmin.domain.DeviceModel.class.getName() + ".devices");
            createCache(cm, com.enotkenny.voipadmin.domain.DeviceModel.class.getName() + ".additionalOptions");
            createCache(cm, com.enotkenny.voipadmin.domain.DeviceType.class.getName());
            createCache(cm, com.enotkenny.voipadmin.domain.DeviceType.class.getName() + ".deviceModels");
            createCache(cm, com.enotkenny.voipadmin.domain.SipAccount.class.getName());
            createCache(cm, com.enotkenny.voipadmin.domain.PbxAccount.class.getName());
            createCache(cm, com.enotkenny.voipadmin.domain.ResponsiblePerson.class.getName());
            createCache(cm, com.enotkenny.voipadmin.domain.ResponsiblePerson.class.getName() + ".devices");
            createCache(cm, com.enotkenny.voipadmin.domain.AdditionalOption.class.getName());
            createCache(cm, com.enotkenny.voipadmin.domain.AdditionalOption.class.getName() + ".deviceSettings");
            createCache(cm, com.enotkenny.voipadmin.domain.AdditionalOption.class.getName() + ".deviceModels");
            createCache(cm, com.enotkenny.voipadmin.domain.DeviceSetting.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}

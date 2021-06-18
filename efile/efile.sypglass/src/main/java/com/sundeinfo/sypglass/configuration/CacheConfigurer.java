package com.sundeinfo.sypglass.configuration;

import com.sundeinfo.sypglass.component.CacheKeyGenerator;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

//@Configuration
//@EnableCaching
public class CacheConfigurer  {
    @Bean
    public SimpleCacheManager simpleCacheManager(){
        SimpleCacheManager s = new SimpleCacheManager();
        s.setCaches(Collections.singletonList(new ConcurrentMapCache("Parent")));
        s.setCaches(Collections.singletonList(new ConcurrentMapCache("Child")));
        return s;
    }
    @Bean
    public KeyGenerator cacheKeyGenerator(){//缓存key生成
        CacheKeyGenerator cacheKeyGenerator = new CacheKeyGenerator();
        return cacheKeyGenerator;

    }


}


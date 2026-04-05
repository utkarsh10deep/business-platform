package com.flexiwms.business.apis.test;

import com.flexiwms.platform.api.PlatformAPIServer;
import com.flexiwms.platform.api.config.PlatformAPIConfig;
import com.flexiwms.platform.api.config.PlatformAPISettings;
import io.dropwizard.setup.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlexiWMSTestAPIService extends PlatformAPIServer {

    private static final Logger log = LogManager.getLogger(FlexiWMSTestAPIService.class);
    private final String config;

    public FlexiWMSTestAPIService(String config) {
        super();
        this.config = config;
    }

    @Override
    public void configure(PlatformAPIConfig platformAPIConfig, Environment environment) {
        environment.jersey().register(new TestController());
    }

    @Override
    public void init() {
        log.info("Initializing FlexiWMS Test API Service");
        final var instance = PlatformAPISettings.getInstance();
        instance.setHealthCheckEnabled(false);
        instance.setApiHealthFun(() -> Boolean.FALSE);

        // Customize header filtering for properties and traceable headers
        //Predicate<String> propertiesHeaderFilter = header -> header.startsWith("X-FlexiWMS-") || header.equalsIgnoreCase("Authorization");
        //Predicate<String> traceableHeadersFilter = header -> header.startsWith("X-FlexiWMS-") || header.equalsIgnoreCase("Authorization");
        //PlatformAPISettings.getInstance().setPropertiesHeaders(propertiesHeaderFilter);
        //PlatformAPISettings.getInstance().setTraceableHeaders(traceableHeadersFilter);

    }

    @Override
    public String getAPIConfig() {
        return config;
    }
}

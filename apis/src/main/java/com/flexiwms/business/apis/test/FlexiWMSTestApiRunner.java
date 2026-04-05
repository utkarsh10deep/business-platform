package com.flexiwms.business.apis.test;

import com.flexiwms.common.BaseServiceRunner;
import com.flexiwms.platform.api.config.PlatformAPISettings;
import com.flexiwms.common.tracing.APITraceHeaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FlexiWMSTestApiRunner extends BaseServiceRunner {
    private static final Logger log = LogManager.getLogger(FlexiWMSTestApiRunner.class);

    public static void main(String[] args) throws Exception {
        initLookupAndObjectPoolSettings();
        // prefer config contents loaded by getConfig(); if it returns a filename, pass that along
        String config = getConfig();
        var service = new FlexiWMSTestAPIService(config);
        PlatformAPISettings.getInstance().setTraceHeaders(APITraceHeaders.getInstance());
        service.startServer();
    }

    private static String getConfig() {
        String resourceName = "testApiConfig.txt";

        // Try classpath then filesystem in a compact way
        return readClasspathResource(resourceName)
                .or(() -> readFileResource(resourceName))
                .orElseGet(() -> {
                    log.warn("Configuration file '{}' not found on classpath or filesystem; falling back to default filename.", resourceName);
                    return "flexiwms-test-api-config.yaml";
                });
    }

    private static Optional<String> readClasspathResource(String name) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name)) {
            if (is != null) {
                log.info("Loaded config from classpath resource: {}", name);
                return Optional.of(new String(is.readAllBytes(), StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            log.warn("Failed to read classpath resource {}: {}", name, e.getMessage());
        }
        return Optional.empty();
    }

    private static Optional<String> readFileResource(String name) {
        try {
            Path p = Paths.get(name);
            if (Files.exists(p)) {
                log.info("Loaded config from filesystem: {}", p.toAbsolutePath());
                return Optional.of(Files.readString(p, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            log.warn("Failed to read file {} from filesystem: {}", name, e.getMessage());
        }
        return Optional.empty();
    }
}

package io.jshift.kit.build.api.auth.handler;

import java.util.Properties;
import java.util.function.Function;

import io.jshift.kit.build.api.auth.RegistryAuth;
import io.jshift.kit.build.api.auth.RegistryAuthConfig;
import io.jshift.kit.build.api.auth.RegistryAuthHandler;
import io.jshift.kit.common.KitLogger;


/**
 * @author roland
 * @since 21.10.18
 */
public class SystemPropertyRegistryAuthHandler implements RegistryAuthHandler {

    private final RegistryAuthConfig registryAuthConfig;
    private final KitLogger log;

    public SystemPropertyRegistryAuthHandler(RegistryAuthConfig registryAuthConfig, KitLogger log) {
        this.log = log;
        this.registryAuthConfig = registryAuthConfig;
    }

    @Override
    public String getId() {
        return "sysprops";
    }

    @Override
    public RegistryAuth create(RegistryAuthConfig.Kind kind, String user, String registry, Function<String, String> decryptor) {
        Properties props = System.getProperties();
        String username = registryAuthConfig.extractFromProperties(props, kind, RegistryAuth.USERNAME);
        String password = registryAuthConfig.extractFromProperties(props, kind, RegistryAuth.PASSWORD);

        if (username == null) {
            return null;
        }
        if (password == null) {
            throw new IllegalArgumentException("No password provided for username " + username);
        }

        log.debug("AuthConfig: credentials from system properties");
        return new RegistryAuth.Builder()
            .username(username)
            .password(password, decryptor)
            .email(registryAuthConfig.extractFromProperties(props, kind, RegistryAuth.EMAIL))
            .auth(registryAuthConfig.extractFromProperties(props, kind, RegistryAuth.AUTH))
            .build();
    }
}

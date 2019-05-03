/**
 * Copyright 2016 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package io.jshift.maven.enricher.api;

import java.util.Map;

import io.jshift.kit.common.Configs;
import io.jshift.kit.config.resource.ProcessorConfig;
import io.jshift.maven.enricher.api.model.Configuration;

/**
 * @author roland
 * @since 24/05/16
 */
public class EnricherConfig {

    private static final String ENRICHER_PROP_PREFIX = "fabric8.enricher";

    private final String name;
    private final Configuration configuration;

    public EnricherConfig(String name, Configuration configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    /**
     * Get a configuration value
     *
     * @param key key to lookup.
     * @return the configuration value
     */
    public String get(Configs.Key key) {
        return get(key, key.def());
    }

    /**
     * Get the raw, untyped configuration or an empty map
     * @return raw configuration.
     */
    public Map<String, String> getRawConfig() {
        return configuration.getProcessorConfig().orElse(ProcessorConfig.EMPTY).getConfigMap(name);
    }

    /**
     * Get a config value with a default. If no value is given, as a last resort, project properties are looked up.
     *
     * @param key key part to lookup. The whole key is build up from <code>prefix + "." + key</code>. If key is null,
     *            then only the prefix is used for the lookup (this is suitable for enrichers having only one config option)
     * @param defaultVal the default value to use when the no config is set
     * @return the value looked up or the default value.
     */
    public String get(Configs.Key key, String defaultVal) {
        String val = configuration.getProcessorConfig().orElse(ProcessorConfig.EMPTY).getConfig(name, key.name());

        if (val == null) {
            String fullKey = ENRICHER_PROP_PREFIX + "." + name + "." + key;
            val = configuration.getPropertyWithSystemOverride(fullKey);
        }
        return val != null ? val : defaultVal;
    }

}
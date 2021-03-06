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
package io.jshift.maven.generator.api;

import io.jshift.kit.common.Configs;
import io.jshift.kit.config.resource.ProcessorConfig;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;


/**
 */
public class GeneratorConfig {

    private static final String GENERATOR_PROP_PREFIX = "jshift.generator";

    private final String name;
    private final ProcessorConfig config;
    private final Properties properties;

    public GeneratorConfig(Properties properties, String name, ProcessorConfig config) {
        this.config =  config;
        this.name = name;
        this.properties = properties;
    }

    /**
     * Get a configuration value
     *
     * @param key key to lookup. If it implements also DefaultValueProvider then use this for a default value
     * @return the defa
     */
    public String get(Configs.Key key) {
        return get(key, key.def());
    }

    /**
     * Get a config value with a default
     * @param key key part to lookup. The whole key is build up from <code>prefix + "." + key</code>. If key is null,
     *            then only the prefix is used for the lookup (this is suitable for enrichers having only one config option)
     * @param defaultVal the default value to use when the no config is set
     * @return the value looked up or the default value.
     */
    public String get(Configs.Key key, String defaultVal) {
        String keyVal = key != null ? key.name() : "";
        String val = config != null ? config.getConfig(name, key.name()) : null;
        if (val == null) {
            String fullKey = GENERATOR_PROP_PREFIX + "." + name + "." + key;
            val = Configs.getSystemPropertyWithMavenPropertyAsFallback(properties, fullKey);
        }
        return val != null ? val : defaultVal;
    }

}

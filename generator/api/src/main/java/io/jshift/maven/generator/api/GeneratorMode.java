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

/**
 * Modes which influence how generators are creating image configurations
 *
 * @author roland
 * @since 03.10.18
 */
public enum GeneratorMode {

    /**
     * Regular build mode. Image will be created which are used in production
     */
    BUILD,

    /**
     * Special generation mode used for watching
     */
    WATCH,

    /**
     * Generate image suitable for remote debugging
     */
    DEBUG
}

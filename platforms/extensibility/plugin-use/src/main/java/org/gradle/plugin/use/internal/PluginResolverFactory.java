/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.plugin.use.internal;

import org.gradle.api.internal.DocumentationRegistry;
import org.gradle.api.internal.initialization.ScriptHandlerInternal;
import org.gradle.api.internal.plugins.PluginRegistry;
import org.gradle.internal.service.scopes.Scopes;
import org.gradle.internal.service.scopes.ServiceScope;
import org.gradle.plugin.use.resolve.internal.CompositePluginResolver;
import org.gradle.plugin.use.resolve.internal.CorePluginResolver;
import org.gradle.plugin.use.resolve.internal.NoopPluginResolver;
import org.gradle.plugin.use.resolve.internal.PluginResolver;
import org.gradle.plugin.use.resolve.internal.PluginResolverContributor;
import org.gradle.plugin.use.resolve.internal.PluginArtifactRepositories;
import org.gradle.plugin.use.resolve.service.internal.ClientInjectedClasspathPluginResolver;
import org.gradle.plugin.use.resolve.service.internal.DefaultInjectedClasspathPluginResolver;

import java.util.LinkedList;
import java.util.List;

@ServiceScope(Scopes.Build.class)
public class PluginResolverFactory {

    private final PluginRegistry pluginRegistry;
    private final DocumentationRegistry documentationRegistry;
    private final ClientInjectedClasspathPluginResolver injectedClasspathPluginResolver;
    private final List<PluginResolverContributor> pluginResolverContributors;

    public PluginResolverFactory(
        PluginRegistry pluginRegistry,
        DocumentationRegistry documentationRegistry,
        ClientInjectedClasspathPluginResolver injectedClasspathPluginResolver,
        List<PluginResolverContributor> pluginResolverContributors
    ) {
        this.pluginRegistry = pluginRegistry;
        this.documentationRegistry = documentationRegistry;
        this.injectedClasspathPluginResolver = injectedClasspathPluginResolver;
        this.pluginResolverContributors = pluginResolverContributors;
    }

    public PluginResolver create(PluginArtifactRepositories pluginResolveContext, ScriptHandlerInternal scriptHandler) {
        return new CompositePluginResolver(createDefaultResolvers(pluginResolveContext, scriptHandler));
    }

    private List<PluginResolver> createDefaultResolvers(PluginArtifactRepositories pluginResolveContext, ScriptHandlerInternal scriptHandler) {
        List<PluginResolver> resolvers = new LinkedList<>();
        addDefaultResolvers(pluginResolveContext, scriptHandler, resolvers);
        return resolvers;
    }

    /**
     * Returns the default PluginResolvers used by Gradle.
     * <p>
     * The plugins will be searched in a chain from the first to the last until a plugin is found.
     * So, order matters.
     * <p>
     * <ol>
     *     <li>{@link NoopPluginResolver} - Only used in tests.</li>
     *     <li>{@link CorePluginResolver} - distributed with Gradle</li>
     *     <li>{@link DefaultInjectedClasspathPluginResolver} - from a TestKit test's ClassPath</li>
     *     <li>Resolvers contributed by this distribution - plugins coming from included builds</li>
     *     <li>Resolvers based on the entries of the `pluginRepositories` block</li>
     *     <li>{@link org.gradle.plugin.use.resolve.internal.ArtifactRepositoriesPluginResolver} - from Gradle Plugin Portal if no `pluginRepositories` were defined</li>
     * </ol>
     * <p>
     * This order is optimized for both performance and to allow resolvers earlier in the order
     * to mask plugins which would have been found later in the order.
     */
    private void addDefaultResolvers(PluginArtifactRepositories pluginResolveContext, ScriptHandlerInternal scriptHandler, List<PluginResolver> resolvers) {
        resolvers.add(new NoopPluginResolver(pluginRegistry));
        resolvers.add(new CorePluginResolver(documentationRegistry, pluginRegistry));

        injectedClasspathPluginResolver.collectResolversInto(scriptHandler, resolvers);

        pluginResolverContributors.forEach(contributor -> contributor.collectResolversInto(resolvers));
        resolvers.add(pluginResolveContext.getPluginResolver());
    }
}

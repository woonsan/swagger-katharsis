/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.woonsan.swagger.katharsis.core;

import io.katharsis.locator.SampleJsonServiceLocator;
import io.katharsis.resource.ResourceFieldNameTransformer;
import io.katharsis.resource.ResourceInformation;
import io.katharsis.resource.ResourceInformationBuilder;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.registry.RegistryEntry;
import io.katharsis.resource.registry.ResourceRegistry;
import io.katharsis.resource.registry.ResourceRegistryBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;

public class ResourceRegistryTest {

    private static Log log = LogFactory.getLog(ResourceRegistryTest.class);

    private static final String RESOURCE_SEARCH_PACKAGE = "com.github.woonsan.swagger.katharsis.core.resource";

    private static final String RESOURCE_DEFAULT_DOMAIN = "http://localhost:8080/api/v1";

    private static final ResourceFieldNameTransformer RESOURCE_FIELD_NAME_TRANSFORMER = new ResourceFieldNameTransformer();

    private ResourceRegistry resourceRegistry;

    private Set<Class<?>> jsonApiResources;

    @Before
    public void before() throws Exception {
        ResourceRegistryBuilder registryBuilder = new ResourceRegistryBuilder(
                                                                              new SampleJsonServiceLocator(),
                                                                              new ResourceInformationBuilder());
        resourceRegistry = registryBuilder.build(RESOURCE_SEARCH_PACKAGE, RESOURCE_DEFAULT_DOMAIN);


        Reflections reflections = new Reflections(RESOURCE_SEARCH_PACKAGE);
        jsonApiResources = reflections.getTypesAnnotatedWith(JsonApiResource.class);
    }

    @Test
    public void testResourceRegistry() throws Exception {
        log.info("serviceUrl: " + resourceRegistry.getServiceUrl());

        for (Class<?> jsonApiResource : jsonApiResources) {
            StringWriter sw = new StringWriter();
            PrintWriter out = new PrintWriter(sw);

            RegistryEntry entry = resourceRegistry.getEntry(jsonApiResource);
            ResourceInformation resourceInfo = entry.getResourceInformation();

            String resourceType = resourceRegistry.getResourceType(jsonApiResource);
            String resourceUrl = resourceRegistry.getResourceUrl(jsonApiResource);

            out.println("resourceType: " + resourceType);

            out.println("resourceURLs:");
            out.println("  - " + resourceUrl);

            out.println("    params:");
            out.println("      include: ");
            out.println("      filter: ");

            out.println("  - " + resourceUrl + "/{id}");

            out.println("    params:");
            out.println("      include: ");
            out.println("      filter: ");

            Set<Field> attributeFields = resourceInfo.getAttributeFields();

            if (attributeFields != null && !attributeFields.isEmpty()) {
                out.println("attributes:");

                for (Field attributeField : attributeFields) {
                    String attributeName = RESOURCE_FIELD_NAME_TRANSFORMER.getName(attributeField);
                    out.println("  " + attributeName + ": " + attributeField.getType().getSimpleName());
                }
            }

            Set<Field> relationshipFields = resourceInfo.getRelationshipFields();

            if (relationshipFields != null && !relationshipFields.isEmpty()) {
                out.println("relationships:");

                for (Field relationshipField : relationshipFields) {
                    String relationshipName = RESOURCE_FIELD_NAME_TRANSFORMER.getName(relationshipField);

                    out.println("  " + relationshipName + ":");
                    out.println("    links:");
                    out.println("      self: " + resourceUrl + "/{id}" + "/relationships/" + relationshipName);
                    out.println("      related: " + resourceUrl + "/{id}" + "/" + relationshipName);
                }
            }

            log.info("\n---\n" + sw + "...\n");
        }
    }
}

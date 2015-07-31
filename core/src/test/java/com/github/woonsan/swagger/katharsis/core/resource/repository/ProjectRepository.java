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
package com.github.woonsan.swagger.katharsis.core.resource.repository;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

import com.github.woonsan.swagger.katharsis.core.resource.model.Project;

public class ProjectRepository implements ResourceRepository<Project, Long> {
    @Override
    public <S extends Project> S save(S entity) {
        return null;
    }

    @Override
    public Project findOne(Long aLong, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Project> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Project> findAll(Iterable<Long> projectIds, RequestParams requestParams) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}

package com.tutelary;

import com.squareup.javapoet.ClassName;

public class AutoPersistenceDescriptor {

    private ClassName domainClass;

    private ClassName queryDomainClass;

    private ClassName entityClass;

    public String mapperName() {
        return domainClass.simpleName() + "Mapper";
    }

    public String daoName() {
        return domainClass.simpleName() + "DAO";
    }

    public String repositoryName() {
        return domainClass.simpleName() + "Repository";
    }

    public String repositoryImplName() {
        return domainClass.simpleName() + "RepositoryImpl";
    }

    public ClassName getDomainClass() {
        return domainClass;
    }

    public void setDomainClass(final ClassName domainClass) {
        this.domainClass = domainClass;
    }

    public ClassName getQueryDomainClass() {
        return queryDomainClass;
    }

    public void setQueryDomainClass(final ClassName queryDomainClass) {
        this.queryDomainClass = queryDomainClass;
    }

    public ClassName getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(final ClassName entityClass) {
        this.entityClass = entityClass;
    }
}

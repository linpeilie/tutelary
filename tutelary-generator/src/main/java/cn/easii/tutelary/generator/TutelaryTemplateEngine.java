package cn.easii.tutelary.generator;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.io.File;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;

public class TutelaryTemplateEngine extends FreemarkerTemplateEngine {

    /**
     * 根模块
     */
    private static final String ROOT_MODULE = "tutelary-server";

    interface TutelaryModule {
        String api = "server-api";
        String core = "server-core";
        String domain = "server-domain";
        String storage = "server-storage";

        interface Storage {
            String storageApi = "server-storage-api";
            String storageRdbCore = "server-storage-rdb-core";
        }
    }

    private final boolean fileOverride;

    public TutelaryTemplateEngine(boolean fileOverride) {
        this.fileOverride = fileOverride;
    }

    private final Template DOMAIN = Template.builder()
        .modules(ListUtil.of(TutelaryModule.domain))
        .classNameKey("domainClassName")
        .classNameSuffix("")
        .packageNameKey("domainClassPackage")
        .packageNameSuffix("bean.domain")
        .pathKey("domainPath")
        .pathSuffix("bean" + File.separator + "domain")
        .templateName("/templates/domain.java")
        .build();

    private final Template QUERY_DOMAIN = Template.builder()
        .modules(ListUtil.of(TutelaryModule.domain))
        .classNameKey("queryDomainName")
        .classNameSuffix("Query")
        .packageNameKey("queryDomainPackage")
        .packageNameSuffix("bean.domain.query")
        .pathKey("queryDomainPath")
        .pathSuffix("bean" + File.separator + "domain" + File.separator + "query")
        .templateName("/templates/queryDomain.java")
        .build();

    private final Template RESPONSE = Template.builder()
        .modules(ListUtil.of(TutelaryModule.api))
        .classNameKey("responseName")
        .classNameSuffix("Response")
        .packageNameKey("responsePackage")
        .packageNameSuffix("bean.resp")
        .pathKey("responsePath")
        .pathSuffix("bean" + File.separator + "resp")
        .templateName("/templates/response.java")
        .build();

    private final Template DAO = Template.builder()
        .modules(ListUtil.of(TutelaryModule.storage, TutelaryModule.Storage.storageApi))
        .classNameKey("daoClassName")
        .classNameSuffix("DAO")
        .packageNameKey("daoPackage")
        .packageNameSuffix("dao")
        .pathKey("daoPath")
        .pathSuffix("dao")
        .templateName("/templates/dao.java")
        .build();

    private final Template REPOSITORY = Template.builder()
        .modules(ListUtil.of(TutelaryModule.storage, TutelaryModule.Storage.storageRdbCore))
        .classNameKey("repositoryClassName")
        .classNameSuffix("Repository")
        .packageNameKey("repositoryPackage")
        .packageNameSuffix("repository")
        .pathKey("repositoryPath")
        .pathSuffix("repository")
        .templateName("/templates/repository.java")
        .build();

    private final Template REPOSITORY_IMPL = Template.builder()
        .modules(ListUtil.of(TutelaryModule.storage, TutelaryModule.Storage.storageRdbCore))
        .classNameKey("repositoryImplClassName")
        .classNameSuffix("RepositoryImpl")
        .packageNameKey("repositoryImplPackage")
        .packageNameSuffix("repository.impl")
        .pathKey("repositoryImplPath")
        .pathSuffix("repository" + File.separator + "impl")
        .templateName("/templates/repositoryImpl.java")
        .build();

    private final Template MAPPER = Template.builder()
        .modules(ListUtil.of(TutelaryModule.storage, TutelaryModule.Storage.storageRdbCore))
        .classNameKey("mapperClassName")
        .classNameSuffix("Mapper")
        .packageNameKey("mapperPackage")
        .packageNameSuffix("mapper")
        .pathKey("mapperPath")
        .pathSuffix("mapper")
        .templateName("/templates/mapper.java")
        .build();

    private final Template ENTITY = Template.builder()
        .modules(ListUtil.of(TutelaryModule.storage, TutelaryModule.Storage.storageRdbCore))
        .classNameKey("entityClassName")
        .classNameSuffix("Entity")
        .packageNameKey("entityPackage")
        .packageNameSuffix("bean.entity")
        .pathKey("entityPath")
        .pathSuffix("bean" + File.separator + "entity")
        .templateName("/templates/do.java")
        .build();

    private final Template CONTROLLER = Template.builder()
        .modules(ListUtil.of(TutelaryModule.api))
        .classNameKey("controllerClassName")
        .classNameSuffix("Controller")
        .packageNameKey("controllerPackage")
        .packageNameSuffix("api")
        .pathKey("controllerPath")
        .pathSuffix("api")
        .templateName("/templates/mController.java")
        .build();

    private final Template SERVICE = Template.builder()
        .modules(ListUtil.of(TutelaryModule.core))
        .classNameKey("serviceClassName")
        .classNameSuffix("Service")
        .packageNameKey("servicePackage")
        .packageNameSuffix("service")
        .pathKey("servicePath")
        .pathSuffix("service")
        .templateName("/templates/mService.java")
        .build();

    private final Template SERVICE_IMPL = Template.builder()
        .modules(ListUtil.of(TutelaryModule.core))
        .classNameKey("serviceImplClassName")
        .classNameSuffix("ServiceImpl")
        .packageNameKey("serviceImplPackage")
        .packageNameSuffix("service.impl")
        .pathKey("serviceImplPath")
        .pathSuffix("service" + File.separator + "impl")
        .templateName("/templates/mServiceImpl.java")
        .build();

    private final Template ADD_REQUEST = Template.builder()
        .modules(ListUtil.of(TutelaryModule.api))
        .classNameKey("addRequestClassName")
        .classNameSuffix("AddRequest")
        .packageNameKey("addRequestPackage")
        .packageNameSuffix("bean.req")
        .pathKey("addRequestPathKey")
        .pathSuffix("bean" + File.separator + "req")
        .templateName("/templates/addRequest.java")
        .build();

    private final Template EDIT_REQUEST = Template.builder()
        .modules(ListUtil.of(TutelaryModule.api))
        .classNameKey("editRequestClassName")
        .classNameSuffix("EditRequest")
        .packageNameKey("editRequestPackage")
        .packageNameSuffix("bean.req")
        .pathKey("editRequestPathKey")
        .pathSuffix("bean" + File.separator + "req")
        .templateName("/templates/editRequest.java")
        .build();

    private final Template PAGE_QUERY_REQUEST = Template.builder()
        .modules(ListUtil.of(TutelaryModule.api))
        .classNameKey("pageQueryRequestClassName")
        .classNameSuffix("PageQueryRequest")
        .packageNameKey("pageQueryRequestPackage")
        .packageNameSuffix("bean.req")
        .pathKey("pageQueryRequestPathKey")
        .pathSuffix("bean" + File.separator + "req")
        .templateName("/templates/pageQueryRequest.java")
        .build();

    @Override
    public Map<String, Object> getObjectMap(final ConfigBuilder config, final TableInfo tableInfo) {
        Map<String, Object> objectMap = super.getObjectMap(config, tableInfo);

        // Domain
        this.addTemplateConfig(config, objectMap, DOMAIN);

        // QueryDomain
        this.addTemplateConfig(config, objectMap, QUERY_DOMAIN);

        // Response
        this.addTemplateConfig(config, objectMap, RESPONSE);

        // DAO
        this.addTemplateConfig(config, objectMap, DAO);

        // Repository
        this.addTemplateConfig(config, objectMap, REPOSITORY);

        // RepositoryImpl
        this.addTemplateConfig(config, objectMap, REPOSITORY_IMPL);

        // Mapper
        this.addTemplateConfig(config, objectMap, MAPPER);

        // Entity
        this.addTemplateConfig(config, objectMap, ENTITY);

        // Controller
        this.addTemplateConfig(config, objectMap, CONTROLLER);

        // Service
        this.addTemplateConfig(config, objectMap, SERVICE);

        // ServiceImpl
        this.addTemplateConfig(config, objectMap, SERVICE_IMPL);

        // addRequest
        this.addTemplateConfig(config, objectMap, ADD_REQUEST);

        // editRequest
        this.addTemplateConfig(config, objectMap, EDIT_REQUEST);

        // pageQueryRequest
        this.addTemplateConfig(config, objectMap, PAGE_QUERY_REQUEST);

        return objectMap;
    }

    private void addTemplateConfig(ConfigBuilder config,
        Map<String, Object> objectMap,
        Template templateConfig) {
        // 类名
        String className = MapUtil.get(objectMap, "table", TableInfo.class)
            .getControllerName()
            .replace("Controller", templateConfig.getClassNameSuffix());
        // 包
        String packageName = MapUtil.get(objectMap, "package", Map.class)
            .get("Controller")
            .toString()
            .replace("controller", templateConfig.getPackageNameSuffix());
        // 路径
        String path = getPath(templateConfig, config);

        objectMap.put(templateConfig.getClassNameKey(), className);
        objectMap.put(templateConfig.getPackageNameKey(), packageName);
        objectMap.put(templateConfig.getPathKey(), path);
    }

    private String getPath(Template template, ConfigBuilder config) {
        StringBuilder path = new StringBuilder(ROOT_MODULE + File.separator);
        for (String module : template.getModules()) {
            path.append(module).append(File.separator);
        }
        path.append("src").append(File.separator).append("main").append(File.separator).append("java");
        String parent = System.getProperty("user.dir");
        if (!parent.endsWith(File.separator)) {
            parent += File.separator;
        }
        return parent + path + File.separator +
               config.getPackageConfig().getParent().replaceAll("\\.", Matcher.quoteReplacement(File.separator)) +
               File.separator +
               template.getPathSuffix();
    }

    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            final ConfigBuilder config = this.getConfigBuilder();
            final List<TableInfo> tableInfoList = config.getTableInfoList();
            tableInfoList.forEach(tableInfo -> {
                final Map<String, Object> objectMap = this.getObjectMap(config, tableInfo);
                Optional.ofNullable(config.getInjectionConfig()).ifPresent(t -> {
                    t.beforeOutputFile(tableInfo, objectMap);
                    outputCustomFile(t.getCustomFiles(), tableInfo, objectMap);
                });
                // entity
                outputTemplate(tableInfo, objectMap, ENTITY);
                // mapper and xml
                outputTemplate(tableInfo, objectMap, MAPPER);
                // domain
                outputTemplate(tableInfo, objectMap, DOMAIN);
                // queryDomain
                outputTemplate(tableInfo, objectMap, QUERY_DOMAIN);
                // output
                outputTemplate(tableInfo, objectMap, RESPONSE);
                // addRequest
                outputTemplate(tableInfo, objectMap, ADD_REQUEST);
                // editRequest
                outputTemplate(tableInfo, objectMap, EDIT_REQUEST);
                // pageQueryRequest
                outputTemplate(tableInfo, objectMap, PAGE_QUERY_REQUEST);
                // dao
                outputTemplate(tableInfo, objectMap, DAO);
                // repository
                outputTemplate(tableInfo, objectMap, REPOSITORY);
                // repositoryImpl
                outputTemplate(tableInfo, objectMap, REPOSITORY_IMPL);
                // service
                outputTemplate(tableInfo, objectMap, SERVICE);
                // serviceImpl
                outputTemplate(tableInfo, objectMap, SERVICE_IMPL);
                // controller
                outputTemplate(tableInfo, objectMap, CONTROLLER);
            });
        } catch (Exception e) {
            throw new RuntimeException("无法创建文件，请检查配置信息", e);
        }
        return this;
    }

    private void outputTemplate(TableInfo tableInfo, Map<String, Object> objectMap, Template template) {
        String path = MapUtil.get(objectMap, template.getPathKey(), String.class);
        getTemplateFilePath(templateFile -> template.getTemplateName())
            .ifPresent(entity -> {
                String filePath = path + File.separator
                                  + MapUtil.get(objectMap, template.getClassNameKey(), String.class)
                                  + suffixJavaOrKt();
                outputFile(new File(filePath), objectMap, entity, this.fileOverride);
            });
    }

    @Override
    protected void outputMapper(TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String mapperPath = getPathInfo(OutputFile.mapper);
        if (StrUtil.isNotBlank(tableInfo.getMapperName()) && StrUtil.isNotBlank(mapperPath)) {
            getTemplateFilePath(TemplateConfig::getMapper).ifPresent(mapper -> {
                String mapperFile =
                    String.format((mapperPath + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()),
                        entityName);
                outputFile(new File(mapperFile), objectMap, mapper, this.fileOverride);
            });
        }
    }

    @Override
    protected void outputEntity(TableInfo tableInfo,
        Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String entityPath = getPathInfo(OutputFile.entity);
        if (StrUtil.isNotBlank(entityName) && StrUtil.isNotBlank(entityPath)) {
            getTemplateFilePath(
                template -> template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())).ifPresent((entity) -> {
                String entityFile = String.format((entityPath + File.separator + "%s" + suffixJavaOrKt()), entityName);
                outputFile(new File(entityFile), objectMap, entity,
                    getConfigBuilder().getStrategyConfig().entity().isFileOverride());
            });
        }
    }
}

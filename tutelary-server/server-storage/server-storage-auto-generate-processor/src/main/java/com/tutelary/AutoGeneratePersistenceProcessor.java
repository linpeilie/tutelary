package com.tutelary;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.tutelary.common.annotation.AutoPersistence;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

@SupportedAnnotationTypes(AutoGeneratePersistenceProcessor.AUTO_PERSISTENCE)
public class AutoGeneratePersistenceProcessor extends AbstractProcessor {

    public static final String AUTO_PERSISTENCE = "com.tutelary.common.annotation.AutoPersistence";

    private static final String MAPPER_PACKAGE = "com.tutelary.mapper";

    private static final String REPOSITORY_PACKAGE = "com.tutelary.repository";

    private static final String REPOSITORY_IMPL_PACKAGE = "com.tutelary.repository.impl";

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        annotations.stream()
            .filter(this::isAutoPersistence)
            .forEach(annotation -> processAutoPersistenceAnnotation(roundEnv, annotation));
        return false;
    }

    private boolean isAutoPersistence(TypeElement annotation) {
        return AUTO_PERSISTENCE.contentEquals(annotation.getQualifiedName());
    }

    private void processAutoPersistenceAnnotation(RoundEnvironment roundEnv, TypeElement annotation) {
        roundEnv.getElementsAnnotatedWith(annotation)
            .stream()
            .map(this::toAutoPersistenceDescriptor)
            .filter(Objects::nonNull)
            .forEach(this::writePersistenceClassFile);
    }

    private AutoPersistenceDescriptor toAutoPersistenceDescriptor(Element ele) {
        final AutoPersistence annotation = ele.getAnnotation(AutoPersistence.class);
        if (annotation == null) {
            return null;
        }
        final AutoPersistenceDescriptor autoPersistenceDescriptor = new AutoPersistenceDescriptor();
        autoPersistenceDescriptor.setDomainClass(classToClassName(annotation::domain));
        autoPersistenceDescriptor.setQueryDomainClass(classToClassName(annotation::queryDomain));
        autoPersistenceDescriptor.setEntityClass(ClassName.get((TypeElement) ele));
        return autoPersistenceDescriptor;
    }

    private ClassName classToClassName(Supplier<Class<?>> supplier) {
        TypeMirror typeMirror = null;
        try {
            final Class<?> domain = supplier.get();
        } catch (MirroredTypeException mte) {
            typeMirror = mte.getTypeMirror();
        }
        if (typeMirror == null) {
            return null;
        }
        return (ClassName) ClassName.get(typeMirror);
    }

    private void writePersistenceClassFile(AutoPersistenceDescriptor descriptor) {
        writePersistenceClass(descriptor, MAPPER_PACKAGE, descriptor.mapperName(), this::createMapperTypeSpec);
        writePersistenceClass(descriptor, REPOSITORY_PACKAGE, descriptor.repositoryName(), this::createRepositoryTypeSpec);
        writePersistenceClass(descriptor, REPOSITORY_IMPL_PACKAGE, descriptor.repositoryImplName(), this::createRepositoryImplTypeSpec);
    }

    private void writePersistenceClass(AutoPersistenceDescriptor descriptor, String packageName, String className, Function<AutoPersistenceDescriptor, TypeSpec> function) {
        try (final Writer writer = processingEnv.getFiler()
            .createSourceFile(packageName + "." + className)
            .openWriter()) {
            JavaFile.builder(packageName, function.apply(descriptor)).build().writeTo(writer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private TypeSpec createRepositoryImplTypeSpec(final AutoPersistenceDescriptor descriptor) {
        final ParameterizedTypeName abstractRepository =
            ParameterizedTypeName.get(ClassName.get("com.tutelary.common.repository", "AbstractRepository"),
                descriptor.getQueryDomainClass(), descriptor.getDomainClass(), descriptor.getEntityClass(),
                ClassName.get(MAPPER_PACKAGE, descriptor.mapperName()));
        return TypeSpec.classBuilder(descriptor.repositoryImplName())
            .superclass(abstractRepository)
            .addSuperinterface(ClassName.get(REPOSITORY_PACKAGE, descriptor.repositoryName()))
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(ClassName.get("org.springframework.stereotype", "Repository"))
            .build();
    }

    private TypeSpec createRepositoryTypeSpec(AutoPersistenceDescriptor descriptor) {
        final ParameterizedTypeName baseRepository =
            ParameterizedTypeName.get(ClassName.get("com.tutelary.common.repository", "BaseRepository"),
                descriptor.getQueryDomainClass(), descriptor.getDomainClass(), descriptor.getEntityClass());
        return TypeSpec.interfaceBuilder(descriptor.repositoryName())
            .addSuperinterface(baseRepository)
            .addSuperinterface(ClassName.get("com.tutelary.dao", descriptor.daoName()))
            .addModifiers(Modifier.PUBLIC)
            .build();
    }

    private TypeSpec createMapperTypeSpec(AutoPersistenceDescriptor descriptor) {
        final ParameterizedTypeName baseMapper =
            ParameterizedTypeName.get(ClassName.get("com.baomidou.mybatisplus.core.mapper", "BaseMapper"),
                descriptor.getEntityClass());
        return TypeSpec.interfaceBuilder(descriptor.mapperName())
            .addSuperinterface(baseMapper)
            .addModifiers(Modifier.PUBLIC)
            .build();
    }

}

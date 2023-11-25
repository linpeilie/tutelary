package cn.easii.tutelary.client.core.compiler;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import java.util.ArrayList;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class CharSequenceCompiler {

    private static final Log LOG = LogFactory.get(CharSequenceCompiler.class);

    private final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

    private final StandardJavaFileManager standardJavaFileManager;

    private final List<String> options = new ArrayList<>();

    private final DynamicClassLoader dynamicClassLoader;

    public CharSequenceCompiler(ClassLoader classLoader) {
        if (javaCompiler == null) {
            throw new IllegalStateException(
                "Can not load JavaCompiler from javax.tools.ToolProvider#getSystemJavaCompiler()," +
                " please confirm the application running in JDK not JRE.");
        }
        standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        options.add("-Xlint:unchecked");
        dynamicClassLoader = new DynamicClassLoader(classLoader);
    }

    public byte[] compile(String qualifiedClassName, String javaSource) {
        List<JavaFileObject> compilationUnits = new ArrayList<>();
        compilationUnits.add(new StringSource(qualifiedClassName, javaSource));

        JavaFileManager javaFileManager = new DynamicJavaFileManager(standardJavaFileManager, dynamicClassLoader);

        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();

        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, javaFileManager, collector, options, null,
            compilationUnits);

        DiagnosticList errors = new DiagnosticList();
        DiagnosticList warnings = new DiagnosticList();

        Boolean result = task.call();
        if (!result || !collector.getDiagnostics().isEmpty()) {
            for (Diagnostic<? extends JavaFileObject> diagnostic : collector.getDiagnostics()) {
                switch (diagnostic.getKind()) {
                    case NOTE:
                    case MANDATORY_WARNING:
                    case WARNING:
                        warnings.addDiagnostics(diagnostic);
                        break;
                    case OTHER:
                    case ERROR:
                    default:
                        errors.addDiagnostics(diagnostic);
                        break;
                }
                if (!warnings.isEmpty()) {
                    LOG.warn("Compilation Warn...\nQualified Class Name : %s\nWarning Message : %s", qualifiedClassName,
                        warnings.getMessages());
                }
                if (!errors.isEmpty()) {
                    throw new CharSequenceCompilerException("Compilation Error", qualifiedClassName, errors);
                }
            }
        }
        return dynamicClassLoader.getByteCodes().get(qualifiedClassName);
    }

}

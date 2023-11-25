package cn.easii.tutelary.client.core.compiler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class DiagnosticList implements Serializable {

    private final List<Diagnostic<? extends JavaFileObject>> diagnostics;

    public DiagnosticList() {
        this.diagnostics = new ArrayList<>();
    }

    public void addDiagnostics(Diagnostic<? extends JavaFileObject> diagnostic) {
        diagnostics.add(diagnostic);
    }

    public boolean isEmpty() {
        return diagnostics.isEmpty();
    }

    private List<Map<String, Object>> getMessageList() {
        List<Map<String, Object>> messages = new ArrayList<Map<String, Object>>();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
            Map<String, Object> message = new HashMap<String, Object>(2);
            message.put("line", diagnostic.getLineNumber());
            message.put("message", diagnostic.getMessage(Locale.US));
            messages.add(message);
        }

        return messages;
    }

    public String getMessages() {
        StringBuilder errors = new StringBuilder();

        for (Map<String, Object> message : getMessageList()) {
            for (Map.Entry<String, Object> entry : message.entrySet()) {
                Object value = entry.getValue();
                if (value != null && !value.toString().isEmpty()) {
                    errors.append(entry.getKey());
                    errors.append(": ");
                    errors.append(value);
                }
                errors.append(" , ");
            }

            errors.append("\n");
        }

        return errors.toString();

    }

}

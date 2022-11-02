package org.example;

import org.graalvm.polyglot.*;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        String modulePath = Paths.get("js/index.js").toAbsolutePath().toString();

        var options = new HashMap<String, String>();
        options.put("js.commonjs-require", "true");
        options.put("js.commonjs-require-cwd", modulePath);

        var ctx = Context.newBuilder("js")
                        .allowExperimentalOptions(true)
                        .allowHostAccess(HostAccess.ALL)
                        .options(options)
                        .allowIO(true)
                        .build();

        var module = ctx.eval("js", "require ('%s')".formatted(modulePath));

        Value jsResult;
        try {
//            jsResult = module.getMember("regularFunction").execute();
            jsResult = module.getMember("asyncFunction").execute();
        } catch (PolyglotException e) {
            e.printStackTrace();
            return;
        }

        if (jsResult.canInvokeMember("then") && jsResult.canInvokeMember("catch")) {
            jsResult.invokeMember(
                    "then", (Consumer<Object>) o -> System.err.println("success"));

            jsResult.invokeMember(
                    "catch", (Consumer<Object>) o -> System.err.println("Stack trace: " + ((Map<?, ?>) o).get("stack")));
        }
    }
}
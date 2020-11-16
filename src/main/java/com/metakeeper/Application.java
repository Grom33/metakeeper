package com.metakeeper;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "metakeeper",
                version = "0.1"
        )
)
public class Application {

    public static void main(String[] args) {
        final Micronaut builder = Micronaut.build(args);
        final ApplicationContext context = builder.start();
    }
}

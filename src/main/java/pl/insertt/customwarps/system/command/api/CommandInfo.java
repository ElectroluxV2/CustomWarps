package pl.insertt.customwarps.system.command.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo
{
    String name();

    String description();

    String usage();

    String permission();

    String[] aliases() default {};

    int minArgs();

    int maxArgs();

    boolean playerOnly() default false;
}

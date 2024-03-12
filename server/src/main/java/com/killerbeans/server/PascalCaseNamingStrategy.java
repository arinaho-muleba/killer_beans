package com.killerbeans.server;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class PascalCaseNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return apply(name);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return apply(name);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return apply(name);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return apply(name);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return apply(name);
    }

    private Identifier apply(Identifier identifier) {
        if (identifier == null) {
            return null;
        }
        String text = identifier.getText();
        if (text == null) {
            return null;
        }
        String pascalCase = toPascalCase(text);
        return Identifier.toIdentifier(pascalCase);
    }

    private String toPascalCase(String input) {
        // Implement your Pascal case conversion logic here
        // For simplicity, you can use a library or write your own logic
        // For example, you can use Apache Commons Lang: StringUtils.capitalize(input)
        return input;
    }
}

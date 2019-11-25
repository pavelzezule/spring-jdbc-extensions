package com.pavelzezule.spring.jdbc.extensions.namedparam;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.util.Assert;

/**
 * Extension of {@link BeanPropertySqlParameterSource} with extra feature of overriding bean property values
 * <p>
 * Usefull for adding custom value mappings and support of unsupported java types by JDBC driver,
 * e.g. {@link Instant} for MySQL
 * 
 * @author Pavel Zezule
 *
 */
public class BeanOverrideSqlParameterSource extends BeanPropertySqlParameterSource {

    private final Map<String, SqlParameterValue> beanOverrideValues;

    /**
     * Creates a new instance for given bean and initial custom sql values
     * 
     * @param bean          instance
     * @param initialValues sql values
     */
    public BeanOverrideSqlParameterSource(Object bean, SqlParameterValue... initialValues) {
        super(bean);
        Assert.notNull(initialValues, "Initial values must not be null");
        this.beanOverrideValues = stream(initialValues).collect(toMap(SqlParameterValue::getName, identity()));
    }

    @Override
    public int getSqlType(String paramName) {
        if (beanOverrideValues.containsKey(paramName)) {
            return beanOverrideValues.get(paramName).getSqlType();
        } else {
            return super.getSqlType(paramName);
        }
    }

    @Override
    public Object getValue(String paramName) {
        Assert.notNull(paramName, "Bean parameter name must not be null");
        if (beanOverrideValues.containsKey(paramName)) {
            return beanOverrideValues.get(paramName).getValue();
        } else {
            return super.getValue(paramName);
        }
    }

    /**
     * Returns unmodifiable map of bean override values
     * 
     * @return Map of override values, key = property name, value = sql value
     */
    public Map<String, SqlParameterValue> getBeanOverrideValues() {
        return Collections.unmodifiableMap(beanOverrideValues);
    }

}
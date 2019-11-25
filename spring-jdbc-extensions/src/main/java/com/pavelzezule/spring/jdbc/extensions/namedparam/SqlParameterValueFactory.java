package com.pavelzezule.spring.jdbc.extensions.namedparam;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlParameterValue;

/**
 * Factory for custom {@link SqlParameterValue}
 * 
 * @author Pavel Zezule
 *
 */
public final class SqlParameterValueFactory {
    
    public static SqlParameterValue createTimestampValue(String parameterName, Instant parameterValue) {
        return new SqlParameterValue(new SqlParameter(parameterName, Types.TIMESTAMP), Timestamp.from(parameterValue));
    }

}

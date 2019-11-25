package com.pavelzezule.spring.jdbc.extensions.namedparam;

import static com.pavelzezule.spring.jdbc.extensions.namedparam.SqlParameterValueFactory.createTimestampValue;
import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.SqlParameterValue;
import com.pavelzezule.spring.jdbc.extensions.model.TestDto;

/**
 * 
 * JUnit test for {@link BeanOverrideSqlParameterSource}
 * 
 * @author Pavel Zezule
 *
 */
public class BeanOverrideSqlParameterSourceTest {
    
    private BeanOverrideSqlParameterSource source;
    
    private static final String NAME_FIELD = "dtoName";
    
    private static final String TIMESTAMP_FIELD = "timestamp";
    
    private Instant now;
    
    private TestDto dto;
    
    private SqlParameterValue sqlValue;
    
    private String name = "abcd";
    
    @Before
    public void setUp() {
        now = Instant.now();
        dto = new TestDto(name, now);
        sqlValue = createTimestampValue(TIMESTAMP_FIELD, dto.getTimestamp());
    }
    
    @Test
    public void init() {
        //WHEN
        source = new BeanOverrideSqlParameterSource(dto, sqlValue);
        //THEN
        assertThat(source.getBeanOverrideValues()).hasSize(1);
        assertThat(source.getBeanOverrideValues()).containsKey(TIMESTAMP_FIELD);
        assertThat(source.getBeanOverrideValues()).containsValue(sqlValue);
    }
    
    @Test
    public void getSqlKey() {
        //WHEN
        source = new BeanOverrideSqlParameterSource(dto, sqlValue);
        //THEN
        assertThat(source.getSqlType(NAME_FIELD)).isEqualTo(Types.VARCHAR);
        assertThat(source.getSqlType(TIMESTAMP_FIELD)).isEqualTo(Types.TIMESTAMP);
    }
    
    @Test
    public void getValue() {
        //WHEN
        source = new BeanOverrideSqlParameterSource(dto, sqlValue);
        //THEN
        assertThat(source.getValue(NAME_FIELD)).isEqualTo(name);
        Object timestampValue = source.getValue(TIMESTAMP_FIELD);
        assertThat(timestampValue).isInstanceOf(Timestamp.class);
        Timestamp timestamp = (Timestamp) timestampValue;
        assertThat(timestamp.toInstant()).isEqualTo(now);
    }

}

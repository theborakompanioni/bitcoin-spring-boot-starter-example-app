package org.tbk.lightning.lnurl.example.db.migration;

import com.google.common.collect.Lists;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

@Component
public class V2__spring_session extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        // taken from "classpath:org/springframework/session/jdbc/schema-sqlite.sql"
        String sql1 = """
                CREATE TABLE SPRING_SESSION (
                    PRIMARY_ID CHARACTER(36) NOT NULL,
                    SESSION_ID CHARACTER(36) NOT NULL,
                    CREATION_TIME INTEGER NOT NULL,
                    LAST_ACCESS_TIME INTEGER NOT NULL,
                    MAX_INACTIVE_INTERVAL INTEGER NOT NULL,
                    EXPIRY_TIME INTEGER NOT NULL,
                    PRINCIPAL_NAME VARCHAR(100),
                    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
                );
                """;
        String sql2 = """
                CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
                """;
        String sql3 = """
                CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
                """;
        String sql4 = """
                CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);
                """;
        String sql5 = """
                CREATE TABLE SPRING_SESSION_ATTRIBUTES (
                    SESSION_PRIMARY_ID CHAR(36) NOT NULL,
                    ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
                    ATTRIBUTE_BYTES BLOB NOT NULL,
                    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
                    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
                );
                """;

        for (String sql : Lists.newArrayList(sql1, sql2, sql3, sql4, sql5)) {
            try (PreparedStatement statement = context.getConnection().prepareStatement(sql)) {
                statement.execute();
            }
        }
    }
}

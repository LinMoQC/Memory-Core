package com.linmoblog.server.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.util.Objects;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean userTableExists = checkUserTableExists();
        System.out.println(checkUserTableExists());
        if (!userTableExists) {
            executeDbSql();
        }else {
        }
    }

    private boolean checkUserTableExists() {
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'memory' AND table_name = 'user'";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count > 0;
    }

    private void executeDbSql() {
        try {
            Resource resource = new ClassPathResource("db_init/db.sql");
            ScriptUtils.executeSqlScript(Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection(), resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

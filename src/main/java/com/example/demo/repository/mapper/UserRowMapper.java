package com.example.demo.repository.mapper;

import com.example.demo.enums.UserStatus;
import com.example.demo.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                   .id(rs.getLong("id"))
                   .username(rs.getString("username"))
                   .passwordHash(rs.getString("password_hash"))
                   .status(UserStatus.valueOf(rs.getString("status")))
                   .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                   .updatedAt(
                           rs.getTimestamp("updated_at") != null ?
                                   rs.getTimestamp("updated_at").toLocalDateTime() : null
                   )
                   .build();
    }
}
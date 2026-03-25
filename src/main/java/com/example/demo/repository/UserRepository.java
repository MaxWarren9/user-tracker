package com.example.demo.repository;

import com.example.demo.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import com.example.demo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.repository.mapper.UserRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper mapper;

    public User save(User user) {

        String sql = """
            INSERT INTO users_service.users(username, password_hash, status, created_at, role)
            VALUES (?, ?, ?, ?, ?)
            RETURNING *
            """;

        return jdbcTemplate.queryForObject(sql, mapper,
                user.getUsername(),
                user.getPasswordHash(),
                user.getStatus().name(),
                user.getCreatedAt(),
                user.getRole().name()
        );
    }

    public Optional<User> findById(Long id) {

        String sql = """
            SELECT * FROM users_service.users
            WHERE id = ? AND status <> ?
            """;

        return jdbcTemplate.query(sql, mapper, id, UserStatus.DELETED.name())
                           .stream()
                           .findFirst();
    }

    public List<User> findAll() {

        String sql = """
            SELECT * FROM users_service.users
            WHERE status <> ?
            """;

        return jdbcTemplate.query(sql, mapper, UserStatus.DELETED.name());
    }

    public void softDelete(Long id) {

        String sql = """
            UPDATE users_service.users
            SET status = ?,
                updated_at = now()
            WHERE id = ?
            """;

        jdbcTemplate.update(sql,UserStatus.DELETED.name(), id);
    }
}
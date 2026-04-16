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
    private static final String SAVE = """
            INSERT INTO users_service.users(username, password_hash, status, created_at, role)
            VALUES (?, ?, ?, ?, ?)
            RETURNING *
            """;
    private static final String FIND_BY_ID = """
            SELECT * FROM users_service.users
            WHERE id = ? AND status <> ?
            """;
    private static final String FIND_ALL = """
            SELECT * FROM users_service.users
            WHERE status <> ?
            """;
    private static final String SOFT_DELETE = """
            UPDATE users_service.users
            SET status = ?,
                updated_at = NOW()
            WHERE id = ?
            """;

    public User save(User user) {
        return jdbcTemplate.queryForObject(SAVE, mapper,
                user.getUsername(),
                user.getPasswordHash(),
                user.getStatus()
                    .name(),
                user.getCreatedAt(),
                user.getRole()
                    .name()
        );
    }

    public Optional<User> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID, mapper, id, UserStatus.DELETED.name())
                           .stream()
                           .findFirst();
    }

    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper, UserStatus.DELETED.name());
    }

    public void softDelete(Long id) {
        jdbcTemplate.update(SOFT_DELETE, UserStatus.DELETED.name(), id);
    }
}
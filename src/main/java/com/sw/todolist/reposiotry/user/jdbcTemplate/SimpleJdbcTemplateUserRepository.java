package com.sw.todolist.reposiotry.user.jdbcTemplate;

import com.sw.todolist.domain.Users;
import com.sw.todolist.reposiotry.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class SimpleJdbcTemplateUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SimpleJdbcTemplateUserRepository(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users create(Users user) {
        log.info("user id = {}", user.getUserId());
        log.info("user name = {}", user.getUserName());

        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoding(rawPassword));

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        Number key = jdbcInsert.executeAndReturnKey(parameterSource);

        user.setId(key.longValue());
        return user;
    }

    /*
     * 증가하는 id값으로 찾는다.
     * 찾은 유저의 id가 찾으려는 userId면?
     *  */
    @Override
    public Optional<Users> read(String userId) {
        String sql = "select id, user_id, user_name, password from users where user_id=:userId";

        try {
            Map<String, String> param = Map.of("userId", userId);
            Users user = template.queryForObject(sql, param, new BeanPropertyRowMapper<>(Users.class));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(String userId, String password) {
        String sql = "update users set password=:password where user_id=:userId";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("password", passwordEncoding(password))
                .addValue("userId", userId);

        template.update(sql, param);
    }

    @Override
    public void delete(String userId) {
        String sql = "delete from users where user_id=:userId";

        MapSqlParameterSource param = new MapSqlParameterSource("userId", userId);
        template.update(sql, param);
    }

    private String passwordEncoding(String rawPassword) {
        String encode = passwordEncoder.encode(rawPassword);
        log.info("암호 = {}, 길이 ={}", encode, encode.length());
        return encode;
    }
}

package com.sw.todolist.reposiotry.list.jdbcTemplate;

import com.sw.todolist.domain.TodoList;
import com.sw.todolist.reposiotry.list.TodoListDto;
import com.sw.todolist.reposiotry.list.TodoListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcTemplateTodoListRepository implements TodoListRepository {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JdbcTemplateTodoListRepository(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("todolist")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public TodoList create(TodoList list) {
         SqlParameterSource param = new BeanPropertySqlParameterSource(list);

        Number key = jdbcInsert.executeAndReturnKey(param);
        list.setId(key.longValue());
        return list;
    }

    @Override
    public Optional<TodoList> read(Long listId) {
        String sql = "select id, content, completion, user_id form todolist where id=:id";

        try {
            Map<String, Object> param = Map.of("id", listId);
            TodoList todoList = template.queryForObject(sql, param, new BeanPropertyRowMapper<>(TodoList.class));
            return Optional.of(todoList);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<TodoList> readOfListAll(String userId) {
        String sql = "select id, content, completion, user_id from todolist where user_id=:userId";

         /*
            where user_id=:userId 에서 userId를 파라미터 맵핑 해주는 역할
            Map.of("userId", userId); "userId" 문자열 이 부분이 =:userId와 맵핑 되고
            실제 userId 값이 들어간다.
         */
        Map<String, Object> param = Map.of("userId", userId);
        // 할일 목록은 동적 쿼리 불필요, 단지 전체 목록을 뿌려주기만 할뿐, 즉 탐색이 없다.
        return template.query(sql, param, new BeanPropertyRowMapper<>(TodoList.class));
    }

    @Override
    public void update(Long listId, TodoListDto list) {
        String sql = "update todolist set content=:content, completion=:completion where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("content", list.getContent())
                .addValue("completion", list.getCompletion())
                .addValue("id", listId);

        template.update(sql, param);
    }

    @Override
    public void delete(Long listId) {
        String sql = "delete from todolist where id=:id";

        SqlParameterSource param = new MapSqlParameterSource("id", listId);
        template.update(sql, param);
    }
}

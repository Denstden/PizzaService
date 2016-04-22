package ua.com.rd.pizzaservice.repository.pizza.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PostgreSQLPizzaRepository implements PizzaRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addPizza(Pizza pizza) {
        jdbcTemplate.update(
                "INSERT INTO pizzas (name, price, type) VALUES(?, ?, (SELECT pizza_type_id FROM pizza_types WHERE pizza_type=?));",
                pizza.getName(), pizza.getPrice(), pizza.getType().name());
    }

    @Override
    public Pizza getPizzaByID(Long id) {
        Pizza pizza = this.jdbcTemplate.queryForObject(
                "SELECT name, price, pizza_type FROM pizzas JOIN pizza_types ON pizza_type_id=type WHERE pizza_id = ?;",
                new Object[]{id},
                new RowMapper<Pizza>(){

                    @Override
                    public Pizza mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Pizza pizza = new Pizza();
                        pizza.setId(id);
                        pizza.setName(rs.getString("name"));
                        pizza.setPrice(rs.getDouble("price"));
                        String type = rs.getString("pizza_type");
                        pizza.setType(Pizza.PizzaType.valueOf(type.trim()));
                        return pizza;
                    }
                });
        return pizza;
    }

    @Override
    public void update(Pizza pizza){
        jdbcTemplate.update(
                "update pizzas SET name = ?, price = ?, type = (SELECT pizza_type_id FROM pizza_types WHERE pizza_type=?);",
                pizza.getName(), pizza.getPrice(), pizza.getType().toString());
    }
    @Override
    public void delete(Pizza pizza){
        jdbcTemplate.update(
                "DELETE FROM pizzas WHERE pizza_id = ?;",
                pizza.getId());
    }
}

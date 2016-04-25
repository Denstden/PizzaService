package ua.com.rd.pizzaservice.repository.pizza.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostgreSQLPizzaRepository implements PizzaRepository {
    private EntityManager entityManager;

    public PostgreSQLPizzaRepository() {
    }

    public PostgreSQLPizzaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addPizza(Pizza pizza) {
        entityManager.persist(pizza);
        /*jdbcTemplate.update(
                "INSERT INTO pizzas (name, price, type) VALUES(?, ?, (SELECT pizza_type_id FROM pizza_types WHERE pizza_type=?));",
                pizza.getName(), pizza.getPrice(), pizza.getType().name());*/
    }

    @Override
    public Pizza getPizzaByID(Long id) {
        return entityManager.find(Pizza.class, id);
        /*Pizza pizza = this.jdbcTemplate.queryForObject(
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
        return pizza;*/
    }

    @Override
    public void update(Pizza pizza){
        Pizza pizza1 = getPizzaByID(pizza.getId());
        pizza1.setName(pizza.getName());
        pizza1.setPrice(pizza.getPrice());
        pizza1.setType(pizza.getType());
        /*jdbcTemplate.update(
                "update pizzas SET name = ?, price = ?, type = (SELECT pizza_type_id FROM pizza_types WHERE pizza_type=?);",
                pizza.getName(), pizza.getPrice(), pizza.getType().toString());*/
    }
    @Override
    public void delete(Pizza pizza){
        entityManager.remove(pizza);
        /*jdbcTemplate.update(
                "DELETE FROM pizzas WHERE pizza_id = ?;",
                pizza.getId());*/
    }

    @Override
    public List<Pizza> findAll() {
        TypedQuery<Pizza> query = entityManager.createQuery("SELECT p FROM Pizza p", Pizza.class);
        return query.getResultList();
    }
}

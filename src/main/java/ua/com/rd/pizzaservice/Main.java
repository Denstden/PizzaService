package ua.com.rd.pizzaservice;

import ua.com.rd.pizzaservice.domain.order.IncorrectStateException;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IncorrectStateException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();

        Pizza pizza = new Pizza();
        pizza.setName("Pizza 1");
        pizza.setPrice(100.0);
        pizza.setType(Pizza.PizzaType.MEAT);

        try{
           /* em.getTransaction().begin();;
			em.persist(pizza);
            em.getTransaction().commit();*/

            Pizza p = em.find(Pizza.class, 6L);
            System.out.println(p);
        }
        finally{
            em.close();
            emf.close();
        }

    }
}

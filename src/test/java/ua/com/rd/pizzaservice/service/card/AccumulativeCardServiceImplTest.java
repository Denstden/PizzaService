package ua.com.rd.pizzaservice.service.card;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccumulativeCardServiceImplTest {
    /*private AccumulativeCardServiceImpl service;
    private AccumulativeCardRepository repository;
    private Customer customer;

    @Before
    public void setUp(){
        customer = new Customer(1l,"Customer",new Address("UA","KIEV","SECHENOVA","12"));
        repository = new InMemAccumulativeCardRepository();
        service = new AccumulativeCardServiceImpl();
        service.setAccumulativeCardRepository(repository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void giveCardShouldBeCallAddCardOneTime(){
        repository = mock(AccumulativeCardRepository.class);
        service = new AccumulativeCardServiceImpl();
        service.setAccumulativeCardRepository(repository);
        service.createCard(customer);
        verify(repository, times(1)).addCard(customer);
    }

    @Test(expected = NoAccumulativeCardException.class)
    public void findCardShouldRiseException() throws NoAccumulativeCardException {
        service.findCard(customer);
    }

    @Test
    public void findCardShouldWork() throws NoAccumulativeCardException {
        AccumulativeCard card = service.createCard(customer);
        assertEquals(card, service.findCard(customer));
    }

    @Test
    public void deleteCardShouldBeCallDeleteCardOneTime() throws NoAccumulativeCardException {
        InMemAccumulativeCardRepository repository = spy(new InMemAccumulativeCardRepository());
        service = new AccumulativeCardServiceImpl();
        service.setAccumulativeCardRepository(repository);
        service.createCard(customer);
        service.deleteCard(customer);
        verify(repository, times(1)).deleteCard(customer);
    }*/
}

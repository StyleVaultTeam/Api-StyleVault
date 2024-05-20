package co.edu.escuelaing.cvds.lab7.controller;

import co.edu.escuelaing.cvds.lab7.model.ToDoItem;
import co.edu.escuelaing.cvds.lab7.service.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class GreetingControllerTest {
    @Mock
    private Model model;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ToDoService toDoService;

    private GreetingController greetingController;

    @BeforeEach
    void setUp() {
        // To be implemented, remove if not needed
        MockitoAnnotations.initMocks(this);
        greetingController = new GreetingController(toDoService);
    }

    @Test
    void example() {
        // Arrange / Given - precondition or setup

        // Act / When - action or the behaviour that we are going test
        String value = greetingController.greeting("Jorge", model);

        // Assert / Then - verify the output
        System.out.println(value);
        assertEquals("greeting", value);
    }

    class ToDoItemMatcher implements ArgumentMatcher<ToDoItem> {
        private ToDoItem left;

        public ToDoItemMatcher(ToDoItem left) {
            this.left = left;
        }

        // https://www.baeldung.com/mockito-argument-matchers
        @Override
        public boolean matches(ToDoItem right) {
            return left.getId().equals(right.getId()) &&
                    left.getUserId().equals(right.getUserId()) &&
                    left.getTitle().equals(right.getTitle()) &&
                    left.getCompleted() == right.getCompleted();
        }
    }

}
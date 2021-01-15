import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjg.jackson.example.model.ImmutableBook;
import com.hjg.jackson.example.model.ImmutableJsonBook;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/15
 */
public class JsonTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private ImmutableBook immutableBook = new ImmutableBook("C++", new Date(), new BigDecimal("2000"));
    private ImmutableJsonBook immutableJsonBook = new ImmutableJsonBook("C++", new Date(), new BigDecimal("2000"));

    @Test
    public void constructorPropertyTest() throws JsonProcessingException {
        String valueStr = objectMapper.writeValueAsString(immutableBook);
        System.out.println(valueStr);

        ImmutableBook newBook = objectMapper.readValue(valueStr, ImmutableBook.class);
        System.out.println(newBook);
    }

    @Test
    public void jsonCreatorTest() throws JsonProcessingException {
        String valueStr = objectMapper.writeValueAsString(immutableJsonBook);
        System.out.println(valueStr);

        ImmutableJsonBook newBook = objectMapper.readValue(valueStr, ImmutableJsonBook.class);
        System.out.println(newBook);
    }
}

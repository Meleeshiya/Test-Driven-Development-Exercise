package uk.ac.shu.centric.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matcher;
import uk.ac.shu.centric.services.OfficerServiceTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TestUtils {

    private static Logger log = LogManager.getLogger(TestUtils.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    // region Helpers

    public static void logJson(Object object) {
        try {
            log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
        } catch (JsonProcessingException e) {
            log.error("Failed when converting the object to JSON in logJson.", e);
        }
    }

    // endregion

    // region Assertions

    public static <T> void assertSuccess(Result<T> result, Matcher<? super T> entityMatcher) {
        assertThat(result, allOf(
                hasProperty("success", equalTo(true)),
                hasProperty("entity", entityMatcher),
                hasProperty("message", is(emptyOrNullString()))
        ));
    }

    public static <T> void assertFailure(Result<T> result, String message) {
        assertThat(result, allOf(
                hasProperty("success", equalTo(false)),
                hasProperty("entity", nullValue()),
                hasProperty("message", equalTo(message))
        ));
    }

    // endregion

    // region Matchers

    /**
     * With any ID.
     */
    public static Matcher<String> withId() {
        return not(is(emptyOrNullString()));
    }

    /**
     * With given ID.
     */
    public static Matcher<String> withId(String id) {
        return equalTo(id);
    }

    // endregion

}

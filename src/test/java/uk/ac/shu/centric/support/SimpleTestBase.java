package uk.ac.shu.centric.support;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.shu.centric.Config;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public abstract class SimpleTestBase {

    // This is a test base class to allow the Spring Context to load for dependency injection
    // on each test. This ensures clean data.

}

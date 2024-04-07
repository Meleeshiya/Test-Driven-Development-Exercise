package uk.ac.shu.centric.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.shu.centric.models.Officer;
import uk.ac.shu.centric.models.OfficerRanks;
import uk.ac.shu.centric.repositories.OfficerRepository;
import uk.ac.shu.centric.services.OfficerService.CreateOfficer;
import uk.ac.shu.centric.services.OfficerService.UpdateOfficerRank;
import uk.ac.shu.centric.support.Result;
import uk.ac.shu.centric.support.SimpleTestBase;
import uk.ac.shu.centric.support.TestUtils;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.ac.shu.centric.models.OfficerRanks.*;
import static uk.ac.shu.centric.support.Givens.given;
import static uk.ac.shu.centric.support.OfficerBuilder.anOfficer;
import static uk.ac.shu.centric.support.TestUtils.*;


/**
 * Instructions:
 *
 * Work through the TODOs below....
 *
 * Don't worry if you are unsure or struggle with some elements, we are trying to build some common ground and more
 * interested in how our conversation evolves in the interview. This exercise is only one of our methods for
 * grading candidates.
 *
 * We are really interested in your thoughts around solving these tasks; your thoughts on the code, structure and
 * architecture.
 *
 * The matchers in test failures can be difficult to read sometimes, so you can use TestUtils.logJson(result) to help.
 *
 */
public class OfficerServiceTest extends SimpleTestBase {

    private static Logger log = LogManager.getLogger(OfficerServiceTest.class);

    @Autowired
    private OfficerService officerService;

    @Autowired
    private OfficerRepository officerRepository;

    // TODO: (Task 1/5): The test shouldCreateOfficer() is failing because of a bad implementation; please fix it.
    // TODO: (Task 2/5): Add test for shouldNotGetOfficerWhenNotFound; ensuring "No such officer" message.
    // TODO: (Task 3/5): Implement the behaviour for shouldUpdateAnOfficerRank().
    // TODO: (Task 4/5): Add a "date of birth" field to the Officer and update tests accordingly, ensuring they are suitably tested.
    // TODO: (Task 5/5): Add new tests and implementations for a "promote officer" function with the following test cases:
    //                   ---
    //                   Case 1:
    //                   > Given an Officer "Alistair Mcdonald" with rank "Constable"
    //                   > When I "promote" them
    //                   > Then Officer "Alistair Mcdonald" has rank "RankSergent"
    //                   ---
    //                   Case 2:
    //                   > Given an Officer "Theo Nichols" with rank "RankInspector"
    //                   > When I "promote" them
    //                   > Then Officer "Alistair Mcdonald" has rank "RankChiefInspector"
    //                   ---
    //                   Case 3:
    //                   > Given an Officer "Veronika Winter" with rank "RankChiefInspector"
    //                   > When I "promote" them
    //                   > Then I get a message saying "Cannot promote an officer already at top rank"
    //                   > And Officer "Veronika Winter" has rank "RankChiefInspector"

    @Test
    public void shouldGetOfficers() {
        given(anOfficer("O1").name("Taylah Peters").rank(RankInspector));
        given(anOfficer("O2").name("Cairon Stubbs").rank(RankSergent));

        Result<List<Officer>> result = officerService.getOfficers();

        assertSuccess(result, containsInAnyOrder(
                isOfficer(withId("O1")),
                isOfficer(withId("O2"))
        ));
    }

    @Test
    public void shouldNotGetOfficerWhenNotFound() {
        given(anOfficer("O1").name("Taylah Peters").rank(RankInspector));
        given(anOfficer("O2").name("Cairon Stubbs").rank(RankSergent));

        Result<Officer> result = officerService.getOfficer("07");
        assertFailure(result, "No such officer");

    }

    @Test
    public void shouldCreateOfficer() {

        Result<Officer> result = officerService.createOfficer(aCreateOfficerForm("Vivien Conley", "RankConstable", LocalDate.of(1988, 11, 23)));

        assertSuccess(result, allOf(
                hasProperty("id", not(is(emptyOrNullString()))),
                hasProperty("name", equalTo("Vivien Conley")),
                hasProperty("rank", equalTo(RankConstable)),
                hasProperty("dateOfBirth", notNullValue())
        ));
    }

    @Test
    public void shouldNotCreateOfficerWithInvalidRank() {

        Result<Officer> result = officerService.createOfficer(aCreateOfficerForm("Vivien Conley", "RankFoo", LocalDate.of(1998, 9, 04) ));

        assertFailure(result, "Invalid rank given");
    }

    @Test
    public void shouldUpdateAnOfficerRank() {
        given(anOfficer("O1").name("Taylah Peters").rank(RankInspector));
        given(anOfficer("O2").name("Cairon Stubbs").rank(RankSergent));

        Result<Officer> result = officerService.updateOfficerRank("O2", anUpdateOfficerRankForm("RankConstable"));

        assertSuccess(result, anything());
        assertThat(officerRepository.findAll(), containsInAnyOrder(
                isOfficer(withId("O1"), withNameAndRank("Taylah Peters", RankInspector)),
                isOfficer(withId("O2"), withNameAndRank("Cairon Stubbs", RankConstable))
        ));
    }

    @Test
    public void shouldPromoteOfficerToSergeantFrmRankConstable() {
        given(anOfficer("O1").name("Alistair Mcdonald").rank(RankConstable));

        Result<Officer> result = officerService.promoteOfficerRank("O1");
        assertSuccess(result, hasProperty("rank", equalTo(RankSergent)));

    }

    @Test
    public void shouldPromoteOfficerToRankInspectorFrmRankSergent() {
        given(anOfficer("O4").name("John Affleck").rank(RankSergent));

        Result<Officer> result = officerService.promoteOfficerRank("O4");
        assertSuccess(result, hasProperty("rank", equalTo(RankInspector)));

    }

    @Test
    public void shouldPromoteOfficerToRankChiefInspectorFrmRankInspector() {
        given(anOfficer("O2").name("Theo Nichols").rank(RankInspector));

        Result<Officer> result = officerService.promoteOfficerRank("O2");
        assertSuccess(result, hasProperty("rank", equalTo(RankChiefInspector)));

    }

  @Test
    public void shouldNotPromoteAnOfficerAlreadyInTopRank() {
        given(anOfficer("O3").name("Veronika Winter").rank(RankChiefInspector));

        Result<Officer> result = officerService.promoteOfficerRank("O3");
        assertFailure(result, "Cannot promote an officer already at top rank");

    }



    // region Support

    private static CreateOfficer aCreateOfficerForm(String name, String rank, LocalDate dob) {
        CreateOfficer form = new CreateOfficer();
        form.setName(name);
        form.setRank(rank);
        form.setDateOfBirth(dob);
        return form;
    }

    private static UpdateOfficerRank anUpdateOfficerRankForm(String rank) {
        UpdateOfficerRank form = new UpdateOfficerRank();
        form.setRank(rank);
        return form;
    }

    @SafeVarargs
    private static Matcher<Officer> isOfficer(Matcher<String> idMatcher, Matcher<Officer>... otherMatchers) {
        return allOf(
                hasProperty("id", idMatcher),
                allOf(otherMatchers)
        );
    }

    private static Matcher<Officer> withNameAndRank(String name, OfficerRanks rank) {
        return allOf(
                hasProperty("name", equalTo(name)),
                hasProperty("rank", equalTo(rank))
        );
    }

    // endregion

}
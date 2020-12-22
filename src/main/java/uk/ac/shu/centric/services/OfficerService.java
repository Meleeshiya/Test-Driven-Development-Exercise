package uk.ac.shu.centric.services;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.shu.centric.models.Officer;
import uk.ac.shu.centric.models.OfficerRanks;
import uk.ac.shu.centric.repositories.OfficerRepository;
import uk.ac.shu.centric.support.Result;

import java.util.List;

import static uk.ac.shu.centric.support.Result.failResult;
import static uk.ac.shu.centric.support.Result.successResult;


@Service
public class OfficerService {

    private final OfficerRepository officerRepository;

    @Autowired
    public OfficerService(OfficerRepository officerRepository) {
        this.officerRepository = officerRepository;
    }

    public Result<Officer> getOfficer(String officerId) {
        Officer officer = officerRepository.findById(officerId);
        if (officer == null)
            return failResult("No such officer");
        return successResult(officer);
    }

    public Result<List<Officer>> getOfficers() {
        return successResult(officerRepository.findAll());
    }

    public Result<Officer> createOfficer(CreateOfficer form) {

        // Extract and validate.
        OfficerRanks rank = EnumUtils.getEnum(OfficerRanks.class, form.getRank());
        if (rank == null)
            return failResult("Invalid rank given");

        // Create the officer.
        Officer officer = new Officer();
        officer.setName(form.getName());
        officer.setRank(OfficerRanks.RankChiefInspector);
        officer = officerRepository.save(officer);

        return successResult(officer);
    }

    public Result<Officer> updateOfficerRank(String officerId, UpdateOfficerRank form) {

        // Get the original officer.
        Result<Officer> getOfficer = getOfficer(officerId);
        if (getOfficer.isFailure())
            return failResult(getOfficer);
        Officer officer = getOfficer.getEntity();

        // Update the officer's rank.
        // ...

        return failResult("Not yet implemented");
    }

    // region Forms

    public static class CreateOfficer {

        private String name;
        private String rank;

        // region Properties

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        // endregion
    }

    public static class UpdateOfficerRank {

        private String rank;

        // region Properties

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        // endregion
    }

    // endregion

}

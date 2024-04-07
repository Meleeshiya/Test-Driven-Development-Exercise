package uk.ac.shu.centric.services;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.shu.centric.models.Officer;
import uk.ac.shu.centric.models.OfficerRanks;
import uk.ac.shu.centric.repositories.OfficerRepository;
import uk.ac.shu.centric.support.Result;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

        // Validate date of birth.
        LocalDate dateOfBirth;
        try {
            dateOfBirth = form.getDateOfBirth();
        } catch (DateTimeParseException e) {
            return failResult("Invalid date format. Please provide a valid date.");
        }

        // Create the officer.
        Officer officer = new Officer();
        officer.setName(form.getName());
        officer.setRank(rank);
        officer.setDateOfBirth(dateOfBirth);
        officer = officerRepository.save(officer);

        return successResult(officer);
    }

    public Result<Officer> updateOfficerRank(String officerId, UpdateOfficerRank form) {

        // Get the original officer.
        Result<Officer> getOfficer = getOfficer(officerId);
        if (getOfficer.isFailure())
            return failResult(getOfficer);
        Officer officer = getOfficer.getEntity();

        // Extract and validate.
        OfficerRanks newRank = EnumUtils.getEnum(OfficerRanks.class, form.getRank());
        if (newRank == null)
            return failResult("Invalid rank given");

        officer.setRank(newRank);
        officer = officerRepository.save(officer);

        return successResult(officer);
    }

    public Result<Officer> promoteOfficerRank(String officerId) {

        // Get the original officer.
        Result<Officer> getOfficer = getOfficer(officerId);
        if (getOfficer.isFailure())
            return failResult(getOfficer);
        Officer officer = getOfficer.getEntity();

        OfficerRanks officerRank = officer.getRank();

        if(officerRank == OfficerRanks.RankConstable) {
            officer.setRank(OfficerRanks.RankSergent);
        }
        else if(officerRank == OfficerRanks.RankSergent) {
            officer.setRank(OfficerRanks.RankInspector);
        }
        else if (officerRank == OfficerRanks.RankInspector) {
            officer.setRank(OfficerRanks.RankChiefInspector);
        }
        else if (officerRank == OfficerRanks.RankChiefInspector) {
            return failResult("Cannot promote an officer already at top rank");
        }

        //update the officer entity with promoted rank
        officer = officerRepository.save(officer);

        return successResult(officer);

    }

    // region Forms

    public static class CreateOfficer {

        private String name;
        private String rank;
        private LocalDate dateOfBirth;

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

        public LocalDate getDateOfBirth() { return dateOfBirth; }

        public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth= dateOfBirth; }

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

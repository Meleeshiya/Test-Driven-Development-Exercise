package uk.ac.shu.centric.models;

import uk.ac.shu.centric.support.SimpleEntity;
import java.time.LocalDate;

public class Officer implements SimpleEntity {

    private String id;
    private String name;
    private OfficerRanks rank;
    private LocalDate dateOfBirth;

    // region Properties

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OfficerRanks getRank() {
        return rank;
    }

    public void setRank(OfficerRanks rank) {
        this.rank = rank;
    }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth= dateOfBirth; }

    // endregion

}

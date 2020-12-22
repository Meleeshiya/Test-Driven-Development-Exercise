package uk.ac.shu.centric.support;

import uk.ac.shu.centric.models.Officer;
import uk.ac.shu.centric.models.OfficerRanks;


public class OfficerBuilder {

    private final Officer officer = new Officer();

    private OfficerBuilder() {}

    public static OfficerBuilder anOfficer(String id) {
        return new OfficerBuilder().id(id);
    }

    public OfficerBuilder id(String id) {
        this.officer.setId(id);
        return this;
    }

    public OfficerBuilder name(String name) {
        this.officer.setName(name);
        return this;
    }

    public OfficerBuilder rank(OfficerRanks rank) {
        this.officer.setRank(rank);
        return this;
    }

    public Officer build() {
        return officer;
    }

}

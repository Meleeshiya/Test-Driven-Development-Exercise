package uk.ac.shu.centric.models;

import uk.ac.shu.centric.support.SimpleEntity;

public class Officer implements SimpleEntity {

    private String id;
    private String name;
    private OfficerRanks rank;

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

    // endregion

}

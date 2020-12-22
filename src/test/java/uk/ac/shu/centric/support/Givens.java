package uk.ac.shu.centric.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.shu.centric.models.Officer;
import uk.ac.shu.centric.repositories.OfficerRepository;


@Component
public class Givens {

    private static Givens instance;

    private final OfficerRepository officerRepository;

    @Autowired
    public Givens(OfficerRepository officerRepository) {
        this.officerRepository = officerRepository;

        // Maintain static instance.
        Givens.instance = this;
    }

    public static Officer given(OfficerBuilder builder) {
        return instance.officerRepository.save(builder.build());
    }

}

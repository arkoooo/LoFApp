package kubiak.lofapp.Repositories;

import kubiak.lofapp.Model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
    Configuration findByOption(String option);
}

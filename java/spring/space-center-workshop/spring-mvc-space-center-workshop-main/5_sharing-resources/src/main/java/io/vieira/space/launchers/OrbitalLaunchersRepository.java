package io.vieira.space.launchers;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface OrbitalLaunchersRepository extends CrudRepository<OrbitalLauncher, Integer> {

    @Query("select * from orbital_launchers o where o.code_name = :codeName")
    Optional<OrbitalLauncher> findByCodeName(@Param("codeName") String codeName);
}

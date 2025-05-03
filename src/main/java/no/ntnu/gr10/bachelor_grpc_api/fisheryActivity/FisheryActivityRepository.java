package no.ntnu.gr10.bachelor_grpc_api.fisheryActivity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing FisheryActivity entities from the database.
 * <p>
 * Extends Spring Data JPA's JpaRepository to provide CRUD and query methods
 * for FisheryActivity instances.</p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
public interface FisheryActivityRepository extends JpaRepository<FisheryActivity, Long> {
  List<FisheryActivity> findAllByCompany_Id(Long companyId);
  Optional<FisheryActivity> findFisheryActivityByIdAndCompany_Id(Long id, Long companyId);

}

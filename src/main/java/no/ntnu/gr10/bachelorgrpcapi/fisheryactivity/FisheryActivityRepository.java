package no.ntnu.gr10.bachelorgrpcapi.fisheryactivity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for accessing FisheryActivity entities from the database.
 *
 * <p>Extends Spring Data JPA JpaRepository to provide CRUD and query methods
 * for FisheryActivity instances.</p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
public interface FisheryActivityRepository extends JpaRepository<FisheryActivity, Long> {
  /**
   * Find all FisheryActivity entities associated with a specific company ID.
   *
   * @param companyId the ID of the company
   * @return a list of FisheryActivity entities
   */
  @SuppressWarnings("checkstyle:MethodName")
  List<FisheryActivity> findAllByCompany_Id(Long companyId);

  /**
   * Find a FisheryActivity entity by its ID and the associated company ID.
   *
   * @param id the ID of the FisheryActivity
   * @param companyId the ID of the company
   * @return an Optional containing the FisheryActivity if found, or empty if not
   */
  @SuppressWarnings("checkstyle:MethodName")
  Optional<FisheryActivity> findFisheryActivityByIdAndCompany_Id(Long id, Long companyId);

}

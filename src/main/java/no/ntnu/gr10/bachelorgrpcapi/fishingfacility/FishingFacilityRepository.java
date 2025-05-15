package no.ntnu.gr10.bachelorgrpcapi.fishingfacility;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Repository interface for accessing FishingFacility entities from the database.
 *
 * <p>Extends Spring Data JPA JpaRepository to provide CRUD and query methods
 * for FishingFacility instances.</p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
public interface FishingFacilityRepository extends JpaRepository<FishingFacility, Long> {
  /**
   * Find all FishingFacility entities associated with a specific company ID.
   *
   * @param companyId the ID of the company
   * @return a list of FishingFacility entities associated with the specified company ID
   */
  @SuppressWarnings("checkstyle:MethodName")
  List<FishingFacility> findAllByCompany_Id(Long companyId);

  /**
   * Find a FishingFacility entity by its ID and the associated company ID.
   *
   * @param id the ID of the FishingFacility
   * @param companyId the ID of the associated company
   * @return an Optional containing the FishingFacility entity if found, or empty if not found
   */
  @SuppressWarnings("checkstyle:MethodName")
  Optional<FishingFacility> findFishingFacilitiesByIdAndCompany_Id(Long id, Long companyId);
}

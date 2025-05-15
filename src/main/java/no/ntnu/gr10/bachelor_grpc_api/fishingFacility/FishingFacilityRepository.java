package no.ntnu.gr10.bachelor_grpc_api.fishingFacility;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


/**
 * Repository interface for accessing FishingFacility entities from the database.
 * <p>
 * Extends Spring Data JPA JpaRepository to provide CRUD and query methods
 * for FishingFacility instances.</p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
public interface FishingFacilityRepository extends JpaRepository<FishingFacility, Long> {
  List<FishingFacility> findAllByCompany_Id(Long companyId);
  Optional<FishingFacility> findFishingFacilitiesByIdAndCompany_Id(Long id, Long companyId);
}

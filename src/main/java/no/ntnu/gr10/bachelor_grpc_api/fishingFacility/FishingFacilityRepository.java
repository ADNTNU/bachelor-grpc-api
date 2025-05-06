package no.ntnu.gr10.bachelor_grpc_api.fishingFacility;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FishingFacilityRepository extends JpaRepository<FishingFacility, Long> {
  List<FishingFacility> findAllByCompany_Id(Long companyId);
  Optional<FishingFacility> findFishingFacilitiesByIdAndCompany_Id(Long id, Long companyId);
}

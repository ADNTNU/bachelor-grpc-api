package no.ntnu.gr10.bachelorgrpcapi.fishingfacility;

import java.util.List;
import no.ntnu.gr10.bachelorgrpcapi.exception.FishingFacilityNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Service layer for managing FishingFacility entities.
 *
 * <p>Provides methods to retrieve activities by company or by specific ID,
 * throwing a FishingFacilityNotFoundException when no results are found.
 * </p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
@Service
public class FishingFacilityService {

  final FishingFacilityRepository fishingFacilityRepository;

  /**
   * Constructs a new FishingFacilityService with the provided repository.
   *
   * @param fishingFacilityRepository the repository handling FisheryActivity entities
   */
  public FishingFacilityService(FishingFacilityRepository fishingFacilityRepository) {
    this.fishingFacilityRepository = fishingFacilityRepository;
  }

  /**
   * Retrieves all FishingFacility records associated with the given company ID.
   *
   * @param companyId the ID of the company whose facilities to fetch
   * @return a list of FishingFacility entities
   */
  public List<FishingFacility> getAllFishingFacilitiesWithCompanyId(Integer companyId) {
    return fishingFacilityRepository.findAllByCompany_Id(companyId.longValue());
  }

  /**
   * Retrieves a single FishingFacility by its ID and associated company ID.
   *
   * @param id        The ID of the FishingFacility to fetch
   * @param companyId The ID of the company to which the activity should belong
   * @return The FishingFacility entity matching the criteria
   * @throws FishingFacilityNotFoundException if no matching activity is found
   */
  public FishingFacility getByIdAndCompanyId(Long id, Integer companyId) {
    return fishingFacilityRepository
            .findFishingFacilitiesByIdAndCompany_Id(id, companyId.longValue())
            .orElseThrow(() -> new FishingFacilityNotFoundException(
                            "Could not find Fishing Facility with that ID!"
                    )
            );
  }


}

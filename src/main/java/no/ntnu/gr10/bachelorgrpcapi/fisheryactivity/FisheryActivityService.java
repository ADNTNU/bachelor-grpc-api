package no.ntnu.gr10.bachelorgrpcapi.fisheryactivity;

import java.util.List;
import no.ntnu.gr10.bachelorgrpcapi.exception.FisheryActivityNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Service layer for managing FisheryActivity entities.
 *
 * <p>Provides methods to retrieve activities by company or by specific ID,
 * throwing a FisheryActivityNotFoundException when no results are found.
 * </p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
@Service
public class FisheryActivityService {

  final FisheryActivityRepository fisheryActivityRepository;


  /**
   * Constructs a new FisheryActivityService with the provided repository.
   *
   * @param fisheryActivityRepository the repository handling FisheryActivity entities
   */
  public FisheryActivityService(FisheryActivityRepository fisheryActivityRepository) {
    this.fisheryActivityRepository = fisheryActivityRepository;
  }


  /**
   * Retrieves all FisheryActivity records associated with the given company ID.
   *
   * @param companyId the ID of the company whose activities to fetch
   * @return a list of FisheryActivity entities
   */
  public List<FisheryActivity> getAllFisheryActivitiesWithCompanyId(Integer companyId) {
    return fisheryActivityRepository.findAllByCompany_Id(companyId.longValue());
  }


  /**
   * Retrieves a single FisheryActivity by its ID and associated company ID.
   *
   * @param id        the ID of the FisheryActivity to fetch
   * @param companyId the ID of the company to which the activity should belong
   * @return the FisheryActivity entity matching the criteria
   * @throws FisheryActivityNotFoundException if no matching activity is found
   */
  public FisheryActivity getByIdAndCompanyId(Long id, Integer companyId)
          throws FisheryActivityNotFoundException {
    return fisheryActivityRepository.findFisheryActivityByIdAndCompany_Id(id, companyId.longValue())
            .orElseThrow(() -> new FisheryActivityNotFoundException(
                            "Fishery Activity with that id could not be found!"
                    )
            );
  }

}

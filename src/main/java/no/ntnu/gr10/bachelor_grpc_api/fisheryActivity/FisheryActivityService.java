package no.ntnu.gr10.bachelor_grpc_api.fisheryActivity;

import no.ntnu.gr10.bachelor_grpc_api.exception.FisheryActivityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service layer for managing FisheryActivity entities.
 * <p>
 * Provides methods to retrieve activities by company or by specific ID,
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
  public FisheryActivityService(FisheryActivityRepository fisheryActivityRepository){
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
  public FisheryActivity getByIdAndCompanyId(Long id, Integer companyId) throws FisheryActivityNotFoundException{
    FisheryActivity fisheryActivity = fisheryActivityRepository.findFisheryActivityByIdAndCompany_Id(id, companyId.longValue());
    if(fisheryActivity == null){
      throw new FisheryActivityNotFoundException("No Fishery Activity connected to that company or with that id");
    }
    return fisheryActivityRepository.findFisheryActivityByIdAndCompany_Id(id, companyId.longValue());
  }

}

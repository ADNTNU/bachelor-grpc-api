package no.ntnu.gr10.bachelor_grpc_api.fisheryActivity;

import no.ntnu.gr10.bachelor_grpc_api.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FisheryActivityService {

  final FisheryActivityRepository fisheryActivityRepository;
  final CompanyService companyService;

  public FisheryActivityService(FisheryActivityRepository fisheryActivityRepository, CompanyService companyService){
    this.fisheryActivityRepository = fisheryActivityRepository;
    this.companyService = companyService;
  }

  public List<FisheryActivity> getAllFisheryActivitiesWithCompanyId(Integer companyId){
    return fisheryActivityRepository.findAllByCompany_Id(companyId.longValue());
  }

  public FisheryActivity getByIdAndCompanyId(Long id, Integer companyId){
    return fisheryActivityRepository.findFisheryActivityByIdAndCompany_Id(id, companyId.longValue());
  }

}

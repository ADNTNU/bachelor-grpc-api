package no.ntnu.gr10.bachelor_grpc_api.fisheryActivity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FisheryActivityRepository extends JpaRepository<FisheryActivity, Long> {
  List<FisheryActivity> findAllByCompany_Id(Long companyId);
  FisheryActivity findFisheryActivityByIdAndCompany_Id(Long id, Long companyId);

}

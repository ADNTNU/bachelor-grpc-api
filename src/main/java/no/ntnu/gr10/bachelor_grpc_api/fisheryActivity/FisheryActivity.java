package no.ntnu.gr10.bachelor_grpc_api.fisheryActivity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import no.ntnu.gr10.bachelor_grpc_api.company.Company;

import java.time.LocalDateTime;

@Entity
@Table(name = "fishery_activities")
public class FisheryActivity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @Column(name = "setup_date_time", nullable = false)
  private LocalDateTime setupDateTime;

  @Column(name = "tool_type_code", length = 50, nullable = false)
  private String toolTypeCode;

  @Column(name = "tool_type_name", length = 100, nullable = false)
  private String toolTypeName;

  @Column(name = "tool_id", length = 36, nullable = false)
  private String toolId;

  @Column(name = "removed_date_time")
  private LocalDateTime removedDateTime;

  @Column(name = "last_changed_date_time")
  private LocalDateTime lastChangedDateTime;

  @Column(name = "starting_point_lat", nullable = false)
  private Double startingPointLat;

  @Column(name = "starting_point_lon", nullable = false)
  private Double startingPointLon;

  @Column(name = "length")
  private Double length;

  @Column(name = "geometry", columnDefinition = "TEXT")
  private String geometry;

  public FisheryActivity() {}

  public Long getId() {
    return id;
  }

  public Company getCompany() {
    return company;
  }
  public void setCompany(Company company) {
    if (company == null) throw new IllegalArgumentException("Company cannot be null");
    this.company = company;
  }

  public LocalDateTime getSetupDateTime() {
    return setupDateTime;
  }
  public void setSetupDateTime(LocalDateTime setupDateTime) {
    this.setupDateTime = setupDateTime;
  }

  public String getToolTypeCode() {
    return toolTypeCode;
  }
  public void setToolTypeCode(String toolTypeCode) {
    this.toolTypeCode = toolTypeCode;
  }

  public String getToolTypeName() {
    return toolTypeName;
  }
  public void setToolTypeName(String toolTypeName) {
    this.toolTypeName = toolTypeName;
  }

  public String getToolId() {
    return toolId;
  }
  public void setToolId(String toolId) {
    this.toolId = toolId;
  }

  public LocalDateTime getRemovedDateTime() {
    return removedDateTime;
  }
  public void setRemovedDateTime(LocalDateTime removedDateTime) {
    this.removedDateTime = removedDateTime;
  }

  public LocalDateTime getLastChangedDateTime() {
    return lastChangedDateTime;
  }
  public void setLastChangedDateTime(LocalDateTime lastChangedDateTime) {
    this.lastChangedDateTime = lastChangedDateTime;
  }

  public Double getStartingPointLat() {
    return startingPointLat;
  }
  public void setStartingPointLat(Double startingPointLat) {
    this.startingPointLat = startingPointLat;
  }

  public Double getStartingPointLon() {
    return startingPointLon;
  }
  public void setStartingPointLon(Double startingPointLon) {
    this.startingPointLon = startingPointLon;
  }

  public Double getLength() {
    return length;
  }
  public void setLength(Double length) {
    this.length = length;
  }

  public String getGeometry() {
    return geometry;
  }
  public void setGeometry(String geometry) {
    this.geometry = geometry;
  }

  @Override
  public String toString() {
    return "FisheryActivity{" +
            "id=" + id +
            ", company=" + company.getId() +
            ", setupDateTime=" + setupDateTime +
            ", toolTypeCode='" + toolTypeCode + '\'' +
            ", toolTypeName='" + toolTypeName + '\'' +
            ", toolId='" + toolId + '\'' +
            ", removedDateTime=" + removedDateTime +
            ", lastChangedDateTime=" + lastChangedDateTime +
            ", startingPointLat=" + startingPointLat +
            ", startingPointLon=" + startingPointLon +
            ", length=" + length +
            ", geometry='" + geometry + '\'' +
            '}';
  }
}
package no.ntnu.gr10.bachelor_grpc_api.fishingFacility;

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
@Table(name = "fishing_facility")
public class FishingFacility {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "bbox", columnDefinition = "TEXT")
  private String bbox;

  @Column(name = "geometry", columnDefinition = "TEXT", nullable = false)
  private String geometry;

  // Properties
  @Column(name = "version", nullable = false)
  private Integer version;

  @Column(name = "vessel_name")
  private String vesselName;

  @Column(name = "vessel_phone")
  private String vesselPhone;

  @Column(name = "tool_type_code", length = 50, nullable = false)
  private String toolTypeCode;

  @Column(name = "setup_datetime", nullable = false)
  private LocalDateTime setupDateTime;

  @Column(name = "tool_id", length = 36, nullable = false)
  private String toolId;

  @Column(name = "ircs")
  private String ircs;

  @Column(name = "mmsi")
  private String mmsi;

  @Column(name = "imo")
  private String imo;

  @Column(name = "vessel_email")
  private String vesselEmail;

  @Column(name = "tool_type_name", length = 100, nullable = false)
  private String toolTypeName;

  @Column(name = "tool_color", length = 7)
  private String toolColor;

  @Column(name = "source")
  private String source;

  @Column(name = "comment", columnDefinition = "TEXT")
  private String comment;

  @Column(name = "removed_datetime")
  private LocalDateTime removedDateTime;

  @Column(name = "last_changed_datetime")
  private LocalDateTime lastChangedDateTime;

  @Column(name = "last_changed_by_source")
  private String lastChangedBySource;

  @Column(name = "reg_num")
  private String regNum;

  @Column(name = "sbr_reg_num")
  private String sbrRegNum;

  @Column(name = "setup_processed_time")
  private LocalDateTime setupProcessedTime;

  @Column(name = "removed_processed_time")
  private LocalDateTime removedProcessedTime;

  @Column(name = "tool_count")
  private Integer toolCount;

  public FishingFacility(){
    //Default JPA method
  }

  public Long getId() { return id; }

  public Company getCompany() {
    return company;
  }
  public void setCompany(Company company) {
    if (company == null) throw new IllegalArgumentException("Company cannot be null");
    this.company = company;
  }

  public String getType() { return type; }
  public void setType(String type) { this.type = type; }

  public String getBbox() { return bbox; }
  public void setBbox(String bbox) { this.bbox = bbox; }

  public String getGeometry() { return geometry; }
  public void setGeometry(String geometry) { this.geometry = geometry; }

  public Integer getVersion() { return version; }
  public void setVersion(Integer version) { this.version = version; }

  public String getVesselName() { return vesselName; }
  public void setVesselName(String vesselName) { this.vesselName = vesselName; }

  public String getVesselPhone() { return vesselPhone; }
  public void setVesselPhone(String vesselPhone) { this.vesselPhone = vesselPhone; }

  public String getToolTypeCode() { return toolTypeCode; }
  public void setToolTypeCode(String toolTypeCode) { this.toolTypeCode = toolTypeCode; }

  public LocalDateTime getSetupDateTime() { return setupDateTime; }
  public void setSetupDateTime(LocalDateTime setupDateTime) { this.setupDateTime = setupDateTime; }

  public String getToolId() { return toolId; }
  public void setToolId(String toolId) { this.toolId = toolId; }

  public String getIrcs() { return ircs; }
  public void setIrcs(String ircs) { this.ircs = ircs; }

  public String getMmsi() { return mmsi; }
  public void setMmsi(String mmsi) { this.mmsi = mmsi; }

  public String getImo() { return imo; }
  public void setImo(String imo) { this.imo = imo; }

  public String getVesselEmail() { return vesselEmail; }
  public void setVesselEmail(String vesselEmail) { this.vesselEmail = vesselEmail; }

  public String getToolTypeName() { return toolTypeName; }
  public void setToolTypeName(String toolTypeName) { this.toolTypeName = toolTypeName; }

  public String getToolColor() { return toolColor; }
  public void setToolColor(String toolColor) { this.toolColor = toolColor; }

  public String getSource() { return source; }
  public void setSource(String source) { this.source = source; }

  public String getComment() { return comment; }
  public void setComment(String comment) { this.comment = comment; }

  public LocalDateTime getRemovedDateTime() { return removedDateTime; }
  public void setRemovedDateTime(LocalDateTime removedDateTime) { this.removedDateTime = removedDateTime; }

  public LocalDateTime getLastChangedDateTime() { return lastChangedDateTime; }
  public void setLastChangedDateTime(LocalDateTime lastChangedDateTime) { this.lastChangedDateTime = lastChangedDateTime; }

  public String getLastChangedBySource() { return lastChangedBySource; }
  public void setLastChangedBySource(String lastChangedBySource) { this.lastChangedBySource = lastChangedBySource; }

  public String getRegNum() { return regNum; }
  public void setRegNum(String regNum) { this.regNum = regNum; }

  public String getSbrRegNum() { return sbrRegNum; }
  public void setSbrRegNum(String sbrRegNum) { this.sbrRegNum = sbrRegNum; }

  public LocalDateTime getSetupProcessedTime() { return setupProcessedTime; }
  public void setSetupProcessedTime(LocalDateTime setupProcessedTime) { this.setupProcessedTime = setupProcessedTime; }

  public LocalDateTime getRemovedProcessedTime() { return removedProcessedTime; }
  public void setRemovedProcessedTime(LocalDateTime removedProcessedTime) { this.removedProcessedTime = removedProcessedTime; }

  public Integer getToolCount() { return toolCount; }
  public void setToolCount(Integer toolCount) { this.toolCount = toolCount; }

  @Override
  public String toString() {
    return "GeoJsonFeature{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", bbox='" + bbox + '\'' +
            ", geometry='" + geometry + '\'' +
            ", version=" + version +
            '}';
  }

}

package no.ntnu.gr10.bachelorgrpcapi.fishingfacility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import no.ntnu.gr10.bachelorgrpcapi.company.Company;


/**
 * Represents a fishing facility associated with a company.
 *
 * <p>Maps to the "fishing_facility" table in the database and contains
 * metadata such as setup and removal timestamps, tool information,
 * spatial start point, path geometry, and tracking of changes.
 * </p>
 *
 * @author Daniel Neset
 * @version 06.05.2025
 */
@Getter
@Setter
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

  /**
   * Default constructor for JPA.
   */
  public FishingFacility() {
    //Default JPA method
  }

  /**
   * Set the company associated with this fishing facility.
   *
   * @param company the company to set
   * @throws IllegalArgumentException if the company is null
   */
  public void setCompany(Company company) {
    if (company == null) {
      throw new IllegalArgumentException("Company cannot be null");
    }
    this.company = company;
  }


  @Override
  public String toString() {
    return "GeoJsonFeature{"
            + "id=" + id
            + ", type='" + type + '\''
            + ", bbox='" + bbox + '\''
            + ", geometry='" + geometry + '\''
            + ", version=" + version
            + '}';
  }

}

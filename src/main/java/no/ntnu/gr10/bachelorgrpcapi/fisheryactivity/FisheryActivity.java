package no.ntnu.gr10.bachelorgrpcapi.fisheryactivity;

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
 * Represents a fishery activity associated with a company.
 *
 * <p>Maps to the "fishery_activities" table in the database and contains
 * metadata such as setup and removal timestamps, tool information,
 * spatial start point, path geometry, and tracking of changes.
 * </p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
@Getter
@Entity
@Table(name = "fishery_activities")
public class FisheryActivity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @Setter
  @Column(name = "setup_date_time", nullable = false)
  private LocalDateTime setupDateTime;

  @Setter
  @Column(name = "tool_type_code", length = 50, nullable = false)
  private String toolTypeCode;

  @Setter
  @Column(name = "tool_type_name", length = 100, nullable = false)
  private String toolTypeName;

  @Setter
  @Column(name = "tool_id", length = 36, nullable = false)
  private String toolId;

  @Setter
  @Column(name = "removed_date_time")
  private LocalDateTime removedDateTime;

  @Setter
  @Column(name = "last_changed_date_time")
  private LocalDateTime lastChangedDateTime;

  @Setter
  @Column(name = "starting_point_lat", nullable = false)
  private Double startingPointLat;

  @Setter
  @Column(name = "starting_point_lon", nullable = false)
  private Double startingPointLon;

  @Setter
  @Column(name = "length")
  private Double length;

  @Setter
  @Column(name = "geometry", columnDefinition = "TEXT")
  private String geometry;

  /**
   * Default constructor for JPA.
   */
  public FisheryActivity() {
    // Default constructor for JPA
  }

  /**
   * Sets the company associated with this fishery activity.
   *
   * @param company The company to set
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
    return "FisheryActivity{"
            + "id=" + id
            + ", company=" + company.getId()
            + ", setupDateTime=" + setupDateTime
            + ", toolTypeCode='" + toolTypeCode + '\''
            + ", toolTypeName='" + toolTypeName + '\''
            + ", toolId='" + toolId + '\''
            + ", removedDateTime=" + removedDateTime
            + ", lastChangedDateTime=" + lastChangedDateTime
            + ", startingPointLat=" + startingPointLat
            + ", startingPointLon=" + startingPointLon
            + ", length=" + length
            + ", geometry='" + geometry + '\''
            + '}';
  }
}
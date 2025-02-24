package org.kgromov.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"name", "population"})
@Builder
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    public Long id;

    @Column(name = "Name")
    public String name;

    @Column(name = "District")
    public String district;
    @Column(name = "Population")
    public Long population;

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CountryCode",
            referencedColumnName = "Code",
            foreignKey = @ForeignKey(name = "city_ibfk_1"),
            nullable = false
    )
    // aka @JsonView hardcoded - so all columns from CountryEntity are loaded and then filtered
    @JsonIgnoreProperties({"name", "code", "capital", "population", "area"})
    public Country country;
}

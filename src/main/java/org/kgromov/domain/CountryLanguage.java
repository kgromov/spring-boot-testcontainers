package org.kgromov.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.TrueFalseConverter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"languageCode"})
@ToString(of = {"languageCode"})
@Builder
@Entity
@Table(name = "countrylanguage")
public class CountryLanguage {
    // TODO: seems @IdClass better suits here
    @EmbeddedId
    private LanguageCode languageCode;

    @Column(name = "IsOfficial")
    @Convert(converter = TrueFalseConverter.class)
    private Boolean official;

    @Column(name = "Percentage")
    private BigDecimal usage;

    protected CountryLanguage() {
    }

    public LanguageCode getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(LanguageCode languageCode) {
        this.languageCode = languageCode;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public BigDecimal getUsage() {
        return usage;
    }

    public void setUsage(BigDecimal usage) {
        this.usage = usage;
    }
}

package org.kgromov.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
@Embeddable
public class LanguageCode implements Serializable {

    @Column(name ="CountryCode")
    private String code;
    @Column(name = "Language")
    private String language;

    protected LanguageCode() {
    }

    public LanguageCode(String code, String language) {
        this.code = code;
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public String getLanguage() {
        return language;
    }
}

package com.zarebin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Iranian license plate split into its four official parts, stored as one
 * column each. Traveled over the API as the structured PlateNumberDto;
 * toString() flattens to "12 345 ب 67" only where plain text is needed
 * (the python PDF request and the local PDF renderer).
 */
@Embeddable
public class PlateNumber {

    @Column(name = "plate_first_two", nullable = false, length = 2)
    private String firstTwo;      // 12

    @Column(name = "plate_letter", nullable = false, length = 1)
    private String letter;        // ب

    @Column(name = "plate_three", nullable = false, length = 3)
    private String threeDigits;   // 345

    @Column(name = "plate_iran_code", nullable = false, length = 2)
    private String iranCode;      // 67

    protected PlateNumber() {}

    public PlateNumber(String firstTwo, String letter, String threeDigits, String iranCode) {
        this.firstTwo = firstTwo;
        this.letter = letter;
        this.threeDigits = threeDigits;
        this.iranCode = iranCode;
    }

    public String getFirstTwo() {
        return firstTwo;
    }

    public String getLetter() {
        return letter;
    }

    public String getThreeDigits() {
        return threeDigits;
    }

    public String getIranCode() {
        return iranCode;
    }

    @Override
    public String toString() {
        return firstTwo + " " + threeDigits + " " + letter + " " + iranCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlateNumber that)) return false;
        return Objects.equals(firstTwo, that.firstTwo)
                && Objects.equals(letter, that.letter)
                && Objects.equals(threeDigits, that.threeDigits)
                && Objects.equals(iranCode, that.iranCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstTwo, letter, threeDigits, iranCode);
    }
}

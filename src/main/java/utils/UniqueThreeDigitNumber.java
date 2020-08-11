package utils;

import java.util.Objects;

public class UniqueThreeDigitNumber {

    private String  uniqueNumber;

    public UniqueThreeDigitNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqueThreeDigitNumber that = (UniqueThreeDigitNumber) o;
        return Objects.equals(uniqueNumber, that.uniqueNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueNumber);
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }
}

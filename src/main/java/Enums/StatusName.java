package Enums;

import javax.naming.Name;

public enum StatusName {
    NEW("NEW"),
    REJECTED("REJECTED"),
    IN_PROGRESS("IN_PROGRESS"),
    FIXED("FIXED"),
    INFO_NEEDED("INFO_NEEDED"),
    CLOSED("CLOSED");

    String status;
    StatusName(String aNew) {
        status = aNew;
    }
}

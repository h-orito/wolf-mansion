package com.ort.fw.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WolfMansionDateUtil {

    private WolfMansionDateUtil() {
    }

    public static LocalDateTime currentLocalDateTime() {
        return LocalDateTime.now(); // TODO h-orito 実装 (2017/12/14)
    }

    public static LocalDate currentLocalDate() {
        return LocalDate.now(); // TODO h-orito 実装 (2017/12/14)
    }
}

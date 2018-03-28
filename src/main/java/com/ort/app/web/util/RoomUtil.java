package com.ort.app.web.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomUtil {

    private RoomUtil() {
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public static Map<Integer, List<Integer>> createRoomAssignMap() {
        // 8-10 4x3 11 5x3 12-13 4x4 14-17 5x4 18-20 6x4
        // 8    9    10   11   
        // _xx_ _xx_ xxx_ _xxx_
        // xxxx xxxx xxxx xxxxx
        // _xx_ _xxx _xxx _xxx_
        // 
        // 12   13   14    15    16    17   
        // _xx_ _xx_ __xx_ __xx_ _xxx_ _xxx_
        // xxxx xxxx xxxxx xxxxx xxxxx xxxxx
        // xxxx xxxx xxxxx xxxxx xxxxx xxxxx
        // _xx_ _xxx _xx__ _xxx_ _xxx_ _xxxx
        //
        // 18     19     20
        // __xxx_ _xxxx_ _xxxx_
        // xxxxxx xxxxxx xxxxxx
        // xxxxxx xxxxxx xxxxxx
        // _xxx__ _xxx__ _xxxx_

        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(8, createPerson08AssignMap());
        map.put(9, createPerson09AssignMap());
        map.put(10, createPerson10AssignMap());
        map.put(11, createPerson11AssignMap());
        map.put(12, createPerson12AssignMap());
        map.put(13, createPerson13AssignMap());
        map.put(14, createPerson14AssignMap());
        map.put(15, createPerson15AssignMap());
        map.put(16, createPerson16AssignMap());
        map.put(17, createPerson17AssignMap());
        map.put(18, createPerson18AssignMap());
        map.put(19, createPerson19AssignMap());
        map.put(20, createPerson20AssignMap());
        return map;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private static List<Integer> createPerson08AssignMap() {
        return Arrays.asList(2, 3, 5, 6, 7, 8, 10, 11);
    }

    private static List<Integer> createPerson09AssignMap() {
        return Arrays.asList(2, 3, 5, 6, 7, 8, 10, 11, 12);
    }

    private static List<Integer> createPerson10AssignMap() {
        return Arrays.asList(1, 2, 3, 5, 6, 7, 8, 10, 11, 12);
    }

    private static List<Integer> createPerson11AssignMap() {
        return Arrays.asList(2, 3, 4, 6, 7, 8, 9, 10, 12, 13, 14);
    }

    private static List<Integer> createPerson12AssignMap() {
        return Arrays.asList(2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15);
    }

    private static List<Integer> createPerson13AssignMap() {
        return Arrays.asList(2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16);
    }

    private static List<Integer> createPerson14AssignMap() {
        return Arrays.asList(3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18);
    }

    private static List<Integer> createPerson15AssignMap() {
        return Arrays.asList(3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19);
    }

    private static List<Integer> createPerson16AssignMap() {
        return Arrays.asList(2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19);
    }

    private static List<Integer> createPerson17AssignMap() {
        return Arrays.asList(2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 20);
    }

    private static List<Integer> createPerson18AssignMap() {
        return Arrays.asList(3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22);
    }

    private static List<Integer> createPerson19AssignMap() {
        return Arrays.asList(2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22);
    }

    private static List<Integer> createPerson20AssignMap() {
        return Arrays.asList(2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22, 23);
    }
}

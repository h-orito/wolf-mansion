package com.ort.app.web.util;

import java.util.HashMap;
import java.util.Map;

public class RoomUtil {

    private RoomUtil() {
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public static Map<Integer, Map<Integer, Integer>> createRoomAssignMap() {
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

        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
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
    private static Map<Integer, Integer> createPerson08AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 5);
        map.put(4, 6);
        map.put(5, 7);
        map.put(6, 8);
        map.put(7, 10);
        map.put(8, 11);
        return map;
    }

    private static Map<Integer, Integer> createPerson09AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 5);
        map.put(4, 6);
        map.put(5, 7);
        map.put(6, 8);
        map.put(7, 10);
        map.put(8, 11);
        map.put(9, 12);
        return map;
    }

    private static Map<Integer, Integer> createPerson10AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 5);
        map.put(5, 6);
        map.put(6, 7);
        map.put(7, 8);
        map.put(8, 10);
        map.put(9, 11);
        map.put(10, 12);
        return map;
    }

    private static Map<Integer, Integer> createPerson11AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);
        map.put(4, 6);
        map.put(5, 7);
        map.put(6, 8);
        map.put(7, 9);
        map.put(8, 10);
        map.put(9, 12);
        map.put(10, 13);
        map.put(11, 14);
        return map;
    }

    private static Map<Integer, Integer> createPerson12AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 5);
        map.put(4, 6);
        map.put(5, 7);
        map.put(6, 8);
        map.put(7, 9);
        map.put(8, 10);
        map.put(9, 11);
        map.put(10, 12);
        map.put(11, 14);
        map.put(12, 15);
        return map;
    }

    private static Map<Integer, Integer> createPerson13AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 5);
        map.put(4, 6);
        map.put(5, 7);
        map.put(6, 8);
        map.put(7, 9);
        map.put(8, 10);
        map.put(9, 11);
        map.put(10, 12);
        map.put(11, 14);
        map.put(12, 15);
        map.put(13, 16);
        return map;
    }

    private static Map<Integer, Integer> createPerson14AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 3);
        map.put(2, 4);
        map.put(3, 6);
        map.put(4, 7);
        map.put(5, 8);
        map.put(6, 9);
        map.put(7, 10);
        map.put(8, 11);
        map.put(9, 12);
        map.put(10, 13);
        map.put(11, 14);
        map.put(12, 15);
        map.put(13, 17);
        map.put(14, 18);
        return map;
    }

    private static Map<Integer, Integer> createPerson15AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 3);
        map.put(2, 4);
        map.put(3, 6);
        map.put(4, 7);
        map.put(5, 8);
        map.put(6, 9);
        map.put(7, 10);
        map.put(8, 11);
        map.put(9, 12);
        map.put(10, 13);
        map.put(11, 14);
        map.put(12, 15);
        map.put(13, 17);
        map.put(14, 18);
        map.put(15, 19);
        return map;
    }

    private static Map<Integer, Integer> createPerson16AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);
        map.put(4, 6);
        map.put(5, 7);
        map.put(6, 8);
        map.put(7, 9);
        map.put(8, 10);
        map.put(9, 11);
        map.put(10, 12);
        map.put(11, 13);
        map.put(12, 14);
        map.put(13, 15);
        map.put(14, 17);
        map.put(15, 18);
        map.put(16, 19);
        return map;
    }

    private static Map<Integer, Integer> createPerson17AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);
        map.put(4, 6);
        map.put(5, 7);
        map.put(6, 8);
        map.put(7, 9);
        map.put(8, 10);
        map.put(9, 11);
        map.put(10, 12);
        map.put(11, 13);
        map.put(12, 14);
        map.put(13, 15);
        map.put(14, 17);
        map.put(15, 18);
        map.put(16, 19);
        map.put(17, 20);
        return map;
    }

    private static Map<Integer, Integer> createPerson18AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 3);
        map.put(2, 4);
        map.put(3, 5);
        map.put(4, 7);
        map.put(5, 8);
        map.put(6, 9);
        map.put(7, 10);
        map.put(8, 11);
        map.put(9, 12);
        map.put(10, 13);
        map.put(11, 14);
        map.put(12, 15);
        map.put(13, 16);
        map.put(14, 17);
        map.put(15, 18);
        map.put(16, 20);
        map.put(17, 21);
        map.put(18, 22);
        return map;
    }

    private static Map<Integer, Integer> createPerson19AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);
        map.put(4, 5);
        map.put(5, 7);
        map.put(6, 8);
        map.put(7, 9);
        map.put(8, 10);
        map.put(9, 11);
        map.put(10, 12);
        map.put(11, 13);
        map.put(12, 14);
        map.put(13, 15);
        map.put(14, 16);
        map.put(15, 17);
        map.put(16, 18);
        map.put(17, 20);
        map.put(18, 21);
        map.put(19, 22);
        return map;
    }

    private static Map<Integer, Integer> createPerson20AssignMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);
        map.put(4, 5);
        map.put(5, 7);
        map.put(6, 8);
        map.put(7, 9);
        map.put(8, 10);
        map.put(9, 11);
        map.put(10, 12);
        map.put(11, 13);
        map.put(12, 14);
        map.put(13, 15);
        map.put(14, 16);
        map.put(15, 17);
        map.put(16, 18);
        map.put(17, 20);
        map.put(18, 21);
        map.put(19, 22);
        map.put(20, 23);
        return map;
    }
}

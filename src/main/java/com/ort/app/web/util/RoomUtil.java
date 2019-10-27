package com.ort.app.web.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoomUtil {

    private RoomUtil() {
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public static RoomSize getRoomSize(int personNum) {
        if (8 <= personNum && personNum <= 10) {
            return new RoomSize(4, 3);
        } else if (11 == personNum) {
            return new RoomSize(5, 3);
        } else if (12 <= personNum && personNum <= 13) {
            return new RoomSize(4, 4);
        } else if (14 <= personNum && personNum <= 17) {
            return new RoomSize(5, 4);
        } else if (18 <= personNum && personNum <= 20) {
            return new RoomSize(6, 4);
        }
        for (int width = 6; width <= 100; width++) {
            for (int height = width - 1; height <= width; height++) {
                if (width * height - 13 <= personNum && personNum <= width * height - 4) {
                    return new RoomSize(width, height);
                }
            }
        }
        return new RoomSize(1, 1);
    }

    public static List<Integer> getRoomNumberList(int personNum) {
        switch (personNum) {
        case 8:
            return createPerson08AssignMap();
        case 9:
            return createPerson09AssignMap();
        case 10:
            return createPerson10AssignMap();
        case 11:
            return createPerson11AssignMap();
        case 12:
            return createPerson12AssignMap();
        case 13:
            return createPerson13AssignMap();
        case 14:
            return createPerson14AssignMap();
        case 15:
            return createPerson15AssignMap();
        case 16:
            return createPerson16AssignMap();
        case 17:
            return createPerson17AssignMap();
        case 18:
            return createPerson18AssignMap();
        case 19:
            return createPerson19AssignMap();
        case 20:
            return createPerson20AssignMap();
        default:
            return createRoomNumberList(personNum);
        }
    }

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

    private static List<Integer> createRoomNumberList(int personNum) {
        RoomSize roomSize = getRoomSize(personNum);
        int width = roomSize.width;
        int height = roomSize.height;

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= width * height; i++) {
            list.add(i);
        }

        list = removeNum(list, 1);
        list = removeNum(list, width);
        list = removeNum(list, width * (height - 1) + 1);
        list = removeNum(list, width * height);

        if (list.size() <= personNum) {
            return list;
        }

        list = removeNum(list, width * height - 1);
        if (list.size() <= personNum) {
            return list;
        }

        list = removeNum(list, 2);
        if (list.size() <= personNum) {
            return list;
        }

        list = removeNum(list, width * (height - 1) + 2);
        if (list.size() <= personNum) {
            return list;
        }

        list = removeNum(list, width - 1);
        if (list.size() <= personNum) {
            return list;
        }

        list = removeNum(list, width * (height - 1));
        if (list.size() <= personNum) {
            return list;
        }

        list = removeNum(list, width + 1);
        if (list.size() <= personNum) {
            return list;
        }

        list = removeNum(list, width * (height - 2) + 1);
        if (list.size() <= personNum) {
            return list;
        }

        list = removeNum(list, width * 2);
        if (list.size() <= personNum) {
            return list;
        }

        list = removeNum(list, 3);
        return list;
    }

    private static List<Integer> removeNum(List<Integer> list, int num) {
        return list.stream().filter(n -> n != num).collect(Collectors.toList());
    }
}

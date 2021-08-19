package com.ort.app.logic;

import com.ort.dbflute.exentity.Village;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RoomLogic {

    // 対象＋周辺8部屋の部屋番号（存在しない部屋番号を含んでいても良い）
    public List<Integer> detectAroundRoomNumber(Integer targetRoomNumber, Integer roomSizeWidth) {
        if (isLeftSide(targetRoomNumber, roomSizeWidth)) {
            return Arrays.asList(//
                    targetRoomNumber, // 対象の部屋
                    targetRoomNumber - roomSizeWidth, // 上
                    targetRoomNumber - roomSizeWidth + 1, // 右上
                    targetRoomNumber + 1, // 右
                    targetRoomNumber + roomSizeWidth, // 下
                    targetRoomNumber + roomSizeWidth + 1 // 右下
            );
        } else if (isRightSide(targetRoomNumber, roomSizeWidth)) {
            return Arrays.asList(//
                    targetRoomNumber, // 対象の部屋
                    targetRoomNumber - roomSizeWidth - 1, // 左上
                    targetRoomNumber - roomSizeWidth, // 上
                    targetRoomNumber - 1, // 左
                    targetRoomNumber + roomSizeWidth - 1, // 左下
                    targetRoomNumber + roomSizeWidth // 下
            );
        } else {
            return Arrays.asList(//
                    targetRoomNumber, // 対象の部屋
                    targetRoomNumber - roomSizeWidth - 1, // 左上
                    targetRoomNumber - roomSizeWidth, // 上
                    targetRoomNumber - roomSizeWidth + 1, // 右上
                    targetRoomNumber - 1, // 左
                    targetRoomNumber + 1, // 右
                    targetRoomNumber + roomSizeWidth - 1, // 左下
                    targetRoomNumber + roomSizeWidth, // 下
                    targetRoomNumber + roomSizeWidth + 1 // 右下
            );
        }
    }

    // 周辺4部屋の部屋番号（存在しない部屋番号を含んでいても良い）
    public List<Integer> detectWasdRoomNumber(Integer myRoomNumber, Integer roomSizeWidth) {
        if (isLeftSide(myRoomNumber, roomSizeWidth)) {
            return Arrays.asList(//
                    myRoomNumber - roomSizeWidth, // 上
                    myRoomNumber + 1, // 右
                    myRoomNumber + roomSizeWidth // 下
            );
        } else if (isRightSide(myRoomNumber, roomSizeWidth)) {
            return Arrays.asList(//
                    myRoomNumber - roomSizeWidth, // 上
                    myRoomNumber - 1, // 左
                    myRoomNumber + roomSizeWidth // 下
            );
        } else {
            return Arrays.asList(//
                    myRoomNumber - roomSizeWidth, // 上
                    myRoomNumber - 1, // 左
                    myRoomNumber + 1, // 右
                    myRoomNumber + roomSizeWidth // 下
            );
        }
    }

    // 対象を除く直線4方向の部屋番号（存在しない部屋番号を含んでいても良い）
    public List<Integer> detectHishaRoomNumber(Integer targetRoomNumber, Village village) {
        int width = village.getRoomSizeWidth();
        int height = village.getRoomSizeHeight();
        List<Integer> roomNumberList = new ArrayList<>();

        // 上
        int tempRoomNumber = targetRoomNumber - width;
        while (tempRoomNumber > 0) {
            roomNumberList.add(tempRoomNumber);
            tempRoomNumber -= width;
        }

        // 右
        tempRoomNumber = targetRoomNumber;
        while (!isRightSide(tempRoomNumber, width)) {
            tempRoomNumber += 1;
            roomNumberList.add(tempRoomNumber);
        }

        // 下
        tempRoomNumber = targetRoomNumber + width;
        while (tempRoomNumber <= width * height) {
            roomNumberList.add(tempRoomNumber);
            tempRoomNumber += width;
        }

        // 左
        tempRoomNumber = targetRoomNumber;
        while (!isLeftSide(tempRoomNumber, width)) {
            tempRoomNumber -= 1;
            roomNumberList.add(tempRoomNumber);
        }

        return roomNumberList;
    }

    // 対象を除く斜め4方向の部屋番号（存在しない部屋番号を含んでいても良い）
    public List<Integer> detectKakuRoomNumber(Integer targetRoomNumber, Village village) {
        int width = village.getRoomSizeWidth();
        int height = village.getRoomSizeHeight();
        List<Integer> roomNumberList = new ArrayList<>();

        // 右上
        int tempRoomNumber = targetRoomNumber - width + 1;
        while (
                tempRoomNumber > 0 && !isRightSide(tempRoomNumber + width - 1, width)
        ) {
            roomNumberList.add(tempRoomNumber);
            tempRoomNumber = tempRoomNumber - width + 1;
        }

        // 右下
        tempRoomNumber = targetRoomNumber + width + 1;
        while (
                !isRightSide(tempRoomNumber - width - 1, width) && targetRoomNumber <= width * height
        ) {
            roomNumberList.add(tempRoomNumber);
            tempRoomNumber = tempRoomNumber + width + 1;
        }

        // 左下
        tempRoomNumber = targetRoomNumber + width - 1;
        while (
                !isLeftSide(tempRoomNumber - width + 1, width) && tempRoomNumber <= width * height
        ) {
            roomNumberList.add(tempRoomNumber);
            tempRoomNumber = tempRoomNumber + width - 1;
        }

        // 左上
        tempRoomNumber = targetRoomNumber - width - 1;
        while (
                !isLeftSide(tempRoomNumber + width + 1, width) && tempRoomNumber > 0
        ) {
            roomNumberList.add(tempRoomNumber);
            tempRoomNumber = tempRoomNumber - width - 1;
        }

        return roomNumberList;
    }


    private boolean isLeftSide(int targetRoomNumber, int width) {
        return targetRoomNumber % width == 1;
    }

    private boolean isRightSide(int targetRoomNumber, int width) {
        return targetRoomNumber % width == 0;
    }
}

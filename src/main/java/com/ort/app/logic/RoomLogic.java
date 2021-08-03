package com.ort.app.logic;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

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

    private boolean isLeftSide(int targetRoomNumber, int width) {
        return targetRoomNumber % width == 1;
    }

    private boolean isRightSide(int targetRoomNumber, int width) {
        return targetRoomNumber % width == 0;
    }
}

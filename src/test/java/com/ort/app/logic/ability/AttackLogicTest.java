package com.ort.app.logic.ability;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ort.TestAssist;
import com.ort.WerewolfMansionTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AttackLogicTest extends WerewolfMansionTest {

    @Autowired
    private TestAssist assist;
    @Autowired
    private AttackLogic attackLogic;

    @Test
    public void test_attack() {
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
    }
}

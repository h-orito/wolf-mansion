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
    private AttackLogic logic;

    @Test
    public void test_attack_襲撃成功() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_attack_襲撃失敗_処刑済み() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_attack_襲撃失敗_護衛() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_attack_襲撃失敗_妖狐() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_attack_襲撃失敗_同棲() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_attack_襲撃成功_同棲ありだが相方が処刑死() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_attack_襲撃なし() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_attack_智狼襲撃成功_同棲により両方死亡() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_insertDefaultAttack() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_insertDefaultAttack_人狼が全滅() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_serAbility() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_serAbility_襲撃担当がおかしい() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_serAbility_襲撃先がおかしい() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_serAbility_足音がおかしい() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_getSelectableTarget_1日目() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_getSelectableTarget_2日目() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_getSelectableTarget_対象なし() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_getAttackableWolfs_連続襲撃可能() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_getAttackableWolfs_連続襲撃不可_2狼生存() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_getAttackableWolfs_連続襲撃不可_1狼生存() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_makeSkillHistoryList_1日目() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    @Test
    public void test_makeSkillHistoryList_3日目() {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }
}

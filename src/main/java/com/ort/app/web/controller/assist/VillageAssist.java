package com.ort.app.web.controller.assist;

import java.util.List;
import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.model.VillageMessageListResultContent;
import com.ort.app.web.model.VillageResultContent;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.Village;

@Component
public class VillageAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    VillageBhv villageBhv;

    @Autowired
    VillageDayBhv villageDayBhv;

    @Autowired
    MessageBhv messageBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void setIndexModel(Integer villageId, Model model) {
        Village village = selectVillage(villageId);
        ListResultBean<Message> messageList = selectMessageList(villageId, 0);

        VillageResultContent content = mappingToContent(village, messageList);
        model.addAttribute("content", content);
    }

    public VillageMessageListResultContent getMessageList(VillageGetMessageListForm form) {
        Integer villageId = form.getVillageId();
        int day = form.getDay() != null ? form.getDay() : selectLatestDay(villageId);
        ListResultBean<Message> messageList = selectMessageList(form.getVillageId(), day);
        VillageMessageListResultContent content = mappingToMessageListContent(messageList);
        return content;
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private Village selectVillage(Integer villageId) {
        return villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
    }

    private ListResultBean<Message> selectMessageList(Integer villageId, Integer day) {
        return messageBhv.selectList(cb -> {
            cb.setupSelect_VillagePlayer().withPlayer();
            cb.setupSelect_VillagePlayer().withChara();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().addOrderBy_MessageDatetime_Asc();
            cb.query().addOrderBy_MessageId_Asc();
        });
    }

    private int selectLatestDay(Integer villageId) {
        return villageDayBhv.selectScalar(Integer.class).max(cb -> {
            cb.specify().columnDay();
            cb.query().setVillageId_Equal(villageId);
        }).get();
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private VillageResultContent mappingToContent(Village village, ListResultBean<Message> messageList) {
        VillageResultContent content = new VillageResultContent();
        content.setVillageId(village.getVillageId());
        content.setVillageName(village.getVillageDisplayName());
        content.setMessageList(convertToMessageList(messageList));
        return content;
    }

    private VillageMessageListResultContent mappingToMessageListContent(ListResultBean<Message> messageList) {
        VillageMessageListResultContent content = new VillageMessageListResultContent();
        content.setMessageList(convertToMessageList(messageList));
        return content;
    }

    private List<VillageMessageDto> convertToMessageList(List<Message> messageList) {
        return messageList.stream().map(message -> {
            VillageMessageDto messageDto = new VillageMessageDto();
            messageDto.setCharacterName(
                    message.getVillagePlayer().map(villagePlayer -> villagePlayer.getChara().get().getCharaName()).orElse(null));
            messageDto.setCharacterImageUrl(
                    message.getVillagePlayer().map(villagePlayer -> villagePlayer.getChara().get().getCharaImgUrl()).orElse(null));
            // エピ入ってないと表示しちゃだめ
            // messageDto.setPlayerName(
            //         message.getVillagePlayer().map(villagePlayer -> villagePlayer.getPlayer().get().getPlayerName()).orElse(null));
            messageDto.setMessageContent(message.getMessageContent());
            messageDto.setMessageDatetime(message.getMessageDatetime());
            messageDto.setMessageNumber(message.getMessageNumber());
            messageDto.setMessageType(message.getMessageTypeCodeAsMessageType().code());
            return messageDto;
        }).collect(Collectors.toList());
    }
}

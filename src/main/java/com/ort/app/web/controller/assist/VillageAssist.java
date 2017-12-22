package com.ort.app.web.controller.assist;

import java.util.List;
import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.ort.app.web.model.VillageResultContent;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exbhv.VillageBhv;
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
    MessageBhv messageBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void setIndexModel(Integer villageId, Model model) {
        Village village = selectVillage(villageId);
        ListResultBean<Message> messageList = selectMessageList(villageId);

        VillageResultContent content = mappingToContent(village, messageList);
        model.addAttribute("content", content);
    }

    private Village selectVillage(Integer villageId) {
        return villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<Message> selectMessageList(Integer villageId) {
        return messageBhv.selectList(cb -> {
            cb.setupSelect_VillagePlayer().withPlayer();
            cb.setupSelect_VillagePlayer().withChara();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(0); // TODO h-orito 日付設定 (2017/12/22)
            cb.query().addOrderBy_MessageDatetime_Asc();
            cb.query().addOrderBy_MessageId_Asc();
        });
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private VillageResultContent mappingToContent(Village village, ListResultBean<Message> messageList) {
        VillageResultContent content = new VillageResultContent();
        content.setVillageName(village.getVillageDisplayName());
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
            messageDto.setPlayerName(
                    message.getVillagePlayer().map(villagePlayer -> villagePlayer.getPlayer().get().getPlayerName()).orElse(null));
            messageDto.setMessageContent(message.getMessageContent());
            messageDto.setMessageDatetime(message.getMessageDatetime());
            messageDto.setMessageNumber(message.getMessageNumber());
            messageDto.setMessageType(message.getMessageTypeCodeAsMessageType());
            return messageDto;
        }).collect(Collectors.toList());
    }
}

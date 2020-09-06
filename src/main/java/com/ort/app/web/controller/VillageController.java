package com.ort.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ort.app.logic.DayChangeLogic;
import com.ort.app.web.controller.assist.VillageAbilityAssist;
import com.ort.app.web.controller.assist.VillageAssist;
import com.ort.app.web.controller.assist.VillageCommitAssist;
import com.ort.app.web.controller.assist.VillageListAssist;
import com.ort.app.web.controller.assist.VillageMessageAssist;
import com.ort.app.web.controller.assist.VillageParticipateAssist;
import com.ort.app.web.controller.assist.VillageSayAssist;
import com.ort.app.web.controller.assist.VillageSettingsAssist;
import com.ort.app.web.form.VillageAbilityForm;
import com.ort.app.web.form.VillageChangeRequestSkillForm;
import com.ort.app.web.form.VillageCommitForm;
import com.ort.app.web.form.VillageGetAnchorMessageForm;
import com.ort.app.web.form.VillageGetFootstepListForm;
import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.form.VillageSettingsForm;
import com.ort.app.web.form.VillageVoteForm;
import com.ort.app.web.form.validator.VillageParticipateFormValidator;
import com.ort.app.web.form.validator.VillageSayFormValidator;
import com.ort.app.web.form.validator.VillageSettingsFormValidator;
import com.ort.app.web.model.VillageAnchorMessageResultContent;
import com.ort.app.web.model.VillageGetFootstepListResultContent;
import com.ort.app.web.model.VillageLatestMessageDatetimeResultContent;
import com.ort.app.web.model.VillageMessageListResultContent;
import com.ort.app.web.model.VillageSayConfirmResultContent;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Controller
public class VillageController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageAssist villageAssist;
    @Autowired
    private VillageParticipateAssist villageParticipateAssist;
    @Autowired
    private VillageSayAssist villageSayAssist;
    @Autowired
    private VillageAbilityAssist villageAbilityAssist;
    @Autowired
    private VillageCommitAssist villageCommitAssist;
    @Autowired
    private VillageSettingsAssist villageSettingsAssist;
    @Autowired
    private VillageMessageAssist villageMessageAssist;
    @Autowired
    private VillageListAssist villageListAssist;
    @Autowired
    private DayChangeLogic dayChangeLogic;
    @Autowired
    private VillageSayFormValidator villageSayFormValidator;
    @Autowired
    private VillageParticipateFormValidator villageParticipateFormValidator;
    @Autowired
    private VillageSettingsFormValidator villageSettingsFormValidator;

    @InitBinder("sayForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(villageSayFormValidator);
    }

    @InitBinder("participateForm")
    public void initBinderParticipate(WebDataBinder binder) {
        binder.addValidators(villageParticipateFormValidator);
    }

    @InitBinder("settingsForm")
    public void initBinderChangeSettings(WebDataBinder binder) {
        binder.addValidators(villageSettingsFormValidator);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 村一覧初期表示
    @GetMapping("/village")
    private String villageListIndex(Model model) {
        return villageListAssist.setIndexModel(model);
    }

    // 村最新日付初期表示
    @GetMapping("/village/{villageId}")
    private String villageIndex(@PathVariable Integer villageId, Model model) {
        // 最新の日付を表示
        return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
    }

    // 村最新日付初期表示
    @GetMapping("/village/{villageId}/day/{day}")
    private String villageDayIndex(@PathVariable Integer villageId, @PathVariable Integer day, Model model) {
        return villageAssist.setIndexModel(villageId, day, null, null, null, model);
    }

    // 発言取得
    @GetMapping("/village/getMessageList")
    @ResponseBody
    private VillageMessageListResultContent getDayMessageList(VillageGetMessageListForm form) {
        // 最終アクセス日時を更新
        villageMessageAssist.updateLastAccessDatetime(form.getVillageId());
        // 更新時間が過ぎていたら日付更新
        dayChangeLogic.dayChangeIfNeeded(form.getVillageId());
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        return villageMessageAssist.getMessageList(form, userInfo);
    }

    // 最終発言時間取得
    @GetMapping("/village/getLatestMessageDatetime")
    @ResponseBody
    private VillageLatestMessageDatetimeResultContent getLatestMessageDatetime(VillageGetMessageListForm form) {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        return villageMessageAssist.getLatestMessageDatetime(form, userInfo);
    }

    // アンカー発言取得
    @GetMapping("/village/getAnchorMessage")
    @ResponseBody
    private VillageAnchorMessageResultContent getAnchorMessage(@Validated VillageGetAnchorMessageForm form, BindingResult result) {
        return villageMessageAssist.getAnchorMessage(form, result);
    }

    // 入村確認画面へ
    @PostMapping("/village/{villageId}/confirm-participate")
    private String confirmParticipate(@PathVariable Integer villageId,
            @Validated @ModelAttribute("participateForm") VillageParticipateForm participateForm, BindingResult result, Model model) {
        return villageParticipateAssist.setConfirmModel(villageId, participateForm, result, model);
    }

    // 入村
    @PostMapping("/village/{villageId}/participate")
    private String participate(@PathVariable Integer villageId,
            @Validated @ModelAttribute("participateForm") VillageParticipateForm participateForm, BindingResult result, Model model) {
        return villageParticipateAssist.participate(villageId, participateForm, result, model);
    }

    // 希望役職変更
    @PostMapping("/village/{villageId}/change-skill")
    private String changeSkill(@PathVariable Integer villageId,
            @Validated @ModelAttribute("changeRequestSkill") VillageChangeRequestSkillForm changeRequestSkillForm, BindingResult result,
            Model model) {
        return villageParticipateAssist.changeRequestSkill(villageId, changeRequestSkillForm, result, model);
    }

    // 退村
    @PostMapping("/village/{villageId}/leave")
    private String leave(@PathVariable Integer villageId, Model model) {
        return villageParticipateAssist.leave(villageId, model);
    }

    // 発言確認画面へ
    @PostMapping("/village/{villageId}/confirm")
    @ResponseBody
    private VillageSayConfirmResultContent confirm(@PathVariable Integer villageId,
            @Validated @ModelAttribute("sayForm") VillageSayForm sayForm, BindingResult result, Model model) {
        return villageSayAssist.sayConfirm(villageId, sayForm, result, model);
    }

    // 発言する
    @PostMapping("/village/{villageId}/say")
    private String say(@PathVariable Integer villageId, @Validated @ModelAttribute("sayForm") VillageSayForm sayForm, BindingResult result,
            Model model) {
        return villageSayAssist.say(villageId, sayForm, result, model);
    }

    // 能力セットする
    @PostMapping("/village/{villageId}/setAbility")
    private String setAbility(@PathVariable Integer villageId, @Validated @ModelAttribute("abilityForm") VillageAbilityForm abilityForm,
            BindingResult result, Model model) {
        return villageAbilityAssist.setAbility(villageId, abilityForm, result, model);
    }

    // 投票セットする
    @PostMapping("/village/{villageId}/setVote")
    private String setVote(@PathVariable Integer villageId, @Validated @ModelAttribute("voteForm") VillageVoteForm voteForm,
            BindingResult result, Model model) {
        return villageAbilityAssist.setVote(villageId, voteForm, result, model);
    }

    // コミットする
    @PostMapping("/village/{villageId}/commit")
    private String setCommit(@PathVariable Integer villageId, @Validated @ModelAttribute("commitForm") VillageCommitForm commitForm,
            BindingResult result, Model model) {
        return villageCommitAssist.setCommit(villageId, commitForm, result, model);
    }

    // 足音候補取得
    @GetMapping("/village/getFootstepList")
    @ResponseBody
    private VillageGetFootstepListResultContent getFootstepList(@Validated VillageGetFootstepListForm form, BindingResult result) {
        return villageAbilityAssist.getFootstepList(form, result);
    }

    // 村設定変更
    @GetMapping("/village/{villageId}/settings")
    private String settingsIndex(@PathVariable Integer villageId, Model model) {
        return villageSettingsAssist.index(villageId, model);
    }

    // 村設定変更
    @PostMapping("/village/{villageId}/settings")
    private String storeSettings(@PathVariable Integer villageId, @Validated @ModelAttribute("settingsForm") VillageSettingsForm form,
            BindingResult bindingResult, Model model) {
        return villageSettingsAssist.updateSettings(villageId, form, bindingResult, model);
    }
}

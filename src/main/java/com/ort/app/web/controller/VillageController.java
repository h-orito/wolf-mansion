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
import com.ort.app.web.controller.assist.VillageActionAssist;
import com.ort.app.web.controller.assist.VillageAssist;
import com.ort.app.web.controller.assist.VillageCommitAssist;
import com.ort.app.web.controller.assist.VillageListAssist;
import com.ort.app.web.controller.assist.VillageMessageAssist;
import com.ort.app.web.controller.assist.VillageParticipateAssist;
import com.ort.app.web.controller.assist.VillageRpAssist;
import com.ort.app.web.controller.assist.VillageSayAssist;
import com.ort.app.web.controller.assist.VillageSettingsAssist;
import com.ort.app.web.controller.assist.impl.VillageForms;
import com.ort.app.web.form.VillageAbilityForm;
import com.ort.app.web.form.VillageActionForm;
import com.ort.app.web.form.VillageChangeNameForm;
import com.ort.app.web.form.VillageChangeRequestSkillForm;
import com.ort.app.web.form.VillageCommitForm;
import com.ort.app.web.form.VillageGetAnchorMessageForm;
import com.ort.app.web.form.VillageGetFootstepListForm;
import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.form.VillageMemoForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.form.VillageSettingsForm;
import com.ort.app.web.form.VillageVoteForm;
import com.ort.app.web.form.validator.VillageActionFormValidator;
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
    private VillageActionAssist villageActionAssist;
    @Autowired
    private VillageRpAssist villageRpAssist;
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
    private VillageActionFormValidator villageActionFormValidator;
    @Autowired
    private VillageParticipateFormValidator villageParticipateFormValidator;
    @Autowired
    private VillageSettingsFormValidator villageSettingsFormValidator;

    @InitBinder("sayForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(villageSayFormValidator);
    }

    @InitBinder("actionForm")
    public void initBinderAction(WebDataBinder binder) {
        binder.addValidators(villageActionFormValidator);
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
    // ?????????????????????
    @GetMapping("/village")
    private String villageListIndex(Model model) {
        return villageListAssist.setIndexModel(model);
    }

    // ???????????????????????????
    @GetMapping("/village/{villageId}")
    private String villageIndex(@PathVariable Integer villageId, Model model) {
        // ????????????????????????
        return villageAssist.setIndexModelAndReturnView(villageId, VillageForms.empty(), model);
    }

    // ???????????????????????????
    @GetMapping("/village/{villageId}/day/{day}")
    private String villageDayIndex(@PathVariable Integer villageId, @PathVariable Integer day, Model model) {
        return villageAssist.setIndexModel(villageId, day, VillageForms.empty(), model);
    }

    // ????????????
    @GetMapping("/village/getMessageList")
    @ResponseBody
    private VillageMessageListResultContent getDayMessageList(VillageGetMessageListForm form) {
        // ?????????????????????????????????
        villageMessageAssist.updateLastAccessDatetime(form.getVillageId());
        // ?????????????????????????????????????????????
        dayChangeLogic.dayChangeIfNeeded(form.getVillageId());
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        return villageMessageAssist.getMessageList(form, userInfo);
    }

    // ????????????????????????
    @GetMapping("/village/getLatestMessageDatetime")
    @ResponseBody
    private VillageLatestMessageDatetimeResultContent getLatestMessageDatetime(VillageGetMessageListForm form) {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        return villageMessageAssist.getLatestMessageDatetime(form, userInfo);
    }

    // ????????????????????????
    @GetMapping("/village/getAnchorMessage")
    @ResponseBody
    private VillageAnchorMessageResultContent getAnchorMessage(@Validated VillageGetAnchorMessageForm form, BindingResult result) {
        return villageMessageAssist.getAnchorMessage(form, result);
    }

    // ?????????????????????
    @PostMapping("/village/{villageId}/confirm-participate")
    private String confirmParticipate(@PathVariable Integer villageId,
            @Validated @ModelAttribute("participateForm") VillageParticipateForm participateForm, BindingResult result, Model model) {
        return villageParticipateAssist.setConfirmModel(villageId, participateForm, result, model);
    }

    // ??????
    @PostMapping("/village/{villageId}/participate")
    private String participate(@PathVariable Integer villageId,
            @Validated @ModelAttribute("participateForm") VillageParticipateForm participateForm, BindingResult result, Model model) {
        return villageParticipateAssist.participate(villageId, participateForm, result, model);
    }

    // ??????????????????
    @PostMapping("/village/{villageId}/change-skill")
    private String changeSkill(@PathVariable Integer villageId,
            @Validated @ModelAttribute("changeRequestSkill") VillageChangeRequestSkillForm changeRequestSkillForm, BindingResult result,
            Model model) {
        return villageParticipateAssist.changeRequestSkill(villageId, changeRequestSkillForm, result, model);
    }

    // ??????
    @PostMapping("/village/{villageId}/leave")
    private String leave(@PathVariable Integer villageId, Model model) {
        return villageParticipateAssist.leave(villageId, model);
    }

    // ?????????????????????
    @PostMapping("/village/{villageId}/confirm")
    @ResponseBody
    private VillageSayConfirmResultContent confirm(@PathVariable Integer villageId,
            @Validated @ModelAttribute("sayForm") VillageSayForm sayForm, BindingResult result, Model model) {
        return villageSayAssist.sayConfirm(villageId, sayForm, result, model);
    }

    // ????????????
    @PostMapping("/village/{villageId}/say")
    private String say(@PathVariable Integer villageId, @Validated @ModelAttribute("sayForm") VillageSayForm sayForm, BindingResult result,
            Model model) {
        return villageSayAssist.say(villageId, sayForm, result, model);
    }

    // ??????????????????????????????
    @PostMapping("/village/{villageId}/action-confirm")
    @ResponseBody
    private VillageSayConfirmResultContent actionConfirm(@PathVariable Integer villageId,
            @Validated @ModelAttribute("actionForm") VillageActionForm actionForm, BindingResult result, Model model) {
        return villageActionAssist.actionConfirm(villageId, actionForm, result, model);
    }

    // ???????????????????????????
    @PostMapping("/village/{villageId}/action")
    private String action(@PathVariable Integer villageId, @Validated @ModelAttribute("actionForm") VillageActionForm actionForm,
            BindingResult result, Model model) {
        return villageActionAssist.action(villageId, actionForm, result, model);
    }

    // ?????????????????????
    @PostMapping("/village/{villageId}/change-name")
    private String changeName(@PathVariable Integer villageId,
            @Validated @ModelAttribute("changeNameForm") VillageChangeNameForm changeNameForm, BindingResult result, Model model) {
        return villageRpAssist.changeName(villageId, changeNameForm, result, model);
    }

    // ???????????????????????????
    @PostMapping("/village/{villageId}/memo")
    private String memo(@PathVariable Integer villageId, @Validated @ModelAttribute("changeNameForm") VillageMemoForm memoForm,
            BindingResult result, Model model) {
        return villageRpAssist.memo(villageId, memoForm, result, model);
    }

    // ?????????????????????
    @PostMapping("/village/{villageId}/setAbility")
    private String setAbility(@PathVariable Integer villageId, @Validated @ModelAttribute("abilityForm") VillageAbilityForm abilityForm,
            BindingResult result, Model model) {
        return villageAbilityAssist.setAbility(villageId, abilityForm, result, model);
    }

    // ?????????????????????
    @PostMapping("/village/{villageId}/setVote")
    private String setVote(@PathVariable Integer villageId, @Validated @ModelAttribute("voteForm") VillageVoteForm voteForm,
            BindingResult result, Model model) {
        return villageAbilityAssist.setVote(villageId, voteForm, result, model);
    }

    // ??????????????????
    @PostMapping("/village/{villageId}/commit")
    private String setCommit(@PathVariable Integer villageId, @Validated @ModelAttribute("commitForm") VillageCommitForm commitForm,
            BindingResult result, Model model) {
        return villageCommitAssist.setCommit(villageId, commitForm, result, model);
    }

    // ??????????????????
    @GetMapping("/village/getFootstepList")
    @ResponseBody
    private VillageGetFootstepListResultContent getFootstepList(@Validated VillageGetFootstepListForm form, BindingResult result) {
        return villageAbilityAssist.getFootstepList(form, result);
    }

    // ???????????????
    @GetMapping("/village/{villageId}/settings")
    private String settingsIndex(@PathVariable Integer villageId, Model model) {
        return villageSettingsAssist.index(villageId, model);
    }

    // ???????????????
    @PostMapping("/village/{villageId}/settings")
    private String storeSettings(@PathVariable Integer villageId, @Validated @ModelAttribute("settingsForm") VillageSettingsForm form,
            BindingResult bindingResult, Model model) {
        return villageSettingsAssist.updateSettings(villageId, form, bindingResult, model);
    }
}

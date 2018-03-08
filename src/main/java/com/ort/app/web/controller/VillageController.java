package com.ort.app.web.controller;

import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalThing;
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

import com.ort.app.web.controller.assist.VillageAssist;
import com.ort.app.web.controller.assist.VillageListAssist;
import com.ort.app.web.controller.assist.VillageMessageAssist;
import com.ort.app.web.controller.logic.AbilityLogic;
import com.ort.app.web.controller.logic.DayChangeLogic;
import com.ort.app.web.controller.logic.FootstepLogic;
import com.ort.app.web.controller.logic.MessageLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageAbilityForm;
import com.ort.app.web.form.VillageGetAnchorMessageForm;
import com.ort.app.web.form.VillageGetFootstepListForm;
import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.form.VillageVoteForm;
import com.ort.app.web.form.validator.VillageSayFormValidator;
import com.ort.app.web.model.VillageAnchorMessageResultContent;
import com.ort.app.web.model.VillageGetFootstepListResultContent;
import com.ort.app.web.model.VillageMessageListResultContent;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.MessageType;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.Vote;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Controller
public class VillageController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageAssist assist;

    @Autowired
    private VillageMessageAssist villageMessageAssist;

    @Autowired
    private VillageListAssist villageListAssist;

    @Autowired
    private DayChangeLogic dayChangeLogic;

    @Autowired
    private MessageLogic messageLogic;

    @Autowired
    private FootstepLogic footstepLogic;

    @Autowired
    private AbilityLogic abilityLogic;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private VoteBhv voteBhv;

    @Autowired
    private VillageSayFormValidator villageSayFormValidator;

    @InitBinder("sayForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(villageSayFormValidator);
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
        // 更新時間が過ぎていたら日付更新
        dayChangeLogic.dayChangeIfNeeded(villageId);

        // 最新の日付を表示
        return setIndexModelAndReturnView(villageId, null, model);
    }

    // 村最新日付初期表示
    @GetMapping("/village/{villageId}/day/{day}")
    private String villageDayIndex(@PathVariable Integer villageId, @PathVariable Integer day, Model model) {
        assist.setIndexModel(villageId, day, null, model);
        return "village";
    }

    // 発言取得
    @GetMapping("/village/getMessageList")
    @ResponseBody
    private VillageMessageListResultContent getDayMessageList(VillageGetMessageListForm form) {
        // 更新時間が過ぎていたら日付更新
        dayChangeLogic.dayChangeIfNeeded(form.getVillageId());
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();

        return villageMessageAssist.getMessageList(form, userInfo);
    }

    // アンカー発言取得
    @GetMapping("/village/getAnchorMessage")
    @ResponseBody
    private VillageAnchorMessageResultContent getAnchorMessage(@Validated VillageGetAnchorMessageForm form, BindingResult result) {
        if (result.hasErrors()) {
            return null;
        }
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (!villageMessageAssist.isViewAllowedMessage(form, userInfo)) {
            return null;
        }
        return villageMessageAssist.getAnchorMessage(form);
    }

    // 参戦
    @PostMapping("/village/{villageId}/participate")
    private String participate(@PathVariable Integer villageId,
            @Validated @ModelAttribute("participateForm") VillageParticipateForm participateForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, null, model);
        }
        // 既にそのキャラが参戦していたらNG
        try {
            assist.assertAlreadyParticipateChara(villageId, participateForm);
        } catch (WerewolfMansionBusinessException e) {
            model.addAttribute("participateErrorMessage", e.getMessage());
            return setIndexModelAndReturnView(villageId, null, model);
        }

        // 入村
        assist.participate(villageId, participateForm, userInfo);

        return "redirect:/village/" + villageId;
    }

    // 発言確認画面へ
    @PostMapping("/village/{villageId}/confirm")
    private String confirm(@PathVariable Integer villageId, @Validated @ModelAttribute("sayForm") VillageSayForm sayForm,
            BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, sayForm, model);
        }
        // 発言権利がなかったらNG
        if (!assist.isAvailableSay(villageId, userInfo, sayForm)) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, sayForm, model);
        }

        model.addAttribute("villageId", villageId);
        assist.setConfirmModel(villageId, model);
        // 発言確認画面へ
        return "say-confirm";
    }

    // 発言する
    @PostMapping("/village/{villageId}/say")
    private String say(@PathVariable Integer villageId, @Validated @ModelAttribute("sayForm") VillageSayForm sayForm, BindingResult result,
            Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, sayForm, model);
        }
        // 発言権利がなかったらNG
        if (!assist.isAvailableSay(villageId, userInfo, sayForm)) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, sayForm, model);
        }

        int day = assist.selectLatestDay(villageId);
        VillagePlayer villagePlayer = assist.selectVillagePlayer(villageId, userInfo).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type == null) {
            throw new IllegalArgumentException("発言種別改ざん");
        }

        messageLogic.insertMessage(villageId, day, type, sayForm.getMessage(), villagePlayer.getVillagePlayerId());
        return "redirect:/village/" + villageId;
    }

    // 能力セットする
    @PostMapping("/village/{villageId}/setAbility")
    private String setAbility(@PathVariable Integer villageId, @Validated @ModelAttribute("abilityForm") VillageAbilityForm abilityForm,
            BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, null, model);
        }
        VillagePlayer villagePlayer = assist.selectVillagePlayer(villageId, userInfo).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        if (isInvalidAbility(villagePlayer, abilityForm)) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, null, model);
        }
        int day = assist.selectLatestDay(villageId);
        abilityLogic.setAbility(villageId, villagePlayer, day, abilityForm.getCharaId(), abilityForm.getTargetCharaId(),
                abilityForm.getFootstep());
        return "redirect:/village/" + villageId;
    }

    // 投票セットする
    @PostMapping("/village/{villageId}/setVote")
    private String setVote(@PathVariable Integer villageId, @Validated @ModelAttribute("voteForm") VillageVoteForm voteForm,
            BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, null, model);
        }
        VillagePlayer villagePlayer = assist.selectVillagePlayer(villageId, userInfo).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        if (isInvalidVote(villageId, villagePlayer, voteForm)) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, null, model);
        }
        int day = assist.selectLatestDay(villageId);
        setVote(villageId, villagePlayer, day, villagePlayer.getCharaId(), voteForm.getTargetCharaId());
        return "redirect:/village/" + villageId;
    }

    // 足音候補取得
    @GetMapping("/village/getFootstepList")
    @ResponseBody
    private VillageGetFootstepListResultContent getFootstepList(@Validated VillageGetFootstepListForm form, BindingResult result) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            return null;
        }
        OptionalThing<VillagePlayer> optVillagePlayer = assist.selectVillagePlayer(form.getVillageId(), userInfo);
        if (!optVillagePlayer.isPresent()) {
            return null;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (isInvalidFootstep(vPlayer, form)) {
            return null;
        }
        int day = assist.selectLatestDay(form.getVillageId());
        List<String> footStepList =
                footstepLogic.getFootstepCandidateList(form.getVillageId(), vPlayer, day, form.getCharaId(), form.getTargetCharaId());
        VillageGetFootstepListResultContent response = new VillageGetFootstepListResultContent();
        response.setFootstepList(footStepList);
        return response;
    }

    // ===================================================================================
    //                                                                          Validation
    //                                                                          ==========
    private boolean isInvalidFootstep(VillagePlayer villagePlayer, VillageGetFootstepListForm form) {
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼 && skill != CDef.Skill.占い師) {
            return true;
        }
        if (skill == CDef.Skill.人狼 && form.getCharaId() == null) {
            return true;
        }
        if (skill == CDef.Skill.占い師 && form.getTargetCharaId() == null) {
            return true;
        }

        return false;
    }

    // ちゃんとしたチェックはロジック側でやる。必須チェックのみやる。
    private boolean isInvalidAbility(VillagePlayer villagePlayer, VillageAbilityForm abilityForm) {
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼 && skill != CDef.Skill.占い師 && skill != CDef.Skill.狩人 && skill != CDef.Skill.狂人
                && skill != CDef.Skill.妖狐) {
            return true;
        }
        Integer charaId = abilityForm.getCharaId();
        Integer targetCharaId = abilityForm.getTargetCharaId();
        String footstep = abilityForm.getFootstep();
        if (skill == CDef.Skill.人狼 && targetCharaId != null && (charaId == null || footstep == null)) {
            return true;
        }
        if (skill == CDef.Skill.占い師 && (targetCharaId == null || footstep == null)) {
            return true;
        }
        if (skill == CDef.Skill.狩人 && targetCharaId == null) {
            return true;
        }
        if ((skill == CDef.Skill.妖狐 || skill == CDef.Skill.狂人) && footstep == null) {
            return true;
        }
        if (villagePlayer.isIsDeadTrue()) {
            return true;
        }
        return false;
    }

    private boolean isInvalidVote(Integer villageId, VillagePlayer villagePlayer, VillageVoteForm voteForm) {
        List<VillagePlayer> villagePlayerList = selectVillagePlayerList(villageId);
        return !villagePlayerList.stream().anyMatch(vp -> vp.isIsDeadFalse() && vp.getCharaId().equals(voteForm.getTargetCharaId()));
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<VillagePlayer> selectVillagePlayerList(Integer villageId) {
        return villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
        });
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void setVote(Integer villageId, VillagePlayer villagePlayer, int day, Integer charaId, Integer targetCharaId) {
        Vote vote = new Vote();
        vote.setVillageId(villageId);
        vote.setDay(day);
        vote.setCharaId(charaId);
        vote.setVoteCharaId(targetCharaId);
        voteBhv.update(vote);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private String setIndexModelAndReturnView(Integer villageId, VillageSayForm sayForm, Model model) {
        // 最新の日付取得
        int day = assist.selectLatestDay(villageId);
        assist.setIndexModel(villageId, day, sayForm, model);
        return "village";
    }
}

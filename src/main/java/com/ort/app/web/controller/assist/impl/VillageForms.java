/**
 * 
 */
package com.ort.app.web.controller.assist.impl;

import com.ort.app.web.form.VillageActionForm;
import com.ort.app.web.form.VillageChangeNameForm;
import com.ort.app.web.form.VillageChangeRequestSkillForm;
import com.ort.app.web.form.VillageMemoForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.form.VillageSayForm;

/**
 * @author h-orito
 *
 */
public class VillageForms {

    public VillageSayForm sayForm;
    public VillageActionForm actionForm;
    public VillageParticipateForm participateForm;
    public VillageChangeRequestSkillForm changeRequestSkillForm;
    public VillageChangeNameForm changeNameForm;
    public VillageMemoForm memoForm;

    public static class Builder {

        private VillageSayForm sayForm;
        private VillageActionForm actionForm;
        private VillageParticipateForm participateForm;
        private VillageChangeRequestSkillForm changeRequestSkillForm;
        private VillageChangeNameForm changeNameForm;
        private VillageMemoForm memoForm;

        public Builder() {
        }

        public Builder sayForm(VillageSayForm form) {
            this.sayForm = form;
            return this;
        }

        public Builder actionForm(VillageActionForm form) {
            this.actionForm = form;
            return this;
        }

        public Builder participateForm(VillageParticipateForm form) {
            this.participateForm = form;
            return this;
        }

        public Builder changeRequestSkillForm(VillageChangeRequestSkillForm form) {
            this.changeRequestSkillForm = form;
            return this;
        }

        public Builder changeNameForm(VillageChangeNameForm form) {
            this.changeNameForm = form;
            return this;
        }

        public Builder memoForm(VillageMemoForm form) {
            this.memoForm = form;
            return this;
        }

        public VillageForms build() {
            return new VillageForms(this);
        }
    }

    public static VillageForms empty() {
        return new VillageForms();
    }

    private VillageForms() {
    }

    public VillageForms(Builder builder) {
        this.sayForm = builder.sayForm;
        this.actionForm = builder.actionForm;
        this.participateForm = builder.participateForm;
        this.changeRequestSkillForm = builder.changeRequestSkillForm;
        this.changeNameForm = builder.changeNameForm;
        this.memoForm = builder.memoForm;
    }
}

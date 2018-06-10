package com.ort.app.web.form;

import javax.validation.constraints.NotNull;

public class VillageCommitForm {

    /** コミットする/取り消す */
    @NotNull
    private Boolean commit;

    public Boolean getCommit() {
        return commit;
    }

    public void setCommit(Boolean commit) {
        this.commit = commit;
    }

}

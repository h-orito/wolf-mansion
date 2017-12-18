package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of CAMP as TABLE. <br>
 * <pre>
 * [primary key]
 *     CAMP_CODE
 *
 * [column]
 *     CAMP_CODE, CAMP_NAME
 *
 * [sequence]
 *     
 *
 * [identity]
 *     
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     
 *
 * [referrer table]
 *     SKILL
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     skillList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfCamp {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<Camp> _selectedList;
    protected BehaviorSelector _selector;
    protected CampBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfCamp ready(List<Camp> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected CampBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(CampBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<Skill> _referrerSkill;

    /**
     * Load referrer of skillList by the set-upper of referrer. <br>
     * SKILL by CAMP_CODE, named 'skillList'.
     * <pre>
     * <span style="color: #0000C0">campBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">campList</span>, <span style="color: #553000">campLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">campLoader</span>.<span style="color: #CC4747">loadSkill</span>(<span style="color: #553000">skillCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">skillCB</span>.setupSelect...
     *         <span style="color: #553000">skillCB</span>.query().set...
     *         <span style="color: #553000">skillCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">skillLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    skillLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (Camp camp : <span style="color: #553000">campList</span>) {
     *     ... = camp.<span style="color: #CC4747">getSkillList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setCampCode_InScope(pkList);
     * cb.query().addOrderBy_CampCode_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfSkill> loadSkill(ReferrerConditionSetupper<SkillCB> refCBLambda) {
        myBhv().loadSkill(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerSkill = refLs);
        return hd -> hd.handle(new LoaderOfSkill().ready(_referrerSkill, _selector));
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<Camp> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}

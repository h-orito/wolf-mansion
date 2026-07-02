package com.ort.dbflute.cbean.cq.bs;

import java.util.*;

import org.dbflute.cbean.*;
import org.dbflute.cbean.chelper.*;
import org.dbflute.cbean.ckey.*;
import org.dbflute.cbean.coption.*;
import org.dbflute.cbean.cvalue.ConditionValue;
import org.dbflute.cbean.ordering.*;
import org.dbflute.cbean.scoping.*;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.dbmeta.DBMetaProvider;
import com.ort.dbflute.allcommon.*;
import com.ort.dbflute.cbean.*;
import com.ort.dbflute.cbean.cq.*;

/**
 * The abstract condition-query of original_chara_image.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsOriginalCharaImageCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsOriginalCharaImageCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    @Override
    protected DBMetaProvider xgetDBMetaProvider() {
        return DBMetaInstanceHandler.getProvider();
    }

    public String asTableDbName() {
        return "original_chara_image";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaImageId The value of originalCharaImageId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaImageId_Equal(Integer originalCharaImageId) {
        doSetOriginalCharaImageId_Equal(originalCharaImageId);
    }

    protected void doSetOriginalCharaImageId_Equal(Integer originalCharaImageId) {
        regOriginalCharaImageId(CK_EQ, originalCharaImageId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaImageId The value of originalCharaImageId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaImageId_NotEqual(Integer originalCharaImageId) {
        doSetOriginalCharaImageId_NotEqual(originalCharaImageId);
    }

    protected void doSetOriginalCharaImageId_NotEqual(Integer originalCharaImageId) {
        regOriginalCharaImageId(CK_NES, originalCharaImageId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaImageId The value of originalCharaImageId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaImageId_GreaterThan(Integer originalCharaImageId) {
        regOriginalCharaImageId(CK_GT, originalCharaImageId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaImageId The value of originalCharaImageId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaImageId_LessThan(Integer originalCharaImageId) {
        regOriginalCharaImageId(CK_LT, originalCharaImageId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaImageId The value of originalCharaImageId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaImageId_GreaterEqual(Integer originalCharaImageId) {
        regOriginalCharaImageId(CK_GE, originalCharaImageId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaImageId The value of originalCharaImageId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaImageId_LessEqual(Integer originalCharaImageId) {
        regOriginalCharaImageId(CK_LE, originalCharaImageId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of originalCharaImageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of originalCharaImageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setOriginalCharaImageId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setOriginalCharaImageId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of originalCharaImageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of originalCharaImageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setOriginalCharaImageId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueOriginalCharaImageId(), "ORIGINAL_CHARA_IMAGE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaImageIdList The collection of originalCharaImageId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaImageId_InScope(Collection<Integer> originalCharaImageIdList) {
        doSetOriginalCharaImageId_InScope(originalCharaImageIdList);
    }

    protected void doSetOriginalCharaImageId_InScope(Collection<Integer> originalCharaImageIdList) {
        regINS(CK_INS, cTL(originalCharaImageIdList), xgetCValueOriginalCharaImageId(), "ORIGINAL_CHARA_IMAGE_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaImageIdList The collection of originalCharaImageId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaImageId_NotInScope(Collection<Integer> originalCharaImageIdList) {
        doSetOriginalCharaImageId_NotInScope(originalCharaImageIdList);
    }

    protected void doSetOriginalCharaImageId_NotInScope(Collection<Integer> originalCharaImageIdList) {
        regINS(CK_NINS, cTL(originalCharaImageIdList), xgetCValueOriginalCharaImageId(), "ORIGINAL_CHARA_IMAGE_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setOriginalCharaImageId_IsNull() { regOriginalCharaImageId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setOriginalCharaImageId_IsNotNull() { regOriginalCharaImageId(CK_ISNN, DOBJ); }

    protected void regOriginalCharaImageId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueOriginalCharaImageId(), "ORIGINAL_CHARA_IMAGE_ID"); }
    protected abstract ConditionValue xgetCValueOriginalCharaImageId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @param originalCharaId The value of originalCharaId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_Equal(Integer originalCharaId) {
        doSetOriginalCharaId_Equal(originalCharaId);
    }

    protected void doSetOriginalCharaId_Equal(Integer originalCharaId) {
        regOriginalCharaId(CK_EQ, originalCharaId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @param originalCharaId The value of originalCharaId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_NotEqual(Integer originalCharaId) {
        doSetOriginalCharaId_NotEqual(originalCharaId);
    }

    protected void doSetOriginalCharaId_NotEqual(Integer originalCharaId) {
        regOriginalCharaId(CK_NES, originalCharaId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @param originalCharaId The value of originalCharaId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_GreaterThan(Integer originalCharaId) {
        regOriginalCharaId(CK_GT, originalCharaId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @param originalCharaId The value of originalCharaId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_LessThan(Integer originalCharaId) {
        regOriginalCharaId(CK_LT, originalCharaId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @param originalCharaId The value of originalCharaId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_GreaterEqual(Integer originalCharaId) {
        regOriginalCharaId(CK_GE, originalCharaId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @param originalCharaId The value of originalCharaId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_LessEqual(Integer originalCharaId) {
        regOriginalCharaId(CK_LE, originalCharaId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @param minNumber The min number of originalCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of originalCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setOriginalCharaId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setOriginalCharaId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @param minNumber The min number of originalCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of originalCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setOriginalCharaId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueOriginalCharaId(), "ORIGINAL_CHARA_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @param originalCharaIdList The collection of originalCharaId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaId_InScope(Collection<Integer> originalCharaIdList) {
        doSetOriginalCharaId_InScope(originalCharaIdList);
    }

    protected void doSetOriginalCharaId_InScope(Collection<Integer> originalCharaIdList) {
        regINS(CK_INS, cTL(originalCharaIdList), xgetCValueOriginalCharaId(), "ORIGINAL_CHARA_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @param originalCharaIdList The collection of originalCharaId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaId_NotInScope(Collection<Integer> originalCharaIdList) {
        doSetOriginalCharaId_NotInScope(originalCharaIdList);
    }

    protected void doSetOriginalCharaId_NotInScope(Collection<Integer> originalCharaIdList) {
        regINS(CK_NINS, cTL(originalCharaIdList), xgetCValueOriginalCharaId(), "ORIGINAL_CHARA_ID");
    }

    protected void regOriginalCharaId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueOriginalCharaId(), "ORIGINAL_CHARA_ID"); }
    protected abstract ConditionValue xgetCValueOriginalCharaId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param faceTypeName The value of faceTypeName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFaceTypeName_Equal(String faceTypeName) {
        doSetFaceTypeName_Equal(fRES(faceTypeName));
    }

    protected void doSetFaceTypeName_Equal(String faceTypeName) {
        regFaceTypeName(CK_EQ, faceTypeName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param faceTypeName The value of faceTypeName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFaceTypeName_NotEqual(String faceTypeName) {
        doSetFaceTypeName_NotEqual(fRES(faceTypeName));
    }

    protected void doSetFaceTypeName_NotEqual(String faceTypeName) {
        regFaceTypeName(CK_NES, faceTypeName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param faceTypeName The value of faceTypeName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFaceTypeName_GreaterThan(String faceTypeName) {
        regFaceTypeName(CK_GT, fRES(faceTypeName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param faceTypeName The value of faceTypeName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFaceTypeName_LessThan(String faceTypeName) {
        regFaceTypeName(CK_LT, fRES(faceTypeName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param faceTypeName The value of faceTypeName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFaceTypeName_GreaterEqual(String faceTypeName) {
        regFaceTypeName(CK_GE, fRES(faceTypeName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param faceTypeName The value of faceTypeName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFaceTypeName_LessEqual(String faceTypeName) {
        regFaceTypeName(CK_LE, fRES(faceTypeName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param faceTypeNameList The collection of faceTypeName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFaceTypeName_InScope(Collection<String> faceTypeNameList) {
        doSetFaceTypeName_InScope(faceTypeNameList);
    }

    protected void doSetFaceTypeName_InScope(Collection<String> faceTypeNameList) {
        regINS(CK_INS, cTL(faceTypeNameList), xgetCValueFaceTypeName(), "FACE_TYPE_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param faceTypeNameList The collection of faceTypeName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFaceTypeName_NotInScope(Collection<String> faceTypeNameList) {
        doSetFaceTypeName_NotInScope(faceTypeNameList);
    }

    protected void doSetFaceTypeName_NotInScope(Collection<String> faceTypeNameList) {
        regINS(CK_NINS, cTL(faceTypeNameList), xgetCValueFaceTypeName(), "FACE_TYPE_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setFaceTypeName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param faceTypeName The value of faceTypeName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setFaceTypeName_LikeSearch(String faceTypeName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setFaceTypeName_LikeSearch(faceTypeName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setFaceTypeName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param faceTypeName The value of faceTypeName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setFaceTypeName_LikeSearch(String faceTypeName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(faceTypeName), xgetCValueFaceTypeName(), "FACE_TYPE_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param faceTypeName The value of faceTypeName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setFaceTypeName_NotLikeSearch(String faceTypeName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setFaceTypeName_NotLikeSearch(faceTypeName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param faceTypeName The value of faceTypeName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setFaceTypeName_NotLikeSearch(String faceTypeName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(faceTypeName), xgetCValueFaceTypeName(), "FACE_TYPE_NAME", likeSearchOption);
    }

    protected void regFaceTypeName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueFaceTypeName(), "FACE_TYPE_NAME"); }
    protected abstract ConditionValue xgetCValueFaceTypeName();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @param charaImgUrl The value of charaImgUrl as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaImgUrl_Equal(String charaImgUrl) {
        doSetCharaImgUrl_Equal(fRES(charaImgUrl));
    }

    protected void doSetCharaImgUrl_Equal(String charaImgUrl) {
        regCharaImgUrl(CK_EQ, charaImgUrl);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @param charaImgUrl The value of charaImgUrl as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaImgUrl_NotEqual(String charaImgUrl) {
        doSetCharaImgUrl_NotEqual(fRES(charaImgUrl));
    }

    protected void doSetCharaImgUrl_NotEqual(String charaImgUrl) {
        regCharaImgUrl(CK_NES, charaImgUrl);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @param charaImgUrl The value of charaImgUrl as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaImgUrl_GreaterThan(String charaImgUrl) {
        regCharaImgUrl(CK_GT, fRES(charaImgUrl));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @param charaImgUrl The value of charaImgUrl as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaImgUrl_LessThan(String charaImgUrl) {
        regCharaImgUrl(CK_LT, fRES(charaImgUrl));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @param charaImgUrl The value of charaImgUrl as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaImgUrl_GreaterEqual(String charaImgUrl) {
        regCharaImgUrl(CK_GE, fRES(charaImgUrl));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @param charaImgUrl The value of charaImgUrl as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaImgUrl_LessEqual(String charaImgUrl) {
        regCharaImgUrl(CK_LE, fRES(charaImgUrl));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @param charaImgUrlList The collection of charaImgUrl as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaImgUrl_InScope(Collection<String> charaImgUrlList) {
        doSetCharaImgUrl_InScope(charaImgUrlList);
    }

    protected void doSetCharaImgUrl_InScope(Collection<String> charaImgUrlList) {
        regINS(CK_INS, cTL(charaImgUrlList), xgetCValueCharaImgUrl(), "CHARA_IMG_URL");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @param charaImgUrlList The collection of charaImgUrl as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaImgUrl_NotInScope(Collection<String> charaImgUrlList) {
        doSetCharaImgUrl_NotInScope(charaImgUrlList);
    }

    protected void doSetCharaImgUrl_NotInScope(Collection<String> charaImgUrlList) {
        regINS(CK_NINS, cTL(charaImgUrlList), xgetCValueCharaImgUrl(), "CHARA_IMG_URL");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)} <br>
     * <pre>e.g. setCharaImgUrl_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param charaImgUrl The value of charaImgUrl as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCharaImgUrl_LikeSearch(String charaImgUrl, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCharaImgUrl_LikeSearch(charaImgUrl, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)} <br>
     * <pre>e.g. setCharaImgUrl_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param charaImgUrl The value of charaImgUrl as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setCharaImgUrl_LikeSearch(String charaImgUrl, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(charaImgUrl), xgetCValueCharaImgUrl(), "CHARA_IMG_URL", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @param charaImgUrl The value of charaImgUrl as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCharaImgUrl_NotLikeSearch(String charaImgUrl, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCharaImgUrl_NotLikeSearch(charaImgUrl, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @param charaImgUrl The value of charaImgUrl as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setCharaImgUrl_NotLikeSearch(String charaImgUrl, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(charaImgUrl), xgetCValueCharaImgUrl(), "CHARA_IMG_URL", likeSearchOption);
    }

    protected void regCharaImgUrl(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueCharaImgUrl(), "CHARA_IMG_URL"); }
    protected abstract ConditionValue xgetCValueCharaImgUrl();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * IS_DISPLAY: {NotNull, BIT, classification=Flg}
     * @param isDisplay The value of isDisplay as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setIsDisplay_Equal(Boolean isDisplay) {
        regIsDisplay(CK_EQ, isDisplay);
    }

    /**
     * Equal(=). As Flg. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IS_DISPLAY: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setIsDisplay_Equal_AsFlg(CDef.Flg cdef) {
        doSetIsDisplay_Equal(cdef != null ? Boolean.valueOf(cdef.code()) : null);
    }

    /**
     * Equal(=). As True. And OnlyOnceRegistered. <br>
     * はい: 有効を示す
     */
    public void setIsDisplay_Equal_True() {
        doSetIsDisplay_Equal(Boolean.valueOf(CDef.Flg.True.code()));
    }

    /**
     * Equal(=). As False. And OnlyOnceRegistered. <br>
     * いいえ: 無効を示す
     */
    public void setIsDisplay_Equal_False() {
        doSetIsDisplay_Equal(Boolean.valueOf(CDef.Flg.False.code()));
    }

    protected void doSetIsDisplay_Equal(Boolean isDisplay) {
        regIsDisplay(CK_EQ, isDisplay);
    }

    protected void regIsDisplay(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueIsDisplay(), "IS_DISPLAY"); }
    protected abstract ConditionValue xgetCValueIsDisplay();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_Equal(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_EQ,  registerDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_GreaterThan(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_GT,  registerDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_LessThan(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_LT,  registerDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_GreaterEqual(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_GE,  registerDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_LessEqual(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_LE, registerDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setRegisterDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setRegisterDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setRegisterDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setRegisterDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setRegisterDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "REGISTER_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueRegisterDatetime(), nm, op);
    }

    protected void regRegisterDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRegisterDatetime(), "REGISTER_DATETIME"); }
    protected abstract ConditionValue xgetCValueRegisterDatetime();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_Equal(String registerTrace) {
        doSetRegisterTrace_Equal(fRES(registerTrace));
    }

    protected void doSetRegisterTrace_Equal(String registerTrace) {
        regRegisterTrace(CK_EQ, registerTrace);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_NotEqual(String registerTrace) {
        doSetRegisterTrace_NotEqual(fRES(registerTrace));
    }

    protected void doSetRegisterTrace_NotEqual(String registerTrace) {
        regRegisterTrace(CK_NES, registerTrace);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_GreaterThan(String registerTrace) {
        regRegisterTrace(CK_GT, fRES(registerTrace));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_LessThan(String registerTrace) {
        regRegisterTrace(CK_LT, fRES(registerTrace));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_GreaterEqual(String registerTrace) {
        regRegisterTrace(CK_GE, fRES(registerTrace));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_LessEqual(String registerTrace) {
        regRegisterTrace(CK_LE, fRES(registerTrace));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTraceList The collection of registerTrace as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_InScope(Collection<String> registerTraceList) {
        doSetRegisterTrace_InScope(registerTraceList);
    }

    protected void doSetRegisterTrace_InScope(Collection<String> registerTraceList) {
        regINS(CK_INS, cTL(registerTraceList), xgetCValueRegisterTrace(), "REGISTER_TRACE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTraceList The collection of registerTrace as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_NotInScope(Collection<String> registerTraceList) {
        doSetRegisterTrace_NotInScope(registerTraceList);
    }

    protected void doSetRegisterTrace_NotInScope(Collection<String> registerTraceList) {
        regINS(CK_NINS, cTL(registerTraceList), xgetCValueRegisterTrace(), "REGISTER_TRACE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setRegisterTrace_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param registerTrace The value of registerTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setRegisterTrace_LikeSearch(String registerTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setRegisterTrace_LikeSearch(registerTrace, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setRegisterTrace_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param registerTrace The value of registerTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setRegisterTrace_LikeSearch(String registerTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(registerTrace), xgetCValueRegisterTrace(), "REGISTER_TRACE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setRegisterTrace_NotLikeSearch(String registerTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setRegisterTrace_NotLikeSearch(registerTrace, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setRegisterTrace_NotLikeSearch(String registerTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(registerTrace), xgetCValueRegisterTrace(), "REGISTER_TRACE", likeSearchOption);
    }

    protected void regRegisterTrace(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRegisterTrace(), "REGISTER_TRACE"); }
    protected abstract ConditionValue xgetCValueRegisterTrace();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_Equal(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_EQ,  updateDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_GreaterThan(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_GT,  updateDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_LessThan(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_LT,  updateDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_GreaterEqual(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_GE,  updateDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_LessEqual(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_LE, updateDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setUpdateDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setUpdateDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setUpdateDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setUpdateDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setUpdateDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "UPDATE_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueUpdateDatetime(), nm, op);
    }

    protected void regUpdateDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueUpdateDatetime(), "UPDATE_DATETIME"); }
    protected abstract ConditionValue xgetCValueUpdateDatetime();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_Equal(String updateTrace) {
        doSetUpdateTrace_Equal(fRES(updateTrace));
    }

    protected void doSetUpdateTrace_Equal(String updateTrace) {
        regUpdateTrace(CK_EQ, updateTrace);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_NotEqual(String updateTrace) {
        doSetUpdateTrace_NotEqual(fRES(updateTrace));
    }

    protected void doSetUpdateTrace_NotEqual(String updateTrace) {
        regUpdateTrace(CK_NES, updateTrace);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_GreaterThan(String updateTrace) {
        regUpdateTrace(CK_GT, fRES(updateTrace));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_LessThan(String updateTrace) {
        regUpdateTrace(CK_LT, fRES(updateTrace));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_GreaterEqual(String updateTrace) {
        regUpdateTrace(CK_GE, fRES(updateTrace));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_LessEqual(String updateTrace) {
        regUpdateTrace(CK_LE, fRES(updateTrace));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTraceList The collection of updateTrace as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_InScope(Collection<String> updateTraceList) {
        doSetUpdateTrace_InScope(updateTraceList);
    }

    protected void doSetUpdateTrace_InScope(Collection<String> updateTraceList) {
        regINS(CK_INS, cTL(updateTraceList), xgetCValueUpdateTrace(), "UPDATE_TRACE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTraceList The collection of updateTrace as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_NotInScope(Collection<String> updateTraceList) {
        doSetUpdateTrace_NotInScope(updateTraceList);
    }

    protected void doSetUpdateTrace_NotInScope(Collection<String> updateTraceList) {
        regINS(CK_NINS, cTL(updateTraceList), xgetCValueUpdateTrace(), "UPDATE_TRACE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setUpdateTrace_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param updateTrace The value of updateTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setUpdateTrace_LikeSearch(String updateTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setUpdateTrace_LikeSearch(updateTrace, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setUpdateTrace_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param updateTrace The value of updateTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setUpdateTrace_LikeSearch(String updateTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(updateTrace), xgetCValueUpdateTrace(), "UPDATE_TRACE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setUpdateTrace_NotLikeSearch(String updateTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setUpdateTrace_NotLikeSearch(updateTrace, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setUpdateTrace_NotLikeSearch(String updateTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(updateTrace), xgetCValueUpdateTrace(), "UPDATE_TRACE", likeSearchOption);
    }

    protected void regUpdateTrace(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueUpdateTrace(), "UPDATE_TRACE"); }
    protected abstract ConditionValue xgetCValueUpdateTrace();

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO = (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<OriginalCharaImageCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, OriginalCharaImageCB.class);
    }

    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO &lt;&gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<OriginalCharaImageCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, OriginalCharaImageCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterThan. <br>
     * {where FOO &gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<OriginalCharaImageCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, OriginalCharaImageCB.class);
    }

    /**
     * Prepare ScalarCondition as lessThan. <br>
     * {where FOO &lt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<OriginalCharaImageCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, OriginalCharaImageCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterEqual. <br>
     * {where FOO &gt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<OriginalCharaImageCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, OriginalCharaImageCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;OriginalCharaImageCB&gt;() {
     *     public void query(OriginalCharaImageCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<OriginalCharaImageCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, OriginalCharaImageCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaImageCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(OriginalCharaImageCQ sq);

    protected OriginalCharaImageCB xcreateScalarConditionCB() {
        OriginalCharaImageCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected OriginalCharaImageCB xcreateScalarConditionPartitionByCB() {
        OriginalCharaImageCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<OriginalCharaImageCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaImageCB cb = new OriginalCharaImageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "ORIGINAL_CHARA_IMAGE_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(OriginalCharaImageCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<OriginalCharaImageCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(OriginalCharaImageCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaImageCB cb = new OriginalCharaImageCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "ORIGINAL_CHARA_IMAGE_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(OriginalCharaImageCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<OriginalCharaImageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        OriginalCharaImageCB cb = new OriginalCharaImageCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(OriginalCharaImageCQ sq);

    // ===================================================================================
    //                                                                        Manual Order
    //                                                                        ============
    /**
     * Order along manual ordering information.
     * <pre>
     * cb.query().addOrderBy_Birthdate_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_GreaterEqual</span>(priorityDate); <span style="color: #3F7E5E">// e.g. 2000/01/01</span>
     * });
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when BIRTHDATE &gt;= '2000/01/01' then 0</span>
     * <span style="color: #3F7E5E">//     else 1</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     *
     * cb.query().addOrderBy_MemberStatusCode_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Withdrawal);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Formalized);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Provisional);
     * });
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'WDL' then 0</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'FML' then 1</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'PRV' then 2</span>
     * <span style="color: #3F7E5E">//     else 3</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     * </pre>
     * <p>This function with Union is unsupported!</p>
     * <p>The order values are bound (treated as bind parameter).</p>
     * @param opLambda The callback for option of manual-order containing order values. (NotNull)
     */
    public void withManualOrder(ManualOrderOptionCall opLambda) { // is user public!
        xdoWithManualOrder(cMOO(opLambda));
    }

    // ===================================================================================
    //                                                                    Small Adjustment
    //                                                                    ================
    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    protected OriginalCharaImageCB newMyCB() {
        return new OriginalCharaImageCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return OriginalCharaImageCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}

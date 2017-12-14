package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of message_type as TABLE. <br>
 * メッセージ種別
 * <pre>
 * [primary-key]
 *     MESSAGE_TYPE_CODE
 *
 * [column]
 *     MESSAGE_TYPE_CODE, MESSAGE_TYPE_NAME
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
 *     message
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     messageList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * String messageTypeCode = entity.getMessageTypeCode();
 * String messageTypeName = entity.getMessageTypeName();
 * entity.setMessageTypeCode(messageTypeCode);
 * entity.setMessageTypeName(messageTypeName);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsMessageType extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(10)} */
    protected String _messageTypeCode;

    /** MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)} */
    protected String _messageTypeName;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "message_type";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_messageTypeCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** message by MESSAGE_TYPE_CODE, named 'messageList'. */
    protected List<Message> _messageList;

    /**
     * [get] message by MESSAGE_TYPE_CODE, named 'messageList'.
     * @return The entity list of referrer property 'messageList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Message> getMessageList() {
        if (_messageList == null) { _messageList = newReferrerList(); }
        return _messageList;
    }

    /**
     * [set] message by MESSAGE_TYPE_CODE, named 'messageList'.
     * @param messageList The entity list of referrer property 'messageList'. (NullAllowed)
     */
    public void setMessageList(List<Message> messageList) {
        _messageList = messageList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsMessageType) {
            BsMessageType other = (BsMessageType)obj;
            if (!xSV(_messageTypeCode, other._messageTypeCode)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _messageTypeCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_messageList != null) { for (Message et : _messageList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "messageList")); } } }
        return sb.toString();
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_messageTypeCode));
        sb.append(dm).append(xfND(_messageTypeName));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_messageList != null && !_messageList.isEmpty())
        { sb.append(dm).append("messageList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public MessageType clone() {
        return (MessageType)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(10)} <br>
     * メッセージ種別コード
     * @return The value of the column 'MESSAGE_TYPE_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getMessageTypeCode() {
        checkSpecifiedProperty("messageTypeCode");
        return _messageTypeCode;
    }

    /**
     * [set] MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(10)} <br>
     * メッセージ種別コード
     * @param messageTypeCode The value of the column 'MESSAGE_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    public void setMessageTypeCode(String messageTypeCode) {
        registerModifiedProperty("messageTypeCode");
        _messageTypeCode = messageTypeCode;
    }

    /**
     * [get] MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * メッセージ種別名
     * @return The value of the column 'MESSAGE_TYPE_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getMessageTypeName() {
        checkSpecifiedProperty("messageTypeName");
        return _messageTypeName;
    }

    /**
     * [set] MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * メッセージ種別名
     * @param messageTypeName The value of the column 'MESSAGE_TYPE_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setMessageTypeName(String messageTypeName) {
        registerModifiedProperty("messageTypeName");
        _messageTypeName = messageTypeName;
    }
}

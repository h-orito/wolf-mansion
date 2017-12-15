package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import org.dbflute.optional.OptionalEntity;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of VILLAGE_PLAYER as TABLE. <br>
 * 村参加者
 * <pre>
 * [primary-key]
 *     VILLAGE_PLAYER_ID
 *
 * [column]
 *     VILLAGE_PLAYER_ID, VILLAGE_ID, PLAYER_ID, CHARA_ID
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILLAGE_PLAYER_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     CHARA, PLAYER, VILLAGE
 *
 * [referrer table]
 *     MESSAGE
 *
 * [foreign property]
 *     chara, player, village
 *
 * [referrer property]
 *     messageByToVillagePlayerIdList, messageByVillagePlayerIdList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villagePlayerId = entity.getVillagePlayerId();
 * Integer villageId = entity.getVillageId();
 * Integer playerId = entity.getPlayerId();
 * Integer charaId = entity.getCharaId();
 * entity.setVillagePlayerId(villagePlayerId);
 * entity.setVillageId(villageId);
 * entity.setPlayerId(playerId);
 * entity.setCharaId(charaId);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillagePlayer extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _villagePlayerId;

    /** VILLAGE_ID: {UQ+, NotNull, INT UNSIGNED(10), FK to village} */
    protected Integer _villageId;

    /** PLAYER_ID: {+UQ, IX, NotNull, INT UNSIGNED(10), FK to player} */
    protected Integer _playerId;

    /** CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara} */
    protected Integer _charaId;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "village_player";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villagePlayerId == null) { return false; }
        return true;
    }

    /**
     * To be unique by the unique column. <br>
     * You can update the entity by the key when entity update (NOT batch update).
     * @param villageId : UQ+, NotNull, INT UNSIGNED(10), FK to village. (NotNull)
     * @param playerId : +UQ, IX, NotNull, INT UNSIGNED(10), FK to player. (NotNull)
     */
    public void uniqueBy(Integer villageId, Integer playerId) {
        __uniqueDrivenProperties.clear();
        __uniqueDrivenProperties.addPropertyName("villageId");
        __uniqueDrivenProperties.addPropertyName("playerId");
        setVillageId(villageId);setPlayerId(playerId);
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** CHARA by my CHARA_ID, named 'chara'. */
    protected OptionalEntity<Chara> _chara;

    /**
     * [get] CHARA by my CHARA_ID, named 'chara'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'chara'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Chara> getChara() {
        if (_chara == null) { _chara = OptionalEntity.relationEmpty(this, "chara"); }
        return _chara;
    }

    /**
     * [set] CHARA by my CHARA_ID, named 'chara'.
     * @param chara The entity of foreign property 'chara'. (NullAllowed)
     */
    public void setChara(OptionalEntity<Chara> chara) {
        _chara = chara;
    }

    /** PLAYER by my PLAYER_ID, named 'player'. */
    protected OptionalEntity<Player> _player;

    /**
     * [get] PLAYER by my PLAYER_ID, named 'player'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'player'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Player> getPlayer() {
        if (_player == null) { _player = OptionalEntity.relationEmpty(this, "player"); }
        return _player;
    }

    /**
     * [set] PLAYER by my PLAYER_ID, named 'player'.
     * @param player The entity of foreign property 'player'. (NullAllowed)
     */
    public void setPlayer(OptionalEntity<Player> player) {
        _player = player;
    }

    /** VILLAGE by my VILLAGE_ID, named 'village'. */
    protected OptionalEntity<Village> _village;

    /**
     * [get] VILLAGE by my VILLAGE_ID, named 'village'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'village'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Village> getVillage() {
        if (_village == null) { _village = OptionalEntity.relationEmpty(this, "village"); }
        return _village;
    }

    /**
     * [set] VILLAGE by my VILLAGE_ID, named 'village'.
     * @param village The entity of foreign property 'village'. (NullAllowed)
     */
    public void setVillage(OptionalEntity<Village> village) {
        _village = village;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** MESSAGE by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdList'. */
    protected List<Message> _messageByToVillagePlayerIdList;

    /**
     * [get] MESSAGE by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdList'.
     * @return The entity list of referrer property 'messageByToVillagePlayerIdList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Message> getMessageByToVillagePlayerIdList() {
        if (_messageByToVillagePlayerIdList == null) { _messageByToVillagePlayerIdList = newReferrerList(); }
        return _messageByToVillagePlayerIdList;
    }

    /**
     * [set] MESSAGE by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdList'.
     * @param messageByToVillagePlayerIdList The entity list of referrer property 'messageByToVillagePlayerIdList'. (NullAllowed)
     */
    public void setMessageByToVillagePlayerIdList(List<Message> messageByToVillagePlayerIdList) {
        _messageByToVillagePlayerIdList = messageByToVillagePlayerIdList;
    }

    /** MESSAGE by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdList'. */
    protected List<Message> _messageByVillagePlayerIdList;

    /**
     * [get] MESSAGE by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdList'.
     * @return The entity list of referrer property 'messageByVillagePlayerIdList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Message> getMessageByVillagePlayerIdList() {
        if (_messageByVillagePlayerIdList == null) { _messageByVillagePlayerIdList = newReferrerList(); }
        return _messageByVillagePlayerIdList;
    }

    /**
     * [set] MESSAGE by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdList'.
     * @param messageByVillagePlayerIdList The entity list of referrer property 'messageByVillagePlayerIdList'. (NullAllowed)
     */
    public void setMessageByVillagePlayerIdList(List<Message> messageByVillagePlayerIdList) {
        _messageByVillagePlayerIdList = messageByVillagePlayerIdList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsVillagePlayer) {
            BsVillagePlayer other = (BsVillagePlayer)obj;
            if (!xSV(_villagePlayerId, other._villagePlayerId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _villagePlayerId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_chara != null && _chara.isPresent())
        { sb.append(li).append(xbRDS(_chara, "chara")); }
        if (_player != null && _player.isPresent())
        { sb.append(li).append(xbRDS(_player, "player")); }
        if (_village != null && _village.isPresent())
        { sb.append(li).append(xbRDS(_village, "village")); }
        if (_messageByToVillagePlayerIdList != null) { for (Message et : _messageByToVillagePlayerIdList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "messageByToVillagePlayerIdList")); } } }
        if (_messageByVillagePlayerIdList != null) { for (Message et : _messageByVillagePlayerIdList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "messageByVillagePlayerIdList")); } } }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_villagePlayerId));
        sb.append(dm).append(xfND(_villageId));
        sb.append(dm).append(xfND(_playerId));
        sb.append(dm).append(xfND(_charaId));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_chara != null && _chara.isPresent())
        { sb.append(dm).append("chara"); }
        if (_player != null && _player.isPresent())
        { sb.append(dm).append("player"); }
        if (_village != null && _village.isPresent())
        { sb.append(dm).append("village"); }
        if (_messageByToVillagePlayerIdList != null && !_messageByToVillagePlayerIdList.isEmpty())
        { sb.append(dm).append("messageByToVillagePlayerIdList"); }
        if (_messageByVillagePlayerIdList != null && !_messageByVillagePlayerIdList.isEmpty())
        { sb.append(dm).append("messageByVillagePlayerIdList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VillagePlayer clone() {
        return (VillagePlayer)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村参加者ID
     * @return The value of the column 'VILLAGE_PLAYER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillagePlayerId() {
        checkSpecifiedProperty("villagePlayerId");
        return _villagePlayerId;
    }

    /**
     * [set] VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村参加者ID
     * @param villagePlayerId The value of the column 'VILLAGE_PLAYER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillagePlayerId(Integer villagePlayerId) {
        registerModifiedProperty("villagePlayerId");
        _villagePlayerId = villagePlayerId;
    }

    /**
     * [get] VILLAGE_ID: {UQ+, NotNull, INT UNSIGNED(10), FK to village} <br>
     * 村ID
     * @return The value of the column 'VILLAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageId() {
        checkSpecifiedProperty("villageId");
        return _villageId;
    }

    /**
     * [set] VILLAGE_ID: {UQ+, NotNull, INT UNSIGNED(10), FK to village} <br>
     * 村ID
     * @param villageId The value of the column 'VILLAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillageId(Integer villageId) {
        registerModifiedProperty("villageId");
        _villageId = villageId;
    }

    /**
     * [get] PLAYER_ID: {+UQ, IX, NotNull, INT UNSIGNED(10), FK to player} <br>
     * プレイヤーID
     * @return The value of the column 'PLAYER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getPlayerId() {
        checkSpecifiedProperty("playerId");
        return _playerId;
    }

    /**
     * [set] PLAYER_ID: {+UQ, IX, NotNull, INT UNSIGNED(10), FK to player} <br>
     * プレイヤーID
     * @param playerId The value of the column 'PLAYER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setPlayerId(Integer playerId) {
        registerModifiedProperty("playerId");
        _playerId = playerId;
    }

    /**
     * [get] CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara} <br>
     * キャラクターID
     * @return The value of the column 'CHARA_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getCharaId() {
        checkSpecifiedProperty("charaId");
        return _charaId;
    }

    /**
     * [set] CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara} <br>
     * キャラクターID
     * @param charaId The value of the column 'CHARA_ID'. (basically NotNull if update: for the constraint)
     */
    public void setCharaId(Integer charaId) {
        registerModifiedProperty("charaId");
        _charaId = charaId;
    }
}

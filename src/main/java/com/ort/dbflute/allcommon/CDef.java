package com.ort.dbflute.allcommon;

import java.util.*;

import org.dbflute.exception.ClassificationNotFoundException;
import org.dbflute.jdbc.Classification;
import org.dbflute.jdbc.ClassificationCodeType;
import org.dbflute.jdbc.ClassificationMeta;
import org.dbflute.jdbc.ClassificationUndefinedHandlingType;
import org.dbflute.optional.OptionalThing;
import static org.dbflute.util.DfTypeUtil.emptyStrings;

/**
 * The definition of classification.
 * @author DBFlute(AutoGenerator)
 */
public interface CDef extends Classification {

    /**
     * フラグを示す
     */
    public enum Flg implements CDef {
        /** はい: 有効を示す */
        True("true", "はい", emptyStrings())
        ,
        /** いいえ: 無効を示す */
        False("false", "いいえ", emptyStrings())
        ;
        private static final Map<String, Flg> _codeClsMap = new HashMap<String, Flg>();
        private static final Map<String, Flg> _nameClsMap = new HashMap<String, Flg>();
        static {
            for (Flg value : values()) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) { _codeClsMap.put(sister.toLowerCase(), value); }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }
        private String _code; private String _alias; private Set<String> _sisterSet;
        private Flg(String code, String alias, String[] sisters)
        { _code = code; _alias = alias; _sisterSet = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters))); }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return _sisterSet; }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.Flg; }

        public boolean inGroup(String groupName) {
            return false;
        }

        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Flg> of(Object code) {
            if (code == null) { return OptionalThing.ofNullable(null, () -> { throw new ClassificationNotFoundException("null code specified"); }); }
            if (code instanceof Flg) { return OptionalThing.of((Flg)code); }
            if (code instanceof OptionalThing<?>) { return of(((OptionalThing<?>)code).orElse(null)); }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Flg> byName(String name) {
            if (name == null) { throw new IllegalArgumentException("The argument 'name' should not be null."); }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span> <br>
         * Get the classification by the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static Flg codeOf(Object code) {
            if (code == null) { return null; }
            if (code instanceof Flg) { return (Flg)code; }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span> <br>
         * Get the classification by the name (also called 'value' in ENUM world).
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         */
        public static Flg nameOf(String name) {
            if (name == null) { return null; }
            try { return valueOf(name); } catch (RuntimeException ignored) { return null; }
        }

        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<Flg> listAll() {
            return new ArrayList<Flg>(Arrays.asList(values()));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if not found, throws exception)
         */
        public static List<Flg> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: Flg." + groupName);
        }

        /**
         * Get the list of classification elements corresponding to the specified codes. (returns new copied list) <br>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         */
        public static List<Flg> listOf(Collection<String> codeList) {
            if (codeList == null) { throw new IllegalArgumentException("The argument 'codeList' should not be null."); }
            List<Flg> clsList = new ArrayList<Flg>(codeList.size());
            for (String code : codeList) { clsList.add(of(code).get()); }
            return clsList;
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         */
        public static List<Flg> groupOf(String groupName) {
            return new ArrayList<Flg>(4);
        }

        @Override public String toString() { return code(); }
    }

    /**
     * 権限
     */
    public enum Authority implements CDef {
        /** 管理者 */
        管理者("ROLE_ADMIN", "管理者", emptyStrings())
        ,
        /** プレイヤー */
        プレイヤー("ROLE_PLAYER", "プレイヤー", emptyStrings())
        ;
        private static final Map<String, Authority> _codeClsMap = new HashMap<String, Authority>();
        private static final Map<String, Authority> _nameClsMap = new HashMap<String, Authority>();
        static {
            for (Authority value : values()) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) { _codeClsMap.put(sister.toLowerCase(), value); }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }
        private String _code; private String _alias; private Set<String> _sisterSet;
        private Authority(String code, String alias, String[] sisters)
        { _code = code; _alias = alias; _sisterSet = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters))); }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return _sisterSet; }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.Authority; }

        public boolean inGroup(String groupName) {
            return false;
        }

        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Authority> of(Object code) {
            if (code == null) { return OptionalThing.ofNullable(null, () -> { throw new ClassificationNotFoundException("null code specified"); }); }
            if (code instanceof Authority) { return OptionalThing.of((Authority)code); }
            if (code instanceof OptionalThing<?>) { return of(((OptionalThing<?>)code).orElse(null)); }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Authority> byName(String name) {
            if (name == null) { throw new IllegalArgumentException("The argument 'name' should not be null."); }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span> <br>
         * Get the classification by the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static Authority codeOf(Object code) {
            if (code == null) { return null; }
            if (code instanceof Authority) { return (Authority)code; }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span> <br>
         * Get the classification by the name (also called 'value' in ENUM world).
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         */
        public static Authority nameOf(String name) {
            if (name == null) { return null; }
            try { return valueOf(name); } catch (RuntimeException ignored) { return null; }
        }

        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<Authority> listAll() {
            return new ArrayList<Authority>(Arrays.asList(values()));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if not found, throws exception)
         */
        public static List<Authority> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: Authority." + groupName);
        }

        /**
         * Get the list of classification elements corresponding to the specified codes. (returns new copied list) <br>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         */
        public static List<Authority> listOf(Collection<String> codeList) {
            if (codeList == null) { throw new IllegalArgumentException("The argument 'codeList' should not be null."); }
            List<Authority> clsList = new ArrayList<Authority>(codeList.size());
            for (String code : codeList) { clsList.add(of(code).get()); }
            return clsList;
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         */
        public static List<Authority> groupOf(String groupName) {
            return new ArrayList<Authority>(4);
        }

        @Override public String toString() { return code(); }
    }

    /**
     * 陣営
     */
    public enum Camp implements CDef {
        /** 愉快犯陣営 */
        愉快犯陣営("CRIMINAL", "愉快犯陣営", emptyStrings())
        ,
        /** 狐陣営 */
        狐陣営("FOX", "狐陣営", emptyStrings())
        ,
        /** 恋人陣営 */
        恋人陣営("LOVERS", "恋人陣営", emptyStrings())
        ,
        /** 村人陣営 */
        村人陣営("VILLAGER", "村人陣営", emptyStrings())
        ,
        /** 人狼陣営 */
        人狼陣営("WEREWOLF", "人狼陣営", emptyStrings())
        ;
        private static final Map<String, Camp> _codeClsMap = new HashMap<String, Camp>();
        private static final Map<String, Camp> _nameClsMap = new HashMap<String, Camp>();
        static {
            for (Camp value : values()) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) { _codeClsMap.put(sister.toLowerCase(), value); }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }
        private String _code; private String _alias; private Set<String> _sisterSet;
        private Camp(String code, String alias, String[] sisters)
        { _code = code; _alias = alias; _sisterSet = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters))); }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return _sisterSet; }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.Camp; }

        public boolean inGroup(String groupName) {
            return false;
        }

        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Camp> of(Object code) {
            if (code == null) { return OptionalThing.ofNullable(null, () -> { throw new ClassificationNotFoundException("null code specified"); }); }
            if (code instanceof Camp) { return OptionalThing.of((Camp)code); }
            if (code instanceof OptionalThing<?>) { return of(((OptionalThing<?>)code).orElse(null)); }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Camp> byName(String name) {
            if (name == null) { throw new IllegalArgumentException("The argument 'name' should not be null."); }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span> <br>
         * Get the classification by the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static Camp codeOf(Object code) {
            if (code == null) { return null; }
            if (code instanceof Camp) { return (Camp)code; }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span> <br>
         * Get the classification by the name (also called 'value' in ENUM world).
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         */
        public static Camp nameOf(String name) {
            if (name == null) { return null; }
            try { return valueOf(name); } catch (RuntimeException ignored) { return null; }
        }

        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<Camp> listAll() {
            return new ArrayList<Camp>(Arrays.asList(values()));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if not found, throws exception)
         */
        public static List<Camp> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: Camp." + groupName);
        }

        /**
         * Get the list of classification elements corresponding to the specified codes. (returns new copied list) <br>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         */
        public static List<Camp> listOf(Collection<String> codeList) {
            if (codeList == null) { throw new IllegalArgumentException("The argument 'codeList' should not be null."); }
            List<Camp> clsList = new ArrayList<Camp>(codeList.size());
            for (String code : codeList) { clsList.add(of(code).get()); }
            return clsList;
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         */
        public static List<Camp> groupOf(String groupName) {
            return new ArrayList<Camp>(4);
        }

        @Override public String toString() { return code(); }
    }

    /**
     * 村ステータス
     */
    public enum VillageStatus implements CDef {
        /** 廃村 */
        廃村("CANCEL", "廃村", emptyStrings())
        ,
        /** 終了 */
        終了("COMPLETED", "終了", emptyStrings())
        ,
        /** エピローグ */
        エピローグ("EPILOGUE", "エピローグ", emptyStrings())
        ,
        /** 募集中 */
        募集中("IN_PREPARATION", "募集中", emptyStrings())
        ,
        /** 進行中 */
        進行中("IN_PROGRESS", "進行中", emptyStrings())
        ,
        /** 開始待ち */
        開始待ち("WAITING", "開始待ち", emptyStrings())
        ;
        private static final Map<String, VillageStatus> _codeClsMap = new HashMap<String, VillageStatus>();
        private static final Map<String, VillageStatus> _nameClsMap = new HashMap<String, VillageStatus>();
        static {
            for (VillageStatus value : values()) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) { _codeClsMap.put(sister.toLowerCase(), value); }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }
        private String _code; private String _alias; private Set<String> _sisterSet;
        private VillageStatus(String code, String alias, String[] sisters)
        { _code = code; _alias = alias; _sisterSet = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters))); }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return _sisterSet; }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.VillageStatus; }

        public boolean inGroup(String groupName) {
            return false;
        }

        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<VillageStatus> of(Object code) {
            if (code == null) { return OptionalThing.ofNullable(null, () -> { throw new ClassificationNotFoundException("null code specified"); }); }
            if (code instanceof VillageStatus) { return OptionalThing.of((VillageStatus)code); }
            if (code instanceof OptionalThing<?>) { return of(((OptionalThing<?>)code).orElse(null)); }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<VillageStatus> byName(String name) {
            if (name == null) { throw new IllegalArgumentException("The argument 'name' should not be null."); }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span> <br>
         * Get the classification by the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static VillageStatus codeOf(Object code) {
            if (code == null) { return null; }
            if (code instanceof VillageStatus) { return (VillageStatus)code; }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span> <br>
         * Get the classification by the name (also called 'value' in ENUM world).
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         */
        public static VillageStatus nameOf(String name) {
            if (name == null) { return null; }
            try { return valueOf(name); } catch (RuntimeException ignored) { return null; }
        }

        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<VillageStatus> listAll() {
            return new ArrayList<VillageStatus>(Arrays.asList(values()));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if not found, throws exception)
         */
        public static List<VillageStatus> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: VillageStatus." + groupName);
        }

        /**
         * Get the list of classification elements corresponding to the specified codes. (returns new copied list) <br>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         */
        public static List<VillageStatus> listOf(Collection<String> codeList) {
            if (codeList == null) { throw new IllegalArgumentException("The argument 'codeList' should not be null."); }
            List<VillageStatus> clsList = new ArrayList<VillageStatus>(codeList.size());
            for (String code : codeList) { clsList.add(of(code).get()); }
            return clsList;
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         */
        public static List<VillageStatus> groupOf(String groupName) {
            return new ArrayList<VillageStatus>(4);
        }

        @Override public String toString() { return code(); }
    }

    /**
     * 役職
     */
    public enum Skill implements CDef {
        /** 絶対人狼 */
        絶対人狼("ABSOLUTEWOLF", "絶対人狼", emptyStrings())
        ,
        /** 占星術師 */
        占星術師("ASTROLOGER", "占星術師", emptyStrings())
        ,
        /** パン屋 */
        パン屋("BAKERY", "パン屋", emptyStrings())
        ,
        /** 爆弾魔 */
        爆弾魔("BOMBER", "爆弾魔", emptyStrings())
        ,
        /** 誑狐 */
        誑狐("CHEATERFOX", "誑狐", emptyStrings())
        ,
        /** C国狂人 */
        C国狂人("CMADMAN", "C国狂人", emptyStrings())
        ,
        /** 同棲者 */
        同棲者("COHABITER", "同棲者", emptyStrings())
        ,
        /** 指揮官 */
        指揮官("COMMANDER", "指揮官", emptyStrings())
        ,
        /** 検死官 */
        検死官("CORONER", "検死官", emptyStrings())
        ,
        /** 求愛者 */
        求愛者("COURTSHIP", "求愛者", emptyStrings())
        ,
        /** おまかせ（愉快犯陣営） */
        おまかせ愉快犯陣営("CRIMINALS", "おまかせ（愉快犯陣営）", emptyStrings())
        ,
        /** 呪狼 */
        呪狼("CURSEWOLF", "呪狼", emptyStrings())
        ,
        /** 探偵 */
        探偵("DETECTIVE", "探偵", emptyStrings())
        ,
        /** 魔神官 */
        魔神官("EVILMEDIUM", "魔神官", emptyStrings())
        ,
        /** 冤罪者 */
        冤罪者("FALSECHARGES", "冤罪者", emptyStrings())
        ,
        /** 狂信者 */
        狂信者("FANATIC", "狂信者", emptyStrings())
        ,
        /** 妄想癖 */
        妄想癖("FANTASIST", "妄想癖", emptyStrings())
        ,
        /** おまかせ（足音職） */
        おまかせ足音職("FOOTSTEPS", "おまかせ（足音職）", emptyStrings())
        ,
        /** 妖狐 */
        妖狐("FOX", "妖狐", emptyStrings())
        ,
        /** おまかせ（妖狐陣営） */
        おまかせ妖狐陣営("FOXS", "おまかせ（妖狐陣営）", emptyStrings())
        ,
        /** おまかせ（役職窓あり） */
        おまかせ役職窓あり("FRIENDS", "おまかせ（役職窓あり）", emptyStrings())
        ,
        /** 果実籠 */
        果実籠("FRUITSBASKET", "果実籠", emptyStrings())
        ,
        /** 導師 */
        導師("GURU", "導師", emptyStrings())
        ,
        /** 狩人 */
        狩人("HUNTER", "狩人", emptyStrings())
        ,
        /** 背徳者 */
        背徳者("IMMORAL", "背徳者", emptyStrings())
        ,
        /** おまかせ */
        おまかせ("LEFTOVER", "おまかせ", emptyStrings())
        ,
        /** 一匹狼 */
        一匹狼("LONEWOLF", "一匹狼", emptyStrings())
        ,
        /** 恋人 */
        恋人("LOVER", "恋人", emptyStrings())
        ,
        /** おまかせ（恋人陣営） */
        おまかせ恋人陣営("LOVERS", "おまかせ（恋人陣営）", emptyStrings())
        ,
        /** 強運者 */
        強運者("LUCKYMAN", "強運者", emptyStrings())
        ,
        /** 狂人 */
        狂人("MADMAN", "狂人", emptyStrings())
        ,
        /** 共鳴者 */
        共鳴者("MASON", "共鳴者", emptyStrings())
        ,
        /** 霊能者 */
        霊能者("MEDIUM", "霊能者", emptyStrings())
        ,
        /** おまかせ（役職窓なし） */
        おまかせ役職窓なし("NOFRIENDS", "おまかせ（役職窓なし）", emptyStrings())
        ,
        /** おまかせ（人外） */
        おまかせ人外("NOVILLAGERS", "おまかせ（人外）", emptyStrings())
        ,
        /** 梟 */
        梟("OWL", "梟", emptyStrings())
        ,
        /** 占い師 */
        占い師("SEER", "占い師", emptyStrings())
        ,
        /** 夢遊病者 */
        夢遊病者("SLEEPWALKER", "夢遊病者", emptyStrings())
        ,
        /** ストーカー */
        ストーカー("STALKER", "ストーカー", emptyStrings())
        ,
        /** 罠師 */
        罠師("TRAPPER", "罠師", emptyStrings())
        ,
        /** 村人 */
        村人("VILLAGER", "村人", emptyStrings())
        ,
        /** おまかせ（村人陣営） */
        おまかせ村人陣営("VILLAGERS", "おまかせ（村人陣営）", emptyStrings())
        ,
        /** 人狼 */
        人狼("WEREWOLF", "人狼", emptyStrings())
        ,
        /** おまかせ（人狼陣営） */
        おまかせ人狼陣営("WEREWOLFS", "おまかせ（人狼陣営）", emptyStrings())
        ,
        /** 賢者 */
        賢者("WISE", "賢者", emptyStrings())
        ,
        /** 智狼 */
        智狼("WISEWOLF", "智狼", emptyStrings())
        ;
        private static final Map<String, Skill> _codeClsMap = new HashMap<String, Skill>();
        private static final Map<String, Skill> _nameClsMap = new HashMap<String, Skill>();
        static {
            for (Skill value : values()) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) { _codeClsMap.put(sister.toLowerCase(), value); }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }
        private static final Map<String, Map<String, Object>> _subItemMapMap = new HashMap<String, Map<String, Object>>();
        static {
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "103");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "絶");
                _subItemMapMap.put(絶対人狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "4");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "星");
                _subItemMapMap.put(占星術師.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "12");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "パ");
                _subItemMapMap.put(パン屋.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "500");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "爆");
                _subItemMapMap.put(爆弾魔.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "401");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "誑");
                _subItemMapMap.put(誑狐.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "201");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "C");
                _subItemMapMap.put(C国狂人.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "301");
                subItemMap.put("campCode", "LOVERS");
                subItemMap.put("skill_short_name", "棲");
                _subItemMapMap.put(同棲者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "14");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "指");
                _subItemMapMap.put(指揮官.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "7");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "検");
                _subItemMapMap.put(検死官.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "302");
                subItemMap.put("campCode", "LOVERS");
                subItemMap.put("skill_short_name", "求");
                _subItemMapMap.put(求愛者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "1005");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "お");
                _subItemMapMap.put(おまかせ愉快犯陣営.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "101");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "呪");
                _subItemMapMap.put(呪狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "10");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "探");
                _subItemMapMap.put(探偵.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "202");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "魔");
                _subItemMapMap.put(魔神官.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "17");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "冤");
                _subItemMapMap.put(冤罪者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "203");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "信");
                _subItemMapMap.put(狂信者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "15");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "妄");
                _subItemMapMap.put(妄想癖.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "1007");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "お");
                _subItemMapMap.put(おまかせ足音職.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "400");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "狐");
                _subItemMapMap.put(妖狐.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "1004");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "お");
                _subItemMapMap.put(おまかせ妖狐陣営.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "1008");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "お");
                _subItemMapMap.put(おまかせ役職窓あり.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "502");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "籠");
                _subItemMapMap.put(果実籠.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "6");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "導");
                _subItemMapMap.put(導師.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "8");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "狩");
                _subItemMapMap.put(狩人.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "402");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "背");
                _subItemMapMap.put(背徳者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "1000");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "お");
                _subItemMapMap.put(おまかせ.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "503");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "匹");
                _subItemMapMap.put(一匹狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "300");
                subItemMap.put("campCode", "LOVERS");
                subItemMap.put("skill_short_name", "恋");
                _subItemMapMap.put(恋人.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "1003");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "お");
                _subItemMapMap.put(おまかせ恋人陣営.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "13");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "運");
                _subItemMapMap.put(強運者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "200");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "狂");
                _subItemMapMap.put(狂人.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "9");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "共");
                _subItemMapMap.put(共鳴者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "5");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "霊");
                _subItemMapMap.put(霊能者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "1009");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "お");
                _subItemMapMap.put(おまかせ役職窓なし.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "1006");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "お");
                _subItemMapMap.put(おまかせ人外.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "501");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "梟");
                _subItemMapMap.put(梟.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "2");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "占");
                _subItemMapMap.put(占い師.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "16");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "夢");
                _subItemMapMap.put(夢遊病者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "303");
                subItemMap.put("campCode", "LOVERS");
                subItemMap.put("skill_short_name", "ス");
                _subItemMapMap.put(ストーカー.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "11");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "罠");
                _subItemMapMap.put(罠師.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "1");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "村");
                _subItemMapMap.put(村人.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "1001");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "お");
                _subItemMapMap.put(おまかせ村人陣営.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "100");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "狼");
                _subItemMapMap.put(人狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "1002");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "お");
                _subItemMapMap.put(おまかせ人狼陣営.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "3");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "賢");
                _subItemMapMap.put(賢者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "102");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "智");
                _subItemMapMap.put(智狼.code(), Collections.unmodifiableMap(subItemMap));
            }
        }
        private String _code; private String _alias; private Set<String> _sisterSet;
        private Skill(String code, String alias, String[] sisters)
        { _code = code; _alias = alias; _sisterSet = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters))); }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return _sisterSet; }
        public Map<String, Object> subItemMap() { return _subItemMapMap.get(code()); }
        public ClassificationMeta meta() { return CDef.DefMeta.Skill; }

        public String order() {
            return (String)subItemMap().get("order");
        }

        public String campCode() {
            return (String)subItemMap().get("campCode");
        }

        public String skill_short_name() {
            return (String)subItemMap().get("skill_short_name");
        }

        /**
         * Is the classification in the group? <br>
         * 囁き可能 <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, C国狂人]
         * @return The determination, true or false.
         */
        public boolean isAvailableWerewolfSay() {
            return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this) || C国狂人.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * 占い能力を持つ <br>
         * The group elements:[占い師, 賢者, 占星術師]
         * @return The determination, true or false.
         */
        public boolean isHasDivineAbility() {
            return 占い師.equals(this) || 賢者.equals(this) || 占星術師.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * 役職霊能能力を持つ <br>
         * The group elements:[導師, 魔神官]
         * @return The determination, true or false.
         */
        public boolean isHasSkillPsychicAbility() {
            return 導師.equals(this) || 魔神官.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * 襲撃能力を持つ <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼]
         * @return The determination, true or false.
         */
        public boolean isHasAttackAbility() {
            return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * 狂人能力を持つ <br>
         * The group elements:[C国狂人, 狂人, 狂信者, 魔神官]
         * @return The determination, true or false.
         */
        public boolean isHasMadmanAbility() {
            return C国狂人.equals(this) || 狂人.equals(this) || 狂信者.equals(this) || 魔神官.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * 徘徊能力を持つ <br>
         * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 妖狐, 背徳者]
         * @return The determination, true or false.
         */
        public boolean isHasDisturbAbility() {
            return C国狂人.equals(this) || 狂人.equals(this) || 狂信者.equals(this) || 魔神官.equals(this) || 妖狐.equals(this) || 背徳者.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * 襲撃されても死なない <br>
         * The group elements:[妖狐, 誑狐, 爆弾魔]
         * @return The determination, true or false.
         */
        public boolean isNoDeadByAttack() {
            return 妖狐.equals(this) || 誑狐.equals(this) || 爆弾魔.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * 勝敗判定時、人狼にカウントされる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼]
         * @return The determination, true or false.
         */
        public boolean isWolfCount() {
            return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * 勝敗判定時、人間にも人狼にもカウントされない <br>
         * The group elements:[妖狐, 誑狐, 梟]
         * @return The determination, true or false.
         */
        public boolean isNoCount() {
            return 妖狐.equals(this) || 誑狐.equals(this) || 梟.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * 人狼が誰かを知ることができる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, C国狂人, 狂信者]
         * @return The determination, true or false.
         */
        public boolean isViewableWolfCharaName() {
            return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this) || C国狂人.equals(this) || 狂信者.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * 占い結果が人狼となる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 一匹狼]
         * @return The determination, true or false.
         */
        public boolean isDivineResultWolf() {
            return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this) || 一匹狼.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * 霊能結果が人狼となる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 一匹狼]
         * @return The determination, true or false.
         */
        public boolean isPsychicResultWolf() {
            return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this) || 一匹狼.equals(this);
        }

        /**
         * Is the classification in the group? <br>
         * おまかせ系 <br>
         * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
         * @return The determination, true or false.
         */
        public boolean isSomeoneSkill() {
            return おまかせ.equals(this) || おまかせ村人陣営.equals(this) || おまかせ人狼陣営.equals(this) || おまかせ恋人陣営.equals(this) || おまかせ妖狐陣営.equals(this) || おまかせ愉快犯陣営.equals(this) || おまかせ足音職.equals(this) || おまかせ役職窓あり.equals(this) || おまかせ役職窓なし.equals(this) || おまかせ人外.equals(this);
        }

        public boolean inGroup(String groupName) {
            if ("availableWerewolfSay".equals(groupName)) { return isAvailableWerewolfSay(); }
            if ("hasDivineAbility".equals(groupName)) { return isHasDivineAbility(); }
            if ("hasSkillPsychicAbility".equals(groupName)) { return isHasSkillPsychicAbility(); }
            if ("hasAttackAbility".equals(groupName)) { return isHasAttackAbility(); }
            if ("hasMadmanAbility".equals(groupName)) { return isHasMadmanAbility(); }
            if ("hasDisturbAbility".equals(groupName)) { return isHasDisturbAbility(); }
            if ("noDeadByAttack".equals(groupName)) { return isNoDeadByAttack(); }
            if ("wolfCount".equals(groupName)) { return isWolfCount(); }
            if ("noCount".equals(groupName)) { return isNoCount(); }
            if ("viewableWolfCharaName".equals(groupName)) { return isViewableWolfCharaName(); }
            if ("divineResultWolf".equals(groupName)) { return isDivineResultWolf(); }
            if ("psychicResultWolf".equals(groupName)) { return isPsychicResultWolf(); }
            if ("someoneSkill".equals(groupName)) { return isSomeoneSkill(); }
            return false;
        }

        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Skill> of(Object code) {
            if (code == null) { return OptionalThing.ofNullable(null, () -> { throw new ClassificationNotFoundException("null code specified"); }); }
            if (code instanceof Skill) { return OptionalThing.of((Skill)code); }
            if (code instanceof OptionalThing<?>) { return of(((OptionalThing<?>)code).orElse(null)); }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Skill> byName(String name) {
            if (name == null) { throw new IllegalArgumentException("The argument 'name' should not be null."); }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span> <br>
         * Get the classification by the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static Skill codeOf(Object code) {
            if (code == null) { return null; }
            if (code instanceof Skill) { return (Skill)code; }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span> <br>
         * Get the classification by the name (also called 'value' in ENUM world).
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         */
        public static Skill nameOf(String name) {
            if (name == null) { return null; }
            try { return valueOf(name); } catch (RuntimeException ignored) { return null; }
        }

        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<Skill> listAll() {
            return new ArrayList<Skill>(Arrays.asList(values()));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if not found, throws exception)
         */
        public static List<Skill> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            if ("availableWerewolfSay".equalsIgnoreCase(groupName)) { return listOfAvailableWerewolfSay(); }
            if ("hasDivineAbility".equalsIgnoreCase(groupName)) { return listOfHasDivineAbility(); }
            if ("hasSkillPsychicAbility".equalsIgnoreCase(groupName)) { return listOfHasSkillPsychicAbility(); }
            if ("hasAttackAbility".equalsIgnoreCase(groupName)) { return listOfHasAttackAbility(); }
            if ("hasMadmanAbility".equalsIgnoreCase(groupName)) { return listOfHasMadmanAbility(); }
            if ("hasDisturbAbility".equalsIgnoreCase(groupName)) { return listOfHasDisturbAbility(); }
            if ("noDeadByAttack".equalsIgnoreCase(groupName)) { return listOfNoDeadByAttack(); }
            if ("wolfCount".equalsIgnoreCase(groupName)) { return listOfWolfCount(); }
            if ("noCount".equalsIgnoreCase(groupName)) { return listOfNoCount(); }
            if ("viewableWolfCharaName".equalsIgnoreCase(groupName)) { return listOfViewableWolfCharaName(); }
            if ("divineResultWolf".equalsIgnoreCase(groupName)) { return listOfDivineResultWolf(); }
            if ("psychicResultWolf".equalsIgnoreCase(groupName)) { return listOfPsychicResultWolf(); }
            if ("someoneSkill".equalsIgnoreCase(groupName)) { return listOfSomeoneSkill(); }
            throw new ClassificationNotFoundException("Unknown classification group: Skill." + groupName);
        }

        /**
         * Get the list of classification elements corresponding to the specified codes. (returns new copied list) <br>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         */
        public static List<Skill> listOf(Collection<String> codeList) {
            if (codeList == null) { throw new IllegalArgumentException("The argument 'codeList' should not be null."); }
            List<Skill> clsList = new ArrayList<Skill>(codeList.size());
            for (String code : codeList) { clsList.add(of(code).get()); }
            return clsList;
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 囁き可能 <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, C国狂人]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfAvailableWerewolfSay() {
            return new ArrayList<Skill>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼, C国狂人));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 占い能力を持つ <br>
         * The group elements:[占い師, 賢者, 占星術師]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfHasDivineAbility() {
            return new ArrayList<Skill>(Arrays.asList(占い師, 賢者, 占星術師));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 役職霊能能力を持つ <br>
         * The group elements:[導師, 魔神官]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfHasSkillPsychicAbility() {
            return new ArrayList<Skill>(Arrays.asList(導師, 魔神官));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 襲撃能力を持つ <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfHasAttackAbility() {
            return new ArrayList<Skill>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 狂人能力を持つ <br>
         * The group elements:[C国狂人, 狂人, 狂信者, 魔神官]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfHasMadmanAbility() {
            return new ArrayList<Skill>(Arrays.asList(C国狂人, 狂人, 狂信者, 魔神官));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 徘徊能力を持つ <br>
         * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 妖狐, 背徳者]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfHasDisturbAbility() {
            return new ArrayList<Skill>(Arrays.asList(C国狂人, 狂人, 狂信者, 魔神官, 妖狐, 背徳者));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 襲撃されても死なない <br>
         * The group elements:[妖狐, 誑狐, 爆弾魔]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfNoDeadByAttack() {
            return new ArrayList<Skill>(Arrays.asList(妖狐, 誑狐, 爆弾魔));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 勝敗判定時、人狼にカウントされる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfWolfCount() {
            return new ArrayList<Skill>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 勝敗判定時、人間にも人狼にもカウントされない <br>
         * The group elements:[妖狐, 誑狐, 梟]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfNoCount() {
            return new ArrayList<Skill>(Arrays.asList(妖狐, 誑狐, 梟));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 人狼が誰かを知ることができる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, C国狂人, 狂信者]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfViewableWolfCharaName() {
            return new ArrayList<Skill>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼, C国狂人, 狂信者));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 占い結果が人狼となる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 一匹狼]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfDivineResultWolf() {
            return new ArrayList<Skill>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼, 一匹狼));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 霊能結果が人狼となる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 一匹狼]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfPsychicResultWolf() {
            return new ArrayList<Skill>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼, 一匹狼));
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * おまかせ系 <br>
         * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfSomeoneSkill() {
            return new ArrayList<Skill>(Arrays.asList(おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         */
        public static List<Skill> groupOf(String groupName) {
            if ("availableWerewolfSay".equals(groupName)) { return listOfAvailableWerewolfSay(); }
            if ("hasDivineAbility".equals(groupName)) { return listOfHasDivineAbility(); }
            if ("hasSkillPsychicAbility".equals(groupName)) { return listOfHasSkillPsychicAbility(); }
            if ("hasAttackAbility".equals(groupName)) { return listOfHasAttackAbility(); }
            if ("hasMadmanAbility".equals(groupName)) { return listOfHasMadmanAbility(); }
            if ("hasDisturbAbility".equals(groupName)) { return listOfHasDisturbAbility(); }
            if ("noDeadByAttack".equals(groupName)) { return listOfNoDeadByAttack(); }
            if ("wolfCount".equals(groupName)) { return listOfWolfCount(); }
            if ("noCount".equals(groupName)) { return listOfNoCount(); }
            if ("viewableWolfCharaName".equals(groupName)) { return listOfViewableWolfCharaName(); }
            if ("divineResultWolf".equals(groupName)) { return listOfDivineResultWolf(); }
            if ("psychicResultWolf".equals(groupName)) { return listOfPsychicResultWolf(); }
            if ("someoneSkill".equals(groupName)) { return listOfSomeoneSkill(); }
            return new ArrayList<Skill>(4);
        }

        @Override public String toString() { return code(); }
    }

    /**
     * メッセージ種別
     */
    public enum MessageType implements CDef {
        /** アクション */
        アクション("ACTION", "アクション", emptyStrings())
        ,
        /** 村建て発言 */
        村建て発言("CREATOR_SAY", "村建て発言", emptyStrings())
        ,
        /** 死者の呻き */
        死者の呻き("GRAVE_SAY", "死者の呻き", emptyStrings())
        ,
        /** 恋人発言 */
        恋人発言("LOVERS_SAY", "恋人発言", emptyStrings())
        ,
        /** 共鳴発言 */
        共鳴発言("MASON_SAY", "共鳴発言", emptyStrings())
        ,
        /** 独り言 */
        独り言("MONOLOGUE_SAY", "独り言", emptyStrings())
        ,
        /** 通常発言 */
        通常発言("NORMAL_SAY", "通常発言", emptyStrings())
        ,
        /** 検死結果 */
        検死結果("PRIVATE_CORONER", "検死結果", emptyStrings())
        ,
        /** 妖狐メッセージ */
        妖狐メッセージ("PRIVATE_FOX", "妖狐メッセージ", emptyStrings())
        ,
        /** 役職霊視結果 */
        役職霊視結果("PRIVATE_GURU", "役職霊視結果", emptyStrings())
        ,
        /** 足音調査結果 */
        足音調査結果("PRIVATE_INVESTIGATE", "足音調査結果", emptyStrings())
        ,
        /** 恋人メッセージ */
        恋人メッセージ("PRIVATE_LOVER", "恋人メッセージ", emptyStrings())
        ,
        /** 白黒霊視結果 */
        白黒霊視結果("PRIVATE_PSYCHIC", "白黒霊視結果", emptyStrings())
        ,
        /** 白黒占い結果 */
        白黒占い結果("PRIVATE_SEER", "白黒占い結果", emptyStrings())
        ,
        /** 非公開システムメッセージ */
        非公開システムメッセージ("PRIVATE_SYSTEM", "非公開システムメッセージ", emptyStrings())
        ,
        /** 襲撃結果 */
        襲撃結果("PRIVATE_WEREWOLF", "襲撃結果", emptyStrings())
        ,
        /** 役職占い結果 */
        役職占い結果("PRIVATE_WISE", "役職占い結果", emptyStrings())
        ,
        /** 公開システムメッセージ */
        公開システムメッセージ("PUBLIC_SYSTEM", "公開システムメッセージ", emptyStrings())
        ,
        /** 秘話 */
        秘話("SECRET_SAY", "秘話", emptyStrings())
        ,
        /** 見学発言 */
        見学発言("SPECTATE_SAY", "見学発言", emptyStrings())
        ,
        /** 人狼の囁き */
        人狼の囁き("WEREWOLF_SAY", "人狼の囁き", emptyStrings())
        ;
        private static final Map<String, MessageType> _codeClsMap = new HashMap<String, MessageType>();
        private static final Map<String, MessageType> _nameClsMap = new HashMap<String, MessageType>();
        static {
            for (MessageType value : values()) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) { _codeClsMap.put(sister.toLowerCase(), value); }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }
        private String _code; private String _alias; private Set<String> _sisterSet;
        private MessageType(String code, String alias, String[] sisters)
        { _code = code; _alias = alias; _sisterSet = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters))); }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return _sisterSet; }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.MessageType; }

        public boolean inGroup(String groupName) {
            return false;
        }

        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<MessageType> of(Object code) {
            if (code == null) { return OptionalThing.ofNullable(null, () -> { throw new ClassificationNotFoundException("null code specified"); }); }
            if (code instanceof MessageType) { return OptionalThing.of((MessageType)code); }
            if (code instanceof OptionalThing<?>) { return of(((OptionalThing<?>)code).orElse(null)); }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<MessageType> byName(String name) {
            if (name == null) { throw new IllegalArgumentException("The argument 'name' should not be null."); }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span> <br>
         * Get the classification by the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static MessageType codeOf(Object code) {
            if (code == null) { return null; }
            if (code instanceof MessageType) { return (MessageType)code; }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span> <br>
         * Get the classification by the name (also called 'value' in ENUM world).
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         */
        public static MessageType nameOf(String name) {
            if (name == null) { return null; }
            try { return valueOf(name); } catch (RuntimeException ignored) { return null; }
        }

        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<MessageType> listAll() {
            return new ArrayList<MessageType>(Arrays.asList(values()));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if not found, throws exception)
         */
        public static List<MessageType> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: MessageType." + groupName);
        }

        /**
         * Get the list of classification elements corresponding to the specified codes. (returns new copied list) <br>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         */
        public static List<MessageType> listOf(Collection<String> codeList) {
            if (codeList == null) { throw new IllegalArgumentException("The argument 'codeList' should not be null."); }
            List<MessageType> clsList = new ArrayList<MessageType>(codeList.size());
            for (String code : codeList) { clsList.add(of(code).get()); }
            return clsList;
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         */
        public static List<MessageType> groupOf(String groupName) {
            return new ArrayList<MessageType>(4);
        }

        @Override public String toString() { return code(); }
    }

    /**
     * 死亡理由
     */
    public enum DeadReason implements CDef {
        /** 襲撃 */
        襲撃("ATTACK", "襲撃", emptyStrings())
        ,
        /** 爆死 */
        爆死("BOMBED", "爆死", emptyStrings())
        ,
        /** 呪殺 */
        呪殺("DIVINED", "呪殺", emptyStrings())
        ,
        /** 処刑 */
        処刑("EXECUTE", "処刑", emptyStrings())
        ,
        /** 突然 */
        突然("SUDDON", "突然", emptyStrings())
        ,
        /** 後追 */
        後追("SUICIDE", "後追", emptyStrings())
        ,
        /** 罠死 */
        罠死("TRAPPED", "罠死", emptyStrings())
        ;
        private static final Map<String, DeadReason> _codeClsMap = new HashMap<String, DeadReason>();
        private static final Map<String, DeadReason> _nameClsMap = new HashMap<String, DeadReason>();
        static {
            for (DeadReason value : values()) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) { _codeClsMap.put(sister.toLowerCase(), value); }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }
        private String _code; private String _alias; private Set<String> _sisterSet;
        private DeadReason(String code, String alias, String[] sisters)
        { _code = code; _alias = alias; _sisterSet = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters))); }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return _sisterSet; }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.DeadReason; }

        /**
         * Is the classification in the group? <br>
         * 無惨 <br>
         * The group elements:[襲撃, 呪殺, 罠死, 爆死]
         * @return The determination, true or false.
         */
        public boolean isMiserable() {
            return 襲撃.equals(this) || 呪殺.equals(this) || 罠死.equals(this) || 爆死.equals(this);
        }

        public boolean inGroup(String groupName) {
            if ("miserable".equals(groupName)) { return isMiserable(); }
            return false;
        }

        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<DeadReason> of(Object code) {
            if (code == null) { return OptionalThing.ofNullable(null, () -> { throw new ClassificationNotFoundException("null code specified"); }); }
            if (code instanceof DeadReason) { return OptionalThing.of((DeadReason)code); }
            if (code instanceof OptionalThing<?>) { return of(((OptionalThing<?>)code).orElse(null)); }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<DeadReason> byName(String name) {
            if (name == null) { throw new IllegalArgumentException("The argument 'name' should not be null."); }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span> <br>
         * Get the classification by the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static DeadReason codeOf(Object code) {
            if (code == null) { return null; }
            if (code instanceof DeadReason) { return (DeadReason)code; }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span> <br>
         * Get the classification by the name (also called 'value' in ENUM world).
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         */
        public static DeadReason nameOf(String name) {
            if (name == null) { return null; }
            try { return valueOf(name); } catch (RuntimeException ignored) { return null; }
        }

        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<DeadReason> listAll() {
            return new ArrayList<DeadReason>(Arrays.asList(values()));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if not found, throws exception)
         */
        public static List<DeadReason> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            if ("miserable".equalsIgnoreCase(groupName)) { return listOfMiserable(); }
            throw new ClassificationNotFoundException("Unknown classification group: DeadReason." + groupName);
        }

        /**
         * Get the list of classification elements corresponding to the specified codes. (returns new copied list) <br>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         */
        public static List<DeadReason> listOf(Collection<String> codeList) {
            if (codeList == null) { throw new IllegalArgumentException("The argument 'codeList' should not be null."); }
            List<DeadReason> clsList = new ArrayList<DeadReason>(codeList.size());
            for (String code : codeList) { clsList.add(of(code).get()); }
            return clsList;
        }

        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 無惨 <br>
         * The group elements:[襲撃, 呪殺, 罠死, 爆死]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<DeadReason> listOfMiserable() {
            return new ArrayList<DeadReason>(Arrays.asList(襲撃, 呪殺, 罠死, 爆死));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         */
        public static List<DeadReason> groupOf(String groupName) {
            if ("miserable".equals(groupName)) { return listOfMiserable(); }
            return new ArrayList<DeadReason>(4);
        }

        @Override public String toString() { return code(); }
    }

    /**
     * 能力種別
     */
    public enum AbilityType implements CDef {
        /** 襲撃 */
        襲撃("ATTACK", "襲撃", emptyStrings())
        ,
        /** 爆弾設置 */
        爆弾設置("BOMB", "爆弾設置", emptyStrings())
        ,
        /** 誑かす */
        誑かす("CHEAT", "誑かす", emptyStrings())
        ,
        /** 同棲 */
        同棲("COHABIT", "同棲", emptyStrings())
        ,
        /** 指揮 */
        指揮("COMMAND", "指揮", emptyStrings())
        ,
        /** 求愛 */
        求愛("COURT", "求愛", emptyStrings())
        ,
        /** 占い */
        占い("DIVINE", "占い", emptyStrings())
        ,
        /** フルーツバスケット */
        フルーツバスケット("FRUITSBASKET", "フルーツバスケット", emptyStrings())
        ,
        /** 護衛 */
        護衛("GUARD", "護衛", emptyStrings())
        ,
        /** 捜査 */
        捜査("INVESTIGATE", "捜査", emptyStrings())
        ,
        /** 単独襲撃 */
        単独襲撃("LONEATTACK", "単独襲撃", emptyStrings())
        ,
        /** ストーキング */
        ストーキング("STALKING", "ストーキング", emptyStrings())
        ,
        /** 罠設置 */
        罠設置("TRAP", "罠設置", emptyStrings())
        ;
        private static final Map<String, AbilityType> _codeClsMap = new HashMap<String, AbilityType>();
        private static final Map<String, AbilityType> _nameClsMap = new HashMap<String, AbilityType>();
        static {
            for (AbilityType value : values()) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) { _codeClsMap.put(sister.toLowerCase(), value); }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }
        private String _code; private String _alias; private Set<String> _sisterSet;
        private AbilityType(String code, String alias, String[] sisters)
        { _code = code; _alias = alias; _sisterSet = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters))); }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return _sisterSet; }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.AbilityType; }

        public boolean inGroup(String groupName) {
            return false;
        }

        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<AbilityType> of(Object code) {
            if (code == null) { return OptionalThing.ofNullable(null, () -> { throw new ClassificationNotFoundException("null code specified"); }); }
            if (code instanceof AbilityType) { return OptionalThing.of((AbilityType)code); }
            if (code instanceof OptionalThing<?>) { return of(((OptionalThing<?>)code).orElse(null)); }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<AbilityType> byName(String name) {
            if (name == null) { throw new IllegalArgumentException("The argument 'name' should not be null."); }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span> <br>
         * Get the classification by the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static AbilityType codeOf(Object code) {
            if (code == null) { return null; }
            if (code instanceof AbilityType) { return (AbilityType)code; }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span> <br>
         * Get the classification by the name (also called 'value' in ENUM world).
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         */
        public static AbilityType nameOf(String name) {
            if (name == null) { return null; }
            try { return valueOf(name); } catch (RuntimeException ignored) { return null; }
        }

        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<AbilityType> listAll() {
            return new ArrayList<AbilityType>(Arrays.asList(values()));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if not found, throws exception)
         */
        public static List<AbilityType> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: AbilityType." + groupName);
        }

        /**
         * Get the list of classification elements corresponding to the specified codes. (returns new copied list) <br>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         */
        public static List<AbilityType> listOf(Collection<String> codeList) {
            if (codeList == null) { throw new IllegalArgumentException("The argument 'codeList' should not be null."); }
            List<AbilityType> clsList = new ArrayList<AbilityType>(codeList.size());
            for (String code : codeList) { clsList.add(of(code).get()); }
            return clsList;
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         */
        public static List<AbilityType> groupOf(String groupName) {
            return new ArrayList<AbilityType>(4);
        }

        @Override public String toString() { return code(); }
    }

    /**
     * 秘話可能範囲
     */
    public enum AllowedSecretSay implements CDef {
        /** 全員 */
        全員("EVERYTHING", "全員", emptyStrings())
        ,
        /** なし */
        なし("NOTHING", "なし", emptyStrings())
        ,
        /** 村建てとのみ */
        村建てとのみ("ONLY_CREATOR", "村建てとのみ", emptyStrings())
        ;
        private static final Map<String, AllowedSecretSay> _codeClsMap = new HashMap<String, AllowedSecretSay>();
        private static final Map<String, AllowedSecretSay> _nameClsMap = new HashMap<String, AllowedSecretSay>();
        static {
            for (AllowedSecretSay value : values()) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) { _codeClsMap.put(sister.toLowerCase(), value); }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }
        private String _code; private String _alias; private Set<String> _sisterSet;
        private AllowedSecretSay(String code, String alias, String[] sisters)
        { _code = code; _alias = alias; _sisterSet = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters))); }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return _sisterSet; }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.AllowedSecretSay; }

        public boolean inGroup(String groupName) {
            return false;
        }

        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<AllowedSecretSay> of(Object code) {
            if (code == null) { return OptionalThing.ofNullable(null, () -> { throw new ClassificationNotFoundException("null code specified"); }); }
            if (code instanceof AllowedSecretSay) { return OptionalThing.of((AllowedSecretSay)code); }
            if (code instanceof OptionalThing<?>) { return of(((OptionalThing<?>)code).orElse(null)); }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<AllowedSecretSay> byName(String name) {
            if (name == null) { throw new IllegalArgumentException("The argument 'name' should not be null."); }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span> <br>
         * Get the classification by the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static AllowedSecretSay codeOf(Object code) {
            if (code == null) { return null; }
            if (code instanceof AllowedSecretSay) { return (AllowedSecretSay)code; }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span> <br>
         * Get the classification by the name (also called 'value' in ENUM world).
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         */
        public static AllowedSecretSay nameOf(String name) {
            if (name == null) { return null; }
            try { return valueOf(name); } catch (RuntimeException ignored) { return null; }
        }

        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<AllowedSecretSay> listAll() {
            return new ArrayList<AllowedSecretSay>(Arrays.asList(values()));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if not found, throws exception)
         */
        public static List<AllowedSecretSay> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: AllowedSecretSay." + groupName);
        }

        /**
         * Get the list of classification elements corresponding to the specified codes. (returns new copied list) <br>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         */
        public static List<AllowedSecretSay> listOf(Collection<String> codeList) {
            if (codeList == null) { throw new IllegalArgumentException("The argument 'codeList' should not be null."); }
            List<AllowedSecretSay> clsList = new ArrayList<AllowedSecretSay>(codeList.size());
            for (String code : codeList) { clsList.add(of(code).get()); }
            return clsList;
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         */
        public static List<AllowedSecretSay> groupOf(String groupName) {
            return new ArrayList<AllowedSecretSay>(4);
        }

        @Override public String toString() { return code(); }
    }

    /**
     * 表情種別
     */
    public enum FaceType implements CDef {
        /** 墓下 */
        墓下("GRAVE", "墓下", emptyStrings())
        ,
        /** 共鳴 */
        共鳴("MASON", "共鳴", emptyStrings())
        ,
        /** 独り言 */
        独り言("MONOLOGUE", "独り言", emptyStrings())
        ,
        /** 通常 */
        通常("NORMAL", "通常", emptyStrings())
        ,
        /** 秘話 */
        秘話("SECRET", "秘話", emptyStrings())
        ,
        /** 囁き */
        囁き("WEREWOLF", "囁き", emptyStrings())
        ;
        private static final Map<String, FaceType> _codeClsMap = new HashMap<String, FaceType>();
        private static final Map<String, FaceType> _nameClsMap = new HashMap<String, FaceType>();
        static {
            for (FaceType value : values()) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) { _codeClsMap.put(sister.toLowerCase(), value); }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }
        private String _code; private String _alias; private Set<String> _sisterSet;
        private FaceType(String code, String alias, String[] sisters)
        { _code = code; _alias = alias; _sisterSet = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters))); }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return _sisterSet; }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.FaceType; }

        public boolean inGroup(String groupName) {
            return false;
        }

        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<FaceType> of(Object code) {
            if (code == null) { return OptionalThing.ofNullable(null, () -> { throw new ClassificationNotFoundException("null code specified"); }); }
            if (code instanceof FaceType) { return OptionalThing.of((FaceType)code); }
            if (code instanceof OptionalThing<?>) { return of(((OptionalThing<?>)code).orElse(null)); }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<FaceType> byName(String name) {
            if (name == null) { throw new IllegalArgumentException("The argument 'name' should not be null."); }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span> <br>
         * Get the classification by the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static FaceType codeOf(Object code) {
            if (code == null) { return null; }
            if (code instanceof FaceType) { return (FaceType)code; }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span> <br>
         * Get the classification by the name (also called 'value' in ENUM world).
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         */
        public static FaceType nameOf(String name) {
            if (name == null) { return null; }
            try { return valueOf(name); } catch (RuntimeException ignored) { return null; }
        }

        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<FaceType> listAll() {
            return new ArrayList<FaceType>(Arrays.asList(values()));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if not found, throws exception)
         */
        public static List<FaceType> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: FaceType." + groupName);
        }

        /**
         * Get the list of classification elements corresponding to the specified codes. (returns new copied list) <br>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         */
        public static List<FaceType> listOf(Collection<String> codeList) {
            if (codeList == null) { throw new IllegalArgumentException("The argument 'codeList' should not be null."); }
            List<FaceType> clsList = new ArrayList<FaceType>(codeList.size());
            for (String code : codeList) { clsList.add(of(code).get()); }
            return clsList;
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         */
        public static List<FaceType> groupOf(String groupName) {
            return new ArrayList<FaceType>(4);
        }

        @Override public String toString() { return code(); }
    }

    /**
     * 村参加者ステータス種別
     */
    public enum VillagePlayerStatusType implements CDef {
        /** 後追い */
        後追い("FOLLOWING_SUICIDE", "後追い", emptyStrings())
        ,
        /** 狐憑き */
        狐憑き("FOX_POSSESSION", "狐憑き", emptyStrings())
        ;
        private static final Map<String, VillagePlayerStatusType> _codeClsMap = new HashMap<String, VillagePlayerStatusType>();
        private static final Map<String, VillagePlayerStatusType> _nameClsMap = new HashMap<String, VillagePlayerStatusType>();
        static {
            for (VillagePlayerStatusType value : values()) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) { _codeClsMap.put(sister.toLowerCase(), value); }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }
        private String _code; private String _alias; private Set<String> _sisterSet;
        private VillagePlayerStatusType(String code, String alias, String[] sisters)
        { _code = code; _alias = alias; _sisterSet = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters))); }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return _sisterSet; }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.VillagePlayerStatusType; }

        public boolean inGroup(String groupName) {
            return false;
        }

        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<VillagePlayerStatusType> of(Object code) {
            if (code == null) { return OptionalThing.ofNullable(null, () -> { throw new ClassificationNotFoundException("null code specified"); }); }
            if (code instanceof VillagePlayerStatusType) { return OptionalThing.of((VillagePlayerStatusType)code); }
            if (code instanceof OptionalThing<?>) { return of(((OptionalThing<?>)code).orElse(null)); }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<VillagePlayerStatusType> byName(String name) {
            if (name == null) { throw new IllegalArgumentException("The argument 'name' should not be null."); }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () ->{
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span> <br>
         * Get the classification by the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static VillagePlayerStatusType codeOf(Object code) {
            if (code == null) { return null; }
            if (code instanceof VillagePlayerStatusType) { return (VillagePlayerStatusType)code; }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span> <br>
         * Get the classification by the name (also called 'value' in ENUM world).
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         */
        public static VillagePlayerStatusType nameOf(String name) {
            if (name == null) { return null; }
            try { return valueOf(name); } catch (RuntimeException ignored) { return null; }
        }

        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<VillagePlayerStatusType> listAll() {
            return new ArrayList<VillagePlayerStatusType>(Arrays.asList(values()));
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if not found, throws exception)
         */
        public static List<VillagePlayerStatusType> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: VillagePlayerStatusType." + groupName);
        }

        /**
         * Get the list of classification elements corresponding to the specified codes. (returns new copied list) <br>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         */
        public static List<VillagePlayerStatusType> listOf(Collection<String> codeList) {
            if (codeList == null) { throw new IllegalArgumentException("The argument 'codeList' should not be null."); }
            List<VillagePlayerStatusType> clsList = new ArrayList<VillagePlayerStatusType>(codeList.size());
            for (String code : codeList) { clsList.add(of(code).get()); }
            return clsList;
        }

        /**
         * Get the list of classification elements in the specified group. (returns new copied list) <br>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         */
        public static List<VillagePlayerStatusType> groupOf(String groupName) {
            return new ArrayList<VillagePlayerStatusType>(4);
        }

        @Override public String toString() { return code(); }
    }

    public enum DefMeta implements ClassificationMeta {
        /** フラグを示す */
        Flg
        ,
        /** 権限 */
        Authority
        ,
        /** 陣営 */
        Camp
        ,
        /** 村ステータス */
        VillageStatus
        ,
        /** 役職 */
        Skill
        ,
        /** メッセージ種別 */
        MessageType
        ,
        /** 死亡理由 */
        DeadReason
        ,
        /** 能力種別 */
        AbilityType
        ,
        /** 秘話可能範囲 */
        AllowedSecretSay
        ,
        /** 表情種別 */
        FaceType
        ,
        /** 村参加者ステータス種別 */
        VillagePlayerStatusType
        ;
        public String classificationName() {
            return name(); // same as definition name
        }

        public OptionalThing<? extends Classification> of(Object code) {
            if (Flg.name().equals(name())) { return CDef.Flg.of(code); }
            if (Authority.name().equals(name())) { return CDef.Authority.of(code); }
            if (Camp.name().equals(name())) { return CDef.Camp.of(code); }
            if (VillageStatus.name().equals(name())) { return CDef.VillageStatus.of(code); }
            if (Skill.name().equals(name())) { return CDef.Skill.of(code); }
            if (MessageType.name().equals(name())) { return CDef.MessageType.of(code); }
            if (DeadReason.name().equals(name())) { return CDef.DeadReason.of(code); }
            if (AbilityType.name().equals(name())) { return CDef.AbilityType.of(code); }
            if (AllowedSecretSay.name().equals(name())) { return CDef.AllowedSecretSay.of(code); }
            if (FaceType.name().equals(name())) { return CDef.FaceType.of(code); }
            if (VillagePlayerStatusType.name().equals(name())) { return CDef.VillagePlayerStatusType.of(code); }
            throw new IllegalStateException("Unknown definition: " + this); // basically unreachable
        }

        public OptionalThing<? extends Classification> byName(String name) {
            if (Flg.name().equals(name())) { return CDef.Flg.byName(name); }
            if (Authority.name().equals(name())) { return CDef.Authority.byName(name); }
            if (Camp.name().equals(name())) { return CDef.Camp.byName(name); }
            if (VillageStatus.name().equals(name())) { return CDef.VillageStatus.byName(name); }
            if (Skill.name().equals(name())) { return CDef.Skill.byName(name); }
            if (MessageType.name().equals(name())) { return CDef.MessageType.byName(name); }
            if (DeadReason.name().equals(name())) { return CDef.DeadReason.byName(name); }
            if (AbilityType.name().equals(name())) { return CDef.AbilityType.byName(name); }
            if (AllowedSecretSay.name().equals(name())) { return CDef.AllowedSecretSay.byName(name); }
            if (FaceType.name().equals(name())) { return CDef.FaceType.byName(name); }
            if (VillagePlayerStatusType.name().equals(name())) { return CDef.VillagePlayerStatusType.byName(name); }
            throw new IllegalStateException("Unknown definition: " + this); // basically unreachable
        }

        public Classification codeOf(Object code) { // null if not found, old style so use of(code)
            if (Flg.name().equals(name())) { return CDef.Flg.codeOf(code); }
            if (Authority.name().equals(name())) { return CDef.Authority.codeOf(code); }
            if (Camp.name().equals(name())) { return CDef.Camp.codeOf(code); }
            if (VillageStatus.name().equals(name())) { return CDef.VillageStatus.codeOf(code); }
            if (Skill.name().equals(name())) { return CDef.Skill.codeOf(code); }
            if (MessageType.name().equals(name())) { return CDef.MessageType.codeOf(code); }
            if (DeadReason.name().equals(name())) { return CDef.DeadReason.codeOf(code); }
            if (AbilityType.name().equals(name())) { return CDef.AbilityType.codeOf(code); }
            if (AllowedSecretSay.name().equals(name())) { return CDef.AllowedSecretSay.codeOf(code); }
            if (FaceType.name().equals(name())) { return CDef.FaceType.codeOf(code); }
            if (VillagePlayerStatusType.name().equals(name())) { return CDef.VillagePlayerStatusType.codeOf(code); }
            throw new IllegalStateException("Unknown definition: " + this); // basically unreachable
        }

        public Classification nameOf(String name) { // null if not found, old style so use byName(name)
            if (Flg.name().equals(name())) { return CDef.Flg.valueOf(name); }
            if (Authority.name().equals(name())) { return CDef.Authority.valueOf(name); }
            if (Camp.name().equals(name())) { return CDef.Camp.valueOf(name); }
            if (VillageStatus.name().equals(name())) { return CDef.VillageStatus.valueOf(name); }
            if (Skill.name().equals(name())) { return CDef.Skill.valueOf(name); }
            if (MessageType.name().equals(name())) { return CDef.MessageType.valueOf(name); }
            if (DeadReason.name().equals(name())) { return CDef.DeadReason.valueOf(name); }
            if (AbilityType.name().equals(name())) { return CDef.AbilityType.valueOf(name); }
            if (AllowedSecretSay.name().equals(name())) { return CDef.AllowedSecretSay.valueOf(name); }
            if (FaceType.name().equals(name())) { return CDef.FaceType.valueOf(name); }
            if (VillagePlayerStatusType.name().equals(name())) { return CDef.VillagePlayerStatusType.valueOf(name); }
            throw new IllegalStateException("Unknown definition: " + this); // basically unreachable
        }

        public List<Classification> listAll() {
            if (Flg.name().equals(name())) { return toClsList(CDef.Flg.listAll()); }
            if (Authority.name().equals(name())) { return toClsList(CDef.Authority.listAll()); }
            if (Camp.name().equals(name())) { return toClsList(CDef.Camp.listAll()); }
            if (VillageStatus.name().equals(name())) { return toClsList(CDef.VillageStatus.listAll()); }
            if (Skill.name().equals(name())) { return toClsList(CDef.Skill.listAll()); }
            if (MessageType.name().equals(name())) { return toClsList(CDef.MessageType.listAll()); }
            if (DeadReason.name().equals(name())) { return toClsList(CDef.DeadReason.listAll()); }
            if (AbilityType.name().equals(name())) { return toClsList(CDef.AbilityType.listAll()); }
            if (AllowedSecretSay.name().equals(name())) { return toClsList(CDef.AllowedSecretSay.listAll()); }
            if (FaceType.name().equals(name())) { return toClsList(CDef.FaceType.listAll()); }
            if (VillagePlayerStatusType.name().equals(name())) { return toClsList(CDef.VillagePlayerStatusType.listAll()); }
            throw new IllegalStateException("Unknown definition: " + this); // basically unreachable
        }

        public List<Classification> listByGroup(String groupName) { // exception if not found
            if (Flg.name().equals(name())) { return toClsList(CDef.Flg.listByGroup(groupName)); }
            if (Authority.name().equals(name())) { return toClsList(CDef.Authority.listByGroup(groupName)); }
            if (Camp.name().equals(name())) { return toClsList(CDef.Camp.listByGroup(groupName)); }
            if (VillageStatus.name().equals(name())) { return toClsList(CDef.VillageStatus.listByGroup(groupName)); }
            if (Skill.name().equals(name())) { return toClsList(CDef.Skill.listByGroup(groupName)); }
            if (MessageType.name().equals(name())) { return toClsList(CDef.MessageType.listByGroup(groupName)); }
            if (DeadReason.name().equals(name())) { return toClsList(CDef.DeadReason.listByGroup(groupName)); }
            if (AbilityType.name().equals(name())) { return toClsList(CDef.AbilityType.listByGroup(groupName)); }
            if (AllowedSecretSay.name().equals(name())) { return toClsList(CDef.AllowedSecretSay.listByGroup(groupName)); }
            if (FaceType.name().equals(name())) { return toClsList(CDef.FaceType.listByGroup(groupName)); }
            if (VillagePlayerStatusType.name().equals(name())) { return toClsList(CDef.VillagePlayerStatusType.listByGroup(groupName)); }
            throw new IllegalStateException("Unknown definition: " + this); // basically unreachable
        }

        public List<Classification> listOf(Collection<String> codeList) {
            if (Flg.name().equals(name())) { return toClsList(CDef.Flg.listOf(codeList)); }
            if (Authority.name().equals(name())) { return toClsList(CDef.Authority.listOf(codeList)); }
            if (Camp.name().equals(name())) { return toClsList(CDef.Camp.listOf(codeList)); }
            if (VillageStatus.name().equals(name())) { return toClsList(CDef.VillageStatus.listOf(codeList)); }
            if (Skill.name().equals(name())) { return toClsList(CDef.Skill.listOf(codeList)); }
            if (MessageType.name().equals(name())) { return toClsList(CDef.MessageType.listOf(codeList)); }
            if (DeadReason.name().equals(name())) { return toClsList(CDef.DeadReason.listOf(codeList)); }
            if (AbilityType.name().equals(name())) { return toClsList(CDef.AbilityType.listOf(codeList)); }
            if (AllowedSecretSay.name().equals(name())) { return toClsList(CDef.AllowedSecretSay.listOf(codeList)); }
            if (FaceType.name().equals(name())) { return toClsList(CDef.FaceType.listOf(codeList)); }
            if (VillagePlayerStatusType.name().equals(name())) { return toClsList(CDef.VillagePlayerStatusType.listOf(codeList)); }
            throw new IllegalStateException("Unknown definition: " + this); // basically unreachable
        }

        public List<Classification> groupOf(String groupName) { // old style
            if (Flg.name().equals(name())) { return toClsList(CDef.Flg.groupOf(groupName)); }
            if (Authority.name().equals(name())) { return toClsList(CDef.Authority.groupOf(groupName)); }
            if (Camp.name().equals(name())) { return toClsList(CDef.Camp.groupOf(groupName)); }
            if (VillageStatus.name().equals(name())) { return toClsList(CDef.VillageStatus.groupOf(groupName)); }
            if (Skill.name().equals(name())) { return toClsList(CDef.Skill.groupOf(groupName)); }
            if (MessageType.name().equals(name())) { return toClsList(CDef.MessageType.groupOf(groupName)); }
            if (DeadReason.name().equals(name())) { return toClsList(CDef.DeadReason.groupOf(groupName)); }
            if (AbilityType.name().equals(name())) { return toClsList(CDef.AbilityType.groupOf(groupName)); }
            if (AllowedSecretSay.name().equals(name())) { return toClsList(CDef.AllowedSecretSay.groupOf(groupName)); }
            if (FaceType.name().equals(name())) { return toClsList(CDef.FaceType.groupOf(groupName)); }
            if (VillagePlayerStatusType.name().equals(name())) { return toClsList(CDef.VillagePlayerStatusType.groupOf(groupName)); }
            throw new IllegalStateException("Unknown definition: " + this); // basically unreachable
        }

        @SuppressWarnings("unchecked")
        private List<Classification> toClsList(List<?> clsList) {
            return (List<Classification>)clsList;
        }

        public ClassificationCodeType codeType() {
            if (Flg.name().equals(name())) { return ClassificationCodeType.Boolean; }
            if (Authority.name().equals(name())) { return ClassificationCodeType.String; }
            if (Camp.name().equals(name())) { return ClassificationCodeType.String; }
            if (VillageStatus.name().equals(name())) { return ClassificationCodeType.String; }
            if (Skill.name().equals(name())) { return ClassificationCodeType.String; }
            if (MessageType.name().equals(name())) { return ClassificationCodeType.String; }
            if (DeadReason.name().equals(name())) { return ClassificationCodeType.String; }
            if (AbilityType.name().equals(name())) { return ClassificationCodeType.String; }
            if (AllowedSecretSay.name().equals(name())) { return ClassificationCodeType.String; }
            if (FaceType.name().equals(name())) { return ClassificationCodeType.String; }
            if (VillagePlayerStatusType.name().equals(name())) { return ClassificationCodeType.String; }
            return ClassificationCodeType.String; // as default
        }

        public ClassificationUndefinedHandlingType undefinedHandlingType() {
            if (Flg.name().equals(name())) { return ClassificationUndefinedHandlingType.LOGGING; }
            if (Authority.name().equals(name())) { return ClassificationUndefinedHandlingType.LOGGING; }
            if (Camp.name().equals(name())) { return ClassificationUndefinedHandlingType.LOGGING; }
            if (VillageStatus.name().equals(name())) { return ClassificationUndefinedHandlingType.LOGGING; }
            if (Skill.name().equals(name())) { return ClassificationUndefinedHandlingType.LOGGING; }
            if (MessageType.name().equals(name())) { return ClassificationUndefinedHandlingType.LOGGING; }
            if (DeadReason.name().equals(name())) { return ClassificationUndefinedHandlingType.LOGGING; }
            if (AbilityType.name().equals(name())) { return ClassificationUndefinedHandlingType.LOGGING; }
            if (AllowedSecretSay.name().equals(name())) { return ClassificationUndefinedHandlingType.LOGGING; }
            if (FaceType.name().equals(name())) { return ClassificationUndefinedHandlingType.LOGGING; }
            if (VillagePlayerStatusType.name().equals(name())) { return ClassificationUndefinedHandlingType.LOGGING; }
            return ClassificationUndefinedHandlingType.LOGGING; // as default
        }

        public static OptionalThing<CDef.DefMeta> find(String classificationName) { // instead of valueOf()
            if (classificationName == null) { throw new IllegalArgumentException("The argument 'classificationName' should not be null."); }
            if (Flg.name().equalsIgnoreCase(classificationName)) { return OptionalThing.of(CDef.DefMeta.Flg); }
            if (Authority.name().equalsIgnoreCase(classificationName)) { return OptionalThing.of(CDef.DefMeta.Authority); }
            if (Camp.name().equalsIgnoreCase(classificationName)) { return OptionalThing.of(CDef.DefMeta.Camp); }
            if (VillageStatus.name().equalsIgnoreCase(classificationName)) { return OptionalThing.of(CDef.DefMeta.VillageStatus); }
            if (Skill.name().equalsIgnoreCase(classificationName)) { return OptionalThing.of(CDef.DefMeta.Skill); }
            if (MessageType.name().equalsIgnoreCase(classificationName)) { return OptionalThing.of(CDef.DefMeta.MessageType); }
            if (DeadReason.name().equalsIgnoreCase(classificationName)) { return OptionalThing.of(CDef.DefMeta.DeadReason); }
            if (AbilityType.name().equalsIgnoreCase(classificationName)) { return OptionalThing.of(CDef.DefMeta.AbilityType); }
            if (AllowedSecretSay.name().equalsIgnoreCase(classificationName)) { return OptionalThing.of(CDef.DefMeta.AllowedSecretSay); }
            if (FaceType.name().equalsIgnoreCase(classificationName)) { return OptionalThing.of(CDef.DefMeta.FaceType); }
            if (VillagePlayerStatusType.name().equalsIgnoreCase(classificationName)) { return OptionalThing.of(CDef.DefMeta.VillagePlayerStatusType); }
            return OptionalThing.ofNullable(null, () -> {
                throw new ClassificationNotFoundException("Unknown classification: " + classificationName);
            });
        }

        public static CDef.DefMeta meta(String classificationName) { // old style so use find(name)
            if (classificationName == null) { throw new IllegalArgumentException("The argument 'classificationName' should not be null."); }
            if (Flg.name().equalsIgnoreCase(classificationName)) { return CDef.DefMeta.Flg; }
            if (Authority.name().equalsIgnoreCase(classificationName)) { return CDef.DefMeta.Authority; }
            if (Camp.name().equalsIgnoreCase(classificationName)) { return CDef.DefMeta.Camp; }
            if (VillageStatus.name().equalsIgnoreCase(classificationName)) { return CDef.DefMeta.VillageStatus; }
            if (Skill.name().equalsIgnoreCase(classificationName)) { return CDef.DefMeta.Skill; }
            if (MessageType.name().equalsIgnoreCase(classificationName)) { return CDef.DefMeta.MessageType; }
            if (DeadReason.name().equalsIgnoreCase(classificationName)) { return CDef.DefMeta.DeadReason; }
            if (AbilityType.name().equalsIgnoreCase(classificationName)) { return CDef.DefMeta.AbilityType; }
            if (AllowedSecretSay.name().equalsIgnoreCase(classificationName)) { return CDef.DefMeta.AllowedSecretSay; }
            if (FaceType.name().equalsIgnoreCase(classificationName)) { return CDef.DefMeta.FaceType; }
            if (VillagePlayerStatusType.name().equalsIgnoreCase(classificationName)) { return CDef.DefMeta.VillagePlayerStatusType; }
            throw new IllegalStateException("Unknown classification: " + classificationName);
        }

        @SuppressWarnings("unused")
        private String[] xinternalEmptyString() {
            return emptyStrings(); // to suppress 'unused' warning of import statement
        }
    }
}

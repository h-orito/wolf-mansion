package com.ort.dbflute.allcommon;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import org.dbflute.exception.ClassificationNotFoundException;
import org.dbflute.jdbc.Classification;
import org.dbflute.jdbc.ClassificationCodeType;
import org.dbflute.jdbc.ClassificationMeta;
import org.dbflute.jdbc.ClassificationUndefinedHandlingType;
import org.dbflute.optional.OptionalThing;

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
        True("true", "はい"),
        /** いいえ: 無効を示す */
        False("false", "いいえ");
        private static ZzzoneSlimmer<Flg> _slimmer = new ZzzoneSlimmer<>(Flg.class, values());
        private String _code; private String _alias;
        private Flg(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.Flg; }
        public boolean inGroup(String groupName) { return false; }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Flg> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Flg> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static Flg codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static Flg nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<Flg> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<Flg> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: Flg." + groupName);
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<Flg> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<Flg> groupOf(String groupName) { return new ArrayList<>(); }
        @Override public String toString() { return code(); }
    }

    /**
     * 権限
     */
    public enum Authority implements CDef {
        /** 管理者 */
        管理者("ROLE_ADMIN", "管理者"),
        /** プレイヤー */
        プレイヤー("ROLE_PLAYER", "プレイヤー");
        private static ZzzoneSlimmer<Authority> _slimmer = new ZzzoneSlimmer<>(Authority.class, values());
        private String _code; private String _alias;
        private Authority(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.Authority; }
        public boolean inGroup(String groupName) { return false; }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Authority> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Authority> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static Authority codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static Authority nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<Authority> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<Authority> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: Authority." + groupName);
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<Authority> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<Authority> groupOf(String groupName) { return new ArrayList<>(); }
        @Override public String toString() { return code(); }
    }

    /**
     * 陣営
     */
    public enum Camp implements CDef {
        /** 愉快犯陣営 */
        愉快犯陣営("CRIMINAL", "愉快犯陣営"),
        /** 狐陣営 */
        狐陣営("FOX", "狐陣営"),
        /** 恋人陣営 */
        恋人陣営("LOVERS", "恋人陣営"),
        /** 村人陣営 */
        村人陣営("VILLAGER", "村人陣営"),
        /** 人狼陣営 */
        人狼陣営("WEREWOLF", "人狼陣営");
        private static ZzzoneSlimmer<Camp> _slimmer = new ZzzoneSlimmer<>(Camp.class, values());
        private String _code; private String _alias;
        private Camp(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.Camp; }
        public boolean inGroup(String groupName) { return false; }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Camp> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Camp> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static Camp codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static Camp nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<Camp> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<Camp> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: Camp." + groupName);
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<Camp> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<Camp> groupOf(String groupName) { return new ArrayList<>(); }
        @Override public String toString() { return code(); }
    }

    /**
     * 村ステータス
     */
    public enum VillageStatus implements CDef {
        /** 廃村 */
        廃村("CANCEL", "廃村"),
        /** 終了 */
        終了("COMPLETED", "終了"),
        /** エピローグ */
        エピローグ("EPILOGUE", "エピローグ"),
        /** 募集中 */
        募集中("IN_PREPARATION", "募集中"),
        /** 進行中 */
        進行中("IN_PROGRESS", "進行中"),
        /** 開始待ち */
        開始待ち("WAITING", "開始待ち");
        private static ZzzoneSlimmer<VillageStatus> _slimmer = new ZzzoneSlimmer<>(VillageStatus.class, values());
        private String _code; private String _alias;
        private VillageStatus(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.VillageStatus; }
        public boolean inGroup(String groupName) { return false; }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<VillageStatus> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<VillageStatus> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static VillageStatus codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static VillageStatus nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<VillageStatus> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<VillageStatus> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: VillageStatus." + groupName);
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<VillageStatus> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<VillageStatus> groupOf(String groupName) { return new ArrayList<>(); }
        @Override public String toString() { return code(); }
    }

    /**
     * 役職
     */
    public enum Skill implements CDef {
        /** 教唆者 */
        教唆者("ABETTER", "教唆者"),
        /** 絶対人狼 */
        絶対人狼("ABSOLUTEWOLF", "絶対人狼"),
        /** 餡麺麭者 */
        餡麺麭者("ANPANMAN", "餡麺麭者"),
        /** 占星術師 */
        占星術師("ASTROLOGER", "占星術師"),
        /** ババ */
        ババ("BABA", "ババ"),
        /** 美人局 */
        美人局("BADGERGAME", "美人局"),
        /** パン屋 */
        パン屋("BAKERY", "パン屋"),
        /** バールのようなもの */
        バールのようなもの("BAR", "バールのようなもの"),
        /** 黒箱者 */
        黒箱者("BLACKBOX", "黒箱者"),
        /** 爆弾魔 */
        爆弾魔("BOMBER", "爆弾魔"),
        /** 組長 */
        組長("BOSS", "組長"),
        /** 誑狐 */
        誑狐("CHEATERFOX", "誑狐"),
        /** 浮気者 */
        浮気者("CHEATLOVER", "浮気者"),
        /** ちくわ大明神 */
        ちくわ大明神("CHIKUWA", "ちくわ大明神"),
        /** 曇天者 */
        曇天者("CLOUDY", "曇天者"),
        /** 道化師 */
        道化師("CLOWN", "道化師"),
        /** C国狂人 */
        C国狂人("CMADMAN", "C国狂人"),
        /** 同棲者 */
        同棲者("COHABITER", "同棲者"),
        /** 指揮官 */
        指揮官("COMMANDER", "指揮官"),
        /** バー狼 */
        バー狼("CONANWOLF", "バー狼"),
        /** 検死官 */
        検死官("CORONER", "検死官"),
        /** 求愛者 */
        求愛者("COURTSHIP", "求愛者"),
        /** おまかせ（愉快犯陣営） */
        おまかせ愉快犯陣営("CRIMINALS", "おまかせ（愉快犯陣営）"),
        /** 反呪者 */
        反呪者("CURSECOUNTER", "反呪者"),
        /** 呪縛者 */
        呪縛者("CURSER", "呪縛者"),
        /** 呪狼 */
        呪狼("CURSEWOLF", "呪狼"),
        /** 探偵 */
        探偵("DETECTIVE", "探偵"),
        /** 興信者 */
        興信者("DETECTSEER", "興信者"),
        /** 剖狼 */
        剖狼("DISSECTWOLF", "剖狼"),
        /** 箪笥 */
        箪笥("DRAWERS", "箪笥"),
        /** 不止者 */
        不止者("DYINGPOINTER", "不止者"),
        /** 情緒 */
        情緒("EMOTION", "情緒"),
        /** 帝狼 */
        帝狼("EMPERORWOLF", "帝狼"),
        /** 闇パン屋 */
        闇パン屋("EVILBAKERY", "闇パン屋"),
        /** 闇探偵 */
        闇探偵("EVILDETECTIVE", "闇探偵"),
        /** 魔神官 */
        魔神官("EVILMEDIUM", "魔神官"),
        /** 執行人 */
        執行人("EXECUTIONER", "執行人"),
        /** 冤罪者 */
        冤罪者("FALSECHARGES", "冤罪者"),
        /** 狂信者 */
        狂信者("FANATIC", "狂信者"),
        /** 妄想癖 */
        妄想癖("FANTASIST", "妄想癖"),
        /** 花占い師 */
        花占い師("FLOWERSEER", "花占い師"),
        /** おまかせ（足音職） */
        おまかせ足音職("FOOTSTEPS", "おまかせ（足音職）"),
        /** 妖狐 */
        妖狐("FOX", "妖狐"),
        /** おまかせ（妖狐陣営） */
        おまかせ妖狐陣営("FOXS", "おまかせ（妖狐陣営）"),
        /** 冷凍者 */
        冷凍者("FREEZER", "冷凍者"),
        /** おまかせ（役職窓あり） */
        おまかせ役職窓あり("FRIENDS", "おまかせ（役職窓あり）"),
        /** 果実籠 */
        果実籠("FRUITSBASKET", "果実籠"),
        /** 歩狼 */
        歩狼("FUWOLF", "歩狼"),
        /** 銀狼 */
        銀狼("GINWOLF", "銀狼"),
        /** ごん */
        ごん("GONFOX", "ごん"),
        /** 喰狼 */
        喰狼("GOURMETWOLF", "喰狼"),
        /** 濡衣者 */
        濡衣者("GUILTER", "濡衣者"),
        /** 導師 */
        導師("GURU", "導師"),
        /** 堅狼 */
        堅狼("HARDWOLF", "堅狼"),
        /** 申し子 */
        申し子("HEAVENCHILD", "申し子"),
        /** 仙狐 */
        仙狐("HERMITFOX", "仙狐"),
        /** 勇者 */
        勇者("HERO", "勇者"),
        /** 飛狼 */
        飛狼("HISHAWOLF", "飛狼"),
        /** 冷やし中華 */
        冷やし中華("HIYASICHUKA", "冷やし中華"),
        /** 狩人 */
        狩人("HUNTER", "狩人"),
        /** 背徳者 */
        背徳者("IMMORAL", "背徳者"),
        /** 稲荷 */
        稲荷("INARI", "稲荷"),
        /** 煽動者 */
        煽動者("INSTIGATOR", "煽動者"),
        /** 保険屋 */
        保険屋("INSURANCER", "保険屋"),
        /** 絡新婦 */
        絡新婦("JOROGUMO", "絡新婦"),
        /** 角狼 */
        角狼("KAKUWOLF", "角狼"),
        /** 王狼 */
        王狼("KINGWOLF", "王狼"),
        /** 金狼 */
        金狼("KINWOLF", "金狼"),
        /** 管狐 */
        管狐("KUDAFOX", "管狐"),
        /** 弁護士 */
        弁護士("LAWYER", "弁護士"),
        /** おまかせ */
        おまかせ("LEFTOVER", "おまかせ"),
        /** 伝説の殺し屋 */
        伝説の殺し屋("LEGENDASSASSIN", "伝説の殺し屋"),
        /** 聴狂人 */
        聴狂人("LISTENMADMAN", "聴狂人"),
        /** 共有者 */
        共有者("LISTENMASON", "共有者"),
        /** 黙狼 */
        黙狼("LISTENWOLF", "黙狼"),
        /** 一匹狼 */
        一匹狼("LONEWOLF", "一匹狼"),
        /** 拡声者 */
        拡声者("LOUDSPEAKER", "拡声者"),
        /** 恋人 */
        恋人("LOVER", "恋人"),
        /** おまかせ（恋人陣営） */
        おまかせ恋人陣営("LOVERS", "おまかせ（恋人陣営）"),
        /** 強運者 */
        強運者("LUCKYMAN", "強運者"),
        /** 狂人 */
        狂人("MADMAN", "狂人"),
        /** 共鳴者 */
        共鳴者("MASON", "共鳴者"),
        /** マタギ */
        マタギ("MATAGI", "マタギ"),
        /** 市長 */
        市長("MAYOR", "市長"),
        /** 霊能者 */
        霊能者("MEDIUM", "霊能者"),
        /** 魅惑の人魚 */
        魅惑の人魚("MERMAID", "魅惑の人魚"),
        /** 耳年増 */
        耳年増("MIMIDOSHIMA", "耳年増"),
        /** 死霊術師 */
        死霊術師("NECROMANCER", "死霊術師"),
        /** 夜狐 */
        夜狐("NIGHTFOX", "夜狐"),
        /** おまかせ（役職窓なし） */
        おまかせ役職窓なし("NOFRIENDS", "おまかせ（役職窓なし）"),
        /** リア充 */
        リア充("NORMIE", "リア充"),
        /** おまかせ（人外） */
        おまかせ人外("NOVILLAGERS", "おまかせ（人外）"),
        /** 監視者 */
        監視者("OBSERVER", "監視者"),
        /** 全知者 */
        全知者("OMNISCIENCE", "全知者"),
        /** 陰陽師 */
        陰陽師("ONMYOJI", "陰陽師"),
        /** 梟 */
        梟("OWL", "梟"),
        /** 牧師 */
        牧師("PASTOR", "牧師"),
        /** 海王者 */
        海王者("POSEIDON", "海王者"),
        /** 画鋲 */
        画鋲("PUSHPIN", "画鋲"),
        /** 虹職人 */
        虹職人("RAINBOW", "虹職人"),
        /** 暴狼 */
        暴狼("RAMPAGEWOLF", "暴狼"),
        /** 転生者 */
        転生者("REINCARNATION", "転生者"),
        /** 覚者 */
        覚者("REMEMBERSEER", "覚者"),
        /** 怨恨者 */
        怨恨者("RESENTER", "怨恨者"),
        /** 蘇生者 */
        蘇生者("RESUSCITATOR", "蘇生者"),
        /** 革命者 */
        革命者("REVOLUTIONARY", "革命者"),
        /** 王族 */
        王族("ROYALTY", "王族"),
        /** 暴走トラック */
        暴走トラック("RUNAWAYTRUCK", "暴走トラック"),
        /** 占い師 */
        占い師("SEER", "占い師"),
        /** 破局者 */
        破局者("SEPARATOR", "破局者"),
        /** 静狼 */
        静狼("SILENTWOLF", "静狼"),
        /** 感覚者 */
        感覚者("SIXTHSENSOR", "感覚者"),
        /** 夢遊病者 */
        夢遊病者("SLEEPWALKER", "夢遊病者"),
        /** 臭狼 */
        臭狼("SMELLWOLF", "臭狼"),
        /** 防音者 */
        防音者("SOUNDPROOFER", "防音者"),
        /** ストーカー */
        ストーカー("STALKER", "ストーカー"),
        /** 濁点者 */
        濁点者("TATSUYA", "濁点者"),
        /** 念狐 */
        念狐("TELEFOX", "念狐"),
        /** 泥棒猫 */
        泥棒猫("THIEFCAT", "泥棒猫"),
        /** 翻訳者 */
        翻訳者("TRANSLATOR", "翻訳者"),
        /** 罠師 */
        罠師("TRAPPER", "罠師"),
        /** 騙狐 */
        騙狐("TRICKFOX", "騙狐"),
        /** トラック */
        トラック("TRUCK", "トラック"),
        /** 村人 */
        村人("VILLAGER", "村人"),
        /** おまかせ（村人陣営） */
        おまかせ村人陣営("VILLAGERS", "おまかせ（村人陣営）"),
        /** 壁殴り代行 */
        壁殴り代行("WALLPUNCHER", "壁殴り代行"),
        /** 風来狩人 */
        風来狩人("WANDERER", "風来狩人"),
        /** 人狼 */
        人狼("WEREWOLF", "人狼"),
        /** おまかせ（人狼陣営） */
        おまかせ人狼陣営("WEREWOLFS", "おまかせ（人狼陣営）"),
        /** 当選者 */
        当選者("WINNER", "当選者"),
        /** 賢者 */
        賢者("WISE", "賢者"),
        /** 智狼 */
        智狼("WISEWOLF", "智狼");
        private static ZzzoneSlimmer<Skill> _slimmer = new ZzzoneSlimmer<>(Skill.class, values());
        private static final Map<String, Map<String, Object>> _subItemMapMap = new HashMap<String, Map<String, Object>>();
        static {
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "211");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "唆");
                _subItemMapMap.put(教唆者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "103");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "絶");
                _subItemMapMap.put(絶対人狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "38");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "餡");
                _subItemMapMap.put(餡麺麭者.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "512");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "バ");
                _subItemMapMap.put(ババ.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "306");
                subItemMap.put("campCode", "LOVERS");
                subItemMap.put("skill_short_name", "美");
                _subItemMapMap.put(美人局.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "19");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "パ");
                _subItemMapMap.put(パン屋.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "208");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "棒");
                _subItemMapMap.put(バールのようなもの.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "207");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "箱");
                _subItemMapMap.put(黒箱者.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "214");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "組");
                _subItemMapMap.put(組長.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "304");
                subItemMap.put("campCode", "LOVERS");
                subItemMap.put("skill_short_name", "浮");
                _subItemMapMap.put(浮気者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "40");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "竹");
                _subItemMapMap.put(ちくわ大明神.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "217");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "曇");
                _subItemMapMap.put(曇天者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "507");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "道");
                _subItemMapMap.put(道化師.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "21");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "指");
                _subItemMapMap.put(指揮官.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "116");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "コ");
                _subItemMapMap.put(バー狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "11");
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
                subItemMap.put("order", "483");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "反");
                _subItemMapMap.put(反呪者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "482");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "縛");
                _subItemMapMap.put(呪縛者.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "16");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "探");
                _subItemMapMap.put(探偵.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "8");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "興");
                _subItemMapMap.put(興信者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "115");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "剖");
                _subItemMapMap.put(剖狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "516");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "箪");
                _subItemMapMap.put(箪笥.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "29");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "止");
                _subItemMapMap.put(不止者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "41");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "情");
                _subItemMapMap.put(情緒.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "114");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "帝");
                _subItemMapMap.put(帝狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "216");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "ン");
                _subItemMapMap.put(闇パン屋.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "212");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "闇");
                _subItemMapMap.put(闇探偵.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "203");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "魔");
                _subItemMapMap.put(魔神官.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "31");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "執");
                _subItemMapMap.put(執行人.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "24");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "冤");
                _subItemMapMap.put(冤罪者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "204");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "信");
                _subItemMapMap.put(狂信者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "22");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "妄");
                _subItemMapMap.put(妄想癖.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "5");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "花");
                _subItemMapMap.put(花占い師.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "213");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "凍");
                _subItemMapMap.put(冷凍者.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "106");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "歩");
                _subItemMapMap.put(歩狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "107");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "銀");
                _subItemMapMap.put(銀狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "402");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "ご");
                _subItemMapMap.put(ごん.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "117");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "喰");
                _subItemMapMap.put(喰狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "215");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "濡");
                _subItemMapMap.put(濡衣者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "10");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "導");
                _subItemMapMap.put(導師.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "111");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "堅");
                _subItemMapMap.put(堅狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "26");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "申");
                _subItemMapMap.put(申し子.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "403");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "仙");
                _subItemMapMap.put(仙狐.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "42");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "勇");
                _subItemMapMap.put(勇者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "104");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "飛");
                _subItemMapMap.put(飛狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "39");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "冷");
                _subItemMapMap.put(冷やし中華.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "12");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "狩");
                _subItemMapMap.put(狩人.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "480");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "背");
                _subItemMapMap.put(背徳者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "405");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "稲");
                _subItemMapMap.put(稲荷.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "205");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "煽");
                _subItemMapMap.put(煽動者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "30");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "保");
                _subItemMapMap.put(保険屋.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "305");
                subItemMap.put("campCode", "LOVERS");
                subItemMap.put("skill_short_name", "絡");
                _subItemMapMap.put(絡新婦.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "105");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "角");
                _subItemMapMap.put(角狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "109");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "王");
                _subItemMapMap.put(王狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "108");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "金");
                _subItemMapMap.put(金狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "404");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "管");
                _subItemMapMap.put(管狐.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "32");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "弁");
                _subItemMapMap.put(弁護士.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "508");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "伝");
                _subItemMapMap.put(伝説の殺し屋.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "202");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "聴");
                _subItemMapMap.put(聴狂人.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "15");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "有");
                _subItemMapMap.put(共有者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "112");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "黙");
                _subItemMapMap.put(黙狼.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "505");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "拡");
                _subItemMapMap.put(拡声者.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "20");
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
                subItemMap.put("order", "14");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "共");
                _subItemMapMap.put(共鳴者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "34");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "マ");
                _subItemMapMap.put(マタギ.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "33");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "市");
                _subItemMapMap.put(市長.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "9");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "霊");
                _subItemMapMap.put(霊能者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "307");
                subItemMap.put("campCode", "LOVERS");
                subItemMap.put("skill_short_name", "魅");
                _subItemMapMap.put(魅惑の人魚.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "309");
                subItemMap.put("campCode", "LOVERS");
                subItemMap.put("skill_short_name", "耳");
                _subItemMapMap.put(耳年増.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "206");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "死");
                _subItemMapMap.put(死霊術師.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "407");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "夜");
                _subItemMapMap.put(夜狐.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "310");
                subItemMap.put("campCode", "LOVERS");
                subItemMap.put("skill_short_name", "充");
                _subItemMapMap.put(リア充.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "17");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "監");
                _subItemMapMap.put(監視者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "37");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "全");
                _subItemMapMap.put(全知者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "481");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "陰");
                _subItemMapMap.put(陰陽師.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "36");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "牧");
                _subItemMapMap.put(牧師.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "308");
                subItemMap.put("campCode", "LOVERS");
                subItemMap.put("skill_short_name", "海");
                _subItemMapMap.put(海王者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "515");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "鋲");
                _subItemMapMap.put(画鋲.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "504");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "虹");
                _subItemMapMap.put(虹職人.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "180");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "暴");
                _subItemMapMap.put(暴狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "25");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "転");
                _subItemMapMap.put(転生者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "7");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "覚");
                _subItemMapMap.put(覚者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "209");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "怨");
                _subItemMapMap.put(怨恨者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "28");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "蘇");
                _subItemMapMap.put(蘇生者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "518");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "革");
                _subItemMapMap.put(革命者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "517");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "族");
                _subItemMapMap.put(王族.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "511");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "走");
                _subItemMapMap.put(暴走トラック.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "210");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "破");
                _subItemMapMap.put(破局者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "110");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "静");
                _subItemMapMap.put(静狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "6");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "感");
                _subItemMapMap.put(感覚者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "23");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "夢");
                _subItemMapMap.put(夢遊病者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "113");
                subItemMap.put("campCode", "WEREWOLF");
                subItemMap.put("skill_short_name", "臭");
                _subItemMapMap.put(臭狼.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "35");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "防");
                _subItemMapMap.put(防音者.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "506");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "濁");
                _subItemMapMap.put(濁点者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "408");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "念");
                _subItemMapMap.put(念狐.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "514");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "泥");
                _subItemMapMap.put(泥棒猫.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "509");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "翻");
                _subItemMapMap.put(翻訳者.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "18");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "罠");
                _subItemMapMap.put(罠師.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "406");
                subItemMap.put("campCode", "FOX");
                subItemMap.put("skill_short_name", "騙");
                _subItemMapMap.put(騙狐.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "510");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "ト");
                _subItemMapMap.put(トラック.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "27");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "壁");
                _subItemMapMap.put(壁殴り代行.code(), Collections.unmodifiableMap(subItemMap));
            }
            {
                Map<String, Object> subItemMap = new HashMap<String, Object>();
                subItemMap.put("order", "13");
                subItemMap.put("campCode", "VILLAGER");
                subItemMap.put("skill_short_name", "風");
                _subItemMapMap.put(風来狩人.code(), Collections.unmodifiableMap(subItemMap));
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
                subItemMap.put("order", "513");
                subItemMap.put("campCode", "CRIMINAL");
                subItemMap.put("skill_short_name", "当");
                _subItemMapMap.put(当選者.code(), Collections.unmodifiableMap(subItemMap));
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
        private String _code; private String _alias;
        private Skill(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
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
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, C国狂人]
         * @return The determination, true or false.
         */
        public boolean isAvailableWerewolfSay() { return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this) || 歩狼.equals(this) || 銀狼.equals(this) || 金狼.equals(this) || 飛狼.equals(this) || 角狼.equals(this) || 王狼.equals(this) || 静狼.equals(this) || 堅狼.equals(this) || 臭狼.equals(this) || 帝狼.equals(this) || 剖狼.equals(this) || バー狼.equals(this) || 喰狼.equals(this) || C国狂人.equals(this); }
        /**
         * Is the classification in the group? <br>
         * 囁きを見られる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, C国狂人, 聴狂人]
         * @return The determination, true or false.
         */
        public boolean isViewableWerewolfSay() { return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this) || 歩狼.equals(this) || 銀狼.equals(this) || 金狼.equals(this) || 飛狼.equals(this) || 角狼.equals(this) || 王狼.equals(this) || 静狼.equals(this) || 堅狼.equals(this) || 黙狼.equals(this) || 臭狼.equals(this) || 帝狼.equals(this) || 剖狼.equals(this) || バー狼.equals(this) || 喰狼.equals(this) || C国狂人.equals(this) || 聴狂人.equals(this); }
        /**
         * Is the classification in the group? <br>
         * 占い能力を持つ <br>
         * The group elements:[占い師, 賢者, 占星術師, 花占い師, 感覚者, 興信者, 管狐]
         * @return The determination, true or false.
         */
        public boolean isHasDivineAbility() { return 占い師.equals(this) || 賢者.equals(this) || 占星術師.equals(this) || 花占い師.equals(this) || 感覚者.equals(this) || 興信者.equals(this) || 管狐.equals(this); }
        /**
         * Is the classification in the group? <br>
         * 役職霊能能力を持つ <br>
         * The group elements:[導師, 魔神官, 稲荷]
         * @return The determination, true or false.
         */
        public boolean isHasSkillPsychicAbility() { return 導師.equals(this) || 魔神官.equals(this) || 稲荷.equals(this); }
        /**
         * Is the classification in the group? <br>
         * 襲撃能力を持つ <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼]
         * @return The determination, true or false.
         */
        public boolean isHasAttackAbility() { return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this) || 歩狼.equals(this) || 銀狼.equals(this) || 金狼.equals(this) || 飛狼.equals(this) || 角狼.equals(this) || 王狼.equals(this) || 静狼.equals(this) || 堅狼.equals(this) || 黙狼.equals(this) || 臭狼.equals(this) || 帝狼.equals(this) || 剖狼.equals(this) || バー狼.equals(this) || 喰狼.equals(this); }
        /**
         * Is the classification in the group? <br>
         * 徘徊能力を持つ <br>
         * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 聴狂人, 妖狐, 仙狐, 夜狐, 背徳者]
         * @return The determination, true or false.
         */
        public boolean isHasDisturbAbility() { return C国狂人.equals(this) || 狂人.equals(this) || 狂信者.equals(this) || 魔神官.equals(this) || 聴狂人.equals(this) || 妖狐.equals(this) || 仙狐.equals(this) || 夜狐.equals(this) || 背徳者.equals(this); }
        /**
         * Is the classification in the group? <br>
         * 襲撃されても死なない <br>
         * The group elements:[壁殴り代行, 堅狼, 妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 念狐, 爆弾魔, 暴走トラック]
         * @return The determination, true or false.
         */
        public boolean isNoDeadByAttack() { return 壁殴り代行.equals(this) || 堅狼.equals(this) || 妖狐.equals(this) || 誑狐.equals(this) || ごん.equals(this) || 仙狐.equals(this) || 管狐.equals(this) || 稲荷.equals(this) || 騙狐.equals(this) || 夜狐.equals(this) || 念狐.equals(this) || 爆弾魔.equals(this) || 暴走トラック.equals(this); }
        /**
         * Is the classification in the group? <br>
         * 勝敗判定時、人狼にカウントされる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, 暴狼]
         * @return The determination, true or false.
         */
        public boolean isWolfCount() { return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this) || 歩狼.equals(this) || 銀狼.equals(this) || 金狼.equals(this) || 飛狼.equals(this) || 角狼.equals(this) || 王狼.equals(this) || 静狼.equals(this) || 堅狼.equals(this) || 黙狼.equals(this) || 臭狼.equals(this) || 帝狼.equals(this) || 剖狼.equals(this) || バー狼.equals(this) || 喰狼.equals(this) || 暴狼.equals(this); }
        /**
         * Is the classification in the group? <br>
         * 勝敗判定時、人間にも人狼にもカウントされない <br>
         * The group elements:[妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 念狐, 梟]
         * @return The determination, true or false.
         */
        public boolean isNoCount() { return 妖狐.equals(this) || 誑狐.equals(this) || ごん.equals(this) || 仙狐.equals(this) || 管狐.equals(this) || 稲荷.equals(this) || 騙狐.equals(this) || 夜狐.equals(this) || 念狐.equals(this) || 梟.equals(this); }
        /**
         * Is the classification in the group? <br>
         * 人狼が誰かを知ることができる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, C国狂人, 狂信者, 煽動者]
         * @return The determination, true or false.
         */
        public boolean isViewableWolfCharaName() { return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this) || 歩狼.equals(this) || 銀狼.equals(this) || 金狼.equals(this) || 飛狼.equals(this) || 角狼.equals(this) || 王狼.equals(this) || 静狼.equals(this) || 堅狼.equals(this) || 黙狼.equals(this) || 臭狼.equals(this) || 帝狼.equals(this) || 剖狼.equals(this) || バー狼.equals(this) || 喰狼.equals(this) || C国狂人.equals(this) || 狂信者.equals(this) || 煽動者.equals(this); }
        /**
         * Is the classification in the group? <br>
         * 占い結果が人狼となる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, 暴狼, 一匹狼]
         * @return The determination, true or false.
         */
        public boolean isDivineResultWolf() { return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this) || 歩狼.equals(this) || 銀狼.equals(this) || 金狼.equals(this) || 飛狼.equals(this) || 角狼.equals(this) || 王狼.equals(this) || 静狼.equals(this) || 堅狼.equals(this) || 黙狼.equals(this) || 臭狼.equals(this) || 帝狼.equals(this) || 剖狼.equals(this) || バー狼.equals(this) || 喰狼.equals(this) || 暴狼.equals(this) || 一匹狼.equals(this); }
        /**
         * Is the classification in the group? <br>
         * 霊能結果が人狼となる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, 暴狼, 一匹狼]
         * @return The determination, true or false.
         */
        public boolean isPsychicResultWolf() { return 人狼.equals(this) || 呪狼.equals(this) || 智狼.equals(this) || 絶対人狼.equals(this) || 歩狼.equals(this) || 銀狼.equals(this) || 金狼.equals(this) || 飛狼.equals(this) || 角狼.equals(this) || 王狼.equals(this) || 静狼.equals(this) || 堅狼.equals(this) || 黙狼.equals(this) || 臭狼.equals(this) || 帝狼.equals(this) || 剖狼.equals(this) || バー狼.equals(this) || 喰狼.equals(this) || 暴狼.equals(this) || 一匹狼.equals(this); }
        /**
         * Is the classification in the group? <br>
         * おまかせ系 <br>
         * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
         * @return The determination, true or false.
         */
        public boolean isSomeoneSkill() { return おまかせ.equals(this) || おまかせ村人陣営.equals(this) || おまかせ人狼陣営.equals(this) || おまかせ恋人陣営.equals(this) || おまかせ妖狐陣営.equals(this) || おまかせ愉快犯陣営.equals(this) || おまかせ足音職.equals(this) || おまかせ役職窓あり.equals(this) || おまかせ役職窓なし.equals(this) || おまかせ人外.equals(this); }
        public boolean inGroup(String groupName) {
            if ("availableWerewolfSay".equalsIgnoreCase(groupName)) { return isAvailableWerewolfSay(); }
            if ("viewableWerewolfSay".equalsIgnoreCase(groupName)) { return isViewableWerewolfSay(); }
            if ("hasDivineAbility".equalsIgnoreCase(groupName)) { return isHasDivineAbility(); }
            if ("hasSkillPsychicAbility".equalsIgnoreCase(groupName)) { return isHasSkillPsychicAbility(); }
            if ("hasAttackAbility".equalsIgnoreCase(groupName)) { return isHasAttackAbility(); }
            if ("hasDisturbAbility".equalsIgnoreCase(groupName)) { return isHasDisturbAbility(); }
            if ("noDeadByAttack".equalsIgnoreCase(groupName)) { return isNoDeadByAttack(); }
            if ("wolfCount".equalsIgnoreCase(groupName)) { return isWolfCount(); }
            if ("noCount".equalsIgnoreCase(groupName)) { return isNoCount(); }
            if ("viewableWolfCharaName".equalsIgnoreCase(groupName)) { return isViewableWolfCharaName(); }
            if ("divineResultWolf".equalsIgnoreCase(groupName)) { return isDivineResultWolf(); }
            if ("psychicResultWolf".equalsIgnoreCase(groupName)) { return isPsychicResultWolf(); }
            if ("someoneSkill".equalsIgnoreCase(groupName)) { return isSomeoneSkill(); }
            return false;
        }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Skill> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<Skill> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static Skill codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static Skill nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<Skill> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<Skill> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            if ("availableWerewolfSay".equalsIgnoreCase(groupName)) { return listOfAvailableWerewolfSay(); }
            if ("viewableWerewolfSay".equalsIgnoreCase(groupName)) { return listOfViewableWerewolfSay(); }
            if ("hasDivineAbility".equalsIgnoreCase(groupName)) { return listOfHasDivineAbility(); }
            if ("hasSkillPsychicAbility".equalsIgnoreCase(groupName)) { return listOfHasSkillPsychicAbility(); }
            if ("hasAttackAbility".equalsIgnoreCase(groupName)) { return listOfHasAttackAbility(); }
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
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<Skill> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 囁き可能 <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, C国狂人]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfAvailableWerewolfSay() {
            return new ArrayList<>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, C国狂人));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 囁きを見られる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, C国狂人, 聴狂人]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfViewableWerewolfSay() {
            return new ArrayList<>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, C国狂人, 聴狂人));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 占い能力を持つ <br>
         * The group elements:[占い師, 賢者, 占星術師, 花占い師, 感覚者, 興信者, 管狐]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfHasDivineAbility() {
            return new ArrayList<>(Arrays.asList(占い師, 賢者, 占星術師, 花占い師, 感覚者, 興信者, 管狐));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 役職霊能能力を持つ <br>
         * The group elements:[導師, 魔神官, 稲荷]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfHasSkillPsychicAbility() {
            return new ArrayList<>(Arrays.asList(導師, 魔神官, 稲荷));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 襲撃能力を持つ <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfHasAttackAbility() {
            return new ArrayList<>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 徘徊能力を持つ <br>
         * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 聴狂人, 妖狐, 仙狐, 夜狐, 背徳者]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfHasDisturbAbility() {
            return new ArrayList<>(Arrays.asList(C国狂人, 狂人, 狂信者, 魔神官, 聴狂人, 妖狐, 仙狐, 夜狐, 背徳者));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 襲撃されても死なない <br>
         * The group elements:[壁殴り代行, 堅狼, 妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 念狐, 爆弾魔, 暴走トラック]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfNoDeadByAttack() {
            return new ArrayList<>(Arrays.asList(壁殴り代行, 堅狼, 妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 念狐, 爆弾魔, 暴走トラック));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 勝敗判定時、人狼にカウントされる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, 暴狼]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfWolfCount() {
            return new ArrayList<>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, 暴狼));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 勝敗判定時、人間にも人狼にもカウントされない <br>
         * The group elements:[妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 念狐, 梟]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfNoCount() {
            return new ArrayList<>(Arrays.asList(妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 念狐, 梟));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 人狼が誰かを知ることができる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, C国狂人, 狂信者, 煽動者]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfViewableWolfCharaName() {
            return new ArrayList<>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, C国狂人, 狂信者, 煽動者));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 占い結果が人狼となる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, 暴狼, 一匹狼]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfDivineResultWolf() {
            return new ArrayList<>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, 暴狼, 一匹狼));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 霊能結果が人狼となる <br>
         * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, 暴狼, 一匹狼]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfPsychicResultWolf() {
            return new ArrayList<>(Arrays.asList(人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, バー狼, 喰狼, 暴狼, 一匹狼));
        }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * おまかせ系 <br>
         * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<Skill> listOfSomeoneSkill() {
            return new ArrayList<>(Arrays.asList(おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外));
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<Skill> groupOf(String groupName) {
            if ("availableWerewolfSay".equalsIgnoreCase(groupName)) { return listOfAvailableWerewolfSay(); }
            if ("viewableWerewolfSay".equalsIgnoreCase(groupName)) { return listOfViewableWerewolfSay(); }
            if ("hasDivineAbility".equalsIgnoreCase(groupName)) { return listOfHasDivineAbility(); }
            if ("hasSkillPsychicAbility".equalsIgnoreCase(groupName)) { return listOfHasSkillPsychicAbility(); }
            if ("hasAttackAbility".equalsIgnoreCase(groupName)) { return listOfHasAttackAbility(); }
            if ("hasDisturbAbility".equalsIgnoreCase(groupName)) { return listOfHasDisturbAbility(); }
            if ("noDeadByAttack".equalsIgnoreCase(groupName)) { return listOfNoDeadByAttack(); }
            if ("wolfCount".equalsIgnoreCase(groupName)) { return listOfWolfCount(); }
            if ("noCount".equalsIgnoreCase(groupName)) { return listOfNoCount(); }
            if ("viewableWolfCharaName".equalsIgnoreCase(groupName)) { return listOfViewableWolfCharaName(); }
            if ("divineResultWolf".equalsIgnoreCase(groupName)) { return listOfDivineResultWolf(); }
            if ("psychicResultWolf".equalsIgnoreCase(groupName)) { return listOfPsychicResultWolf(); }
            if ("someoneSkill".equalsIgnoreCase(groupName)) { return listOfSomeoneSkill(); }
            return new ArrayList<>();
        }
        @Override public String toString() { return code(); }
    }

    /**
     * メッセージ種別
     */
    public enum MessageType implements CDef {
        /** アクション */
        アクション("ACTION", "アクション"),
        /** 村建て発言 */
        村建て発言("CREATOR_SAY", "村建て発言"),
        /** 死者の呻き */
        死者の呻き("GRAVE_SAY", "死者の呻き"),
        /** 恋人発言 */
        恋人発言("LOVERS_SAY", "恋人発言"),
        /** 共鳴発言 */
        共鳴発言("MASON_SAY", "共鳴発言"),
        /** 独り言 */
        独り言("MONOLOGUE_SAY", "独り言"),
        /** 通常発言 */
        通常発言("NORMAL_SAY", "通常発言"),
        /** 参加者一覧 */
        参加者一覧("PARTICIPANTS", "参加者一覧"),
        /** 能力行使メッセージ */
        能力行使メッセージ("PRIVATE_ABILITY", "能力行使メッセージ"),
        /** 検死結果 */
        検死結果("PRIVATE_CORONER", "検死結果"),
        /** 妖狐メッセージ */
        妖狐メッセージ("PRIVATE_FOX", "妖狐メッセージ"),
        /** 役職霊視結果 */
        役職霊視結果("PRIVATE_GURU", "役職霊視結果"),
        /** 足音調査結果 */
        足音調査結果("PRIVATE_INVESTIGATE", "足音調査結果"),
        /** 恋人メッセージ */
        恋人メッセージ("PRIVATE_LOVER", "恋人メッセージ"),
        /** 白黒霊視結果 */
        白黒霊視結果("PRIVATE_PSYCHIC", "白黒霊視結果"),
        /** 白黒占い結果 */
        白黒占い結果("PRIVATE_SEER", "白黒占い結果"),
        /** 非公開システムメッセージ */
        非公開システムメッセージ("PRIVATE_SYSTEM", "非公開システムメッセージ"),
        /** 襲撃結果 */
        襲撃結果("PRIVATE_WEREWOLF", "襲撃結果"),
        /** 役職占い結果 */
        役職占い結果("PRIVATE_WISE", "役職占い結果"),
        /** 公開システムメッセージ */
        公開システムメッセージ("PUBLIC_SYSTEM", "公開システムメッセージ"),
        /** 秘話 */
        秘話("SECRET_SAY", "秘話"),
        /** 見学発言 */
        見学発言("SPECTATE_SAY", "見学発言"),
        /** 念話 */
        念話("TELEPATHY", "念話"),
        /** 人狼の囁き */
        人狼の囁き("WEREWOLF_SAY", "人狼の囁き");
        private static ZzzoneSlimmer<MessageType> _slimmer = new ZzzoneSlimmer<>(MessageType.class, values());
        private String _code; private String _alias;
        private MessageType(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.MessageType; }
        public boolean inGroup(String groupName) { return false; }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<MessageType> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<MessageType> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static MessageType codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static MessageType nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<MessageType> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<MessageType> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: MessageType." + groupName);
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<MessageType> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<MessageType> groupOf(String groupName) { return new ArrayList<>(); }
        @Override public String toString() { return code(); }
    }

    /**
     * 死亡理由
     */
    public enum DeadReason implements CDef {
        /** 襲撃 */
        襲撃("ATTACK", "襲撃"),
        /** 爆死 */
        爆死("BOMBED", "爆死"),
        /** 呪殺 */
        呪殺("DIVINED", "呪殺"),
        /** 処刑 */
        処刑("EXECUTE", "処刑"),
        /** 突然 */
        突然("SUDDON", "突然"),
        /** 後追 */
        後追("SUICIDE", "後追"),
        /** 罠死 */
        罠死("TRAPPED", "罠死"),
        /** 雑魚 */
        雑魚("ZAKO", "雑魚");
        private static ZzzoneSlimmer<DeadReason> _slimmer = new ZzzoneSlimmer<>(DeadReason.class, values());
        private String _code; private String _alias;
        private DeadReason(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.DeadReason; }
        /**
         * Is the classification in the group? <br>
         * 無惨 <br>
         * The group elements:[襲撃, 呪殺, 罠死, 爆死, 雑魚]
         * @return The determination, true or false.
         */
        public boolean isMiserable() { return 襲撃.equals(this) || 呪殺.equals(this) || 罠死.equals(this) || 爆死.equals(this) || 雑魚.equals(this); }
        public boolean inGroup(String groupName) {
            if ("miserable".equalsIgnoreCase(groupName)) { return isMiserable(); }
            return false;
        }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<DeadReason> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<DeadReason> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static DeadReason codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static DeadReason nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<DeadReason> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<DeadReason> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            if ("miserable".equalsIgnoreCase(groupName)) { return listOfMiserable(); }
            throw new ClassificationNotFoundException("Unknown classification group: DeadReason." + groupName);
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<DeadReason> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * Get the list of group classification elements. (returns new copied list) <br>
         * 無惨 <br>
         * The group elements:[襲撃, 呪殺, 罠死, 爆死, 雑魚]
         * @return The snapshot list of classification elements in the group. (NotNull)
         */
        public static List<DeadReason> listOfMiserable() {
            return new ArrayList<>(Arrays.asList(襲撃, 呪殺, 罠死, 爆死, 雑魚));
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<DeadReason> groupOf(String groupName) {
            if ("miserable".equalsIgnoreCase(groupName)) { return listOfMiserable(); }
            return new ArrayList<>();
        }
        @Override public String toString() { return code(); }
    }

    /**
     * 能力種別
     */
    public enum AbilityType implements CDef {
        /** 襲撃 */
        襲撃("ATTACK", "襲撃"),
        /** 襲撃希望 */
        襲撃希望("ATTACK_REQUEST", "襲撃希望"),
        /** ババを渡す */
        ババを渡す("BABAGIVE", "ババを渡す"),
        /** 美人局 */
        美人局("BADGERGAME", "美人局"),
        /** 殴打 */
        殴打("BEAT", "殴打"),
        /** 爆弾設置 */
        爆弾設置("BOMB", "爆弾設置"),
        /** 破局 */
        破局("BREAKUP", "破局"),
        /** 誑かす */
        誑かす("CHEAT", "誑かす"),
        /** 浮気 */
        浮気("CHEATLOVE", "浮気"),
        /** 誰だ今の */
        誰だ今の("CHIKUWA", "誰だ今の"),
        /** 曇天 */
        曇天("CLOUD", "曇天"),
        /** 同棲 */
        同棲("COHABIT", "同棲"),
        /** 指揮 */
        指揮("COMMAND", "指揮"),
        /** 反呪 */
        反呪("COUNTERCURSE", "反呪"),
        /** 求愛 */
        求愛("COURT", "求愛"),
        /** 呪縛 */
        呪縛("CURSE", "呪縛"),
        /** 死者占い */
        死者占い("DEADDIVINE", "死者占い"),
        /** 占い */
        占い("DIVINE", "占い"),
        /** 道化 */
        道化("DOUKE", "道化"),
        /** 情緒 */
        情緒("EMOTION", "情緒"),
        /** 強制転生 */
        強制転生("FORCE_REINCARNATION", "強制転生"),
        /** フルーツバスケット */
        フルーツバスケット("FRUITSBASKET", "フルーツバスケット"),
        /** 念力付与 */
        念力付与("GIVETELEKINESIS", "念力付与"),
        /** 護衛 */
        護衛("GUARD", "護衛"),
        /** 濡衣 */
        濡衣("GUILTY", "濡衣"),
        /** 隠蔽 */
        隠蔽("HIDE", "隠蔽"),
        /** 冷やし中華 */
        冷やし中華("HIYASICHUKA", "冷やし中華"),
        /** 狩猟 */
        狩猟("HUNTING", "狩猟"),
        /** 教唆 */
        教唆("INCITE", "教唆"),
        /** 煽動 */
        煽動("INSTIGATE", "煽動"),
        /** 保険 */
        保険("INSURANCE", "保険"),
        /** 捜査 */
        捜査("INVESTIGATE", "捜査"),
        /** 単独襲撃 */
        単独襲撃("LONEATTACK", "単独襲撃"),
        /** 拡声 */
        拡声("LOUDSPEAK", "拡声"),
        /** 恋泥棒 */
        恋泥棒("LOVESTEAL", "恋泥棒"),
        /** ナマ足 */
        ナマ足("NAMAASHI", "ナマ足"),
        /** 死霊蘇生 */
        死霊蘇生("NECROMANCE", "死霊蘇生"),
        /** 全知 */
        全知("OMNISCIENCE", "全知"),
        /** 降霊 */
        降霊("ONMYO_NECROMANCE", "降霊"),
        /** 説得 */
        説得("PERSUADE", "説得"),
        /** 戦闘力発揮 */
        戦闘力発揮("POWER", "戦闘力発揮"),
        /** 殺し屋化 */
        殺し屋化("PRO", "殺し屋化"),
        /** 虹塗り */
        虹塗り("RAINBOW", "虹塗り"),
        /** 蘇生 */
        蘇生("RESUSCITATE", "蘇生"),
        /** 革命 */
        革命("REVOLUTION", "革命"),
        /** 暴走転生 */
        暴走転生("RUNAWAY", "暴走転生"),
        /** 世界を救う */
        世界を救う("SAVETHEWORLD", "世界を救う"),
        /** 誘惑 */
        誘惑("SEDUCE", "誘惑"),
        /** 叫び */
        叫び("SHOUT", "叫び"),
        /** ストーキング */
        ストーキング("STALKING", "ストーキング"),
        /** 翻訳 */
        翻訳("TRANSLATE", "翻訳"),
        /** 人魚化 */
        人魚化("TRANSMERMAID", "人魚化"),
        /** 罠設置 */
        罠設置("TRAP", "罠設置"),
        /** 壁殴り */
        壁殴り("WALLPUNCH", "壁殴り"),
        /** 風来護衛 */
        風来護衛("WANDERERGUARD", "風来護衛"),
        /** 当選 */
        当選("WIN", "当選"),
        /** 指差死 */
        指差死("YUBISASHI", "指差死");
        private static ZzzoneSlimmer<AbilityType> _slimmer = new ZzzoneSlimmer<>(AbilityType.class, values());
        private String _code; private String _alias;
        private AbilityType(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.AbilityType; }
        public boolean inGroup(String groupName) { return false; }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<AbilityType> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<AbilityType> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static AbilityType codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static AbilityType nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<AbilityType> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<AbilityType> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: AbilityType." + groupName);
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<AbilityType> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<AbilityType> groupOf(String groupName) { return new ArrayList<>(); }
        @Override public String toString() { return code(); }
    }

    /**
     * 秘話可能範囲
     */
    public enum AllowedSecretSay implements CDef {
        /** 全員 */
        全員("EVERYTHING", "全員"),
        /** なし */
        なし("NOTHING", "なし"),
        /** 村建てとのみ */
        村建てとのみ("ONLY_CREATOR", "村建てとのみ");
        private static ZzzoneSlimmer<AllowedSecretSay> _slimmer = new ZzzoneSlimmer<>(AllowedSecretSay.class, values());
        private String _code; private String _alias;
        private AllowedSecretSay(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.AllowedSecretSay; }
        public boolean inGroup(String groupName) { return false; }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<AllowedSecretSay> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<AllowedSecretSay> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static AllowedSecretSay codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static AllowedSecretSay nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<AllowedSecretSay> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<AllowedSecretSay> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: AllowedSecretSay." + groupName);
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<AllowedSecretSay> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<AllowedSecretSay> groupOf(String groupName) { return new ArrayList<>(); }
        @Override public String toString() { return code(); }
    }

    /**
     * 表情種別
     */
    public enum FaceType implements CDef {
        /** 墓下 */
        墓下("GRAVE", "墓下"),
        /** 恋人 */
        恋人("LOVER", "恋人"),
        /** 共鳴 */
        共鳴("MASON", "共鳴"),
        /** 独り言 */
        独り言("MONOLOGUE", "独り言"),
        /** 通常 */
        通常("NORMAL", "通常"),
        /** 秘話 */
        秘話("SECRET", "秘話"),
        /** 囁き */
        囁き("WEREWOLF", "囁き");
        private static ZzzoneSlimmer<FaceType> _slimmer = new ZzzoneSlimmer<>(FaceType.class, values());
        private String _code; private String _alias;
        private FaceType(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.FaceType; }
        public boolean inGroup(String groupName) { return false; }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<FaceType> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<FaceType> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static FaceType codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static FaceType nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<FaceType> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<FaceType> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: FaceType." + groupName);
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<FaceType> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<FaceType> groupOf(String groupName) { return new ArrayList<>(); }
        @Override public String toString() { return code(); }
    }

    /**
     * 村参加者ステータス種別
     */
    public enum VillagePlayerStatusType implements CDef {
        /** 信念 */
        信念("BELIEF", "信念"),
        /** 反呪符 */
        反呪符("COUNTERCURSEMARK", "反呪符"),
        /** 呪縛符 */
        呪縛符("CURSEMARK", "呪縛符"),
        /** 不敬 */
        不敬("DISRESPECTFUL", "不敬"),
        /** 後追い */
        後追い("FOLLOWING_SUICIDE", "後追い"),
        /** 狐憑き */
        狐憑き("FOX_POSSESSION", "狐憑き"),
        /** 狂気 */
        狂気("INSANITY", "狂気"),
        /** 保険 */
        保険("INSURANCE", "保険"),
        /** 念力 */
        念力("TELEKINESIS", "念力");
        private static ZzzoneSlimmer<VillagePlayerStatusType> _slimmer = new ZzzoneSlimmer<>(VillagePlayerStatusType.class, values());
        private String _code; private String _alias;
        private VillagePlayerStatusType(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.VillagePlayerStatusType; }
        public boolean inGroup(String groupName) { return false; }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<VillagePlayerStatusType> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<VillagePlayerStatusType> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static VillagePlayerStatusType codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static VillagePlayerStatusType nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<VillagePlayerStatusType> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<VillagePlayerStatusType> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: VillagePlayerStatusType." + groupName);
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<VillagePlayerStatusType> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<VillagePlayerStatusType> groupOf(String groupName) { return new ArrayList<>(); }
        @Override public String toString() { return code(); }
    }

    /**
     * 村タグ種別
     */
    public enum VillageTagItem implements CDef {
        /** 誰歓 */
        誰歓("ANYONE_WELCOME", "誰歓"),
        /** R15 */
        R15("R15", "R15"),
        /** R18 */
        R18("R18", "R18"),
        /** 身内 */
        身内("RELATIVES_ONLY", "身内");
        private static ZzzoneSlimmer<VillageTagItem> _slimmer = new ZzzoneSlimmer<>(VillageTagItem.class, values());
        private String _code; private String _alias;
        private VillageTagItem(String code, String alias) { _code = code; _alias = alias; }
        public String code() { return _code; } public String alias() { return _alias; }
        public Set<String> sisterSet() { return Collections.emptySet(); }
        public Map<String, Object> subItemMap() { return Collections.emptyMap(); }
        public ClassificationMeta meta() { return CDef.DefMeta.VillageTagItem; }
        public boolean inGroup(String groupName) { return false; }
        /**
         * Get the classification of the code. (CaseInsensitive)
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns empty)
         * @return The optional classification corresponding to the code. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<VillageTagItem> of(Object code) { return _slimmer.of(code); }
        /**
         * Find the classification by the name. (CaseInsensitive)
         * @param name The string of name, which is case-insensitive. (NotNull)
         * @return The optional classification corresponding to the name. (NotNull, EmptyAllowed: if not found, returns empty)
         */
        public static OptionalThing<VillageTagItem> byName(String name) { return _slimmer.byName(name); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use of(code).</span>
         * @param code The value of code, which is case-insensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the code. (NullAllowed: if not found, returns null)
         */
        public static VillageTagItem codeOf(Object code) { return _slimmer.codeOf(code); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use byName(name).</span>
         * @param name The string of name, which is case-sensitive. (NullAllowed: if null, returns null)
         * @return The instance of the corresponding classification to the name. (NullAllowed: if not found, returns null)
         * @deprecated use byName(name) instead.
         */
        @Deprecated
        public static VillageTagItem nameOf(String name) { return _slimmer.nameOf(name, nm -> valueOf(nm)); }
        /**
         * Get the list of all classification elements. (returns new copied list)
         * @return The snapshot list of all classification elements. (NotNull)
         */
        public static List<VillageTagItem> listAll() { return _slimmer.listAll(values()); }
        /**
         * Get the list of classification elements in the specified group. (returns new copied list)
         * @param groupName The string of group name, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the group. (NotNull)
         * @throws ClassificationNotFoundException When the group is not found.
         */
        public static List<VillageTagItem> listByGroup(String groupName) {
            if (groupName == null) { throw new IllegalArgumentException("The argument 'groupName' should not be null."); }
            throw new ClassificationNotFoundException("Unknown classification group: VillageTagItem." + groupName);
        }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use e.g. Stream API with of().</span>
         * @param codeList The list of plain code, which is case-insensitive. (NotNull)
         * @return The snapshot list of classification elements in the code list. (NotNull, EmptyAllowed: when empty specified)
         * @deprecated use e.g. Stream API with of() instead.
         */
        @Deprecated
        public static List<VillageTagItem> listOf(Collection<String> codeList) { return _slimmer.listOf(codeList); }
        /**
         * <span style="color: #AD4747; font-size: 120%">Old style so use listByGroup(groupName).</span>
         * @param groupName The string of group name, which is case-sensitive. (NullAllowed: if null, returns empty list)
         * @return The snapshot list of classification elements in the group. (NotNull, EmptyAllowed: if the group is not found)
         * @deprecated use listByGroup(groupName) instead.
         */
        @Deprecated
        public static List<VillageTagItem> groupOf(String groupName) { return new ArrayList<>(); }
        @Override public String toString() { return code(); }
    }

    public enum DefMeta implements ClassificationMeta {
        /** フラグを示す */
        Flg(cd -> CDef.Flg.of(cd), nm -> CDef.Flg.byName(nm)
        , () -> CDef.Flg.listAll(), gp -> CDef.Flg.listByGroup(gp)
        , ClassificationCodeType.Boolean, ClassificationUndefinedHandlingType.LOGGING),

        /** 権限 */
        Authority(cd -> CDef.Authority.of(cd), nm -> CDef.Authority.byName(nm)
        , () -> CDef.Authority.listAll(), gp -> CDef.Authority.listByGroup(gp)
        , ClassificationCodeType.String, ClassificationUndefinedHandlingType.LOGGING),

        /** 陣営 */
        Camp(cd -> CDef.Camp.of(cd), nm -> CDef.Camp.byName(nm)
        , () -> CDef.Camp.listAll(), gp -> CDef.Camp.listByGroup(gp)
        , ClassificationCodeType.String, ClassificationUndefinedHandlingType.LOGGING),

        /** 村ステータス */
        VillageStatus(cd -> CDef.VillageStatus.of(cd), nm -> CDef.VillageStatus.byName(nm)
        , () -> CDef.VillageStatus.listAll(), gp -> CDef.VillageStatus.listByGroup(gp)
        , ClassificationCodeType.String, ClassificationUndefinedHandlingType.LOGGING),

        /** 役職 */
        Skill(cd -> CDef.Skill.of(cd), nm -> CDef.Skill.byName(nm)
        , () -> CDef.Skill.listAll(), gp -> CDef.Skill.listByGroup(gp)
        , ClassificationCodeType.String, ClassificationUndefinedHandlingType.LOGGING),

        /** メッセージ種別 */
        MessageType(cd -> CDef.MessageType.of(cd), nm -> CDef.MessageType.byName(nm)
        , () -> CDef.MessageType.listAll(), gp -> CDef.MessageType.listByGroup(gp)
        , ClassificationCodeType.String, ClassificationUndefinedHandlingType.LOGGING),

        /** 死亡理由 */
        DeadReason(cd -> CDef.DeadReason.of(cd), nm -> CDef.DeadReason.byName(nm)
        , () -> CDef.DeadReason.listAll(), gp -> CDef.DeadReason.listByGroup(gp)
        , ClassificationCodeType.String, ClassificationUndefinedHandlingType.LOGGING),

        /** 能力種別 */
        AbilityType(cd -> CDef.AbilityType.of(cd), nm -> CDef.AbilityType.byName(nm)
        , () -> CDef.AbilityType.listAll(), gp -> CDef.AbilityType.listByGroup(gp)
        , ClassificationCodeType.String, ClassificationUndefinedHandlingType.LOGGING),

        /** 秘話可能範囲 */
        AllowedSecretSay(cd -> CDef.AllowedSecretSay.of(cd), nm -> CDef.AllowedSecretSay.byName(nm)
        , () -> CDef.AllowedSecretSay.listAll(), gp -> CDef.AllowedSecretSay.listByGroup(gp)
        , ClassificationCodeType.String, ClassificationUndefinedHandlingType.LOGGING),

        /** 表情種別 */
        FaceType(cd -> CDef.FaceType.of(cd), nm -> CDef.FaceType.byName(nm)
        , () -> CDef.FaceType.listAll(), gp -> CDef.FaceType.listByGroup(gp)
        , ClassificationCodeType.String, ClassificationUndefinedHandlingType.LOGGING),

        /** 村参加者ステータス種別 */
        VillagePlayerStatusType(cd -> CDef.VillagePlayerStatusType.of(cd), nm -> CDef.VillagePlayerStatusType.byName(nm)
        , () -> CDef.VillagePlayerStatusType.listAll(), gp -> CDef.VillagePlayerStatusType.listByGroup(gp)
        , ClassificationCodeType.String, ClassificationUndefinedHandlingType.LOGGING),

        /** 村タグ種別 */
        VillageTagItem(cd -> CDef.VillageTagItem.of(cd), nm -> CDef.VillageTagItem.byName(nm)
        , () -> CDef.VillageTagItem.listAll(), gp -> CDef.VillageTagItem.listByGroup(gp)
        , ClassificationCodeType.String, ClassificationUndefinedHandlingType.LOGGING);

        private static final Map<String, DefMeta> _nameMetaMap = new HashMap<>();
        static {
            for (DefMeta value : values()) {
                _nameMetaMap.put(value.name().toLowerCase(), value);
            }
        }
        private final Function<Object, OptionalThing<? extends Classification>> _ofCall;
        private final Function<String, OptionalThing<? extends Classification>> _byNameCall;
        private final Supplier<List<? extends Classification>> _listAllCall;
        private final Function<String, List<? extends Classification>> _listByGroupCall;
        private final ClassificationCodeType _codeType;
        private final ClassificationUndefinedHandlingType _undefinedHandlingType;
        private DefMeta(Function<Object, OptionalThing<? extends Classification>> ofCall
                      , Function<String, OptionalThing<? extends Classification>> byNameCall
                      , Supplier<List<? extends Classification>> listAllCall
                      , Function<String, List<? extends Classification>> listByGroupCall
                      , ClassificationCodeType codeType
                      , ClassificationUndefinedHandlingType undefinedHandlingType
                ) {
            _ofCall = ofCall;
            _byNameCall = byNameCall;
            _listAllCall = listAllCall;
            _listByGroupCall = listByGroupCall;
            _codeType = codeType;
            _undefinedHandlingType = undefinedHandlingType;
        }
        public String classificationName() { return name(); } // same as definition name

        public OptionalThing<? extends Classification> of(Object code) { return _ofCall.apply(code); }
        public OptionalThing<? extends Classification> byName(String name) { return _byNameCall.apply(name); }

        public Classification codeOf(Object code) // null allowed, old style
        { return of(code).orElse(null); }
        public Classification nameOf(String name) { // null allowed, old style
            if (name == null) { return null; } // for compatible
            return byName(name).orElse(null); // case insensitive
        }

        public List<Classification> listAll()
        { return toClsList(_listAllCall.get()); }
        public List<Classification> listByGroup(String groupName) // exception if not found
        { return toClsList(_listByGroupCall.apply(groupName)); }

        @SuppressWarnings("unchecked")
        private List<Classification> toClsList(List<?> clsList) { return (List<Classification>)clsList; }

        public List<Classification> listOf(Collection<String> codeList) { // copied from slimmer, old style
            if (codeList == null) {
                throw new IllegalArgumentException("The argument 'codeList' should not be null.");
            }
            List<Classification> clsList = new ArrayList<>(codeList.size());
            for (String code : codeList) {
                clsList.add(of(code).get());
            }
            return clsList;
        }
        public List<Classification> groupOf(String groupName) { // empty if not found, old style
            try {
                return listByGroup(groupName); // case insensitive
            } catch (IllegalArgumentException | ClassificationNotFoundException e) {
                return new ArrayList<>(); // null or not found
            }
        }

        public ClassificationCodeType codeType() { return _codeType; }
        public ClassificationUndefinedHandlingType undefinedHandlingType() { return _undefinedHandlingType; }

        public static OptionalThing<CDef.DefMeta> find(String classificationName) { // instead of valueOf()
            if (classificationName == null) { throw new IllegalArgumentException("The argument 'classificationName' should not be null."); }
            return OptionalThing.ofNullable(_nameMetaMap.get(classificationName.toLowerCase()), () -> {
                throw new ClassificationNotFoundException("Unknown classification: " + classificationName);
            });
        }
        public static CDef.DefMeta meta(String classificationName) { // old style so use find(name)
            return find(classificationName).orElseTranslatingThrow(cause -> {
                return new IllegalStateException("Unknown classification: " + classificationName);
            });
        }
    }

    public static class ZzzoneSlimmer<CLS extends CDef> {

        public static Set<String> toSisterSet(String[] sisters) { // used by initializer so static
            return Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(sisters)));
        }

        private final Class<CLS> _clsType;
        private final Map<String, CLS> _codeClsMap = new HashMap<>();
        private final Map<String, CLS> _nameClsMap = new HashMap<>();

        public ZzzoneSlimmer(Class<CLS> clsType, CLS[] values) {
            _clsType = clsType;
            initMap(values);
        }

        private void initMap(CLS[] values) {
            for (CLS value : values) {
                _codeClsMap.put(value.code().toLowerCase(), value);
                for (String sister : value.sisterSet()) {
                    _codeClsMap.put(sister.toLowerCase(), value);
                }
                _nameClsMap.put(value.name().toLowerCase(), value);
            }
        }

        public OptionalThing<CLS> of(Object code) {
            if (code == null) {
                return OptionalThing.ofNullable(null, () -> {
                    throw new ClassificationNotFoundException("null code specified");
                });
            }
            if (_clsType.isAssignableFrom(code.getClass())) {
                @SuppressWarnings("unchecked")
                CLS cls = (CLS) code;
                return OptionalThing.of(cls);
            }
            if (code instanceof OptionalThing<?>) {
                return of(((OptionalThing<?>) code).orElse(null));
            }
            return OptionalThing.ofNullable(_codeClsMap.get(code.toString().toLowerCase()), () -> {
                throw new ClassificationNotFoundException("Unknown classification code: " + code);
            });
        }

        public OptionalThing<CLS> byName(String name) {
            if (name == null) {
                throw new IllegalArgumentException("The argument 'name' should not be null.");
            }
            return OptionalThing.ofNullable(_nameClsMap.get(name.toLowerCase()), () -> {
                throw new ClassificationNotFoundException("Unknown classification name: " + name);
            });
        }

        public CLS codeOf(Object code) {
            if (code == null) {
                return null;
            }
            if (_clsType.isAssignableFrom(code.getClass())) {
                @SuppressWarnings("unchecked")
                CLS cls = (CLS) code;
                return cls;
            }
            return _codeClsMap.get(code.toString().toLowerCase());
        }

        public CLS nameOf(String name, java.util.function.Function<String, CLS> valueOfCall) {
            if (name == null) {
                return null;
            }
            try {
                return valueOfCall.apply(name);
            } catch (RuntimeException ignored) { // not found
                return null;
            }
        }

        public List<CLS> listAll(CLS[] clss) {
            return new ArrayList<>(Arrays.asList(clss));
        }

        public List<CLS> listOf(Collection<String> codeList) {
            if (codeList == null) {
                throw new IllegalArgumentException("The argument 'codeList' should not be null.");
            }
            List<CLS> clsList = new ArrayList<>(codeList.size());
            for (String code : codeList) {
                clsList.add(of(code).get());
            }
            return clsList;
        }
    }
}

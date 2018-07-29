package com.ort.app.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
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

import com.ort.app.web.form.RandomKeywordForm;
import com.ort.app.web.form.validator.RandomKeywordFormValidator;
import com.ort.app.web.model.RandomMessageResultContent;
import com.ort.app.web.model.inner.RandomMessageDetailDto;
import com.ort.dbflute.exbhv.RandomContentBhv;
import com.ort.dbflute.exbhv.RandomKeywordBhv;
import com.ort.dbflute.exentity.RandomContent;
import com.ort.dbflute.exentity.RandomKeyword;

@Controller
public class RandomController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private RandomKeywordBhv randomKeywordBhv;

    @Autowired
    private RandomContentBhv randomContentBhv;

    @Autowired
    private RandomKeywordFormValidator randomKeywordFormValidator;

    @InitBinder("randomKeywordForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(randomKeywordFormValidator);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @GetMapping("/random-message")
    private String index(Model model) {
        createIndexModel(model);
        return "random-message";
    }

    @GetMapping("/new-random-keyword")
    private String newRandomKeyword(Model model) {
        model.addAttribute("randomKeywordForm", new RandomKeywordForm());
        return "new-random-keyword";
    }

    @PostMapping("/new-random-keyword")
    private String registerRandomKeyword(@Validated @ModelAttribute("randomKeywordForm") RandomKeywordForm form, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("randomKeywordForm", form);
            return "new-random-keyword";
        }

        int keywordCount = randomKeywordBhv.selectCount(cb -> {
            cb.query().setKeyword_Equal(form.getKeyword());
        });
        if (keywordCount > 0) {
            model.addAttribute("errorMessage", "すでに同じキーワードで登録されています");
            model.addAttribute("randomKeywordForm", form);
            return "new-random-keyword";
        }

        insertKeywordAndContent(form);

        return "redirect:/random-message";
    }

    @GetMapping("/random-keyword/{keywordId}")
    private String randomKeywordIndex(@PathVariable Integer keywordId, Model model) {
        OptionalEntity<RandomKeyword> optKeyword = randomKeywordBhv.selectEntity(cb -> {
            cb.query().setRandomKeywordId_Equal(keywordId);
            cb.query().addOrderBy_RandomKeywordId_Asc();
        });
        if (!optKeyword.isPresent()) {
            return "random-message";
        }
        RandomKeyword randomKeyword = optKeyword.get();
        randomKeywordBhv.loadRandomContent(randomKeyword, contentCB -> {
            contentCB.query().addOrderBy_RandomContentId_Asc();
        });
        String message = String.join("\n",
                randomKeyword.getRandomContentList().stream().map(content -> content.getRandomMessage()).collect(Collectors.toList()));
        RandomKeywordForm form = new RandomKeywordForm();
        form.setKeyword(randomKeyword.getKeyword());
        form.setMessage(message);
        model.addAttribute("randomKeywordForm", form);

        return "random-keyword";
    }

    @PostMapping("/delete-random-keyword")
    private String deleteRandomKeyword(RandomKeywordForm form, Model model) {
        randomContentBhv.queryDelete(cb -> {
            cb.query().queryRandomKeyword().setKeyword_Equal(form.getKeyword());
        });
        randomKeywordBhv.queryDelete(cb -> {
            cb.query().setKeyword_Equal(form.getKeyword());
        });
        return "redirect:/random-message";
    }

    @PostMapping("/update-random-keyword")
    private String updateRandomKeyword(@Validated @ModelAttribute("randomKeywordForm") RandomKeywordForm form, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("randomKeywordForm", form);
            return "random-keyword";
        }

        randomContentBhv.queryDelete(cb -> {
            cb.query().queryRandomKeyword().setKeyword_Equal(form.getKeyword());
        });
        OptionalEntity<RandomKeyword> optKeyword = randomKeywordBhv.selectEntity(cb -> {
            cb.query().setKeyword_Equal(form.getKeyword());
        });
        if (!optKeyword.isPresent()) {
            return "random-message";
        }
        Stream.of(form.getMessage().trim().split("\r\n")).forEach(mes -> {
            RandomContent content = new RandomContent();
            content.setRandomKeywordId(optKeyword.get().getRandomKeywordId());
            content.setRandomMessage(mes);
            randomContentBhv.insert(content);
        });

        return "redirect:/random-message";
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void createIndexModel(Model model) {
        ListResultBean<RandomKeyword> randomKeywordList = randomKeywordBhv.selectList(cb -> {
            cb.query().addOrderBy_RandomKeywordId_Asc();
        });
        randomKeywordBhv.loadRandomContent(randomKeywordList, contentCB -> {
            contentCB.query().addOrderBy_RandomContentId_Asc();
        });
        RandomMessageResultContent content = new RandomMessageResultContent();
        List<RandomMessageDetailDto> detailList = randomKeywordList.stream().map(randomKeyword -> {
            RandomMessageDetailDto detail = new RandomMessageDetailDto();
            detail.setKeywordId(randomKeyword.getRandomKeywordId());
            detail.setKeyword(randomKeyword.getKeyword());
            detail.setContentExample(String.join("\n",
                    randomKeyword.getRandomContentList().stream().map(randomContent -> randomContent.getRandomMessage()).collect(
                            Collectors.toList())));
            return detail;
        }).collect(Collectors.toList());
        content.setRandomKeywordList(detailList);
        model.addAttribute("content", content);
    }

    private void insertKeywordAndContent(RandomKeywordForm form) {
        RandomKeyword keyword = new RandomKeyword();
        keyword.setKeyword(form.getKeyword());
        randomKeywordBhv.insert(keyword);
        Integer keywordId = keyword.getRandomKeywordId();

        Stream.of(form.getMessage().trim().split("\r\n")).forEach(mes -> {
            RandomContent content = new RandomContent();
            content.setRandomKeywordId(keywordId);
            content.setRandomMessage(mes);
            randomContentBhv.insert(content);
        });
    }
}

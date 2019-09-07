package com.tech4flag.community.scheduled;

import com.tech4flag.community.cache.HotTagCache;
import com.tech4flag.community.mapper.QuestionMapper;
import com.tech4flag.community.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-09-06 21:18
 */
@Component
@Slf4j
public class HotTagTask {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;


    @Scheduled(fixedRate = 1000*60*60*3)
    public void hotTag(){
        List<Question> questionList = questionMapper.allList();
        Map<String, Integer> tagsMap = new HashMap<>();
        for (Question question : questionList) {

            String[] tags = StringUtils.split(question.getTag(), ",");
            for (String tag : tags) {
                Integer hotDotNum = tagsMap.get(tag);
                if (hotDotNum !=null){
                    tagsMap.put(tag,hotDotNum+5+question.getCommentCount());
                }else {
                    tagsMap.put(tag,5+question.getCommentCount());
                }

            }
        }
        hotTagCache.setTags(tagsMap);
        hotTagCache.updateTags(tagsMap);

    }
}

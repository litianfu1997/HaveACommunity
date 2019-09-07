package com.tech4flag.community.cache;

import com.tech4flag.community.dto.HotTagDTO;
import lombok.Data;
import org.apache.catalina.Host;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-09-06 21:46
 */
@Component
@Data
public class HotTagCache {
    private  Map<String,Integer> tags = new HashMap<>();
    private List<String> hots =new ArrayList<>();
    public void updateTags(Map<String,Integer> tags){
        int max = 5;
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>();
        tags.forEach(
                (name,priority)->{
                    HotTagDTO hotTagDTO = new HotTagDTO();
                    hotTagDTO.setName(name);
                    hotTagDTO.setPriority(priority);
                    if (priorityQueue.size()<max){
                        priorityQueue.add(hotTagDTO);
                    }else {
                        HotTagDTO minHot = priorityQueue.peek();
                        if (hotTagDTO.compareTo(minHot)>0){
                            priorityQueue.poll();//将最小元素取出来
                            priorityQueue.add(hotTagDTO);//将大的元素放进去
                        }
                    }
                }
        );
        List<String> caches = new ArrayList<>();
        HotTagDTO poll =priorityQueue.poll();
        hots.add(poll.getName());
        while (poll!=null){
            caches.add(0,poll.getName());
            poll = priorityQueue.poll();
        }
        hots = caches;
    }
}

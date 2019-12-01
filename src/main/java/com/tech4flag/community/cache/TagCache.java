package com.tech4flag.community.cache;

import com.tech4flag.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-16 10:39
 */
public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS =new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript","php","css","html","html5","java","node.js","python","c++","c","golang","objective-c","typescript","shell","c#","swift","sass","bash","ruby","less","asp.net","lua","scala","coffeescript","actionscript","rust","erlang","perl"));
        tagDTOS.add(program);
        TagDTO programLive = new TagDTO();
        programLive.setCategoryName("程序人生");
        programLive.setTags(Arrays.asList("开发心得","数据结构","编程随想","人生感悟","职场吐槽"));
        tagDTOS.add(programLive);
        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel","spring","express","django","flask","yii","ruby-on-rails tornado","koa","struts"));
        tagDTOS.add(framework);
        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","缓存","tomcat","负载均衡","unix","hadoop","windows-server"));
        tagDTOS.add(server);
        TagDTO database = new TagDTO();
        database.setCategoryName("数据库和缓存");
        database.setTags(Arrays.asList("mysql","redis","mongodb","sql","oracle","nosql","memcached","sqlserver","postgresql","sqlite"));
        tagDTOS.add(database);
        TagDTO devTools = new TagDTO();
        devTools.setCategoryName("开发工具");
        devTools.setTags(Arrays.asList("git","github","visual-studio-code","vim","sublime-text","xcode intellij-idea","eclipse","maven","ide","svn","visual-studio","atom emacs","textmate","hg"));
        tagDTOS.add(devTools);
        return tagDTOS;
    }
    public static String isValid(String tags){
        String[] split = StringUtils.split(tags,",");
        List<TagDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }
}

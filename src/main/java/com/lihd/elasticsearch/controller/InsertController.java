package com.lihd.elasticsearch.controller;

import com.lihd.elasticsearch.highlevel.ElasticSearchInsert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@ResponseBody
public class InsertController {


    @Resource
    private ElasticSearchInsert elasticSearchInsert;

    @RequestMapping("/insert/{index}")
    public String insert(@PathVariable String index) throws IOException {

        elasticSearchInsert.insert(index);

        return "success";

    }


    @RequestMapping("/batchInsert/{index}")
    public String batchInsert(@PathVariable String index) throws IOException {

        elasticSearchInsert.batchInsert(index);

        return "success";

    }


}

package com.lihd.elasticsearch.controller;

import com.lihd.elasticsearch.highlevel.ElasticSearchSelect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@ResponseBody
public class SelectController {

    @Resource
    private ElasticSearchSelect elasticSearchSelect;


    @RequestMapping("/select/{index}/{method}/{value}")
    public String select(@PathVariable String index, @PathVariable String method, @PathVariable String value) throws IOException {


        String result = "";

        switch (method) {
            case "term":
                result = elasticSearchSelect.term(index, value);
                break;
            case "match":
                result = elasticSearchSelect.match(index, value);
                break;
            case "like":
                result = elasticSearchSelect.like(index, value);
                break;
            case "and":
                result = elasticSearchSelect.and(index, value);
                break;
            case "or":
                result = elasticSearchSelect.or(index, value);
                break;
            case "range":
                result = elasticSearchSelect.range(index, value);
                break;
            case "pagination":
                result = elasticSearchSelect.pagination(index, value);
                break;
            default:
                result = elasticSearchSelect.term(index, value);
                break;

        }

        return result;
    }


}

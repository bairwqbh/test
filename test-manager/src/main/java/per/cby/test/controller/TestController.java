package per.cby.test.controller;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import per.cby.test.model.Test;

/**
 * <p>
 * 传输通道 前端控制器
 * </p>
 *
 * @author chenboyang
 * @since 2019-11-04
 */
@Validated
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping
    public Object post(@RequestBody @Valid Test test) {
        return true;
    }

    @GetMapping
    public Object get(@RequestParam String id, @RequestParam String name) {
        return true;
    }

}

package pers.walyex.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.walyex.common.core.util.ResultUtil;

@RestController
public class InventoryController {


    @GetMapping("/getInventory")
    public Object getInventory() {

        return ResultUtil.getSuccessResult(300);
    }

}

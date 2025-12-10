package br.com.william.planner.controllers.dto;

import br.com.william.planner.model.Record;
import br.com.william.planner.services.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/record")
@CrossOrigin("*")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService service;

    public Record save(@RequestBody Record record){
        return service.save(record);
    }
}

package br.com.william.planner.services;

import br.com.william.planner.model.Record;
import br.com.william.planner.repositories.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository repository;

    public Record save(Record record){
        return repository.save(record);
    }
}

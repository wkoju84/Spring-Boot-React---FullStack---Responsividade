package br.com.william.planner.repositories;

import br.com.william.planner.model.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordCustomRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Page<Record> findPaginated(String service, String customer, String location, LocalDate initialDate,
                                      LocalDate finalDate, Boolean canceled, Boolean done, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("date_time").descending());

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        Integer totalRecords = count(service, customer, location, initialDate,
                finalDate, canceled, done, parameterSource);

        List<Record> records = find(service, customer, location, initialDate, finalDate, canceled, done,
                size, parameterSource, pageRequest);

        return new PageImpl<>(records, pageRequest, totalRecords);
    }

    private List<Record> find(String service, String customer, String location, LocalDate initialDate,
                              LocalDate finalDate, Boolean canceled, Boolean done, int size,
                              MapSqlParameterSource parameterSource, PageRequest pageRequest){

        String dataSql = "SELECT COUNT(*) FROM record WHERE 1 = 1"
                + buildWhereClause(
                service, customer, location, initialDate, finalDate, canceled, done, parameterSource)
                + " ORDER BY date_time DESC LIMIT :limit OFFSET :offset";

        parameterSource.addValue("limit", size);
        parameterSource.addValue("offset", pageRequest.getOffset());
        return jdbcTemplate.query(dataSql, parameterSource, new BeanPropertyRowMapper<>(Record.class));
    }

    private Integer count(String service, String customer, String location, LocalDate initialDate,
                          LocalDate finalDate, Boolean canceled, Boolean done, MapSqlParameterSource parameterSource){

        String countSql = "SELECT COUNT(*) FROM record WHERE 1 = 1"
                + buildWhereClause(
                        service, customer, location, initialDate, finalDate, canceled, done, parameterSource);
        Integer total = jdbcTemplate.queryForObject(countSql, parameterSource, Integer.class);
        return total != null ? total : 0;//verifica se é null
    }

    private String buildWhereClause(String service, String customer, String location, LocalDate initialDate,
                                    LocalDate finalDate, Boolean canceled, Boolean done,
                                    MapSqlParameterSource parameterSource){

        StringBuilder whereClause = new StringBuilder();

        if (service != null){
            whereClause.append(" AND service LIKE :service");
            parameterSource.addValue("service", "%" + service + "%");
        }

        if (customer != null){
            whereClause.append(" AND customer LIKE :customer");
            parameterSource.addValue("customer", "%" + customer + "%");
        }

        if (location != null){
            whereClause.append(" AND location LIKE :location");
            parameterSource.addValue("location", "%" + location + "%");
        }

        if (initialDate != null){

            if (finalDate != null){

                whereClause.append(" AND date_time BETWEN :initialDate AND :finalDate");
                parameterSource.addValue("initialDate", initialDate.atStartOfDay());//0h
                parameterSource.addValue("finalDate", finalDate.atTime(LocalTime.MAX));//23h59
            }
            else {
                whereClause.append(" AND date_time BETWEN :initialDate AND :finalDate");
                parameterSource.addValue("initialDate", initialDate.atStartOfDay());//0h
                parameterSource.addValue("finalDate", LocalDate.now().plusYears(1));//add 1 ano
            }
        }

        if (done != null){
            whereClause.append(" AND done = :done");
            parameterSource.addValue("done", done);
        }

        if (canceled != null){
            whereClause.append(" AND canceled = :canceled");
            parameterSource.addValue("canceled", canceled);
        }

        return whereClause.toString();
    }
}

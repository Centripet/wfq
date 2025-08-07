package org.wfq.wufangquan.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ElasticSearchService {

    private final ElasticsearchClient esClient;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Map<String, Object> searchProject(String projectId, String keyword) throws IOException {
        System.out.println(projectId);
        System.out.println(keyword);
        SearchResponse<Map> response = esClient.search(s -> s
                        .index("index_w_project,index_w_project_stage,index_w_project_task")
                        .query(q -> q
                                .bool(b -> b
                                        .must(m -> m
                                                .term(t -> t
                                                        .field("project_id")
                                                        .value(projectId)
                                                )
                                        )
                                        .must(m -> m
                                                .multiMatch(mm -> mm
                                                        .query(keyword)
                                                        .fields("*")
                                                        .type(TextQueryType.BestFields)
                                                        .operator(Operator.And)
                                                )
                                        )
                                )
                        ),
                Map.class
        );


        System.out.println(response);


        List<Hit<Map>> hits = response.hits().hits();

        List<Map<String, Object>> sources = new ArrayList<>();

        for (Hit<Map> hit : hits) {
            Map<String, Object> source = hit.source();

            // 检查并转换时间戳字段
            Object ts = source.get("create_time");
            if (ts instanceof Number) {
                String dateStr = sdf.format(new Date(((Number) ts).longValue()));
                source.put("create_time", dateStr);
            }

            ts = source.get("deadline");
            if (ts instanceof Number) {
                String dateStr = sdf.format(new Date(((Number) ts).longValue()));
                source.put("deadline", dateStr);
            }

            sources.add(source);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("total", hits.size());
        res.put("hits", sources);

        return res;
    }

}
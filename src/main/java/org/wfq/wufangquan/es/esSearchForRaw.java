package org.wfq.wufangquan.es;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.core.Map;
import org.springframework.data.elasticsearch.core.SearchHits;

@RequiredArgsConstructor
public SearchHits<Map> searchProjectData(String projectId, String keyword) {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    String rawJson = """
        {
          "query": {
            "bool": {
              "must": [
                { "term": { "project_id": "%s" } },
                { "multi_match": {
                    "query": "%s",
                    "type": "best_fields",
                    "fields": ["*"],
                    "operator": "and"
                  }
                }
              ]
            }
          }
        }
        """.formatted(projectId, keyword);

    NativeSearchQuery query = new NativeSearchQueryBuilder()
            .withQuery(QueryBuilders.wrapperQuery(rawJson))
            .build();

    return elasticsearchRestTemplate.search(query, Map.class);
}

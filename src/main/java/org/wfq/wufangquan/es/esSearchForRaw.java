//package org.wfq.wufangquan.es;
//
//import lombok.RequiredArgsConstructor;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.core.Map;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.springframework.data.elasticsearch.core.SearchHits;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//public class esSearchForRaw {
//    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
//    private RestHighLevelClient restHighLevelClient;
//
//    public SearchHits<Map> searchProjectData(String projectId, String keyword) {
//        String rawJson = """
//        {
//          "query": {
//            "bool": {
//              "must": [
//                { "term": { "project_id": "%s" } },
//                { "multi_match": {
//                    "query": "%s",
//                    "type": "best_fields",
//                    "fields": ["*"],
//                    "operator": "and"
//                  }
//                }
//              ]
//            }
//          }
//        }
//        """.formatted(projectId, keyword);
//
//        NativeSearchQuery query = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.wrapperQuery(rawJson))
//                .build();
//
//        return elasticsearchRestTemplate.search(query, Map.class);
//    }
//
//    public SearchResponse nativeSearch() throws IOException {
//        SearchRequest searchRequest = new SearchRequest(new String[]{
//                "index_w_project", "index_w_project_stage", "index_w_project_task"
//        });
//
//        String rawJson = """
//        {
//          "query": {
//            "bool": {
//              "must": [
//                { "term": { "project_id": "11111" } },
//                { "multi_match": {
//                    "query": "测试项目",
//                    "type": "best_fields",
//                    "fields": ["*"],
//                    "operator": "and"
//                  }
//                }
//              ]
//            }
//          }
//        }
//        """;
//
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.wrapperQuery(rawJson));
//        searchRequest.source(sourceBuilder);
//
//        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//    }
//}
//

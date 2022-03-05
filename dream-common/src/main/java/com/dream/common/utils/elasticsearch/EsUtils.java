//package com.dream.common.utils.elasticsearch;
//
//import com.alibaba.fastjson.JSON;
//import com.dream.common.utils.reflect.ReflectUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.elasticsearch.action.DocWriteResponse;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetRequest;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.support.master.AcknowledgedResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.text.Text;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.rest.RestStatus;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//import org.elasticsearch.search.sort.FieldSortBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//
///**
// * @author Tan
// */
//public class EsUtils{
//    private static Logger log = LoggerFactory.getLogger(ReflectUtils.class);
//    /**
//     * 获取客户端
//     * @return
//     */
//    public static RestHighLevelClient getRestHighLevelClient() throws Exception {
//        return EsClientPool.getClient();
//    }
//
//    /**
//     * 关闭客户端
//     */
//    public static void closeClient(RestHighLevelClient client) {
//     EsClientPool.returnClient(client);
//    }
//
//    /**
//     * 创建索引 分片和副本采用默认值
//     * @param esIndex
//     * @return false索引创建失败，true索引创建成功
//     */
//    public static boolean createIndex(String esIndex) throws Exception {
//        //获取客户端
//        RestHighLevelClient client = getRestHighLevelClient();
//        boolean result = false;
//        if (!isIndexExist(esIndex)) {
//            //1.创建索引请求
//            CreateIndexRequest request = new CreateIndexRequest(esIndex);
//            try {
//                //2.客户端执行请求，请求之后得到相应
//                CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
//                result = response.isAcknowledged();
//                if (result) {
//                    log.info(String.format("索引%s已创建", esIndex));
//                } else {
//                    log.info(String.format("索引%s创建失败", esIndex));
//                }
//            } catch (Exception e) {
//                log.error("ex",e);
//            }finally {
//                closeClient(client);
//            }
//        }else {
//            log.info(String.format("索引%s已存在", esIndex));
//        }
//        return result;
//    }
//
//    /**
//     * 新建索引
//     * @param esIndex
//     * @param shards       分片数
//     * @param replications 副本数
//     * @param fields       字段名->类型
//     */
//    public static void createIndex(String esIndex, int shards, int replications, Map<String, String> fields) throws Exception {
//        if (!isIndexExist(esIndex)) {
//            //获取客户端
//            RestHighLevelClient client = getRestHighLevelClient();
//            try {
//                XContentBuilder builder = XContentFactory.jsonBuilder()
//                        .startObject()
//                        .field("properties")
//                        .startObject();
//                for (String s : fields.keySet()) {
//                    builder.field(s).startObject().field("index", "true").field("type", fields.get(s)).endObject();
//                }
//                builder.endObject().endObject();
//                CreateIndexRequest request = new CreateIndexRequest(esIndex);
//                request.settings(Settings.builder()
//                        .put("index.number_of_shards", shards)
//                        .put("index.number_of_replicas", replications)
//                ).mapping(builder);
//                CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
//                boolean acknowledged = createIndexResponse.isAcknowledged();
//                if (acknowledged) {
//                    log.info(String.format("索引%s创建成功", esIndex));
//                } else {
//                    log.info(String.format("索引%s创建失败", esIndex));
//                }
//            } catch (IOException e) {
//                log.error("ex",e);
//            }finally {
//                closeClient(client);
//            }
//        } else {
//            log.info(String.format("索引%s已存在", esIndex));
//        }
//    }
//
//    /**
//     * 删除索引
//     * @param esIndex
//     * @return false索引删除失败，true索引删除成功
//     */
//    public static boolean deleteIndex(String esIndex) throws Exception {
//        boolean result = false;
//        //获取客户端
//        RestHighLevelClient client = getRestHighLevelClient();
//        DeleteIndexRequest request = new DeleteIndexRequest(esIndex);
//        try {
//            AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
//            result = deleteIndexResponse.isAcknowledged();
//            if (result) {
//                log.info(String.format("索引%s已删除", esIndex));
//            } else {
//                log.info(String.format("索引%s删除失败", esIndex));
//            }
//        } catch (IOException e) {
//            log.error("ex",e);
//        }finally {
//            closeClient(client);
//        }
//        return result;
//    }
//
//    /**
//     * 判断索引是否存在
//     * @param esIndex
//     * @return
//     */
//    public static boolean isIndexExist(String esIndex) throws Exception {
//        boolean result = false;
//        //获取客户端
//        RestHighLevelClient client = getRestHighLevelClient();
//        GetIndexRequest request = new GetIndexRequest(esIndex);
//        try {
//            result = client.indices().exists(request, RequestOptions.DEFAULT);
//            if (result) {
//                log.info(String.format("索引%s已存在", esIndex));
//            } else {
//                log.info(String.format("索引%s不存在", esIndex));
//            }
//        } catch (IOException e) {
//            log.error("ex",e);
//        }finally {
//            closeClient(client);
//        }
//        return result;
//    }
//
//    /**
//     * 插入一条数据
//     * @param o
//     * @param esIndex
//     */
//    public static void addDocument(Object o,String esIndex) throws Exception {
//        //获取客户端
//        RestHighLevelClient client = getRestHighLevelClient();
//        IndexRequest request = new IndexRequest(esIndex);
//        //不设置id，es会自动分配id
//        //request.id("1");
//        //设置超时时间
//        //request.timeout("1s");
//        request.source(JSON.toJSONString(o), XContentType.JSON);
//        try {
//            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
//            RestStatus status = indexResponse.status();
//            System.out.println(status);
//        } catch (IOException e) {
//            log.error("ex",e);
//        }finally {
//            closeClient(client);
//        }
//    }
//
//    /**
//     * 根据id获取数据，返回map(字段名,字段值)
//     * @param esIndex
//     * @param id
//     * @return
//     */
//    public static Map<String, Object> getDocumentById(String esIndex, String id) throws Exception {
//        //获取客户端
//        RestHighLevelClient client = getRestHighLevelClient();
//        GetRequest request = new GetRequest(esIndex, id);
//        Map<String, Object> source = null;
//        try {
//            GetResponse response = client.get(request, RequestOptions.DEFAULT);
//            if (response.isExists()) {
//                source = response.getSource();
//            }
//        } catch (IOException e) {
//            log.error("ex",e);
//        }finally {
//            closeClient(client);
//        }
//        return source;
//    }
//
//    /**
//     * 更新文档 采用map传递数据方式
//     * @param esIndex
//     * @param id
//     * @param updateFields 更新的字段名->字段值
//     */
//    public static void updateDocumentById(String esIndex, String id, Map<String, Object> updateFields) throws Exception {
//        //获取客户端
//        RestHighLevelClient client = getRestHighLevelClient();
//        UpdateRequest request = new UpdateRequest(esIndex, id).doc(updateFields);
//        try {
//            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
//            if (response.status() == RestStatus.OK) {
//                log.info(String.format("更新索引为%s,id为%s的文档成功", response.getIndex(), response.getId()));
//            } else {
//                log.info(String.format("更新索引为%s,id为%s的文档失败", response.getIndex(), response.getId()));
//            }
//        } catch (IOException e) {
//            log.error("ex",e);
//        }finally {
//            closeClient(client);
//        }
//    }
//
//    /**
//     * 更新文档 采用对象传递数据方式
//     * @param esIndex
//     * @param id
//     * @param o 更新的值放入对象中
//     */
//    public static void updateDocumentById(String esIndex, String id, Object o) throws Exception {
//        //获取客户端
//        RestHighLevelClient client = getRestHighLevelClient();
//        UpdateRequest request = new UpdateRequest(esIndex, id).doc(JSON.toJSONString(o), XContentType.JSON);
//        try {
//            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
//            if (response.status() == RestStatus.OK) {
//                log.info(String.format("更新索引为%s,id为%s的文档成功", response.getIndex(), response.getId()));
//            } else {
//                log.info(String.format("更新索引为%s,id为%s的文档失败", response.getIndex(), response.getId()));
//            }
//        } catch (IOException e) {
//            log.error("ex",e);
//        }finally {
//            closeClient(client);
//        }
//    }
//
//    /**
//     * 删除指定id的文档
//     * @param esIndex
//     * @param id
//     */
//    public static boolean deleteDocumentById(String esIndex, String id) throws Exception {
//        //获取客户端
//        RestHighLevelClient client = getRestHighLevelClient();
//        boolean result = false;
//        DeleteRequest request = new DeleteRequest(esIndex, id);
//        try {
//            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
//            if (response.getResult() == DocWriteResponse.Result.DELETED) {
//                result = true;
//                log.info(String.format("id为%s的文档删除成功", id));
//            } else {
//                log.info(String.format("id为%s的文档删除失败", id));
//            }
//        } catch (IOException e) {
//            log.error("ex",e);
//        }finally {
//            closeClient(client);
//        }
//        return result;
//    }
//
//    /**
//     * 批量插入
//     * @param esIndex
//     * @param datalist 数据集，数据格式为map<字段名,字段值>
//     */
//    public static boolean bulkLoadMap(String esIndex, List<Map<String, Object>> datalist) throws Exception {
//        //获取客户端
//        RestHighLevelClient client = getRestHighLevelClient();
//        boolean result = false;
//        BulkRequest bulkRequest = new BulkRequest();
//        for (Map<String, Object> data : datalist) {
//            Object id = data.get("id");
//            //如果数据包含id字段，使用数据id作为文档id
//            if (id != null) {
//                data.remove("id");
//                bulkRequest.add(new IndexRequest(esIndex).id(id.toString()).source(data));
//            } else {//让es自动生成id
//                bulkRequest.add(new IndexRequest(esIndex).source(data));
//            }
//        }
//        try {
//            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
//            System.out.println(response.hasFailures());
//            if (!response.hasFailures()) {
//                result = true;
//                log.info(String.format("索引%s批量插入成功，共插入%d条", esIndex, datalist.size()));
//            } else {
//                log.info(String.format("索引%s批量插入失败", esIndex));
//            }
//        } catch (IOException e) {
//            log.error("ex",e);
//        }finally {
//            closeClient(client);
//        }
//        return result;
//    }
//
//    /**
//     * 批量插入
//     * @param esIndex
//     * @param list 直接插入对象集合
//     * @return
//     */
//    public static boolean bulkLoadObject(String esIndex,List<Object> list) throws Exception {
//        //获取客户端
//        RestHighLevelClient client = getRestHighLevelClient();
//        boolean result = false;
//        BulkRequest bulkRequest = new BulkRequest();
//        //等待时间
//        //bulkRequest.timeout("10s");
//        try {
//            for(int i=0;i<list.size();i++){
//                //如果数据中不包含id，自己生成id，当然如果自己不给id的话，es会自动生成id
//                if (!list.contains("id")) {
//                    bulkRequest.add(new IndexRequest(esIndex).id(""+(i+1)).source(JSON.toJSONString(list.get(i)),XContentType.JSON));
//                } else {//让es自动生成id
//                    bulkRequest.add(new IndexRequest(esIndex).source(JSON.toJSONString(list.get(i)),XContentType.JSON));
//                }
//            }
//            BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
//            if(!responses.hasFailures()){
//                result = true;
//            }
//        } catch (IOException e) {
//            log.error("ex",e);
//        }finally {
//            closeClient(client);
//        }
//        return result;
//    }
//
//    /**
//     * 全文检索
//     * @param query
//     * @return
//     */
//    public static Map<String,Object> search(SearchRequestQuery query) throws Exception {
//        //获取客户端
//        RestHighLevelClient client = getRestHighLevelClient();
//        Map<String,Object> result = new HashMap<>();
//        List<Map<String,Object>> list = new ArrayList<>();
//        // 1、创建查询索引
//        SearchRequest searchRequest = new SearchRequest(query.getEsIndex());
//        // 2、条件查询
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        //3.构建分页
//        int pageNo = 1,pageSize =10;
//        if(query.getPageNo() != null){
//            pageNo = query.getPageNo();
//        }
//        if(query.getPageSize() != null){
//            pageSize = query.getPageSize();
//        }
//        //3.1 es默认从第0页开始
//        sourceBuilder.from((pageNo - 1) * pageSize);
//        sourceBuilder.size(pageSize);
//        //4.构建基础查询（包含基础查询和过滤条件）【过滤关系，key为（and或者or或者not），value为过滤字段和值】
//        QueryBuilder queryBuilder =buildBasicQueryWithFilter(query);
//        sourceBuilder.query(queryBuilder);
//        //4.2 设置最长等待时间1分钟
//        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        // 5、高亮设置(替换返回结果文本中目标值的文本内容)
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        for (int i = 0; i < query.getKeywordFields().length; i++) {
//            highlightBuilder.field(query.getKeywordFields()[i]);
//        }
//        //5.1允许同一个检索词多次高亮，false则表示，同意字段中同一个检索词第一个位置的高亮，其他不高亮
//        highlightBuilder.requireFieldMatch(true);
//        highlightBuilder.preTags("<span style='color:red'>");
//        highlightBuilder.postTags("</span>");
//        sourceBuilder.highlighter(highlightBuilder);
//        //6.构建排序
//        String sortBy = query.getSortBy();
//        Boolean desc = query.getIsDesc();
//        if (StringUtils.isNotBlank(sortBy)) {
//            sourceBuilder.sort(new FieldSortBuilder(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
//        }
//        //7.聚合（分组）
//        Map<String, String> aggs = query.getAggMap();
//        if(aggs != null){
//            for (Map.Entry<String, String> entry : aggs.entrySet()) {
//                //聚合名称(分组）
//                String aggName = entry.getKey();
//                //聚合字段
//                String aggFiled = entry.getValue();
//                if(aggName != null || aggFiled != null) {
//                    sourceBuilder.aggregation(AggregationBuilders.terms(aggName).field(aggFiled+".keyword"));
//                }
//            }
//        }
//        //8、通过sourceFilter设置返回的结果字段,第一个参数是现实的字段，第二各个参数是不显示的字段，默认设置为null
//        sourceBuilder.fetchSource(query.getSourceFilter(),null);
//        //9、执行搜索
//        searchRequest.source(sourceBuilder);
//        try {
//            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//            for (SearchHit doc : searchResponse.getHits().getHits()) {
//                // 解析高亮字段
//                Map<String, HighlightField> highlightFields = doc.getHighlightFields();
//                for (int i = 0; i < query.getKeywordFields().length; i++) {
//                    HighlightField fieldTitle = highlightFields.get(query.getKeywordFields()[i]);
//                    // 获取原来的结果集
//                    Map<String, Object> sourceAsMap = doc.getSourceAsMap();
//                    if (fieldTitle != null) {
//                        // 获取内容中匹配的片段
//                        Text[] fragments = fieldTitle.fragments();
//                        // 设置当前的目标字段为空
//                        String new_fieldTitle = "";
//                        for (Text res : fragments) {
//                            new_fieldTitle += res;
//                        }
//                        // 将原来的结果替换为新结果
//                        sourceAsMap.put(query.getKeywordFields()[i], new_fieldTitle);
//                    }
//                    list.add(sourceAsMap);
//                }
//            }
//            // List 数组去重， 多字段查询高亮解析的时候存在数组重复的情况（优化方法未知！）
//            list = list.stream().distinct().collect(Collectors.toList());
//            int total = (int) searchResponse.getHits().getTotalHits().value;
//            result.put("data",list);
//            result.put("total",total);
//            result.put("totalPage",total== 0 ? 0: (total%pageSize == 0 ? total / pageSize : (total / pageSize) + 1));
//            result.put("pageSize",pageSize);
//            result.put("pageNo",pageNo);
//
//            //聚和结果处理
//            Aggregations aggregations = searchResponse.getAggregations();
//            List<Object> aggData = new ArrayList<>();
//            if(aggregations != null){
//                aggData = getAggData(aggregations,query);
//            }
//            result.put("aggData",aggData);
//
//        } catch (IOException e) {
//            log.error("ex",e);
//        }finally {
//            closeClient(client);
//        }
//        return result;
//    }
//
//    /**
//     * 聚合数据处理（分组）
//     * @param aggregations
//     * @param query
//     * @return
//     */
//    private static List<Object> getAggData( Aggregations aggregations ,SearchRequestQuery query) {
//        List<Object> result = new ArrayList<>();
//        for (Map.Entry<String, String> entry : query.getAggMap().entrySet()) {
//            LinkedHashMap<String,Object> map = new LinkedHashMap<>();
//            //聚合名称(分组）
//            String aggName = entry.getKey();
//            //聚合字段
//            String aggFiled = entry.getValue();
//            if(aggName != null) {
//                LinkedHashMap<String,Object> groupItem=new LinkedHashMap<>();
//                Terms aggregation = aggregations.get(aggName);
//                for (Terms.Bucket bucket : aggregation.getBuckets()) {
//                    map.put(bucket.getKey().toString(),bucket.getDocCount());
//                }
//                groupItem.put("aggregationName",aggName);
//                groupItem.put("aggregationField",aggFiled);
//                groupItem.put("aggregationData",map);
//                result.add(groupItem);
//            }
//        }
//        return result;
//    }
//
//    private static QueryBuilder buildBasicQueryWithFilter( SearchRequestQuery query ) {
//        String flag = "";
//		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//		BoolQueryBuilder shouldQuery = QueryBuilders.boolQuery();
//        //过滤条件(and,or,not关系)
//        Map<String, Object> filter = query.getFilter();
//        if(filter != null) {
//        	for (Map.Entry<String, Object> entry : filter.entrySet()) {
//                String key = entry.getKey();
//                flag = key;
//                Map<String, String> value = (Map<String, String>) entry.getValue();
//                	for (Map.Entry<String, String> map : value.entrySet()) {
//                		String filterKey = map.getKey();
//                		String filterValue = map.getValue();
//                		if(key == "and") {
//                			queryBuilder.filter(QueryBuilders.termQuery(filterKey, filterValue));
//                		}
//                		if(key == "or") {
//                			shouldQuery.should(QueryBuilders.termQuery(filterKey, filterValue));
//                		}
//                		if(key == "not") {
//                			queryBuilder.mustNot(QueryBuilders.termQuery(filterKey, filterValue));
//                		}
//                	}
//            }
//        }
//        //过滤日期期间的值，比如2019-07-01到2019-07-17
//        if(StringUtils.isNotBlank(query.getDateField()) || StringUtils.isNotBlank(query.getStartDate()) || StringUtils.isNotBlank(query.getEndDate())) {
//        	queryBuilder.must(QueryBuilders.rangeQuery(query.getDateField()).from(query.getStartDate()).to(query.getEndDate()));
//        }
//        //如果输入的查询条件为空，则查询所有数据
//		if(query.getKeyword() == null || "".equals(query.getKeyword())) {
//	      	queryBuilder.must(QueryBuilders.matchAllQuery());
//	      	return queryBuilder;
//	    }
//        if("or".equals(flag) ) {
//            //配置中文分词器并指定并分词的搜索方式operator
//        	queryBuilder.must(QueryBuilders.multiMatchQuery(query.getKeyword(), query.getKeywordFields()))
//                    //解决should和must共用不生效问题
//			        .must(shouldQuery);
//        }else {
//            //多字段查询，字段直接是or的关系
//            queryBuilder.must(QueryBuilders.multiMatchQuery(query.getKeyword(),query.getKeywordFields()));
//        	/*queryBuilder.must(QueryBuilders.multiMatchQuery(query.getKeyword(),query.getKeywordFields())
//                    .analyzer("ik_smart").operator(Operator.OR));*/
//        }
//		return queryBuilder;
//    }
//
//}
//

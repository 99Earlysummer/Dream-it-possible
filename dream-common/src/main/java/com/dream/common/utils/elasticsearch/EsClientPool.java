//package com.dream.common.utils.elasticsearch;
//
//import com.dream.common.config.ESClientConfig;
//import com.dream.common.utils.reflect.ReflectUtils;
//import org.apache.commons.pool2.PooledObject;
//import org.apache.commons.pool2.PooledObjectFactory;
//import org.apache.commons.pool2.impl.DefaultPooledObject;
//import org.apache.commons.pool2.impl.GenericObjectPool;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
///**
// * @author Tan
// *
// */
//public class EsClientPool{
//    private static Logger log = LoggerFactory.getLogger(ReflectUtils.class);
//
//    /**
//     * 集群节点
//     */
//    private static ESClientConfig esConfig;
//
//    @Autowired
//    public void setEsConfig(ESClientConfig esConfig) {
//        EsClientPool.esConfig = esConfig;
//    }
//
//    /**
//     * 对象池配置类，不写也可以，采用默认配置
//     */
//    private static GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//    // 采用默认配置maxTotal是8，池中有8个client
//    static {
//        poolConfig.setMaxTotal(20);
//        poolConfig.setMaxIdle(5);
//        poolConfig.setTestWhileIdle(true);
//        poolConfig.setTestOnBorrow(false);
//        poolConfig.setTimeBetweenEvictionRunsMillis(300000L);
//        poolConfig.setMinIdle(5);
//    }
//
//    private static GenericObjectPool<RestHighLevelClient> clientPool = new GenericObjectPool<RestHighLevelClient>(new PooledObjectFactory<RestHighLevelClient>(){
//        @Override
//        public PooledObject<RestHighLevelClient> makeObject(){
//            RestHighLevelClient client = null;
//            try {
//                client = new RestHighLevelClient(RestClient.builder(new HttpHost(esConfig.getServer(),esConfig.getPort(),esConfig.getScheme())));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return new DefaultPooledObject<>(client);
//        }
//
//        @Override
//        public void destroyObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
//            RestHighLevelClient client = pooledObject.getObject();
//            client.close();
//        }
//
//        @Override
//        public boolean validateObject(PooledObject<RestHighLevelClient> pooledObject) {
//            return true;
//        }
//
//        @Override
//        public void activateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
//           log.info("激活客户端");
//        }
//
//        @Override
//        public void passivateObject( PooledObject<RestHighLevelClient> pooledObject) throws Exception {
//            log.info("释放客户端");
//        }
//    },poolConfig);
//
//    /**
//     * 获得对象
//     * @return
//     * @throws Exception
//     */
//    public static RestHighLevelClient getClient() throws Exception {
//        RestHighLevelClient client = clientPool.borrowObject();
//        return client;
//    }
//
//    /**
//     * 归还对象
//     * @param client
//     */
//    public static void returnClient(RestHighLevelClient client) {
//        if (client != null) {
//            clientPool.returnObject(client);
//        }
//    }
//
//}

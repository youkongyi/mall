package com.youkongyi.mall;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;
import com.google.gson.Gson;
import com.youkongyi.mall.nosql.elasticsearch.document.EsProduct;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest(classes = {MallApplication.class})
@RunWith(SpringRunner.class)
public class MallApplicationTests {

    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(ElasticsearchRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Test
    public void createIndex(){
        IndexOperations index = restTemplate.indexOps(EsProduct.class);
        index.create();
        index.putMapping();
    }

    @Test
    public void deleteIndex(){
        IndexOperations index = restTemplate.indexOps(EsProduct.class);
        index.delete();
    }

    @Test
    public void insertDoc(){

        EsProduct product = new EsProduct();
        product.setId(2L);
        product.setName("测试用1");
        product.setPrice(BigDecimal.valueOf(11.23));

        IndexQuery query = new IndexQueryBuilder()
                .withObject(product)
                .build();
        restTemplate.index(query, IndexCoordinates.of("pms"));
    }

    @Test
    public void updateDoc(){
        EsProduct product = new EsProduct();
        product.setId(1L);
        product.setName("测试用1");
        product.setPrice(BigDecimal.valueOf(11.23));
        Document document = Document.parse(new Gson().toJson(product));
        UpdateQuery updateQuery = UpdateQuery.builder("1").withDocument(document).build();
        restTemplate.update(updateQuery, IndexCoordinates.of("pms"));
    }

    @Test
    public void deleteDoc(){
//        Criteria criteria = Criteria.where("name").is("测试用4");
//        Query query = CriteriaQuery.builder(criteria).build();
//        restTemplate.delete(query, EsProduct.class, IndexCoordinates.of("pms"));
        EsProduct product = new EsProduct();
        product.setId(1L);
        product.setName("测试用1");
//        product.setPrice(BigDecimal.valueOf(11.23));
        restTemplate.delete(product, IndexCoordinates.of("pms"));
    }

    @Test
    public void queryDoc(){
        /*
         * 使用 Criteria 标准构建查询条件
         */
//        Criteria criteria = Criteria.where("name").is("测试用1");
//        Query query = new CriteriaQuery(criteria);
//        SearchHits<EsProduct> products = restTemplate.search(query, EsProduct.class, IndexCoordinates.of("pms"));
//        System.out.println(products.getTotalHits());
        /*
         * 使用 JSON 字符串查询,类似于RESTFul body进行查询
         */
        StringQuery query = new StringQuery("{ \"match\": { \"name\": \"测试用1\"  } } ");
        SearchHits<EsProduct> products = restTemplate.search(query, EsProduct.class, IndexCoordinates.of("pms"));
        System.out.println(products.getTotalHits());

        /*
         * 使用 org.elasticsearch.index.query.QueryBuilders 进行查询
         * queryStringQuery 字符串字段匹配
         * matchQuery 对应字段匹配
         * rangeQuery 范围匹配
         */
//        QueryBuilder builder = QueryBuilders.queryStringQuery("测试用1");
//        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(builder).build();
//        SearchHits<EsProduct> products = restTemplate.search(query, EsProduct.class, IndexCoordinates.of("pms"));
//        System.out.println(products.getTotalHits());

    }

    @Test
    public void oss(){
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-shanghai.aliyuncs.com/";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "";
        String accessKeySecret = "";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "mall-aimer";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = "mall/images/exampleobject.txt";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
        String filePath= "D:\\examplefile.txt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(filePath));
            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
             ObjectMetadata metadata = new ObjectMetadata();
             metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
             metadata.setObjectAcl(CannedAccessControlList.PublicReadWrite);
             putObjectRequest.setMetadata(metadata);

            // 上传文件。
            ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    private StringEncryptor encryptor;

    @Autowired
    public void setEncryptor(StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Test
    public void jasypt(){
        String j = encryptor.encrypt("e593813e7d4c729da37e5e41fe760b0e");
        String m = encryptor.decrypt(j);
        System.out.println(j);
        System.out.println(m);
    }

    @Test
    public void waite() throws InterruptedException {

        Object obj = new Object();
        synchronized (obj){
            while (true){
                System.out.println("start");
                obj.wait(2000);
                obj.notify();
                System.out.println("end");
                if(1 == 1){
                    break;
                }
            }
        }
        System.out.println("hell");
    }

    @Test
    public void maptest() throws Exception {

        Map<Integer,Integer> map = new HashMap<>(2);

        //获取HashMap整个类
        Class<?> mapType = map.getClass();
        //获取指定属性，也可以调用getDeclaredFields()方法获取属性数组
        Field threshold =  mapType.getDeclaredField("threshold");
        //将目标属性设置为可以访问
        threshold.setAccessible(true);
        //获取指定方法，因为HashMap没有容量这个属性，但是capacity方法会返回容量值
        Method capacity = mapType.getDeclaredMethod("capacity");
        //设置目标方法为可访问
        capacity.setAccessible(true);
        //打印刚初始化的HashMap的容量、阈值和元素数量
        System.out.println("容量："+capacity.invoke(map)+"    阈值："+threshold.get(map)+"    元素数量："+map.size());
        for (int i = 0;i<25;i++){
            map.put(i,i);
            //动态监测HashMap的容量、阈值和元素数量
            System.out.println("容量："+capacity.invoke(map)+"    阈值："+threshold.get(map)+"    元素数量："+map.size());
        }
    }

    @Test
    public void listtest() throws Exception {
        List<Integer> list = new ArrayList<>(26);
        list.add(1);
        System.out.println(list.size());
        System.out.println("1".length());
        System.out.println(new Object[]{"1"}.length);
        Class<?> mapType = list.getClass();
        Field threshold =  mapType.getDeclaredField("elementData");
        threshold.setAccessible(true);
        System.out.println("容量："+ ((Object[]) threshold.get(list)).length+ "元素数量: "  + list.size());
        for (int i = 0;i<25;i++){
            list.add(i);
            System.out.println("容量："+ ((Object[]) threshold.get(list)).length + "元素数量: "  + list.size() );
        }
    }

    @Test
    public void test3(){
        String str = "ACC";
        this.test5(str);
        System.out.println(str);
    }

    void test5(String str){
        str += "aaa";
    }


    @Test
    public void test5(){
        int i=0;

        if(i>1000){
            System.out.println(1);
        }

        if(i<=1000 && i!=0){
            System.out.println(2);
        }

        if (i==0){
            System.out.println(3);
        }


    }
}

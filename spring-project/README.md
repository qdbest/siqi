### 后端controller接收多个对象的情况
- 使用map，通过json进行对象转换，遇到Collect，需要对内部元素再进行转换
```java
// 1.引入依赖包
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.47</version>
</dependency>
    // 2.controller
    @PostMapping("/addMany")
    public ResultVO<String> addMany(@RequestBody Map mapData) throws Exception {
        //将map转换为对象
        PurchaseOrder purchaseOrder = JSONObject.parseObject(JSONObject.toJSONString(mapData.get("purchaseOrder")), PurchaseOrder.class);
        List<StockCommodity> stockCommodities = JSONObject.parseObject(JSONObject.toJSONString(mapData.get("stockCommodities")), List.class);

        stockCommodityService.addMany(purchaseOrder, stockCommodities);

        return ResultVOUtil.success("提交成功");
    }
    // service
    @Override
    @Transactional
    public void addMany(PurchaseOrder purchaseOrder, Set<StockCommodity> stockCommodities) throws Exception {
        List<StockCommodity> stockCommodityList=new ArrayList<>();
        for(Object o:stockCommodities){
            StockCommodity stockCommodity=JSONObject.parseObject(JSONObject.toJSONString(o),StockCommodity.class);
            stockCommodity.setPurchaseOrder(purchaseOrder);

            stockCommodity.setCommodity(commodityRepository.getOne(stockCommodity.getCommodity().getId()));

            stockCommodityList.add(stockCommodity);
        }
        try {
            stockCommodityRepository.saveAll(stockCommodities);
        } catch (Exception e) {
            throw new Exception("插入数据库错误，请检查是否填写完整！");
        }
    }
```
- 使用ObjectMapper进行json解析
```java
@PostMapping("/putIn")
    public ResultVO putIn(@RequestBody Map map) throws IOException{
        log.info("商品入库");
        PurchaseOrder purchaseOrder=objectMapper.readValue(objectMapper.writeValueAsString(map.get("purchaseOrder")), PurchaseOrder.class);
        JavaType javaType=objectMapper.getTypeFactory().constructParametricType(Set.class, PurchaseCommodity.class);
        Set<PurchaseCommodity> purchaseCommodities = objectMapper.readValue(objectMapper.writeValueAsString(map.get("purchaseCommodities")),javaType);
        Set<StockCommodity> stockCommodities=purchaseCommodityService.putInStorage(purchaseOrder, purchaseCommodities);

        return ResultVOUtil.success("入库成功", stockCommodities);
    }
```
- 封装对象传值
```java
// 1.封装pojo类
@Getter
@Setter
public class StockCommodityDto {
    private PurchaseOrder purchaseOrder;
    private Set<StockCommodity> stockCommodities;
}
    //2.controller
    @PostMapping("/addAll")
    public ResultVO<String> addAll(@RequestBody StockCommodityDto stockCommodityDto) throws Exception {
        stockCommodityService.addAll(stockCommodityDto.getPurchaseOrder(), stockCommodityDto.getStockCommodities());

        return ResultVOUtil.success("提交成功");
    }
    // 3.service
    @Override
    @Transactional
    public void addAll(PurchaseOrder purchaseOrder, Set<StockCommodity> stockCommodities) throws Exception {
        stockCommodities.forEach(stockCommodity -> {
            stockCommodity.setPurchaseOrder(purchaseOrder);
            stockCommodity.setCommodity(commodityRepository.getOne(stockCommodity.getCommodity().getId()));
        });
        try {
            stockCommodityRepository.saveAll(stockCommodities);
        } catch (Exception e) {
            throw new Exception("插入数据库错误，请检查是否填写完整！");
        }
    }
```
### jpa中实体类因懒加载使用代理类转换json时报错
- 报错内容
```java
2018-12-01 10:16:42.785 ERROR 8360 --- [nio-8899-exec-4] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.yucn.vo.ResultVO["data"]->com.yucn.entity.CartCommodity_$$_jvst8b3_2["handler"])] with root cause

com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.yucn.vo.ResultVO["data"]->com.yucn.entity.CartCommodity_$$_jvst8b3_2["handler"])
	at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1191) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.DatabindContext.reportBadDefinition(DatabindContext.java:313) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ser.impl.UnknownSerializer.failForEmpty(UnknownSerializer.java:71) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ser.impl.UnknownSerializer.serialize(UnknownSerializer.java:33) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:727) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:719) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:155) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:727) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:719) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:155) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ObjectWriter$Prefetch.serialize(ObjectWriter.java:1396) ~[jackson-databind-2.9.7.jar:2.9.7]
	at com.fasterxml.jackson.databind.ObjectWriter.writeValue(ObjectWriter.java:913) ~[jackson-databind-2.9.7.jar:2.9.7]
	at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.writeInternal(AbstractJackson2HttpMessageConverter.java:285) ~[spring-web-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.http.converter.AbstractGenericHttpMessageConverter.write(AbstractGenericHttpMessageConverter.java:102) ~[spring-web-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor.writeWithMessageConverters(AbstractMessageConverterMethodProcessor.java:272) ~[spring-webmvc-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.handleReturnValue(RequestResponseBodyMethodProcessor.java:180) ~[spring-webmvc-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite.handleReturnValue(HandlerMethodReturnValueHandlerComposite.java:82) ~[spring-web-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:119) ~[spring-webmvc-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:891) ~[spring-webmvc-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797) ~[spring-webmvc-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:991) ~[spring-webmvc-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:925) ~[spring-webmvc-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:974) ~[spring-webmvc-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:877) ~[spring-webmvc-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:661) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:851) ~[spring-webmvc-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:742) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52) ~[tomcat-embed-websocket-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99) ~[spring-web-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.springframework.web.filter.HttpPutFormContentFilter.doFilterInternal(HttpPutFormContentFilter.java:109) ~[spring-web-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:93) ~[spring-web-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200) ~[spring-web-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198) ~[tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:493) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:800) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:806) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1498) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142) [na:1.8.0_112]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617) [na:1.8.0_112]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) [tomcat-embed-core-8.5.34.jar:8.5.34]
	at java.lang.Thread.run(Thread.java:745) [na:1.8.0_112]

```
- 解决办法，在实体类上加注释
```java
//jpa懒加载会产生代理类，转换json的时候无法发射以下属性，系统会报错，因此忽略掉以下属性的json转换
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
```
- 参考资料
[Jackson与Hibernate配合时产生一些问题](https://blog.csdn.net/zxy16305/article/details/77816918)
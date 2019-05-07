## IoC容器之BeanFactory
&emsp;&emsp;Spring的IoC容器是一个IoC Service Provider，但是，这只是它被冠以IoC之名的部分原因，我们不能忽略的是“容器”。Spring的IoC容器是
一个提供了IoC支持的轻量级容器，除了基本的IoC支持，它作为轻量级容器还提供了IoC之外的支持。如在Spring的IoC容器之上，Spring还提供了相应的AOP框架
支持、企业级服务集成等服务。Spring的IoC容器和IoC Service Provider所提供的服务存在一定的交集。<br>
![](image/beanfactory01.png)

&emsp;&emsp;提供了两种容器类型：BeanFactory和ApplicationContext.
- **BeanFactory**：基础类型IoC容器，提供完整的IoC服务支持。如果没有特殊指定，默认使用延迟初始化策略(lazy-load)。只有客户端对象在需要访问某个受管
对象的时候，才对该对象进行初始化以及依赖注入操作。所以，相对来说，容器启动初期速度较快，所需的资源也有限。

- **ApplicationContext**：ApplicationContext是在BeanFactory的基础上构建，是相对比较高级的容器实现。除了拥有BeanFactory的所有支持，它还提供
了其它高级的特性，比如事件发布、国际化信息支持等。ApplicationContext所管理的对象，在该类型容器启动之后，默认全部初始化并绑定完成。所以，相对于
BeanFactory来说，ApplicationContext要求更多的系统资源，启动时长相对要比BeanFactory要长一些。二者的关系图：<br>
![](image/beanfactory02.png)

&emsp;&emsp;在新版中，ApplicationContext还继承EnvironmentCapable接口，ApplicationContext间接继承自BeanFactory，所以说它是构建于BeanFactory
之上的IoC容器。<br>
&emsp;&emsp;BeanFactory，顾名思义，就是生产Bean的工厂。既然Spring框架提倡使用POJO，那么把每个业务对象看做一个JavaBean对象，或许更容易理解
为什么Spring的IoC容器会起这么一个名字。作为Spring提供基本的IoC容器，BeanFactory可以完成作为IoC Service Provider的所有职责，包括业务对象
注册和对象间的依赖绑定。<br>
&emsp;&emsp;将应用所需的所有业务对象交给BeanFactory之后，剩下要做的就是直接从BeanFactory取得最终组装完成并且可用的对象。至于业务对象如何组装
，你不需要关心，BeanFactory可以帮你搞定。所以，对于客户端而言，与BeanFactory打交道其实很简单。最基本地，BeanFactory肯定会公开一些取得组装完成
的对象方法接口。对于独立的应用程序，这些主入口是和容器API直接耦合的

### 使用BeanFactory后的改观
&emsp;&emsp;确切的说，拥有BeanFactory之后，对于系统的设计和业务逻辑的处理并没有什么影响。而唯一的不同就是对象间依赖关系的解决方式改变了。现在需要
什么对象，直接让BeanFactory提供就可以了。所以简单点说，拥有BeanFactory之后，要使用IoC设计模式进行系统业务对象的开发。（实际上，即使不使用BeanFactory
之类的轻量级容器支持开发，开发中也尽量使用IoC模式）<br>
&emsp;&emsp;拥有BeanFactory之后，对象的创建和对象间依赖关系的绑定都由BeanFactory来完成。但是如何做，还是需要通过某种方式来告诉它，例如通过XML
文件来注册并管理对象间的依赖关系。
    
    <beans>
        <bean id="djNewsProvider" calss="...FXNewsProvider">
            <constructor-arg index="0">
                <ref bean="djNewsListener"/>
            </constructor-arg>
            <constructor-arg index="1">
                <ref bean="djNewsPersistent"/>
            </constructor-arg>
        </bean>
        ...
    </beans>

&emsp;&emsp;在使用FXNewsProvider的时候，之前我们需要自己new出来，但是现在只需要将生产图纸(XML文件)交给BeanFactory，让BeanFactory生产一个即可
    
    DefaultListableBeanFactory factory = new DefaultListableBeanFactory();//构造工厂
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);//新增Xml阅读器
    reader.loadBeanDefinitions(new ClassPathResource("配置文件路径"));//规则注册入容器
    FXNewsProvider newsProvider = (FXNewsProvider)factory.getBean("djNewsProvider");
    
    或者
    
    ApplicationContext container = new ClassPathXmlApplicationContext("配置文件路径");
    FXNewsProvider newsProvider = (FXNewsProvider)container.getBean("djNewsProvider");

### BeanFactory的对象注册和依赖绑定方式
&emsp;&emsp;BeanFactory作为一个IoC Service Provider，为了明确管理各个业务对象以及业务对象间的依赖绑定关系，同样需要某种途径需要记录和管理
这些信息。而BeanFactory支持常用的三种方式。
- **直接编码方式**：其实，把编码方式单独作为一种方式并不十分恰当，因为无论何种方式，最终都需要编码才能落实。不过通过这些编码可以让我们更加清楚BeanFactory
在底层是如何运作的。
```    
public static void main(String[] args) {
    DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();//构造工厂
    BeanFactory container = bindViaCode(beanRegistry);
    FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("djNewsProvider");
    newsProvider.getAndPersistNews();
}
public static BeanFactory bindViaCode(BeanDefinitionRegistry  registry) {
    AbstractBeanDefinition newsProvider = new RootBeanDefinition(FXNewsProvider.class);
    AbstractBeanDefinition newsListener = new RootBeanDefinition(DowJonesNewsListener.class);
    AbstractBeanDefinition newsPersistent = new RootBeanDefinition(DowJonesNewsPersistent.class);

    // 将Bean定义注册到容器中
    registry.registerBeanDefinition("djNewsProvider", newsProvider);
    registry.registerBeanDefinition("djNewsListener", newsListener);
    registry.registerBeanDefinition("djNewsPersistent", newsPersistent);

    // 指定依赖关系
    // 1.通过构造方法注入方式
    ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
    argumentValues.addIndexedArgumentValue(0, newsListener);
    argumentValues.addIndexedArgumentValue(1, newsPersistent);
    newsProvider.setConstructorArgumentValues(argumentValues);
    // 2.或者通过setter方法注入方式
    MutablePropertyValues propertyValues = new MutablePropertyValues();
    propertyValues.addPropertyValue(new PropertyValue("newsListener", newsListener));
    propertyValues.addPropertyValue(new PropertyValue("newsPersistent", newsPersistent));
    newsProvider.setPropertyValues(propertyValues);

    // 绑定完成
    return (BeanFactory) registry;
}
```
&emsp;&emsp;BeanFactory只是一个接口，我们最终需要该接口的实现类进行实际的Bean管理，DefaultListableBeanFactory就是一个比较通用的BeanFactory
实现类。它是间接地实现了BeanFactory接口，还实现了BeanDefinitionRegistry接口，该接口才是在BeanFactory的实现中担当Bean的注册和管理的角色。
基本上BeanFactory只定义了如何访问容器内部Bean的方法，而它的具体实现类负责具体的Bean注册及管理的工作。BeanDefinitionRegistry接口抽象了Bean
的注册逻辑。通常，只有具体的BeanFactory实现类才实现这个接口来管理Bean。<br>
![](image/beanfactory03.png)

&emsp;&emsp;注意：最后一行的强制转换是在特定场景的，因为DefaultListableBeanFactory同时实现了BeanFactory和BeanDefinitionRegistry接口
所以，强制转换是不会出现问题。单纯的BeanDefinitionRegistry是无法强制转换到BeanFactory类型的。 

- **外部配置文件方式**：Spring的IoC容器支持两种配置文件格式properties文件格式、XML文件格式，当然也可以引入自己的格式。采用外部文件时，Spring
的IoC容器有一个统一的处理方式。通常情况下，需要根据不同的外部文件格式，给出相应的BeanDefinitionReader实现类，由BeanDefinitionReader的实现
类负责将相应配置文件内容读取并映射到BeanDefinition(创建对象)，然后将映射后的BeanDefinition注册到一个BeanDefinitionRegistry，之后，
BeanDefinitionRegistry将完成Bean的注册、绑定、加载。当然，大部分工作，包括解析文件、装配BeanDefinition之类的工作，都是由BeanDefinitionReader
的实现类来完成的，BeanDefinitionRegistry只不过负责保管而已。

    
    BeanDefinitionRegistry beanRegistry = new DefaultListableBeanFactory(); // 某个BeanDefinitionRegistry的实现类
    BeanDefinitionReader beanDefinitionReader = new BeanDefinitionReaderImpl(beanRegistry);
    beanDefinitionReader.loadBeanDefinitions("配置文件路径");

&emsp;&emsp;通过Properties文件的方式

    /**
     * 2.配置文件方式
     */
    public static BeanFactory bindViaPropertiesFile(BeanDefinitionRegistry registry) {
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(registry);
        reader.loadBeanDefinitions("binding-config.properties");
        return (BeanFactory) registry;
    }
    
&emsp;&emsp;配置文件的方式很简单，所有（对象之间的注册和依赖注入信息）都配置到了文件上，不需要通过代码来完成对象的注册和依赖绑定。这些工作都交给了
PropertiesBeanDefinitionReader来做。<br>

&emsp;&emsp;通过XML文件方式

    <beans>
        <bean id="djNewsProvider" class="com.redlips.spring.features.base.FXNewsProvider">
            <constructor-arg index="0">
                <ref bean="djNewsListener"/>
            </constructor-arg>
            <constructor-arg index="1">
                <ref bean="djNewsPersistent"/>
            </constructor-arg>
        </bean>
        <bean id="djNewsListener" class="com.redlips.spring.features.ioc.iocserviceprovider.DowJonesNewsListener"/>
        <bean id="djNewsPersistent" class="com.redlips.spring.features.ioc.iocserviceprovider.DowJonesNewsPersistent"/>
    </beans>
    
    /**
     * 3.通过配置文件XML
     */
    public static BeanFactory bindViaXMLFile(BeanDefinitionRegistry registry) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        reader.loadBeanDefinitions("new-config.xml");
        return (BeanFactory) registry;
    }

- **注解方式**：在Spring2.5之前，Spring框架并没有正式支持基于注解方式的依赖注入，另外，注解是Java5之后才引入的
```
@Component
public class FXNewsProvider {
    // 监听新闻
    @Autowired
    private IFXNewsListener newsListener;
    // 同步新闻到库的接口
    @Autowired
    private IFXNewsPersistent newsPersistent;
}
```

&emsp;&emsp;@Autowired是这里的主角，它将告诉Spring容器需要为当前对象哪些依赖对象。而@Componet是配合Spring2.5中的classpath-scanning功能
使用的。现在只需要向Spring的配置文件中增加一个"触发器"，使用注解标注的类就能获得依赖对象的注入了。
    
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="
           http://www.springframework.org/schema/beans 
           http://www.springframework.org/schema/beans/spring-beans.xsd 
           http://www.springframework.org/schema/context 
           http://www.springframework.org/schema/context/spring-context.xsd">
        <context:component-scan base-package="com.redlips.spring.features.ioc01"/>
    </beans>

&emsp;&emsp;\<context:component-scan/\>会到指定的包下面扫描带有@Component的类，如果找到，就将他们添加到容器进行管理，并根据他们所标注的
@Autowired为这些类注入符合条件的依赖对象。

### BeanFactory的XML方式
&emsp;&emsp;XML格式的容器信息管理方式是Spring提供最为强大、支持最为全面的方式。从Spring的参考文档到各种书籍，都是按照XML方式进行说明的，虽然
现在流行使用注解的方式。<br>

- **\<beans\>和\<bean\>**：所有使用XML文件进行配置信息加载的Spring IoC容器，包括BeanFactory和ApplicationContext的所有XML相应实现，都
使用统一的XML格式。<br>

&emsp;&emsp;在Spring2.0版本之前,XML格式由Spring提供的DTD规定，也就是所有Spring容器加载的XML配置文件头部，都需要有DOCTYPE声明
    
    <?xml version="1.0" encoding="UTF-8">
    <!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN"
    "http://www.springframework.org/dtd/spring-beans.dtd"
    <beans>
    ...
    </beans>

&emsp;&emsp;从Spring2.0版本之后，Spring在继续向前兼容的情况下，即可以继续使用DTO方式的DOCTYPE进行配置文件格式的限定，又引入了基于XML schema
的文档声明。
    
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="
           http://www.springframework.org/schema/beans 
           http://www.springframework.org/schema/beans/spring-beans.xsd 
           http://www.springframework.org/schema/context 
           http://www.springframework.org/schema/context/spring-context.xsd">
    </beans>

&emsp;&emsp;不管使用哪一种形式的文档声明，限定的元素都是相同的，beans是XML配置的中最顶层的元素，它下面包换0或者1个description标签、以及0或多个
bean/import/alias标签<br>
&emsp;&emsp;\<beans\>作为所有bean标签的统帅，它拥有的属性对所辖属的bean进行统一的默认行为设置。<br>
1. default-lazy-init。值为true或者false，默认false。是否对bean进行延迟初始化。
2. default-autowire。值为no、byName、byType、constructor，默认为no，依赖的绑定方式。
3. default-init-method。值为所管辖的方法名，如果所管辖的\<bean\>按照某种规则，都有相同名称的初始化方法，可以统一指定这个这个初始化方法名，而不用
在每个bean上重复单独指定。
4. default-destroy-method。与default-init-method相对应，如果所管辖bean有按照某种规则使用了相同名称的对象销毁方法，可以通过这个属性统一指定
<br>

&emsp;&emsp;\<description\>、\<import\>及\<alias\>并非必须的标签
1. description。只是指定一些描述性信息，通常可以省略。
2. import。可以根据模块功能后者层次关系，将配置信息分类放置到不同的配置文件中。在加载主要配置文件时，并将主配置文件所依赖的配置文件同时加载，可以
在这个主要配置文件中通过\<import\>元素对其所依赖的配置文件进行引用。但是，这个功能在我看来意义不大，因为容器本身可以加载多个配置文件，没必要通过
一个配置文件来加载所有配置，不过，或许在某种场景下比较方便也说不定。
3. alias。可以使用该标签为某些bean起一些别名，通常只是为了输入方便。

- **单个bean**：
1. id属性。id为bean指定一个名称，是bean的唯一标识，在不需要根据bean的名称来指定依赖关系的时候，id可以省略。除了id可以指定bean的名字外，name
属性可以指定bean的别名(alias)，而且比较灵活，里面可以包含逗号、空格、冒号分割指定多个名字。
2. class属性。每个注册到容器的对象都需要通过class属性指定其类型，在大部分情况下该属性是必须的。仅在少数情况下不用指定，如在使用抽象配置模板的情况下。
```
<bean id="djNewsProvider" class="com.redlips.spring.features.base.FXNewsProvider" name="/news/djNews,provider">
</bean>
```

- **XML的注入之道**：在Spring的IoC容器的XML配置中，如何表达对象与对象之间的依赖性。

  - 构造方法注入，为当前对象注入所依赖的对象需要使用\<constructor-arg\>标签。ref属性用来指定所引用的bean。当无法和构造方法参数列表一一对应时
  就需要其它属性来辅助。type属性指定参数类型，index属性指定参数位置(从0开始)
  ```
  <bean id="djNewsProvider" class="com.redlips.spring.features.base.FXNewsProvider" name="/news/djNews,provider">
      <constructor-arg index="0" ref="djNewsListener"/>
      <constructor-arg index="1" ref="djNewsPersistent"/>
  </bean>
  ```
  - setter注入，提供的是\<property\>元素，name属性指定被注入对象的变量名，之后通过value或者ref属性或者内嵌其它元素来指定具体依赖对象引用或值。
  如果只是使用property元素进行依赖注入的话，需要提供空的构造方法。
  ```
  <bean id="djNewsProvider" class="com.redlips.spring.features.base.FXNewsProvider" name="/news/djNews,provider">
      <property name="newsListener" ref="djNewsListener"/>
      <property name="newsPersistent" ref="djNewsPersistent"/>
  </bean>
  或者混用
  <bean id="djNewsProvider" class="com.redlips.spring.features.base.FXNewsProvider" name="/news/djNews,provider">
      <constructor-arg index="0" ref="djNewsListener"/>
      <property name="newsPersistent" ref="djNewsPersistent"/>
  </bean>
  ```
  - \<property\>和\<constructor-arg\>中可用的配置项，前面提到这两个元素内部嵌套\<value\>或者\<ref\>，用来指定当前注入的是引用类型还是基础
  数据类型。不过，Spring为了提供多种注入类型，它提供了更多其他的元素。
    - \<value\>。最底层元素(不能再嵌套其它元素)，可以为主体注入简单数据类型，如String类型、基础数据类型及其包装器类型(容器注入时会做装换工作)
    ```
    <constructor-arg>
        <value>111</value>
    </constructor-arg>
    <property name="attrubuteName">
        <value>222</value>
    </property>
    或者以上一层属性身份出现
    <constructor-arg value="111"/>
    <property name="attributeName" value="222"/>
    ```
    - \<ref\>。用来引用容器中其它对象实例，可以通过local(只能引用同一配置文件的bean，但Spring4.0版本已经不支持该属性)、parent(只能引用当前
    容器的父容器)、bean(基本通用)属性来指定引用对象的beanName。
    ```
    <constructor-arg>
        <ref parent="djNewsPersistent"/>
    </constructor-arg>
    或者
    <constructor-arg>
        <ref bean="djNewsPersistent"/>
    </constructor-arg>
    ```
    - \<idref\>。如果要为当前对象注入被依赖对象的名称，而非引用，通常情况下可以使用value来指定被依赖对象的名称(字符串)。但是idref会是更合适的
    做法。
    ```
    <property name="newsListenerBeanName">
        <value>djNewsListener</value>
    </property>
    <!-- 更合适的做法,因为使用idref,容器在解析配置时会检查这个beanName是否存在,而不是运行时才发现这个beanName不存在 -->
    <property name="newsListenerBeanName">
        <idref bean="djNewsListener"/>
    </property>
    ```
    - 内部\<bean\>。使用ref可以引用容器中独立定义的对象。但有时，我们定义的对象只有一个对象引用，或者某个对象定义我们不想其它对象通过ref引用。
    这时可以使用内嵌bean，将这个私有的对象bean定义在局部当前对象。
    ```
    <bean id="djNewsProvider" class="com.redlips.spring.features.base.FXNewsProvider" name="/news/djNews,provider">
        <constructor-arg index="1" ref="djNewsPersistent"/>
        <constructor-arg index="0">
            <bean class="com.redlips.spring.features.ioc.iocserviceprovider.DowJonesNewsListener">
            </bean>
        </constructor-arg>
    </bean>
    ```
    由于内部bean只有当前对象引用，所以内部bean的id不是必须的。但是内部bean和独立bean用法完全相同。
    
    - \<list\>。list对应注入的对象类型是java.util.List及其子类型或者数组类型。通过list可以有序地为当前对象注入以collection形式声明的依赖。
    ```
    public class MockDemoObject {
        private List param1;
        private String[] param2;
        // 对应getter/setter
        ....
    }
    
    <bean id="mockDemo" class="com.redlips.spring.features.ioc.beanfactory.beanfactory0431.MockDemoObject">
        <property name="param1">
            <list>
                <value>something</value>
                <ref bean="djNewsListener"></ref>
                <bean class="com.redlips.spring.features.ioc.iocserviceprovider.DowJonesNewsListener"></bean>
            </list>
        </property>
        <property name="param2">
            <list>
                <value>stringValue1</value>
                <value>stringValue2</value>
            </list>
        </property>
    </bean>
    ```
    优雅的配置是不会这么做的，此处只是为了演示list的功效。
    
    - \<set\>。如果说list是有序的注入一系列依赖，那么set就是无序的，而且对于set来说，元素的顺序本来就无关紧要。set对应Java Collection中类型
    为java.util.Set或其子类的依赖对象。
    ```
    public class MockDemoObject {
        private Set valueSet;
        // getter/setter方法
        ...
    }
    
    <property name="valueSet">
        <set>
            <value>someting</value>
            <ref bean="djNewsListener"/>
            <bean class="com.redlips.spring.features.ioc.iocserviceprovider.DowJonesNewsListener"/>
            <list>
                ...
            </list>
        </set>
    </property>
    // 例子就是例子，只是为了演示这个元素有多大能耐，通常是不会这样配置，除非你希望ClassCastException的到来。
    ```
    - \<map\>。和上面两种集合类型相同，都是为对象注入Collection类型的依赖，不同点是它对应注入java.util.Map或其子类类型的依赖对象。
    ```
    public class MockDemoObject {
        private Map mapping;
        // ...
    }
    
    <property name="mapping">
        <map>
            <entry key="strValueKey">
                <value>strValue</value>
            </entry>
            <entry>
                <!-- 此时key是一个对象引用 -->
                <key>
                    <ref bean="provider"/>
                </key>
                <ref bean="djNewsListener"></ref>
            </entry>
            <!-- 此时key是一个对象引用 -->
            <entry key-ref="djNewsPersistent">
                <list>
                    <value>value1</value>
                    <value>value2</value>
                </list>
            </entry>
        </map>
    </property>
    // 离谱的花样，但是它的功效确实如此
    ```
    &emsp;&emsp;对map来说，它可以内嵌任意多个entry元素，每一个\<entry\>都要为其指定一个键一个值。entry的键：可以使用\<entry\>的属性key
    或者key-ref来指定键，也可以使用内嵌元素\<key\>来指定键；内嵌元素又可以任意搭配。entry的值：除了\<key\>用来指定键，其它元素可以任意使用来
    指定entry对应的值，如果值只是简单的基础类型或者单一的对象引用，可以直接使用\<entry\>的value或者value-ref属性来指定。
    
    - \<props\>。简化后的\<map\>，或者说是特殊化的map，该元素配置的类型是java.util.Properties的依赖。因为Properties只能指定String类型
    的键和值，所以props简化了很多，只有固定的格式。
    ```
    public class MockDemoObject {
        private Properties emailAddrs;
        ...
    }
    
    <property name="emailAddrs">
        <props>
            <prop key="author">xxx@.163.com</prop>
            <prop key="support">suport@qq.com</prop>
        </props>
    </property>
    ```
    
    &emsp;&emsp;每个\<props\>可以嵌套多个\<prop\>，每个prop通过key属性指定键，在prop内部可以指定具体的值。prop内部没有任何元素可以使用，
    只能指定字符串，这是Properties的语义决定的
    - \<null\/>。最后提到的一个元素是\<null\/>，空元素，使用的场景并不多。对于String类型，通过value以\<value\>\</value>的方式注入，得到
    的结果是""，而不是null，如果要为string或者其他类型指定null的话可使用null
    ```
    <property name="param1">
        <null/>
    </property>
    ```
    
  - depends-on，通常情况下，可以直接通过之前提到的所有元素，来显示的指定bean之间的依赖关系。这样容器在初始化当前bean定义的时候，会根据元素标记
  的依赖关系，首先实例化当前bean定义所依赖的其它bean定义。但是在某些时候，我们没有通过类似\<ref\>来明确指定对象A依赖对象B的话，如何让容器在实例化
  对象A之前首先实例化对象B呢？
  ```
    public static SystemConfigurationSetup {
        static {
            DOMConfigurator.configure("配置文件路径");
            // 其它初始化代码
        }
    }
  ```
  &emsp;&emsp;系统所有需要使用日志记录的类，都需要在这些类使用之前初始换log4j。就会非显式的依赖于SystemConfigurationSetup的静态初始化块。
  如果ClassA需要使用log4j，就必须在bean定义中使用depends-on来要求容器在初始化自身之前首先实例化SystemConfigurationSetup以保证日志正常使用
  ```
  <!-- depends-on可以指定多个beanName，用逗号隔开 -->
  <bean id="classA" class="...ClassA" depends-on="configSetup"/>
  <bean id="configSetup" class="SystemConfigurationSetup"/>
  ```
  - autowire，除了通过配置来明确bean之间的依赖关系，Spring还提供了根据bean定义的某些特点将相互依赖的某些bean直接自动完成绑定，从而减少工作量
  。此时可以通过bean元素的autowire属性，可以指定当前bean采用某种类型的自动绑定模式。
    - no。容器默认的自动绑定方式，也就是不采用任何形式的自动绑定，完全手工配置各个bean之间的依赖关系。
    - byName。按照类中声明的实际变量的名称，与XML配置文件中声明bean定义的beanName的值进行匹配。此处有个限制，就是变量名必须与bean定义的名称
    相同。
    ```
    public class Foo {
        private Bar emphasisAttribute;
        ...
        // setter方法
    }
    public calss Bar {
    }
    
    <bean id="fooBean" class="...Foo" autowire="byName"/>
    <bean id="emphasisAttribute" class="...Bar"/>
    ```
    - byType。如果当前bean定义的autowire模式为byType，容器会根据当前bean所依赖的对象类型，在容器中找与之对应类型的bean，但是如果容器中有多个
    相同类型的bean时，它会告诉你"该选择哪个"的问题，这时就需要手动更改配置了。
    ```
    <bean id="fooBean" class="...Foo" autowire="byType"/>
    <bean id="emphasisAttribute" class="...Bar"/>
    ```
    - constructor。byName和byType类型的自动绑定模式是针对property的自动绑定，而constructor是针对构造方法参数类型的自动绑定，它同样是byType
    类型的绑定模式。用法也上面类似。
    ```
    public class Foo {
        private Bar bar;
        public Foo(Bar bar) {
            this.bar = bar;
        }
    }
    <!-- 相应配置 -->
    <bean id="foo" class="...Foo" autowire="constructor"/>
    <bean id="bar" class="...Bar"/>
    ```
  &emsp;&emsp;注意：手动绑定总会覆盖自动绑定；自动绑定对原生类型、String类型、Class类型、及其这些类型的数组是无效的；beans元素的default-autowire
  属性可以为其下所有的bean设置自动绑定模式。
  - lazy-init。延迟初始化这个特性的作用，主要是可以针对ApplicationContext容器的bean初始化行为施以更多控制。该属性是作用在bean元素上，beans
  元素使用的是default-lazy-init。
  
- **继承配置**：继承配置用的是bean元素的parent属性来指定其父bean。
- **Bean的Scope**：BeanFactory除了拥有作为IoC Provider的职责，作为一个轻量级容器，它还有其它职责，其中包括对象的生命周期管理。Bean的Scope
类型最初为：singleton和prototype，但发布2.0之后又引入了另外三种类型：request、session、global session类型，不过这三种类型有所限制，只能
在web应用中使用。也就是说，只有在支持web应用的ApplicationContext中使用这三种才合理。
  - singleton。单例，所有对该对象的引用将共享这个实例，它与IoC容器几乎拥有相同的生命周期。Bean元素的scope属性默认就是该类型。
  - prototype。多例，对于每一个需要该类型的对象时，容器都会为每次的对象请求创建一个新的实例，返回给请求方后，容器不再拥有当前返回对象的引用。该对象
  的生命周期由请求方负责，一般情况下，bean的scope属性为prototype类型时，都是一些有状态的，比如保存每个顾客信息的对象。
  - request。
  ```
  // 通常配置方式
  <bean id="requestProcessor" class="...RequestProcessor" scope="request"/>
  ```
  &emsp;&emsp;Spring容器，即XmlWebApplicationContext会为每一个HTTP请求创建一个全新的RequestProcessor对象供当前请求使用，直至请求结束。
  当有10个请求过来，容器会分别返回10个全新的RequestProcessor对象，互不干扰。从不严格的意义上讲request是prototype的特例，除了场景不同。
  - session。对于web应用来说，session中最普遍的信息就是用户的登录信息，对于这种放到session中的信息，可以指定scope为session。
  ```
  <bean id="userPreferences" class="...UserPreferences" scope="session"/>
  ```
  &emsp;&emsp;Spring会为每个session创建一个全新的实例，和request相比，除了生命周期可能会长点，其它方面没什么不同。
  
  - global session。global session只有应用在基于portlet的web应用程序中才有意义，它映射到portlet的global范围的session。如果在基于Servlet
  的web应用程序来使用这个类型的scope，容器会将其作为普通的session类型的scope对待。
- **自定义scope类型**：Spring2.0之后，容器提供了对scope的扩展，可以根据自己的需要来自定义scope，需要说明的是，默认的singleton和prototype是
硬编码到代码中的，而request、session、global session和自定义的scope则属于可扩展的scope。他们都实现了org.springframework.beans.factory.config.Scope
接口。
- **工厂方法与FactoryBean(factory-bean、factory-method属性应用)**：在强调“面向接口”编程的同时，有一点需要注意：虽然对象可以声明接口来避免对特定接口实现类的过度耦合，但总归是需要一种
方式将声明依赖接口的对象与接口实现类关联起来。否则，该声明的接口便没有了任何意义。
```
public class Foo {
    private BirInterface barInterface;
    public Foo() {
        // 避免这种做法
        // barInterface = new BarInterafceImpl();
    }
}
```
&emsp;&emsp;如果该类使我们自己设计的，就变得简单了，我们可以通过依赖注入，让容器帮我们解除接口类和实现类之间的耦合性。但是如果需要依赖的是第三方
类库，需要实例化并使用第三方库中的相关类，这时，接口与实现类的耦合性需要其它方式来避免。<br>
&emsp;&emsp;通常的做法是通过使用工厂方法（Factory Method）模式，提供一个工厂类来实例化接口的实现类，这样主体对象只需要依赖工厂类，具体实现类
如果有变更的话，只需要变更工厂类即可。
```
public class Foo {
    private BirInterface barInterface;
    public Foo() {
        barInterface = BirInterfaceFactory.getInstance();
        // or
        barInterface = new BirInterfaceFactory().getInstance();
    }
}
```
&emsp;&emsp;针对使用工厂模式实例化对象的方式，Spring的IoC也提供了支持，只需要将工厂类所返回的具体接口的实现类注入到主体对象即可（这里主体对象是Foo）
  - - 静态工厂方法(Static Factory Method)。假设某第三方库发布了BarInterface,为了向使用该接口的客户端对象屏蔽以后可能对BarInterface实现类的
  变动，同时还提供了一个静态工厂方法实现类StaticBarInterfaceFactory。
  ```
  // 第一种模式：
  public class StaticBarInterfaceFactory {
    public static BarInterface getInstance() {
        return new BarInterfaceImpl();
    }
  }
  // 为了将该静态工厂方法类返回的实现类注入Foo,使用下面进行配置(setter方法注入)
  <bean id="foo" class="....Foo">
    <property name="barInterface">
        <ref bean="bar"/>
    </property>
  </bean>
  
  <bean id="bar" class="...StaticBarInterfaceFactory" factory-method="getInstance"/>
  
  // 第二种模式：
  // 也可以使用<constructor-arg>来指定工厂方法所需要的参数
  public class StaticBarInterfaceFactory {
      public static BarInterface getInstance(FooBar fooBar) {
          return new BarInterfaceImpl(fooBar);
      }
  }
  <bean id="bar" class="...StaticBarInterfaceFactory" factory-method="getInstance">
    <constructor-arg>
        <ref bean="fooBar"/>
    </constructor-arg>
  </bean>
  <bean id="fooBar" class="...FooBar"/>
  ```
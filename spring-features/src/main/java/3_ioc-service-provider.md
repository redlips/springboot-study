## IoC Service Provider

&emsp;&emsp;虽然业务对象可以通过IoC方式声明相应的依赖，但是最终仍需要通过某种角色或者服务将这些相互依赖的对象绑定到一起，而IoC Service Provider
就是对应IoC场景中的这一角色。IoC Service Provider是一个抽象出来的概念，它可以指任何将IoC场景中的业务对象绑定到一起的实现方式。它可以是一段代码
，也可以是一组相关的类，甚至可以是比较通用的IoC框架或者IoC容器实现。上文中关于绑定与新闻相关的对象也是这个场景中的IoC Service Provider，只不过
比较简单而已，而且也过于简单。往往我们需要将系统中成百上千的业务对象绑定到一起，采用这种方式明显不切实际，通用性暂且不提，单单这些绑定的代码也是一种
糟糕的体验。不过现在有很多开源产品通过各种方式为我们做了这部分工作。所以，目前我们只需要使用这些产品提供的服务都可以了。Spring的IoC容器就是一个提供
依赖注入服务的IoC Service Provider。
    
    DowJonesNewsListener newsListener = new DowJonesNewsListener();
    DowJonesNewsPersistent newsPersistent = new DowJonesNewsPersistent();
    FXNewsProvider newsProvider = new FXNewsProvider(newsListener, newsPersistent);
    newsProvider.getAndPersistNews();

### IoC Service Provider的职责

- 业务对象的构建管理：在IoC场景里，业务对象无需关心所依赖的对象是如何构建如何取得，但是这部分工作始终需要有人来做。所以，IoC Service Provider
需要将对象的构建逻辑从客户端对象(这里指使用某个对象或者某种服务的对象，如果对象A需要引用对象B，那么A就是B的客户端对象，而不管A处于业务层还是数据
访问层)剥离出来，以免这部分逻辑污染业务对象的实现。
- 业务对象间的依赖绑定：IoC Service Provider通过结合之前构建和管理所有业务对象，以及各个业务对象间可以识别的依赖关系，将这些对象所依赖的对象注
入绑定，从而保证每个业务对象在使用的时候，可以处于就绪状态。

### IoC Service Provider如何管理对象间的依赖关系

&emsp;&emsp;前面提到，被注入对象可以通过多种方式通知IoC Service Provider为其注入相应的依赖，可问题的关键是，收到通知的IoC Service Provider
是否能够完全领会其意图并及时有效地为其提供想要的依赖。其实IoC Service Provider并没有那么智能，它必须要通过某种方式来记录诸多对象间的对应关系。比如，
它可以通过文本来记录被注入与被依赖对象之间的关系、可以通过XML来记录、也可通过编码来注册对应信息等等。但是，在实际情况下，是通过哪种方式来记录“服务信息”
的呢。
<br>

- 直接编码方式：当前大部分IoC容器都应该支持直接编码方式，比如PicoContainer、Spring等。在容器启动之前，我们就可以通过程序编码的方式将被注入对象
和依赖对象注册到容器中，并明确他们相互之间的依赖关系。
<br>
伪代码演示：通过为相应的类指定具体实例，可以告知IoC容器，当需要这种类型的对象实例时，将容器中已注册的具体对应实例返回。

         
    IoContainer container = ...;
    container.register(FXNewsProvider.class, new FXNewsProvider());
    container.register(IFXNewsListener.class, new DowJonesNewsListener());
    ...
    FXNewsProvider newsProvider = (FXNewsProvider)container.get(FXNewsProvider.class);
    newsProvider.getAndPersistNews();
        
&emsp;&emsp;如果是接口注入，伪代码要多一些，但是道理上是一样的，只不过除了除了注册相应对象，还要将“注入标志接口”与相应的依赖对象绑定一下，才能让
容器知道最终是什么样的对应关系。
         
    IoContainer container = ...;
    container.register(FXNewsProvider.class, new FXNewsProvider());
    container.register(IFXNewsListener.class, new DowJonesNewsListener());
    ...
    container.bind(IFXNewsListenerCallable.class, container.get(IFXNewsListener.class));
    ...
    FXNewsProvider newsProvider = (FXNewsProvider)container.get(FXNewsProvider.class);
    newsProvider.getAndPersistNews();
    
&emsp;&emsp;通过bind方法，将“被注入对象”(由IFXNewsListenerCallable接口添加标志)所依赖的对象，绑定为容器中注册过的IFXNewsListener类型的
对象实例。容器在返回FXNewsProvider对象实例之前，会根据这个绑定信息，将IFXNewsListener注册到容器中的对象实例注入到“被注入对象”FXNewsProvider
中，并最终返回已经组装完成的FXNewsProvider对象。

- 配置文件方式：这是一种较为普遍的依赖注入关系管理方式。像普通文本文件、properties文件、XML文件等，都可以成为管理依赖注入关系的载体。不过，最为常见
的，还是通过XML文件来管理对象注册和对象间的依赖关系，比如Spring IoC容器和在PicoContainer基础上扩展的NanoContainer，都是采用XML文件方式管理
和保存依赖注入信息的。对于例子中的FXNewsProvider来说，可以通过Spring配置文件的方式来配置和管理各个对象间的依赖关系。
    
    
    <bean id="newsProvider" class="...FXNewsProvider">
        <property name="newsListener">
            <ref bean="djNewsListener"/>
        </property>
        <property name="newsPersistent">
            <ref bean="djNewsPersistent"/>
        </property>
    </bean>
    
    <bean id="djNewsListener" class="..impl.DowJonesNewsListener>
    </bean>
    <bean id="djNewsPersistent" class="..impl.DowJonesNewsPersistent>
    </bean>

&emsp;&emsp;最后，通过"newsProvider"这个名字，从容器中取得已经组装好的FXNewsProvider并直接使用。
    
    ...
    container.readConfigurationFiles(...);
    FXNewsProvider newsProvider = (FXNewsProvider)container.getBean("newsProvider");
    newsProvider.getAndPersistNews();
    
- 元数据方式：这种方式的代表实现是Google Guice，这是Bob Lee在Java5的注解和Generic的基础上开发的一套IoC框架。我们可以直接在类中使用元数据信息
来标注各个对象间的依赖关系，然后由Guice框架根据这些注解所提供的信息将这些对象组装后，交给客户端对象使用。
<br>
代码清单：使用Guice注解标注后的FXNewsProvider的定义

    
    public class FXNewsProvider {
        // 监听新闻
        private IFXNewsListener newsListener;
        // 同步新闻到库的接口
        private IFXNewsPersistent newsPersistent;
        @Inject
        public FXNewsProvider(IFXNewsListener newsListener, IFXNewsPersistent newsPersistent) {
                this.newsListener = newsListener;
                this.newsPersistent = newsPersistent;
        }
    }

&emsp;&emsp;通过@Inject，指明IoC Service Provider通过构造方法注入方式，为FXNewsProvider注入其所依赖的对象。至于余下的依赖相关信息，在Guice
中是由相应的Module来提供，FXNewsProvider所使用的Module实现
    
    public class NewsBindingModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(IFXNewsListener.class).to(DowJonesNewsListener.class).in(Scopes.SINGLETON);
            bind(IFXNewsPersistent.class).to(DowJonesNewsPersistent.class).in(Scopes.SINGLETON);
        }
    }

&emsp;&emsp;通过Module指定进一步的依赖注入信息后，我们就可以直接从Guice那里取得最终已经注入完毕并且直接可以使用的对象
    
    Injector injector = Guice.createInjector(new NewsBindingModule());
    FXNewsProvider newsProvider = injector.getInstance(FXNewsProvider.class);
    newsProvider.getAndPersistNews();

&emsp;&emsp;当然，注解最终也要通过代码处理来确定最终的注入关系，从这点来讲，注解方式可以算作编码方式的一种特殊情况。

### 小结
&emsp;&emsp;IoC场景中的IoC Service Provider只是为了简化概念提出的一个一般性的概念，它对应的有特定的产品，好比：Spring提供的IoC容器就是一个
特定的IoC Service Provider实现产品。
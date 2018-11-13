## IOC

&emsp;&emsp;Spring的IoC容器是整个Spring框架的核心和基础。Spring为什么要提供这样一个容器呢？即使是提供了这样一个容器，为什么要以IoC冠名呢？
<br>

### 理念：让别人为你服务

&emsp;&emsp;IoC是近年来轻量级容器（Lightweight Container）的兴起而逐渐被许多人提起的名词，它的全称是Inversion of Control（控制反转），
它还有个别名是依赖注入（Dependency Injection），”反转“是用来形容IoC容器最多的一句话。那么为什么需要IoC？它的意义是什么？有什么独到之处？此处
将IoC和依赖注入等同看待，但在有的书籍中是将依赖注入看做是IoC的一种方式。
<br>

&emsp;&emsp;下面以ioc的项目案例来详细讲解 eg:com.redlips.spring.features.ioc
    
    /**
     * 假设默认使用（道琼斯新闻社）的新闻，我们需要提供上面两个接口的实现类，通常需要在构造函数中构造FXNewsProvider依赖的这两个类，这种被依赖
     * 的类、或对象，简称为“依赖类”、“依赖对象”。
     * 下面是构造道琼斯的两个依赖类
     */
    public FXNewsProvider() {
        newsListener = new DowJonesNewsListener();
        newsPersistent = new DowJonesNewsPersistent();
    }

&emsp;&emsp;这就是我们通常做事的方式，如果依赖于某个类或服务，最简单而有效的方式就是直接在类的构造函数中构造依赖的类。就像我们要装修房子，需要用
家具，这时可以用我们通常的做法：直接打造出家具。不过，我们也可以直接去家具城买回来，然后再进行布置。
<br>
&emsp;&emsp;不管是直接打造家具（通过new构造对象），还是去家具城买家具（或许是通过Service-Locator解决直接的耦合关系），有一个共同点就是都是自
己主动去获取依赖的对象。Service-Locator是J2EE核心模式的一种，主要通过引入中间代理者消除对象间复杂的耦合关系，并统一管理分散的复杂耦合关系。
<br>
&emsp;&emsp;回头想想，每次用到依赖对象就主动去获取，真的有这个必要吗？我们最终所做的就是直接用到依赖对象所提供的服务而已。只要用到这个对象的时候
，它能够准备就绪，我们完全可以不管这个对象是自己找来的还是别人送来的。对于FXNewsProvider来说，就是在getAndPersistNews()方法调用newsListener
的相应方法时，newsListener能够准备就绪就行。如果有人能够将我们需要的某个依赖对象送过来，就没必要大费周折自己去折腾了。
<br>
&emsp;&emsp;实际上，IoC就是为了帮助我们避免之前的大费周折，而提供的更加轻松简洁的方式。它的反转就是从原来的‘事必躬亲’到现在‘享受服务’，IoC的理念就是
让别人为你服务，也就是让IoC Service Provider来为你服务。
![](image/ioc01.png)

&emsp;&emsp;通常情况下，被注入对象依赖于被依赖对象，但是在IoC的场景里，二者之间通过IoC Service Provider来打交道，所有的被注入对象和被依赖对象
都由IoC Service Provider来管理，被注入对象需要什么，只需要告诉IoC Service Provider，后者就可以把被依赖对象注入到被注入对象中。
<br>
&emsp;&emsp;在IoC模式中，被注入对象通知IoC Service Provider为其提供服务的方式：构造方法注入、setter注入、以及接口注入。

- 构造方法注入：
就是被注入对象可以通过在其构造方法中声明依赖对象的参数列表，让外部知道它（通常是IoC容器）需要依赖哪些对象。对于FXNewsProvider来说的示例：   
    
        
    public FXNewsProvider(IFXNewsListener newsListener, IFXNewsPersistent newsPersistent) {
        this.newsListener = newsListener;
        this.newsPersistent = newsPersistent;
    }

&emsp;&emsp;IoC Service Provider会检查被注入对象的构造方法，取得它所需要的依赖对象列表，进而为其注入相应的对象。同一对象不能构造两次，因此，被注入对象的
构造乃至其整个生命周期，都应该由IoC Service Provider来管理。这种方式比较直观，对象被构造完成后，即进入就绪状态。

- setter方法注入：对于JavaBean对象，通过get或set方法进行获取和设置对象属性的值，FXNewsProvider中就可以为其属性添加该方法，这样外界就可以调用set方法
注入所依赖的对象。set方法随意性比较强，可以在被注入对象构造完成后再注入依赖对象。

- 接口注入：相比于前两种注入方式，接口注入比较繁琐。如果需要注入依赖对象，被注入对象就必须声明和实现另外的接口。

**三种注入方式比较**

- 接口注入：从注入方式上使用来说，接口注入是现在不甚提供的一种方式，基本处于退役状态。因为它强制被注入对象实现不必要的接口，带有侵入性。

- 构造方法注入：优点是在被注入对象完成之后，即已进入就绪状态，可以马上使用。缺点是，当依赖对象比较多的时候，构造方法的参数列表会比较长。而通过反射
构造对象的时候，对相同类型的参数处理会比较困难，维护使用上也比较麻烦。而且在Java中，构造方法无法被继承，无法设置默认值。对于非必须的依赖处理，可能
需要引入多个构造方法。

- setter方法注入：因为方法可以命名，所以setter方法注入在描述性上要比构造方法注入好一些。另外，setter方法可以被继承，允许设置默认值，而且有良好
的IDE支持。缺点就是，对象无法在构造完成后马上进入就绪状态。

&emsp;&emsp;综上所述，构造方法和setter方式注入，因为其侵入性较弱，是现在使用最多的注入方式；而接口注入因为其侵入性较强，已经不流行了。

### IoC的附加值

&emsp;&emsp;从主动获取依赖关系的方式转向IoC方式，不只是一个方向上的改变，简单的背后实际上隐藏着更多的玄机。要说IoC模式带来的好处，书籍上和网上
罗列的很多，比如：不会对业务对象造成很强的侵入性、使用IoC后，对象具有更好的可测性，可重用性和可扩展性等等。下面举一个例子来深入理解它带来的好处。
<br>
&emsp;&emsp;对于前面例子中的FXNewsProvider来说，在使用IoC重构之前，若果没有需求变动，看起来和用起来都没什么问题。但是，一旦多添加逻辑，问题可能
就来了。
<br>
&emsp;&emsp;如果又新增加一个需求，

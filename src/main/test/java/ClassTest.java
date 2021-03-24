import org.junit.Test;
import sun.awt.windows.awtLocalization;


// 默认类的修饰是default，只能在同包内可访问
// public类可以在不同类中访问
// 外部类上面就是包，对于外部类来说访问控制只存在于本包或者任意位置，所以只有默认和public两种
// protected比default要宽松，那么为什么不用protected？
// protected比default多了一个不同包的子类，
// 但是如果类A用protected修饰，与类A不同包的类B想要访问类A的话，类B就必须是继承类A的（或者说类B必须为类A的子类），但是类B继承类A的前提又是类B可以访问到类A，但是类B在没有成为类A的子类时是不能访问类A的
public class ClassTest {

    // private不能修饰外部类，只能修饰内部类,该内部类只能被包含他的类访问
    // 因为如果使用private修饰Java外部类，那么这个类不能创建实例，这个类的属性和方法不能被访问，那么创建这个类毫无意义，所以不能使用private修饰Java外部类。
    private class Fruit {
        String type = "fruit";
        public void printType() {
            System.out.println(type);
        }

        public void printColor() {

        }
    }

    // 默认的内部类可以被同包访问
    class Apple extends Fruit {
        String type = "apple";
        String color = "red";

        @Override
        public void printType() {
            System.out.println(type);
        }

        @Override
        public void printColor() {
            System.out.println(color);
        }
    }

    //public类型的内部类可以被不同包访问
    public class Pear extends Fruit {
        String type = "Pear";
        String color = "yellow";

        @Override
        public void printType() {
            System.out.println(type);
        }

        @Override
        public void printColor() {
            System.out.println(color);
        }
    }

    private class Plate<T> {
        private T item;

        public Plate(T item) {
            this.item = item;
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }

        public void printInfo() {
            System.out.println(item.hashCode());
        }
    }

    //泛型类可以被子类继承，此时子类可以是泛型类也可以不是泛型类
    private class PlusPlate<T> extends Plate<T> {
        //子类必须实现父类定义的有参构造器
        public PlusPlate(T item) {
            super(item);
        }
    }
    //如果不是泛型类，则在继承时需要指明父泛型类的类型
    private class ApplePlate extends Plate<Apple> {

        public ApplePlate(Apple item) {
            super(item);
        }
    }

    private class FruitType extends Plate<Fruit> {

        public FruitType(Fruit item) {
            super(item);
        }

        @Override
        public Fruit getItem() {
            Fruit fruit = super.getItem();
            fruit.printType();
            return fruit;
        }

        //这里会存在一个类型擦除带来的问题，我们知道Java中的方法调用采用的是动态绑定的方式，应该呈现出多态的特性：
        //子类重写超类中的方法，如果将子类向下转型成超类后，仍然可以调用重写后的方法
        //但是因为存在类型擦除，所以FruitColor的父类Plate<Fruit>中setItem函数由
        //public void setItem(Fruit item)  变成了  public void setItem(Object item)
        //我们的本意是想重写超类中的setItem方法，但是从方法签名上来看，两个是完全不同的方法，这样类型擦除就和多态产生了冲突。
        //为了解决这个问题java在子类中又生成了一个桥方法：
        //public void setItem(Object item) {setItem((Fruit)Item)}
        //真正重写父类setItem方法的是这个桥方法
        @Override
        public void setItem(Fruit item) {
            super.setItem(item);
            item.printType();
        }

        @Override
        public void printInfo() {
            super.getItem().printType();
        }
    }

    private class FruitColor extends Plate<Fruit> {

        public FruitColor(Fruit item) {
            super(item);
        }

        @Override
        public Fruit getItem() {
            Fruit fruit = super.getItem();
            fruit.printColor();
            return fruit;
        }

        @Override
        public void setItem(Fruit item) {
            super.setItem(item);
            item.printColor();
        }


        @Override
        public void printInfo() {
            super.getItem().printColor();
        }
    }


    @Test
    public void Test() {

        // 正常的某类引用指向某类对象
        Fruit fruit1 = new Fruit();
        Apple apple1 = new Apple();


        // 父类引用可以指向子类对象，相当于将子类向上转换为父类对象，因为子类对象中一定包含父类对象的成员，所以可以直接转换，
        // java是单继承的，因此对于每一个有基类的类而言，向上转换都是唯一的，所以直接向上转换是安全的
        // 向上转换会丢失子类特有的方法和字段，但是子类重写的父类方法依然是子类有效，因为java是动态绑定的，是在执行期间判断引用对象的实际类型，根据实际的类型调用相应的方法
        Fruit fruit2 = new Apple();
        fruit1.printType(); // 输出fruit
        fruit2.printType(); // 多态机制，动态绑定在运行时调用引用所指实际对象类型的方法，因此输出apple
        System.out.println(fruit2.type); //特有字段丢失，因此还是输出fruit

        // 因为一个基类可以被多个类进行继承，父类对象向下转换时很难确定应该转变为怎样的子类，每个子类中都可能含有独有的子段和方法
        // 向下转换分为两种情况，如果父类引用的对象是子类对象，则恢复成子类引用指向子类对象是完全可以的，后续对该引用对象的使用与正常无异
        // 如果父类引用的对象就是父类对象本身，则不能向下转换，因为子类中可能会有其特有的方法与字段，因此不能直接向下转换，
        // 如果强行向下转换则运行的时候会抛出类型转换异常
        Apple apple2 = (Apple) fruit2;
        // Apple apple4 = new Fruit()（不合法）
        // Apple apple3 = (Apple) new Fruit() （运行时异常）
        apple1.printType(); //输出apple
        apple2.printType(); //输出apple
        System.out.println(apple2.type); //输出apple

        //可以发现如果有重名字段，不管是向上转换还是向下转换都是以转换后的为准，但是子类重写的父类方法会按照引用对象对象的世界类型，通过动态绑定在执行期间调用相应的方法
    }


    // 泛型的初衷就是为了保证类型安全，保证在运行时出现的错误能提早放到编译时检查，下面是对泛型的一些测试
    @Test
    public void GenericsTest() {
        // p1是一个放水果的盘子
        Plate<Fruit> p1 = new Plate<Fruit>(new Fruit());

        // 苹果和梨都是水果所以可以放进来,因此可以放入Fruit的所有子类
        p1.setItem(new Apple());
        p1.setItem(new Fruit());


        // 需要注意的是，虽然Apple是Fruit的子类，但是不能将Plate<Apple>转为Plate<Fruit>，因为Plate<Apple>并不是Plate<Fruit>的子类，不能强制转换类型
        // Plate<Fruit> p = new Plate<Apple>(new Apple());
        // 如果上述语法合法，则说明p3是一个放苹果的盘子，但是显然p3.setItem(new Pear())是合法的(Pear也是Fruit的子类)，这样苹果盘子里就可以放任何水果了，因此这种赋值是非法的
        // Plate原始类型是Plate<Apple>的父类型，可以直接向上转换，同理PlusPlate<Plus>也可以向上转换
        Plate p2 = new Plate<Apple>(new Apple());
        Plate p3 = new PlusPlate<Apple>(new Apple());
        // Plate<Fruit> p4 = new Plate<Apple>(new Apple());不合法
        // 需要注意与普通类不同的是，这里是允许向下强制转换的，因为在jvm中会进行类型擦除，这里就相当于把Plate<Object>转成Plate<Object>，其实是完全合法的
        // 不需要担心p5中会被放入不是苹果的item，因为p5本事是Plate<Apple>类型的，在写入时会进行类型检查
        Plate<Apple> p5 = (Plate<Apple>) new Plate(new Pear());
        // p5.setItem(new Pear());不合法

        // 通过上述研究，我们可以发现泛型内建本身时不变的，那么如果我们确实想使泛型具有协变和逆变的特性，那么我们可以通过通配符来实现
        // 上界通配符，p4中存放的都是Fruit及其子类，相当于协变
        // 使用完上界通配符后，使得Plate<? extends Fruit>这一类型表示： Plate具体类型为Fruit或其子类，但不能确定是哪一个子类
        // 例如这里p4中的元素可以是Fruit及其子类，这里创建的时候是Apple，但是编译器不知道p4的具体类型，所以无法进行写操作
        // 因此当使用上界通配符时，不能使用写操作
        // 与之相反，因为里面存储的都是Fruit及其子类，因此可以执行读操作，因为向上转换可以直接进行转换，因此读操作时需将读取到的全部转为Fruit类型
        Plate<? extends Fruit> p6 = new Plate<Apple>(new Apple());
        // p6.setItem(new Apple()); 写非法
        // 需要注意的是，这里读出来的是Fruit类型，不能进行向下转换，所以只能将读出来的内容存储在Fruit或者Fruit的基类中
        Fruit fruit = p6.getItem();
        Object object = p6.getItem();

        //下界通配符，p5中存放的都是Fruit及其基类
        //使用下界通配符时，不能进行读操作
        //因为不知道读取的内容的具体类型，只知道读取出来的是Fruit或者其基类，因为不能向下转换，所以无法找到一个合适的类型来存储读出来的内容
        //反之，可以将所有Fruit的子类，向上转换成Fruit类型进行存储
        Plate<? super Fruit> p7 = new Plate<Object>(new Apple());
        p5.setItem(new Apple());
        //这里进行读取只能使用所有类的基类object，但这么做会使得子类的特性完全丧失
        Object object2 = p7.getItem();
    }

    @Test
    public void GenericsArrayTest() {
        /*
        java中不允许泛型数组出现，eg:
        Plate<Apple>[] plates = new Plate<Apple>[10];

        因为在JVM会进行类型擦除，Plate<Apple>会变成其原始类型Plate<Object>，因此plates这个数组中每个元素的类型其实是Plate<Object>
        此时如果我们执行 plates[0] = new Plate<Peer>(); 这一命令是可以躲过数组的类型检查的
        因此出于这个原因，不允许使用泛型数组

        综上所述, 可以用一句话来描述 java 为什么不能创建泛型数组:
        使用泛型的作用是使得程序在编译期可以检查出与类型相关的错误，但是如果使用了泛型数组，这种能力就会受到破坏。
         */

        //如果确实想使用，可以先声明一个非泛型数组然后进行强制类型转换
        Plate<Apple>[] plates = (Plate<Apple>[]) new Plate[10];


    }

    @Test
    public void ErasedTest() {
        //父类引用指向子类对象，典型多态
        Plate<Fruit> plate = new FruitType(new Apple());

        //新建一个父类引用指向pear对象
        Fruit fruit = new Pear();
        //修改前盘子中的水果信息
        plate.printInfo();
        //修改盘子中的水果
        plate.setItem(fruit);
        //修改后的盘子中的水果信息
        plate.printInfo();

        //改变父类引用指向
        plate = new FruitColor(new Pear());

        //修改前盘子中的水果信息
        plate.printInfo();
        plate.setItem(new Apple());
        plate.printInfo();
    }

}

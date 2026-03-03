package Design;

// 单例模式（Singleton Pattern）是一种设计模式，确保一个类只有一个实例，并提供一个全局访问点来获取该实例。
// 单例模式的主要目的是控制对象的创建，避免重复创建对象，从而节省资源和保证数据的一致性。
public class Singleton {
//    // 饿汉式单例：在类加载时就创建实例，线程安全，但可能会浪费资源
//    // 静态成员变量
//    //1、私有构造方法:外界无法使用构造方法来创建本类的实例对象
//    private Singleton(){}
//
//    //2、静态成员变量的方式: 创建本类对象
//    private static Singleton instance = new Singleton();
//
//    //3、提供一个获取单例的入口，注意是 public 和 static
//    public static Singleton getInstance(){
//        return instance;
//    }

//    // 静态代码块
//    //1、私有构造方法:外界无法使用构造方法来创建本类的实例对象
//    private Singleton(){}
//
//    //2、声明一个成员变量
//    private static Singleton instance;
//
//    //3、在静态代码块中进行赋值
//    static {
//        instance = new Singleton();
//    }
//
//    //4、提供一个获取单例的入口，注意是 public 和 static
//    public static Singleton getInstance(){
//        return instance;
//    }

//    // 枚举方式
//    //1、定义一个枚举类，枚举类的实例就是单例对象
//    public enum SingletonEnum {
//        INSTANCE; // 枚举实例
//    }

    // 懒汉式单例
    // 双重检查锁（Double-Checked Locking）单例：在需要时才创建实例，线程安全，性能较好
    // 为什么需要双重检查锁？因为在多线程环境下，如果多个线程同时调用getInstance()方法
    // 可能会导致多个线程进入if(instance == null)的判断，从而创建多个实例。
    // 通过在同步块内再次检查instance是否为null，可以确保只有一个线程能够创建实例，其他线程会等待并获取到已经创建的实例。
    //1、私有构造方法:外界无法使用构造方法来创建本类的实例对象
    private Singleton(){}

    //2、声明一个成员变量，使用volatile关键字保证可见性和禁止指令重排
    private static volatile Singleton instance;

    //3、提供一个获取单例的入口，注意是 public 和 static
    public static Singleton getInstance(){
        if(instance == null){ // 第一次检查，如果instance不为null，则直接返回，不需要进入同步块，提高性能
            synchronized (Singleton.class){ // 同步块，锁住类对象，保证只有一个线程能进入
                if(instance == null){ // 第二次检查，如果instance仍然为null，则创建实例
                    instance = new Singleton();
                }
            }
        }
        return instance; // 返回单例对象
    }
}

package org.thread;
/**
 * Created by hpf on 2017/3/19.
 * 功能说明：启动线程在10个以内，线程进入阻塞状态，一旦线程到达10个，
 * 那么这些线程将被唤醒从而重新进入就绪队列
 * 问题：
 *  1：如果不在线程启动的时候添加（1），那么线程就会被阻塞，这是为什么？
 */
public class ThreadDemo1 implements Runnable{
    public static Object object=new Object();//静态块锁
    public volatile int count=0;//计数
    @Override
    public void run() {
        synchronized(object){
            count++;//计数器自动加一
            //如果还没有到10个，就wait
            if(count<=10 || count>=1){
                System.out.println("count="+count);
                try{
                    System.out.println(Thread.currentThread().getName()+"线程进入wait");
                    object.wait();
                    System.out.println(Thread.currentThread().getName()+"线程被唤醒!!!!");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        ThreadDemo1 t1=new ThreadDemo1();
        for(int i=0;i<10;i++){
            String name=""+i;
            new Thread(t1,name).start();
            Thread.sleep(100);//（1）
        }
        synchronized (object){
            object.notifyAll();
        }
    }
}


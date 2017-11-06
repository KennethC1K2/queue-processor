package com.ckcc.disruptor.demo;

import java.util.PriorityQueue;
import java.util.concurrent.*;

public class FutureTest {

    public static void main(String[] args) throws Exception {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        final int age = 20;
        Future<String> future = executor.submit(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(1000L);
                return ">>> " + age;
            }
        });
        Future future1 = executor.submit(new Runnable() {
            public void run() {
                System.out.println(">>> " + age + " thread");
            }
        });
        final UserInfo userInfo = new UserInfo();
        Future<UserInfo> future2 = executor.submit(new Runnable() {
            public void run() {
                userInfo.setUserName("CK");
                userInfo.setAge(20);
            }
        }, userInfo);
        System.out.println(future.get());
        System.out.println(future1.get() + " | " + future1.isDone());
        System.out.println(future2.get());
    }

    static class UserInfo {
        private String userName;
        private Integer age;

        public UserInfo() {
        }

        public UserInfo(String userName, Integer age) {
            this.userName = userName;
            this.age = age;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "userName='" + userName + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}

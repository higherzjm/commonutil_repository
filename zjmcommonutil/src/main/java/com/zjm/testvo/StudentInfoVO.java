package com.zjm.testvo;

public  class  StudentInfoVO{
        private String name;
        private Integer age;

       @Override
       public String toString() {
           return "com.zjm.testvo.StudentInfo{" +
                   "name='" + name + '\'' +
                   ", age=" + age +
                   '}';
       }

       public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
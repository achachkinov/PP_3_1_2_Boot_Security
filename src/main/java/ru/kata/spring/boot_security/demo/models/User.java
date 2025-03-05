package ru.kata.spring.boot_security.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "firstName")
   private String firstName;

   @Column(name = "lastName")
   private String lastName;

   @Column(name = "Age")
   private String age;

   public User() {}
   
   public User(String firstName, String lastName, String age) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.age = age;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getAge() {
      return age;
   }

   public void setAge(String age) {
      this.age = age;
   }
}

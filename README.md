# VueJS & Spring Boot CRUD App

### Description
The aim of the project is that how to create full stack web app using VueJS and Spring Boot.
The second idea is understanding structure of project.

> Technologies & Frameworks
* Frontend 
    * VueJS
        * [*vue-resource: ^1.5.1*](https://github.com/pagekit/vue-resource)
        * [*vue-router: ^3.0.2*](https://router.vuejs.org/)
        * [*vuelidate: ^0.7.4*](https://github.com/vuelidate/vuelidate)
* Backend
    * Spring Boot: 2.0.3.RELEASE
        * *actuator*
        * *data-jpa*
        * *web*
        * *test*
        * *swagger*
        * *h2database*
        
> Project Topic

This project is a basic CRUD application. It fetch users from api server. You can apply create, update and delete
operations and see the results on the users table.

> REST Routes

**Backend:**

| Name   | Path             | HTTP Verb | Purpose                                     	 |
|--------|------------------|-----------|------------------------------------------------|
| Create | /api/user/save   | POST      | Create new user                                |
| Update | /api/user/update | PUT       | Update particular user                         |
| Remove | /api/user/delete | DELETE    | Delete particular user                         |
| Index  | /api/user/get    | GET       | Get particular user     	                     |
| Index  | /api/user/get    | GET       | List all user                         	     |

* After run backend server, you can see backend routes on [**Swagger UI**](http://localhost:8082/swagger-ui.html)

**Frontend:**

| Name   | Path            | HTTP Verb | Purpose                                     	|
|--------|-----------------|-----------|------------------------------------------------|
| Index  | /               | GET       | Project topic                               	|
| New    | /create         | GET       | Show new user form                             |
| Edit   | /:id/edit       | GET       | Show edit user form                          	|


### Installation

1. After cloning process, in project directory run `mvn clean install`, it will install all necessary dependencies.
2. To run complete app run `mvn --projects backend spring-boot:run` and [result](http://localhost:8082/)
3. [OPTIONAL] To run Vue CLI, run `npm run serve` command in frontend directory. This will work on `localhost:8080`

to see more detail : [spring-boot-vuejs](https://github.com/jonashackt/spring-boot-vuejs#project-setup)
